/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Crescenzo Rega
 */
@ExtendedObjectClassDefinition(
	category = "orders", factoryInstanceLabelAttribute = "key",
	featureFlagKey = "LPD-10562",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.configuration.CommerceReturnReasonConfiguration",
	localization = "content/Language",
	name = "commerce-return-reason-configuration-name"
)
public interface CommerceReturnReasonConfiguration {

	@Meta.AD(name = "key")
	public String key();

	@Meta.AD(name = "name", required = false)
	public LocalizedValuesMap name();

	@Meta.AD(name = "priority", required = false)
	public int priority();

}