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

import com.liferay.commerce.price.CommercePriceListQualificationType;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	properties = {
		"commerce.price.list.qualification.type.priority:Integer=-1",
		"commerce.price.list.qualification.type.key=all"

	},
	service = CommercePriceListQualificationType.class)
public class AllCommercePriceListQualificationTypeImpl
	implements CommercePriceListQualificationType {

	@Override
	public boolean isQualified(HttpServletRequest httpServletRequest) {
		return true;
	}

}