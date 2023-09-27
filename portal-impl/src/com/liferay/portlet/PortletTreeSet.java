/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Portlet;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTreeSet extends TreeSet<Portlet> {

	public PortletTreeSet(Collection<Portlet> collection) {
		super(_portletIdComparator);

		addAll(collection);
	}

	public PortletTreeSet(Portlet... portlets) {
		super(_portletIdComparator);

		for (Portlet portlet : portlets) {
			add(portlet);
		}
	}

	private static final Comparator<Portlet> _portletIdComparator =
		Comparator.comparing(Portlet::getPortletId);

}