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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

		StringBundler sb = new StringBundler(5);

		sb.append(getAllowedContentText());
		sb.append(" a[*](*); div[*](*){text-align}; img[*](*){*}; p[*](*); ");
		sb.append(getAllowedContentLists());
		sb.append(getAllowedContentTable());
		sb.append(" span[*](*){*}; ");

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "_EDITOR_NAME_selectItem",
			getFileItemSelectorCriterion(), getLayoutItemSelectorURL());
		PortletURL imageSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "_EDITOR_NAME_selectItem",
			getImageItemSelectorCriterion(), getURLItemSelectorCriterion());

		jsonObject.put(
			"allowedContent", sb.toString()
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
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected String getAllowedContentLists() {
		return "li ol ul [*](*){*};";
	}

	protected String getAllowedContentTable() {
		return "table[border, cellpadding, cellspacing] {width}; tbody td " +
			"th[scope]; thead tr[scope];";
	}

	protected String getAllowedContentText() {
		return "b code em h1 h2 h3 h4 h5 h6 hr i p pre strong u [*](*){*};";
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

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass, int type) {

		return JSONUtil.put(
			"name", styleFormatName
		).put(
			"style", getStyleJSONObject(element, cssClass, type)
		);
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		Class<?> clazz = getClass();

		ResourceBundle resourceBundle = null;

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				new ClassResourceBundleLoader(
					"content.Language", clazz.getClassLoader()),
				_resourceBundleLoader, LanguageUtil.getResourceBundleLoader());

		try {
			resourceBundle = resourceBundleLoader.loadResourceBundle(locale);
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(missingResourceException, missingResourceException);
			}

			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "small"), "span", "small",
				_CKEDITOR_STYLE_INLINE),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "lead"), "span", "lead",
				_CKEDITOR_STYLE_INLINE),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				null, _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				null, _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				null, _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				null, _CKEDITOR_STYLE_BLOCK));
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		return JSONUtil.put(
			"cfg", JSONUtil.put("styles", getStyleFormatsJSONArray(locale))
		).put(
			"name", "styles"
		);
	}

	protected JSONObject getStyleJSONObject(
		String element, String cssClass, int type) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			styleJSONObject.put("attributes", JSONUtil.put("class", cssClass));
		}

		styleJSONObject.put(
			"element", element
		).put(
			"type", type
		);

		return styleJSONObject;
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		return JSONUtil.put(
			"add",
			JSONUtil.put(
				"buttons", toJSONArray("['image', 'hline']")
			).put(
				"tabIndex", 1
			)
		).put(
			"styles", getToolbarsStylesJSONObject(locale)
		);
	}

	protected JSONObject getToolbarsStylesJSONObject(Locale locale) {
		return JSONUtil.put(
			"selections", getToolbarsStylesSelectionsJSONArray(locale)
		).put(
			"tabIndex", 1
		);
	}

	protected JSONObject getToolbarsStylesSelectionsImageJSONObject() {
		return JSONUtil.put(
			"buttons", JSONUtil.putAll("imageLeft", "imageCenter", "imageRight")
		).put(
			"name", "image"
		).put(
			"test", "AlloyEditor.SelectionTest.image"
		);
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getToolbarsStylesSelectionsImageJSONObject(),
			getToolbarsStylesSelectionsLinkJSONObject(),
			getToolbarsStylesSelectionsTextJSONObject(locale));
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		return JSONUtil.put(
			"buttons", toJSONArray("['linkEditBrowse']")
		).put(
			"name", "link"
		).put(
			"test", "AlloyEditor.SelectionTest.link"
		);
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons",
			JSONUtil.putAll(
				getStyleFormatsJSONObject(locale),
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

	protected ItemSelectorCriterion getURLItemSelectorCriterion() {
		ItemSelectorCriterion itemSelectorCriterion =
			new URLItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkRichTextEditorConfigContributor.class);

	@Reference
	private ItemSelector _itemSelector;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}