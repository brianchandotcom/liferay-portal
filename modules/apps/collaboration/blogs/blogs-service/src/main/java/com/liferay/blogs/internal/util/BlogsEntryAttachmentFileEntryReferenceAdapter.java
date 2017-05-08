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

package com.liferay.blogs.internal.util;

import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Alejandro Tardín
 */
public class BlogsEntryAttachmentFileEntryReferenceAdapter
	extends com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryReference {

	public BlogsEntryAttachmentFileEntryReferenceAdapter(
		long tempBlogsEntryAttachmentFileEntryId,
		FileEntry blogsEntryAttachmentFileEntry) {

		super(
			tempBlogsEntryAttachmentFileEntryId, blogsEntryAttachmentFileEntry);

		_tempBlogsEntryAttachmentFileEntryId =
			tempBlogsEntryAttachmentFileEntryId;
		_blogsEntryAttachmentFileEntry = blogsEntryAttachmentFileEntry;
	}

	@Override
	public FileEntry getBlogsEntryAttachmentFileEntry() {
		return _blogsEntryAttachmentFileEntry;
	}

	@Override
	public long getTempBlogsEntryAttachmentFileEntryId() {
		return _tempBlogsEntryAttachmentFileEntryId;
	}

	private final FileEntry _blogsEntryAttachmentFileEntry;
	private final long _tempBlogsEntryAttachmentFileEntryId;

}