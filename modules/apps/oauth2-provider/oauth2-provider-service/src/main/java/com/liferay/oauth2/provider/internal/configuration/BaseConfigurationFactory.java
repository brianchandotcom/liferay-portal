/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.oauth2.provider.internal.configuration;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public abstract class BaseConfigurationFactory {

	@Deactivate
	protected void deactivate(Integer reason) throws PortalException {
		if (reason !=
				ComponentConstants.DEACTIVATION_REASON_CONFIGURATION_DELETED) {

			return;
		}

		Log log = getLog();

		if (log.isDebugEnabled()) {
			log.debug("Deactivating " + oAuth2Application);
		}

		oAuth2ApplicationLocalService.deleteOAuth2Application(
			oAuth2Application);

		if (Validator.isNotNull(_configMapName)) {
			portalK8sConfigMapModifier.modifyConfigMap(
				configMapModel -> _extensionProperties.forEach(
					configMapModel.data()::remove),
				_configMapName);
		}
	}

	protected abstract Log getLog();

	protected String getServiceAddress(Company company) {
		return Http.HTTPS_WITH_SLASH.concat(company.getVirtualHostname());
	}

	protected void modifyConfigMap(
		Company company, Map<String, String> extensionProperties,
		Map<String, Object> properties) {

		_extensionProperties = extensionProperties;

		String serviceId = GetterUtil.getString(
			properties.get("ext.lxc.liferay.com.serviceId"));

		if ((portalK8sConfigMapModifier == null) ||
			Validator.isNull(serviceId)) {

			return;
		}

		_configMapName = StringBundler.concat(
			serviceId, StringPool.DASH, company.getWebId(),
			"-lxc-ext-init-metadata");

		portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				extensionProperties.forEach(data::put);

				Map<String, String> labels = configMapModel.labels();

				labels.put(
					"dxp.lxc.liferay.com/virtualInstanceId",
					company.getWebId());
				labels.put(
					"ext.lxc.liferay.com/projectId",
					GetterUtil.getString(
						properties.get("ext.lxc.liferay.com.projectId")));
				labels.put(
					"ext.lxc.liferay.com/projectUid",
					GetterUtil.getString(
						properties.get("ext.lxc.liferay.com.projectUid")));
				labels.put(
					"ext.lxc.liferay.com/serviceId",
					GetterUtil.getString(
						properties.get("ext.lxc.liferay.com.serviceId")));
				labels.put(
					"ext.lxc.liferay.com/serviceUid",
					GetterUtil.getString(
						properties.get("ext.lxc.liferay.com.serviceUid")));
				labels.put("lxc.liferay.com/metadataType", "ext-init");
			},
			_configMapName);
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	protected ModuleServiceLifecycle moduleServiceLifecycle;

	protected volatile OAuth2Application oAuth2Application;

	@Reference
	protected OAuth2ApplicationLocalService oAuth2ApplicationLocalService;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	protected PortalK8sConfigMapModifier portalK8sConfigMapModifier;

	@Reference
	protected UserLocalService userLocalService;

	private volatile String _configMapName;
	private volatile Map<String, String> _extensionProperties;

}