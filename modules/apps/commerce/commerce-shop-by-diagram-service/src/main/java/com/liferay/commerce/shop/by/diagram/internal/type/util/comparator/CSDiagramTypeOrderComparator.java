/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.internal.type.util.comparator;

import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CSDiagramTypeOrderComparator
	implements Comparator<ServiceWrapper<CSDiagramType>>, Serializable {

	public CSDiagramTypeOrderComparator() {
		this(true);
	}

	public CSDiagramTypeOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<CSDiagramType> serviceWrapper1,
		ServiceWrapper<CSDiagramType> serviceWrapper2) {

		int value = Integer.compare(
			MapUtil.getInteger(
				serviceWrapper1.getProperties(),
				"commerce.product.definition.diagram.type.order",
				Integer.MAX_VALUE),
			MapUtil.getInteger(
				serviceWrapper2.getProperties(),
				"commerce.product.definition.diagram.type.order",
				Integer.MAX_VALUE));

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}