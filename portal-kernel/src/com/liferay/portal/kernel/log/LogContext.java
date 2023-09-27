/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.log;

import java.util.Map;

/**
 * @author Tina Tian
 */
public interface LogContext {

	public Map<String, String> getContext(String logName);

	public String getName();

}