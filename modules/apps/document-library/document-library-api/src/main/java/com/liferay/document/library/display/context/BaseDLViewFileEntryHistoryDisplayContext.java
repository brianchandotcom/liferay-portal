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
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mauro Mariuzzo
 */
public class BaseDLViewFileEntryHistoryDisplayContext
	extends BaseDLDisplayContext<DLViewFileEntryHistoryDisplayContext>
	implements DLViewFileEntryHistoryDisplayContext {

	public BaseDLViewFileEntryHistoryDisplayContext(
		UUID uuid,
		DLViewFileEntryHistoryDisplayContext
			parentDLViewFileEntryHistoryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		super(
			uuid, parentDLViewFileEntryHistoryDisplayContext,
			httpServletRequest, httpServletResponse);

		this.fileVersion = fileVersion;
	}

	@Override
	public Menu getMenu() throws PortalException {
		return parentDisplayContext.getMenu();
	}

	protected FileVersion fileVersion;

}