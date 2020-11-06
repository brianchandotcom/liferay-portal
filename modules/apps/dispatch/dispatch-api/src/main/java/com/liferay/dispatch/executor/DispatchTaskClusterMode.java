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

package com.liferay.dispatch.executor;

import com.liferay.portal.kernel.scheduler.StorageType;

/**
 * @author Matija Petanjek
 */
public enum DispatchTaskClusterMode {

	ALL_NODES(1, StorageType.MEMORY), NOT_APPLICABLE(0, StorageType.PERSISTED),
	SINGLE_NODE(2, StorageType.PERSISTED);

	public static DispatchTaskClusterMode valueOf(int mode) {
		for (DispatchTaskClusterMode dispatchTaskClusterMode : values()) {
			if (mode == dispatchTaskClusterMode._mode) {
				return dispatchTaskClusterMode;
			}
		}

		throw new IllegalArgumentException("Illegal task cluster mode " + mode);
	}

	public int getMode() {
		return _mode;
	}

	public StorageType getStorageType() {
		return _storageType;
	}

	private DispatchTaskClusterMode(int mode, StorageType storageType) {
		_mode = mode;
		_storageType = storageType;
	}

	private final int _mode;
	private final StorageType _storageType;

}