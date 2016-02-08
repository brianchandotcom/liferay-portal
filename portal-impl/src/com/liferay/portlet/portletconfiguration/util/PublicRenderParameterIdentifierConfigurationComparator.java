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

package com.liferay.portlet.portletconfiguration.util;

import com.liferay.portal.kernel.model.PublicRenderParameter;

import java.util.Comparator;

/**
 * @author Alberto Montero
 */
public class PublicRenderParameterIdentifierConfigurationComparator
	implements Comparator<PublicRenderParameterConfiguration> {

	@Override
	public int compare(
		PublicRenderParameterConfiguration publicRenderParameterConfiguration1,
		PublicRenderParameterConfiguration
			publicRenderParameterConfiguration2) {

		PublicRenderParameter publicRenderParameter1 =
			publicRenderParameterConfiguration1.getPublicRenderParameter();
		PublicRenderParameter publicRenderParameter2 =
			publicRenderParameterConfiguration2.getPublicRenderParameter();

		String identifier1 = publicRenderParameter1.getIdentifier();
		String identifier2 = publicRenderParameter2.getIdentifier();

		return identifier1.compareTo(identifier2);
	}

}