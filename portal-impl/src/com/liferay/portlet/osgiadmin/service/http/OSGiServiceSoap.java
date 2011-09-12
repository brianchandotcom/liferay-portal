/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.osgiadmin.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.osgiadmin.service.OSGiServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.osgiadmin.service.OSGiServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
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
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OSGiServiceHttp
 * @see       com.liferay.portlet.osgiadmin.service.OSGiServiceUtil
 * @generated
 */
public class OSGiServiceSoap {
	public static java.lang.Object addBundle(java.lang.String location)
		throws RemoteException {
		try {
			java.lang.Object returnValue = OSGiServiceUtil.addBundle(location);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Object getBundleContext() throws RemoteException {
		try {
			java.lang.Object returnValue = OSGiServiceUtil.getBundleContext();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Object getFramework() throws RemoteException {
		try {
			java.lang.Object returnValue = OSGiServiceUtil.getFramework();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getState(long bundleId)
		throws RemoteException {
		try {
			java.lang.String returnValue = OSGiServiceUtil.getState(bundleId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setBundleStartLevel(long bundleId, int startLevel)
		throws RemoteException {
		try {
			OSGiServiceUtil.setBundleStartLevel(bundleId, startLevel);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void startBundle(long bundleId) throws RemoteException {
		try {
			OSGiServiceUtil.startBundle(bundleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void startBundle(long bundleId, int options)
		throws RemoteException {
		try {
			OSGiServiceUtil.startBundle(bundleId, options);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void stopBundle(long bundleId) throws RemoteException {
		try {
			OSGiServiceUtil.stopBundle(bundleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void stopBundle(long bundleId, int options)
		throws RemoteException {
		try {
			OSGiServiceUtil.stopBundle(bundleId, options);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void uninstallBundle(long bundleId) throws RemoteException {
		try {
			OSGiServiceUtil.uninstallBundle(bundleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateBundle(long bundleId) throws RemoteException {
		try {
			OSGiServiceUtil.updateBundle(bundleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OSGiServiceSoap.class);
}