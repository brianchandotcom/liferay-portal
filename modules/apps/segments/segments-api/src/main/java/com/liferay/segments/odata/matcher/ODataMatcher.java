/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.odata.matcher;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public interface ODataMatcher<T> {

	public boolean matches(String filterString, T pattern)
		throws PortalException;

}