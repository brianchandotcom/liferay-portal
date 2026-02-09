/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Assignee;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Igor Franca
 */
public class ViewAssigneeSectionDisplayContext {

	public ViewAssigneeSectionDisplayContext(
		ObjectFieldBusinessType assigneeObjectFieldBusinessType,
		Language language, ObjectEntry objectEntry, ThemeDisplay themeDisplay) {

		_assigneeObjectFieldBusinessType = assigneeObjectFieldBusinessType;
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
			() -> {
				Map<String, Serializable> values = _objectEntry.getValues();

				Assignee assignee =
					(Assignee)_assigneeObjectFieldBusinessType.getDTOValue(
						null, null, null, null, values.get("assignTo"));

				if (assignee == null) {
					return null;
				}

				return JSONFactoryUtil.createJSONObject(assignee.toString());
			}
		).put(
			"visible", true
		).build();
	}

	private final ObjectFieldBusinessType _assigneeObjectFieldBusinessType;
	private final Language _language;
	private final ObjectEntry _objectEntry;
	private final ThemeDisplay _themeDisplay;

}