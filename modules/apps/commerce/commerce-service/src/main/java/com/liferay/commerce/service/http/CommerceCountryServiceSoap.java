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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceCountryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommerceCountryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceCountrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceCountry</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceCountrySoap</code>. Methods that SOAP
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
 * @author Alessio Antonio Rendina
 * @see CommerceCountryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceCountryServiceSoap {

	public static com.liferay.commerce.model.CommerceCountrySoap
			addCommerceCountry(
				String[] nameMapLanguageIds, String[] nameMapValues,
				boolean billingAllowed, boolean shippingAllowed,
				String twoLettersISOCode, String threeLettersISOCode,
				int numericISOCode, boolean subjectToVAT, double priority,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.addCommerceCountry(
					nameMap, billingAllowed, shippingAllowed, twoLettersISOCode,
					threeLettersISOCode, numericISOCode, subjectToVAT, priority,
					active, serviceContext);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceCountry(long commerceCountryId)
		throws RemoteException {

		try {
			CommerceCountryServiceUtil.deleteCommerceCountry(commerceCountryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap
			fetchCommerceCountry(long companyId, String twoLettersISOCode)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.fetchCommerceCountry(
					companyId, twoLettersISOCode);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getBillingCommerceCountries(
				long companyId, boolean billingAllowed, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue =
					CommerceCountryServiceUtil.getBillingCommerceCountries(
						companyId, billingAllowed, active);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getBillingCommerceCountriesByChannelId(
				long commerceChannelId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue =
					CommerceCountryServiceUtil.
						getBillingCommerceCountriesByChannelId(
							commerceChannelId, start, end);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getCommerceCountries(long companyId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue = CommerceCountryServiceUtil.getCommerceCountries(
					companyId, active);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getCommerceCountries(
				long companyId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceCountry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue = CommerceCountryServiceUtil.getCommerceCountries(
					companyId, active, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getCommerceCountries(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceCountry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue = CommerceCountryServiceUtil.getCommerceCountries(
					companyId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceCountriesCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceCountryServiceUtil.getCommerceCountriesCount(companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceCountriesCount(long companyId, boolean active)
		throws RemoteException {

		try {
			int returnValue =
				CommerceCountryServiceUtil.getCommerceCountriesCount(
					companyId, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap
			getCommerceCountry(long commerceCountryId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.getCommerceCountry(
					commerceCountryId);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap
			getCommerceCountry(long companyId, String twoLettersISOCode)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.getCommerceCountry(
					companyId, twoLettersISOCode);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getShippingCommerceCountries(
				long companyId, boolean shippingAllowed, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue =
					CommerceCountryServiceUtil.getShippingCommerceCountries(
						companyId, shippingAllowed, active);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getShippingCommerceCountriesByChannelId(
				long commerceChannelId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue =
					CommerceCountryServiceUtil.
						getShippingCommerceCountriesByChannelId(
							commerceChannelId, start, end);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap[]
			getWarehouseCommerceCountries(long companyId, boolean all)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceCountry>
				returnValue =
					CommerceCountryServiceUtil.getWarehouseCommerceCountries(
						companyId, all);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap setActive(
			long commerceCountryId, boolean active)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.setActive(commerceCountryId, active);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap
			updateCommerceCountry(
				long commerceCountryId, String[] nameMapLanguageIds,
				String[] nameMapValues, boolean billingAllowed,
				boolean shippingAllowed, String twoLettersISOCode,
				String threeLettersISOCode, int numericISOCode,
				boolean subjectToVAT, double priority, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.updateCommerceCountry(
					commerceCountryId, nameMap, billingAllowed, shippingAllowed,
					twoLettersISOCode, threeLettersISOCode, numericISOCode,
					subjectToVAT, priority, active, serviceContext);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCountrySoap
			updateCommerceCountryChannelFilter(
				long commerceCountryId, boolean enable)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceCountry returnValue =
				CommerceCountryServiceUtil.updateCommerceCountryChannelFilter(
					commerceCountryId, enable);

			return com.liferay.commerce.model.CommerceCountrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceCountryServiceSoap.class);

}