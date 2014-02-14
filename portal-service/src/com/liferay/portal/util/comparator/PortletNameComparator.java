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
 * @author Carlos Sierra Andrés
 */
public class PortletNameComparator implements Comparator<Portlet> {

	@Override
	public int compare(Portlet portlet, Portlet portlet2) {
		String portletName = portlet.getPortletName();
		String portletName2 = portlet2.getPortletName();

		if (portletName == null) {
			if (portletName2 == null) {
				return 0;
			}

			return -1;
		}

		return portletName.compareTo(portletName2);
	}

}