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

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.ckeditor.web.internal.constants.CKEditorConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=ckeditor_creole",
	service = EditorConfigContributor.class
)
public class CKEditorCreoleConfigContributor
	extends BaseCKEditorConfigContributor {

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
			"b strong i u hr h1 h2 h3 h4 h5 h6 em ul ol li pre table tr th; " +
				"img a[*]");

		Map<String, String> fileBrowserParams =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				CKEditorConstants.ATTRIBUTE_NAMESPACE + ":fileBrowserParams");

		if (fileBrowserParams != null) {
			String attachmentURLPrefix = fileBrowserParams.get(
				"attachmentURLPrefix");

			if (Validator.isNotNull(attachmentURLPrefix)) {
				jsonObject.put("attachmentURLPrefix", attachmentURLPrefix);
			}
		}

		jsonObject.put(
			"decodeLinks", Boolean.TRUE
		).put(
			"disableObjectResizing", Boolean.TRUE
		).put(
			"extraPlugins",
			"a11yhelpbtn,creole,itemselector,lfrpopup,showborders," +
				"sourcearea,wikilink"
		).put(
			"filebrowserWindowFeatures",
			"title=" + LanguageUtil.get(themeDisplay.getLocale(), "browse")
		).put(
			"format_tags", "p;h1;h2;h3;h4;h5;h6;pre"
		).put(
			"removePlugins",
			StringBundler.concat(
				"bidi,codemirror,colorbutton,colordialog,div,elementspath,",
				"font,forms,indentblock,justify,keystrokes,link,maximize,",
				"newpage,pagebreak,preview,print,save,showblocks,smiley,",
				"stylescombo,templates,video")
		).put(
			"toolbar_creole",
			_getToolbarsCreoleJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_phone",
			_getToolbarsPhoneJSONArray(inputEditorTaglibAttributes)
		).put(
			"toolbar_tablet",
			_getToolbarsTabletJSONArray(inputEditorTaglibAttributes)
		);
	}

	private JSONArray _getToolbarsCreoleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic', 'Underline', '-' ,'RemoveFormat']"),
			toJSONArray("['NumberedList', 'BulletedList', '-']"),
			toJSONArray("['Format']"), toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['Table', '-','ImageSelector', '-', 'HorizontalRule']")
		).put(
			() -> {
				if (isShowSource(inputEditorTaglibAttributes)) {
					return toJSONArray("['Source']");
				}

				return null;
			}
		).put(
			toJSONArray("['A11YBtn']")
		);
	}

	private JSONArray _getToolbarsPhoneJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', 'Unlink']"), toJSONArray("['ImageSelector']")
		).put(
			() -> {
				if (isShowSource(inputEditorTaglibAttributes)) {
					return toJSONArray("['Source']");
				}

				return null;
			}
		);
	}

	private JSONArray _getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.putAll(
			toJSONArray("['Bold', 'Italic']"),
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"),
			toJSONArray("['Format']"), toJSONArray("['Link', 'Unlink']"),
			toJSONArray("['ImageSelector']")
		).put(
			() -> {
				if (isShowSource(inputEditorTaglibAttributes)) {
					return toJSONArray("['Source']");
				}

				return null;
			}
		);
	}

}