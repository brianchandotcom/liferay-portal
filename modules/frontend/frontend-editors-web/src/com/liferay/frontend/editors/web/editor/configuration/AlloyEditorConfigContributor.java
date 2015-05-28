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

package com.liferay.frontend.editors.web.editor.configuration;

import com.liferay.document.library.item.selector.web.DLItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.layout.item.selector.web.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.net.URL;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	property = {"editor.name=alloyeditor"},
	service = EditorConfigContributor.class
)
public class AlloyEditorConfigContributor extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

		String contentsLanguageDir = LanguageUtil.get(
			contentsLocale, "lang.dir");

		contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

		jsonObject.put(
			"contentsLangDirection", HtmlUtil.escapeJS(contentsLanguageDir));
		jsonObject.put(
			"contentsLanguage", contentsLanguageId.replace("iw_", "he_"));
		jsonObject.put(
			"extraPlugins",
			"autolink,dragresize,dropimages,placeholder,selectionregion," +
				"tableresize,tabletools,uicore");

		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		jsonObject.put("language", languageId.replace("iw_", "he_"));
		jsonObject.put(
			"removePlugins", "elementspath,link,liststyle,resize,toolbar");

		if (liferayPortletResponse != null) {
			String name =
				liferayPortletResponse.getNamespace() +
					GetterUtil.getString(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:name"));

			populateFileBrowserURL(
				jsonObject, liferayPortletResponse,
				themeDisplay.getScopeGroupId(), name + "selectDocument");

			jsonObject.put("srcNode", name);
		}

		jsonObject.put("toolbars", getToolbarsJSONObject());
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected JSONObject getToolbarsAddJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['image', 'table', 'hline']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsAddJSONObject());
		jsonObject.put("styles", getToolbarsStylesJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("selections", getToolbarsStylesSelectionsJSONArray());
		jsonObject.put("tabIndex", 1);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsImageJSONObject() {
		JSONObject jsonNObject = JSONFactoryUtil.createJSONObject();

		jsonNObject.put("buttons", toJSONArray("['imageLeft', 'imageRight']"));
		jsonNObject.put("name", "image");
		jsonNObject.put("test", "AlloyEditor.SelectionTest.image");

		return jsonNObject;
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getToolbarsStylesSelectionsLinkJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsImageJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTextJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTableJSONObject());

		return jsonArray;
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['linkEdit']"));
		jsonObject.put("name", "link");
		jsonObject.put("test", "AlloyEditor.SelectionTest.link");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTableJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons",
			toJSONArray(
				"['tableRow', 'tableColumn', 'tableCell', 'tableRemove']"));
		jsonObject.put(
			"getArrowBoxClasses",
			"AlloyEditor.SelectionGetArrowBoxClasses.table");
		jsonObject.put("name", "table");
		jsonObject.put("setPosition", "AlloyEditor.SelectionSetPosition.table");
		jsonObject.put("test", "AlloyEditor.SelectionTest.table");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons",
			toJSONArray(
				"['styles', 'bold', 'italic', 'underline', 'link', " +
					"'twitter']"));
		jsonObject.put("name", "text");
		jsonObject.put("test", "AlloyEditor.SelectionTest.text");

		return jsonObject;
	}

	protected void populateFileBrowserURL(
		JSONObject jsonObject, LiferayPortletResponse liferayPortletResponse,
		long scopeGroupId, String eventName) {

		Set<Class<?>> desiredReturnTypes = new HashSet<>();

		desiredReturnTypes.add(URL.class);

		ItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion(scopeGroupId);

		layoutItemSelectorCriterion.setDesiredReturnTypes(desiredReturnTypes);

		PortletURL layoutItemSelectorURL = _itemSelector.getItemSelectorURL(
			liferayPortletResponse, eventName, layoutItemSelectorCriterion);

		jsonObject.put(
			"filebrowserBrowseUrl", layoutItemSelectorURL.toString());

		ItemSelectorCriterion dlItemSelectorCriterion =
			new DLItemSelectorCriterion(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, scopeGroupId,
				"images", PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES,
				false);

		dlItemSelectorCriterion.setDesiredReturnTypes(desiredReturnTypes);

		PortletURL dlItemSelectorURL = _itemSelector.getItemSelectorURL(
			liferayPortletResponse, eventName, dlItemSelectorCriterion);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", dlItemSelectorURL.toString());
		jsonObject.put(
			"filebrowserImageBrowseUrl", dlItemSelectorURL.toString());
	}

	private ItemSelector _itemSelector;

}