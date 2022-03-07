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

package com.liferay.commerce.shipping.engine.fixed.internal.upgrade;

import com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v1_1_0.CommerceShippingFixedOptionRelUpgradeProcess;
import com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v2_2_0.util.CommerceShippingFixedOptionQualifierTable;
import com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v2_3_0.CommerceShippingFixedOptionUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceShippingEngineFixedUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce shipping engine fixed upgrade step registrator " +
					"STARTED");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommerceShippingFixedOptionRelUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			new com.liferay.commerce.shipping.engine.fixed.internal.upgrade.
				v2_0_0.CommerceShippingFixedOptionRelUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CShippingFixedOptionRel", "CommerceShippingFixedOption"
					};
				}

			});

		registry.register(
			"2.1.0", "2.2.0",
			CommerceShippingFixedOptionQualifierTable.create());

		registry.register(
			"2.2.0", "2.3.0", new CommerceShippingFixedOptionUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce shipping engine fixed upgrade step registrator " +
					"finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingEngineFixedUpgradeStepRegistrator.class);

}