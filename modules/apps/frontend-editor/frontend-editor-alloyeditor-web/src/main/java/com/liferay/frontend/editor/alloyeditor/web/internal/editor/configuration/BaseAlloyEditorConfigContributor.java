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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.alloyeditor.web.internal.constants.AlloyEditorConstants;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseAlloyEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put(
			"allowedContent", Boolean.TRUE
		).put(
			"contentsLangDirection",
			HtmlUtil.escapeJS(
				getContentsLanguageDir(inputEditorTaglibAttributes))
		).put(
			"contentsLanguage",
			StringUtil.replace(
				getContentsLanguageId(inputEditorTaglibAttributes), "iw_",
				"he_")
		).put(
			"disableNativeSpellChecker", Boolean.FALSE
		).put(
			"extraPlugins",
			"addimages,ae_autolink,ae_dragresize,ae_imagealignment," +
				"ae_placeholder,ae_selectionregion,ae_tableresize," +
					"ae_tabletools,ae_uicore"
		).put(
			"imageScaleResize", "scale"
		).put(
			"language",
			StringUtil.replace(getLanguageId(themeDisplay), "iw_", "he_")
		).put(
			"removePlugins",
			"contextmenu,elementspath,floatingspace,image,link,liststyle," +
				"resize,table,tabletools,toolbar"
		).put(
			"skin", "moono-lisa"
		);

		String namespace = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				AlloyEditorConstants.ATTRIBUTE_NAMESPACE + ":namespace"));

		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				AlloyEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

		name = namespace + name;

		jsonObject.put("srcNode", name);

		populateFileBrowserURL(
			jsonObject, requestBackedPortletURLFactory,
			name + "selectDocument");
	}

	protected void populateFileBrowserURL(
		JSONObject jsonObject,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String eventName) {

		ItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());
		layoutItemSelectorCriterion.setShowHiddenPages(true);

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, eventName,
			fileItemSelectorCriterion, layoutItemSelectorCriterion);

		jsonObject.put("documentBrowseLinkUrl", itemSelectorURL.toString());
	}

	@Reference(unbind = "-")
	protected void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	private ItemSelector _itemSelector;

}