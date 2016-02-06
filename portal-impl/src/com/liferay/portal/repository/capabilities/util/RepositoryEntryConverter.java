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

package com.liferay.portal.repository.capabilities.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Iván Zaera
 */
public class RepositoryEntryConverter {

	public DLFileEntry getDLFileEntry(FileEntry fileEntry) {
		Object model = fileEntry.getModel();

		if (model instanceof DLFileEntry) {
			return (DLFileEntry)model;
		}

		throw new IllegalArgumentException(
			"Invalid file entry model " + model.getClass());
	}

}