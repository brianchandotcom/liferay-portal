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