/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management.jmx;

import com.liferay.portal.kernel.jmx.model.MBean;
import com.liferay.portal.kernel.management.ManageActionException;

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

			return new MBean(objectName, mBeanServer.getMBeanInfo(objectName));
		}
		catch (Exception exception) {
			throw new ManageActionException(exception);
		}
	}

	private final MBean _mBean;

}