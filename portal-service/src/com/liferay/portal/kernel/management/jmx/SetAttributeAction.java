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
		catch (Exception e) {
			throw new ManageActionException(e);
		}
	}

	private final String _name;
	private final ObjectName _objectName;
	private final Object _value;

}