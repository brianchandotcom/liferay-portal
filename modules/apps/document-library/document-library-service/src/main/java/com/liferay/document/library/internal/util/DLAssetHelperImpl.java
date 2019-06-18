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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.util.DLAssetHelper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLAssetHelper.class)
public class DLAssetHelperImpl implements DLAssetHelper {

	@Override
	public long getAssetClassPK(FileEntry fileEntry, FileVersion fileVersion) {
		if (fileEntry == null) {
			return 0;
		}

		if (fileVersion == null) {
			return fileEntry.getFileEntryId();
		}

		String version = fileVersion.getVersion();

		if (fileVersion.isApproved() || Validator.isNull(version) ||
			version.equals(DLFileEntryConstants.VERSION_DEFAULT) ||
			fileEntry.isInTrash()) {

			return fileEntry.getFileEntryId();
		}

		return fileVersion.getFileVersionId();
	}

}