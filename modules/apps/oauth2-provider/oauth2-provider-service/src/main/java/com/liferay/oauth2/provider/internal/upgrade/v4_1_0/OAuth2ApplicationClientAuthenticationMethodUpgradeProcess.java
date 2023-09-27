/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.internal.upgrade.v4_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Arthur Chan
 */
public class OAuth2ApplicationClientAuthenticationMethodUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update OAuth2Application set clientAuthenticationMethod = " +
				"'client_secret_post' where (clientAuthenticationMethod is " +
					"null or clientAuthenticationMethod = '')");
		runSQL(
			"update OAuth2Application set clientAuthenticationMethod = " +
				"'none' where (clientSecret is null OR clientSecret = '')");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"OAuth2Application",
				"clientAuthenticationMethod VARCHAR(75) null"),
			UpgradeProcessFactory.addColumns(
				"OAuth2Application", "jwks VARCHAR(3999) null")
		};
	}

}