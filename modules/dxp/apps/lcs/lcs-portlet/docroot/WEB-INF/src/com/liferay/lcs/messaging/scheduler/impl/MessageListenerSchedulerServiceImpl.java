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

import com.liferay.lcs.advisor.MonitoringAdvisor;
import com.liferay.lcs.advisor.MonitoringAdvisorFactory;
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
 * @author Igor Beslic
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

		MessageListener messageListener = (MessageListener)beanLocator.locate(
			messageListenerName);

		MessageBusUtil.registerMessageListener(
			destinationName, messageListener);

		_messageListenerNamesDestinationNames.put(
			messageListenerName, destinationName);

		if (_log.isDebugEnabled()) {
			_log.debug("Scheduled message listener " + messageListenerName);
		}

		MonitoringAdvisor monitoringAdvisor =
			MonitoringAdvisorFactory.getInstance(messageListener.getClass());

		if (monitoringAdvisor != null) {
			monitoringAdvisor.activateMonitoring();
		}
	}

	@Override
	public void unscheduleAllMessageListeners() {
		BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
			"lcs-portlet");

		if (beanLocator == null) {
			return;
		}

		for (Map.Entry<String, String> entry :
				_messageListenerNamesDestinationNames.entrySet()) {

			String messageListenerName = entry.getKey();

			MessageBusUtil.unregisterMessageListener(
				entry.getValue(),
				(MessageListener)beanLocator.locate(messageListenerName));

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unscheduled message listener " + messageListenerName);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageListenerSchedulerServiceImpl.class);

	private final Map<String, String> _messageListenerNamesDestinationNames =
		new HashMap<>();

}