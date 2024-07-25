/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.portlets.web.internal.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jorge Ferrer
 */
public class PortletDisplayNameComparator extends OrderByComparator<Portlet> {

	public static PortletDisplayNameComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(Portlet portlet1, Portlet portlet2) {
		String portletDisplayName1 = StringPool.BLANK;

		if (portlet1 != null) {
			portletDisplayName1 = GetterUtil.getString(
				portlet1.getDisplayName());
		}

		String portletDisplayName2 = StringPool.BLANK;

		if (portlet2 != null) {
			portletDisplayName2 = GetterUtil.getString(
				portlet2.getDisplayName());
		}

		int value = portletDisplayName1.compareTo(portletDisplayName2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private PortletDisplayNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final PortletDisplayNameComparator _INSTANCE_ASCENDING =
		new PortletDisplayNameComparator(true);

	private static final PortletDisplayNameComparator _INSTANCE_DESCENDING =
		new PortletDisplayNameComparator(false);

	private final boolean _ascending;

}