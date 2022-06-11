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

package com.liferay.commerce.qualifier.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Riccardo Alberti
 */
@ExtendedObjectClassDefinition(
	category = "qualifiers", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.qualifier.configuration.CommerceDiscountCommerceQualifierConfiguration",
	localization = "content/Language",
	name = "commerce-discount-commerce-qualifier-configuration-name"
)
public interface CommerceDiscountCommerceQualifierConfiguration
	extends CommerceQualifierConfiguration {

	@Meta.AD(
		deflt = "account|account-group,channel,order-type",
		name = "allowed-target-keys", required = false
	)
	public String[] allowedTargetKeys();

	@Meta.AD(
		deflt = "account|order-type|channel,account|order-type,account|channel,account,account-group|order-type|channel,account-group|order-type,account-group|channel,account-group,order-type|channel,order-type,channel",
		name = "order-by-target-keys", required = false
	)
	public String[] orderByTargetKeys();

}