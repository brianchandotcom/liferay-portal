/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

/**
 * @author Michael C. Han
 */
public enum SnapshotState {

	FAILED((byte)2, true, false), IN_PROGRESS((byte)0, false, false),
	INCOMPATIBLE((byte)4, true, false), PARTIAL((byte)3, true, true),
	SUCCESS((byte)1, true, true);

	public byte getValue() {
		return _value;
	}

	public boolean isCompleted() {
		return _completed;
	}

	public boolean isRestorable() {
		return _restorable;
	}

	private SnapshotState(byte value, boolean restorable, boolean completed) {
		_value = value;
		_restorable = restorable;
		_completed = completed;
	}

	private final boolean _completed;
	private final boolean _restorable;
	private final byte _value;

}