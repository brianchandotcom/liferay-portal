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

package com.liferay.sharing.constants;

import java.util.Objects;

/**
 * @author Sergio González
 */
public enum SharingEntryActionKey {

	ADD_DISCUSSION("ADD_DISCUSSION", 4), UPDATE("UPDATE", 2), VIEW("VIEW", 1);

	public static boolean isSupportedActionId(String actionId) {
		for (SharingEntryActionKey sharingEntryActionKey :
				SharingEntryActionKey.values()) {

			if (Objects.equals(sharingEntryActionKey.getActionId(), actionId)) {
				return true;
			}
		}

		return false;
	}

	public static SharingEntryActionKey parseFromActionId(String actionId) {
		if (Objects.equals(ADD_DISCUSSION.getActionId(), actionId)) {
			return ADD_DISCUSSION;
		}
		else if (Objects.equals(UPDATE.getActionId(), actionId)) {
			return UPDATE;
		}
		else if (Objects.equals(VIEW.getActionId(), actionId)) {
			return VIEW;
		}

		throw new IllegalArgumentException("Invalid action ID " + actionId);
	}

	public static SharingEntryActionKey parseFromBitwiseValue(
		long bitwiseValue) {

		if (Objects.equals(ADD_DISCUSSION.getBitwiseValue(), bitwiseValue)) {
			return ADD_DISCUSSION;
		}
		else if (Objects.equals(UPDATE.getBitwiseValue(), bitwiseValue)) {
			return UPDATE;
		}
		else if (Objects.equals(VIEW.getBitwiseValue(), bitwiseValue)) {
			return VIEW;
		}

		throw new IllegalArgumentException(
			"Invalid bitwise value " + bitwiseValue);
	}

	public String getActionId() {
		return _actionId;
	}

	public long getBitwiseValue() {
		return _bitwiseValue;
	}

	private SharingEntryActionKey(String actionId, long bitwiseValue) {
		_actionId = actionId;
		_bitwiseValue = bitwiseValue;
	}

	private final String _actionId;
	private final long _bitwiseValue;

}