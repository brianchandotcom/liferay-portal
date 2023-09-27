/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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