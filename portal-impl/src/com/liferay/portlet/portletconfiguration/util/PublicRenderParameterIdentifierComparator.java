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

package com.liferay.portlet.portletconfiguration.util;

import com.liferay.portal.model.PublicRenderParameter;

import java.util.Comparator;

/**
 * @author Alberto Montero
 */
public class PublicRenderParameterIdentifierComparator
	implements Comparator<PublicRenderParameter> {

	@Override
	public int compare(
		PublicRenderParameter publicRenderParameter1,
		PublicRenderParameter publicRenderParameter2) {

		String identifier1 = publicRenderParameter1.getIdentifier();
		String identifier2 = publicRenderParameter2.getIdentifier();

		return identifier1.compareTo(identifier2);
	}

}