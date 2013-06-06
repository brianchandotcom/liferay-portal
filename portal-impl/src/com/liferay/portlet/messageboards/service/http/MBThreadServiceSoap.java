/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.messageboards.service.MBThreadServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.messageboards.model.MBThreadSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.messageboards.model.MBThread}, that is translated to a
 * {@link com.liferay.portlet.messageboards.model.MBThreadSoap}. Methods that SOAP cannot
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
 * @see MBThreadServiceHttp
 * @see com.liferay.portlet.messageboards.model.MBThreadSoap
 * @see com.liferay.portlet.messageboards.service.MBThreadServiceUtil
 * @generated
 */
public class MBThreadServiceSoap {
	public static void deleteThread(long threadId) throws RemoteException {
		try {
			MBThreadServiceUtil.deleteThread(threadId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap[] getGroupThreads(
		long groupId, long userId, java.util.Date modifiedDate, int status,
		int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBThread> returnValue =
				MBThreadServiceUtil.getGroupThreads(groupId, userId,
					modifiedDate, status, start, end);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap[] getGroupThreads(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBThread> returnValue =
				MBThreadServiceUtil.getGroupThreads(groupId, userId, status,
					subscribed, includeAnonymous, start, end);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap[] getGroupThreads(
		long groupId, long userId, int status, boolean subscribed, int start,
		int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBThread> returnValue =
				MBThreadServiceUtil.getGroupThreads(groupId, userId, status,
					subscribed, start, end);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap[] getGroupThreads(
		long groupId, long userId, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBThread> returnValue =
				MBThreadServiceUtil.getGroupThreads(groupId, userId, status,
					start, end);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		java.util.Date modifiedDate, int status) throws RemoteException {
		try {
			int returnValue = MBThreadServiceUtil.getGroupThreadsCount(groupId,
					userId, modifiedDate, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupThreadsCount(long groupId, long userId, int status)
		throws RemoteException {
		try {
			int returnValue = MBThreadServiceUtil.getGroupThreadsCount(groupId,
					userId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		int status, boolean subscribed) throws RemoteException {
		try {
			int returnValue = MBThreadServiceUtil.getGroupThreadsCount(groupId,
					userId, status, subscribed);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		int status, boolean subscribed, boolean includeAnonymous)
		throws RemoteException {
		try {
			int returnValue = MBThreadServiceUtil.getGroupThreadsCount(groupId,
					userId, status, subscribed, includeAnonymous);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap[] getThreads(
		long groupId, long categoryId, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBThread> returnValue =
				MBThreadServiceUtil.getThreads(groupId, categoryId, status,
					start, end);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getThreadsCount(long groupId, long categoryId, int status)
		throws RemoteException {
		try {
			int returnValue = MBThreadServiceUtil.getThreadsCount(groupId,
					categoryId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap moveThread(
		long categoryId, long threadId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBThread returnValue = MBThreadServiceUtil.moveThread(categoryId,
					threadId);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap moveThreadFromTrash(
		long categoryId, long threadId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBThread returnValue = MBThreadServiceUtil.moveThreadFromTrash(categoryId,
					threadId);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap moveThreadToTrash(
		long threadId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBThread returnValue = MBThreadServiceUtil.moveThreadToTrash(threadId);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreThreadFromTrash(long threadId)
		throws RemoteException {
		try {
			MBThreadServiceUtil.restoreThreadFromTrash(threadId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBThreadSoap splitThread(
		long messageId, java.lang.String subject,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBThread returnValue = MBThreadServiceUtil.splitThread(messageId,
					subject, serviceContext);

			return com.liferay.portlet.messageboards.model.MBThreadSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockThread(long threadId) throws RemoteException {
		try {
			MBThreadServiceUtil.unlockThread(threadId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBThreadServiceSoap.class);
}