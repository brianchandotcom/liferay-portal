/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceSubscriptionConfiguration",
	localization = "content/Language",
	name = "commerce-subscription-configuration-name"
)
public interface CommerceSubscriptionConfiguration {

	@Meta.AD(
		deflt = "10", name = "renewal-check-interval-minutes", required = false
	)
	public int renewalCheckIntervalMinutes();

	@Meta.AD(
		deflt = "false", name = "subscription-cancellation-allowed",
		required = false
	)
	public boolean subscriptionCancellationAllowed();

	@Meta.AD(
		deflt = "false", name = "subscription-suspension-allowed",
		required = false
	)
	public boolean subscriptionSuspensionAllowed();

}