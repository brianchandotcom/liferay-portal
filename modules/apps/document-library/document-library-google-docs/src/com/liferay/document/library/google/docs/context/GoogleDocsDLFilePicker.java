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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.FreeMarkerRenderer;
import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.context.DLFilePicker;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import freemarker.template.TemplateException;

import java.io.IOException;

import javax.portlet.PortletPreferences;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class GoogleDocsDLFilePicker implements DLFilePicker {

	public GoogleDocsDLFilePicker(
		GoogleDocsMetadataHelper googleDocsMetadataHelper,
		ThemeDisplay themeDisplay, String namespace,
		String onFilePickCallback) {

		_googleDocsMetadataHelper = googleDocsMetadataHelper;
		_namespace = namespace;
		_onFilePickCallback = onFilePickCallback;
		_themeDisplay = themeDisplay;
	}

	@Override
	public DDMStructure getDDMStructure() throws PortalException {
		return _googleDocsMetadataHelper.getDDMStructure();
	}

	@Override
	public String getDescriptionFieldName() throws PortalException {
		return GoogleDocsConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public String getIconFieldName() throws PortalException {
		return GoogleDocsConstants.DDM_FIELD_NAME_ICON;
	}

	@Override
	public String getJavascript() throws PortalException {
		try {
			FreeMarkerRenderer freeMarkerRenderer = new FreeMarkerRenderer(
				"com/liferay/document/library/google/docs/context/"+
					"dependencies/google_file_picker.ftl");

			PortletPreferences companyPortletPreferences =
				PrefsPropsUtil.getPreferences(_themeDisplay.getCompanyId());

			String googleAppsAPIKey = companyPortletPreferences.getValue(
				"googleAppsAPIKey", null);
			String googleClientId = companyPortletPreferences.getValue(
				"googleClientId", null);

			freeMarkerRenderer.setAttribute(
				"googleAppsAPIKey", googleAppsAPIKey);
			freeMarkerRenderer.setAttribute("googleClientId", googleClientId);

			freeMarkerRenderer.setAttribute("namespace", _namespace);

			freeMarkerRenderer.setAttribute(
				"onFilePickCallback", _onFilePickCallback);

			return freeMarkerRenderer.render();
		}
		catch (IOException | TemplateException e) {
			throw new PortalException(e);
		}
	}

	@Override
	public String getOnClickCallback() throws PortalException {
		return _namespace + "openPicker";
	}

	@Override
	public String getTitleFieldName() throws PortalException {
		return GoogleDocsConstants.DDM_FIELD_NAME_TITLE;
	}

	private GoogleDocsMetadataHelper _googleDocsMetadataHelper;
	private String _namespace;
	private String _onFilePickCallback;
	private ThemeDisplay _themeDisplay;

}