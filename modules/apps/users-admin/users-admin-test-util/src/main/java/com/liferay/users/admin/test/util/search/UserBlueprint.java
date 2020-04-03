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

package com.liferay.users.admin.test.util.search;

import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;

/**
 * @author André de Oliveira
 */
public interface UserBlueprint {

	public int getBirthdayDay();

	public int getBirthdayMonth();

	public int getBirthdayYear();

	public long getCompanyId();

	public long getCreatorUserId();

	public String getEmailAddress();

	public long getFacebookId();

	public String getFirstName();

	public long[] getGroupIds();

	public String getJobTitle();

	public String getLastName();

	public Locale getLocale();

	public String getMiddleName();

	public String getOpenId();

	public long[] getOrganizationIds();

	public String getPassword1();

	public String getPassword2();

	public long getPrefixId();

	public long[] getRoleIds();

	public String getScreenName();

	public ServiceContext getServiceContext();

	public long getSuffixId();

	public long[] getUserGroupIds();

	public boolean isAutoPassword();

	public boolean isAutoScreenName();

	public boolean isMale();

	public boolean isSendMail();

	public interface UserBlueprintBuilder {

		public UserBlueprintBuilder autoPassword(boolean autoPassword);

		public UserBlueprintBuilder birthdayDay(int birthdayDay);

		public UserBlueprintBuilder birthdayMonth(int birthdayMonth);

		public UserBlueprintBuilder birthdayYear(int birthdayYear);

		public UserBlueprint build();

		public UserBlueprintBuilder companyId(long companyId);

		public UserBlueprintBuilder emailAddress(String emailAddress);

		public UserBlueprintBuilder firstName(String firstName);

		public UserBlueprintBuilder groupIds(long... groupIds);

		public UserBlueprintBuilder jobTitle(String jobTitle);

		public UserBlueprintBuilder lastName(String lastName);

		public UserBlueprintBuilder locale(Locale locale);

		public UserBlueprintBuilder middleName(String middleName);

		public UserBlueprintBuilder password1(String password1);

		public UserBlueprintBuilder password2(String password2);

		public UserBlueprintBuilder screenName(String screenName);

		public UserBlueprintBuilder serviceContext(
			ServiceContext serviceContext);

		public UserBlueprintBuilder userId(long userId);

	}

}