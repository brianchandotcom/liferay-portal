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

package com.liferay.layout.content.page.editor.web.internal.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadURLItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"editor.config.key=fragmenEntryLinkRichTextEditor",
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET
	},
	service = EditorConfigContributor.class
)
public class FragmentEntryLinkRichTextEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "_EDITOR_NAME_selectItem",
			getFileItemSelectorCriterion(), getLayoutItemSelectorURL());
		PortletURL imageSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "_EDITOR_NAME_selectItem",
			getImageItemSelectorCriterion(), getURLItemSelectorCriterion());

		jsonObject.put(
			"allowedContent",
			StringBundler.concat(
				_getAllowedContentText(),
				" a[*](*); div[*](*){text-align}; img[*](*){*}; p[*](*); ",
				_getAllowedContentLists(), _getAllowedContentTable(),
				" span[*](*){*}; ")
		).put(
			"documentBrowseLinkUrl", itemSelectorURL.toString()
		).put(
			"enterMode", 2
		).put(
			"extraPlugins", getExtraPluginsLists()
		).put(
			"filebrowserImageBrowseLinkUrl", imageSelectorURL.toString()
		).put(
			"filebrowserImageBrowseUrl", imageSelectorURL.toString()
		).put(
			"removePlugins", getRemovePluginsLists()
		).put(
			"skin", "moono-lisa"
		).put(
			"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).put(
			"toolbars", _getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected String getExtraPluginsLists() {
		return "ae_autolink,ae_dragresize,ae_addimages,ae_imagealignment," +
			"ae_placeholder,ae_selectionregion,ae_tableresize," +
				"ae_tabletools,ae_uicore,itemselector,media,adaptivemedia";
	}

	protected ItemSelectorCriterion getFileItemSelectorCriterion() {
		ItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadURLItemSelectorReturnType());

		return fileItemSelectorCriterion;
	}

	protected ItemSelectorCriterion getImageItemSelectorCriterion() {
		ItemSelectorCriterion itemSelectorCriterion =
			new ImageItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadURLItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	protected ItemSelectorCriterion getLayoutItemSelectorURL() {
		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());
		layoutItemSelectorCriterion.setShowHiddenPages(true);

		return layoutItemSelectorCriterion;
	}

	protected String getRemovePluginsLists() {
		return "contextmenu,elementspath,floatingspace,image,link,liststyle," +
			"magicline,resize,tabletools,toolbar,ae_embed";
	}

	protected ItemSelectorCriterion getURLItemSelectorCriterion() {
		ItemSelectorCriterion itemSelectorCriterion =
			new URLItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	private String _getAllowedContentLists() {
		return "li ol ul [*](*){*};";
	}

	private String _getAllowedContentTable() {
		return "table[border, cellpadding, cellspacing] {width}; tbody td " +
			"th[scope]; thead tr[scope];";
	}

	private String _getAllowedContentText() {
		return "b code em h1 h2 h3 h4 h5 h6 hr i p pre strong u [*](*){*};";
	}

	private JSONObject _getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass, int type) {

		return JSONUtil.put(
			"name", styleFormatName
		).put(
			"style", _getStyleJSONObject(element, cssClass, type)
		);
	}

	private JSONArray _getStyleFormatsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "small"), "span", "small",
				_CKEDITOR_STYLE_INLINE),
			_getStyleFormatJSONObject(
				LanguageUtil.get(locale, "lead"), "span", "lead",
				_CKEDITOR_STYLE_INLINE),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "1"), "h1", null,
				_CKEDITOR_STYLE_BLOCK),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "2"), "h2", null,
				_CKEDITOR_STYLE_BLOCK),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "3"), "h3", null,
				_CKEDITOR_STYLE_BLOCK),
			_getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "4"), "h4", null,
				_CKEDITOR_STYLE_BLOCK));
	}

	private JSONObject _getStyleFormatsJSONObject(Locale locale) {
		return JSONUtil.put(
			"cfg", JSONUtil.put("styles", _getStyleFormatsJSONArray(locale))
		).put(
			"name", "styles"
		);
	}

	private JSONObject _getStyleJSONObject(
		String element, String cssClass, int type) {

		return JSONUtil.put(
			"attributes",
			() -> {
				if (Validator.isNotNull(cssClass)) {
					return JSONUtil.put("class", cssClass);
				}

				return null;
			}
		).put(
			"element", element
		).put(
			"type", type
		);
	}

	private JSONObject _getToolbarsJSONObject(Locale locale) {
		return JSONUtil.put(
			"add",
			JSONUtil.put(
				"buttons", toJSONArray("['image', 'hline']")
			).put(
				"tabIndex", 1
			)
		).put(
			"styles", _getToolbarsStylesJSONObject(locale)
		);
	}

	private JSONObject _getToolbarsStylesJSONObject(Locale locale) {
		return JSONUtil.put(
			"selections", _getToolbarsStylesSelectionsJSONArray(locale)
		).put(
			"tabIndex", 1
		);
	}

	private JSONObject _getToolbarsStylesSelectionsImageJSONObject() {
		return JSONUtil.put(
			"buttons", JSONUtil.putAll("imageLeft", "imageCenter", "imageRight")
		).put(
			"name", "image"
		).put(
			"test", "AlloyEditor.SelectionTest.image"
		);
	}

	private JSONArray _getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			_getToolbarsStylesSelectionsImageJSONObject(),
			_getToolbarsStylesSelectionsLinkJSONObject(),
			_getToolbarsStylesSelectionsTextJSONObject(locale));
	}

	private JSONObject _getToolbarsStylesSelectionsLinkJSONObject() {
		return JSONUtil.put(
			"buttons", toJSONArray("['linkEditBrowse']")
		).put(
			"name", "link"
		).put(
			"test", "AlloyEditor.SelectionTest.link"
		);
	}

	private JSONObject _getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons",
			JSONUtil.putAll(
				_getStyleFormatsJSONObject(locale),
				"bold", "italic", "underline", "ol",
				"ul", "linkBrowse",

				// Separate

				"paragraphLeft", "paragraphCenter",
				"paragraphRight", "paragraphJustify",

				// Separate

				"spacing",

				// Separate

				"color",

				// Separate

				"removeFormat"
			)
		).put(
			"name", "text"
		).put("test", "AlloyEditor.SelectionTest.text");
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	@Reference
	private ItemSelector _itemSelector;

}