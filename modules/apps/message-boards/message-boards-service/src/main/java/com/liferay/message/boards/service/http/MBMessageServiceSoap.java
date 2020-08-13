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

package com.liferay.message.boards.service.http;

import com.liferay.message.boards.service.MBMessageServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>MBMessageServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.message.boards.model.MBMessageSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.message.boards.model.MBMessage</code>, that is translated to a
 * <code>com.liferay.message.boards.model.MBMessageSoap</code>. Methods that SOAP
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
 * @see MBMessageServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class MBMessageServiceSoap {

	public static com.liferay.message.boards.model.MBMessageSoap
			addDiscussionMessage(
				long groupId, String className, long classPK, long threadId,
				long parentMessageId, String subject, String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.addDiscussionMessage(
					groupId, className, classPK, threadId, parentMessageId,
					subject, body, serviceContext);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap addMessage(
			long groupId, long categoryId, String subject, String body,
			String format,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.addMessage(
					groupId, categoryId, subject, body, format, inputStreamOVPs,
					anonymous, priority, allowPingbacks, serviceContext);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap addMessage(
			long categoryId, String subject, String body,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.addMessage(
					categoryId, subject, body, serviceContext);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap addMessage(
			long parentMessageId, String subject, String body, String format,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.addMessage(
					parentMessageId, subject, body, format, inputStreamOVPs,
					anonymous, priority, allowPingbacks, serviceContext);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteDiscussionMessage(long messageId)
		throws RemoteException {

		try {
			MBMessageServiceUtil.deleteDiscussionMessage(messageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteMessage(long messageId) throws RemoteException {
		try {
			MBMessageServiceUtil.deleteMessage(messageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteMessageAttachment(long messageId, String fileName)
		throws RemoteException {

		try {
			MBMessageServiceUtil.deleteMessageAttachment(messageId, fileName);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteMessageAttachments(long messageId)
		throws RemoteException {

		try {
			MBMessageServiceUtil.deleteMessageAttachments(messageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteTempAttachment(
			long groupId, long categoryId, String folderName, String fileName)
		throws RemoteException {

		try {
			MBMessageServiceUtil.deleteTempAttachment(
				groupId, categoryId, folderName, fileName);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void emptyMessageAttachments(long messageId)
		throws RemoteException {

		try {
			MBMessageServiceUtil.emptyMessageAttachments(messageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap
			fetchMBMessageByUrlSubject(long groupId, String urlSubject)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.fetchMBMessageByUrlSubject(
					groupId, urlSubject);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap[]
			getCategoryMessages(
				long groupId, long categoryId, int status, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBMessage>
				returnValue = MBMessageServiceUtil.getCategoryMessages(
					groupId, categoryId, status, start, end);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoryMessagesCount(
			long groupId, long categoryId, int status)
		throws RemoteException {

		try {
			int returnValue = MBMessageServiceUtil.getCategoryMessagesCount(
				groupId, categoryId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupMessagesCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = MBMessageServiceUtil.getGroupMessagesCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap getMessage(
			long messageId)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.getMessage(messageId);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String[] getTempAttachmentNames(
			long groupId, String folderName)
		throws RemoteException {

		try {
			String[] returnValue = MBMessageServiceUtil.getTempAttachmentNames(
				groupId, folderName);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getThreadAnswersCount(
			long groupId, long categoryId, long threadId)
		throws RemoteException {

		try {
			int returnValue = MBMessageServiceUtil.getThreadAnswersCount(
				groupId, categoryId, threadId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap[]
			getThreadMessages(
				long groupId, long categoryId, long threadId, int status,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBMessage>
				returnValue = MBMessageServiceUtil.getThreadMessages(
					groupId, categoryId, threadId, status, start, end);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getThreadMessagesCount(
			long groupId, long categoryId, long threadId, int status)
		throws RemoteException {

		try {
			int returnValue = MBMessageServiceUtil.getThreadMessagesCount(
				groupId, categoryId, threadId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void moveMessageAttachmentToTrash(
			long messageId, String fileName)
		throws RemoteException {

		try {
			MBMessageServiceUtil.moveMessageAttachmentToTrash(
				messageId, fileName);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void restoreMessageAttachmentFromTrash(
			long messageId, String fileName)
		throws RemoteException {

		try {
			MBMessageServiceUtil.restoreMessageAttachmentFromTrash(
				messageId, fileName);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void subscribeMessage(long messageId) throws RemoteException {
		try {
			MBMessageServiceUtil.subscribeMessage(messageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unsubscribeMessage(long messageId)
		throws RemoteException {

		try {
			MBMessageServiceUtil.unsubscribeMessage(messageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateAnswer(
			long messageId, boolean answer, boolean cascade)
		throws RemoteException {

		try {
			MBMessageServiceUtil.updateAnswer(messageId, answer, cascade);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap
			updateDiscussionMessage(
				String className, long classPK, long messageId, String subject,
				String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.updateDiscussionMessage(
					className, classPK, messageId, subject, body,
					serviceContext);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBMessageSoap updateMessage(
			long messageId, String subject, String body,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBMessage returnValue =
				MBMessageServiceUtil.updateMessage(
					messageId, subject, body, inputStreamOVPs, priority,
					allowPingbacks, serviceContext);

			return com.liferay.message.boards.model.MBMessageSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageServiceSoap.class);

}