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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.context.BaseDLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.context.FilePickerCustomizer;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import freemarker.template.TemplateException;

import java.io.IOException;

import java.util.UUID;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Zaera
 */
public class GoogleDocsDLEditFileEntryDisplayContext
	extends BaseDLEditFileEntryDisplayContext
	implements FilePickerCustomizer {

	public GoogleDocsDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		super(
			_UUID, parentDLEditFileEntryDisplayContext, request, response,
			dlFileEntryType);

		_googleDocsMetadataHelper = new GoogleDocsMetadataHelper(
			dlFileEntryType);
	}

	public GoogleDocsDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		super(
			_UUID, parentDLEditFileEntryDisplayContext, request, response,
			fileEntry);

		_googleDocsMetadataHelper = new GoogleDocsMetadataHelper(
			(DLFileEntry)fileEntry.getModel());
	}

	@Override
	public DDMStructure getDDMStructure() {
		return _googleDocsMetadataHelper.getDDMStructure();
	}

	@Override
	public String getDescriptionFieldName() throws PortalException {
		return GoogleDocsConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public FilePickerCustomizer getFilePickerCustomizer()
		throws PortalException {

		return this;
	}

	@Override
	public String getIconFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_ICON;
	}

	@Override
	public String getJavascript() throws PortalException {
		try {
			FreeMarkerRenderer freeMarkerRenderer = new FreeMarkerRenderer(
				"com/liferay/document/library/google/docs/context/"+
				"dependencies/google_file_picker.ftl");

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletPreferences companyPortletPreferences =
				PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId());

			String googleAppsAPIKey = companyPortletPreferences.getValue(
				"googleAppsAPIKey", null);
			String googleClientId = companyPortletPreferences.getValue(
				"googleClientId", null);

			freeMarkerRenderer.setAttribute(
				"filePickCallback", _filePickCallback);
			freeMarkerRenderer.setAttribute(
				"googleAppsAPIKey", googleAppsAPIKey);
			freeMarkerRenderer.setAttribute("googleClientId", googleClientId);

			freeMarkerRenderer.setAttribute("namespace", _getNamespace());

			return freeMarkerRenderer.render();
		}
		catch (IOException | TemplateException e) {
			throw new PortalException(e);
		}
	}

	@Override
	public long getMaximumUploadSize() {
		return 0;
	}

	@Override
	public String getOnClickCallback() {
		return _getNamespace() + "openPicker";
	}

	@Override
	public String getTitleFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_TITLE;
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure)
		throws PortalException {

		String ddmStructureKey = ddmStructure.getStructureKey();

		if (ddmStructureKey.equals(
				GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

			return false;
		}

		return super.isDDMStructureVisible(ddmStructure);
	}

	@Override
	public void setOnFilePickCallback(String filePickCallback) {
		_filePickCallback = filePickCallback;
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse =
			(PortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private String _getNamespace() {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		return liferayPortletResponse.getNamespace();
	}

	private static final UUID _UUID = UUID.fromString(
		"62BE5287-BEA3-4E3F-9731-15B1B901380D");

	private String _filePickCallback;
	private GoogleDocsMetadataHelper _googleDocsMetadataHelper;

}