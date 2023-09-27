/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductOptionUtil {

	public static CPDefinitionOptionRel addOrUpdateCPDefinitionOptionRel(
			CPDefinitionOptionRelService cpDefinitionOptionRelService,
			CPOptionService cpOptionService, ProductOption productOption,
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		CPOption cpOption = cpOptionService.getCPOption(
			productOption.getOptionId());

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelService.fetchCPDefinitionOptionRel(
				cpDefinitionId, cpOption.getCPOptionId());

		if (cpDefinitionOptionRel == null) {
			cpDefinitionOptionRel =
				cpDefinitionOptionRelService.addCPDefinitionOptionRel(
					cpDefinitionId, cpOption.getCPOptionId(),
					LanguageUtils.getLocalizedMap(productOption.getName()),
					LanguageUtils.getLocalizedMap(
						productOption.getDescription()),
					GetterUtil.get(
						productOption.getFieldType(),
						cpOption.getCommerceOptionTypeKey()),
					GetterUtil.get(productOption.getPriority(), 0D),
					GetterUtil.get(
						productOption.getFacetable(), cpOption.isFacetable()),
					GetterUtil.get(
						productOption.getRequired(), cpOption.isRequired()),
					GetterUtil.get(
						productOption.getSkuContributor(),
						cpOption.isSkuContributor()),
					true, serviceContext);
		}
		else {
			cpDefinitionOptionRel =
				cpDefinitionOptionRelService.updateCPDefinitionOptionRel(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
					cpDefinitionOptionRel.getCPOptionId(),
					LanguageUtils.getLocalizedMap(productOption.getName()),
					LanguageUtils.getLocalizedMap(
						productOption.getDescription()),
					GetterUtil.get(
						productOption.getFieldType(),
						cpDefinitionOptionRel.getCommerceOptionTypeKey()),
					GetterUtil.get(
						productOption.getPriority(),
						cpDefinitionOptionRel.getPriority()),
					GetterUtil.get(
						productOption.getFacetable(),
						cpDefinitionOptionRel.isFacetable()),
					GetterUtil.get(
						productOption.getRequired(),
						cpDefinitionOptionRel.isRequired()),
					GetterUtil.get(
						productOption.getSkuContributor(),
						cpDefinitionOptionRel.isSkuContributor()),
					serviceContext);
		}

		return cpDefinitionOptionRel;
	}

}