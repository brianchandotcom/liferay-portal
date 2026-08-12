/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class SiteExportImportParameterUtil {

	public static String getCurrentSiteExternalReferenceCode(
		Map<String, String[]> parameterMap) {

		if (parameterMap == null) {
			return null;
		}

		String siteExternalReferenceCode = MapUtil.getString(
			parameterMap, _CURRENT_SITE_EXTERNAL_REFERENCE_CODE);

		if (Validator.isNull(siteExternalReferenceCode)) {
			return null;
		}

		return siteExternalReferenceCode;
	}

	public static String getCurrentSiteExternalReferenceCode(
		PortletDataContext portletDataContext) {

		return getCurrentSiteExternalReferenceCode(
			portletDataContext.getParameterMap());
	}

	public static String[] getSelectedSiteExternalReferenceCodes(
		Map<String, String[]> parameterMap) {

		if (parameterMap == null) {
			return new String[0];
		}

		String[] siteExternalReferenceCodes = parameterMap.get(
			PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES);

		if (siteExternalReferenceCodes == null) {
			return new String[0];
		}

		Set<String> uniqueSiteExternalReferenceCodes = new LinkedHashSet<>();

		for (String siteExternalReferenceCode : siteExternalReferenceCodes) {
			if (Validator.isNotNull(siteExternalReferenceCode)) {
				uniqueSiteExternalReferenceCodes.add(
					siteExternalReferenceCode.trim());
			}
		}

		return uniqueSiteExternalReferenceCodes.toArray(new String[0]);
	}

	public static String[] getSelectedSiteExternalReferenceCodes(
		PortletDataContext portletDataContext) {

		return getSelectedSiteExternalReferenceCodes(
			portletDataContext.getParameterMap());
	}

	public static boolean isSiteExportImportEnabled(long companyId) {
		return FeatureFlagManagerUtil.isEnabled(companyId, "LPD-85946");
	}

	public static boolean isSiteScoped(Map<String, String[]> parameterMap) {
		if (getCurrentSiteExternalReferenceCode(parameterMap) != null) {
			return true;
		}

		return false;
	}

	public static boolean isSiteScoped(PortletDataContext portletDataContext) {
		return isSiteScoped(portletDataContext.getParameterMap());
	}

	public static Map<String, String[]> toSiteExportParameterMap(
		Map<String, String[]> parameterMap, String siteExternalReferenceCode) {

		return _toSiteParameterMap(parameterMap, siteExternalReferenceCode);
	}

	public static Map<String, String[]> toSiteImportParameterMap(
		Map<String, String[]> parameterMap, String siteExternalReferenceCode) {

		return HashMapBuilder.putAll(
			_toSiteParameterMap(parameterMap, siteExternalReferenceCode)
		).put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID
			}
		).build();
	}

	private static Map<String, String[]> _toSiteParameterMap(
		Map<String, String[]> parameterMap, String siteExternalReferenceCode) {

		Map<String, String[]> siteParameterMap = HashMapBuilder.putAll(
			parameterMap
		).put(
			_CURRENT_SITE_EXTERNAL_REFERENCE_CODE,
			new String[] {siteExternalReferenceCode}
		).put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PRIVATE_LAYOUT,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LOGO, new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()}
		).build();

		siteParameterMap.remove(
			PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES);

		return siteParameterMap;
	}

	private static final String _CURRENT_SITE_EXTERNAL_REFERENCE_CODE =
		"CURRENT_SITE_EXTERNAL_REFERENCE_CODE";

}