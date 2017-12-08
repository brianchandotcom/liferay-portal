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

import com.liferay.html.preview.constants.HtmlPreviewConstants;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.base.HtmlPreviewEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
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

		htmlPreviewEntryPersistence.update(htmlPreviewEntry);

		// Preview

		_sendMessage(userId, groupId, htmlPreviewEntryId, content, mimeType);

		return htmlPreviewEntry;
	}

	@Override
	public HtmlPreviewEntry deleteHtmlPreviewEntry(
			HtmlPreviewEntry htmlPreviewEntry)
		throws PortalException {

		// HTML Preview

		htmlPreviewEntryPersistence.remove(htmlPreviewEntry);

		// Portlet File Entry

		PortletFileRepositoryUtil.deletePortletFileEntry(
			htmlPreviewEntry.getFileEntryId());

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

		htmlPreviewEntryPersistence.update(htmlPreviewEntry);

		// Preview

		_sendMessage(
			htmlPreviewEntry.getUserId(), htmlPreviewEntry.getGroupId(),
			htmlPreviewEntryId, content, mimeType);

		return htmlPreviewEntry;
	}

	private void _sendMessage(
		long userId, long groupId, long htmlPreviewEntryId, String content,
		String mimeType) {

		Message message = new Message();

		Map<String, Object> payload = new HashMap<>();

		payload.put("content", content);
		payload.put("groupId", groupId);
		payload.put("htmlPreviewEntryId", htmlPreviewEntryId);
		payload.put("mimeType", mimeType);
		payload.put("userId", userId);

		message.setPayload(payload);

		MessageBusUtil.sendMessage(
			HtmlPreviewConstants.DESTINATION_NAME, message);
	}

}