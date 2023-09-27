/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.top.head.extender;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public interface TopHeadResources {

	public Collection<String> getAuthenticatedJsResourcePaths();

	public Collection<String> getJsResourcePaths();

	public String getServletContextPath();

}