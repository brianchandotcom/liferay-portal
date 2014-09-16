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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseDLEditFileEntryDisplayContext
	extends BaseDLDisplayContext<DLEditFileEntryDisplayContext>
	implements DLEditFileEntryDisplayContext {

	public BaseDLEditFileEntryDisplayContext(
		UUID uuid,
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		super(uuid, parentDLEditFileEntryDisplayContext, request, response);

		this.dlFileEntryType = dlFileEntryType;
	}

	public BaseDLEditFileEntryDisplayContext(
		UUID uuid,
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		super(uuid, parentDLEditFileEntryDisplayContext, request, response);

		this.fileEntry = fileEntry;

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			try {
				this.dlFileEntryType = dlFileEntry.getDLFileEntryType();
			}
			catch (PortalException pe) {
				throw new SystemException(pe);
			}
		}
	}

	@Override
	public long getMaximumUploadSize() {
		return parentDLDisplayContext.getMaximumUploadSize();
	}

	protected DLFileEntryType dlFileEntryType;
	protected FileEntry fileEntry;

}