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
		boolean socialInteractionsEnabled,
		boolean socialInteractionsAnyUserEnabled,
		boolean socialInteractionsSitesEnabled,
		boolean socialInteractionsSocialRelationTypesEnabled,
		String socialRelationTypes) {

		_socialInteractionsEnabled = socialInteractionsEnabled;
		_socialInteractionsAnyUserEnabled = socialInteractionsAnyUserEnabled;
		_socialInteractionsSitesEnabled = socialInteractionsSitesEnabled;
		_socialInteractionSocialRelationTypesEnabled =
			socialInteractionsSocialRelationTypesEnabled;
		_socialInteractionsSocialRelationTypes = socialRelationTypes;

		_socialInteractionsSocialRelationTypesArray =
			GetterUtil.getIntegerValues(
				StringUtil.split(_socialInteractionsSocialRelationTypes));
	}

	public String getSocialInteractionsSocialRelationTypes() {
		return _socialInteractionsSocialRelationTypes;
	}

	public int[] getSocialInteractionsSocialRelationTypesArray() {
		return _socialInteractionsSocialRelationTypesArray;
	}

	public boolean isSocialInteractionsAnyUserEnabled() {
		return _socialInteractionsAnyUserEnabled;
	}

	public boolean isSocialInteractionsEnabled() {
		return _socialInteractionsEnabled;
	}

	public boolean isSocialInteractionsSitesEnabled() {
		return _socialInteractionsSitesEnabled;
	}

	public boolean isSocialInteractionsSocialRelationTypesEnabled() {
		return _socialInteractionSocialRelationTypesEnabled;
	}

	private boolean _socialInteractionsAnyUserEnabled;
	private boolean _socialInteractionsEnabled;
	private boolean _socialInteractionSocialRelationTypesEnabled;
	private boolean _socialInteractionsSitesEnabled;
	private String _socialInteractionsSocialRelationTypes;
	private int[] _socialInteractionsSocialRelationTypesArray;

}