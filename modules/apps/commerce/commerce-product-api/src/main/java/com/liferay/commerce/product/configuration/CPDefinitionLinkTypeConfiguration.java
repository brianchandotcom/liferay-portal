/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(
	category = "catalog", factoryInstanceLabelAttribute = "type",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.product.configuration.CPDefinitionLinkTypeConfiguration",
	localization = "content/Language",
	name = "cp-definition-link-type-configuration-name"
)
public interface CPDefinitionLinkTypeConfiguration {

	@Meta.AD(name = "type", required = false)
	public String type();

}