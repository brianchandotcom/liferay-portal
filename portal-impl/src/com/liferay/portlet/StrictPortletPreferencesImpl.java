/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			getCompanyId(), getOwnerId(), getOwnerType(), getPlid(),
			getPortletId(), getOriginalXML(), getOriginalPreferences());
	}

}