/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.access.control;

import com.liferay.portal.kernel.security.access.control.AccessControlled;

import java.lang.reflect.Method;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public interface AccessControlAdvisor {

	public void accept(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException;

}