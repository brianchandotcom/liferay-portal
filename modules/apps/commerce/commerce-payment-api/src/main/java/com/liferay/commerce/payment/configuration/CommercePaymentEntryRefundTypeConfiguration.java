/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "payment", factoryInstanceLabelAttribute = "key",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.payment.configuration.CommercePaymentEntryRefundTypeConfiguration",
	localization = "content/Language",
	name = "commerce-payment-entry-refund-type-configuration-name"
)
public interface CommercePaymentEntryRefundTypeConfiguration {

	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(name = "key")
	public String key();

	@Meta.AD(name = "name", required = false)
	public LocalizedValuesMap name();

	@Meta.AD(name = "priority", required = false)
	public int priority();

}