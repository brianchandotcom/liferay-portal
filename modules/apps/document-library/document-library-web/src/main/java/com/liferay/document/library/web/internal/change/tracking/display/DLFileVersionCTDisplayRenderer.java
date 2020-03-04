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

package com.liferay.document.library.web.internal.change.tracking.display;

import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileVersion",
	service = CTDisplayRenderer.class
)
public class DLFileVersionCTDisplayRenderer
	implements CTDisplayRenderer<DLFileVersion> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest, DLFileVersion dlFileVersion) {

		return null;
	}

	@Override
	public Class<DLFileVersion> getModelClass() {
		return DLFileVersion.class;
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, DLFileVersionCTDisplayRenderer.class);

		return _language.get(
			resourceBundle,
			"model.resource.com.liferay.document.library.kernel.model." +
				"DLFileVersion");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DLFileVersion dlFileVersion)
		throws Exception {

		FileEntry fileEntry = _dlAppService.getFileEntry(
			dlFileVersion.getFileEntryId());

		httpServletRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, fileEntry);

		FileVersion fileVersion = _dlAppService.getFileVersion(
			dlFileVersion.getFileVersionId());

		httpServletRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/document_library/ct_display/render_file_version.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.web)"
	)
	private ServletContext _servletContext;

}