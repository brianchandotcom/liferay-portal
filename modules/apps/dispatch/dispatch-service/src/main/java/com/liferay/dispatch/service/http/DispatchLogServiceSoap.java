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

import com.liferay.dispatch.service.DispatchLogServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DispatchLogServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dispatch.model.DispatchLogSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dispatch.model.DispatchLog</code>, that is translated to a
 * <code>com.liferay.dispatch.model.DispatchLogSoap</code>. Methods that SOAP
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
 * @see DispatchLogServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DispatchLogServiceSoap {

	public static void deleteDispatchLog(long dispatchLogId)
		throws RemoteException {

		try {
			DispatchLogServiceUtil.deleteDispatchLog(dispatchLogId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dispatch.model.DispatchLogSoap getDispatchLog(
			long dispatchLogId)
		throws RemoteException {

		try {
			com.liferay.dispatch.model.DispatchLog returnValue =
				DispatchLogServiceUtil.getDispatchLog(dispatchLogId);

			return com.liferay.dispatch.model.DispatchLogSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dispatch.model.DispatchLogSoap[] getDispatchLogs(
			long dispatchTriggerId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dispatch.model.DispatchLog> returnValue =
				DispatchLogServiceUtil.getDispatchLogs(
					dispatchTriggerId, start, end);

			return com.liferay.dispatch.model.DispatchLogSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dispatch.model.DispatchLogSoap[] getDispatchLogs(
			long dispatchTriggerId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dispatch.model.DispatchLog> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dispatch.model.DispatchLog> returnValue =
				DispatchLogServiceUtil.getDispatchLogs(
					dispatchTriggerId, start, end, orderByComparator);

			return com.liferay.dispatch.model.DispatchLogSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getDispatchLogsCount(long dispatchTriggerId)
		throws RemoteException {

		try {
			int returnValue = DispatchLogServiceUtil.getDispatchLogsCount(
				dispatchTriggerId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DispatchLogServiceSoap.class);

}