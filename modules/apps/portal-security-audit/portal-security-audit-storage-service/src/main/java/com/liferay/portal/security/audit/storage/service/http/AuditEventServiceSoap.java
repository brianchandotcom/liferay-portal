/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.security.audit.storage.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.audit.storage.service.AuditEventServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link AuditEventServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.security.audit.storage.model.AuditEventSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.security.audit.storage.model.AuditEvent}, that is translated to a
 * {@link com.liferay.portal.security.audit.storage.model.AuditEventSoap}. Methods that SOAP cannot
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
 * @see com.liferay.portal.security.audit.storage.model.AuditEventSoap
 * @see AuditEventServiceUtil
 * @generated
 */
@ProviderType
public class AuditEventServiceSoap {
	public static com.liferay.portal.security.audit.storage.model.AuditEventSoap[] getAuditEvents(
		long companyId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.security.audit.storage.model.AuditEvent> returnValue =
				AuditEventServiceUtil.getAuditEvents(companyId, start, end);

			return com.liferay.portal.security.audit.storage.model.AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.security.audit.storage.model.AuditEventSoap[] getAuditEvents(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.security.audit.storage.model.AuditEvent> returnValue =
				AuditEventServiceUtil.getAuditEvents(companyId, start, end,
					orderByComparator);

			return com.liferay.portal.security.audit.storage.model.AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.security.audit.storage.model.AuditEventSoap[] getAuditEvents(
		long companyId, long userId, String userName,
		java.util.Date createDateGT, java.util.Date createDateLT,
		String eventType, String className, String classPK, String clientHost,
		String clientIP, String serverName, int serverPort, String sessionID,
		boolean andSearch, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.security.audit.storage.model.AuditEvent> returnValue =
				AuditEventServiceUtil.getAuditEvents(companyId, userId,
					userName, createDateGT, createDateLT, eventType, className,
					classPK, clientHost, clientIP, serverName, serverPort,
					sessionID, andSearch, start, end);

			return com.liferay.portal.security.audit.storage.model.AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.security.audit.storage.model.AuditEventSoap[] getAuditEvents(
		long companyId, long userId, String userName,
		java.util.Date createDateGT, java.util.Date createDateLT,
		String eventType, String className, String classPK, String clientHost,
		String clientIP, String serverName, int serverPort, String sessionID,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.security.audit.storage.model.AuditEvent> returnValue =
				AuditEventServiceUtil.getAuditEvents(companyId, userId,
					userName, createDateGT, createDateLT, eventType, className,
					classPK, clientHost, clientIP, serverName, serverPort,
					sessionID, andSearch, start, end, orderByComparator);

			return com.liferay.portal.security.audit.storage.model.AuditEventSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAuditEventsCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = AuditEventServiceUtil.getAuditEventsCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAuditEventsCount(long companyId, long userId,
		String userName, java.util.Date createDateGT,
		java.util.Date createDateLT, String eventType, String className,
		String classPK, String clientHost, String clientIP, String serverName,
		int serverPort, String sessionID, boolean andSearch)
		throws RemoteException {
		try {
			int returnValue = AuditEventServiceUtil.getAuditEventsCount(companyId,
					userId, userName, createDateGT, createDateLT, eventType,
					className, classPK, clientHost, clientIP, serverName,
					serverPort, sessionID, andSearch);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AuditEventServiceSoap.class);
}