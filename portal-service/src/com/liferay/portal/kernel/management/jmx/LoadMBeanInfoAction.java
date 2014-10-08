/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class LoadMBeanInfoAction extends BaseJMXManageAction<MBean> {

	public LoadMBeanInfoAction(MBean mBean) {
		_mBean = mBean;
	}

	@Override
	public MBean action() throws ManageActionException {
		try {
			ObjectName objectName = _mBean.getObjectName();

			MBeanServer mBeanServer = getMBeanServer();

			MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);

			return new MBean(objectName, mBeanInfo);
		}
		catch (Exception e) {
			throw new ManageActionException(e);
		}
	}

	private final MBean _mBean;

}