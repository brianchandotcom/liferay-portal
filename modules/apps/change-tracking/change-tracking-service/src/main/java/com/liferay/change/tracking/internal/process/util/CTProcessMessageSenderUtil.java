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

package com.liferay.change.tracking.internal.process.util;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Daniel Kocsis
 */
public class CTProcessMessageSenderUtil {

	public static void logCTEntryCollision(
		CTEntry ctEntry, boolean ignoreCollision) {

		if (ignoreCollision) {
			_sendBackgroundTaskStatusMessage(
				new Date(), "log-level-warn",
				"collision-detected-for-x-x-ignore-collision-is-selected",
				_getMessageParameters(ctEntry));

			return;
		}

		_sendBackgroundTaskStatusMessage(
			new Date(), "log-level-error",
			"publications-stopped-due-tp-collision-on-x-x-version-x-and-x",
			_getMessageParameters(ctEntry));
	}

	public static void logCTEntryPublished(CTEntry ctEntry) {
		_sendBackgroundTaskStatusMessage(
			new Date(), "log-level-info", "adding-x-x-version-x",
			_getMessageParameters(ctEntry));
	}

	public static void logCTProcessFinished() {
		_sendBackgroundTaskStatusMessage(
			new Date(), "log-level-info", "publication-succeeded",
			Collections.emptyMap());
	}

	public static void logCTProcessStarted(int ctCollectionCTEntriesCount) {
		_sendBackgroundTaskStatusMessage(
			new Date(), "log-level-info",
			"publication-is-starting-with-x-changes",
			Collections.singletonMap(
				"numberOfChanges", ctCollectionCTEntriesCount));
	}

	private static Map<String, Serializable> _getMessageParameters(
		CTEntry ctEntry) {

		Map<String, Serializable> messageParameters = new HashMap<>();

		messageParameters.put("ctEntryId", ctEntry.getCtEntryId());
		messageParameters.put("modelClassName", ctEntry.getModelClassName());
		messageParameters.put("modelClassPK", ctEntry.getModelClassPK());

		return messageParameters;
	}

	private static void _sendBackgroundTaskStatusMessage(
		Date date, String level, String message,
		Map<String, Serializable> messageParameters) {

		Message statusMessage = new Message();

		statusMessage.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		statusMessage.put("date", date);
		statusMessage.put("level", level);
		statusMessage.put("message", message);
		statusMessage.put("messageParameters", messageParameters);

		BackgroundTaskStatusMessageSender backgroundTaskStatusMessageSender =
			_serviceTracker.getService();

		backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			statusMessage);
	}

	private static final ServiceTracker
		<BackgroundTaskStatusMessageSender, BackgroundTaskStatusMessageSender>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CTProcessMessageSenderUtil.class);

		ServiceTracker
			<BackgroundTaskStatusMessageSender,
			 BackgroundTaskStatusMessageSender> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					BackgroundTaskStatusMessageSender.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}