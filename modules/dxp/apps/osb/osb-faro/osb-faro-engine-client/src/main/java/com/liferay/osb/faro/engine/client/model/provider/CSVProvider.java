/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.provider;

import com.liferay.osb.faro.engine.client.model.Provider;

/**
 * @author Matthew Kong
 */
public class CSVProvider implements Provider {

	public static final String TYPE = "CSV";

	@Override
	public String getType() {
		return TYPE;
	}

}