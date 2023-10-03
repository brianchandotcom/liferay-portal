/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.product.configuration.CPDefinitionOptionRelConfiguration",
	localization = "content/Language",
	name = "cp-definition-option-rel-configuration-name"
)
public interface CPDefinitionOptionRelConfiguration {

	@Meta.AD(
		deflt = "false", name = "show-unselectable-options", required = false
	)
	public boolean showUnselectableOptions();

}