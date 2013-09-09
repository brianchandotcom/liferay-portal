/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 */
public class StrictPortletPreferencesImpl
	extends PortletPreferencesImpl
	implements Cloneable, Serializable {

	public StrictPortletPreferencesImpl() {
		super();
	}

	public StrictPortletPreferencesImpl(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml, Map<String, Preference> preferences) {

		super(companyId, ownerId, ownerType, plid, portletId, xml, preferences);

		_companyId = companyId;
	}

	public StrictPortletPreferencesImpl(
		String xml, Map<String, Preference > preferences) {

		super(xml, preferences);
	}

	@Override
	public Object clone() {
		return new StrictPortletPreferencesImpl(
			_companyId, getOwnerId(), getOwnerType(), getPlid(), getPortletId(),
			getOriginalXML(), getOriginalPreferences());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof StrictPortletPreferencesImpl)) {
			return false;
		}

		return super.equals(obj);
	}

	private long _companyId;

}