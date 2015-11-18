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

package com.liferay.portlet;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Tomas Polesovsky
 * @author Shuyang Zhou
 */
public class StrictPortletPreferencesImpl
	extends PortletPreferencesImpl implements Cloneable, Serializable {

	public StrictPortletPreferencesImpl() {
	}

	public StrictPortletPreferencesImpl(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml, Map<String, Preference> preferences) {

		super(companyId, ownerId, ownerType, plid, portletId, xml, preferences);
	}

	@Override
	public Object clone() {
		return new StrictPortletPreferencesImpl(
			companyId, getOwnerId(), getOwnerType(), getPlid(), getPortletId(),
			getOriginalXML(), getOriginalPreferences());
	}

}