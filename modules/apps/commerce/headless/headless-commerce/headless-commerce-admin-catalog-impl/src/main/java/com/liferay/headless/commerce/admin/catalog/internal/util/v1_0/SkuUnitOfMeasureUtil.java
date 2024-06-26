/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

/**
 * @author João Victor Cordeiro
 */
public class SkuUnitOfMeasureUtil {

	public static CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureService
				cpInstanceUnitOfMeasureService,
			CPInstance cpInstance, SkuUnitOfMeasure skuUnitOfMeasure,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = LanguageUtils.getLocalizedMap(
			skuUnitOfMeasure.getName());

		return cpInstanceUnitOfMeasureService.
			addOrUpdateCPInstanceUnitOfMeasure(
				cpInstance.getCPInstanceId(),
				GetterUtil.get(skuUnitOfMeasure.getActive(), true),
				BigDecimalUtil.get(
					skuUnitOfMeasure.getIncrementalOrderQuantity(),
					BigDecimal.ONE),
				GetterUtil.get(skuUnitOfMeasure.getKey(), StringPool.BLANK),
				nameMap, GetterUtil.get(skuUnitOfMeasure.getPrecision(), 1),
				GetterUtil.get(skuUnitOfMeasure.getPrimary(), false),
				GetterUtil.get(skuUnitOfMeasure.getPriority(), 0),
				BigDecimalUtil.get(skuUnitOfMeasure.getRate(), BigDecimal.ONE),
				GetterUtil.get(skuUnitOfMeasure.getSku(), cpInstance.getSku()));
	}

}