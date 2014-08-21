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

package com.liferay.portal.service.impl;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.image.HookFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.base.ImageLocalServiceBaseImpl;
import com.liferay.portal.webserver.WebServerServletTokenUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Shuyang Zhou
 */
public class ImageLocalServiceImpl extends ImageLocalServiceBaseImpl {

	@Override
	public Image deleteImage(long imageId) throws PortalException {
		if (imageId <= 0) {
			return null;
		}

		/*if (PropsValues.IMAGE_HOOK_IMPL.equals(
				DatabaseHook.class.getName()) &&
			(imagePersistence.getListeners().length == 0)) {

			runSQL("delete from Image where imageId = " + imageId);

			imagePersistence.clearCache();
		}
		else {*/
			Image image = getImage(imageId);

			if (image != null) {
				imagePersistence.remove(image);

				Hook hook = HookFactory.getInstance();

				try {
					hook.deleteImage(image);
				}
				catch (NoSuchImageException nsie) {

					// DLHook throws NoSuchImageException if the file no longer
					// exists. See LPS-30430. This exception can be ignored.

					if (_log.isWarnEnabled()) {
						_log.warn(nsie, nsie);
					}
				}
			}

			return image;
		//}
	}

	@Override
	public Image getCompanyLogo(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			image = ImageToolUtil.getDefaultCompanyLogo();
		}

		return image;
	}

	@Override
	public Image getImage(long imageId) {
		if (imageId > 0) {
			try {
				return imagePersistence.fetchByPrimaryKey(imageId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get image " + imageId + ": " +
							e.getMessage());
				}
			}
		}

		return null;
	}

	@Override
	public Image getImageOrDefault(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			image = ImageToolUtil.getDefaultSpacer();
		}

		return image;
	}

	@Override
	public List<Image> getImages() {
		return imagePersistence.findAll();
	}

	@Override
	public List<Image> getImagesBySize(int size) {
		return imagePersistence.findByLtSize(size);
	}

	@Override
	public Image updateImage(long imageId, byte[] bytes)
		throws PortalException {

		Image image = null;

		try {
			image = ImageToolUtil.getImage(bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	@Override
	public Image updateImage(
			long imageId, byte[] bytes, String type, int height, int width,
			int size)
		throws PortalException {

		validate(type);

		Image image = imagePersistence.fetchByPrimaryKey(imageId);

		if (image == null) {
			image = imagePersistence.create(imageId);
		}

		image.setModifiedDate(new Date());
		image.setType(type);
		image.setHeight(height);
		image.setWidth(width);
		image.setSize(size);

		Hook hook = HookFactory.getInstance();

		hook.updateImage(image, type, bytes);

		imagePersistence.update(image);

		WebServerServletTokenUtil.resetToken(imageId);

		return image;
	}

	@Override
	public Image updateImage(long imageId, File file) throws PortalException {
		Image image = null;

		try {
			image = ImageToolUtil.getImage(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	@Override
	public Image updateImage(long imageId, InputStream is)
		throws PortalException {

		Image image = null;

		try {
			image = ImageToolUtil.getImage(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	@Override
	public Image updateImage(
			long imageId, InputStream is, boolean cleanUpStream)
		throws PortalException {

		Image image = null;

		try {
			image = ImageToolUtil.getImage(is, cleanUpStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	protected void validate(String type) throws PortalException {
		if ((type == null) ||
			type.contains(StringPool.BACK_SLASH) ||
			type.contains(StringPool.COLON) ||
			type.contains(StringPool.GREATER_THAN) ||
			type.contains(StringPool.LESS_THAN) ||
			type.contains(StringPool.PERCENT) ||
			type.contains(StringPool.PERIOD) ||
			type.contains(StringPool.PIPE) ||
			type.contains(StringPool.QUESTION) ||
			type.contains(StringPool.QUOTE) ||
			type.contains(StringPool.SLASH) ||
			type.contains(StringPool.SPACE) ||
			type.contains(StringPool.STAR)) {

			throw new ImageTypeException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageLocalServiceImpl.class);

}