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
import com.liferay.html.preview.exception.InvalidMimeTypeException;
import com.liferay.html.preview.model.HtmlPreview;
import com.liferay.html.preview.processor.HtmlPreviewProcessor;
import com.liferay.html.preview.processor.HtmlPreviewProcessorTracker;
import com.liferay.html.preview.service.base.HtmlPreviewLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class HtmlPreviewLocalServiceImpl
	extends HtmlPreviewLocalServiceBaseImpl {

	@Override
	public HtmlPreview generateHtmlPreview(
			long userId, long groupId, long classNameId, long classPK,
			String content, String mimeType, boolean asynchronous,
			ServiceContext serviceContext)
		throws PortalException {

		HtmlPreview htmlPreview = htmlPreviewPersistence.fetchByG_C_C(
			groupId, classNameId, classPK);

		if (htmlPreview == null) {
			User user = userLocalService.getUser(userId);

			long htmlPreviewId = counterLocalService.increment();

			htmlPreview = htmlPreviewPersistence.create(htmlPreviewId);

			htmlPreview.setGroupId(groupId);
			htmlPreview.setCompanyId(user.getCompanyId());
			htmlPreview.setUserId(user.getUserId());
			htmlPreview.setUserName(user.getFullName());
			htmlPreview.setCreateDate(serviceContext.getCreateDate(new Date()));
			htmlPreview.setModifiedDate(
				serviceContext.getModifiedDate(new Date()));

			htmlPreview.setClassNameId(classNameId);
			htmlPreview.setClassPK(classPK);

			htmlPreviewPersistence.update(htmlPreview);
		}

		if (asynchronous) {
			Message message = new Message();

			Map<String, Object> payload = new HashMap<>();

			payload.put("classNameId", classNameId);
			payload.put("classPK", classPK);
			payload.put("content", content);
			payload.put("groupId", groupId);
			payload.put("mimeType", mimeType);
			payload.put("userId", userId);

			message.setPayload(payload);

			MessageBusUtil.sendMessage(
				HtmlPreviewConstants.HTML_PREVIEW_GENERATOR, message);

			return htmlPreview;
		}

		HtmlPreviewProcessor htmlPreviewProcessor =
			_htmlPreviewProcessorTracker.getHtmlPreviewProcessor(mimeType);

		if (htmlPreviewProcessor == null) {
			throw new InvalidMimeTypeException(
				"No HTML preview processor available for MIME type " +
					mimeType);
		}

		FileEntry fileEntry = htmlPreviewProcessor.generateHtmlPreviewFileEntry(
			userId, groupId, content);

		htmlPreview.setFileEntryId(fileEntry.getFileEntryId());

		htmlPreviewPersistence.update(htmlPreview);

		return htmlPreview;
	}

	@ServiceReference(type = HtmlPreviewProcessorTracker.class)
	private HtmlPreviewProcessorTracker _htmlPreviewProcessorTracker;

}