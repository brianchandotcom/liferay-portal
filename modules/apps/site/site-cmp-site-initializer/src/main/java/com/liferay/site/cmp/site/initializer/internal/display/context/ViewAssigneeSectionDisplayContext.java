/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Assignee;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Igor Franca
 */
public class ViewAssigneeSectionDisplayContext
	extends BaseAssigneeSectionDisplayContext {

	public ViewAssigneeSectionDisplayContext(
		ObjectFieldBusinessType assigneeObjectFieldBusinessType,
		Language language, ObjectEntry objectEntry, ThemeDisplay themeDisplay) {

		super(language, objectEntry, themeDisplay);

		_assigneeObjectFieldBusinessType = assigneeObjectFieldBusinessType;
	}

	@Override
	public String getAdditionalAPIURLParameters() {
		return StringPool.BLANK;
	}

	@Override
	public String getLabelKey() {
		return "assignee";
	}

	@Override
	public String getName() {
		return "ObjectField_assignTo";
	}

	@Override
	public boolean getUsersOnly() {
		return false;
	}

	@Override
	public JSONObject getValueJSONObject() throws Exception {
		Map<String, Serializable> values = objectEntry.getValues();

		Assignee assignee =
			(Assignee)_assigneeObjectFieldBusinessType.getDTOValue(
				null, null, null, null, values.get("assignTo"));

		if (assignee == null) {
			return null;
		}

		return JSONFactoryUtil.createJSONObject(assignee.toString());
	}

	private final ObjectFieldBusinessType _assigneeObjectFieldBusinessType;

}