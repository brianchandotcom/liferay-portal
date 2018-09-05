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

package com.liferay.social.kernel.util;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class SocialInteractionsConfiguration {

	public SocialInteractionsConfiguration(
		boolean socialInteractionsFriendsEnabled,
		boolean socialInteractionsSitesEnabled,
		SocialInteractionsType socialInteractionsType) {

		_socialInteractionsFriendsEnabled = socialInteractionsFriendsEnabled;
		_socialInteractionsSitesEnabled = socialInteractionsSitesEnabled;
		_socialInteractionsType = socialInteractionsType;
	}

	public boolean isSocialInteractionsAnyUserEnabled() {
		if (_socialInteractionsType.equals(SocialInteractionsType.ALL_USERS)) {
			return true;
		}

		return false;
	}

	public boolean isSocialInteractionsFriendsEnabled() {
		return _socialInteractionsFriendsEnabled;
	}

	public boolean isSocialInteractionsSelectUsersEnabled() {
		if (_socialInteractionsType.equals(
				SocialInteractionsType.SELECT_USERS)) {

			return true;
		}

		return false;
	}

	public boolean isSocialInteractionsSitesEnabled() {
		return _socialInteractionsSitesEnabled;
	}

	public enum SocialInteractionsType {

		ALL_USERS("all_users"), SELECT_USERS("select_users");

		public static SocialInteractionsType parse(String value) {
			if (ALL_USERS.getValue().equals(value)) {
				return ALL_USERS;
			}
			else if (SELECT_USERS.getValue().equals(value)) {
				return SELECT_USERS;
			}

			throw new IllegalArgumentException("Invalid value " + value);
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private SocialInteractionsType(String value) {
			_value = value;
		}

		private final String _value;

	}

	private final boolean _socialInteractionsFriendsEnabled;
	private final boolean _socialInteractionsSitesEnabled;
	private final SocialInteractionsType _socialInteractionsType;

}