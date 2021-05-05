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

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.delivery.dto.v1_0.ContentDocument;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class ContentDocumentUtil {

	public static ContentDocument toContentDocument(
			DLURLHelper dlURLHelper, String fieldName, FileEntry fileEntry,
			Optional<UriInfo> uriInfoOptional)
		throws Exception {

		return new ContentDocument() {
			{
				contentType = "Document";
				contentUrl = dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				contentValue = ContentValueUtil.toContentValue(
					fieldName + ".contentValue", fileEntry::getContentStream,
					uriInfoOptional);
				description = fileEntry.getDescription();
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

}