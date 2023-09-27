/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (Exception exception) {
			throw new ManageActionException(exception);
		}
	}

	private final ObjectName _objectName;
	private final String _operationName;
	private final Object[] _parameters;
	private final String[] _signature;

}