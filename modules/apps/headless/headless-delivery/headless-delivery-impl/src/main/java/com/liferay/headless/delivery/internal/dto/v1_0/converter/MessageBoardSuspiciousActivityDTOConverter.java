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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.MessageBoardSuspiciousActivity;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.service.MBSuspiciousActivityService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Felipe Veloso
 */
@Component(
	property = "dto.class.name=com.liferay.message.boards.model.MBSuspiciousActivity",
	service = {
		DTOConverter.class, MessageBoardSuspiciousActivityDTOConverter.class
	}
)
public class MessageBoardSuspiciousActivityDTOConverter
	implements DTOConverter
		<MBSuspiciousActivity, MessageBoardSuspiciousActivity> {

	@Override
	public String getContentType() {
		return MessageBoardSuspiciousActivity.class.getSimpleName();
	}

	@Override
	public MessageBoardSuspiciousActivity toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		MBSuspiciousActivity mbSuspiciousActivity =
			_mbSuspiciousActivityService.getSuspiciousActivity(
				(Long)dtoConverterContext.getId());

		return new MessageBoardSuspiciousActivity() {
			{
				createDate = mbSuspiciousActivity.getCreateDate();
				messageId = mbSuspiciousActivity.getMessageId();
				modifiedDate = mbSuspiciousActivity.getModifiedDate();
				suspiciousActivityId =
					mbSuspiciousActivity.getSuspiciousActivityId();
				threadId = mbSuspiciousActivity.getThreadId();
				reason = mbSuspiciousActivity.getReason();
				userId = mbSuspiciousActivity.getUserId();
				validated = mbSuspiciousActivity.getValidated();
			}
		};
	}

	@Reference
	private MBSuspiciousActivityService _mbSuspiciousActivityService;

}