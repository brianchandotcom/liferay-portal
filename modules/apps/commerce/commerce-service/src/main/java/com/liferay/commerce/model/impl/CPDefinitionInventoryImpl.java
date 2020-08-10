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

package com.liferay.commerce.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionInventoryImpl extends CPDefinitionInventoryBaseImpl {

	public CPDefinitionInventoryImpl() {
	}

	public int[] getAllowedOrderQuantitiesArray() {
		String allowedOrderQuantitiesString = getAllowedOrderQuantities();

		if (Validator.isNull(allowedOrderQuantitiesString)) {
			return new int[0];
		}

		allowedOrderQuantitiesString = allowedOrderQuantitiesString.replaceAll(
			" *(, *)|(\\. *)|( +)", StringPool.COMMA);

		int[] allowedOrderQuantities = StringUtil.split(
			allowedOrderQuantitiesString, 0);

		Arrays.sort(allowedOrderQuantities);

		return allowedOrderQuantities;
	}

}