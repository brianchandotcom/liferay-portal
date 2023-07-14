/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuOption;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Crescenzo Rega
 */
public class SkuOptionUtil {

	public static SkuOption[] getSkuOptions(
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionValueRelsMap,
			CPInstanceLocalService cpInstanceLocalService)
		throws Exception {

		List<SkuOption> skuOptions = new ArrayList<>();

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				entry : cpDefinitionOptionValueRelsMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel = entry.getKey();

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				entry.getValue();

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

				CPInstance cpInstance =
					cpInstanceLocalService.fetchCProductInstance(
						cpDefinitionOptionValueRel.getCProductId(),
						cpDefinitionOptionValueRel.getCPInstanceUuid());
				BigDecimal priceBigDecimal =
					cpDefinitionOptionValueRel.getPrice();

				SkuOption skuOption = new SkuOption() {
					{
						key =
							cpDefinitionOptionRel.getCPDefinitionOptionRelId();
						price =
							(priceBigDecimal == null) ?
								BigDecimal.ZERO.toString() :
									priceBigDecimal.toString();
						priceType = cpDefinitionOptionRel.getPriceType();
						quantity = String.valueOf(
							cpDefinitionOptionValueRel.getQuantity());
						skuId = (cpInstance == null) ? null :
							cpInstance.getCPInstanceId();
						skuOptionId =
							cpDefinitionOptionRel.getCPDefinitionOptionRelId();
						skuOptionKey = cpDefinitionOptionRel.getKey();
						skuOptionValueId =
							cpDefinitionOptionValueRel.
								getCPDefinitionOptionValueRelId();
						skuOptionValueKey = cpDefinitionOptionValueRel.getKey();
						value =
							cpDefinitionOptionValueRel.
								getCPDefinitionOptionValueRelId();
					}
				};

				skuOptions.add(skuOption);
			}
		}

		return skuOptions.toArray(new SkuOption[0]);
	}

}