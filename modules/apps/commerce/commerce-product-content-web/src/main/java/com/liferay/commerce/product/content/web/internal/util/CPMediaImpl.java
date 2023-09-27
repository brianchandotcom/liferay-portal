/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.media.CommerceMediaResolverUtil;
import com.liferay.commerce.product.content.util.CPMedia;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.document.library.util.DLURLHelperUtil;
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

		String defaultURL = DLURLHelperUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);

		_downloadURL = defaultURL;

		_id = fileEntry.getFileEntryId();
		_mimeType = fileEntry.getMimeType();
		_size = 0;
		_thumbnailURL = defaultURL;
		_title = fileEntry.getTitle();
		_url = defaultURL;
	}

	public CPMediaImpl(long groupId) throws PortalException {
		String defaultURL = CommerceMediaResolverUtil.getDefaultURL(groupId);

		_downloadURL = defaultURL;

		_id = 0;
		_mimeType = null;
		_size = 0;
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
			_size = 0;
		}
		else {
			_mimeType = fileEntry.getMimeType();
			_size = fileEntry.getSize();
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
	public long getSize() {
		return _size;
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
	private final long _size;
	private final String _thumbnailURL;
	private final String _title;
	private final String _url;

}