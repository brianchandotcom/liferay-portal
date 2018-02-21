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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Comparator;

/**
 * @author Carlos Sierra Andrés
 */
public class PortletNameComparator implements Comparator<Portlet> {

	@Override
	public int compare(Portlet portlet1, Portlet portlet2) {
		String portletName1 = StringPool.BLANK;

		if (portlet1 != null) {
			portletName1 = GetterUtil.getString(portlet1.getPortletName());
		}

		String portletName2 = StringPool.BLANK;

		if (portlet2 != null) {
			portletName2 = GetterUtil.getString(portlet2.getPortletName());
		}

		return portletName1.compareTo(portletName2);
	}

}