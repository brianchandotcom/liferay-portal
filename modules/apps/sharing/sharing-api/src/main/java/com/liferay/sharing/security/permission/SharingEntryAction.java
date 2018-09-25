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

package com.liferay.sharing.security.permission;

import java.util.Objects;

/**
 * Provides the actions that a user can perform on a resource that has been
 * shared with him.
 *
 * Each sharing entry action is composed of an actionId that is used to identify
 * the sharing entry action and a bitwiseValue that is used to perform
 * permission checks.
 *
 * @author Sergio González
 * @review
 */
public enum SharingEntryAction {

	ADD_DISCUSSION("ADD_DISCUSSION", 4), UPDATE("UPDATE", 2), VIEW("VIEW", 1);

	/**
	 * Returns <code>true</code> if the action id matches a valid sharing entry
	 * action.
	 *
	 * @param  actionId the action id of the sharing entry action to be checked
	 * @return <code>true</code> if the action id matches a valid sharing entry action
	 */
	public static boolean isSupportedActionId(String actionId) {
		for (SharingEntryAction sharingEntryAction :
				SharingEntryAction.values()) {

			if (Objects.equals(sharingEntryAction.getActionId(), actionId)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns a sharing entry action parsed from the action id.
	 *
	 * @param  actionId the action id of the sharing entry action to be parsed
	 * @return a {@link SharingEntryAction} with the action id
	 */
	public static SharingEntryAction parseFromActionId(String actionId) {
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

	/**
	 * Returns a sharing entry action parsed from the bitwise value.
	 *
	 * @param  bitwiseValue the bitwise value of the sharing entry action to be parsed
	 * @return a {@link SharingEntryAction} with the bitwise value
	 */
	public static SharingEntryAction parseFromBitwiseValue(long bitwiseValue) {
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

	/**
	 * Returns the action id of the sharing entry action.
	 *
	 * @return the action id of the sharing entry action
	 */
	public String getActionId() {
		return _actionId;
	}

	/**
	 * Returns the bitwise value of the sharing entry action.
	 *
	 * @return the bitwise value of the sharing entry action
	 */
	public long getBitwiseValue() {
		return _bitwiseValue;
	}

	private SharingEntryAction(String actionId, long bitwiseValue) {
		_actionId = actionId;
		_bitwiseValue = bitwiseValue;
	}

	private final String _actionId;
	private final long _bitwiseValue;

}