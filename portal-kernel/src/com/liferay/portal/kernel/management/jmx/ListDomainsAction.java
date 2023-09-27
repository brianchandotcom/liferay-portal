/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management.jmx;

import javax.management.MBeanServer;

/**
 * @author Shuyang Zhou
 */
public class ListDomainsAction extends BaseJMXManageAction<String[]> {

	@Override
	public String[] action() {
		MBeanServer mBeanServer = getMBeanServer();

		return mBeanServer.getDomains();
	}

}