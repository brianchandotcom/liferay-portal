/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v3_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseIndexedColumnSizeUpgradeProcessTestCase;

import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class OpenIdConnectSessionIndexedColumnSizeUpgradeProcessTest
	extends BaseIndexedColumnSizeUpgradeProcessTestCase {

	@Override
	protected int getNewColumnLength() {
		return 255;
	}

	@Override
	protected int getOldColumnLength() {
		return 256;
	}

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{"OpenIdConnectSession", "authServerWellKnownURI"},
			{"OpenIdConnectSession", "clientId"}
		};
	}

	@Override
	protected String getUpgradeProcessClassName() {
		return "com.liferay.portal.security.sso.openid.connect.persistence." +
			"internal.upgrade.v3_0_0." +
				"OpenIdConnectSessionIndexedColumnSizeUpgradeProcess";
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Inject(
		filter = "component.name=com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.registry.OpenIdConnectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}