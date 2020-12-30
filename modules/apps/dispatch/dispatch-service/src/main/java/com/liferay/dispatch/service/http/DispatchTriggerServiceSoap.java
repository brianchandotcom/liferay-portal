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

package com.liferay.dispatch.service.http;

import com.liferay.dispatch.service.DispatchTriggerServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DispatchTriggerServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dispatch.model.DispatchTriggerSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dispatch.model.DispatchTrigger</code>, that is translated to a
 * <code>com.liferay.dispatch.model.DispatchTriggerSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Matija Petanjek
 * @see DispatchTriggerServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DispatchTriggerServiceSoap {

	public static void deleteDispatchTrigger(long dispatchTriggerId)
		throws RemoteException {

		try {
			DispatchTriggerServiceUtil.deleteDispatchTrigger(dispatchTriggerId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dispatch.model.DispatchTriggerSoap[]
			getDispatchTriggers(int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dispatch.model.DispatchTrigger>
				returnValue = DispatchTriggerServiceUtil.getDispatchTriggers(
					start, end);

			return com.liferay.dispatch.model.DispatchTriggerSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getDispatchTriggersCount() throws RemoteException {
		try {
			int returnValue =
				DispatchTriggerServiceUtil.getDispatchTriggersCount();

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dispatch.model.DispatchTriggerSoap
			updateDispatchTrigger(
				long dispatchTriggerId, boolean active, String cronExpression,
				com.liferay.dispatch.executor.DispatchTaskClusterMode
					dispatchTaskClusterMode,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd,
				boolean overlapAllowed, int startDateMonth, int startDateDay,
				int startDateYear, int startDateHour, int startDateMinute)
		throws RemoteException {

		try {
			com.liferay.dispatch.model.DispatchTrigger returnValue =
				DispatchTriggerServiceUtil.updateDispatchTrigger(
					dispatchTriggerId, active, cronExpression,
					dispatchTaskClusterMode, endDateMonth, endDateDay,
					endDateYear, endDateHour, endDateMinute, neverEnd,
					overlapAllowed, startDateMonth, startDateDay, startDateYear,
					startDateHour, startDateMinute);

			return com.liferay.dispatch.model.DispatchTriggerSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DispatchTriggerServiceSoap.class);

}