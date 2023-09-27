/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceShippingEngineException;

import java.util.List;
import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public interface CommerceShippingEngine {

	public String getCommerceShippingOptionLabel(String name, Locale locale);

	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException;

	public String getDescription(Locale locale);

	public List<CommerceShippingOption> getEnabledCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException;

	public String getName(Locale locale);

}