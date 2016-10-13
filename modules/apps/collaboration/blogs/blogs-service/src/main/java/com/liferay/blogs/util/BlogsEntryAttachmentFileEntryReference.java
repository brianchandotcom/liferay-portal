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

package com.liferay.blogs.util;

import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class BlogsEntryAttachmentFileEntryReference {

	public BlogsEntryAttachmentFileEntryReference(
		long tempBlogsEntryAttachmentFileEntryId,
		FileEntry blogsEntryAttachmentFileEntry) {

		_tempBlogsEntryAttachmentFileEntryId =
			tempBlogsEntryAttachmentFileEntryId;
		_blogsEntryAttachmentFileEntry = blogsEntryAttachmentFileEntry;
	}

	public FileEntry getBlogsEntryAttachmentFileEntry() {
		return _blogsEntryAttachmentFileEntry;
	}

	public long getTempBlogsEntryAttachmentFileEntryId() {
		return _tempBlogsEntryAttachmentFileEntryId;
	}

	private final FileEntry _blogsEntryAttachmentFileEntry;
	private final long _tempBlogsEntryAttachmentFileEntryId;

}