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

package com.liferay.portal.repository.capabilities;

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Iván Zaera
 */
public interface WorkflowSupport {

	public void addFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, FileEntry, DLVersionNumberIncrease,
	 *             ServiceContext)}
	 */
	@Deprecated
	public void checkInFileEntry(
			long userId, FileEntry fileEntry, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException;

	public default void checkInFileEntry(
			long userId, FileEntry fileEntry,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			ServiceContext serviceContext)
		throws PortalException {

		boolean majorVersion = false;

		if (dlVersionNumberIncrease == DLVersionNumberIncrease.MAJOR) {
			majorVersion = true;
		}

		checkInFileEntry(userId, fileEntry, majorVersion, serviceContext);
	}

	public void revertFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, FileEntry, DLVersionNumberIncrease,
	 *             ServiceContext)}
	 */
	@Deprecated
	public void updateFileEntry(
			long userId, FileEntry fileEntry, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException;

	public default void updateFileEntry(
			long userId, FileEntry fileEntry,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			ServiceContext serviceContext)
		throws PortalException {

		boolean majorVersion = false;

		if (dlVersionNumberIncrease == DLVersionNumberIncrease.MAJOR) {
			majorVersion = true;
		}

		updateFileEntry(userId, fileEntry, majorVersion, serviceContext);
	}

}