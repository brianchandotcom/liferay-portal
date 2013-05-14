/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.flags.service.impl;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.flags.messaging.FlagsRequest;
import com.liferay.portlet.flags.service.base.FlagsEntryServiceBaseImpl;

/**
 * @author Julio Camarero
 */
public class FlagsEntryServiceImpl extends FlagsEntryServiceBaseImpl {

	@Override
	public void addEntry(
		String className, long classPK, String reporterEmailAddress,
		long reportedUserId, String contentTitle, String contentURL,
		String reason, ServiceContext serviceContext) {

		FlagsRequest flagsRequest = new FlagsRequest(
			className, classPK, reporterEmailAddress, reportedUserId,
			contentTitle, contentURL, reason, serviceContext);

		MessageBusUtil.sendMessage(DestinationNames.FLAGS, flagsRequest);
	}

}