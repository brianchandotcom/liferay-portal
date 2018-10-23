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

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides the actions that users can perform on resources shared with them.
 * Each sharing entry action is composed of an ID and a bitwise value used to
 * check permissions.
 *
 * @author Sergio González
 */
public enum SharingEntryAction {

	ADD_DISCUSSION("ADD_DISCUSSION", 4), UPDATE("UPDATE", 2), VIEW("VIEW", 1);

	/**
	 * Returns the sharing entry actions from the bitwise value.
	 *
	 * @param  bitwiseValue the bitwise value
	 * @return the sharing entry actions
	 */
	public static Collection<SharingEntryAction> getSharingEntryActions(
		long bitwiseValue) {

		return Stream.of(
			values()
		).filter(
			sharingEntryAction ->
				(sharingEntryAction.getBitwiseValue() & bitwiseValue) != 0
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Returns {@code true} if the sharing entry action's ID matches a valid
	 * sharing entry action.
	 *
	 * @param  actionId the sharing entry action's ID
	 * @return {@code true} if the sharing entry action's ID matches a valid
	 *         sharing entry action; {@code false} otherwise
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
	 * Returns the sharing entry action parsed from a sharing entry action's ID.
	 *
	 * @param  actionId the sharing entry action's ID
	 * @return the sharing entry action
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
	 * Returns the sharing entry action parsed from the bitwise value.
	 *
	 * @param  bitwiseValue the bitwise value
	 * @return the sharing entry action
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
	 * Returns the sharing entry action's ID.
	 *
	 * @return the sharing entry action's ID
	 */
	public String getActionId() {
		return _actionId;
	}

	/**
	 * Returns the sharing entry action's bitwise value.
	 *
	 * @return the bitwise value
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