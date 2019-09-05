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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.OrgLaborServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>OrgLaborServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.OrgLaborSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.OrgLabor</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.OrgLaborSoap</code>. Methods that SOAP
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
 * @author Brian Wing Shun Chan
 * @see OrgLaborServiceHttp
 * @generated
 */
public class OrgLaborServiceSoap {

	public static com.liferay.portal.kernel.model.OrgLaborSoap addOrgLabor(
			long organizationId, long typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.OrgLabor returnValue =
				OrgLaborServiceUtil.addOrgLabor(
					organizationId, typeId, sunOpen, sunClose, monOpen,
					monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
					thuClose, friOpen, friClose, satOpen, satClose);

			return com.liferay.portal.kernel.model.OrgLaborSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteOrgLabor(long orgLaborId) throws RemoteException {
		try {
			OrgLaborServiceUtil.deleteOrgLabor(orgLaborId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.OrgLaborSoap getOrgLabor(
			long orgLaborId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.OrgLabor returnValue =
				OrgLaborServiceUtil.getOrgLabor(orgLaborId);

			return com.liferay.portal.kernel.model.OrgLaborSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.OrgLaborSoap[] getOrgLabors(
			long organizationId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.OrgLabor>
				returnValue = OrgLaborServiceUtil.getOrgLabors(organizationId);

			return com.liferay.portal.kernel.model.OrgLaborSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.OrgLaborSoap updateOrgLabor(
			long orgLaborId, long typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.OrgLabor returnValue =
				OrgLaborServiceUtil.updateOrgLabor(
					orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose,
					tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
					friOpen, friClose, satOpen, satClose);

			return com.liferay.portal.kernel.model.OrgLaborSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OrgLaborServiceSoap.class);

}