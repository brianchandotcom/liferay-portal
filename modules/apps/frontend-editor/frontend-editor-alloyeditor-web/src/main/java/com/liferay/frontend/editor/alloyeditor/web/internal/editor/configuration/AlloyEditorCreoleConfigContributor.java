/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.alloyeditor.web.internal.constants.AlloyEditorConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		JSONObject buttonCfgJSONObject = JSONUtil.put(
			"linkEditBrowse",
			JSONUtil.put(
				"appendProtocol", false
			).put(
				"showTargetSelector", false
			));

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

		jsonObject.put(
			"removePlugins",
			StringBundler.concat(
				removePlugins,
				",ae_dragresize,ae_tableresize,bidi,div,font,forms,",
				"indentblock,justify,keystrokes,maximize,newpage,pagebreak,",
				"preview,print,save,showblocks,smiley,stylescombo,templates,",
				"video")
		).put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, int type) {

		return JSONUtil.put(
			"name", styleFormatName
		).put(
			"style",
			JSONUtil.put(
				"element", element
			).put(
				"type", type
			)
		);
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				_language.get(locale, "normal"), "p", _CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				_language.format(locale, "heading-x", "1"), "h1",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				_language.format(locale, "heading-x", "2"), "h2",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				_language.format(locale, "heading-x", "3"), "h3",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				_language.format(locale, "heading-x", "4"), "h4",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				_language.format(locale, "heading-x", "5"), "h5",
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				_language.format(locale, "heading-x", "6"), "h6",
				_CKEDITOR_STYLE_BLOCK));
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		return JSONUtil.put(
			"cfg", JSONUtil.put("styles", getStyleFormatsJSONArray(locale))
		).put(
			"name", "styles"
		);
	}

	protected JSONObject getToolbarsAddJSONObject() {
		return JSONUtil.put(
			"buttons",
			JSONUtil.putAll(
				"image",
				JSONUtil.put(
					"cfg",
					JSONUtil.put(
						"tableAttributes", _jsonFactory.createJSONObject())
				).put(
					"name", "table"
				),
				"hline")
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

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			_getToolbarsStylesSelectionsHeadingTextJSONObject(locale),
			getToolbarsStylesSelectionsLinkJSONObject(),
			getToolbarsStylesSelectionsTextJSONObject(locale),
			getToolbarsStylesSelectionsTableJSONObject());
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		return JSONUtil.put(
			"buttons",
			JSONUtil.put(
				JSONUtil.put(
					"cfg",
					JSONUtil.put(
						"appendProtocol", false
					).put(
						"showTargetSelector", false
					)
				).put(
					"name", "linkEditBrowse"
				))
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

	private JSONObject _getToolbarsStylesSelectionsHeadingTextJSONObject(
		Locale locale) {

		return JSONUtil.put(
			"buttons", JSONUtil.put(getStyleFormatsJSONObject(locale))
		).put(
			"name", "headertext"
		).put(
			"test", "AlloyEditor.SelectionTest.headingtext"
		);
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}