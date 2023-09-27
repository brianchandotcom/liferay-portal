/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Set;

/**
 * @author Ryan Park
 * @author Brian Wing Shun Chan
 */
public interface CustomJspRegistry {

	public String getCustomJspFileName(
		String servletContextName, String fileName);

	public String getDisplayName(String servletContextName);

	public Set<String> getServletContextNames();

	public void registerServletContextName(
		String servletContextName, String displayName);

	public void unregisterServletContextName(String servletContextName);

}