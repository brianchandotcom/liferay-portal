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

package com.liferay.document.library.display.context;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public interface DLFilePicker {

	public default String getCurrentIconURL() {
		return null;
	}

	public default String getCurrentTitle() {
		return null;
	}

	public String getDescriptionFieldName();

	public default String getFileNameFieldName() {
		return null;
	}

	public String getIconFieldName();

	public String getJavaScript() throws PortalException;

	public String getJavaScriptModuleName();

	public String getOnClickCallback();

	public String getTitleFieldName();

	public default boolean isCustomizedFileButtonVisible() {
		return true;
	}

	public default void renderFilePicker(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {
	}

}