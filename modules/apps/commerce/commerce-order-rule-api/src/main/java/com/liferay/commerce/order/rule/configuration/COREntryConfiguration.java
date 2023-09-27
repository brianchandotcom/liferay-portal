/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.order.rule.configuration.COREntryConfiguration",
	localization = "content/Language",
	name = "commerce-order-rule-entry-configuration-name"
)
public interface COREntryConfiguration {

	@Meta.AD(deflt = "15", name = "check-interval", required = false)
	public int checkInterval();

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

}