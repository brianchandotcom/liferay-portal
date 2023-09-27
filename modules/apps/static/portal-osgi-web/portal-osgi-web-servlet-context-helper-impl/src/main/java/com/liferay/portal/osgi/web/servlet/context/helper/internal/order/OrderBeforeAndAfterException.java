/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.context.helper.internal.order;

/**
 * @author Vernon Singleton
 * @author Juan González
 */
public class OrderBeforeAndAfterException extends Exception {

	public OrderBeforeAndAfterException(String configName, String name) {
		super(configName + " cannot be both before and after " + name);
	}

}