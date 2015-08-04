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

package com.liferay.wiki.web.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.exception.PageAttachmentNameException;
import com.liferay.wiki.exception.PageAttachmentSizeException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.web.util.WikiWebComponentProvider;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Roberto Díaz
 */
public class PageAttachmentWikiUploadHandler extends BaseUploadHandler {

	public PageAttachmentWikiUploadHandler(long classPK) {
		_classPK = classPK;
	}

	protected PageAttachmentWikiUploadHandler() {
		this(0);
	}

	@Override
	protected FileEntry addFileEntry(
			ThemeDisplay themeDisplay, String fileName, InputStream inputStream,
			String contentType)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

		return WikiPageServiceUtil.addPageAttachment(
			page.getNodeId(), page.getTitle(), fileName, inputStream,
			contentType);
	}

	@Override
	protected void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

		WikiNodePermissionChecker.check(
			permissionChecker, page.getNodeId(), ActionKeys.ADD_ATTACHMENT);
	}

	@Override
	protected FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

			return PortletFileRepositoryUtil.getPortletFileEntry(
				themeDisplay.getScopeGroupId(), page.getAttachmentsFolderId(),
				fileName);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	@Override
	protected String getParameterName() {
		return "imageSelectorFileName";
	}

	@Override
	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof FileNameException ||
			pe instanceof PageAttachmentNameException ||
			pe instanceof PageAttachmentSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof PageAttachmentNameException) {
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (pe instanceof PageAttachmentSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);

			try {
				JSONPortletResponseUtil.writeJSON(
					portletRequest, portletResponse, jsonObject);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
		else {
			throw pe;
		}
	}

	@Override
	protected void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		WikiWebComponentProvider wikiWebComponentProvider =
			WikiWebComponentProvider.getWikiWebComponentProvider();

		WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
			wikiWebComponentProvider.getWikiGroupServiceConfiguration();

		long maxSize = wikiGroupServiceConfiguration.imageMaxSize();

		if ((maxSize > 0) && (size > maxSize)) {
			throw new PageAttachmentSizeException();
		}

		String extension = FileUtil.getExtension(fileName);

		for (String imageExtension :
			wikiGroupServiceConfiguration.imageExtensions()) {

			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new PageAttachmentNameException(
			"Invalid image for file name " + fileName);
	}

	private final long _classPK;

}