/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util;

import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOptionTypeUtil {

	public static List<CommerceOptionType> getAllowedCommerceOptionTypes(
		List<CommerceOptionType> commerceOptionTypes,
		String[] allowedCommerceOptionTypes) {

		return ListUtil.filter(
			commerceOptionTypes,
			commerceOptionType -> ArrayUtil.contains(
				allowedCommerceOptionTypes, commerceOptionType.getKey()));
	}

}