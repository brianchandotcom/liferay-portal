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

package com.liferay.analytics.demo.data.creator;

import com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration;
import com.liferay.analytics.demo.data.creator.csv.UserCsv;
import com.liferay.analytics.demo.data.creator.util.GeneratedDataUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	configurationPid = "com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class AnalyticsCSVDemoDataCreatorImpl {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_loadConfig(properties);

		_userCsv.addCsvUsers(
			_analyticsDemoDataCreatorConfiguration.pathToUserCsv());

		if (_log.isInfoEnabled()) {
			_log.info(
				_analyticsDemoDataCreatorConfiguration.customActivationMessage());
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			_generatedDataUtil.deleteAll();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private void _loadConfig(Map<String, Object> properties) {
		_analyticsDemoDataCreatorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsDemoDataCreatorConfiguration.class, properties);

		try {
			Company company = _companyLocalService.getCompanyByVirtualHost(
				_analyticsDemoDataCreatorConfiguration.virtualHostName());

			long companyId = company.getPrimaryKey();

			_generatedDataUtil.setCompanyId(companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsCSVDemoDataCreatorImpl.class);

	private AnalyticsDemoDataCreatorConfiguration
		_analyticsDemoDataCreatorConfiguration;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GeneratedDataUtil _generatedDataUtil;

	@Reference
	private UserCsv _userCsv;

}