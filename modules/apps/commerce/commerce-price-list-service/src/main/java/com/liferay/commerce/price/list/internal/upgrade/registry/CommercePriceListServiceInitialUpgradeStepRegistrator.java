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

package com.liferay.commerce.price.list.internal.upgrade.registry;

import com.liferay.commerce.price.list.internal.verify.CommercePriceListServiceVerifyProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Chaparro
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommercePriceListServiceInitialUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialUpgradeSteps(
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					_commercePriceListServiceVerifyProcess.
						verifyBasePriceLists();
				}

			});
	}

	@Reference
	private CommercePriceListServiceVerifyProcess
		_commercePriceListServiceVerifyProcess;

}