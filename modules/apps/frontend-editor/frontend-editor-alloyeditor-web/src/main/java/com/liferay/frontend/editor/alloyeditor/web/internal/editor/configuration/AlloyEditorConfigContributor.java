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

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	property = "editor.name=alloyeditor",
	service = EditorConfigContributor.class
)
public class AlloyEditorConfigContributor
	extends BaseAlloyEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put("entities", Boolean.FALSE);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins += ",itemselector,media,embedurl";
		}
		else {
			extraPlugins = "itemselector,media,embedurl";
		}

		jsonObject.put(
			"extraPlugins", extraPlugins
		).put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	@Activate
	protected void activate() {
		_aggregateResourceBundleLoader = new AggregateResourceBundleLoader(
			_resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
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
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _aggregateResourceBundleLoader.loadResourceBundle(
				locale);
		}
		catch (MissingResourceException mre) {
			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p", null,
				_CKEDITOR_STYLE_BLOCK),
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
				null, _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "pre",
				null, _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null,
				_CKEDITOR_STYLE_INLINE),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code", null,
				_CKEDITOR_STYLE_INLINE),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "info-message"), "div",
				"portlet-msg-info", _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "alert-message"), "div",
				"portlet-msg-alert", _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "error-message"), "div",
				"portlet-msg-error", _CKEDITOR_STYLE_BLOCK));
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		JSONObject stylesJSONObject = JSONUtil.put(
			"styles", getStyleFormatsJSONArray(locale));

		return JSONUtil.put(
			"cfg", stylesJSONObject
		).put(
			"name", "styles"
		);
	}

	protected JSONObject getStyleJSONObject(
		String element, String cssClass, int type) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject = JSONUtil.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put(
			"element", element
		).put(
			"type", type
		);

		return styleJSONObject;
	}

	protected JSONObject getToolbarsAddJSONObject() {
		return JSONUtil.put(
			"buttons", toJSONArray("['image', 'embedVideo', 'table', 'hline']")
		).put(
			"tabIndex", 2
		);
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		return JSONUtil.put(
			"add", getToolbarsAddJSONObject()
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

	protected JSONObject getToolbarsStylesSelectionsEmbedURLJSONObject() {
		return JSONUtil.put(
			"buttons", toJSONArray("['imageLeft', 'imageCenter', 'imageRight']")
		).put(
			"name", "embedurl"
		).put(
			"test", "AlloyEditor.SelectionTest.embedUrl"
		);
	}

	protected JSONObject getToolbarsStylesSelectionsImageJSONObject() {
		return JSONUtil.put(
			"buttons",
			toJSONArray(
				"['imageLeft', 'imageCenter', 'imageRight', 'linkBrowse', " +
					"'imageAlt']")
		).put(
			"name", "image"
		).put(
			"setPosition", "AlloyEditor.SelectionSetPosition.image"
		).put(
			"test", "AlloyEditor.SelectionTest.image"
		);
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getToolbarsStylesSelectionsEmbedURLJSONObject(),
			getToolbarsStylesSelectionsLinkJSONObject(),
			getToolbarsStylesSelectionsImageJSONObject(),
			getToolbarsStylesSelectionsTextJSONObject(locale),
			getToolbarsStylesSelectionsTableJSONObject());
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

	protected JSONObject getToolbarsStylesSelectionsTableJSONObject() {
		return JSONUtil.put(
			"buttons",
			toJSONArray(
				"['tableHeading', 'tableRow', 'tableColumn', 'tableCell', " +
					"'tableRemove']")
		).put(
			"getArrowBoxClasses",
			"AlloyEditor.SelectionGetArrowBoxClasses.table"
		).put(
			"name", "table"
		).put(
			"setPosition", "AlloyEditor.SelectionSetPosition.table"
		).put(
			"test", "AlloyEditor.SelectionTest.table"
		);
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons",
			JSONUtil.putAll(
				getStyleFormatsJSONObject(locale), "bold", "italic",
				"underline", "ol", "ul", "linkBrowse")
		).put(
			"name", "text"
		).put(
			"test", "AlloyEditor.SelectionTest.text"
		);
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	private ResourceBundleLoader _aggregateResourceBundleLoader;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}