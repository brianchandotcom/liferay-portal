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
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class CommerceDataIntegrationUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"COMMERCE DATA INTEGRATION UPGRADE STEP REGISTRATOR STARTED");
		}

		registry.register(
			_SCHEMA_VERSION_1_0_0, _SCHEMA_VERSION_1_1_0,
			new CommerceDataIntegrationProcessLogUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_1_1_0, _SCHEMA_VERSION_2_0_0,
			new CommerceDataIntegrationProcessSystemUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info(
				"COMMERCE DATA INTEGRATION UPGRADE STEP REGISTRATOR FINISHED");
		}
	}

	private static final String _SCHEMA_VERSION_1_0_0 = "1.0.0";

	private static final String _SCHEMA_VERSION_1_1_0 = "1.1.0";

	private static final String _SCHEMA_VERSION_2_0_0 = "2.0.0";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationUpgradeStepRegistrator.class);

}