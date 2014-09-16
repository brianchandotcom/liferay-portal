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
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultDLEditFileEntryDisplayContext
	implements DLEditFileEntryDisplayContext {

	public DefaultDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {
	}

	public DefaultDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {
	}

	@Override
	public long getMaximumUploadSize() {
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}

		return fileMaxSize;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private static final UUID _UUID = UUID.fromString(
		"63326141-02F6-42B5-AE38-ABC73FA72BB5");

}