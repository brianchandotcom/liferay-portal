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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class SocialInteractionsConfiguration {

	public SocialInteractionsConfiguration(
		SocialInteractionsType socialInteractionsType,
		boolean socialInteractionsSitesEnabled,
		String socialInteractionsSocialRelationTypes,
		boolean socialInteractionsSocialRelationTypesEnabled,
		SocialInteractionsConfiguration
			defaultSocialInteractionsConfiguration) {

		_socialInteractionsType = socialInteractionsType;
		_socialInteractionsSitesEnabled = socialInteractionsSitesEnabled;
		_socialInteractionsSocialRelationTypes =
			socialInteractionsSocialRelationTypes;
		_socialInteractionSocialRelationTypesEnabled =
			socialInteractionsSocialRelationTypesEnabled;
		_defaultSocialInteractionsConfiguration =
			defaultSocialInteractionsConfiguration;

		_socialInteractionsSocialRelationTypesArray =
			GetterUtil.getIntegerValues(
				StringUtil.split(_socialInteractionsSocialRelationTypes));
	}

	public String getSocialInteractionsSocialRelationTypes() {
		if (isInheritSocialInteractionsConfiguration() &&
			(_defaultSocialInteractionsConfiguration != null)) {

			return _defaultSocialInteractionsConfiguration.
				getSocialInteractionsSocialRelationTypes();
		}

		return _socialInteractionsSocialRelationTypes;
	}

	public int[] getSocialInteractionsSocialRelationTypesArray() {
		if (isInheritSocialInteractionsConfiguration() &&
			(_defaultSocialInteractionsConfiguration != null)) {

			return _defaultSocialInteractionsConfiguration.
				getSocialInteractionsSocialRelationTypesArray();
		}

		return _socialInteractionsSocialRelationTypesArray;
	}

	public boolean isInheritSocialInteractionsConfiguration() {
		if (_socialInteractionsType.equals(SocialInteractionsType.INHERIT)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSocialInteractionsAnyUserEnabled() {
		if (isInheritSocialInteractionsConfiguration() &&
			(_defaultSocialInteractionsConfiguration != null)) {

			return _defaultSocialInteractionsConfiguration.
				isSocialInteractionsAnyUserEnabled();
		}

		if (_socialInteractionsType.equals(SocialInteractionsType.ALL_USERS)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSocialInteractionsSelectUsersEnabled() {
		if (isInheritSocialInteractionsConfiguration() &&
			(_defaultSocialInteractionsConfiguration != null)) {

			return _defaultSocialInteractionsConfiguration.
				isSocialInteractionsSelectUsersEnabled();
		}

		if (_socialInteractionsType.equals(
				SocialInteractionsType.SELECT_USERS)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSocialInteractionsSitesEnabled() {
		if (isInheritSocialInteractionsConfiguration() &&
			(_defaultSocialInteractionsConfiguration != null)) {

			return _defaultSocialInteractionsConfiguration.
				isSocialInteractionsSitesEnabled();
		}

		return _socialInteractionsSitesEnabled;
	}

	public boolean isSocialInteractionsSocialRelationTypesEnabled() {
		if (isInheritSocialInteractionsConfiguration() &&
			(_defaultSocialInteractionsConfiguration != null)) {

			return _defaultSocialInteractionsConfiguration.
				isSocialInteractionsSocialRelationTypesEnabled();
		}

		return _socialInteractionSocialRelationTypesEnabled;
	}

	public enum SocialInteractionsType {

		ALL_USERS("all_users"), INHERIT("inherit"),
		SELECT_USERS("select_users");

		public static SocialInteractionsType parse(String value) {
			if (ALL_USERS.getValue().equals(value)) {
				return ALL_USERS;
			}
			else if (INHERIT.getValue().equals(value)) {
				return INHERIT;
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

	private final SocialInteractionsConfiguration
		_defaultSocialInteractionsConfiguration;
	private final boolean _socialInteractionSocialRelationTypesEnabled;
	private final boolean _socialInteractionsSitesEnabled;
	private final String _socialInteractionsSocialRelationTypes;
	private final int[] _socialInteractionsSocialRelationTypesArray;
	private final SocialInteractionsType _socialInteractionsType;

}