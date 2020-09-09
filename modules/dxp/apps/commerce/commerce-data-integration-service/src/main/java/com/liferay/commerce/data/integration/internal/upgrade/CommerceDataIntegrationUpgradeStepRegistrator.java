/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.internal.upgrade;

import com.liferay.commerce.data.integration.internal.upgrade.v1_1_0.CommerceDataIntegrationProcessLogUpgradeProcess;
import com.liferay.commerce.data.integration.internal.upgrade.v2_0_0.CommerceDataIntegrationProcessSystemUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ethan Bustad
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceDataIntegrationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce data integration upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommerceDataIntegrationProcessLogUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			new CommerceDataIntegrationProcessSystemUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce data integration upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationUpgradeStepRegistrator.class);

}