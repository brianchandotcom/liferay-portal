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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImageImpl;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/blog-posting-image.properties",
	scope = ServiceScope.PROTOTYPE, service = BlogPostingImageResource.class
)
public class BlogPostingImageResourceImpl
	extends BaseBlogPostingImageResourceImpl {

	@Override
	public BlogPostingImage getImageObject(Long imageObjectId)
		throws Exception {

		FileEntry fileEntry = _dlAppService.getFileEntry(imageObjectId);

		return _toBlogPostingImage(fileEntry, fileEntry.getFileVersion());
	}

	private BlogPostingImage _toBlogPostingImage(
		FileEntry fileEntry, FileVersion fileVersion) {

		return new BlogPostingImageImpl() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileVersion, null, "");
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

}