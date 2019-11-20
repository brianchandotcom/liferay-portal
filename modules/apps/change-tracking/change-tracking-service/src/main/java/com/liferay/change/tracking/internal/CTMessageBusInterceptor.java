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

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusInterceptor;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = MessageBusInterceptor.class)
public class CTMessageBusInterceptor implements MessageBusInterceptor {

	@Override
	public boolean intercept(
		MessageBus messageBus, String destinationName, Message message) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return false;
		}

		Destination destination = messageBus.getDestination(destinationName);

		if ((destination != null) &&
			Objects.equals(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				destination.getDestinationType())) {

			return false;
		}

		message.setDestinationName(destinationName);

		try {
			_ctMessageLocalService.addCTMessage(ctCollectionId, message);
		}
		catch (PortalException pe) {
			_log.error(
				StringBundler.concat(
					"Unable to intercept message ", message.toString(),
					" for collection id ", String.valueOf(ctCollectionId)),
				pe);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTMessageBusInterceptor.class);

	@Reference
	private CTMessageLocalService _ctMessageLocalService;

}