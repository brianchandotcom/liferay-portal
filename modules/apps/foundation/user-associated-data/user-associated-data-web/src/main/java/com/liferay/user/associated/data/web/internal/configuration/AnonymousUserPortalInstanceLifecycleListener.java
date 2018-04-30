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

package com.liferay.user.associated.data.web.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;

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
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AnonymousUserPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_registerAnonymousUser(company.getCompanyId(), company.getMx());
	}

	private User _createAnonymousUser(long companyId, String mx)
		throws PortalException {

		long creatorUserId = 0;

		String password = StringUtil.randomString();

		boolean autoPassword = false;
		String password1 = password;
		String password2 = password;
		boolean autoScreenName = false;
		long facebookId = 0;
		String openId = StringPool.BLANK;
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

		String screenName = firstName + String.valueOf(companyId);

		String emailAddress = StringBundler.concat(
			screenName, StringPool.AT, mx);

		User anonymousUser = _userLocalService.addUserWithWorkflow(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		return anonymousUser;
	}

	private void _registerAnonymousUser(long companyId, String mx)
		throws Exception {

		Optional<Configuration> configurationOptional =
			_anonymousUserConfigurationRetriever.getOptional(companyId);

		if (configurationOptional.isPresent()) {
			return;
		}

		User anonymousUser = _createAnonymousUser(companyId, mx);

		Configuration anonymousUserConfiguration =
			_configurationAdmin.createFactoryConfiguration(
				AnonymousUserConfiguration.class.getName(),
				StringPool.QUESTION);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("companyId", companyId);
		properties.put("userId", anonymousUser.getUserId());

		anonymousUserConfiguration.update(properties);
	}

	@Reference
	private AnonymousUserConfigurationRetriever
		_anonymousUserConfigurationRetriever;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private UserLocalService _userLocalService;

}