/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.soap.extender.test;

/**
 * @author Carlos Sierra Andrés
 */
public class GreeterImpl implements Greeter {

	@Override
	public String greet() {
		return "Greetings.";
	}

}