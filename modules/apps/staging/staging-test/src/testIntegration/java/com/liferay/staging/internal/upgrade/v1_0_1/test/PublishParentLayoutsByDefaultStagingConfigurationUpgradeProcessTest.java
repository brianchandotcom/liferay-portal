/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.version.Version;
import com.liferay.staging.internal.upgrade.BaseStagingConfigurationUpgradeProcessTestCase;

import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class PublishParentLayoutsByDefaultStagingConfigurationUpgradeProcessTest
	extends BaseStagingConfigurationUpgradeProcessTestCase {

	@Override
	protected String getPropertyName() {
		return "publishParentLayoutsByDefault";
	}

	@Override
	protected Version getUpgradeStepVersion() {
		return new Version(1, 0, 1);
	}

}