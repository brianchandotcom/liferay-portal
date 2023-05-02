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

package com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Crescenzo Rega
 */
public class SkuOptionUtil {

	public static SkuOption[] getSkuOptions(
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelsMap,
			String languageId)
		throws Exception {

		List<SkuOption> skuOptions = new ArrayList<>();

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				entry : cpDefinitionOptionRelsMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel = entry.getKey();

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				entry.getValue();

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

				SkuOption skuOption = new SkuOption() {
					{
						key =
							cpDefinitionOptionRel.getCPDefinitionOptionRelId();
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