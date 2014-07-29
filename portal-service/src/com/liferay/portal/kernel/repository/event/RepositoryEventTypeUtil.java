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

package com.liferay.portal.kernel.repository.event;

import com.liferay.portlet.documentlibrary.model.DLSyncConstants;

/**
 * @author Adolfo Pérez
 */
public class RepositoryEventTypeUtil {

	public static Class<? extends SyncRepositoryEventType> toSyncEvent(
		String event) {

		if (event.equals(DLSyncConstants.EVENT_ADD)) {
			return SyncRepositoryEventType.DelayedAdd.class;
		}
		else if (event.equals(DLSyncConstants.EVENT_UPDATE)) {
			return SyncRepositoryEventType.DelayedUpdate.class;
		}
		else {
			throw new IllegalArgumentException(
				String.format("Unsupported sync event %s", event));
		}
	}

}