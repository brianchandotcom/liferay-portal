/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public interface PortalLifecycle {

	public static final int METHOD_ALL = 0;

	public static final int METHOD_DESTROY = 1;

	public static final int METHOD_INIT = 2;

	public void portalDestroy();

	public void portalInit();

}