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

package com.liferay.sync.engine.documentlibrary.handler;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.SyncFile;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class CopyFileEntryHandler extends AddFileFolderHandler {

	public CopyFileEntryHandler(Event event) {
		super(event);
	}

	@Override
	public boolean handlePortalException(String exception) throws Exception {
		if (exception.isEmpty()) {
			return false;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("Handling exception {}", exception);
		}

		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		FileEventUtil.addFile(
			Paths.get(syncFile.getFilePathName()), syncFile.getParentFolderId(),
			syncFile.getRepositoryId(), getSyncAccountId(),
			syncFile.getChecksum(), syncFile.getName(), syncFile.getMimeType(),
			syncFile);

		return true;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		CopyFileEntryHandler.class);

}