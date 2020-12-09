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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CountryServiceSoap {

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
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
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCountry(long countryId) throws RemoteException {
		try {
			CountryServiceUtil.deleteCountry(countryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap fetchCountryByA2(
			long companyId, String a2)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountryByA2(companyId, a2);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.CountrySoap fetchCountryByA2(
			String a2)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountryByA2(a2);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap fetchCountryByA3(
			long companyId, String a3)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountryByA3(companyId, a3);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.CountrySoap fetchCountryByA3(
			String a3)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.fetchCountryByA3(a3);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap[]
			getCompanyCountries(long companyId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCompanyCountries(companyId);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap[]
			getCompanyCountries(long companyId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCompanyCountries(
					companyId, active);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap[]
			getCompanyCountries(
				long companyId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.Country> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCompanyCountries(
					companyId, active, start, end, orderByComparator);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap[]
			getCompanyCountries(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.Country> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCompanyCountries(
					companyId, start, end, orderByComparator);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCompanyCountriesCount(long companyId)
		throws RemoteException {

		try {
			int returnValue = CountryServiceUtil.getCompanyCountriesCount(
				companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCompanyCountriesCount(long companyId, boolean active)
		throws RemoteException {

		try {
			int returnValue = CountryServiceUtil.getCompanyCountriesCount(
				companyId, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.CountrySoap[] getCountries(
			boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Country>
				returnValue = CountryServiceUtil.getCountries(active);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountryByA2(
			long companyId, String a2)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByA2(companyId, a2);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.CountrySoap getCountryByA2(
			String a2)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByA2(a2);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountryByA3(
			long companyId, String a3)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByA3(companyId, a3);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.CountrySoap getCountryByA3(
			String a3)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByA3(a3);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap getCountryByName(
			long companyId, String name)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByName(companyId, name);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.CountrySoap getCountryByName(
			String name)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.getCountryByName(name);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap updateActive(
			long countryId, boolean active)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.updateActive(countryId, active);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.CountrySoap
			updateGroupFilterEnabled(long countryId, boolean groupFilterEnabled)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Country returnValue =
				CountryServiceUtil.updateGroupFilterEnabled(
					countryId, groupFilterEnabled);

			return com.liferay.portal.kernel.model.CountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CountryServiceSoap.class);

}