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

package com.liferay.commerce.account.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-account-group-service-configuration-name"
)
public interface CommerceAccountGroupServiceConfiguration {

	@Meta.AD(
		deflt = "" + CommerceAccountConstants.SITE_TYPE_B2C,
		name = "commerce-site-type", optionLabels = {"B2C", "B2B", "B2X"},
		optionValues = {"0", "1", "2"}, required = false
	)
	public int commerceSiteType();

}