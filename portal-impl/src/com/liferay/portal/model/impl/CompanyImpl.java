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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.model.cache.CacheField;
import com.liferay.portal.kernel.service.CompanyInfoLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.security.Key;

import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class CompanyImpl extends CompanyBaseImpl {

	@Override
	public int compareTo(Company company) {
		String webId1 = getWebId();

		if (webId1.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return -1;
		}

		String webId2 = company.getWebId();

		if (webId2.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return 1;
		}

		return webId1.compareTo(webId2);
	}

	@Override
	public String getAdminName() {
		return "Administrator";
	}

	@Override
	public String getAuthType() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._authType;
	}

	@Override
	public CompanyInfo getCompanyInfo() {
		if (_companyInfo == null) {
			CompanyInfo companyInfo = CompanyInfoLocalServiceUtil.fetchCompany(
				getCompanyId());

			if (companyInfo == null) {
				companyInfo = CompanyInfoLocalServiceUtil.createCompanyInfo(
					CounterLocalServiceUtil.increment());

				companyInfo.setCompanyId(getCompanyId());
			}

			_companyInfo = companyInfo;
		}

		return _companyInfo;
	}

	@Override
	public CompanySecurityBag getCompanySecurityBag() {
		if (_companySecurityBag == null) {
			_companySecurityBag = new CompanySecurityBag(this);
		}

		return _companySecurityBag;
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
			ThreadLocalCache<Group> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.REQUEST, Company.class.getName());

			String cacheKey = StringUtil.toHexString(getCompanyId());

			Group companyGroup = threadLocalCache.get(cacheKey);

			if (companyGroup == null) {
				companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					getCompanyId());

				threadLocalCache.put(cacheKey, companyGroup);
			}

			return companyGroup;
		}

		return new GroupImpl();
	}

	@Override
	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public String getKey() {
		CompanyInfo companyInfo = getCompanyInfo();

		return companyInfo.getKey();
	}

	@Override
	public Key getKeyObj() {
		if (_keyObj == null) {
			String key = getKey();

			if (Validator.isNotNull(key)) {
				_keyObj = Encryptor.deserializeKey(key);
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
	public String getName() {
		return super.getName();
	}

	@Override
	public String getPortalURL(long groupId) throws PortalException {
		int portalServerPort = PortalUtil.getPortalServerPort(false);

		String portalURL = PortalUtil.getPortalURL(
			getVirtualHostname(), portalServerPort, false);

		if (groupId <= 0) {
			return portalURL;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.hasPublicLayouts()) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, false);

			TreeMap<String, String> virtualHostnames =
				layoutSet.getVirtualHostnames();

			if (!virtualHostnames.isEmpty()) {
				portalURL = PortalUtil.getPortalURL(
					virtualHostnames.firstKey(), portalServerPort, false);
			}
		}
		else if (group.hasPrivateLayouts()) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, true);

			TreeMap<String, String> virtualHostnames =
				layoutSet.getVirtualHostnames();

			if (!virtualHostnames.isEmpty()) {
				portalURL = PortalUtil.getPortalURL(
					virtualHostnames.firstKey(), portalServerPort, false);
			}
		}

		return portalURL;
	}

	@Override
	public String getPortalURL(long groupId, boolean privateLayout)
		throws PortalException {

		int portalServerPort = PortalUtil.getPortalServerPort(false);

		String portalURL = PortalUtil.getPortalURL(
			getVirtualHostname(), portalServerPort, false);

		if (groupId <= 0) {
			return portalURL;
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (!virtualHostnames.isEmpty()) {
			portalURL = PortalUtil.getPortalURL(
				virtualHostnames.firstKey(), portalServerPort, false);
		}

		return portalURL;
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
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

		for (String mailHostName : mailHostNames) {
			if (StringUtil.equalsIgnoreCase(mx, mailHostName)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isAutoLogin() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._autoLogin;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isSendPassword() {
		return false;
	}

	@Override
	public boolean isSendPasswordResetLink() {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
			PropsValues.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK);
	}

	@Override
	public boolean isSiteLogo() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._siteLogo;
	}

	@Override
	public boolean isStrangers() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._strangers;
	}

	@Override
	public boolean isStrangersVerify() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._strangersVerify;
	}

	@Override
	public boolean isStrangersWithMx() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._strangersWithMx;
	}

	@Override
	public boolean isUpdatePasswordRequired() {
		CompanySecurityBag companySecurityBag = getCompanySecurityBag();

		return companySecurityBag._updatePasswordRequired;
	}

	public void setCompanySecurityBag(Object companySecurityBag) {
		_companySecurityBag = (CompanySecurityBag)companySecurityBag;
	}

	@Override
	public void setKey(String key) {
		CompanyInfo companyInfo = getCompanyInfo();

		companyInfo.setKey(key);

		_keyObj = null;
	}

	@Override
	public void setKeyObj(Key keyObj) {
		_keyObj = keyObj;
	}

	@Override
	public void setVirtualHostname(String virtualHostname) {
		_virtualHostname = virtualHostname;
	}

	public static class CompanySecurityBag implements Serializable {

		private CompanySecurityBag(Company company) {
			PortletPreferences preferences = PrefsPropsUtil.getPreferences(
				company.getCompanyId(), true);

			_authType = _getPrefsPropsString(
				preferences, company, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				PropsValues.COMPANY_SECURITY_AUTH_TYPE);
			_autoLogin = _getPrefsPropsBoolean(
				preferences, company, PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
				PropsValues.COMPANY_SECURITY_AUTO_LOGIN);
			_siteLogo = _getPrefsPropsBoolean(
				preferences, company, PropsKeys.COMPANY_SECURITY_SITE_LOGO,
				PropsValues.COMPANY_SECURITY_SITE_LOGO);
			_strangers = _getPrefsPropsBoolean(
				preferences, company, PropsKeys.COMPANY_SECURITY_STRANGERS,
				PropsValues.COMPANY_SECURITY_STRANGERS);
			_strangersVerify = _getPrefsPropsBoolean(
				preferences, company,
				PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY,
				PropsValues.COMPANY_SECURITY_STRANGERS_VERIFY);
			_strangersWithMx = _getPrefsPropsBoolean(
				preferences, company,
				PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
				PropsValues.COMPANY_SECURITY_STRANGERS_WITH_MX);
			_updatePasswordRequired = _getPrefsPropsBoolean(
				preferences, company,
				PropsKeys.COMPANY_SECURITY_UPDATE_PASSWORD_REQUIRED,
				PropsValues.COMPANY_SECURITY_UPDATE_PASSWORD_REQUIRED);
		}

		private final String _authType;
		private final boolean _autoLogin;
		private final boolean _siteLogo;
		private final boolean _strangers;
		private final boolean _strangersVerify;
		private final boolean _strangersWithMx;
		private final boolean _updatePasswordRequired;

	}

	private static boolean _getPrefsPropsBoolean(
		PortletPreferences portletPreferences, Company company, String name,
		boolean defaultValue) {

		String value = portletPreferences.getValue(
			name, PropsUtil.get(company, name));

		if (value != null) {
			return GetterUtil.getBoolean(value);
		}

		return defaultValue;
	}

	private static String _getPrefsPropsString(
		PortletPreferences portletPreferences, Company company, String name,
		String defaultValue) {

		String value = portletPreferences.getValue(
			name, PropsUtil.get(company, name));

		if (value != null) {
			return value;
		}

		return defaultValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(CompanyImpl.class);

	private CompanyInfo _companyInfo;

	@CacheField
	private CompanySecurityBag _companySecurityBag;

	private Key _keyObj;

	@CacheField(propagateToInterface = true)
	private String _virtualHostname;

}