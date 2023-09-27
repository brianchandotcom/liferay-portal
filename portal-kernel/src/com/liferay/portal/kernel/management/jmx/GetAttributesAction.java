/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management.jmx;

import com.liferay.portal.kernel.jmx.model.MBean;
import com.liferay.portal.kernel.management.ManageActionException;

import javax.management.AttributeList;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class GetAttributesAction extends BaseJMXManageAction<AttributeList> {

	public GetAttributesAction(MBean mBean) {
		_mBean = mBean;
	}

	@Override
	public AttributeList action() throws ManageActionException {
		try {
			ObjectName objectName = _mBean.getObjectName();

			MBeanServer mBeanServer = getMBeanServer();

			MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);

			MBeanAttributeInfo[] mBeanAttributeInfos =
				mBeanInfo.getAttributes();

			String[] attributeNames = new String[mBeanAttributeInfos.length];

			for (int i = 0; i < attributeNames.length; i++) {
				attributeNames[i] = mBeanAttributeInfos[i].getName();
			}

			return mBeanServer.getAttributes(objectName, attributeNames);
		}
		catch (Exception exception) {
			throw new ManageActionException(exception);
		}
	}

	private final MBean _mBean;

}