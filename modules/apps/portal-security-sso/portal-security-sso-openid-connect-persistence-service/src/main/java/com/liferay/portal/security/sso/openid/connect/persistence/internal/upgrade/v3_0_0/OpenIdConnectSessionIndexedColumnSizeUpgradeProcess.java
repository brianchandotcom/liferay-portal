/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v3_0_0;

import com.liferay.portal.kernel.upgrade.BaseIndexedColumnSizeUpgradeProcess;

/**
 * @author Alvaro Saugar
 */
public class OpenIdConnectSessionIndexedColumnSizeUpgradeProcess
	extends BaseIndexedColumnSizeUpgradeProcess {

	@Override
	protected int getMaxColumnLength() {
		return 255;
	}

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{"OpenIdConnectSession", "authServerWellKnownURI"},
			{"OpenIdConnectSession", "clientId"}
		};
	}

	@Override
	protected String[][] getTableAndUniqueIndexColumnNames() {
		return new String[][] {
			{
				"OpenIdConnectSession", "userId", "authServerWellKnownURI",
				"clientId"
			}
		};
	}

}