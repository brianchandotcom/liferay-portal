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

import com.liferay.commerce.service.CommerceOrderTypeServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommerceOrderTypeServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceOrderTypeSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceOrderType</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceOrderTypeSoap</code>. Methods that SOAP
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
 * @see CommerceOrderTypeServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceOrderTypeServiceSoap {

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			addCommerceOrderType(
				String externalReferenceCode, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute, int displayOrder,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.addCommerceOrderType(
					externalReferenceCode, nameMap, descriptionMap, active,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, displayOrder,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			deleteCommerceOrderType(long commerceOrderTypeId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.deleteCommerceOrderType(
					commerceOrderTypeId);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			fetchCommerceOrderType(long commerceOrderTypeId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.fetchCommerceOrderType(
					commerceOrderTypeId);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			getCommerceOrderType(long commerceOrderTypeId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.getCommerceOrderType(
					commerceOrderTypeId);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap[]
			getCommerceOrderTypes(
				String className, long classPK, boolean active, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrderType>
				returnValue =
					CommerceOrderTypeServiceUtil.getCommerceOrderTypes(
						className, classPK, active, start, end);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderTypesCount(
			String className, long classPK, boolean active)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderTypeServiceUtil.getCommerceOrderTypesCount(
					className, classPK, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			updateCommerceOrderType(
				String externalReferenceCode, long commerceOrderTypeId,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute, int displayOrder,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.updateCommerceOrderType(
					externalReferenceCode, commerceOrderTypeId, nameMap,
					descriptionMap, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					displayOrder, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeSoap
			updateCommerceOrderTypeExternalReferenceCode(
				String externalReferenceCode, long commerceOrderTypeId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderType returnValue =
				CommerceOrderTypeServiceUtil.
					updateCommerceOrderTypeExternalReferenceCode(
						externalReferenceCode, commerceOrderTypeId);

			return com.liferay.commerce.model.CommerceOrderTypeSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceOrderTypeServiceSoap.class);

}