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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author Marcela Cunha
 */
public class DocumentLibraryFieldType extends FieldType {

	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		DataDefinitionField dataDefinitionField = super.deserialize(jsonObject);

		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "groupId",
				jsonObject.getLong("groupId")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(),
				"itemSelectorAuthToken",
				jsonObject.getString("itemSelectorAuthToken")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "lexiconIconsPath",
				jsonObject.getString("lexiconIconsPath")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "strings",
				CustomPropertyUtil.toMap(jsonObject.getJSONObject("strings"))));

		return dataDefinitionField;
	}

	public void includeContext(
		Map<String, Object> context, DataDefinitionField dataDefinitionField,
		DLAppService dlAppService, Html html,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portal portal,
		boolean readOnly) {

		super.includeContext(
			context, dataDefinitionField, httpServletRequest,
			httpServletResponse, readOnly);

		context.put(
			"groupId",
			CustomPropertyUtil.getLong(
				dataDefinitionField.getCustomProperties(), "groupId"));
		context.put(
			"itemSelectorAuthToken",
			_getItemSelectorAuthToken(httpServletRequest, portal));
		context.put(
			"lexiconIconsPath", _getLexiconIconsPath(httpServletRequest));
		context.put(
			"value",
			JSONFactoryUtil.looseDeserialize(
				CustomPropertyUtil.getString(
					dataDefinitionField.getCustomProperties(), "value", "{}")));

		if (!StringUtils.isEmpty(
				CustomPropertyUtil.getString(
					dataDefinitionField.getCustomProperties(), "value"))) {

			JSONObject valueJSONObject = _getValueJSONObject(
				CustomPropertyUtil.getString(
					dataDefinitionField.getCustomProperties(), "value"));

			if (valueJSONObject.length() > 0) {
				FileEntry fileEntry = _getFileEntry(
					valueJSONObject, dlAppService);

				context.put(
					"fileEntryTitle", _getFileEntryTitle(fileEntry, html));
				context.put(
					"fileEntryURL",
					_getFileEntryURL(httpServletRequest, fileEntry, html));
			}
		}

		Locale displayLocale = _getDisplayLocale(httpServletRequest);

		ResourceBundle resourceBundle = _getResourceBundle(
			displayLocale, portal);

		Map<String, String> values = new HashMap<>();

		values.put("select", LanguageUtil.get(resourceBundle, "select"));

		context.put("strings", values);
	}

	public JSONObject toJSONObject(DataDefinitionField dataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(dataDefinitionField);

		return jsonObject.put(
			"groupId",
			CustomPropertyUtil.getLong(
				dataDefinitionField.getCustomProperties(), "groupId")
		).put(
			"itemSelectorAuthToken",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(),
				"itemSelectorAuthToken")
		).put(
			"lexiconIconsPath",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "lexiconIconsPath")
		).put(
			"strings",
			CustomPropertyUtil.toJSONObject(
				CustomPropertyUtil.getMap(
					dataDefinitionField.getCustomProperties(), "strings"))
		);
	}

	private Locale _getDisplayLocale(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getLocale();
	}

	private FileEntry _getFileEntry(
		JSONObject valueJSONObject, DLAppService dlAppService) {

		try {
			return dlAppService.getFileEntryByUuidAndGroupId(
				valueJSONObject.getString("uuid"),
				valueJSONObject.getLong("groupId"));
		}
		catch (PortalException pe) {
			_log.error("Unable to retrieve file entry ", pe);

			return null;
		}
	}

	private String _getFileEntryTitle(FileEntry fileEntry, Html html) {
		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return html.escape(fileEntry.getTitle());
	}

	private String _getFileEntryURL(
		HttpServletRequest request, FileEntry fileEntry, Html html) {

		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(9);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathContext());

		sb.append("/documents/");
		sb.append(fileEntry.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(
			URLCodec.encodeURL(html.unescape(fileEntry.getTitle()), true));
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		return html.escape(sb.toString());
	}

	private String _getItemSelectorAuthToken(
		HttpServletRequest request, Portal portal) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		try {
			return AuthTokenUtil.getToken(
				request,
				portal.getControlPanelPlid(themeDisplay.getCompanyId()),
				PortletKeys.ITEM_SELECTOR);
		}
		catch (PortalException pe) {
			_log.error("Unable to generate item selector auth token ", pe);
		}

		return StringPool.BLANK;
	}

	private String _getLexiconIconsPath(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/lexicon/icons.svg");
		sb.append(StringPool.POUND);

		return sb.toString();
	}

	private ResourceBundle _getResourceBundle(Locale locale, Portal portal) {
		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			moduleResourceBundle, portalResourceBundle);
	}

	private JSONObject _getValueJSONObject(String value) {
		try {
			return JSONFactoryUtil.createJSONObject(value);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryFieldType.class);

}