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

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPOptionValueServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CPOptionValueServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CPOptionValueSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CPOptionValue</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CPOptionValueSoap</code>. Methods that SOAP
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
 * @author Marco Leo
 * @see CPOptionValueServiceHttp
 * @generated
 */
public class CPOptionValueServiceSoap {

	public static com.liferay.commerce.product.model.CPOptionValueSoap
			addCPOptionValue(
				long cpOptionId, String[] titleMapLanguageIds,
				String[] titleMapValues, double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);

			com.liferay.commerce.product.model.CPOptionValue returnValue =
				CPOptionValueServiceUtil.addCPOptionValue(
					cpOptionId, titleMap, priority, key, serviceContext);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCPOptionValue(long cpOptionValueId)
		throws RemoteException {

		try {
			CPOptionValueServiceUtil.deleteCPOptionValue(cpOptionValueId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue =
				CPOptionValueServiceUtil.fetchByExternalReferenceCode(
					companyId, externalReferenceCode);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap
			fetchCPOptionValue(long cpOptionValueId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue =
				CPOptionValueServiceUtil.fetchCPOptionValue(cpOptionValueId);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap
			getCPOptionValue(long cpOptionValueId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue =
				CPOptionValueServiceUtil.getCPOptionValue(cpOptionValueId);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap[]
			getCPOptionValues(long cpOptionId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPOptionValue>
				returnValue = CPOptionValueServiceUtil.getCPOptionValues(
					cpOptionId, start, end);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPOptionValuesCount(long cpOptionId)
		throws RemoteException {

		try {
			int returnValue = CPOptionValueServiceUtil.getCPOptionValuesCount(
				cpOptionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap
			updateCPOptionValue(
				long cpOptionValueId, String[] titleMapLanguageIds,
				String[] titleMapValues, double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);

			com.liferay.commerce.product.model.CPOptionValue returnValue =
				CPOptionValueServiceUtil.updateCPOptionValue(
					cpOptionValueId, titleMap, priority, key, serviceContext);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap
			upsertCPOptionValue(
				long cpOptionId, String[] nameMapLanguageIds,
				String[] nameMapValues, double priority, String key,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.commerce.product.model.CPOptionValue returnValue =
				CPOptionValueServiceUtil.upsertCPOptionValue(
					cpOptionId, nameMap, priority, key, externalReferenceCode,
					serviceContext);

			return com.liferay.commerce.product.model.CPOptionValueSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPOptionValueServiceSoap.class);

}