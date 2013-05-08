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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion}, that is translated to a
 * {@link com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap}. Methods that SOAP cannot
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
 * @see       SCFrameworkVersionServiceHttp
 * @see       com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap
 * @see       com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil
 * @generated
 */
public class SCFrameworkVersionServiceSoap {
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap addFrameworkVersion(
		java.lang.String name, java.lang.String url, boolean active,
		int priority, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
				SCFrameworkVersionServiceUtil.addFrameworkVersion(name, url,
					active, priority, serviceContext);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws RemoteException {
		try {
			SCFrameworkVersionServiceUtil.deleteFrameworkVersion(frameworkVersionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap getFrameworkVersion(
		long frameworkVersionId) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
				SCFrameworkVersionServiceUtil.getFrameworkVersion(frameworkVersionId);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap[] getFrameworkVersions(
		long groupId, boolean active) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> returnValue =
				SCFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
					active);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap[] getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> returnValue =
				SCFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
					active, start, end);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
				SCFrameworkVersionServiceUtil.updateFrameworkVersion(frameworkVersionId,
					name, url, active, priority);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionServiceSoap.class);
}