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
import com.liferay.portal.util.PortletKeys;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletLuceneComparator
	implements Comparator<Portlet>, Serializable {

	@Override
	public int compare(Portlet portlet1, Portlet portlet2) {
		String portletId1 = portlet1.getPortletId();
		String portletId2 = portlet2.getPortletId();

		// Index document library last because it's usually the slowest.

		if (portletId1.equals(PortletKeys.DOCUMENT_LIBRARY)) {
			return 1;
		}
		else if (portletId2.equals(PortletKeys.DOCUMENT_LIBRARY)) {
			return -1;
		}
		else {
			return portletId1.compareTo(portletId2);
		}
	}

}