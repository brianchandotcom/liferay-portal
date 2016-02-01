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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;

import java.util.Comparator;

/**
 * @author Shuyang Zhou
 */
public class PortletConfigurationIconFactoryComparator
	implements Comparator<PortletConfigurationIconFactory> {

	public static final Comparator<PortletConfigurationIconFactory> INSTANCE =
		new PortletConfigurationIconFactoryComparator();

	@Override
	public int compare(
		PortletConfigurationIconFactory portletConfigurationIconFactory1,
		PortletConfigurationIconFactory portletConfigurationIconFactory2) {

		return Double.compare(
			portletConfigurationIconFactory2.getWeight(),
			portletConfigurationIconFactory1.getWeight());
	}

	private PortletConfigurationIconFactoryComparator() {
	}

}