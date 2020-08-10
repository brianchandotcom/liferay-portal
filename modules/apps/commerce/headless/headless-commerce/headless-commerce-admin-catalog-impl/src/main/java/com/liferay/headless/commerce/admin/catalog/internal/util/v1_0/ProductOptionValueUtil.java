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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductOptionValueUtil {

	public static CPDefinitionOptionValueRel upsertCPDefinitionOptionValueRel(
			CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService,
			ProductOptionValue productOptionValue, long cpDefinitionOptionRelId,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelService.fetchCPDefinitionOptionValueRel(
				cpDefinitionOptionRelId, productOptionValue.getKey());

		if (cpDefinitionOptionValueRel == null) {
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelService.addCPDefinitionOptionValueRel(
					cpDefinitionOptionRelId,
					LanguageUtils.getLocalizedMap(productOptionValue.getName()),
					GetterUtil.get(productOptionValue.getPriority(), 0D),
					productOptionValue.getKey(), serviceContext);
		}
		else {
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelService.
					updateCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
						LanguageUtils.getLocalizedMap(
							productOptionValue.getName()),
						GetterUtil.get(
							productOptionValue.getPriority(),
							cpDefinitionOptionValueRel.getPriority()),
						productOptionValue.getKey(), serviceContext);
		}

		return cpDefinitionOptionValueRel;
	}

}