/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=ckeditor_bbcode",
	service = EditorConfigContributor.class
)
public class CKEditorBBCodeConfigContributor
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
			"allowedContent", Boolean.TRUE
		).put(
			"enterMode", 2
		).put(
			"extraPlugins",
			"a11yhelpbtn,bbcode,filebrowser,itemselector,sourcearea"
		).put(
			"fontSize_defaultLabel", "14"
		).put(
			"fontSize_sizes",
			"10/10px;12/12px;14/14px;16/16px;18/18px;24/24px;32/32px;48/48px"
		).put(
			"format_tags", "p;pre"
		).put(
			"imagesPath",
			_html.escape(themeDisplay.getPathThemeImages()) + "/message_boards/"
		).put(
			"lang", _getLangJSONObject(inputEditorTaglibAttributes)
		).put(
			"newThreadURL", MBThreadConstants.NEW_THREAD_URL
		).put(
			"removePlugins",
			"bidi,codemirror,div,elementspath,forms,indentblock,keystrokes," +
				"maximize,newpage,pagebreak,preview,print,save," +
					"showblocks,templates,video"
		).put(
			"smiley_descriptions",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonDescriptions())
		).put(
			"smiley_images",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonFiles())
		).put(
			"smiley_path",
			_html.escape(themeDisplay.getPathThemeImages()) + "/emoticons/"
		).put(
			"smiley_symbols",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonSymbols())
		);
	}

	private JSONObject _getLangJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.put(
			"code",
			_language.get(
				getContentsLocale(inputEditorTaglibAttributes), "code"));
	}

	@Reference
	private Html _html;

	@Reference
	private Language _language;

}