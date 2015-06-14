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

package com.liferay.portal.event.admin.cluster;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		Details.DESTINATION_NAME + "=" +
			Details.EVENT_ADMIN_DESTINATION
	},
	service = MessageListener.class
)
public class ClusterMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Event event = (Event)message.getPayload();

		_eventAdmin.sendEvent(event);
	}

	@Reference
	protected void setClusterDestination(
		ClusterDestination clusterDestination) {
	}

	@Reference
	protected void setEventAdmin(EventAdmin eventAdmin) {
		_eventAdmin = eventAdmin;
	}

	private EventAdmin _eventAdmin;

}