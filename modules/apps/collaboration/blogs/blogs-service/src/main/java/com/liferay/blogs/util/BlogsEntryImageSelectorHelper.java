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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.FileUtil;

/**
 * @author Roberto Díaz
 */
public class BlogsEntryImageSelectorHelper {

	public BlogsEntryImageSelectorHelper(
		long imageFileEntryId, long oldImageFileEntryId, String imageCropRegion,
		String imageURL, String oldImageURL) {

		_imageFileEntryId = imageFileEntryId;
		_oldImageFileEntryId = oldImageFileEntryId;
		_imageCropRegion = imageCropRegion;
		_imageURL = imageURL;
		_oldImageURL = oldImageURL;
	}

	public ImageSelector getImageSelector() throws Exception {
		if (_imageFileEntryId != _oldImageFileEntryId) {
			if (_imageFileEntryId != 0) {
				FileEntry fileEntry =
					PortletFileRepositoryUtil.getPortletFileEntry(
						_imageFileEntryId);

				_fileEntryTempFile = fileEntry.isRepositoryCapabilityProvided(
					TemporaryFileEntriesCapability.class);

				return new ImageSelector(
					FileUtil.getBytes(fileEntry.getContentStream()),
					fileEntry.getFileName(), fileEntry.getMimeType(),
					_imageCropRegion);
			}
			else {
				return new ImageSelector();
			}
		}
		else if (!_imageURL.equals(_oldImageURL)) {
			return new ImageSelector(_imageURL);
		}

		return null;
	}

	public boolean isFileEntryTempFile() {
		if (_fileEntryTempFile == null) {
			if ((_imageFileEntryId == 0) ||
				(_imageFileEntryId == _oldImageFileEntryId)) {

				_fileEntryTempFile = false;
			}
			else {
				try {
					FileEntry fileEntry =
						PortletFileRepositoryUtil.getPortletFileEntry(
							_imageFileEntryId);

					_fileEntryTempFile =
						fileEntry.isRepositoryCapabilityProvided(
							TemporaryFileEntriesCapability.class);
				}
				catch (PortalException pe) {
					return false;
				}
			}
		}

		return _fileEntryTempFile;
	}

	private Boolean _fileEntryTempFile;
	private final String _imageCropRegion;
	private final long _imageFileEntryId;
	private final String _imageURL;
	private final long _oldImageFileEntryId;
	private final String _oldImageURL;

}