/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.service.base.ImageLocalServiceBaseImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.webserver.WebServerServletTokenUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class ImageLocalServiceImpl extends ImageLocalServiceBaseImpl {

	@Override
	public void afterPropertiesSet() {
		ClassLoader classLoader = getClass().getClassLoader();

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsKeys.IMAGE_DEFAULT_SPACER));

			if (is == null) {
				_log.error("Default spacer is not available");
			}

			_defaultSpacer = getImage(is);
		}
		catch (Exception ioe) {
			_log.error(
				"Unable to configure the default spacer: " + ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO));

			if (is == null) {
				_log.error("Default company logo is not available");
			}

			_defaultCompanyLogo = getImage(is);
		}
		catch (Exception ioe) {
			_log.error(
				"Unable to configure the default company logo: " +
					ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsKeys.IMAGE_DEFAULT_ORGANIZATION_LOGO));

			if (is == null) {
				_log.error("Default organization logo is not available");
			}

			_defaultOrganizationLogo = getImage(is);
		}
		catch (Exception ioe) {
			_log.error(
				"Unable to configure the default organization logo: " +
					ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsKeys.IMAGE_DEFAULT_USER_FEMALE_PORTRAIT));

			if (is == null) {
				_log.error("Default user female portrait is not available");
			}

			_defaultUserFemalePortrait = getImage(is);
		}
		catch (Exception ioe) {
			_log.error(
				"Unable to configure the default user female portrait: " +
					ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsKeys.IMAGE_DEFAULT_USER_MALE_PORTRAIT));

			if (is == null) {
				_log.error("Default user male portrait is not available");
			}

			_defaultUserMalePortrait = getImage(is);
		}
		catch (Exception ioe) {
			_log.error(
				"Unable to configure the default user male portrait: " +
					ioe.getMessage());
		}
	}

	@Override
	public void deleteImage(long imageId)
		throws PortalException, SystemException {

		if (imageId <= 0) {
			return;
		}

		/*if (PropsValues.SOURCE_IMAGE_HOOK_IMPL.equals(
				DatabaseHook.class.getName()) &&
			(imagePersistence.getListeners().length == 0)) {

			runSQL("delete from Image where imageId = " + imageId);

			imagePersistence.clearCache();
		}
		else {*/
			try {
				Image image = getImage(imageId);

				imagePersistence.remove(imageId);

				Hook hook = HookFactory.getInstance();

				hook.deleteImage(image);
			}
			catch (NoSuchImageException nsie) {
			}
		//}
	}

	public Image getCompanyLogo(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			image = getDefaultCompanyLogo();
		}

		return image;
	}

	public Image getDefaultCompanyLogo() {
		return _defaultCompanyLogo;
	}

	public Image getDefaultOrganizationLogo() {
		return _defaultOrganizationLogo;
	}

	public Image getDefaultSpacer() {
		return _defaultSpacer;
	}

	public Image getDefaultUserFemalePortrait() {
		return _defaultUserFemalePortrait;
	}

	public Image getDefaultUserMalePortrait() {
		return _defaultUserMalePortrait;
	}

	@Override
	public Image getImage(long imageId) {
		try {
			if (imageId > 0) {
				return imagePersistence.findByPrimaryKey(imageId);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get image " + imageId + ": " + e.getMessage());
			}
		}

		return null;
	}

	public Image getImage(byte[] bytes)
		throws PortalException, SystemException {

		return getImage(null, bytes);
	}

	public Image getImage(File file) throws PortalException, SystemException {
		try {
			return getImage(new FileInputStream(file));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Image getImage(InputStream is)
		throws PortalException, SystemException {

		return getImage(is, null);
	}

	public Image getImageOrDefault(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			image = getDefaultSpacer();
		}

		return image;
	}

	public List<Image> getImages() throws SystemException {
		return imagePersistence.findAll();
	}

	@Override
	public List<Image> getImages(int start, int end) throws SystemException {
		return imagePersistence.findAll(start, end);
	}

	public List<Image> getImagesBySize(int size) throws SystemException {
		return imagePersistence.findByLtSize(size);
	}

	public boolean isNullOrDefaultSpacer(byte[] bytes) {
		if ((bytes == null) || (bytes.length == 0) ||
			(Arrays.equals(bytes, getDefaultSpacer().getTextObj()))) {

			return true;
		}
		else {
			return false;
		}
	}

	public Image updateImage(long imageId, byte[] bytes)
		throws PortalException, SystemException {

		Image image = getImage(bytes);

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	public Image updateImage(long imageId, File file)
		throws PortalException, SystemException {

		Image image = getImage(file);

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	public Image updateImage(long imageId, InputStream is)
		throws PortalException, SystemException {

		Image image = getImage(is);

		return updateImage(
			imageId, image.getTextObj(), image.getType(), image.getHeight(),
			image.getWidth(), image.getSize());
	}

	public Image updateImage(
			long imageId, byte[] bytes, String type, int height, int width,
			int size)
		throws PortalException, SystemException {

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

		imagePersistence.update(image, false);

		WebServerServletTokenUtil.resetToken(imageId);

		return image;
	}

	protected Image getImage(InputStream is, byte[] bytes)
		throws PortalException, SystemException {

		try {
			if (is != null) {
				bytes = FileUtil.getBytes(is);
			}

			if (bytes == null) {
				return null;
			}

			ImageBag imageBag = ImageProcessorUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();
			String type = imageBag.getType();

			if (renderedImage == null) {
				throw new ImageTypeException();
			}

			int height = renderedImage.getHeight();
			int width = renderedImage.getWidth();
			int size = bytes.length;

			Image image = new ImageImpl();

			image.setTextObj(bytes);
			image.setType(type);
			image.setHeight(height);
			image.setWidth(width);
			image.setSize(size);

			return image;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe);
					}
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ImageLocalServiceImpl.class);

	private Image _defaultSpacer;
	private Image _defaultCompanyLogo;
	private Image _defaultOrganizationLogo;
	private Image _defaultUserFemalePortrait;
	private Image _defaultUserMalePortrait;

}