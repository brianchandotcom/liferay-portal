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
public class UserBlueprintImpl implements UserBlueprint {

	@Override
	public int getBirthdayDay() {
		return _birthdayDay;
	}

	@Override
	public int getBirthdayMonth() {
		return _birthdayMonth;
	}

	@Override
	public int getBirthdayYear() {
		return _birthdayYear;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public long getCreatorUserId() {
		return _creatorUserId;
	}

	@Override
	public String getEmailAddress() {
		return _emailAddress;
	}

	@Override
	public long getFacebookId() {
		return _facebookId;
	}

	@Override
	public String getFirstName() {
		return _firstName;
	}

	@Override
	public long[] getGroupIds() {
		return _groupIds;
	}

	@Override
	public String getJobTitle() {
		return _jobTitle;
	}

	@Override
	public String getLastName() {
		return _lastName;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public String getMiddleName() {
		return _middleName;
	}

	@Override
	public String getOpenId() {
		return _openId;
	}

	@Override
	public long[] getOrganizationIds() {
		return _organizationIds;
	}

	@Override
	public String getPassword1() {
		return _password1;
	}

	@Override
	public String getPassword2() {
		return _password2;
	}

	@Override
	public long getPrefixId() {
		return _prefixId;
	}

	@Override
	public long[] getRoleIds() {
		return _roleIds;
	}

	@Override
	public String getScreenName() {
		return _screenName;
	}

	@Override
	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	@Override
	public long getSuffixId() {
		return _suffixId;
	}

	@Override
	public long[] getUserGroupIds() {
		return _userGroupIds;
	}

	@Override
	public boolean isAutoPassword() {
		return _autoPassword;
	}

	@Override
	public boolean isAutoScreenName() {
		return _autoScreenName;
	}

	@Override
	public boolean isMale() {
		return _male;
	}

	@Override
	public boolean isSendMail() {
		return _sendMail;
	}

	public static class UserBlueprintBuilderImpl
		implements UserBlueprintBuilder {

		@Override
		public UserBlueprintBuilder autoPassword(boolean autoPassword) {
			_userBlueprintImpl._autoPassword = autoPassword;

			return this;
		}

		@Override
		public UserBlueprintBuilder birthdayDay(int birthdayDay) {
			_userBlueprintImpl._birthdayDay = birthdayDay;

			return this;
		}

		@Override
		public UserBlueprintBuilder birthdayMonth(int birthdayMonth) {
			_userBlueprintImpl._birthdayMonth = birthdayMonth;

			return this;
		}

		@Override
		public UserBlueprintBuilder birthdayYear(int birthdayYear) {
			_userBlueprintImpl._birthdayYear = birthdayYear;

			return this;
		}

		@Override
		public UserBlueprint build() {
			return new UserBlueprintImpl(_userBlueprintImpl);
		}

		@Override
		public UserBlueprintBuilder companyId(long companyId) {
			_userBlueprintImpl._companyId = companyId;

			return this;
		}

		@Override
		public UserBlueprintBuilder emailAddress(String emailAddress) {
			_userBlueprintImpl._emailAddress = emailAddress;

			return this;
		}

		@Override
		public UserBlueprintBuilder firstName(String firstName) {
			_userBlueprintImpl._firstName = firstName;

			return this;
		}

		@Override
		public UserBlueprintBuilder groupIds(long... groupIds) {
			_userBlueprintImpl._groupIds = groupIds;

			return this;
		}

		@Override
		public UserBlueprintBuilder jobTitle(String jobTitle) {
			_userBlueprintImpl._jobTitle = jobTitle;

			return this;
		}

		@Override
		public UserBlueprintBuilder lastName(String lastName) {
			_userBlueprintImpl._lastName = lastName;

			return this;
		}

		@Override
		public UserBlueprintBuilder locale(Locale locale) {
			_userBlueprintImpl._locale = locale;

			return this;
		}

		@Override
		public UserBlueprintBuilder middleName(String middleName) {
			_userBlueprintImpl._middleName = middleName;

			return this;
		}

		@Override
		public UserBlueprintBuilder password1(String password1) {
			_userBlueprintImpl._password1 = password1;

			return this;
		}

		@Override
		public UserBlueprintBuilder password2(String password2) {
			_userBlueprintImpl._password2 = password2;

			return this;
		}

		@Override
		public UserBlueprintBuilder screenName(String screenName) {
			_userBlueprintImpl._screenName = screenName;

			return this;
		}

		@Override
		public UserBlueprintBuilder serviceContext(
			ServiceContext serviceContext) {

			_userBlueprintImpl._serviceContext = serviceContext;

			return this;
		}

		@Override
		public UserBlueprintBuilder userId(long userId) {
			_userBlueprintImpl._userId = userId;

			return this;
		}

		private final UserBlueprintImpl _userBlueprintImpl =
			new UserBlueprintImpl();

	}

	private UserBlueprintImpl() {
	}

	private UserBlueprintImpl(UserBlueprintImpl userBlueprintImpl) {
		_autoPassword = userBlueprintImpl._autoPassword;
		_autoScreenName = userBlueprintImpl._autoScreenName;
		_birthdayDay = userBlueprintImpl._birthdayDay;
		_birthdayMonth = userBlueprintImpl._birthdayMonth;
		_birthdayYear = userBlueprintImpl._birthdayYear;
		_companyId = userBlueprintImpl._companyId;
		_creatorUserId = userBlueprintImpl._creatorUserId;
		_emailAddress = userBlueprintImpl._emailAddress;
		_facebookId = userBlueprintImpl._facebookId;
		_firstName = userBlueprintImpl._firstName;
		_groupIds = userBlueprintImpl._groupIds;
		_jobTitle = userBlueprintImpl._jobTitle;
		_lastName = userBlueprintImpl._lastName;
		_locale = userBlueprintImpl._locale;
		_male = userBlueprintImpl._male;
		_middleName = userBlueprintImpl._middleName;
		_openId = userBlueprintImpl._openId;
		_organizationIds = userBlueprintImpl._organizationIds;
		_password1 = userBlueprintImpl._password1;
		_password2 = userBlueprintImpl._password2;
		_prefixId = userBlueprintImpl._prefixId;
		_roleIds = userBlueprintImpl._roleIds;
		_screenName = userBlueprintImpl._screenName;
		_sendMail = userBlueprintImpl._sendMail;
		_serviceContext = userBlueprintImpl._serviceContext;
		_suffixId = userBlueprintImpl._suffixId;
		_userGroupIds = userBlueprintImpl._userGroupIds;
		_userId = userBlueprintImpl._userId;
	}

	private boolean _autoPassword;
	private boolean _autoScreenName;
	private int _birthdayDay;
	private int _birthdayMonth;
	private int _birthdayYear;
	private long _companyId;
	private long _creatorUserId;
	private String _emailAddress;
	private long _facebookId;
	private String _firstName;
	private long[] _groupIds;
	private String _jobTitle;
	private String _lastName;
	private Locale _locale;
	private boolean _male;
	private String _middleName;
	private String _openId;
	private long[] _organizationIds;
	private String _password1;
	private String _password2;
	private long _prefixId;
	private long[] _roleIds;
	private String _screenName;
	private boolean _sendMail;
	private ServiceContext _serviceContext;
	private long _suffixId;
	private long[] _userGroupIds;
	private long _userId;

}