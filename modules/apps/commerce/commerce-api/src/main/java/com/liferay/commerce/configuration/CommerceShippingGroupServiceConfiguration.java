/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "shipping", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-shipping-group-service-configuration-name"
)
public interface CommerceShippingGroupServiceConfiguration {

	@Meta.AD(
		deflt = "address", name = "commerce-shipping-origin-locator-key",
		required = false
	)
	public String commerceShippingOriginLocatorKey();

}