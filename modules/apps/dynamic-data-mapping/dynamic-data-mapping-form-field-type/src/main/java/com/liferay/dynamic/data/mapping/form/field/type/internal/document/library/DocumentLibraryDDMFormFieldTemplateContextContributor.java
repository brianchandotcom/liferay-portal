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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=document_library",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		DocumentLibraryDDMFormFieldTemplateContextContributor.class
	}
)
public class DocumentLibraryDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		if (ddmFormFieldRenderingContext.isReadOnly() &&
			Validator.isNotNull(ddmFormFieldRenderingContext.getValue())) {

			JSONObject valueJSONObject = getValueJSONObject(
				ddmFormFieldRenderingContext.getValue());

			if ((valueJSONObject != null) && (valueJSONObject.length() > 0)) {
				FileEntry fileEntry = getFileEntry(valueJSONObject);

				parameters.put("fileEntryTitle", getFileEntryTitle(fileEntry));
				parameters.put(
					"fileEntryURL",
					getFileEntryURL(httpServletRequest, fileEntry));
			}
		}

		parameters.put(
			"groupId", ddmFormFieldRenderingContext.getProperty("groupId"));

		parameters.put(
			"itemSelectorAuthToken",
			getItemSelectorAuthToken(httpServletRequest));
		parameters.put(
			"lexiconIconsPath", getLexiconIconsPath(httpServletRequest));

		Locale displayLocale;

		if (ddmFormFieldRenderingContext.isViewMode()) {
			displayLocale = ddmFormFieldRenderingContext.getLocale();
		}
		else {
			displayLocale = getDisplayLocale(
				ddmFormFieldRenderingContext.getHttpServletRequest());
		}

		Map<String, String> stringsMap = HashMapBuilder.put(
			"select",
			LanguageUtil.get(getResourceBundle(displayLocale), "select")
		).build();

		parameters.put("strings", stringsMap);

		String value = ddmFormFieldRenderingContext.getValue();

		if (Validator.isNull(value)) {
			value = "{}";
		}

		parameters.put("value", jsonFactory.looseDeserialize(value));

		return parameters;
	}

	protected Locale getDisplayLocale(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getLocale();
	}

	protected FileEntry getFileEntry(JSONObject valueJSONObject) {
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

	protected String getFileEntryTitle(FileEntry fileEntry) {
		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return html.escape(fileEntry.getTitle());
	}

	protected String getFileEntryURL(
		HttpServletRequest httpServletRequest, FileEntry fileEntry) {

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
			URLCodec.encodeURL(html.unescape(fileEntry.getTitle()), true));
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		return html.escape(sb.toString());
	}

	protected String getItemSelectorAuthToken(
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
				portal.getControlPanelPlid(themeDisplay.getCompanyId()),
				PortletKeys.ITEM_SELECTOR);
		}
		catch (PortalException pe) {
			_log.error("Unable to generate item selector auth token ", pe);
		}

		return StringPool.BLANK;
	}

	protected String getLexiconIconsPath(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/lexicon/icons.svg");
		sb.append(StringPool.POUND);

		return sb.toString();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			moduleResourceBundle, portalResourceBundle);
	}

	protected JSONObject getValueJSONObject(String value) {
		try {
			return jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return null;
		}
	}

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected Html html;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryDDMFormFieldTemplateContextContributor.class);

}