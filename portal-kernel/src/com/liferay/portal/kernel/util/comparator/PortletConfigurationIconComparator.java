/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;

import java.util.Comparator;

/**
 * @author Shuyang Zhou
 */
public class PortletConfigurationIconComparator
	implements Comparator<PortletConfigurationIcon> {

	public static final Comparator<PortletConfigurationIcon> INSTANCE =
		new PortletConfigurationIconComparator();

	@Override
	public int compare(
		PortletConfigurationIcon portletConfigurationIconFactory1,
		PortletConfigurationIcon portletConfigurationIconFactory2) {

		return Double.compare(
			portletConfigurationIconFactory2.getWeight(),
			portletConfigurationIconFactory1.getWeight());
	}

	private PortletConfigurationIconComparator() {
	}

}