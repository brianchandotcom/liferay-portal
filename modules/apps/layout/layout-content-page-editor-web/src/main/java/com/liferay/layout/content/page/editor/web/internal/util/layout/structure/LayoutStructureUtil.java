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

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static JSONObject updateLayoutPageTemplateData(
			long groupId, long segmentsExperienceId, long classNameId,
			long classPK, long plid,
			UnsafeConsumer<LayoutStructure, PortalException> unsafeConsumer)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					plid, true);

		LayoutStructure layoutStructure = _parse(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		unsafeConsumer.accept(layoutStructure);

		JSONObject dataJSONObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, segmentsExperienceId,
				dataJSONObject.toString());

		return dataJSONObject;
	}

	private static LayoutStructure _parse(String layoutStructure)
		throws JSONException {

		JSONObject layoutStructureJSONObject = JSONFactoryUtil.createJSONObject(
			layoutStructure);

		JSONObject rootItemsJSONObject =
			layoutStructureJSONObject.getJSONObject("rootItems");

		JSONObject itemsJSONObject = layoutStructureJSONObject.getJSONObject(
			"items");

		Map<String, LayoutStructureItem> layoutStructureItems = new HashMap<>(
			itemsJSONObject.length());

		for (String key : itemsJSONObject.keySet()) {
			layoutStructureItems.put(
				key, _toItem(itemsJSONObject.getJSONObject(key)));
		}

		return new LayoutStructure(
			layoutStructureItems, rootItemsJSONObject.getString("main"));
	}

	private static LayoutStructureItem _toItem(JSONObject jsonObject) {
		JSONObject configJSONObject = jsonObject.getJSONObject("config");
		String itemId = jsonObject.getString("itemId");
		String parentId = jsonObject.getString("parentId");
		String type = jsonObject.getString("type");

		List<String> childrenItemIds = new ArrayList<>();

		JSONUtil.addToStringCollection(
			childrenItemIds, jsonObject.getJSONArray("children"));

		return new LayoutStructureItem(
			childrenItemIds, configJSONObject, itemId, parentId, type);
	}

}