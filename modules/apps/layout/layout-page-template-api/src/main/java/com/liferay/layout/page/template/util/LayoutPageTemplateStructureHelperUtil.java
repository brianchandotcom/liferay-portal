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
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
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

		return generateContentLayoutStructure(
			fragmentEntryLinks,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
	}

	public static JSONObject generateContentLayoutStructure(
		List<FragmentEntryLink> fragmentEntryLinks, int type) {

		JSONArray structureJSONArray = JSONFactoryUtil.createJSONArray();

		if (fragmentEntryLinks.isEmpty() &&
			(type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			structureJSONArray.put(_getColumnJSONObject(0, "drop-zone", 0, 1));
		}
		else {
			for (int i = 0; i < fragmentEntryLinks.size(); i++) {
				FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

				structureJSONArray.put(
					_getColumnJSONObject(
						i,
						String.valueOf(
							fragmentEntryLink.getFragmentEntryLinkId()),
						i, _getRowType(fragmentEntryLink)));
			}
		}

		JSONObject jsonObject = JSONUtil.put(
			"config", JSONFactoryUtil.createJSONObject()
		).put(
			"nextColumnId", fragmentEntryLinks.size()
		).put(
			"nextRowId", fragmentEntryLinks.size()
		);

		if (!fragmentEntryLinks.isEmpty()) {
			jsonObject.put("nextRowId", fragmentEntryLinks.size() - 1);
		}

		if (fragmentEntryLinks.isEmpty() &&
			(type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			jsonObject.put(
				"nextColumnId", 1
			).put(
				"nextRowId", 1
			);
		}

		jsonObject.put("structure", structureJSONArray);

		return jsonObject;
	}

	private static JSONObject _getColumnJSONObject(
		int columnId, String fragmentEntryLinkId, int rowId, int type) {

		return JSONUtil.put(
			"columns",
			JSONUtil.put(
				JSONUtil.put(
					"columnId", String.valueOf(columnId)
				).put(
					"fragmentEntryLinkIds",
					JSONUtil.put(String.valueOf(fragmentEntryLinkId))
				).put(
					"size", StringPool.BLANK
				))
		).put(
			"config", JSONFactoryUtil.createJSONObject()
		).put(
			"rowId", String.valueOf(rowId)
		).put(
			"type", String.valueOf(type)
		);
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