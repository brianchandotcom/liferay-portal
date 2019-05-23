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

package com.liferay.html.preview.service.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.html.preview.exception.InvalidHtmlPreviewEntryMimeTypeException;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.processor.HtmlPreviewProcessor;
import com.liferay.html.preview.processor.HtmlPreviewProcessorTracker;
import com.liferay.html.preview.service.base.HtmlPreviewEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.html.preview.model.HtmlPreviewEntry",
	service = AopService.class
)
public class HtmlPreviewEntryLocalServiceImpl
	extends HtmlPreviewEntryLocalServiceBaseImpl {

	@Override
	public HtmlPreviewEntry addHtmlPreviewEntry(
			long userId, long groupId, long classNameId, long classPK,
			String content, String mimeType, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long htmlPreviewEntryId = counterLocalService.increment();

		HtmlPreviewEntry htmlPreviewEntry = htmlPreviewEntryPersistence.create(
			htmlPreviewEntryId);

		htmlPreviewEntry.setGroupId(groupId);
		htmlPreviewEntry.setCompanyId(user.getCompanyId());
		htmlPreviewEntry.setUserId(user.getUserId());
		htmlPreviewEntry.setUserName(user.getFullName());
		htmlPreviewEntry.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		htmlPreviewEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		htmlPreviewEntry.setClassNameId(classNameId);
		htmlPreviewEntry.setClassPK(classPK);

		FileEntry fileEntry = _getFileEntry(
			htmlPreviewEntry.getUserId(), htmlPreviewEntry.getGroupId(),
			htmlPreviewEntryId, content, mimeType);

		if (fileEntry != null) {
			htmlPreviewEntry.setFileEntryId(fileEntry.getFileEntryId());
		}

		htmlPreviewEntryPersistence.update(htmlPreviewEntry);

		return htmlPreviewEntry;
	}

	@Override
	public HtmlPreviewEntry deleteHtmlPreviewEntry(
			HtmlPreviewEntry htmlPreviewEntry)
		throws PortalException {

		htmlPreviewEntryPersistence.remove(htmlPreviewEntry);

		if (htmlPreviewEntry.getFileEntryId() > 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				htmlPreviewEntry.getFileEntryId());
		}

		return htmlPreviewEntry;
	}

	@Override
	public HtmlPreviewEntry deleteHtmlPreviewEntry(long htmlPreviewEntryId)
		throws PortalException {

		HtmlPreviewEntry htmlPreviewEntry =
			htmlPreviewEntryPersistence.fetchByPrimaryKey(htmlPreviewEntryId);

		return deleteHtmlPreviewEntry(htmlPreviewEntry);
	}

	@Override
	public HtmlPreviewEntry updateHtmlPreviewEntry(
			long htmlPreviewEntryId, String content, String mimeType,
			ServiceContext serviceContext)
		throws PortalException {

		HtmlPreviewEntry htmlPreviewEntry =
			htmlPreviewEntryPersistence.fetchByPrimaryKey(htmlPreviewEntryId);

		htmlPreviewEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));

		FileEntry fileEntry = _getFileEntry(
			htmlPreviewEntry.getUserId(), htmlPreviewEntry.getGroupId(),
			htmlPreviewEntryId, content, mimeType);

		if (fileEntry != null) {
			htmlPreviewEntry.setFileEntryId(fileEntry.getFileEntryId());
		}

		htmlPreviewEntryPersistence.update(htmlPreviewEntry);

		return htmlPreviewEntry;
	}

	private FileEntry _getFileEntry(
			long userId, long groupId, long htmlPreviewEntryId, String content,
			String mimeType)
		throws PortalException {

		HtmlPreviewProcessor htmlPreviewProcessor =
			_htmlPreviewProcessorTracker.getHtmlPreviewProcessor(mimeType);

		if (htmlPreviewProcessor == null) {
			throw new InvalidHtmlPreviewEntryMimeTypeException(
				"No HTML preview processor available for MIME type " +
					mimeType);
		}

		File file = htmlPreviewProcessor.generateContentHtmlPreview(content);

		if (file == null) {
			return null;
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, HtmlPreviewEntry.class.getName());

		if (repository != null) {
			FileEntry fileEntry =
				PortletFileRepositoryUtil.fetchPortletFileEntry(
					groupId, repository.getDlFolderId(),
					String.valueOf(htmlPreviewEntryId));

			if (fileEntry != null) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					groupId, repository.getDlFolderId(),
					String.valueOf(htmlPreviewEntryId));
			}
		}

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, HtmlPreviewEntry.class.getName(),
			htmlPreviewEntryId, HtmlPreviewEntry.class.getName(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, file,
			String.valueOf(htmlPreviewEntryId), mimeType, false);
	}

	@Reference
	private HtmlPreviewProcessorTracker _htmlPreviewProcessorTracker;

}