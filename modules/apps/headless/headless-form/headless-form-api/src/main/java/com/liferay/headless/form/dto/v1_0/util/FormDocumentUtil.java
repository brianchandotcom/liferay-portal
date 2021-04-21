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

package com.liferay.headless.form.dto.v1_0.util;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Victor Oliveira
 */
public class FormDocumentUtil {

	public static FormDocument toFormDocument(
			DLURLHelper dlurlHelper, FileEntry fileEntry)
		throws Exception {

		return new FormDocument() {
			{
				contentUrl = dlurlHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "");
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				siteId = fileEntry.getGroupId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

}