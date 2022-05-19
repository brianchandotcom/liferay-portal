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

package com.liferay.change.tracking.internal.messaging;

import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTEventListener.class)
public class CTMessageEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				List<Message> messages = _ctMessageLocalService.getMessages(
					ctCollectionId);

				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setProductionModeWithSafeCloseable()) {

					for (Message message : messages) {
						_messageBus.sendMessage(
							message.getDestinationName(), message);
					}
				}

				return null;
			});
	}

	@Reference
	private CTMessageLocalService _ctMessageLocalService;

	@Reference
	private MessageBus _messageBus;

}