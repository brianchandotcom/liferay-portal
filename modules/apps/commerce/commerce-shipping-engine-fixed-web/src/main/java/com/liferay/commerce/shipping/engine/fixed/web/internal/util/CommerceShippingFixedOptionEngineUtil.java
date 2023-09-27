/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.web.internal.util;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionEngineUtil {

	public static List<CommerceShippingFixedOption>
		getEligibleCommerceShippingFixedOptions(
			long commerceOrderTypeId,
			CommerceShippingFixedOptionQualifierLocalService
				commerceShippingFixedOptionQualifierLocalService,
			List<CommerceShippingFixedOption> commerceShippingFixedOptions) {

		List<CommerceShippingFixedOption> eligibleCommerceShippingFixedOptions =
			new ArrayList<>();

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			List<CommerceShippingFixedOptionQualifier>
				commerceShippingFixedOptionQualifiers =
					commerceShippingFixedOptionQualifierLocalService.
						getCommerceShippingFixedOptionQualifiers(
							CommerceOrderType.class.getName(),
							commerceShippingFixedOption.
								getCommerceShippingFixedOptionId());

			if ((commerceOrderTypeId > 0) &&
				ListUtil.isNotEmpty(commerceShippingFixedOptionQualifiers) &&
				!ListUtil.exists(
					commerceShippingFixedOptionQualifiers,
					commercePaymentMethodGroupRelQualifier -> {
						long classPK =
							commercePaymentMethodGroupRelQualifier.getClassPK();

						return classPK == commerceOrderTypeId;
					})) {

				continue;
			}

			eligibleCommerceShippingFixedOptions.add(
				commerceShippingFixedOption);
		}

		return eligibleCommerceShippingFixedOptions;
	}

}