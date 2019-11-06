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

import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = {})
public class FragmentEntryLinkConfigurationUtil {

	public static void addFragmentEntryLinkFieldsSelectorURL(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, JSONObject jsonObject) {

		JSONArray fieldSetsJSONArray = jsonObject.getJSONArray("fieldSets");

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject fieldSetsJSONObject = fieldSetsJSONArray.getJSONObject(
				i);

			JSONArray fieldsJSONArray = fieldSetsJSONObject.getJSONArray(
				"fields");

			for (int j = 0; j < fieldsJSONArray.length(); j++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(j);

				if ((fieldJSONObject != null) &&
					Objects.equals(
						fieldJSONObject.getString("type"), "itemSelector") &&
					fieldJSONObject.has("typeOptions")) {

					JSONObject typeOptionsJSONObject =
						fieldJSONObject.getJSONObject("typeOptions");

					if (typeOptionsJSONObject.has("className")) {
						typeOptionsJSONObject.put(
							"itemSelectorUrl",
							_getInfoItemSelectorURL(
								httpServletRequest, liferayPortletResponse,
								typeOptionsJSONObject.getString("className")));
					}
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	private static String _getInfoItemSelectorURL(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, String className) {

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		if (Validator.isNotNull(className)) {
			itemSelectorCriterion.setClassName(className);
		}

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());

		PortletURL infoItemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			liferayPortletResponse.getNamespace() + "selectInfoItem",
			itemSelectorCriterion);

		if (infoItemSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoItemSelectorURL.toString();
	}

	private static ItemSelector _itemSelector;

}