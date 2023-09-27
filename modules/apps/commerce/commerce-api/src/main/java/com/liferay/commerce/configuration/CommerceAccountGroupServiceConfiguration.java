/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceAccountGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-account-group-service-configuration-name"
)
public interface CommerceAccountGroupServiceConfiguration {

	@Meta.AD(
		deflt = "" + CommerceChannelConstants.SITE_TYPE_B2C,
		name = "commerce-site-type", optionLabels = {"B2C", "B2B", "B2X"},
		optionValues = {"0", "1", "2"}, required = false
	)
	public int commerceSiteType();

}