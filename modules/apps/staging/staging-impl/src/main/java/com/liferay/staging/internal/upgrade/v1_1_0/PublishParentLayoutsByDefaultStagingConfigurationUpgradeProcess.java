/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.upgrade.v1_1_0;

import com.liferay.staging.internal.upgrade.BaseStagingConfigurationUpgradeProcess;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alberto Javier Moreno Lage
 */
public class PublishParentLayoutsByDefaultStagingConfigurationUpgradeProcess
	extends BaseStagingConfigurationUpgradeProcess {

	public PublishParentLayoutsByDefaultStagingConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		super(configurationAdmin);
	}

	@Override
	protected String getStagingConfigurationName() {
		return "publishParentLayoutsByDefault";
	}

}