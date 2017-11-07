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

package com.liferay.adaptive.media.blogs.web.internal.blogs.util;

import com.liferay.blogs.util.BlogsEntryAttachmentContentUpdater;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=2",
	service = BlogsEntryAttachmentContentUpdater.class
)
public class AMBlogsEntryAttachmentContentUpdater
	extends BlogsEntryAttachmentContentUpdater {

	public AMBlogsEntryAttachmentContentUpdater() {
	}

	protected AMBlogsEntryAttachmentContentUpdater(
		PortletFileRepository portletFileRepository) {

		_portletFileRepository = portletFileRepository;
	}

	@Override
	protected String getBlogsEntryAttachmentFileEntryImgTag(
		FileEntry blogsEntryAttachmentFileEntry) {

		String fileEntryURL = _portletFileRepository.getPortletFileEntryURL(
			null, blogsEntryAttachmentFileEntry, StringPool.BLANK);

		return StringBundler.concat(
			"<img data-fileEntryId=\"",
			String.valueOf(blogsEntryAttachmentFileEntry.getFileEntryId()),
			"\" src=\"", fileEntryURL, "\" />");
	}

	@Reference
	private PortletFileRepository _portletFileRepository;

}