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

package com.liferay.blogs.editor.configuration.internal;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsFileUploadsConfiguration",
	property = {
		"editor.config.key=contentEditor",
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN
	},
	service = EditorConfigContributor.class
)
public class BlogsContentEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put(
			"allowedContent",
			StringBundler.concat(
				"a[*](*); ", _getAllowedContentText(),
				" div[*](*); figcaption; figure; iframe[*](*); img[*](*){*}; ",
				_getAllowedContentLists(), " p[*](*){text-align}; ",
				_getAllowedContentTable(), " source[*](*); video[*](*);")
		).put(
			"stylesSet", _getStyleFormatsJSONArray(themeDisplay.getLocale())
		);

		String namespace = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:namespace"));
		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get("liferay-ui:input-editor:name"));

		_populateFileBrowserURL(
			jsonObject, requestBackedPortletURLFactory,
			namespace + name + "selectItem");

		_populateTwitterButton(jsonObject);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Validator.isNotNull(portletDisplay.getId())) {
			jsonObject.put(
				"uploadUrl",
				PortletURLBuilder.create(
					requestBackedPortletURLFactory.createActionURL(
						portletDisplay.getId())
				).setActionName(
					"/blogs/upload_temp_image"
				).buildString());
		}

		String editorName = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:editorName"));

		if (editorName.equals("ballooneditor")) {
			jsonObject.put(
				"extraPlugins",
				"itemselector,stylescombo,ballooneditor," +
					"videoembed,insertbutton,codemirror"
			).put(
				"toolbarText",
				"Styles,Bold,Italic,Underline,BulletedList" +
					",NumberedList,TextLink,SourceEditor"
			);
		}
	}

	private String _getAllowedContentLists() {
		return "li ol ul;";
	}

	private String _getAllowedContentTable() {
		return StringBundler.concat(
			"col[span]; colgroup[span]; table[border, cellpadding, ",
			"cellspacing]{width}; tbody td[colspan, headers, rowspan]{*}; ",
			"th[abbr, colspan, headers, rowspan, scope, sorted]{*}; thead tr;");
	}

	private String _getAllowedContentText() {
		return "b blockquote cite code em h1 h2 h3 h4 h5 h6 hr i pre s " +
			"strike strong u;";
	}

	private JSONObject _getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject = JSONUtil.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put(
			"element", element
		).put(
			"name", styleFormatName
		);

		return styleJSONObject;
	}

	private JSONArray _getStyleFormatsJSONArray(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, "com.liferay.frontend.editor.lang");

		return JSONUtil.putAll(
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p", null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				null),
			_getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "pre",
				null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code",
				null),
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "info-message"), "div",
				"overflow-auto portlet-msg-info"),
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "alert-message"), "div",
				"overflow-auto portlet-msg-alert"),
			_getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "error-message"), "div",
				"overflow-auto portlet-msg-error"));
	}

	private void _populateFileBrowserURL(
		JSONObject jsonObject,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String eventName) {

		ItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType(),
			new URLItemSelectorReturnType());

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType(),
			new URLItemSelectorReturnType());

		ItemSelectorCriterion urlItemSelectorCriterion =
			new URLItemSelectorCriterion();

		urlItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, eventName,
			blogsItemSelectorCriterion, imageItemSelectorCriterion,
			urlItemSelectorCriterion);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorURL.toString()
		).put(
			"filebrowserImageBrowseUrl", itemSelectorURL.toString()
		);
	}

	private void _populateTwitterButton(JSONObject jsonObject) {
		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		if (toolbarsJSONObject == null) {
			return;
		}

		JSONObject toolbarsStylesJSONObject = toolbarsJSONObject.getJSONObject(
			"styles");

		if (toolbarsStylesJSONObject == null) {
			return;
		}

		JSONArray toolbarsStylesSelectionsJSONArray =
			toolbarsStylesJSONObject.getJSONArray("selections");

		if (toolbarsStylesSelectionsJSONArray == null) {
			return;
		}

		for (int i = 0; i < toolbarsStylesSelectionsJSONArray.length(); i++) {
			JSONObject toolbarsStylesSelectionsJSONObject =
				toolbarsStylesSelectionsJSONArray.getJSONObject(i);

			if (toolbarsStylesSelectionsJSONObject == null) {
				continue;
			}

			String toolbarsStylesSelectionsTest =
				toolbarsStylesSelectionsJSONObject.getString("test");

			if (!toolbarsStylesSelectionsTest.equals(
					"AlloyEditor.SelectionTest.text")) {

				continue;
			}

			JSONArray buttonsJSONArray =
				toolbarsStylesSelectionsJSONObject.getJSONArray("buttons");

			if (buttonsJSONArray == null) {
				continue;
			}

			buttonsJSONArray.put("twitter");

			return;
		}
	}

	@Reference
	private ItemSelector _itemSelector;

}