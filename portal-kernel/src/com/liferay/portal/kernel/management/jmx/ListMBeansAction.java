/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management.jmx;

import com.liferay.portal.kernel.jmx.model.MBean;
import com.liferay.portal.kernel.management.ManageActionException;

import java.util.HashSet;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class ListMBeansAction extends BaseJMXManageAction<Set<MBean>> {

	public ListMBeansAction(String domainName) {
		_domainName = domainName;
	}

	@Override
	public Set<MBean> action() throws ManageActionException {
		try {
			MBeanServer mBeanServer = getMBeanServer();

			Set<ObjectName> objectNames = mBeanServer.queryNames(
				null, new ObjectName(_domainName.concat(":*")));

			Set<MBean> mBeans = new HashSet<>();

			for (ObjectName objectName : objectNames) {
				mBeans.add(new MBean(objectName));
			}

			return mBeans;
		}
		catch (MalformedObjectNameException malformedObjectNameException) {
			throw new ManageActionException(malformedObjectNameException);
		}
	}

	private final String _domainName;

}