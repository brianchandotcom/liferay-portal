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

package com.liferay.lcs.messaging.scheduler.impl;

import com.liferay.lcs.messaging.scheduler.MessageListenerSchedulerService;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class MessageListenerSchedulerServiceImpl
	implements MessageListenerSchedulerService {

	@Override
	public void scheduleMessageListener(Map<String, String> schedulerContext) {
		String destinationName = schedulerContext.get("destinationName");
		String messageListenerName = schedulerContext.get(
			"messageListenerName");

		BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
			"lcs-portlet");

		MessageBusUtil.registerMessageListener(
			destinationName,
			(MessageListener)beanLocator.locate(messageListenerName));

		_messageListenerNamesDestinationNames.put(
			messageListenerName, destinationName);

		if (_log.isDebugEnabled()) {
			_log.debug("Scheduled message listener " + messageListenerName);
		}
	}

	@Override
	public void unscheduleAllMessageListeners() {
		BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
			"lcs-portlet");

		if (beanLocator == null) {
			return;
		}

		for (String messageListenerName :
				_messageListenerNamesDestinationNames.keySet()) {

			MessageBusUtil.unregisterMessageListener(
				_messageListenerNamesDestinationNames.get(messageListenerName),
				(MessageListener)beanLocator.locate(messageListenerName));

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unscheduled message listener " + messageListenerName);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MessageListenerSchedulerServiceImpl.class);

	private Map<String, String> _messageListenerNamesDestinationNames =
		new HashMap<>();

}