/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Portlet;

import java.util.Comparator;

/**
 * @author Jorge Ferrer
 * @author Minhchau Dang
 * @author Brian Wing Shun Chan
 */
public class PortletControlPanelWeightComparator
	implements Comparator<Portlet> {

	@Override
	public int compare(Portlet portlet1, Portlet portlet2) {
		double portletWeight1 = portlet1.getControlPanelEntryWeight();
		double portletWeight2 = portlet2.getControlPanelEntryWeight();

		int value = Double.compare(portletWeight1, portletWeight2);

		if (value != 0) {
			return value;
		}

		String portletId1 = portlet1.getPortletId();
		String portletId2 = portlet2.getPortletId();

		return portletId1.compareTo(portletId2);
	}

}