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

import com.liferay.portal.kernel.management.ManageActionException;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class DoOperationAction extends BaseJMXManageAction<Object> {

	public DoOperationAction(
		ObjectName objectName, String operationName, Object[] parameters,
		String[] signature) {

		_objectName = objectName;
		_operationName = operationName;
		_parameters = parameters;
		_signature = signature;
	}

	@Override
	public Object action() throws ManageActionException {
		try {
			MBeanServer mBeanServer = getMBeanServer();

			return mBeanServer.invoke(
				_objectName, _operationName, _parameters, _signature);

		}
		catch (Exception e) {
			throw new ManageActionException(e);
		}
	}

	private ObjectName _objectName;
	private String _operationName;
	private Object[] _parameters;
	private String[] _signature;

}