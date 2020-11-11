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
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ParamUtil;
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

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

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

		parameters.put(
			"allowGuestUsers",
			GetterUtil.getBoolean(ddmFormField.getProperty("allowGuestUsers")));

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		if (Validator.isNotNull(ddmFormFieldRenderingContext.getValue())) {
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

		long folderId = 0;

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		User user = themeDisplay.getUser();

		if (!user.isDefaultUser()) {
			try {
				folderId = _getFolderId(
					httpServletRequest, ddmFormFieldRenderingContext);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to retrieve private uploads folder of user " +
							themeDisplay.getUserId(),
						portalException);
				}
			}
		}

		parameters.put("folderId", folderId);

		parameters.put(
			"groupId", ddmFormFieldRenderingContext.getProperty("groupId"));

		parameters.put(
			"itemSelectorAuthToken",
			getItemSelectorAuthToken(httpServletRequest));
		parameters.put(
			"lexiconIconsPath", getLexiconIconsPath(httpServletRequest));
		parameters.put(
			"maximumRepetitions",
			GetterUtil.getInteger(
				ddmFormField.getProperty("maximumRepetitions")));

		Locale displayLocale;

		if (ddmFormFieldRenderingContext.isViewMode()) {
			displayLocale = ddmFormFieldRenderingContext.getLocale();
		}
		else {
			displayLocale = getDisplayLocale(
				ddmFormFieldRenderingContext.getHttpServletRequest());
		}

		Map<String, String> stringsMap = new HashMap<>();

		stringsMap.put(
			"select",
			LanguageUtil.get(getResourceBundle(displayLocale), "select"));

		parameters.put("strings", stringsMap);

		parameters.put(
			"uploadURL",
			getUploadURL(ddmFormFieldRenderingContext, httpServletRequest));

		String value = ddmFormFieldRenderingContext.getValue();

		if (Validator.isNull(value)) {
			value = "{}";
		}

		parameters.put("value", value);

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
		catch (PortalException portalException) {
			_log.error("Unable to retrieve file entry ", portalException);

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(9);

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
		catch (PortalException portalException) {
			_log.error(
				"Unable to generate item selector auth token ",
				portalException);
		}

		return StringPool.BLANK;
	}

	protected String getLexiconIconsPath(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/clay/icons.svg");
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

	protected ThemeDisplay getThemeDisplay(
		HttpServletRequest httpServletRequest) {

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected String getUploadURL(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL portletURL = requestBackedPortletURLFactory.createActionURL(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/dynamic_data_mapping_form/upload_file_entry");
		portletURL.setParameter(
			"formInstanceId",
			ParamUtil.getString(httpServletRequest, "formInstanceId"));
		portletURL.setParameter(
			"groupId",
			String.valueOf(
				ddmFormFieldRenderingContext.getProperty("groupId")));

		return portletURL.toString();
	}

	protected JSONObject getValueJSONObject(String value) {
		try {
			return jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
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

	private long _getFolderId(
			HttpServletRequest httpServletRequest,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		Folder folder;

		Long groupId = GetterUtil.getLong(
			ddmFormFieldRenderingContext.getProperty("groupId"));

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		User user = themeDisplay.getUser();

		try {
			folder = dlAppService.getFolder(
				groupId, 0, String.valueOf(user.getUserId()));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"The user " + user.getUserId() +
						" does not have a private uploads folder",
					portalException);
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			folder = dlAppService.addFolder(
				groupId, 0, String.valueOf(user.getUserId()),
				LanguageUtil.get(
					getResourceBundle(user.getLocale()),
					"this-folder-was-automatically-created-by-forms-to-store-" +
						"all-your-uploaded-files"),
				serviceContext);
		}

		return folder.getFolderId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryDDMFormFieldTemplateContextContributor.class);

}