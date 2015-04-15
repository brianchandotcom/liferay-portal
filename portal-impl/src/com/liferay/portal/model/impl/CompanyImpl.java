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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.CacheField;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.User;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.security.Key;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class CompanyImpl extends CompanyBaseImpl {

	@Override
	public int compareTo(Company company) {
		String webId1 = getWebId();
		String webId2 = company.getWebId();

		if (webId1.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return -1;
		}
		else if (webId2.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return 1;
		}
		else {
			return webId1.compareTo(webId2);
		}
	}

	@Override
	public Account getAccount() throws PortalException {
		return AccountLocalServiceUtil.getAccount(
			getCompanyId(), getAccountId());
	}

	@Override
	public String getAdminName() {
		return "Administrator";
	}

	@Override
	public String getAuthType() {
		return PrefsPropsUtil.getString(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
			PropsValues.COMPANY_SECURITY_AUTH_TYPE);
	}

	@Override
	public User getDefaultUser() throws PortalException {
		return UserLocalServiceUtil.getDefaultUser(getCompanyId());
	}

	@Override
	public String getDefaultWebId() {
		return PropsValues.COMPANY_DEFAULT_WEB_ID;
	}

	@Override
	public String getEmailAddress() {

		// Primary email address

		return "admin@" + getMx();
	}

	@Override
	public Group getGroup() throws PortalException {
		if (getCompanyId() > CompanyConstants.SYSTEM) {
			return GroupLocalServiceUtil.getCompanyGroup(getCompanyId());
		}

		return new GroupImpl();
	}

	@Override
	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public Key getKeyObj() {
		if (_keyObj == null) {
			String key = getKey();

			if (Validator.isNotNull(key)) {
				_keyObj = (Key)Base64.stringToObjectSilent(key);
			}
		}

		return _keyObj;
	}

	@Override
	public Locale getLocale() throws PortalException {
		return getDefaultUser().getLocale();
	}

	@AutoEscape
	@Override
	public String getName() throws PortalException {
		return getAccount().getName();
	}

	@Override
	public String getPortalURL(long groupId) throws PortalException {
		String portalURL = PortalUtil.getPortalURL(
			getVirtualHostname(), Http.HTTP_PORT, false);

		if (groupId <= 0) {
			return portalURL;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.hasLayouts()) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId);

			if (Validator.isNotNull(layoutSet.getVirtualHostname())) {
				portalURL = PortalUtil.getPortalURL(
					layoutSet.getVirtualHostname(), Http.HTTP_PORT, false);
			}
		}

		return portalURL;
	}

	@Override
	public String getShardName() throws PortalException {
		Shard shard = ShardLocalServiceUtil.getShard(
			Company.class.getName(), getCompanyId());

		return shard.getName();
	}

	@Override
	public String getShortName() throws PortalException {
		return getName();
	}

	@Override
	public TimeZone getTimeZone() throws PortalException {
		return getDefaultUser().getTimeZone();
	}

	@Override
	public String getVirtualHostname() {
		if (_virtualHostname != null) {
			return _virtualHostname;
		}

		VirtualHost virtualHost = null;

		try {
			virtualHost = VirtualHostLocalServiceUtil.fetchVirtualHost(
				getCompanyId(), 0);
		}
		catch (Exception e) {
		}

		if (virtualHost == null) {
			return StringPool.BLANK;
		}

		_virtualHostname = virtualHost.getHostname();

		return _virtualHostname;
	}

	@Override
	public boolean hasCompanyMx(String emailAddress) {
		emailAddress = StringUtil.toLowerCase(emailAddress.trim());

		int pos = emailAddress.indexOf(CharPool.AT);

		if (pos == -1) {
			return false;
		}

		String mx = emailAddress.substring(pos + 1);

		if (mx.equals(getMx())) {
			return true;
		}

		String[] mailHostNames = PrefsPropsUtil.getStringArray(
			getCompanyId(), PropsKeys.ADMIN_MAIL_HOST_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_MAIL_HOST_NAMES);

		for (int i = 0; i < mailHostNames.length; i++) {
			if (StringUtil.equalsIgnoreCase(mx, mailHostNames[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isAutoLogin() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
			PropsValues.COMPANY_SECURITY_AUTO_LOGIN);
	}

	@Override
	public boolean isSendPassword() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_SEND_PASSWORD,
			PropsValues.COMPANY_SECURITY_SEND_PASSWORD);
	}

	@Override
	public boolean isSendPasswordResetLink() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
			PropsValues.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK);
	}

	@Override
	public boolean isSiteLogo() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_SITE_LOGO,
			PropsValues.COMPANY_SECURITY_SITE_LOGO);
	}

	@Override
	public boolean isStrangers() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS,
			PropsValues.COMPANY_SECURITY_STRANGERS);
	}

	@Override
	public boolean isStrangersVerify() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY,
			PropsValues.COMPANY_SECURITY_STRANGERS_VERIFY);
	}

	@Override
	public boolean isStrangersWithMx() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
			PropsValues.COMPANY_SECURITY_STRANGERS_WITH_MX);
	}

	@Override
	public void setKey(String key) {
		_keyObj = null;

		super.setKey(key);
	}

	@Override
	public void setKeyObj(Key keyObj) {
		_keyObj = keyObj;
	}

	@Override
	public void setVirtualHostname(String virtualHostname) {
		_virtualHostname = virtualHostname;
	}

	@CacheField
	private Key _keyObj;

	@CacheField
	private String _virtualHostname;

}