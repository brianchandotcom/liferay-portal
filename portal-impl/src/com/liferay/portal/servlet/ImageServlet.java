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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ImageConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ImageServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 */
public class ImageServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_lastModified = GetterUtil.getBoolean(
			servletConfig.getInitParameter("last_modified"), true);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			User user = PortalUtil.getUser(request);

			if (user == null) {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				user = company.getDefaultUser();
			}

			PrincipalThreadLocal.setName(user.getUserId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			if (_lastModified) {
				long lastModified = getLastModified(request);

				if (lastModified > 0) {
					long ifModifiedSince = request.getDateHeader(
						HttpHeaders.IF_MODIFIED_SINCE);

					if ((ifModifiedSince > 0) &&
						(ifModifiedSince == lastModified)) {

						response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

						return;
					}
				}

				if (lastModified > 0) {
					response.setDateHeader(
						HttpHeaders.LAST_MODIFIED, lastModified);
				}
			}

			writeImage(request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, e, request, response);
		}
	}

	protected Image getDefaultImage(HttpServletRequest request, long imageId)
		throws NoSuchImageException {

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.startsWith("/company_logo")) {
			return ImageLocalServiceUtil.getDefaultCompanyLogo();
		}
		else if (path.startsWith("/organization_logo")) {
			return ImageLocalServiceUtil.getDefaultOrganizationLogo();
		}
		else if (path.startsWith("/user_female_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserFemalePortrait();
		}
		else if (path.startsWith("/user_male_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserMalePortrait();
		}
		else if (path.startsWith("/user_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserMalePortrait();
		}
		else {
			throw new NoSuchImageException(
				"No default image exists for " + imageId);
		}
	}

	protected Image getIGImageThumbnail(Image image, long igImageId)
		throws PortalException, SystemException {

		if (image == null) {
			return null;
		}

		long igThumbnailMaxDimension = PrefsPropsUtil.getLong(
			PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION);

		if ((image.getHeight() > igThumbnailMaxDimension) ||
			(image.getWidth() > igThumbnailMaxDimension)) {

			IGImage igImage = IGImageLocalServiceUtil.getImage(
				igImageId);

			IGImageLocalServiceUtil.updateSmallImage(
				igImage.getSmallImageId(), igImage.getLargeImageId());

			return ImageServiceUtil.getImage(image.getImageId());
		}

		return image;
	}

	protected Image getImage(HttpServletRequest request, boolean getDefault)
		throws PortalException, SystemException {

		long imageId = getImageId(request);

		Image image = null;

		if (imageId > 0) {
			image = ImageServiceUtil.getImage(imageId);

			String path = GetterUtil.getString(request.getPathInfo());

			if (path.startsWith("/user_female_portrait") ||
				path.startsWith("/user_male_portrait") ||
				path.startsWith("/user_portrait")) {

				image = getUserPortraitImageResized(image, imageId);
			}
			else {
				long igImageId = ParamUtil.getLong(request, "igImageId");
				boolean igSmallImage = ParamUtil.getBoolean(
					request, "igSmallImage");

				if ((igImageId > 0) && igSmallImage) {
					image = getIGImageThumbnail(image, igImageId);
				}
			}
		}
		else {
			String uuid = ParamUtil.getString(request, "uuid");
			long groupId = ParamUtil.getLong(request, "groupId");

			try {
				if (Validator.isNotNull(uuid) && (groupId > 0)) {
					IGImage igImage =
						IGImageLocalServiceUtil.getImageByUuidAndGroupId(
							uuid, groupId);

					image = ImageServiceUtil.getImage(
						igImage.getLargeImageId());
				}
			}
			catch (PrincipalException pe) {
				throw pe;
			}
			catch (Exception e) {
			}
		}

		if (getDefault) {
			if (image == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Get a default image for " + imageId);
				}

				image = getDefaultImage(request, imageId);
			}
		}

		return image;
	}

	protected byte[] getImageBytes(HttpServletRequest request, Image image) {
		if (!PropsValues.IMAGE_AUTO_SCALE) {
			return image.getTextObj();
		}

		int height = ParamUtil.getInteger(request, "height", image.getHeight());
		int width = ParamUtil.getInteger(request, "width", image.getWidth());

		if ((height >= image.getHeight()) && (width >= image.getWidth())) {
			return image.getTextObj();
		}

		try {
			ImageBag imageBag = ImageProcessorUtil.read(image.getTextObj());

			RenderedImage renderedImage = ImageProcessorUtil.scale(
				imageBag.getRenderedImage(), height, width);

			return ImageProcessorUtil.getBytes(
				renderedImage, imageBag.getType());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error scaling image " + image.getImageId(), e);
			}
		}

		return image.getTextObj();
	}

	protected long getImageId(HttpServletRequest request) {

		// The image id may be passed in as image_id, img_id, or i_id

		long imageId = ParamUtil.getLong(request, "image_id");

		if (imageId <= 0) {
			imageId = ParamUtil.getLong(request, "img_id");
		}

		if (imageId <= 0) {
			imageId = ParamUtil.getLong(request, "i_id");
		}

		if (imageId <= 0) {
			long companyId = ParamUtil.getLong(request, "companyId");
			String screenName = ParamUtil.getString(request, "screenName");

			try {
				if ((companyId > 0) && Validator.isNotNull(screenName)) {
					User user = UserLocalServiceUtil.getUserByScreenName(
						companyId, screenName);

					imageId = user.getPortraitId();
				}
			}
			catch (Exception e) {
			}
		}

		return imageId;
	}

	@Override
	protected long getLastModified(HttpServletRequest request) {
		try {
			Image image = getImage(request, false);

			if (image == null) {
				return -1;
			}

			Date modifiedDate = image.getModifiedDate();

			if (modifiedDate == null) {
				modifiedDate = PortalUtil.getUptime();
			}

			// Round down and remove milliseconds

			return (modifiedDate.getTime() / 1000) * 1000;
		}
		catch (PrincipalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return -1;
	}

	protected Image getUserPortraitImageResized(Image image, long imageId)
		throws PortalException, SystemException {

		if (image == null) {
			return null;
		}

		if ((image.getHeight() > PropsValues.USERS_IMAGE_MAX_HEIGHT) ||
			(image.getWidth() > PropsValues.USERS_IMAGE_MAX_WIDTH)) {

			User user = UserLocalServiceUtil.getUserByPortraitId(imageId);

			UserLocalServiceUtil.updatePortrait(
				user.getUserId(), image.getTextObj());

			return ImageLocalServiceUtil.getImage(imageId);
		}

		return image;
	}

	protected void writeImage(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException, SystemException {

		Image image = getImage(request, true);

		if (image == null) {
			throw new NoSuchImageException("Image is null");
		}
		else {
			String contentType = null;

			if (!image.getType().equals(ImageConstants.TYPE_NOT_AVAILABLE)) {
				contentType = MimeTypesUtil.getContentType(image.getType());

				response.setContentType(contentType);
			}

			String fileName = ParamUtil.getString(request, "fileName");

			try {
				byte[] bytes = getImageBytes(request, image);

				if (Validator.isNotNull(fileName)) {
					ServletResponseUtil.sendFile(
						request, response, fileName, bytes, contentType);
				}
				else {
					ServletResponseUtil.write(response, bytes);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImageServlet.class);

	private boolean _lastModified = true;

}