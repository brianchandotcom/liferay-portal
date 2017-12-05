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
import com.liferay.html.preview.model.HtmlPreview;
import com.liferay.html.preview.service.base.HtmlPreviewLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class HtmlPreviewLocalServiceImpl
	extends HtmlPreviewLocalServiceBaseImpl {

	@Override
	public HtmlPreview addHtmlPreview(
			long userId, long groupId, long classNameId, long classPK,
			String content, String mimeType, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long htmlPreviewId = counterLocalService.increment();

		HtmlPreview htmlPreview = htmlPreviewPersistence.create(htmlPreviewId);

		htmlPreview.setGroupId(groupId);
		htmlPreview.setCompanyId(user.getCompanyId());
		htmlPreview.setUserId(user.getUserId());
		htmlPreview.setUserName(user.getFullName());
		htmlPreview.setCreateDate(serviceContext.getCreateDate(new Date()));
		htmlPreview.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		htmlPreview.setClassNameId(classNameId);
		htmlPreview.setClassPK(classPK);

		htmlPreviewPersistence.update(htmlPreview);

		// Preview

		_sendMessage(userId, groupId, htmlPreviewId, content, mimeType);

		return htmlPreview;
	}

	@Override
	public HtmlPreview deleteHtmlPreview(HtmlPreview htmlPreview) {

		// HTML Preview

		htmlPreviewPersistence.remove(htmlPreview);

		return htmlPreview;
	}

	@Override
	public HtmlPreview deleteHtmlPreview(long htmlPreviewId) {
		HtmlPreview htmlPreview = htmlPreviewPersistence.fetchByPrimaryKey(
			htmlPreviewId);

		return deleteHtmlPreview(htmlPreview);
	}

	@Override
	public HtmlPreview updateHtmlPreview(
			long htmlPreviewId, String content, String mimeType,
			ServiceContext serviceContext)
		throws PortalException {

		HtmlPreview htmlPreview = htmlPreviewPersistence.fetchByPrimaryKey(
			htmlPreviewId);

		htmlPreview.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		htmlPreviewPersistence.update(htmlPreview);

		// Preview

		_sendMessage(
			htmlPreview.getUserId(), htmlPreview.getGroupId(), htmlPreviewId,
			content, mimeType);

		return htmlPreview;
	}

	private void _sendMessage(
		long userId, long groupId, long htmlPreviewId, String content,
		String mimeType) {

		Message message = new Message();

		Map<String, Object> payload = new HashMap<>();

		payload.put("content", content);
		payload.put("groupId", groupId);
		payload.put("htmlPreviewId", htmlPreviewId);
		payload.put("mimeType", mimeType);
		payload.put("userId", userId);

		message.setPayload(payload);

		MessageBusUtil.sendMessage(
			HtmlPreviewConstants.HTML_PREVIEW_GENERATOR, message);
	}

}