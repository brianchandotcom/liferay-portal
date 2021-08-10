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

package com.liferay.commerce.image.service.internal.upgrade;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.image.upgrade.ImageCompanyIdUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Alicia García
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceImageServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new ImageCompanyIdUpgradeProcess<>(
				_commercePaymentMethodGroupRelLocalService::
					getActionableDynamicQuery,
				CommercePaymentMethodGroupRel::getCompanyId,
				CommercePaymentMethodGroupRel::getImageId),
			new ImageCompanyIdUpgradeProcess<>(
				_commerceShippingMethodLocalService::getActionableDynamicQuery,
				CommerceShippingMethod::getCompanyId,
				CommerceShippingMethod::getImageId));
	}

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

}