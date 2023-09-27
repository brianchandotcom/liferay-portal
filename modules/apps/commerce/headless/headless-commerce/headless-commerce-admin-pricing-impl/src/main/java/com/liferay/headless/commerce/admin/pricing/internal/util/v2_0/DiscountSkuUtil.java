/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountSku;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountSkuUtil {

	public static CommerceDiscountRel addCommerceDiscountRel(
			CommerceDiscount commerceDiscount,
			CommerceDiscountRelService commerceDiscountRelService,
			CPInstanceLocalService cpInstanceLocalService,
			CPInstanceUnitOfMeasureLocalService
				cpInstanceUnitOfMeasureLocalService,
			DiscountSku discountSku, ServiceContextHelper serviceContextHelper)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			discountSku.getSkuId());

		UnicodeProperties typeSettingsUnicodeProperties = null;
		String unitOfMeasureKey = discountSku.getUnitOfMeasureKey();

		if (unitOfMeasureKey != null) {
			cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
				cpInstance.getCPInstanceId(), unitOfMeasureKey);

			typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				HashMapBuilder.put(
					"unitOfMeasureKey", unitOfMeasureKey
				).build(),
				true
			).build();
		}

		return commerceDiscountRelService.addCommerceDiscountRel(
			commerceDiscount.getCommerceDiscountId(),
			CPInstance.class.getName(), cpInstance.getCPInstanceId(),
			typeSettingsUnicodeProperties,
			serviceContextHelper.getServiceContext());
	}

}