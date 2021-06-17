/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.discount.target;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

/**
 * @author Marco Leo
 */
public interface CommerceDiscountSKUTarget {

	public void contributeDocument(
			Document document, CommerceDiscount commerceDiscount)
		throws PortalException;

	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, CPInstance cpInstance)
		throws PortalException;

}