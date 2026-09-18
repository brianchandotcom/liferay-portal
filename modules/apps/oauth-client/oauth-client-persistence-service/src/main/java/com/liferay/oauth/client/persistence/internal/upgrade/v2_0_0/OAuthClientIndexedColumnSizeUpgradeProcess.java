/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.BaseIndexedColumnSizeUpgradeProcess;

/**
 * @author Alvaro Saugar
 */
public class OAuthClientIndexedColumnSizeUpgradeProcess
	extends BaseIndexedColumnSizeUpgradeProcess {

	@Override
	protected int getMaxColumnLength() {
		return 255;
	}

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{"OAuthClientASLocalMetadata", "issuer"},
			{"OAuthClientASLocalMetadata", "localWellKnownURI"},
			{"OAuthClientASLocalMetadata", "oAuthASLocalWellKnownURI"},
			{"OAuthClientEntry", "authServerWellKnownURI"},
			{"OAuthClientEntry", "clientId"},
			{"OAuthClientPRLocalMetadata", "localWellKnownURI"},
			{"OAuthClientPRLocalMetadata", "protectedResourceURI"}
		};
	}

	@Override
	protected String[][] getTableAndUniqueIndexColumnNames() {
		return new String[][] {
			{"OAuthClientASLocalMetadata", "companyId", "issuer"},
			{"OAuthClientASLocalMetadata", "companyId", "localWellKnownURI"},
			{
				"OAuthClientASLocalMetadata", "companyId",
				"oAuthASLocalWellKnownURI"
			},
			{
				"OAuthClientEntry", "companyId", "authServerWellKnownURI",
				"clientId"
			},
			{"OAuthClientPRLocalMetadata", "companyId", "localWellKnownURI"},
			{"OAuthClientPRLocalMetadata", "companyId", "protectedResourceURI"}
		};
	}

}