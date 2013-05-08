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

package com.liferay.portlet.expando.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.expando.service.ExpandoColumnServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.expando.model.ExpandoColumnSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.expando.model.ExpandoColumn}, that is translated to a
 * {@link com.liferay.portlet.expando.model.ExpandoColumnSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnServiceHttp
 * @see       com.liferay.portlet.expando.model.ExpandoColumnSoap
 * @see       com.liferay.portlet.expando.service.ExpandoColumnServiceUtil
 * @generated
 */
public class ExpandoColumnServiceSoap {
	public static com.liferay.portlet.expando.model.ExpandoColumnSoap addColumn(
		long tableId, java.lang.String name, int type)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoColumn returnValue = ExpandoColumnServiceUtil.addColumn(tableId,
					name, type);

			return com.liferay.portlet.expando.model.ExpandoColumnSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumnSoap addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoColumn returnValue = ExpandoColumnServiceUtil.addColumn(tableId,
					name, type, defaultData);

			return com.liferay.portlet.expando.model.ExpandoColumnSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteColumn(long columnId) throws RemoteException {
		try {
			ExpandoColumnServiceUtil.deleteColumn(columnId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumnSoap updateColumn(
		long columnId, java.lang.String name, int type)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoColumn returnValue = ExpandoColumnServiceUtil.updateColumn(columnId,
					name, type);

			return com.liferay.portlet.expando.model.ExpandoColumnSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumnSoap updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoColumn returnValue = ExpandoColumnServiceUtil.updateColumn(columnId,
					name, type, defaultData);

			return com.liferay.portlet.expando.model.ExpandoColumnSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumnSoap updateTypeSettings(
		long columnId, java.lang.String typeSettings) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoColumn returnValue = ExpandoColumnServiceUtil.updateTypeSettings(columnId,
					typeSettings);

			return com.liferay.portlet.expando.model.ExpandoColumnSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoColumnServiceSoap.class);
}