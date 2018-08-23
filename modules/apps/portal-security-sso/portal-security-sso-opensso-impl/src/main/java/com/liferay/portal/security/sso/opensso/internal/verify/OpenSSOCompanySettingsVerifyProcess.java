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

package com.liferay.portal.security.sso.opensso.internal.verify;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.security.sso.opensso.constants.LegacyOpenSSOPropsKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConfigurationKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.verify.BaseCompanySettingsVerifyProcess;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Greenwald
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.portal.security.sso.opensso",
	service = VerifyProcess.class
)
public class OpenSSOCompanySettingsVerifyProcess
	extends BaseCompanySettingsVerifyProcess {

	@Override
	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	@Override
	protected Set<String> getLegacyPropertyKeys() {
		return SetUtil.fromArray(LegacyOpenSSOPropsKeys.OPENSSO_KEYS);
	}

	@Override
	protected String[][] getRenamePropertyKeysArray() {
		return new String[][] {
			{
				LegacyOpenSSOPropsKeys.OPENSSO_EMAIL_ADDRESS_ATTR,
				OpenSSOConfigurationKeys.EMAIL_ADDRESS_ATTR
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_AUTH_ENABLED,
				OpenSSOConfigurationKeys.AUTH_ENABLED
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_FIRST_NAME_ATTR,
				OpenSSOConfigurationKeys.FIRST_NAME_ATTR
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_IMPORT_FROM_LDAP,
				OpenSSOConfigurationKeys.IMPORT_FROM_LDAP
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_LAST_NAME_ATTR,
				OpenSSOConfigurationKeys.LAST_NAME_ATTR
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_LOGIN_URL,
				OpenSSOConfigurationKeys.LOGIN_URL
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_ON_SESSION_EXPIRATION,
				OpenSSOConfigurationKeys.LOGOUT_ON_SESSION_EXPIRATION
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_URL,
				OpenSSOConfigurationKeys.LOGOUT_URL
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_SCREEN_NAME_ATTR,
				OpenSSOConfigurationKeys.SCREEN_NAME_ATTR
			},
			{
				LegacyOpenSSOPropsKeys.OPENSSO_SERVICE_URL,
				OpenSSOConfigurationKeys.SERVICE_URL
			}
		};
	}

	@Override
	protected SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Override
	protected String getSettingsId() {
		return OpenSSOConstants.SERVICE_NAME;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private CompanyLocalService _companyLocalService;
	private SettingsFactory _settingsFactory;

}