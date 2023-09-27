/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.util;

import java.util.Objects;

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
			if (Objects.equals(ALL_USERS.getValue(), value)) {
				return ALL_USERS;
			}
			else if (Objects.equals(SELECT_USERS.getValue(), value)) {
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