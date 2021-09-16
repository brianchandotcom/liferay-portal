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

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.media.CommerceMediaResolverUtil;
import com.liferay.commerce.product.content.util.CPMedia;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Marco Leo
 */
public class CPMediaImpl implements CPMedia {

	public CPMediaImpl(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		String defaultURL = DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);

		_downloadURL = defaultURL;

		_id = fileEntry.getFileEntryId();
		_url = defaultURL;
		_thumbnailURL = defaultURL;
		_mimeType = fileEntry.getMimeType();
		_title = fileEntry.getTitle();
	}

	public CPMediaImpl(long groupId) throws PortalException {
		String defaultURL = CommerceMediaResolverUtil.getDefaultURL(groupId);

		_downloadURL = defaultURL;

		_id = 0;
		_mimeType = null;
		_thumbnailURL = defaultURL;
		_title = null;
		_url = defaultURL;
	}

	public CPMediaImpl(
			long commerceAccountId, CPAttachmentFileEntry cpAttachmentFileEntry,
			ThemeDisplay themeDisplay)
		throws PortalException {

		_downloadURL = CommerceMediaResolverUtil.getDownloadURL(
			commerceAccountId,
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
		_id = cpAttachmentFileEntry.getCPAttachmentFileEntryId();

		FileEntry fileEntry = cpAttachmentFileEntry.fetchFileEntry();

		if (fileEntry == null) {
			_mimeType = StringPool.BLANK;
		}
		else {
			_mimeType = fileEntry.getMimeType();
		}

		_thumbnailURL = CommerceMediaResolverUtil.getThumbnailURL(
			commerceAccountId,
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
		_title = cpAttachmentFileEntry.getTitle(themeDisplay.getLanguageId());
		_url = CommerceMediaResolverUtil.getURL(
			commerceAccountId,
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	@Override
	public String getDownloadURL() {
		return _downloadURL;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public String getThumbnailURL() {
		return _thumbnailURL;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public String mimeType() {
		return _mimeType;
	}

	private final String _downloadURL;
	private final long _id;
	private final String _mimeType;
	private final String _thumbnailURL;
	private final String _title;
	private final String _url;

}