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