/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.executor;

import com.liferay.portal.kernel.scheduler.StorageType;

/**
 * @author Matija Petanjek
 */
public enum DispatchTaskClusterMode {

	ALL_NODES("all-nodes", 1, StorageType.MEMORY),
	NOT_APPLICABLE("not-applicable", 0, StorageType.PERSISTED),
	SINGLE_NODE_MEMORY_CLUSTERED(
		"single-node-memory-clustered", 3, StorageType.MEMORY_CLUSTERED),
	SINGLE_NODE_PERSISTED("single-node-persisted", 2, StorageType.PERSISTED);

	public static DispatchTaskClusterMode valueOf(int mode) {
		for (DispatchTaskClusterMode dispatchTaskClusterMode : values()) {
			if (mode == dispatchTaskClusterMode._mode) {
				return dispatchTaskClusterMode;
			}
		}

		throw new IllegalArgumentException("Illegal task cluster mode " + mode);
	}

	public String getLabel() {
		return _label;
	}

	public int getMode() {
		return _mode;
	}

	public StorageType getStorageType() {
		return _storageType;
	}

	private DispatchTaskClusterMode(
		String label, int mode, StorageType storageType) {

		_label = label;
		_mode = mode;
		_storageType = storageType;
	}

	private final String _label;
	private final int _mode;
	private final StorageType _storageType;

}