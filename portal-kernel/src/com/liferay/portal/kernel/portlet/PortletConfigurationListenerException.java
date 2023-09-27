/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

/**
 * @author Lourdes Fernández Besada
 */
public class PortletConfigurationListenerException extends RuntimeException {

	public PortletConfigurationListenerException() {
	}

	public PortletConfigurationListenerException(String msg) {
		super(msg);
	}

	public PortletConfigurationListenerException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public PortletConfigurationListenerException(Throwable throwable) {
		super(throwable);
	}

}