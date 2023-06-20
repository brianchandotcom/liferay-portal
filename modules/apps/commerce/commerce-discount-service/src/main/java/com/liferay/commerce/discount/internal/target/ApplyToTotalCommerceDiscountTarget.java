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

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.discount.target.key=" + CommerceDiscountConstants.TARGET_TOTAL,
		"commerce.discount.target.order:Integer=50"
	},
	service = CommerceDiscountTarget.class
)
public class ApplyToTotalCommerceDiscountTarget
	extends BaseCommerceDiscountTarget {

	@Override
	public String getKey() {
		return CommerceDiscountConstants.TARGET_TOTAL;
	}

	@Override
	public Type getType() {
		return Type.APPLY_TO_TOTAL;
	}

}