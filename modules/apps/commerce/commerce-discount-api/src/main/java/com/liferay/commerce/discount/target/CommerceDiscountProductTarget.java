/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.target;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

/**
 * @author Marco Leo
 */
public interface CommerceDiscountProductTarget {

	public void contributeDocument(
			Document document, CommerceDiscount commerceDiscount)
		throws PortalException;

	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, CPDefinition cpDefinition)
		throws PortalException;

}