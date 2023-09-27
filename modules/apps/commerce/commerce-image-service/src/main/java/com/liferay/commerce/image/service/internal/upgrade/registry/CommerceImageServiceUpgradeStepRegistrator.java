/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.image.service.internal.upgrade.registry;

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
@Component(service = UpgradeStepRegistrator.class)
public class CommerceImageServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
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