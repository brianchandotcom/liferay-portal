/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Alicia García
 */
public class DDMStructureUtil {

	public static List<DDMStructure> getHighlightedDDMStructures(
		ThemeDisplay themeDisplay) {

		List<DDMStructure> highlightedDDMStructuresList = new ArrayList<>();

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, 0,
				JournalConstants.SERVICE_NAME, null);

		String highlightedDDMStructures = portletPreferences.getValue(
			"highlightedDDMStructures", null);

		if (Validator.isNull(highlightedDDMStructures)) {
			return highlightedDDMStructuresList;
		}

		List<String> highlightedDDMStructureIds = StringUtil.split(
			highlightedDDMStructures, CharPool.COMMA);

		for (String highlightedDDMStructureId : highlightedDDMStructureIds) {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchDDMStructure(
					GetterUtil.getLong(highlightedDDMStructureId));

			if (ddmStructure != null) {
				highlightedDDMStructuresList.add(ddmStructure);
			}
		}

		return highlightedDDMStructuresList;
	}

}