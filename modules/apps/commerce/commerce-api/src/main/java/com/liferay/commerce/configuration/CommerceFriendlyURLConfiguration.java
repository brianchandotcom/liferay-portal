/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceFriendlyURLConfiguration",
	localization = "content/Language",
	name = "commerce-friendly-url-configuration-name"
)
public interface CommerceFriendlyURLConfiguration {

	@Meta.AD(
		deflt = "order", description = "order-url-separator-help",
		name = "order-url-separator", required = false
	)
	public String orderURLSeparator();

}