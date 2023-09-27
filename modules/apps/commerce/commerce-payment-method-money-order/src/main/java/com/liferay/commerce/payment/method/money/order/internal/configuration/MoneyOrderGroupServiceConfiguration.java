/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.money.order.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "payment", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.payment.method.money.order.internal.configuration.MoneyOrderGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-payment-method-money-order-group-service-configuration-name"
)
public interface MoneyOrderGroupServiceConfiguration {

	@Meta.AD(name = "message-as-localized-xml", required = false)
	public String messageAsLocalizedXML();

	@Meta.AD(name = "show-message-page", required = false)
	public boolean showMessagePage();

}