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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.ParallelDestination;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property =
		Details.DESTINATION_NAME + "=" +
			Details.EVENT_ADMIN_DESTINATION,
	service = {ClusterDestination.class, Destination.class}
)
public class ClusterDestination extends ParallelDestination {

	@Activate
	protected void activate(Map<String, Object> properties) {
		Details details = Configurable.createConfigurable(
			Details.class, properties);

		setName(details.destination_name());

		afterPropertiesSet();
	}

	@Override
	protected void dispatch(
		Set<MessageListener> messageListeners, Message message) {

		super.dispatch(messageListeners, message);
	}

}