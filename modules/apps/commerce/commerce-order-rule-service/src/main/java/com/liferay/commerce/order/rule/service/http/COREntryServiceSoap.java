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

package com.liferay.commerce.order.rule.service.http;

import com.liferay.commerce.order.rule.service.COREntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>COREntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.order.rule.model.COREntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.order.rule.model.COREntry</code>, that is translated to a
 * <code>com.liferay.commerce.order.rule.model.COREntrySoap</code>. Methods that SOAP
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
 * @see COREntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class COREntryServiceSoap {

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			addCOREntry(
				String externalReferenceCode, boolean active,
				String description, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire, String name,
				int priority, String type, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.addCOREntry(
					externalReferenceCode, active, description,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, name, priority, type,
					typeSettings, serviceContext);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			deleteCOREntry(long corEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.deleteCOREntry(corEntryId);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.fetchByExternalReferenceCode(
					companyId, externalReferenceCode);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			fetchCOREntry(long corEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.fetchCOREntry(corEntryId);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap[]
			getCOREntries(long companyId, boolean active, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntry>
				returnValue = COREntryServiceUtil.getCOREntries(
					companyId, active, start, end);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap[]
			getCOREntries(
				long companyId, boolean active, String type, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntry>
				returnValue = COREntryServiceUtil.getCOREntries(
					companyId, active, type, start, end);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap[]
			getCOREntries(long companyId, String type, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntry>
				returnValue = COREntryServiceUtil.getCOREntries(
					companyId, type, start, end);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			getCOREntry(long corEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.getCOREntry(corEntryId);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			updateCOREntry(
				long corEntryId, boolean active, String description,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire, String name,
				int priority, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.updateCOREntry(
					corEntryId, active, description, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, name, priority,
					typeSettings, serviceContext);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntrySoap
			updateCOREntryExternalReferenceCode(
				String externalReferenceCode, long corEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntry returnValue =
				COREntryServiceUtil.updateCOREntryExternalReferenceCode(
					externalReferenceCode, corEntryId);

			return com.liferay.commerce.order.rule.model.COREntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(COREntryServiceSoap.class);

}