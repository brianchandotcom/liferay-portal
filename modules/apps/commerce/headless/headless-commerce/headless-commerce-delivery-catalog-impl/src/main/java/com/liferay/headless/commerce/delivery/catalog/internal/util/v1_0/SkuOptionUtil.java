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
import java.util.Locale;
import java.util.Map;

/**
 * @author Crescenzo Rega
 */
public class SkuOptionUtil {

	public static SkuOption[] getSkuOptions(
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionValueRelsMap,
			CPInstanceLocalService cpInstanceLocalService, Locale locale)
		throws Exception {

		List<SkuOption> skuOptions = new ArrayList<>();

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				entry : cpDefinitionOptionValueRelsMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel = entry.getKey();

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				entry.getValue();

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

				SkuOption skuOption = new SkuOption() {
					{
						setKey(
							() ->
								cpDefinitionOptionRel.
									getCPDefinitionOptionRelId());
						setPrice(
							() -> {
								BigDecimal priceBigDecimal =
									cpDefinitionOptionValueRel.getPrice();

								if (priceBigDecimal == null) {
									return BigDecimal.ZERO.toString();
								}

								return priceBigDecimal.toString();
							});
						setPriceType(cpDefinitionOptionRel::getPriceType);
						setQuantity(
							() -> String.valueOf(
								cpDefinitionOptionValueRel.getQuantity()));
						setSkuId(
							() -> {
								CPInstance cpInstance =
									cpInstanceLocalService.
										fetchCProductInstance(
											cpDefinitionOptionValueRel.
												getCProductId(),
											cpDefinitionOptionValueRel.
												getCPInstanceUuid());

								if (cpInstance == null) {
									return null;
								}

								return cpInstance.getCPInstanceId();
							});
						setSkuOptionId(
							() ->
								cpDefinitionOptionRel.
									getCPDefinitionOptionRelId());
						setSkuOptionKey(cpDefinitionOptionRel::getKey);
						setSkuOptionName(
							() -> cpDefinitionOptionRel.getName(locale));
						setSkuOptionValueId(
							() ->
								cpDefinitionOptionValueRel.
									getCPDefinitionOptionValueRelId());
						setSkuOptionValueKey(
							cpDefinitionOptionValueRel::getKey);
						setSkuOptionValueNames(
							() -> new String[] {
								cpDefinitionOptionValueRel.getName(locale)
							});
						setValue(
							() ->
								cpDefinitionOptionValueRel.
									getCPDefinitionOptionValueRelId());
					}
				};

				skuOptions.add(skuOption);
			}
		}

		return skuOptions.toArray(new SkuOption[0]);
	}

}