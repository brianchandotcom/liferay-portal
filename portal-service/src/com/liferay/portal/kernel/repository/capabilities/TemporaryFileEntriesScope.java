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

package com.liferay.portal.kernel.repository.capabilities;

import java.util.UUID;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntriesScope {

	public TemporaryFileEntriesScope(
		UUID callerUuid, long userId, String folderPath) {

		_callerUuid = callerUuid;
		_userId = userId;
		_folderPath = folderPath;
	}

	public UUID getCallerUuid() {
		return _callerUuid;
	}

	public String getFolderPath() {
		return _folderPath;
	}

	public long getUserId() {
		return _userId;
	}

	private final UUID _callerUuid;
	private final String _folderPath;
	private final long _userId;

}