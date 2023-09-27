/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.capabilities;

import java.util.UUID;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntriesScope {

	public TemporaryFileEntriesScope(
		UUID callerUUID, long userId, String folderPath) {

		_callerUUID = callerUUID;
		_userId = userId;
		_folderPath = folderPath;
	}

	public UUID getCallerUuid() {
		return _callerUUID;
	}

	public String getFolderPath() {
		return _folderPath;
	}

	public long getUserId() {
		return _userId;
	}

	private final UUID _callerUUID;
	private final String _folderPath;
	private final long _userId;

}