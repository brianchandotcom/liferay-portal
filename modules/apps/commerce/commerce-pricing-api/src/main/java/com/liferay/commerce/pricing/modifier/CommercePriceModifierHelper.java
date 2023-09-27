/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.modifier;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Riccardo Alberti
 */
@ProviderType
public interface CommercePriceModifierHelper {

	public BigDecimal applyCommercePriceModifier(
			long commercePriceListId, long cpDefinitionId,
			CommerceMoney originalCommerceMoney)
		throws PortalException;

	public boolean hasCommercePriceModifiers(
			long commercePriceListId, long cpDefinitionId)
		throws PortalException;

}