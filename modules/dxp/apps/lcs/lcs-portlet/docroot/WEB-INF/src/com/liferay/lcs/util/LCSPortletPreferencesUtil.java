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

package com.liferay.lcs.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Mladen Cikara
 */
public class LCSPortletPreferencesUtil {

	public static synchronized void deletePortletPreferences() {
		try {
			PortletPreferencesLocalServiceUtil.deletePortletPreferences(
				CompanyConstants.SYSTEM, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				0, PortletKeys.MONITORING);

			if (_log.isInfoEnabled()) {
				_log.info("Deleted portlet preferences");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static synchronized javax.portlet.PortletPreferences
		fetchReadOnlyJxPortletPreferences() {

		return fetchReadOnlyJxPortletPreferences(null);
	}

	public static synchronized javax.portlet.PortletPreferences
		fetchReadOnlyJxPortletPreferences(PortletRequest portletRequest) {

		PortletPreferences portletPreferences = _fetchPortletPreferences(
			portletRequest);

		try {
			return new ImmutablePortletPreferences(
				PortletPreferencesFactoryUtil.fromXML(
					CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
					PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
					PortletKeys.MONITORING,
					portletPreferences.getPreferences()));
		}
		catch (SystemException se) {
			_log.error(se, se);

			if (portletRequest != null) {
				SessionErrors.add(portletRequest, "oAuthSettingsAccess");
			}
		}

		return null;
	}

	public static synchronized int getCredentialsStatus() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchReadOnlyJxPortletPreferences();

		if (jxPortletPreferences == null) {
			return _CREDENTIALS_STATUS_MISSING;
		}

		String lcsAccessToken = jxPortletPreferences.getValue(
			"lcsAccessToken", null);
		String lcsAccessSecret = jxPortletPreferences.getValue(
			"lcsAccessSecret", null);

		if (Validator.isNull(lcsAccessToken) ||
			Validator.isNull(lcsAccessSecret)) {

			return _CREDENTIALS_STATUS_INVALID;
		}

		return _CREDENTIALS_STATUS_SET;
	}

	public static synchronized Map<String, Boolean>
		getLCSServicesPreferences() {

		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchReadOnlyJxPortletPreferences();

		Boolean metricsServiceEnabled = Boolean.valueOf(
			jxPortletPreferences.getValue(
				LCSConstants.METRICS_LCS_SERVICE_ENABLED,
				Boolean.FALSE.toString()));

		Boolean patchesServiceEnabled = Boolean.valueOf(
			jxPortletPreferences.getValue(
				LCSConstants.PATCHES_LCS_SERVICE_ENABLED,
				Boolean.FALSE.toString()));

		Boolean portalPropertiesServiceEnabled = Boolean.valueOf(
			jxPortletPreferences.getValue(
				LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
				Boolean.FALSE.toString()));

		Boolean enableAllLCSServices =
			metricsServiceEnabled && portalPropertiesServiceEnabled &&
			patchesServiceEnabled;

		Map<String, Boolean> preferences = new HashMap<>();

		preferences.put(
			LCSConstants.METRICS_LCS_SERVICE_ENABLED, metricsServiceEnabled);

		preferences.put(
			LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
			portalPropertiesServiceEnabled);

		preferences.put(
			LCSConstants.PATCHES_LCS_SERVICE_ENABLED, patchesServiceEnabled);

		preferences.put("enableAllLCSServices", enableAllLCSServices);

		return preferences;
	}

	public static String getValue(String key, String defaultValue) {
		PortletPreferences portletPreferences = _fetchPortletPreferences(null);

		if (portletPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to read portlet preferences");
			}

			return defaultValue;
		}

		try {
			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.fromXML(
					CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
					PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
					PortletKeys.MONITORING,
					portletPreferences.getPreferences());

			return jxPortletPreferences.getValue(key, defaultValue);
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}
	}

	public static boolean isCredentialsSet() {
		if (getCredentialsStatus() == _CREDENTIALS_STATUS_SET) {
			return true;
		}

		return false;
	}

	public static void removeCredentials() {
		try {
			reset("lcsAccessToken");
			reset("lcsAccessSecret");
			reset("lcsClusterEntryId");
			reset("lcsClusterEntryTokenId");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static synchronized void reset(String key) throws Exception {
		PortletPreferences portletPreferences = _fetchPortletPreferences(null);

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0, PortletKeys.MONITORING,
				portletPreferences.getPreferences());

		jxPortletPreferences.reset(key);

		portletPreferences.setPreferences(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		PortletPreferencesLocalServiceUtil.updatePortletPreferences(
			portletPreferences);

		PortletPreferencesPersistence portletPreferencesPersistence =
			_getPersistence();

		portletPreferencesPersistence.clearCache(portletPreferences);
	}

	public static synchronized void store(String key, String value)
		throws Exception {

		PortletPreferences portletPreferences = _fetchPortletPreferences(null);

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0, PortletKeys.MONITORING,
				portletPreferences.getPreferences());

		jxPortletPreferences.setValue(key, value);

		portletPreferences.setPreferences(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		PortletPreferencesLocalServiceUtil.updatePortletPreferences(
			portletPreferences);

		PortletPreferencesPersistence portletPreferencesPersistence =
			_getPersistence();

		portletPreferencesPersistence.clearCache(portletPreferences);
	}

	private static PortletPreferences _fetchPortletPreferences(
		PortletRequest portletRequest) {

		PortletPreferences portletPreferences = null;

		try {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
				PortletPreferences.class,
				PortalClassLoaderUtil.getClassLoader());

			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("ownerId", CompanyConstants.SYSTEM));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"ownerType", PortletKeys.PREFS_OWNER_TYPE_COMPANY));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("plid", Long.valueOf(0)));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"portletId", PortletKeys.MONITORING));

			List<PortletPreferences> portletPreferencesList =
				PortletPreferencesLocalServiceUtil.dynamicQuery(dynamicQuery);

			if (!portletPreferencesList.isEmpty()) {
				if (portletPreferencesList.size() == 1) {
					portletPreferences = portletPreferencesList.get(0);
				}
				else {
					_log.error(
						"Unable to determine unique portlet preferences");

					if (portletRequest != null) {
						SessionErrors.add(
							portletRequest, "oAuthSettingsAccess");
					}

					return null;
				}
			}

			if (portletPreferences == null) {
				portletPreferences =
					PortletPreferencesLocalServiceUtil.addPortletPreferences(
						CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
						PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
						PortletKeys.MONITORING, null, null);
			}
		}
		catch (SystemException se) {
			_log.error("Unable to get portlet preferences", se);

			if (portletRequest != null) {
				SessionErrors.add(portletRequest, "oAuthSettingsAccess");
			}

			return null;
		}

		return portletPreferences;
	}

	private static PortletPreferencesPersistence _getPersistence() {
		return (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(
			PortletPreferencesPersistence.class.getName());
	}

	private static final int _CREDENTIALS_STATUS_INVALID = 2;

	private static final int _CREDENTIALS_STATUS_MISSING = 1;

	private static final int _CREDENTIALS_STATUS_SET = 3;

	private static final Log _log = LogFactoryUtil.getLog(
		LCSPortletPreferencesUtil.class);

}