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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Sergio González
 */
@Component(
	property = "editor.name=alloyeditor_creole",
	service = EditorConfigContributor.class
)
public class AlloyEditorCreoleConfigContributor
	extends BaseAlloyEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put(
			"allowedContent",
			"b strong i hr h1 h2 h3 h4 h5 h6 em ul ol li pre table tr th; " +
				"img a[*]");

		Map<String, String> fileBrowserParams =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				AlloyEditorConstants.ATTRIBUTE_NAMESPACE +
					":fileBrowserParams");

		if (fileBrowserParams != null) {
			String attachmentURLPrefix = fileBrowserParams.get(
				"attachmentURLPrefix");

			if (Validator.isNotNull(attachmentURLPrefix)) {
				jsonObject.put("attachmentURLPrefix", attachmentURLPrefix);
			}
		}

		JSONObject linkEditJSONObject = JSONUtil.put(
			"appendProtocol", false
		).put(
			"showTargetSelector", false
		);

		JSONObject buttonCfgJSONObject = JSONUtil.put(
			"linkEditBrowse", linkEditJSONObject);

		jsonObject.put(
			"buttonCfg", buttonCfgJSONObject
		).put(
			"decodeLinks", Boolean.TRUE
		).put(
			"disableObjectResizing", Boolean.TRUE
		);

		String extraPlugins = jsonObject.getString("extraPlugins");

		extraPlugins = extraPlugins.concat(",creole,itemselector,media");

		jsonObject.put(
			"extraPlugins", extraPlugins
		).put(
			"format_tags", "p;h1;h2;h3;h4;h5;h6;pre"
		);

		String removePlugins = jsonObject.getString("removePlugins");

		StringBundler sb = new StringBundler(4);

		sb.append("ae_dragresize,ae_tableresize,bidi,div,flash,font,forms,");
		sb.append("indentblock,justify,keystrokes,maximize,newpage,pagebreak,");
		sb.append("preview,print,save,showblocks,smiley,stylescombo,");
		sb.append("templates,video");

		jsonObject.put(
			"removePlugins",
			removePlugins.concat(
				","
			).concat(
				sb.toString()
			)
		).put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, int type) {

		JSONObject jsonObject = JSONUtil.put("name", styleFormatName);

		JSONObject styleJSONObject = JSONUtil.put(
			"element", element
		).put(
			"type", type
		);

		jsonObject.put("style", styleJSONObject);

		return jsonObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);
		}
		catch (MissingResourceException mre) {
			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "5"), "h5",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "6"), "h6",
				_CKEDITOR_STYLE_BLOCK));
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

	protected JSONObject getToolbarsAddJSONObject() {
		JSONObject cfgJSONObject = JSONUtil.put(
			"tableAttributes", JSONFactoryUtil.createJSONObject());

		JSONObject buttonJSONObject = JSONUtil.put(
			"cfg", cfgJSONObject
		).put(
			"name", "table"
		);

		return JSONUtil.put(
			"buttons", JSONUtil.putAll("image", buttonJSONObject, "hline")
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

	protected JSONObject getToolbarsStylesSelectionsHeadingTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons", JSONUtil.put(getStyleFormatsJSONObject(locale))
		).put(
			"name", "headertext"
		).put(
			"test", "AlloyEditor.SelectionTest.headingtext"
		);
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getToolbarsStylesSelectionsHeadingTextJSONObject(locale),
			getToolbarsStylesSelectionsLinkJSONObject(),
			getToolbarsStylesSelectionsTextJSONObject(locale),
			getToolbarsStylesSelectionsTableJSONObject());
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject cfgJSONObject = JSONUtil.put(
			"appendProtocol", false
		).put(
			"showTargetSelector", false
		);

		JSONObject linkEditJSONObject = JSONUtil.put(
			"cfg", cfgJSONObject
		).put(
			"name", "linkEditBrowse"
		);

		return JSONUtil.put(
			"buttons", JSONUtil.put(linkEditJSONObject)
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
				getStyleFormatsJSONObject(locale), "bold", "italic", "ul", "ol",
				"linkBrowse", "removeFormat")
		).put(
			"name", "text"
		).put(
			"test", "AlloyEditor.SelectionTest.text"
		);
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}