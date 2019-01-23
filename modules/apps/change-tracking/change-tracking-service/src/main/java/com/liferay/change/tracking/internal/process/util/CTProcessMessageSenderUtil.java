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

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.service.CTEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
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
		boolean collisionIgnored, CTEntry ctEntry) {

		if (ctEntry == null) {
			return;
		}

		if (collisionIgnored) {
			_sendBackgroundTaskStatusMessage(
				new Date(), Level.WARN,
				"collision-detected-for-x-x-ignore-collision-is-selected",
				_getMessageParameters(ctEntry));

			return;
		}

		_sendBackgroundTaskStatusMessage(
			new Date(), Level.ERROR,
			"publications-stopped-due-tp-collision-on-x-x-version-x-and-x",
			_getMessageParameters(ctEntry));
	}

	public static void logCTEntryPublished(CTEntry ctEntry) {
		if (ctEntry == null) {
			return;
		}

		_sendBackgroundTaskStatusMessage(
			new Date(), Level.INFO, "adding-x-x-version-x",
			_getMessageParameters(ctEntry));
	}

	public static void logCTProcessFailed() {
		_sendBackgroundTaskStatusMessage(
			new Date(), Level.ERROR, "publication-failed",
			Collections.emptyMap());
	}

	public static void logCTProcessFinished() {
		_sendBackgroundTaskStatusMessage(
			new Date(), Level.INFO, "publication-succeeded",
			Collections.emptyMap());
	}

	public static void logCTProcessStarted(CTProcess ctProcess) {
		if (ctProcess == null) {
			return;
		}

		CTCollection ctCollection =
			CTCollectionLocalServiceUtil.fetchCTCollection(
				ctProcess.getCtCollectionId());

		if (ctCollection == null) {
			return;
		}

		int ctCollectionCTEntriesCount =
			CTEntryLocalServiceUtil.getCTCollectionCTEntriesCount(
				ctCollection.getCtCollectionId());

		Map<String, Serializable> messageParameters = new HashMap<>();

		messageParameters.put("numberOfChanges", ctCollectionCTEntriesCount);

		_sendBackgroundTaskStatusMessage(
			new Date(), Level.INFO, "publication-is-starting-with-x-changes",
			messageParameters);
	}

	public enum Level {

		ERROR, INFO, WARN;

		public String getLabel() {
			if (ERROR.equals(this)) {
				return "log-level-error";
			}
			else if (INFO.equals(this)) {
				return "log-level-info";
			}
			else if (WARN.equals(this)) {
				return "log-level-warn";
			}

			return StringPool.BLANK;
		}

	}

	private static Map<String, Serializable> _getMessageParameters(
		CTEntry ctEntry) {

		Map<String, Serializable> messageParameters = new HashMap<>();

		messageParameters.put("className", ctEntry.getClassName());
		messageParameters.put("classPK", ctEntry.getClassPK());
		messageParameters.put("ctEntryId", ctEntry.getCtEntryId());

		return messageParameters;
	}

	private static void _sendBackgroundTaskStatusMessage(
		Date date, Level level, String message,
		Map<String, Serializable> messageParameters) {

		Message statusMessage = new Message();

		statusMessage.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		statusMessage.put("date", date);
		statusMessage.put("level", level);
		statusMessage.put("message", message);
		statusMessage.put(
			"messageParameters", new HashMap<>(messageParameters));

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