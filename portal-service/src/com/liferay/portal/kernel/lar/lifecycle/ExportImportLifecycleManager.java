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

package com.liferay.portal.kernel.lar.lifecycle;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

import java.io.Serializable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleManager {

	public static void fireExportImportLifecycleEvent(
		int code, int processFlag, Serializable... arguments) {

		Message message = new Message();

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			ExportImportLifecycleEventFactoryUtil.create(
				code, processFlag, arguments);

		message.put("exportImportLifecycleEvent", exportImportLifecycleEvent);

		MessageBusUtil.sendMessage(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC,
			message.clone());
		MessageBusUtil.sendMessage(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC,
			message.clone());
	}

}