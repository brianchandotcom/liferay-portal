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
import com.liferay.portal.kernel.service.CountryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CountryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.CountrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Country</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.CountrySoap</code>. Methods that SOAP
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
 * @see CountryServiceHttp
 * @generated
 */
public class CountryServiceSoap {

	public static com.liferay.portal.kernel.model.CountrySoap addCountry(
			String name, String a2, String a3, String number, String idd,
			boolean active)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.addCountry(
					name, a2, a3, number, idd, active);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap fetchCountry(
			long countryId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountry(countryId);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap fetchCountryByA2(
			String a2)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountryByA2(a2);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap fetchCountryByA3(
			String a3)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountryByA3(a3);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap[] getCountries()
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCountries();

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap[] getCountries(
			boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCountries(active);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountry(
			long countryId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountry(countryId);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountryByA2(
			String a2)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByA2(a2);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountryByA3(
			String a3)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByA3(a3);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountryByName(
			String name)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByName(name);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CountryServiceSoap.class);

}