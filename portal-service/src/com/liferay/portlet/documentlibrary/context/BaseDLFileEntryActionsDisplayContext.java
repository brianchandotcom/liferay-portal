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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public abstract class BaseDLFileEntryActionsDisplayContext
	extends BaseDLDisplayContext<DLFileEntryActionsDisplayContext>
	implements DLFileEntryActionsDisplayContext {

	public BaseDLFileEntryActionsDisplayContext(
		UUID uuid, DLFileEntryActionsDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry, FileVersion fileVersion) {

		super(uuid, parentDLDisplayContext, request, response);

		this.fileEntry = fileEntry;
		this.fileVersion = fileVersion;
	}

	protected FileEntry fileEntry;
	protected FileVersion fileVersion;

}