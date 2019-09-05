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
import com.liferay.portal.kernel.service.WebsiteServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>WebsiteServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.WebsiteSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Website</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.WebsiteSoap</code>. Methods that SOAP
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
 * @see WebsiteServiceHttp
 * @generated
 */
public class WebsiteServiceSoap {

	public static com.liferay.portal.kernel.model.WebsiteSoap addWebsite(
			String className, long classPK, String url, long typeId,
			boolean primary,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Website returnValue =
				WebsiteServiceUtil.addWebsite(
					className, classPK, url, typeId, primary, serviceContext);

			return com.liferay.portal.kernel.model.WebsiteSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteWebsite(long websiteId) throws RemoteException {
		try {
			WebsiteServiceUtil.deleteWebsite(websiteId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.WebsiteSoap getWebsite(
			long websiteId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Website returnValue =
				WebsiteServiceUtil.getWebsite(websiteId);

			return com.liferay.portal.kernel.model.WebsiteSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.WebsiteSoap[] getWebsites(
			String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Website>
				returnValue = WebsiteServiceUtil.getWebsites(
					className, classPK);

			return com.liferay.portal.kernel.model.WebsiteSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.WebsiteSoap updateWebsite(
			long websiteId, String url, long typeId, boolean primary)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Website returnValue =
				WebsiteServiceUtil.updateWebsite(
					websiteId, url, typeId, primary);

			return com.liferay.portal.kernel.model.WebsiteSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WebsiteServiceSoap.class);

}