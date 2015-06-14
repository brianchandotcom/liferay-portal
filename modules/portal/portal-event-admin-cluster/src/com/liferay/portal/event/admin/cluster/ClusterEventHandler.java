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

import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;


/**
 * @author Raymond Augé
 */
@Component(immediate = true, property = EventConstants.EVENT_TOPIC + "=*")
public class ClusterEventHandler implements EventHandler {

	@Override
	public void handleEvent(Event event) {
		Message message = new Message();

		message.setDestinationName(Details.EVENT_ADMIN_DESTINATION);
		message.setPayload(event);

		_clusterLink.sendMulticastMessage(message, Priority.LEVEL1);
	}

	@Reference
	protected void setClusterDestination(
		ClusterDestination clusterDestination) {
	}

	@Reference
	protected void setClusterLink(ClusterLink clusterLink) {
		_clusterLink = clusterLink;
	}

	private ClusterLink _clusterLink;

}