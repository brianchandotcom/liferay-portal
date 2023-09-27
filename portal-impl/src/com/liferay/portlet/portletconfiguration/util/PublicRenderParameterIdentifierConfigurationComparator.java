/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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