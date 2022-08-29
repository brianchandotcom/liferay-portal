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