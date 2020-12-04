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

package com.liferay.document.library.external.video.internal.display.context;

import com.liferay.document.library.display.context.DLFilePicker;
import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 * @author Sergio González
 * @author Alejandro Tardín
 */
public class DLExternalVideoDLFilePicker implements DLFilePicker {

	public DLExternalVideoDLFilePicker(
		DLExternalVideo dlExternalVideo, String onFilePickCallback,
		ServletContext servletContext) {

		_dlExternalVideo = dlExternalVideo;
		_onFilePickCallback = onFilePickCallback;
		_servletContext = servletContext;
	}

	@Override
	public String getCurrentIconURL() {
		return StringPool.BLANK;
	}

	@Override
	public String getCurrentTitle() {
		if (_dlExternalVideo != null) {
			return GetterUtil.getString(
				_dlExternalVideo.getTitle(), _dlExternalVideo.getURL());
		}

		return StringPool.BLANK;
	}

	@Override
	public String getDescriptionFieldName() {
		return DLExternalVideoConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public String getIconFieldName() {
		return null;
	}

	@Override
	public String getJavaScript() {
		return null;
	}

	@Override
	public String getJavaScriptModuleName() {
		return null;
	}

	@Override
	public String getOnClickCallback() {
		return null;
	}

	@Override
	public String getTitleFieldName() {
		return DLExternalVideoConstants.DDM_FIELD_NAME_TITLE;
	}

	@Override
	public boolean isCustomizedFileButtonVisible() {
		return false;
	}

	@Override
	public void renderFilePicker(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/file_picker.jsp");

		httpServletRequest.setAttribute(
			DLExternalVideo.class.getName(), _dlExternalVideo);
		httpServletRequest.setAttribute(
			DLExternalVideoWebKeys.ON_FILE_PICK_CALLBACK, _onFilePickCallback);

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	private final DLExternalVideo _dlExternalVideo;
	private final String _onFilePickCallback;
	private final ServletContext _servletContext;

}