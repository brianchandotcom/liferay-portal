/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.type;

import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Riccardo Alberti
 */
public interface CommercePriceModifierType {

	public BigDecimal evaluate(
			BigDecimal commercePrice,
			CommercePriceModifier commercePriceModifier)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

}