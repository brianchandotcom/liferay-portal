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

package com.liferay.portal.instances.internal.configuration;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.instances.internal.configuration.PortalInstancesConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class PortalInstancesConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties)
		throws PortalException {

		PortalInstancesConfiguration portalInstancesConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalInstancesConfiguration.class, properties);

		String webId = _getWebId(properties);
		String virtualHostname = portalInstancesConfiguration.virtualHostname();
		String mx = portalInstancesConfiguration.mx();
		int maxUsers = portalInstancesConfiguration.maxUsers();
		boolean active = portalInstancesConfiguration.active();

		Company company = null;

		try {
			company = _companyLocalService.getCompanyByWebId(webId);
		}
		catch (NoSuchCompanyException noSuchCompanyException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchCompanyException);
			}
		}

		if (company == null) {
			company = _companyLocalService.addCompany(
				null, webId, virtualHostname, mx, maxUsers, active, null, null,
				null, null, null, null);

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(
						company.getCompanyId())) {

				_portalInstancesLocalService.initializePortalInstance(
					company.getCompanyId(),
					portalInstancesConfiguration.siteInitializerKey());
			}
		}
		else {
			if (company.getCompanyId() ==
					_portalInstancesLocalService.getDefaultCompanyId()) {

				active = true;
			}

			_companyLocalService.updateCompany(
				company.getCompanyId(), virtualHostname, mx, maxUsers, active);
		}

		_portalInstancesLocalService.synchronizePortalInstances();
	}

	private String _getWebId(Map<String, Object> properties) {
		String pid = GetterUtil.getString(
			properties.get(Constants.SERVICE_PID));

		int index = pid.indexOf('~');

		if (index > 0) {
			pid = pid.substring(index + 1);
		}

		return pid;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstancesConfigurationFactory.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}