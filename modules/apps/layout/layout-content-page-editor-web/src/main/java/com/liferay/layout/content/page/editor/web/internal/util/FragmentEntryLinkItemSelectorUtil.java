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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryLinkItemSelectorUtil {

	public static void addFragmentEntryLinkFieldsSelectorURL(
		ItemSelector itemSelector, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, JSONObject jsonObject) {

		JSONArray fieldSetsJSONArray = jsonObject.getJSONArray("fieldSets");

		if (fieldSetsJSONArray == null) {
			return;
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject fieldSetsJSONObject = fieldSetsJSONArray.getJSONObject(
				i);

			JSONArray fieldsJSONArray = fieldSetsJSONObject.getJSONArray(
				"fields");

			if (fieldsJSONArray == null) {
				continue;
			}

			for (int j = 0; j < fieldsJSONArray.length(); j++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(j);

				if ((fieldJSONObject != null) &&
					Objects.equals(
						fieldJSONObject.getString("type"), "itemSelector") &&
					fieldJSONObject.has("typeOptions")) {

					JSONObject typeOptionsJSONObject =
						fieldJSONObject.getJSONObject("typeOptions");

					if (typeOptionsJSONObject.has("itemType")) {
						typeOptionsJSONObject.put(
							"itemSelectorUrl",
							_getInfoItemSelectorURL(
								itemSelector, httpServletRequest,
								liferayPortletResponse, typeOptionsJSONObject));
					}
				}
			}
		}
	}

	private static String _getInfoItemSelectorURL(
		ItemSelector itemSelector, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		JSONObject typeOptionsJSONObject) {

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		String itemType = typeOptionsJSONObject.getString("itemType");

		if (Validator.isNotNull(itemType)) {
			itemSelectorCriterion.setItemType(itemType);

			String itemSubtype = typeOptionsJSONObject.getString("itemSubtype");

			if (Validator.isNotNull(itemSubtype)) {
				itemSelectorCriterion.setItemSubtype(itemSubtype);
			}

			JSONArray mimeTypesJSONArray = typeOptionsJSONObject.getJSONArray(
				"mimeTypes");

			if (mimeTypesJSONArray != null) {
				List<String> mimeTypes = new ArrayList<>();

				for (int i = 0; i < mimeTypesJSONArray.length(); i++) {
					mimeTypes.add(mimeTypesJSONArray.getString(i));
				}

				itemSelectorCriterion.setMimeTypes(
					mimeTypes.toArray(new String[0]));
			}
		}

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());

		PortletURL infoItemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			liferayPortletResponse.getNamespace() + "selectInfoItem",
			itemSelectorCriterion);

		if (infoItemSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoItemSelectorURL.toString();
	}

}