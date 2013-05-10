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

package com.liferay.portal.util.comparator;

import com.liferay.portal.model.Portlet;

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