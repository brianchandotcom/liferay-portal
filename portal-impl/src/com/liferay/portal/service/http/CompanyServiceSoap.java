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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.CompanyServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.CompanyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.CompanySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Company}, that is translated to a
 * {@link com.liferay.portal.model.CompanySoap}. Methods that SOAP cannot
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
 * @see       CompanyServiceHttp
 * @see       com.liferay.portal.model.CompanySoap
 * @see       com.liferay.portal.service.CompanyServiceUtil
 * @generated
 */
public class CompanyServiceSoap {
	public static com.liferay.portal.model.CompanySoap addCompany(
		java.lang.String webId, java.lang.String virtualHost,
		java.lang.String mx, java.lang.String shardName, boolean system,
		int maxUsers, boolean active) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.addCompany(webId,
					virtualHost, mx, shardName, system, maxUsers, active);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLogo(long companyId) throws RemoteException {
		try {
			CompanyServiceUtil.deleteLogo(companyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap getCompanyById(
		long companyId) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyById(companyId);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap getCompanyByLogoId(
		long logoId) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByLogoId(logoId);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap getCompanyByMx(
		java.lang.String mx) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByMx(mx);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap getCompanyByVirtualHost(
		java.lang.String virtualHost) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByVirtualHost(virtualHost);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap getCompanyByWebId(
		java.lang.String webId) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByWebId(webId);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void removePreferences(long companyId, java.lang.String[] keys)
		throws RemoteException {
		try {
			CompanyServiceUtil.removePreferences(companyId, keys);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
		int maxUsers, boolean active) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.updateCompany(companyId,
					virtualHost, mx, maxUsers, active);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CompanySoap updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size) throws RemoteException {
		try {
			com.liferay.portal.model.Company returnValue = CompanyServiceUtil.updateCompany(companyId,
					virtualHost, mx, homeURL, name, legalName, legalId,
					legalType, sicCode, tickerSymbol, industry, type, size);

			return com.liferay.portal.model.CompanySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateDisplay(long companyId,
		java.lang.String languageId, java.lang.String timeZoneId)
		throws RemoteException {
		try {
			CompanyServiceUtil.updateDisplay(companyId, languageId, timeZoneId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateSecurity(long companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers, boolean strangersWithMx, boolean strangersVerify,
		boolean siteLogo) throws RemoteException {
		try {
			CompanyServiceUtil.updateSecurity(companyId, authType, autoLogin,
				sendPassword, strangers, strangersWithMx, strangersVerify,
				siteLogo);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CompanyServiceSoap.class);
}