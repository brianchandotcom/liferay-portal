/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

			Set<MBean> mBeans = new HashSet<MBean>(objectNames.size());

			for (ObjectName objectName : objectNames) {
				mBeans.add(new MBean(objectName));
			}

			return mBeans;
		}
		catch (MalformedObjectNameException mone) {
			throw new ManageActionException(mone);
		}
	}

	private String _domainName;

}