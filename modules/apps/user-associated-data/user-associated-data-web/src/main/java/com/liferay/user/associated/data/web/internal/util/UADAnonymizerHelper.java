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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfigurationRetriever;

import java.util.Calendar;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = UADAnonymizerHelper.class)
public class UADAnonymizerHelper {

	public User getAnonymousUser() throws Exception {
		return _getAnonymousUser(CompanyThreadLocal.getCompanyId());
	}

	public User getAnonymousUser(long companyId) throws Exception {
		return _getAnonymousUser(companyId);
	}

	public boolean isAnonymousUser(User user) {
		try {
			User anonymousUser = getAnonymousUser(user.getCompanyId());

			if (user.getUserId() == anonymousUser.getUserId()) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			return false;
		}
	}

	private User _createAnonymousUser(long companyId) throws Exception {
		long creatorUserId = 0;

		String password = StringUtil.randomString();

		boolean autoPassword = false;
		String password1 = password;
		String password2 = password;
		boolean autoScreenName = false;

		long counter = _counterLocalService.increment(
			UADAnonymizerHelper.class.getName());

		String screenName = StringBundler.concat(
			"Anonymous", companyId, StringPool.UNDERLINE, counter);

		Company company = _companyLocalService.getCompany(companyId);

		String emailAddress = StringBundler.concat(
			screenName, StringPool.AT, company.getMx());

		Locale locale = LocaleThreadLocal.getDefaultLocale();
		String firstName = "Anonymous";
		String middleName = StringPool.BLANK;
		String lastName = "Anonymous";
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = null;

		User anonymousUser = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);

		anonymousUser.setComments(
			StringBundler.concat(
				"This user is automatically created by the UAD application. ",
				"Application data anonymized by Personal Data Erasure will be ",
				"assigned to this user."));

		_userLocalService.updateUser(anonymousUser);

		_userLocalService.updateStatus(
			anonymousUser.getUserId(), WorkflowConstants.STATUS_INACTIVE,
			new ServiceContext());

		return anonymousUser;
	}

	private User _getAnonymousUser(long companyId) throws Exception {
		Optional<Configuration> configurationOptional =
			_anonymousUserConfigurationRetriever.getOptional(companyId);

		if (!configurationOptional.isPresent()) {
			User anonymousUser = _createAnonymousUser(companyId);

			Configuration configuration =
				_configurationAdmin.createFactoryConfiguration(
					AnonymousUserConfiguration.class.getName(),
					StringPool.QUESTION);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("companyId", companyId);
			properties.put("userId", anonymousUser.getUserId());

			configuration.update(properties);

			return anonymousUser;
		}

		Configuration configuration = configurationOptional.get();

		Dictionary<String, Object> properties = configuration.getProperties();

		AnonymousUserConfiguration anonymousUserConfiguration =
			ConfigurableUtil.createConfigurable(
				AnonymousUserConfiguration.class, properties);

		User anonymousUser = _userLocalService.fetchUser(
			anonymousUserConfiguration.userId());

		if (anonymousUser != null) {
			return anonymousUser;
		}

		anonymousUser = _createAnonymousUser(companyId);

		properties.put("userId", anonymousUser.getUserId());

		configuration.update(properties);

		return anonymousUser;
	}

	@Reference
	private AnonymousUserConfigurationRetriever
		_anonymousUserConfigurationRetriever;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private UserLocalService _userLocalService;

}