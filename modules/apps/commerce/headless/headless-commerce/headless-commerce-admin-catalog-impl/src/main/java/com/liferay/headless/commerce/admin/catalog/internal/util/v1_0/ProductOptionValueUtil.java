/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductOptionValueUtil {

	public static CPDefinitionOptionValueRel
			addOrUpdateCPDefinitionOptionValueRel(
				CPDefinitionOptionValueRelService
					cpDefinitionOptionValueRelService,
				ProductOptionValue productOptionValue,
				long cpDefinitionOptionRelId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelService.fetchCPDefinitionOptionValueRel(
				cpDefinitionOptionRelId, productOptionValue.getKey());

		if (cpDefinitionOptionValueRel == null) {
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelService.addCPDefinitionOptionValueRel(
					cpDefinitionOptionRelId, productOptionValue.getKey(),
					LanguageUtils.getLocalizedMap(productOptionValue.getName()),
					GetterUtil.get(productOptionValue.getPriority(), 0D),
					serviceContext);
		}
		else {
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelService.
					updateCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId(),
						0, productOptionValue.getKey(),
						LanguageUtils.getLocalizedMap(
							productOptionValue.getName()),
						false, null,
						GetterUtil.get(
							productOptionValue.getPriority(),
							cpDefinitionOptionValueRel.getPriority()),
						BigDecimal.ZERO, StringPool.BLANK, serviceContext);
		}

		return cpDefinitionOptionValueRel;
	}

}