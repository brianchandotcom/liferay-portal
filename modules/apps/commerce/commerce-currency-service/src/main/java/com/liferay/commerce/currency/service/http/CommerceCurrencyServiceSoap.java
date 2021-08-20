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

package com.liferay.commerce.currency.service.http;

import com.liferay.commerce.currency.service.CommerceCurrencyServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommerceCurrencyServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.currency.model.CommerceCurrencySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.currency.model.CommerceCurrency</code>, that is translated to a
 * <code>com.liferay.commerce.currency.model.CommerceCurrencySoap</code>. Methods that SOAP
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
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceCurrencyServiceSoap {

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			addCommerceCurrency(
				String code, String[] nameMapLanguageIds,
				String[] nameMapValues, String symbol,
				java.math.BigDecimal rate, String[] formatPatternMapLanguageIds,
				String[] formatPatternMapValues, int maxFractionDigits,
				int minFractionDigits, String roundingMode, boolean primary,
				double priority, boolean active)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> formatPatternMap =
				LocalizationUtil.getLocalizationMap(
					formatPatternMapLanguageIds, formatPatternMapValues);

			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.addCommerceCurrency(
					code, nameMap, symbol, rate, formatPatternMap,
					maxFractionDigits, minFractionDigits, roundingMode, primary,
					priority, active);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceCurrency(long commerceCurrencyId)
		throws RemoteException {

		try {
			CommerceCurrencyServiceUtil.deleteCommerceCurrency(
				commerceCurrencyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			fetchPrimaryCommerceCurrency(long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.fetchPrimaryCommerceCurrency(
					companyId);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap[]
			getCommerceCurrencies(
				long companyId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.currency.model.CommerceCurrency>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.currency.model.CommerceCurrency>
				returnValue = CommerceCurrencyServiceUtil.getCommerceCurrencies(
					companyId, active, start, end, orderByComparator);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap[]
			getCommerceCurrencies(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.currency.model.CommerceCurrency>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.currency.model.CommerceCurrency>
				returnValue = CommerceCurrencyServiceUtil.getCommerceCurrencies(
					companyId, start, end, orderByComparator);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceCurrenciesCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceCurrencyServiceUtil.getCommerceCurrenciesCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceCurrenciesCount(long companyId, boolean active)
		throws RemoteException {

		try {
			int returnValue =
				CommerceCurrencyServiceUtil.getCommerceCurrenciesCount(
					companyId, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			getCommerceCurrency(long commerceCurrencyId)
		throws RemoteException {

		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.getCommerceCurrency(
					commerceCurrencyId);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			getCommerceCurrency(long companyId, String code)
		throws RemoteException {

		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.getCommerceCurrency(
					companyId, code);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			setActive(long commerceCurrencyId, boolean active)
		throws RemoteException {

		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.setActive(
					commerceCurrencyId, active);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			setPrimary(long commerceCurrencyId, boolean primary)
		throws RemoteException {

		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.setPrimary(
					commerceCurrencyId, primary);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap
			updateCommerceCurrency(
				long commerceCurrencyId, String code,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String symbol, java.math.BigDecimal rate,
				String[] formatPatternMapLanguageIds,
				String[] formatPatternMapValues, int maxFractionDigits,
				int minFractionDigits, String roundingMode, boolean primary,
				double priority, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> formatPatternMap =
				LocalizationUtil.getLocalizationMap(
					formatPatternMapLanguageIds, formatPatternMapValues);

			com.liferay.commerce.currency.model.CommerceCurrency returnValue =
				CommerceCurrencyServiceUtil.updateCommerceCurrency(
					commerceCurrencyId, code, nameMap, symbol, rate,
					formatPatternMap, maxFractionDigits, minFractionDigits,
					roundingMode, primary, priority, active, serviceContext);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws RemoteException {

		try {
			CommerceCurrencyServiceUtil.updateExchangeRate(
				commerceCurrencyId, exchangeRateProviderKey);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateExchangeRates() throws RemoteException {
		try {
			CommerceCurrencyServiceUtil.updateExchangeRates();
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceCurrencyServiceSoap.class);

}