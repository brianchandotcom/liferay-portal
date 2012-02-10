/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageStatusMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruno Farache
 */
public class LayoutsRemotePublisherMessageListener
	extends BaseMessageStatusMessageListener {

	public LayoutsRemotePublisherMessageListener() {
	}

	/**
	 * @deprecated
	 */
	public LayoutsRemotePublisherMessageListener(
		SingleDestinationMessageSender statusSender,
		MessageSender responseSender) {

		super(statusSender, responseSender);
	}

	@Override
	protected void doReceive(Message message, MessageStatus messageStatus)
		throws Exception {

		LayoutsRemotePublisherRequest publisherRequest =
			(LayoutsRemotePublisherRequest)message.getPayload();

		messageStatus.setPayload(publisherRequest);

		long userId = publisherRequest.getUserId();
		long sourceGroupId = publisherRequest.getSourceGroupId();
		boolean privateLayout = publisherRequest.isPrivateLayout();
		Map<Long, Boolean> layoutIdMap = publisherRequest.getLayoutIdMap();
		Map<String, String[]> parameterMap = publisherRequest.getParameterMap();
		String remoteAddress = publisherRequest.getRemoteAddress();
		int remotePort = publisherRequest.getRemotePort();
		boolean secureConnection = publisherRequest.isSecureConnection();
		long remoteGroupId = publisherRequest.getRemoteGroupId();
		boolean remotePrivateLayout = publisherRequest.isRemotePrivateLayout();
		Date startDate = publisherRequest.getStartDate();
		Date endDate = publisherRequest.getEndDate();

		String range = MapUtil.getString(parameterMap, "range");

		if (range.equals("last")) {
			int last = MapUtil.getInteger(parameterMap, "last");

			if (last > 0) {
				Date scheduledFireTime =
					publisherRequest.getScheduledFireTime();

				startDate = new Date(
					scheduledFireTime.getTime() - (last * Time.HOUR));

				endDate = scheduledFireTime;
			}
		}

		PrincipalThreadLocal.setName(userId);

		User user = UserLocalServiceUtil.getUserById(userId);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setPathMain(PortalUtil.getPathMain());
		serviceContext.setSignedIn(!user.isDefaultUser());
		serviceContext.setUserId(user.getUserId());

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String param = entry.getKey();
			String[] values = entry.getValue();

			if ((values != null) && (values.length > 0)) {
				if (values.length == 1) {
					attributes.put(param, values[0]);
				}
				else {
					attributes.put(param, values);
				}
			}
		}

		serviceContext.setAttributes(attributes);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			StagingUtil.copyRemoteLayouts(
				sourceGroupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, secureConnection, remoteGroupId,
				remotePrivateLayout, startDate, endDate);
		}
		finally {
			PrincipalThreadLocal.setName(null);
			PermissionThreadLocal.setPermissionChecker(null);
		}
	}

}