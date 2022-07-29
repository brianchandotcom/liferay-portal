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

package com.liferay.flags.service.impl;

import com.liferay.flags.internal.messaging.FlagsRequest;
import com.liferay.flags.service.base.FlagsEntryServiceBaseImpl;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBSuspiciousActivityService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	property = {
		"json.web.service.context.name=flags",
		"json.web.service.context.path=FlagsEntry"
	},
	service = AopService.class
)
public class FlagsEntryServiceImpl extends FlagsEntryServiceBaseImpl {

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void addEntry(
			String className, long classPK, String reporterEmailAddress,
			long reportedUserId, String contentTitle, String contentURL,
			String reason, ServiceContext serviceContext)
		throws PortalException {

		if (!Validator.isEmailAddress(reporterEmailAddress)) {
			throw new EmailAddressException();
		}

		FlagsRequest flagsRequest = new FlagsRequest(
			className, classPK, reporterEmailAddress, reportedUserId,
			contentTitle, contentURL, reason, serviceContext);

		Message message = new Message();

		message.setPayload(flagsRequest);

		_messageBus.sendMessage(DestinationNames.FLAGS, message);

		if (className.equals("com.liferay.message.boards.model.MBMessage")) {
			MBMessage mbMessage = _mbMessageService.getMessage(classPK);
			if (mbMessage.getMessageId() == mbMessage.getRootMessageId()){
				_messageBoardSuspiciousActivityLocalService.
					addOrUpdateThreadSuspiciousActivity(
						reason, mbMessage.getThreadId());
			}else{
				_messageBoardSuspiciousActivityLocalService.
					addOrUpdateMessageSuspiciousActivity(classPK, reason);
			}
	}

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBSuspiciousActivityService
		_messageBoardSuspiciousActivityLocalService;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private MBSuspiciousActivityService
		_messageBoardSuspiciousActivityLocalService;

}