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

package com.liferay.commerce.term.service.http;

import com.liferay.commerce.term.service.CommerceTermEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommerceTermEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.term.model.CommerceTermEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.term.model.CommerceTermEntry</code>, that is translated to a
 * <code>com.liferay.commerce.term.model.CommerceTermEntrySoap</code>. Methods that SOAP
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
 * @author Luca Pellizzon
 * @see CommerceTermEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceTermEntryServiceSoap {

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			addCommerceTermEntry(
				String externalReferenceCode, boolean active,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String[] labelMapLanguageIds,
				String[] labelMapValues, String name, double priority,
				String type, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> labelMap = LocalizationUtil.getLocalizationMap(
				labelMapLanguageIds, labelMapValues);

			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.addCommerceTermEntry(
					externalReferenceCode, active, descriptionMap,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, labelMap, name, priority,
					type, typeSettings, serviceContext);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			deleteCommerceTermEntry(long commerceTermEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.deleteCommerceTermEntry(
					commerceTermEntryId);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.fetchByExternalReferenceCode(
					companyId, externalReferenceCode);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			fetchCommerceTermEntry(long commerceTermEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.fetchCommerceTermEntry(
					commerceTermEntryId);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			getCommerceTermEntry(long commerceTermEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.getCommerceTermEntry(
					commerceTermEntryId);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			updateCommerceTermEntry(
				long commerceTermEntryId, boolean active,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String[] labelMapLanguageIds,
				String[] labelMapValues, String name, double priority,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> labelMap = LocalizationUtil.getLocalizationMap(
				labelMapLanguageIds, labelMapValues);

			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.updateCommerceTermEntry(
					commerceTermEntryId, active, descriptionMap,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, labelMap, name, priority,
					typeSettings, serviceContext);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.term.model.CommerceTermEntrySoap
			updateCommerceTermEntryExternalReferenceCode(
				String externalReferenceCode, long commerceTermEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.term.model.CommerceTermEntry returnValue =
				CommerceTermEntryServiceUtil.
					updateCommerceTermEntryExternalReferenceCode(
						externalReferenceCode, commerceTermEntryId);

			return com.liferay.commerce.term.model.CommerceTermEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceTermEntryServiceSoap.class);

}