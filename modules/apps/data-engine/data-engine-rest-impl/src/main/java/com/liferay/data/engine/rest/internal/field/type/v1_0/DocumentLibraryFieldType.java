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

import com.liferay.data.engine.field.type.BaseFieldType;
import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.description=document-library-field-type-description",
		"data.engine.field.type.display.order:Integer=9",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=upload",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/DocumentLibrary/DocumentLibrary.es",
		"data.engine.field.type.label=document-library-field-type-label"
	},
	service = FieldType.class
)
public class DocumentLibraryFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put("groupId", jsonObject.getLong("groupId"));
		customProperties.put(
			"itemSelectorAuthToken",
			jsonObject.getString("itemSelectorAuthToken"));
		customProperties.put(
			"lexiconIconsPath", jsonObject.getString("lexiconIconsPath"));
		customProperties.put(
			"strings",
			CustomPropertiesUtil.toMap(jsonObject.getJSONObject("strings")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "document_library";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		return jsonObject.put(
			"groupId",
			MapUtil.getLong(
				spiDataDefinitionField.getCustomProperties(), "groupId")
		).put(
			"itemSelectorAuthToken",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(),
				"itemSelectorAuthToken")
		).put(
			"lexiconIconsPath",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(),
				"lexiconIconsPath")
		).put(
			"strings",
			CustomPropertiesUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "strings"))
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		if (!StringUtils.isEmpty(
				MapUtil.getString(
					spiDataDefinitionField.getCustomProperties(), "value"))) {

			JSONObject valueJSONObject = _toJSONObject(
				CustomPropertiesUtil.getString(
					spiDataDefinitionField.getCustomProperties(), "value"));

			if ((valueJSONObject != null) && (valueJSONObject.length() > 0)) {
				FileEntry fileEntry = _getFileEntry(valueJSONObject);

				context.put("fileEntryTitle", _getFileEntryTitle(fileEntry));
				context.put(
					"fileEntryURL",
					_getFileEntryURL(fileEntry, httpServletRequest));
			}
		}

		context.put(
			"groupId",
			MapUtil.getLong(
				spiDataDefinitionField.getCustomProperties(), "groupId"));
		context.put(
			"itemSelectorAuthToken",
			_getItemSelectorAuthToken(httpServletRequest));
		context.put(
			"lexiconIconsPath", _getLexiconIconsPath(httpServletRequest));
		context.put("strings", _getStrings(httpServletRequest));
		context.put(
			"value",
			JSONFactoryUtil.looseDeserialize(
				MapUtil.getString(
					spiDataDefinitionField.getCustomProperties(), "value",
					"{}")));
	}

	private FileEntry _getFileEntry(JSONObject valueJSONObject) {
		try {
			return _dlAppService.getFileEntryByUuidAndGroupId(
				valueJSONObject.getString("uuid"),
				valueJSONObject.getLong("groupId"));
		}
		catch (PortalException pe) {
			_log.error("Unable to retrieve file entry ", pe);

			return null;
		}
	}

	private String _getFileEntryTitle(FileEntry fileEntry) {
		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return _html.escape(fileEntry.getTitle());
	}

	private String _getFileEntryURL(
		FileEntry fileEntry, HttpServletRequest httpServletRequest) {

		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(9);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathContext());

		sb.append("/documents/");
		sb.append(fileEntry.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(
			URLCodec.encodeURL(_html.unescape(fileEntry.getTitle()), true));
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		return _html.escape(sb.toString());
	}

	private String _getItemSelectorAuthToken(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		try {
			return AuthTokenUtil.getToken(
				httpServletRequest,
				_portal.getControlPanelPlid(themeDisplay.getCompanyId()),
				PortletKeys.ITEM_SELECTOR);
		}
		catch (PortalException pe) {
			_log.error("Unable to generate item selector auth token ", pe);
		}

		return StringPool.BLANK;
	}

	private String _getLexiconIconsPath(HttpServletRequest httpServletRequest) {
		StringBundler sb = new StringBundler(3);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathThemeImages());

		sb.append("/lexicon/icons.svg");
		sb.append(StringPool.POUND);

		return sb.toString();
	}

	private Map<String, String> _getStrings(
		HttpServletRequest httpServletRequest) {

		Map<String, String> values = new HashMap<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass()),
			_portal.getResourceBundle(themeDisplay.getLocale()));

		values.put("select", LanguageUtil.get(resourceBundle, "select"));

		return values;
	}

	private JSONObject _toJSONObject(String string) {
		try {
			return JSONFactoryUtil.createJSONObject(string);
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

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Html _html;

	@Reference
	private Portal _portal;

}