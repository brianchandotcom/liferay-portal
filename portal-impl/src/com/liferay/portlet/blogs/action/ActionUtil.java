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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getEntry(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(request, "entryId");

		String urlTitle = ParamUtil.getString(request, "urlTitle");

		BlogsEntry entry = null;

		if (entryId > 0) {
			entry = BlogsEntryServiceUtil.getEntry(entryId);
		}
		else if (Validator.isNotNull(urlTitle)) {
			try {
				entry = BlogsEntryServiceUtil.getEntry(
					themeDisplay.getScopeGroupId(), urlTitle);
			}
			catch (NoSuchEntryException nsee) {
				if (urlTitle.indexOf(CharPool.UNDERLINE) != -1) {

					// Check another URL title for backwards compatibility. See
					// LEP-5733.

					urlTitle = StringUtil.replace(
						urlTitle, CharPool.UNDERLINE, CharPool.DASH);

					entry = BlogsEntryServiceUtil.getEntry(
						themeDisplay.getScopeGroupId(), urlTitle);
				}
				else {
					throw nsee;
				}
			}
		}

		if ((entry != null) && entry.isInTrash()) {
			throw new NoSuchEntryException("{entryId=" + entryId + "}");
		}

		request.setAttribute(WebKeys.BLOGS_ENTRY, entry);
	}

	public static void getEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEntry(request);
	}

	public static FileEntry saveCoverImage(ActionRequest actionRequest)
		throws Exception {

		long coverImageId = ParamUtil.getLong(actionRequest, "coverImageId");

		if (coverImageId == 0) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		int xPos = ParamUtil.getInteger(actionRequest, "xPos");
		int yPos = ParamUtil.getInteger(actionRequest, "yPos");
		int width = ParamUtil.getInteger(actionRequest, "width");
		int height = ParamUtil.getInteger(actionRequest, "height");

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageId);

		if ((xPos > 0) || (yPos > 0) || (width > 0) || (height > 0)) {
			ImageBag imageBag = ImageToolUtil.read(
				fileEntry.getContentStream());

			RenderedImage renderedImage = imageBag.getRenderedImage();

			renderedImage = ImageToolUtil.crop(
				renderedImage, height, width, xPos, yPos);

			byte[] bytes = ImageToolUtil.getBytes(
				renderedImage, imageBag.getType());

			File file = FileUtil.createTempFile(bytes);

			FileEntry resizedFileEntry =
				PortletFileRepositoryUtil.addPortletFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					"", 0, PortletKeys.BLOGS,
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, file,
					fileEntry.getTitle() + "_resized",
					MimeTypesUtil.getContentType(fileEntry.getTitle()), true);

			TempFileUtil.deleteTempFile(coverImageId);

			return resizedFileEntry;
		}

		return fileEntry;
	}

	public static FileEntry uploadCoverImage(
			ActionRequest actionRequest, Class clazz)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fileName = uploadPortletRequest.getFileName("file");
		InputStream inputStream = uploadPortletRequest.getFileAsStream("file");

		String tempImageFolderName = clazz.getName();

		String uniqueTempFileName = TempFileUtil.getUniqueTempFileName(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			tempImageFolderName);

		return TempFileUtil.addTempFile(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			uniqueTempFileName, tempImageFolderName, inputStream,
			MimeTypesUtil.getContentType(fileName));
	}

}