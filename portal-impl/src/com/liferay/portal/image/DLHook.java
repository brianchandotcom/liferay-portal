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

package com.liferay.portal.image;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jorge Ferrer
 */
public class DLHook extends BaseHook {

	@Override
	public void deleteImage(Image image) throws PortalException {
		String fileName = getFileName(image.getImageId(), image.getType());

		try {
			DLStoreUtil.deleteFile(
				image.getCompanyId(), _REPOSITORY_ID, fileName);
		}
		catch (NoSuchFileException noSuchFileException) {
			throw new NoSuchImageException(noSuchFileException);
		}
	}

	@Override
	public byte[] getImageAsBytes(Image image) throws PortalException {
		InputStream inputStream = DLStoreUtil.getFileAsStream(
			image.getCompanyId(), _REPOSITORY_ID,
			getFileName(image.getImageId(), image.getType()));

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(inputStream);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}

		return bytes;
	}

	@Override
	public InputStream getImageAsStream(Image image) throws PortalException {
		return DLStoreUtil.getFileAsStream(
			image.getCompanyId(), _REPOSITORY_ID,
			getFileName(image.getImageId(), image.getType()));
	}

	@Override
	public void updateImage(Image image, String type, byte[] bytes)
		throws PortalException {

		String fileName = getFileName(image.getImageId(), image.getType());

		DLValidatorUtil.validateFileSize(fileName, bytes);

		if (DLStoreUtil.hasFile(
				image.getCompanyId(), _REPOSITORY_ID, fileName)) {

			DLStoreUtil.deleteFile(
				image.getCompanyId(), _REPOSITORY_ID, fileName);
		}

		DLStoreUtil.addFile(
			image.getCompanyId(), _REPOSITORY_ID, fileName, true, bytes);
	}

	protected String getFileName(long imageId, String type) {
		return imageId + StringPool.PERIOD + type;
	}

	private static final long _REPOSITORY_ID = 0;

}