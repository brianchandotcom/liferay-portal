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

package com.liferay.adaptive.media.blogs.web.internal.attachment;

import com.liferay.blogs.util.BlogsEntryAttachmentContentUpdater;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=2",
	service = BlogsEntryAttachmentContentUpdater.class
)
public class AMBlogsEntryAttachmentContentUpdater
	extends BlogsEntryAttachmentContentUpdater {

	@Override
	protected String getBlogsEntryAttachmentFileEntryImgTag(
		FileEntry blogsEntryAttachmentFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, blogsEntryAttachmentFileEntry, StringPool.BLANK);

		return "<img data-fileEntryId=\"" +
			blogsEntryAttachmentFileEntry.getFileEntryId() + "\" src=\"" +
				fileEntryURL + "\" />";
	}

}