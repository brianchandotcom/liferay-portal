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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.price.CommercePriceListHelper;
import com.liferay.commerce.price.CommercePriceListQualificationTypeRegistry;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommercePriceListHelperImpl implements CommercePriceListHelper {

	@Override
	public CommercePriceList getCommercePriceList(
		HttpServletRequest httpServletRequest) {

		return null;
	}

	@Reference
	private CommercePriceListQualificationTypeRegistry
		_commercePriceListQualificationTypeRegistry;

}