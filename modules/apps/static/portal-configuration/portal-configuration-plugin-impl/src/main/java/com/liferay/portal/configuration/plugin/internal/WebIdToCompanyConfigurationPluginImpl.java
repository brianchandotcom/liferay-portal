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

package com.liferay.portal.configuration.plugin.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationPlugin;

/**
 * @author Raymond Augé
 */
public class WebIdToCompanyConfigurationPluginImpl
	implements ConfigurationPlugin {

	public WebIdToCompanyConfigurationPluginImpl(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void modifyConfiguration(
		ServiceReference<?> serviceReference,
		Dictionary<String, Object> properties) {

		String webId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Validator.isNull(webId)) {
			return;
		}

		if (Objects.equals(webId, "default")) {
			webId = PropsValues.COMPANY_DEFAULT_WEB_ID;
		}

		Company company = null;

		try {
			ServiceReference<CompanyLocalService> companyLocalServiceReference =
				_bundleContext.getServiceReference(CompanyLocalService.class);

			if (companyLocalServiceReference != null) {
				CompanyLocalService companyLocalService =
					_bundleContext.getService(companyLocalServiceReference);

				company = companyLocalService.getCompanyByWebId(webId);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Skip web ID " + webId);
			}

			return;
		}

		if (company != null) {
			properties.put("companyId", company.getCompanyId());

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Injected company ID ", company.getCompanyId(),
						" for web ID ", webId));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebIdToCompanyConfigurationPluginImpl.class);

	private final BundleContext _bundleContext;

}