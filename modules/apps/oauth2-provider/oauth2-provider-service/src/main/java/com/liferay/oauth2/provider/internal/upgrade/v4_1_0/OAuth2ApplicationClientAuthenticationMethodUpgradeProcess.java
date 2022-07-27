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

package com.liferay.oauth2.provider.internal.upgrade.v4_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Arthur Chan
 */
public class OAuth2ApplicationClientAuthenticationMethodUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn(
			"OAuth2Application", "clientAuthenticationMethod",
			"VARCHAR(75) null");

		runSQL(
			"update OAuth2Application set clientAuthenticationMethod = " +
				"'client_secret_post' where (clientAuthenticationMethod is " +
					"null or clientAuthenticationMethod = '')");
		runSQL(
			"update OAuth2Application set clientAuthenticationMethod = " +
				"'none' where (clientSecret is null OR clientSecret = '')");

		alterTableAddColumn("OAuth2Application", "jwks", "VARCHAR(3999) null");
	}

}