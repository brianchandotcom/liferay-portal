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