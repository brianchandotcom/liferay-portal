/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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