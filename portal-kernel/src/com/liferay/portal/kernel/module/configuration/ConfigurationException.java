/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.module.configuration;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class ConfigurationException extends PortalException {

	public ConfigurationException() {
	}

	public ConfigurationException(String msg) {
		super(msg);
	}

	public ConfigurationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ConfigurationException(Throwable throwable) {
		super(throwable);
	}

}