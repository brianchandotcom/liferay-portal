/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.client.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Riccardo Ferrari
 */
public class DataSourceConnectionException extends PortalException {

	public DataSourceConnectionException(String msg) {
		super(msg);
	}

}