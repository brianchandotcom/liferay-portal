/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.cmp.site.initializer.internal.util.ObjectEntryValuesUtil;

import java.util.Map;

/**
 * @author Igor Franca
 */
public class ViewAssigneeSectionDisplayContext {

	public ViewAssigneeSectionDisplayContext(
		Language language, ObjectEntry objectEntry, ThemeDisplay themeDisplay) {

		_language = language;
		_objectEntry = objectEntry;
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"label", _language.get(_themeDisplay.getLocale(), "assignee")
		).put(
			"name", "ObjectField_assignTo"
		).put(
			"searchURL",
			_themeDisplay.getPortalURL() +
				"/o/headless-cmp/v1.0/task-assignees/"
		).put(
			"triggerClassName", "form-control"
		).put(
			"value",
			ObjectEntryValuesUtil.getAssigneeFieldValue(
				_objectEntry, _themeDisplay)
		).put(
			"visible", true
		).build();
	}

	private final Language _language;
	private final ObjectEntry _objectEntry;
	private final ThemeDisplay _themeDisplay;

}