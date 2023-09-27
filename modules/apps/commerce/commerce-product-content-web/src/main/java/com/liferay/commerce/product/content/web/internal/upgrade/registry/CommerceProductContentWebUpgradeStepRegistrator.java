/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.upgrade.registry;

import com.liferay.commerce.product.content.web.internal.upgrade.v1_0_0.PortletPreferenceValueUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Danny Situ
 */
@Component(service = UpgradeStepRegistrator.class)
public class CommerceProductContentWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce product content web upgrade step registrator " +
					"started");
		}

		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0", new PortletPreferenceValueUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce product content web upgrade step registrator " +
					"finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductContentWebUpgradeStepRegistrator.class);

}