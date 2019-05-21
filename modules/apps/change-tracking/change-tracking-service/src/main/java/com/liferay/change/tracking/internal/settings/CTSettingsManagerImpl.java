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

package com.liferay.change.tracking.internal.settings;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.settings.CTSettingsManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = CTSettingsManager.class)
public class CTSettingsManagerImpl implements CTSettingsManager {

	public String getGlobalCTSetting(long companyId, String key) {
		return getGlobalCTSetting(companyId, key, null);
	}

	public String getGlobalCTSetting(
		long companyId, String key, String defaultValue) {

		String value = PrefsPropsUtil.getString(companyId, key);

		if (Validator.isNull(value)) {
			return defaultValue;
		}

		return value;
	}

	public String getUserCTSetting(long userId, String key) {
		return getUserCTSetting(userId, key, null);
	}

	public String getUserCTSetting(
		long userId, String key, String defaultValue) {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user " + userId);
			}

			return defaultValue;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		String value = portalPreferences.getValue(
			CTPortletKeys.CHANGE_LISTS, key);

		if (Validator.isNull(value)) {
			value = getGlobalCTSetting(user.getCompanyId(), key);
		}

		if (Validator.isNull(value)) {
			return defaultValue;
		}

		return value;
	}

	public void setGlobalCTSetting(long companyId, String key, String value) {
		if (Validator.isNull(value)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Value is null");
			}

			return;
		}

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			portletPreferences.setValue(key, value);

			portletPreferences.store();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to store global setting", e);
			}
		}
	}

	public void setUserCTSetting(long userId, String key, String value) {
		if (Validator.isNull(value)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Value is null");
			}

			return;
		}

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user " + userId);
			}

			return;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		portalPreferences.setValue(CTPortletKeys.CHANGE_LISTS, key, value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSettingsManagerImpl.class);

	@Reference
	private UserLocalService _userLocalService;

}