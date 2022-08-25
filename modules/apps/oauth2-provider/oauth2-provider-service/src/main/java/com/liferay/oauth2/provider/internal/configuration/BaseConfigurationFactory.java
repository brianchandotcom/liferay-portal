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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public abstract class BaseConfigurationFactory {

	public String createAndRetainConfigMapName(String webId) {
		if (_configMapName != null) {
			return _configMapName;
		}

		if (_serviceId == null) {
			throw new IllegalStateException(
				"setServiceId must be called first");
		}

		return _configMapName = StringBundler.concat(
			_serviceId, StringPool.DASH, webId, "-lxc-ext-init-metadata");
	}

	public String getConfigMapName() {
		return _configMapName;
	}

	public Map<String, String> getExtensionProperties() {
		return _extensionProperties;
	}

	public String getExternalReferenceCode(Map<String, Object> properties) {
		String externalReferenceCode = GetterUtil.getString(
			properties.get(Constants.SERVICE_PID));

		int index = externalReferenceCode.indexOf('~');

		if (index > 0) {
			externalReferenceCode = externalReferenceCode.substring(index + 1);
		}

		return externalReferenceCode;
	}

	public OAuth2Application getOAuth2Application() {
		return _oAuth2Application;
	}

	public String getServiceId() {
		return _serviceId;
	}

	public void setExtensionProperties(
		Map<String, String> extensionProperties) {

		_extensionProperties = extensionProperties;
	}

	public void setOAuth2Application(OAuth2Application oAuth2Application) {
		_oAuth2Application = oAuth2Application;
	}

	public void setServiceId(String serviceId) {
		_serviceId = serviceId;
	}

	protected Company getCompany(Map<String, Object> properties)
		throws Exception {

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId > 0) {
			return companyLocalService.getCompanyById(companyId);
		}

		String webId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Validator.isNotNull(webId)) {
			if (Objects.equals(webId, "default")) {
				webId = PropsValues.COMPANY_DEFAULT_WEB_ID;
			}

			return companyLocalService.getCompanyByWebId(webId);
		}

		throw new IllegalStateException(
			"The property \"companyId\" or " +
				"\"dxp.lxc.liferay.com.virtualInstanceId\" must be set");
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected OAuth2ApplicationLocalService oAuth2ApplicationLocalService;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	protected PortalK8sConfigMapModifier portalK8sConfigMapModifier;

	@Reference
	protected UserLocalService userLocalService;

	private volatile String _configMapName;
	private volatile Map<String, String> _extensionProperties;
	private volatile OAuth2Application _oAuth2Application;
	private volatile String _serviceId;

}