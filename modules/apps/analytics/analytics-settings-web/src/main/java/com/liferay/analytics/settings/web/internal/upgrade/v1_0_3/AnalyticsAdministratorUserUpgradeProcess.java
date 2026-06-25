/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.web.internal.upgrade.v1_0_3;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Shuyang Zhou
 */
public class AnalyticsAdministratorUserUpgradeProcess extends UpgradeProcess {

	public AnalyticsAdministratorUserUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin,
		RoleLocalService roleLocalService, UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations;

		if (PropsValues.DATABASE_PARTITION_ENABLED) {
			configurations = _configurationAdmin.listConfigurations(
				StringBundler.concat(
					"(&(companyId=", CompanyThreadLocal.getCompanyId(),
					")(service.pid=", AnalyticsConfiguration.class.getName(),
					"*))"));
		}
		else {
			configurations = _configurationAdmin.listConfigurations(
				"(service.pid=" + AnalyticsConfiguration.class.getName() +
					"*)");
		}

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if ((properties == null) ||
				Validator.isNull(properties.get("token"))) {

				continue;
			}

			long companyId = GetterUtil.getLong(properties.get("companyId"));

			Company company = _companyLocalService.fetchCompany(companyId);

			if (company == null) {
				continue;
			}

			User user = _userLocalService.fetchUserByScreenName(
				companyId,
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

			if (user != null) {
				continue;
			}

			_addAnalyticsAdministratorUser(company);
		}
	}

	private void _addAnalyticsAdministratorUser(Company company)
		throws Exception {

		long companyId = company.getCompanyId();

		Role role = _roleLocalService.getRole(
			companyId, "Analytics Administrator");

		User user = _userLocalService.addUser(
			0, companyId, true, null, null, false,
			AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN,
			"analytics.administrator@" + company.getMx(),
			LocaleUtil.fromLanguageId(
				UpgradeProcessUtil.getDefaultLanguageId(companyId)),
			"Analytics", "", "Administrator", 0, 0, true, 0, 1, 1970, "",
			UserConstants.TYPE_REGULAR, null, null,
			new long[] {role.getRoleId()}, null, false, new ServiceContext());

		_userLocalService.updateUser(user);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Added analytics administrator user for company " + companyId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsAdministratorUserUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;

}