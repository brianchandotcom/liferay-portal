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

package com.liferay.dispatch.internal.core.scheduler;

import com.liferay.dispatch.core.scheduler.ScheduledTaskDispatchTrigger;
import com.liferay.dispatch.model.impl.DispatchTriggerImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.scheduler.StorageType;

/**
 * @author Matija Petanjek
 */
public class ScheduledTaskDispatchTriggerImpl
	extends DispatchTriggerImpl implements ScheduledTaskDispatchTrigger {

	public ScheduledTaskDispatchTriggerImpl(
		String destinationName, String groupName, StorageType storageType) {

		_destinationName = destinationName;
		_groupName = groupName;
		_storageType = storageType;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getSimpleName() {
		String name = getName();

		return name.substring(name.lastIndexOf(StringPool.PERIOD) + 1);
	}

	public StorageType getStorageType() {
		return _storageType;
	}

	private final String _destinationName;
	private final String _groupName;
	private final StorageType _storageType;

}