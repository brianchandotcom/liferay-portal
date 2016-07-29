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
import com.liferay.document.library.google.docs.internal.util.FreeMarkerRenderer;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsConfigurationHelper;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class GoogleDocsDLFilePicker implements DLFilePicker {

	public GoogleDocsDLFilePicker(
		String namespace, String onFilePickCallback,
		ThemeDisplay themeDisplay) {

		_namespace = namespace;
		_onFilePickCallback = onFilePickCallback;

		_googleDocsConfigurationHelper = new GoogleDocsConfigurationHelper(
			themeDisplay.getCompanyId());
	}

	@Override
	public String getDescriptionFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public String getIconFieldName() {
		return GoogleDocsConstants.DDM_FIELD_NAME_ICON_URL;
	}

	@Override
	public String getJavaScript() throws PortalException {
		try {
			FreeMarkerRenderer freeMarkerRenderer = new FreeMarkerRenderer(
				"com/liferay/document/library/google/docs/internal/display/" +
					"context/dependencies/google_file_picker.ftl");

			freeMarkerRenderer.setAttribute(
				"googleAppsAPIKey",
				_googleDocsConfigurationHelper.getGoogleAppsAPIKey());
			freeMarkerRenderer.setAttribute(
				"googleClientId",
				_googleDocsConfigurationHelper.getGoogleClientId());
			freeMarkerRenderer.setAttribute("namespace", _namespace);
			freeMarkerRenderer.setAttribute(
				"onFilePickCallback", _onFilePickCallback);

			return freeMarkerRenderer.render();
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
		catch (TemplateException te) {
			throw new PortalException(te);
		}
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
		return GoogleDocsConstants.DDM_FIELD_NAME_NAME;
	}

	private final GoogleDocsConfigurationHelper _googleDocsConfigurationHelper;
	private final String _namespace;
	private final String _onFilePickCallback;

}