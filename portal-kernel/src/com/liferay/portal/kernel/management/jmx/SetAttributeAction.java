/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management.jmx;

import com.liferay.portal.kernel.management.ManageActionException;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class SetAttributeAction extends BaseJMXManageAction<Void> {

	public SetAttributeAction(
		ObjectName objectName, String name, Object value) {

		_objectName = objectName;
		_name = name;
		_value = value;
	}

	@Override
	public Void action() throws ManageActionException {
		try {
			MBeanServer mBeanServer = getMBeanServer();

			mBeanServer.setAttribute(_objectName, new Attribute(_name, _value));

			return null;
		}
		catch (Exception exception) {
			throw new ManageActionException(exception);
		}
	}

	private final String _name;
	private final ObjectName _objectName;
	private final Object _value;

}