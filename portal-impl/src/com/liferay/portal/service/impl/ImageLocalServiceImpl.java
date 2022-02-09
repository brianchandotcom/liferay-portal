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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.image.HookFactory;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.service.base.ImageLocalServiceBaseImpl;

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
		try {
			if (imageId <= 0) {
				return null;
			}

			Image image = getImage(imageId);

			if (image == null) {
				return null;
			}

			imagePersistence.remove(image);

			Hook hook = HookFactory.getInstance();

			hook.deleteImage(image);

			return image;
		}
		catch (NoSuchImageException noSuchImageException) {

			// DLHook throws NoSuchImageException if the file no longer
			// exists. See LPS-30430. This exception can be ignored.

			if (_log.isWarnEnabled()) {
				_log.warn(noSuchImageException);
			}

			return null;
		}
	}

	@Override
	public Image getCompanyLogo(long imageId) {
		Image image = getImage(imageId);

		if (image != null) {
			return image;
		}

		return ImageToolUtil.getDefaultCompanyLogo();
	}

	@Override
	public Image getImage(long imageId) {
		try {
			if (imageId <= 0) {
				return null;
			}

			return imagePersistence.fetchByPrimaryKey(imageId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get image ", imageId, ": ",
						exception.getMessage()));
			}

			return null;
		}
	}

	@Override
	public Image getImageOrDefault(long imageId) {
		Image image = getImage(imageId);

		if (image != null) {
			return image;
		}

		return ImageToolUtil.getDefaultSpacer();
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
	public Image moveImage(long imageId, byte[] bytes) throws PortalException {
		Image image = updateImage(
			_getImageCompanyId(imageId), counterLocalService.increment(),
			bytes);

		deleteImage(imageId);

		return image;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #updateImage(long, long, byte[])}
	 */
	@Deprecated
	@Override
	public Image updateImage(long imageId, byte[] bytes)
		throws PortalException {

		return updateImage(CompanyConstants.SYSTEM, imageId, bytes);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #updateImage(long, long, byte[], String, int, int, int)}
	 */
	@Deprecated
	@Override
	public Image updateImage(
			long imageId, byte[] bytes, String type, int height, int width,
			int size)
		throws PortalException {

		return updateImage(
			CompanyConstants.SYSTEM, imageId, bytes, type, height, width, size);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #updateImage(long, long, File)}
	 */
	@Deprecated
	@Override
	public Image updateImage(long imageId, File file) throws PortalException {
		return updateImage(CompanyConstants.SYSTEM, imageId, file);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #updateImage(long, long, InputStream)}
	 */
	@Deprecated
	@Override
	public Image updateImage(long imageId, InputStream inputStream)
		throws PortalException {

		return updateImage(CompanyConstants.SYSTEM, imageId, inputStream);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #updateImage(long, long, InputStream, boolean)}
	 */
	@Deprecated
	@Override
	public Image updateImage(
			long imageId, InputStream inputStream, boolean cleanUpStream)
		throws PortalException {

		try {
			Image image = ImageToolUtil.getImage(inputStream, cleanUpStream);

			return updateImage(
				imageId, image.getTextObj(), image.getType(), image.getHeight(),
				image.getWidth(), image.getSize());
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public Image updateImage(long companyId, long imageId, byte[] bytes)
		throws PortalException {

		try {
			Image image = ImageToolUtil.getImage(bytes);

			return updateImage(
				companyId, imageId, image.getTextObj(), image.getType(),
				image.getHeight(), image.getWidth(), image.getSize());
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public Image updateImage(
			long companyId, long imageId, byte[] bytes, String type, int height,
			int width, int size)
		throws PortalException {

		if ((companyId == CompanyConstants.SYSTEM) && _log.isWarnEnabled()) {
			_log.warn("Associating image " + imageId + " to a system company");
		}

		validate(type);

		Image image = imagePersistence.fetchByPrimaryKey(imageId);

		if (image == null) {
			image = imagePersistence.create(imageId);

			image.setCompanyId(companyId);
		}

		image.setModifiedDate(new Date());
		image.setType(type);
		image.setHeight(height);
		image.setWidth(width);
		image.setSize(size);

		Hook hook = HookFactory.getInstance();

		hook.updateImage(image, type, bytes);

		image = imagePersistence.update(image);

		WebServerServletTokenUtil.resetToken(imageId);

		return image;
	}

	@Override
	public Image updateImage(long companyId, long imageId, File file)
		throws PortalException {

		try {
			Image image = ImageToolUtil.getImage(file);

			return updateImage(
				companyId, imageId, image.getTextObj(), image.getType(),
				image.getHeight(), image.getWidth(), image.getSize());
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public Image updateImage(
			long companyId, long imageId, InputStream inputStream)
		throws PortalException {

		try {
			Image image = ImageToolUtil.getImage(inputStream);

			return updateImage(
				companyId, imageId, image.getTextObj(), image.getType(),
				image.getHeight(), image.getWidth(), image.getSize());
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public Image updateImage(
			long companyId, long imageId, InputStream inputStream,
			boolean cleanUpStream)
		throws PortalException {

		try {
			Image image = ImageToolUtil.getImage(inputStream, cleanUpStream);

			return updateImage(
				companyId, imageId, image.getTextObj(), image.getType(),
				image.getHeight(), image.getWidth(), image.getSize());
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	protected void validate(String type) throws PortalException {
		if ((type == null) || type.contains(StringPool.BACK_SLASH) ||
			type.contains(StringPool.COLON) ||
			type.contains(StringPool.GREATER_THAN) ||
			type.contains(StringPool.LESS_THAN) ||
			type.contains(StringPool.PERCENT) ||
			type.contains(StringPool.PERIOD) ||
			type.contains(StringPool.PIPE) ||
			type.contains(StringPool.QUESTION) ||
			type.contains(StringPool.QUOTE) ||
			type.contains(StringPool.SLASH) ||
			type.contains(StringPool.SPACE) || type.contains(StringPool.STAR)) {

			throw new ImageTypeException();
		}
	}

	private long _getImageCompanyId(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Image " + imageId + " is associated to a system company");
			}

			return CompanyConstants.SYSTEM;
		}

		return image.getCompanyId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageLocalServiceImpl.class);

}