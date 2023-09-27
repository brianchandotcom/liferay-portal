/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public interface CustomUserAttributes extends Cloneable {

	public Object clone();

	public String getValue(String name, Map<String, String> userInfo);

}