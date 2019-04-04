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

package com.liferay.layout.page.template.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author Jürgen
 */
public class LayoutPageTemplateStructureHelperUtil {

	public static JSONObject generateContentLayoutStructure(
		List<FragmentEntryLink> fragmentEntryLinks) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray structureJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < fragmentEntryLinks.size(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

			JSONObject structureJSONObject = JSONFactoryUtil.createJSONObject();

			JSONArray columnJSONArray = JSONFactoryUtil.createJSONArray();

			JSONObject columnJSONObject = JSONFactoryUtil.createJSONObject();

			columnJSONObject.put("columnId", String.valueOf(i));

			JSONArray fragmentEntryLinksJSONArray =
				JSONFactoryUtil.createJSONArray();

			fragmentEntryLinksJSONArray.put(
				fragmentEntryLink.getFragmentEntryLinkId());

			columnJSONObject.put(
				"fragmentEntryLinkIds", fragmentEntryLinksJSONArray);

			columnJSONObject.put("size", StringPool.BLANK);

			columnJSONArray.put(columnJSONObject);

			structureJSONObject.put("columns", columnJSONArray);

			structureJSONObject.put("rowId", String.valueOf(i));
			structureJSONObject.put("type", _getRowType(fragmentEntryLink));

			structureJSONArray.put(structureJSONObject);
		}

		jsonObject.put("config", JSONFactoryUtil.createJSONObject());
		jsonObject.put("nextColumnId", fragmentEntryLinks.size());
		jsonObject.put("nextRowId", fragmentEntryLinks.size());

		if (!fragmentEntryLinks.isEmpty()) {
			jsonObject.put(
				"nextRowId", String.valueOf(fragmentEntryLinks.size() - 1));
		}

		jsonObject.put("structure", structureJSONArray);

		return jsonObject;
	}

	private static String _getRowType(FragmentEntryLink fragmentEntryLink) {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if ((fragmentEntry != null) &&
			(fragmentEntry.getType() == FragmentConstants.TYPE_COMPONENT)) {

			return "fragments-editor-component-row";
		}

		return "fragments-editor-section-row";
	}

}