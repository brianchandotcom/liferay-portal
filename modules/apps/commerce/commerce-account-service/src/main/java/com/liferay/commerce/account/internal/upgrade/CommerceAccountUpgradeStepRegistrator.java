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

package com.liferay.commerce.account.internal.upgrade;

import com.liferay.commerce.account.internal.upgrade.v1_1_0.CommerceAccountUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupCommerceAccountRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_3_0.CommerceAccountNameUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_4_0.CommerceAccountDefaultAddressesUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v2_0_0.CommerceAccountGroupSystemUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v4_0_0.CommerceAccountOrganizationRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v5_0_0.CommerceAccountUserRelUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceAccountUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce account upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0", new CommerceAccountUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0",
			new CommerceAccountGroupCommerceAccountRelUpgradeProcess(),
			new CommerceAccountGroupRelUpgradeProcess(),
			new CommerceAccountGroupUpgradeProcess());

		registry.register(
			"1.2.0", "1.3.0", new CommerceAccountNameUpgradeProcess());

		registry.register(
			"1.3.0", "1.4.0",
			new CommerceAccountDefaultAddressesUpgradeProcess());

		registry.register("1.4.0", "1.5.0", new DummyUpgradeProcess());

		registry.register(
			"1.5.0", "2.0.0", new CommerceAccountGroupSystemUpgradeProcess());

		registry.register(
			"2.0.0", "3.0.0",
			new com.liferay.commerce.account.internal.upgrade.v3_0_0.
				CommerceAccountUpgradeProcess());

		registry.register(
			"3.0.0", "4.0.0",
			new CommerceAccountOrganizationRelUpgradeProcess());

		registry.register(
			"4.0.0", "5.0.0", new CommerceAccountUserRelUpgradeProcess());

		registry.register(
			"5.0.0", "6.0.0",
			new com.liferay.commerce.account.internal.upgrade.v6_0_0.
				CommerceAccountGroupUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("Commerce account upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountUpgradeStepRegistrator.class);

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.account.service)(release.schema.version>=2.1.0))"
	)
	private Release _release;

}