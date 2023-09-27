/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryModelOperation {

	public void execute(FileEntry fileEntry) throws PortalException;

	public void execute(FileShortcut fileShortcut) throws PortalException;

	public void execute(FileVersion fileVersion) throws PortalException;

	public void execute(Folder folder) throws PortalException;

}