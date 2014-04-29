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
 */
public class SocialRelationConfiguration {

	public SocialRelationConfiguration(
		boolean interactionsEnabled, boolean interactionsAnyUserEnabled,
		boolean interactionsSitesEnabled,
		boolean interactionsSocialRelationTypesEnabled,
		String socialRelationTypes) {

		_interactionsEnabled = interactionsEnabled;
		_interactionsAnyUserEnabled = interactionsAnyUserEnabled;
		_interactionsSitesEnabled = interactionsSitesEnabled;
		_interactionSocialRelationTypesEnabled =
			interactionsSocialRelationTypesEnabled;
		_interactionsSocialRelationTypes = socialRelationTypes;

		_interactionsSocialRelationTypesArray = GetterUtil.getIntegerValues(
			StringUtil.split(_interactionsSocialRelationTypes));
	}

	public String getInteractionsSocialRelationTypes() {
		return _interactionsSocialRelationTypes;
	}

	public int[] getInteractionsSocialRelationTypesArray() {
		return _interactionsSocialRelationTypesArray;
	}

	public boolean isInteractionsAnyUserEnabled() {
		return _interactionsAnyUserEnabled;
	}

	public boolean isInteractionsEnabled() {
		return _interactionsEnabled;
	}

	public boolean isInteractionsSitesEnabled() {
		return _interactionsSitesEnabled;
	}

	public boolean isInteractionsSocialRelationTypesEnabled() {
		return _interactionSocialRelationTypesEnabled;
	}

	private boolean _interactionsAnyUserEnabled;
	private boolean _interactionsEnabled;
	private boolean _interactionSocialRelationTypesEnabled;
	private boolean _interactionsSitesEnabled;
	private String _interactionsSocialRelationTypes;
	private int[] _interactionsSocialRelationTypesArray;

}