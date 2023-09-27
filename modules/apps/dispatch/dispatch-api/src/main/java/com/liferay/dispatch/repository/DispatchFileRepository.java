/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.InputStream;

/**
 * @author Igor Beslic
 */
public interface DispatchFileRepository {

	public FileEntry addFileEntry(
			long userId, long dispatchTriggerId, String fileName, long size,
			String contentType, InputStream inputStream)
		throws PortalException;

	public FileEntry fetchFileEntry(long dispatchTriggerId);

	public String fetchFileEntryName(long dispatchTriggerId);

}