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

package com.liferay.portal.security.audit.storage.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.audit.storage.service.AuditEventServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>AuditEventServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.security.audit.storage.model.AuditEventSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.security.audit.storage.model.AuditEvent</code>, that is translated to a
 * <code>com.liferay.portal.security.audit.storage.model.AuditEventSoap</code>. Methods that SOAP
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
 * @see AuditEventServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AuditEventServiceSoap {

	public static
		com.liferay.portal.security.audit.storage.model.AuditEventSoap[]
				getAuditEvents(long companyId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.security.audit.storage.model.AuditEvent>
					returnValue = AuditEventServiceUtil.getAuditEvents(
						companyId, start, end);

			return com.liferay.portal.security.audit.storage.model.
				AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.security.audit.storage.model.AuditEventSoap[]
				getAuditEvents(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.security.audit.storage.model.
							AuditEvent> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.security.audit.storage.model.AuditEvent>
					returnValue = AuditEventServiceUtil.getAuditEvents(
						companyId, start, end, orderByComparator);

			return com.liferay.portal.security.audit.storage.model.
				AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.security.audit.storage.model.AuditEventSoap[]
				getAuditEvents(
					long companyId, long userId, String userName,
					java.util.Date createDateGT, java.util.Date createDateLT,
					String eventType, String className, String classPK,
					String clientHost, String clientIP, String serverName,
					int serverPort, String sessionID, boolean andSearch,
					int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.security.audit.storage.model.AuditEvent>
					returnValue = AuditEventServiceUtil.getAuditEvents(
						companyId, userId, userName, createDateGT, createDateLT,
						eventType, className, classPK, clientHost, clientIP,
						serverName, serverPort, sessionID, andSearch, start,
						end);

			return com.liferay.portal.security.audit.storage.model.
				AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.security.audit.storage.model.AuditEventSoap[]
				getAuditEvents(
					long companyId, long userId, String userName,
					java.util.Date createDateGT, java.util.Date createDateLT,
					String eventType, String className, String classPK,
					String clientHost, String clientIP, String serverName,
					int serverPort, String sessionID, boolean andSearch,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.security.audit.storage.model.
							AuditEvent> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.security.audit.storage.model.AuditEvent>
					returnValue = AuditEventServiceUtil.getAuditEvents(
						companyId, userId, userName, createDateGT, createDateLT,
						eventType, className, classPK, clientHost, clientIP,
						serverName, serverPort, sessionID, andSearch, start,
						end, orderByComparator);

			return com.liferay.portal.security.audit.storage.model.
				AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getAuditEventsCount(long companyId)
		throws RemoteException {

		try {
			int returnValue = AuditEventServiceUtil.getAuditEventsCount(
				companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getAuditEventsCount(
			long companyId, long userId, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			String eventType, String className, String classPK,
			String clientHost, String clientIP, String serverName,
			int serverPort, String sessionID, boolean andSearch)
		throws RemoteException {

		try {
			int returnValue = AuditEventServiceUtil.getAuditEventsCount(
				companyId, userId, userName, createDateGT, createDateLT,
				eventType, className, classPK, clientHost, clientIP, serverName,
				serverPort, sessionID, andSearch);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AuditEventServiceSoap.class);

}