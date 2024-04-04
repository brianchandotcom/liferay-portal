/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.item.selector.web.internal.display.context;

import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.item.selector.criterion.LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;

/**
 * @author Bárbara Cabrera
 */
public class LayoutPageTemplateCollectionsTreeNodeDisplayContext {

	public LayoutPageTemplateCollectionsTreeNodeDisplayContext(
		LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion
			layoutPageTemplateCollectionTreeNodeItemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		_layoutPageTemplateCollectionTreeNodeItemSelectorCriterion =
			layoutPageTemplateCollectionTreeNodeItemSelectorCriterion;
		_themeDisplay = themeDisplay;
	}

	public JSONArray getLayoutPageTemplateCollectionJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"children",
				_getLayoutPageTemplateCollectionJSONArray(
					_themeDisplay.getScopeGroupId(), 0)
			).put(
				"id", 0
			).put(
				"name", LanguageUtil.get(_themeDisplay.getLocale(), "home")
			));
	}

	private JSONArray _getLayoutPageTemplateCollectionJSONArray(
		long groupId, long layoutPageTemplateCollectionId) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			LayoutPageTemplateCollectionLocalServiceUtil.
				getLayoutPageTemplateCollections(
					groupId, layoutPageTemplateCollectionId,
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE);

		for (LayoutPageTemplateCollection layoutPageTemplateCollection :
				layoutPageTemplateCollections) {

			jsonArray.put(
				JSONUtil.put(
					"children",
					() -> {
						JSONArray childrenJSONArray =
							_getLayoutPageTemplateCollectionJSONArray(
								groupId,
								layoutPageTemplateCollection.
									getLayoutPageTemplateCollectionId());

						if (childrenJSONArray.length() > 0) {
							return childrenJSONArray;
						}

						return null;
					}
				).put(
					"disabled",
					() -> {
						if (ArrayUtil.contains(
								_layoutPageTemplateCollectionTreeNodeItemSelectorCriterion.
									getLayoutPageTemplateCollectionIds(),
								layoutPageTemplateCollection.
									getLayoutPageTemplateCollectionId())) {

							return true;
						}

						return false;
					}
				).put(
					"id",
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId()
				).put(
					"name", layoutPageTemplateCollection.getName()
				));
		}

		return jsonArray;
	}

	private final LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion
		_layoutPageTemplateCollectionTreeNodeItemSelectorCriterion;
	private final ThemeDisplay _themeDisplay;

}