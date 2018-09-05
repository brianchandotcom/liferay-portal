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

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Brian Wing Shun Chan
 */
public class UserAttributes {

	// Mandatory Liferay attributes

	public static final String LIFERAY_COMPANY_ID = "liferay.company.id";

	public static final String LIFERAY_USER_ID = "liferay.user.id";

	public static final String USER_NAME_FULL = "user.name.full";

	// See page 249 of the JSR 286 spec

	public static final String USER_BDATE = "user.bdate";

	public static final String USER_BDATE_DAY = "user.bdate.ymd.day";

	public static final String USER_BDATE_FRACTIONSECOND =
		"user.bdate.fractionsecond";

	public static final String USER_BDATE_HOUR = "user.bdate.hms.hour";

	public static final String USER_BDATE_MINUTE = "user.bdate.hms.minute";

	public static final String USER_BDATE_MONTH = "user.bdate.ymd.month";

	public static final String USER_BDATE_SECOND = "user.bdate.hms.second";

	public static final String USER_BDATE_TIMEZONE = "user.bdate.timezone";

	public static final String USER_BDATE_YEAR = "user.bdate.ymd.year";

	public static final String USER_GENDER = "user.gender";

	public static final String USER_EMPLOYER = "user.employer";

	public static final String USER_DEPARTMENT = "user.department";

	public static final String USER_JOBTITLE = "user.jobtitle";

	public static final String USER_NAME_PREFIX = "user.name.prefix";

	public static final String USER_NAME_GIVEN = "user.name.given";

	public static final String USER_NAME_FAMILY = "user.name.family";

	public static final String USER_NAME_MIDDLE = "user.name.middle";

	public static final String USER_NAME_SUFFIX = "user.name.suffix";

	public static final String USER_NAME_NICKNAME = "user.name.nickName";

	public static final String USER_HOME_INFO_POSTAL_NAME =
		"user.home-info.postal.name";

	public static final String USER_HOME_INFO_POSTAL_STREET =
		"user.home-info.postal.street";

	public static final String USER_HOME_INFO_POSTAL_CITY =
		"user.home-info.postal.city";

	public static final String USER_HOME_INFO_POSTAL_STATEPROV =
		"user.home-info.postal.stateprov";

	public static final String USER_HOME_INFO_POSTAL_POSTALCODE =
		"user.home-info.postal.postalcode";

	public static final String USER_HOME_INFO_POSTAL_COUNTRY =
		"user.home-info.postal.country";

	public static final String USER_HOME_INFO_POSTAL_ORGANIZATION =
		"user.home-info.postal.organization";

	public static final String USER_HOME_INFO_TELECOM_TELEPHONE_INTCODE =
		"user.home-info.telecom.telephone.intcode";

	public static final String USER_HOME_INFO_TELECOM_TELEPHONE_LOCCODE =
		"user.home-info.telecom.telephone.loccode";

	public static final String USER_HOME_INFO_TELECOM_TELEPHONE_NUMBER =
		"user.home-info.telecom.telephone.number";

	public static final String USER_HOME_INFO_TELECOM_TELEPHONE_EXT =
		"user.home-info.telecom.telephone.ext";

	public static final String USER_HOME_INFO_TELECOM_TELEPHONE_COMMENT =
		"user.home-info.telecom.telephone.comment";

	public static final String USER_HOME_INFO_TELECOM_FAX_INTCODE =
		"user.home-info.telecom.fax.intcode";

	public static final String USER_HOME_INFO_TELECOM_FAX_LOCCODE =
		"user.home-info.telecom.fax.loccode";

	public static final String USER_HOME_INFO_TELECOM_FAX_NUMBER =
		"user.home-info.telecom.fax.number";

	public static final String USER_HOME_INFO_TELECOM_FAX_EXT =
		"user.home-info.telecom.fax.ext";

	public static final String USER_HOME_INFO_TELECOM_FAX_COMMENT =
		"user.home-info.telecom.fax.comment";

	public static final String USER_HOME_INFO_TELECOM_MOBILE_INTCODE =
		"user.home-info.telecom.mobile.intcode";

	public static final String USER_HOME_INFO_TELECOM_MOBILE_LOCCODE =
		"user.home-info.telecom.mobile.loccode";

	public static final String USER_HOME_INFO_TELECOM_MOBILE_NUMBER =
		"user.home-info.telecom.mobile.number";

	public static final String USER_HOME_INFO_TELECOM_MOBILE_EXT =
		"user.home-info.telecom.mobile.ext";

	public static final String USER_HOME_INFO_TELECOM_MOBILE_COMMENT =
		"user.home-info.telecom.mobile.comment";

	public static final String USER_HOME_INFO_TELECOM_PAGER_INTCODE =
		"user.home-info.telecom.pager.intcode";

	public static final String USER_HOME_INFO_TELECOM_PAGER_LOCCODE =
		"user.home-info.telecom.pager.loccode";

	public static final String USER_HOME_INFO_TELECOM_PAGER_NUMBER =
		"user.home-info.telecom.pager.number";

	public static final String USER_HOME_INFO_TELECOM_PAGER_EXT =
		"user.home-info.telecom.pager.ext";

	public static final String USER_HOME_INFO_TELECOM_PAGER_COMMENT =
		"user.home-info.telecom.pager.comment";

	public static final String USER_HOME_INFO_ONLINE_EMAIL =
		"user.home-info.online.email";

	public static final String USER_HOME_INFO_ONLINE_URI =
		"user.home-info.online.uri";

	public static final String USER_BUSINESS_INFO_POSTAL_NAME =
		"user.business-info.postal.name";

	public static final String USER_BUSINESS_INFO_POSTAL_STREET =
		"user.business-info.postal.street";

	public static final String USER_BUSINESS_INFO_POSTAL_CITY =
		"user.business-info.postal.city";

	public static final String USER_BUSINESS_INFO_POSTAL_STATEPROV =
		"user.business-info.postal.stateprov";

	public static final String USER_BUSINESS_INFO_POSTAL_POSTALCODE =
		"user.business-info.postal.postalcode";

	public static final String USER_BUSINESS_INFO_POSTAL_COUNTRY =
		"user.business-info.postal.country";

	public static final String USER_BUSINESS_INFO_POSTAL_ORGANIZATION =
		"user.business-info.postal.organization";

	public static final String USER_BUSINESS_INFO_TELECOM_TELEPHONE_INTCODE =
		"user.business-info.telecom.telephone.intcode";

	public static final String USER_BUSINESS_INFO_TELECOM_TELEPHONE_LOCCODE =
		"user.business-info.telecom.telephone.loccode";

	public static final String USER_BUSINESS_INFO_TELECOM_TELEPHONE_NUMBER =
		"user.business-info.telecom.telephone.number";

	public static final String USER_BUSINESS_INFO_TELECOM_TELEPHONE_EXT =
		"user.business-info.telecom.telephone.ext";

	public static final String USER_BUSINESS_INFO_TELECOM_TELEPHONE_COMMENT =
		"user.business-info.telecom.telephone.comment";

	public static final String USER_BUSINESS_INFO_TELECOM_FAX_INTCODE =
		"user.business-info.telecom.fax.intcode";

	public static final String USER_BUSINESS_INFO_TELECOM_FAX_LOCCODE =
		"user.business-info.telecom.fax.loccode";

	public static final String USER_BUSINESS_INFO_TELECOM_FAX_NUMBER =
		"user.business-info.telecom.fax.number";

	public static final String USER_BUSINESS_INFO_TELECOM_FAX_EXT =
		"user.business-info.telecom.fax.ext";

	public static final String USER_BUSINESS_INFO_TELECOM_FAX_COMMENT =
		"user.business-info.telecom.fax.comment";

	public static final String USER_BUSINESS_INFO_TELECOM_MOBILE_INTCODE =
		"user.business-info.telecom.mobile.intcode";

	public static final String USER_BUSINESS_INFO_TELECOM_MOBILE_LOCCODE =
		"user.business-info.telecom.mobile.loccode";

	public static final String USER_BUSINESS_INFO_TELECOM_MOBILE_NUMBER =
		"user.business-info.telecom.mobile.number";

	public static final String USER_BUSINESS_INFO_TELECOM_MOBILE_EXT =
		"user.business-info.telecom.mobile.ext";

	public static final String USER_BUSINESS_INFO_TELECOM_MOBILE_COMMENT =
		"user.business-info.telecom.mobile.comment";

	public static final String USER_BUSINESS_INFO_TELECOM_PAGER_INTCODE =
		"user.business-info.telecom.pager.intcode";

	public static final String USER_BUSINESS_INFO_TELECOM_PAGER_LOCCODE =
		"user.business-info.telecom.pager.loccode";

	public static final String USER_BUSINESS_INFO_TELECOM_PAGER_NUMBER =
		"user.business-info.telecom.pager.number";

	public static final String USER_BUSINESS_INFO_TELECOM_PAGER_EXT =
		"user.business-info.telecom.pager.ext";

	public static final String USER_BUSINESS_INFO_TELECOM_PAGER_COMMENT =
		"user.business-info.telecom.pager.comment";

	public static final String USER_BUSINESS_INFO_ONLINE_EMAIL =
		"user.business-info.online.email";

	public static final String USER_BUSINESS_INFO_ONLINE_URI =
		"user.business-info.online.uri";

	public static final String USER_LOGIN_ID = "user.login.id";

	public UserAttributes(User user) {
		_user = user;

		Address businessAddress = null;
		Address personalAddress = null;

		Phone businessPhone = null;
		Phone businessFaxPhone = null;
		Phone mobilePhone = null;
		Phone pagerPhone = null;
		Phone personalPhone = null;
		Phone personalFaxPhone = null;

		try {
			for (Address address : user.getAddresses()) {
				ListType listType = address.getType();

				String listTypeName = listType.getName();

				if (listTypeName.equals("business")) {
					businessAddress = address;
				}
				else if (listTypeName.equals("personal")) {
					personalAddress = address;
				}
			}

			for (Phone phone : user.getPhones()) {
				ListType listType = phone.getType();

				String listTypeName = listType.getName();

				if (listTypeName.equals("business")) {
					businessPhone = phone;
				}
				else if (listTypeName.equals("business-fax")) {
					businessFaxPhone = phone;
				}
				else if (listTypeName.equals("mobile-phone")) {
					mobilePhone = phone;
				}
				else if (listTypeName.equals("pager")) {
					pagerPhone = phone;
				}
				else if (listTypeName.equals("personal")) {
					personalPhone = phone;
				}
				else if (listTypeName.equals("personal-fax")) {
					personalFaxPhone = phone;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_businessAddress = businessAddress;
		_personalAddress = personalAddress;

		_businessPhone = businessPhone;
		_businessFaxPhone = businessFaxPhone;
		_mobilePhone = mobilePhone;
		_pagerPhone = pagerPhone;
		_personalPhone = personalPhone;
		_personalFaxPhone = personalFaxPhone;
	}

	public String getValue(String name) throws PortalException {
		if (name == null) {
			return null;
		}

		if (name.equals(LIFERAY_COMPANY_ID)) {
			return String.valueOf(_user.getCompanyId());
		}
		else if (name.equals(LIFERAY_USER_ID)) {
			return String.valueOf(_user.getUserId());
		}
		else if (name.equals(USER_NAME_FULL)) {
			return _user.getFullName();
		}
		else if (name.equals(USER_BDATE)) {
			Date birthday = _user.getBirthday();

			return birthday.toString();
		}
		else if (name.equals(USER_BDATE_DAY)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.DATE));
			}

			return null;
		}
		else if (name.equals(USER_BDATE_FRACTIONSECOND)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.MILLISECOND));
			}

			return null;
		}
		else if (name.equals(USER_BDATE_HOUR)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.HOUR));
			}

			return null;
		}
		else if (name.equals(USER_BDATE_MINUTE)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.MINUTE));
			}

			return null;
		}
		else if (name.equals(USER_BDATE_MONTH)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.MONTH) + 1);
			}

			return null;
		}
		else if (name.equals(USER_BDATE_SECOND)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.SECOND));
			}

			return null;
		}
		else if (name.equals(USER_BDATE_TIMEZONE)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.ZONE_OFFSET));
			}

			return null;
		}
		else if (name.equals(USER_BDATE_YEAR)) {
			Date birthday = _user.getBirthday();

			if (birthday != null) {
				_calendar.setTime(birthday);

				return String.valueOf(_calendar.get(Calendar.YEAR));
			}

			return null;
		}
		else if (name.equals(USER_GENDER)) {
			if (_user.isMale()) {
				return "male";
			}

			return "female";
		}
		else if (name.equals(USER_EMPLOYER)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_DEPARTMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_JOBTITLE)) {
			return _user.getJobTitle();
		}
		else if (name.equals(USER_NAME_PREFIX)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_NAME_GIVEN)) {
			return _user.getFirstName();
		}
		else if (name.equals(USER_NAME_FAMILY)) {
			return _user.getLastName();
		}
		else if (name.equals(USER_NAME_MIDDLE)) {
			return _user.getMiddleName();
		}
		else if (name.equals(USER_NAME_SUFFIX)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_NAME_NICKNAME)) {
			return _user.getScreenName();
		}
		else if (name.equals(USER_LOGIN_ID)) {
			return _user.getScreenName();
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_NAME)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_STREET)) {
			if (_personalAddress != null) {
				return _personalAddress.getStreet1();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_CITY)) {
			if (_personalAddress != null) {
				return _personalAddress.getCity();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_STATEPROV)) {
			if (_personalAddress != null) {
				Region region = _personalAddress.getRegion();

				return region.getRegionCode();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_POSTALCODE)) {
			if (_personalAddress != null) {
				return _personalAddress.getZip();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_COUNTRY)) {
			if (_personalAddress != null) {
				Country country = _personalAddress.getCountry();

				return country.getName(_user.getLocale());
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_POSTAL_ORGANIZATION)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_TELEPHONE_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_TELEPHONE_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_TELEPHONE_NUMBER)) {
			if (_personalPhone != null) {
				return _personalPhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_TELEPHONE_EXT)) {
			if (_personalPhone != null) {
				return _personalPhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_TELEPHONE_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_FAX_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_FAX_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_FAX_NUMBER)) {
			if (_personalFaxPhone != null) {
				return _personalFaxPhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_FAX_EXT)) {
			if (_personalFaxPhone != null) {
				return _personalFaxPhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_FAX_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_MOBILE_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_MOBILE_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_MOBILE_NUMBER)) {
			if (_mobilePhone != null) {
				return _mobilePhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_MOBILE_EXT)) {
			if (_mobilePhone != null) {
				return _mobilePhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_MOBILE_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_PAGER_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_PAGER_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_PAGER_NUMBER)) {
			if (_pagerPhone != null) {
				return _pagerPhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_PAGER_EXT)) {
			if (_pagerPhone != null) {
				return _pagerPhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_TELECOM_PAGER_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_HOME_INFO_ONLINE_EMAIL)) {
			return _user.getEmailAddress();
		}
		else if (name.equals(USER_HOME_INFO_ONLINE_URI)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_NAME)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_STREET)) {
			if (_businessAddress != null) {
				return _businessAddress.getStreet1();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_CITY)) {
			if (_businessAddress != null) {
				return _businessAddress.getCity();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_STATEPROV)) {
			if (_businessAddress != null) {
				Region region = _businessAddress.getRegion();

				return region.getRegionCode();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_POSTALCODE)) {
			if (_businessAddress != null) {
				return _businessAddress.getZip();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_COUNTRY)) {
			if (_businessAddress != null) {
				Country country = _businessAddress.getCountry();

				return country.getName(_user.getLocale());
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_POSTAL_ORGANIZATION)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_TELEPHONE_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_TELEPHONE_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_TELEPHONE_NUMBER)) {
			if (_businessPhone != null) {
				return _businessPhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_TELEPHONE_EXT)) {
			if (_businessPhone != null) {
				return _businessPhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_TELEPHONE_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_FAX_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_FAX_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_FAX_NUMBER)) {
			if (_businessFaxPhone != null) {
				return _businessFaxPhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_FAX_EXT)) {
			if (_businessFaxPhone != null) {
				return _businessFaxPhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_FAX_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_MOBILE_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_MOBILE_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_MOBILE_NUMBER)) {
			if (_mobilePhone != null) {
				return _mobilePhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_MOBILE_EXT)) {
			if (_mobilePhone != null) {
				return _mobilePhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_MOBILE_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_PAGER_INTCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_PAGER_LOCCODE)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_PAGER_NUMBER)) {
			if (_pagerPhone != null) {
				return _pagerPhone.getNumber();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_PAGER_EXT)) {
			if (_pagerPhone != null) {
				return _pagerPhone.getExtension();
			}

			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_TELECOM_PAGER_COMMENT)) {
			return StringPool.BLANK;
		}
		else if (name.equals(USER_BUSINESS_INFO_ONLINE_EMAIL)) {
			return _user.getEmailAddress();
		}
		else if (name.equals(USER_BUSINESS_INFO_ONLINE_URI)) {
			return StringPool.BLANK;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(UserAttributes.class);

	private final Address _businessAddress;
	private final Phone _businessFaxPhone;
	private final Phone _businessPhone;
	private final Calendar _calendar = new GregorianCalendar();
	private final Phone _mobilePhone;
	private final Phone _pagerPhone;
	private final Address _personalAddress;
	private final Phone _personalFaxPhone;
	private final Phone _personalPhone;
	private final User _user;

}