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

package com.liferay.portlet.imagegallerydisplay.display.context;

import com.liferay.portal.kernel.display.context.DisplayContextFactory;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Zaera
 */
public interface IGDisplayContextFactory extends DisplayContextFactory {

	public IGViewFileVersionDisplayContext
		getIGViewFileVersionDisplayContext(
			IGViewFileVersionDisplayContext
				parentIGViewFileVersionDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut);

	public IGViewFileVersionDisplayContext
		getIGViewFileVersionDisplayContext(
			IGViewFileVersionDisplayContext
				parentIGViewFileVersionDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion);

}