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

package com.liferay.dispatch.internal.messaging;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.service.ScheduledTaskExecutorService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
	property = "destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME,
	service = MessageListener.class
)
public class DispatchMessageListener implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		String payload = (String)message.getPayload();

		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(payload);
		}
		catch (JSONException jsonException) {
			throw new MessageListenerException(jsonException);
		}

		long dispatchTriggerId = jsonObject.getLong("dispatchTriggerId");

		try {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.getDispatchTrigger(
					dispatchTriggerId);

			ScheduledTaskExecutorService scheduledTaskExecutorService =
				_scheduledTaskExecutorServiceTrackerMap.getService(
					dispatchTrigger.getType());

			scheduledTaskExecutorService.runProcess(dispatchTriggerId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scheduledTaskExecutorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScheduledTaskExecutorService.class,
				"scheduled.task.executor.service.type");
	}

	@Deactivate
	protected void deactivate() {
		_scheduledTaskExecutorServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchMessageListener.class);

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private ServiceTrackerMap<String, ScheduledTaskExecutorService>
		_scheduledTaskExecutorServiceTrackerMap;

}