/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.checkout.web.internal.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Balázs Breier
 */
@ExtendedObjectClassDefinition(
	category = "orders",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.checkout.web.internal.portlet.configuration.CommerceCheckoutPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-checkout-web-portlet-instance-configuration-name"
)
public interface CommerceCheckoutPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "false", name = "order-summary-show-full-address",
		required = false
	)
	public boolean orderSummaryShowFullAddress();

	@Meta.AD(
		deflt = "false", name = "order-summary-show-phone-number",
		required = false
	)
	public boolean orderSummaryShowPhoneNumber();

}