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

package com.liferay.document.library.google.docs.internal.display.context;

import com.liferay.document.library.display.context.DLFilePicker;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsConfigurationHelper;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsMetadataHelper;
import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class GoogleDocsDLFilePicker implements DLFilePicker {

	public GoogleDocsDLFilePicker(
			GoogleDocsMetadataHelper googleDocsMetadataHelper, String namespace,
			String onFilePickCallback, ThemeDisplay themeDisplay)
		throws PortalException {

		_googleDocsMetadataHelper = googleDocsMetadataHelper;
		_namespace = namespace;
		_onFilePickCallback = onFilePickCallback;

		_googleDocsConfigurationHelper = new GoogleDocsConfigurationHelper(
			themeDisplay.getCompanyId());
	}

	@Override
	public String getCurrentIconURL() {
		if (_googleDocsMetadataHelper != null) {
			return _googleDocsMetadataHelper.getFieldValue(getIconFieldName());
		}

		return StringPool.BLANK;
	}

	@Override
	public String getCurrentTitle() {
		if (_googleDocsMetadataHelper != null) {
			return _googleDocsMetadataHelper.getFieldValue(getTitleFieldName());
		}

		return StringPool.BLANK;
	}

	@Override
	public String getDescriptionFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public String getFileNameFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_NAME;
	}

	@Override
	public String getIconFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_ICON_URL;
	}

	@Override
	public String getJavaScript() throws PortalException {
		String templateId =
			"/com/liferay/document/library/google/docs/internal/display" +
				"/context/dependencies/google_file_picker.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource templateResource = new URLTemplateResource(
			templateId, clazz.getResource(templateId));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		template.put(
			"googleAppsAPIKey",
			_googleDocsConfigurationHelper.getGoogleAppsAPIKey());
		template.put(
			"googleClientId",
			_googleDocsConfigurationHelper.getGoogleClientId());
		template.put("htmlUtil", HtmlUtil.getHtml());
		template.put("namespace", _namespace);
		template.put("onFilePickCallback", _onFilePickCallback);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	@Override
	public String getJavaScriptModuleName() {
		return "FilePicker";
	}

	@Override
	public String getOnClickCallback() {
		return "openPicker";
	}

	@Override
	public String getTitleFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_TITLE;
	}

	private final GoogleDocsConfigurationHelper _googleDocsConfigurationHelper;
	private final GoogleDocsMetadataHelper _googleDocsMetadataHelper;
	private final String _namespace;
	private final String _onFilePickCallback;

}