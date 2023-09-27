/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.credentials;

import com.liferay.osb.faro.engine.client.model.Credentials;

/**
 * @author Matthew Kong
 */
public class DummyCredentials implements Credentials {

	public static final String TYPE = "Dummy Authentication";

	@Override
	public String getType() {
		return TYPE;
	}

}