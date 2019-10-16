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

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.constants.SegmentsAsahDestinationNames;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = "destination.name=" + SegmentsAsahDestinationNames.INDIVIDUAL_SEGMENTS,
	service = MessageListener.class
)
public class IndividualSegmentsMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String userId = (String)message.getPayload();

		if (Validator.isNull(userId)) {
			return;
		}

		_individualSegmentsChecker.checkIndividualSegments(userId);
	}

	@Reference
	private IndividualSegmentsChecker _individualSegmentsChecker;

}