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
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.List;

/**
 * @author Jürgen
 */
public class LayoutPageTemplateStructureHelperUtil {

	public static JSONObject generateContentLayoutStructure(
		List<FragmentEntryLink> fragmentEntryLinks) {

		JSONArray structureJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < fragmentEntryLinks.size(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

			JSONObject columnJSONObject = JSONUtil.put(
				"columnId", String.valueOf(i)
			).put(
				"fragmentEntryLinkIds",
				JSONUtil.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()))
			).put(
				"size", StringPool.BLANK
			);

			JSONObject structureJSONObject = JSONUtil.put(
				"columns", JSONUtil.put(columnJSONObject)
			).put(
				"rowId", String.valueOf(i)
			).put(
				"type", String.valueOf(_getRowType(fragmentEntryLink))
			);

			structureJSONArray.put(structureJSONObject);
		}

		JSONObject jsonObject = JSONUtil.put(
			"config", JSONFactoryUtil.createJSONObject()
		).put(
			"nextColumnId", fragmentEntryLinks.size()
		).put(
			"nextRowId", fragmentEntryLinks.size()
		);

		if (!fragmentEntryLinks.isEmpty()) {
			jsonObject.put(
				"nextRowId", String.valueOf(fragmentEntryLinks.size() - 1));
		}

		jsonObject.put("structure", structureJSONArray);

		return jsonObject;
	}

	private static int _getRowType(FragmentEntryLink fragmentEntryLink) {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if ((fragmentEntry != null) &&
			(fragmentEntry.getType() == FragmentConstants.TYPE_COMPONENT)) {

			return FragmentConstants.TYPE_COMPONENT;
		}

		return FragmentConstants.TYPE_SECTION;
	}

}