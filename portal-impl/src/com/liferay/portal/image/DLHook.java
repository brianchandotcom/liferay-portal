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

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DLValidatorUtil;

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
			DLStoreUtil.deleteFile(_COMPANY_ID, _REPOSITORY_ID, fileName);
		}
		catch (NoSuchFileException nsfe) {
			throw new NoSuchImageException(nsfe);
		}
	}

	@Override
	public byte[] getImageAsBytes(Image image) throws PortalException {
		String fileName = getFileName(image.getImageId(), image.getType());

		InputStream is = DLStoreUtil.getFileAsStream(
			_COMPANY_ID, _REPOSITORY_ID, fileName);

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return bytes;
	}

	@Override
	public InputStream getImageAsStream(Image image) throws PortalException {
		String fileName = getFileName(image.getImageId(), image.getType());

		return DLStoreUtil.getFileAsStream(
			_COMPANY_ID, _REPOSITORY_ID, fileName);
	}

	@Override
	public void updateImage(Image image, String type, byte[] bytes)
		throws PortalException {

		String fileName = getFileName(image.getImageId(), image.getType());

		DLValidatorUtil.validateFileSize(fileName, bytes);

		if (DLStoreUtil.hasFile(_COMPANY_ID, _REPOSITORY_ID, fileName)) {
			DLStoreUtil.deleteFile(_COMPANY_ID, _REPOSITORY_ID, fileName);
		}

		DLStoreUtil.addFile(_COMPANY_ID, _REPOSITORY_ID, fileName, true, bytes);
	}

	protected String getFileName(long imageId, String type) {
		return imageId + StringPool.PERIOD + type;
	}

	private static final long _COMPANY_ID = 0;

	private static final long _REPOSITORY_ID = 0;

}