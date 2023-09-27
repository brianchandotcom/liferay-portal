/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.validator;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public interface CommerceDiscountValidatorRegistry {

	public CommerceDiscountValidator getCommerceDiscountValidator(String key);

	public List<CommerceDiscountValidator> getCommerceDiscountValidators();

	public List<CommerceDiscountValidator> getCommerceDiscountValidators(
		String... types);

}