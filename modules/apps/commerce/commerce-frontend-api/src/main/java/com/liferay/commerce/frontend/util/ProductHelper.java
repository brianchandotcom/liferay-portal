/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@ProviderType
public interface ProductHelper {

	public PriceModel getMinPriceModel(
			long cpDefinitionId, CommerceContext commerceContext, Locale locale)
		throws PortalException;

	public PriceModel getPriceModel(
			long cpInstanceId, String commerceOptionValuesJSON,
			BigDecimal quantity, String unitOfMeasureKey,
			CommerceContext commerceContext, Locale locale)
		throws PortalException;

	public ProductSettingsModel getProductSettingsModel(long cpDefinitionId)
		throws PortalException;

}