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

package com.liferay.user.generator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.user.generator.configuration.UserGeneratorConfiguration;

import java.util.ArrayList;
import java.util.Locale;
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
	configurationPid = "com.liferay.user.generator.configuration.UserGeneratorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class UserGenerator {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_userGeneratorConfiguration = ConfigurableUtil.createConfigurable(
			UserGeneratorConfiguration.class, properties);

		try {
			Company company = _companyLocalService.getCompanyByVirtualHost(
				_userGeneratorConfiguration.virtualHostName());

			long companyId = company.getPrimaryKey();

			long creatorUserId = _userLocalService.getDefaultUserId(companyId);

			boolean autoPassword = false;
			String password1 = "test";
			String password2 = "test";
			boolean autoScreenName = false;
			String screenName = "shrall.tang";
			String emailAddress = "shrall.tang@test.com";
			long facebookId = 0;
			String openId = StringPool.BLANK;
			Locale locale = LocaleUtil.getDefault();
			String firstName = "Shrall";
			String middleName = StringPool.BLANK;
			String lastName = "Tang";
			long prefixId = 0;
			long suffixId = 0;
			boolean male = false;
			int birthdayMonth = 9;
			int birthdayDay = 14;
			int birthdayYear = 1994;
			String jobTitle = "Test Engineer";
			long[] groupIds = null;
			long[] organizationIds = null;
			long[] roleIds = null;
			long[] userGroupIds = null;
			boolean sendEmail = false;

			User user = _userLocalService.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
				new ServiceContext());

			_addedUserList.add(user);

			//outside future for loop to iterate csv rows

			if (_log.isInfoEnabled()) {
				_log.info(
					_userGeneratorConfiguration.customActivationMessage());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (User user : _addedUserList) {
			try {
				_userLocalService.deleteUser(user);
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(UserGenerator.class);

	private final ArrayList<User> _addedUserList = new ArrayList<>();

	@Reference
	private CompanyLocalService _companyLocalService;

	private UserGeneratorConfiguration _userGeneratorConfiguration;

	@Reference
	private UserLocalService _userLocalService;

}