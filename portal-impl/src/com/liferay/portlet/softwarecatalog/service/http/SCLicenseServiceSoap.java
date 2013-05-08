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

import com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.softwarecatalog.model.SCLicenseSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.softwarecatalog.model.SCLicense}, that is translated to a
 * {@link com.liferay.portlet.softwarecatalog.model.SCLicenseSoap}. Methods that SOAP cannot
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
 * @see       SCLicenseServiceHttp
 * @see       com.liferay.portlet.softwarecatalog.model.SCLicenseSoap
 * @see       com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil
 * @generated
 */
public class SCLicenseServiceSoap {
	public static com.liferay.portlet.softwarecatalog.model.SCLicenseSoap addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCLicense returnValue = SCLicenseServiceUtil.addLicense(name,
					url, openSource, active, recommended);

			return com.liferay.portlet.softwarecatalog.model.SCLicenseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLicense(long licenseId) throws RemoteException {
		try {
			SCLicenseServiceUtil.deleteLicense(licenseId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicenseSoap getLicense(
		long licenseId) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCLicense returnValue = SCLicenseServiceUtil.getLicense(licenseId);

			return com.liferay.portlet.softwarecatalog.model.SCLicenseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicenseSoap updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCLicense returnValue = SCLicenseServiceUtil.updateLicense(licenseId,
					name, url, openSource, active, recommended);

			return com.liferay.portlet.softwarecatalog.model.SCLicenseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCLicenseServiceSoap.class);
}