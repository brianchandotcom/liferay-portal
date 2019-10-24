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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MBMessageServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageServiceSoap
 * @generated
 */
public class MBMessageServiceHttp {

	public static com.liferay.message.boards.model.MBMessage
			addDiscussionMessage(
				HttpPrincipal httpPrincipal, long groupId, String className,
				long classPK, long threadId, long parentMessageId,
				String subject, String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addDiscussionMessage",
				_addDiscussionMessageParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, threadId,
				parentMessageId, subject, body, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			String subject, String body, String format,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addMessage",
				_addMessageParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, subject, body, format,
				inputStreamOVPs, anonymous, priority, allowPingbacks,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			String subject, String body, String format, String fileName,
			java.io.File file, boolean anonymous, double priority,
			boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			   java.io.FileNotFoundException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addMessage",
				_addMessageParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, subject, body, format, fileName,
				file, anonymous, priority, allowPingbacks, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof java.io.FileNotFoundException) {
					throw (java.io.FileNotFoundException)e;
				}

				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			HttpPrincipal httpPrincipal, long categoryId, String subject,
			String body,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addMessage",
				_addMessageParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, subject, body, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			HttpPrincipal httpPrincipal, long parentMessageId, String subject,
			String body, String format,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addMessage",
				_addMessageParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentMessageId, subject, body, format,
				inputStreamOVPs, anonymous, priority, allowPingbacks,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addMessageAttachment(
			HttpPrincipal httpPrincipal, long messageId, String fileName,
			java.io.File file, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addMessageAttachment",
				_addMessageAttachmentParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, fileName, file, mimeType);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempAttachment(
				HttpPrincipal httpPrincipal, long groupId, long categoryId,
				String folderName, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "addTempAttachment",
				_addTempAttachmentParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, folderName, fileName,
				inputStream, mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteDiscussionMessage(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "deleteDiscussionMessage",
				_deleteDiscussionMessageParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteMessage(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "deleteMessage",
				_deleteMessageParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteMessageAttachment(
			HttpPrincipal httpPrincipal, long messageId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "deleteMessageAttachment",
				_deleteMessageAttachmentParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, fileName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteMessageAttachments(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "deleteMessageAttachments",
				_deleteMessageAttachmentsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTempAttachment(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			String folderName, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "deleteTempAttachment",
				_deleteTempAttachmentParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, folderName, fileName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void emptyMessageAttachments(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "emptyMessageAttachments",
				_emptyMessageAttachmentsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
			getCategoryMessages(
				HttpPrincipal httpPrincipal, long groupId, long categoryId,
				int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getCategoryMessages",
				_getCategoryMessagesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBMessage>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoryMessagesCount(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getCategoryMessagesCount",
				_getCategoryMessagesCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getCategoryMessagesRSS(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getCategoryMessagesRSS",
				_getCategoryMessagesRSSParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getCompanyMessagesRSS(
			HttpPrincipal httpPrincipal, long companyId, int status, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getCompanyMessagesRSS",
				_getCompanyMessagesRSSParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, max, type, version, displayStyle,
				feedURL, entryURL, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupMessagesCount(
		HttpPrincipal httpPrincipal, long groupId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getGroupMessagesCount",
				_getGroupMessagesCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getGroupMessagesRSS(
			HttpPrincipal httpPrincipal, long groupId, int status, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getGroupMessagesRSS",
				_getGroupMessagesRSSParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, max, type, version, displayStyle,
				feedURL, entryURL, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getGroupMessagesRSS(
			HttpPrincipal httpPrincipal, long groupId, long userId, int status,
			int max, String type, double version, String displayStyle,
			String feedURL, String entryURL,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getGroupMessagesRSS",
				_getGroupMessagesRSSParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage getMessage(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getMessage",
				_getMessageParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessageDisplay
			getMessageDisplay(
				HttpPrincipal httpPrincipal, long messageId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getMessageDisplay",
				_getMessageDisplayParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessageDisplay)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String[] getTempAttachmentNames(
			HttpPrincipal httpPrincipal, long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getTempAttachmentNames",
				_getTempAttachmentNamesParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getThreadAnswersCount(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		long threadId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getThreadAnswersCount",
				_getThreadAnswersCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, threadId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadMessages(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			long threadId, int status, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getThreadMessages",
				_getThreadMessagesParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, threadId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBMessage>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getThreadMessagesCount(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		long threadId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getThreadMessagesCount",
				_getThreadMessagesCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, threadId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getThreadMessagesRSS(
			HttpPrincipal httpPrincipal, long threadId, int status, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "getThreadMessagesRSS",
				_getThreadMessagesRSSParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId, status, max, type, version, displayStyle,
				feedURL, entryURL, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void moveMessageAttachmentToTrash(
			HttpPrincipal httpPrincipal, long messageId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "moveMessageAttachmentToTrash",
				_moveMessageAttachmentToTrashParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, fileName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreMessageAttachmentFromTrash(
			HttpPrincipal httpPrincipal, long messageId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "restoreMessageAttachmentFromTrash",
				_restoreMessageAttachmentFromTrashParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, fileName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeMessage(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "subscribeMessage",
				_subscribeMessageParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsubscribeMessage(
			HttpPrincipal httpPrincipal, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "unsubscribeMessage",
				_unsubscribeMessageParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateAnswer(
			HttpPrincipal httpPrincipal, long messageId, boolean answer,
			boolean cascade)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "updateAnswer",
				_updateAnswerParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, answer, cascade);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage
			updateDiscussionMessage(
				HttpPrincipal httpPrincipal, String className, long classPK,
				long messageId, String subject, String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "updateDiscussionMessage",
				_updateDiscussionMessageParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, messageId, subject, body,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBMessage updateMessage(
			HttpPrincipal httpPrincipal, long messageId, String subject,
			String body,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBMessageServiceUtil.class, "updateMessage",
				_updateMessageParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, subject, body, inputStreamOVPs, priority,
				allowPingbacks, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBMessage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageServiceHttp.class);

	private static final Class<?>[] _addDiscussionMessageParameterTypes0 =
		new Class[] {
			long.class, String.class, long.class, long.class, long.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addMessageParameterTypes1 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		java.util.List.class, boolean.class, double.class, boolean.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addMessageParameterTypes2 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		String.class, java.io.File.class, boolean.class, double.class,
		boolean.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addMessageParameterTypes3 = new Class[] {
		long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addMessageParameterTypes4 = new Class[] {
		long.class, String.class, String.class, String.class,
		java.util.List.class, boolean.class, double.class, boolean.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addMessageAttachmentParameterTypes5 =
		new Class[] {
			long.class, String.class, java.io.File.class, String.class
		};
	private static final Class<?>[] _addTempAttachmentParameterTypes6 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.io.InputStream.class, String.class
		};
	private static final Class<?>[] _deleteDiscussionMessageParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteMessageParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteMessageAttachmentParameterTypes9 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _deleteMessageAttachmentsParameterTypes10 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteTempAttachmentParameterTypes11 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _emptyMessageAttachmentsParameterTypes12 =
		new Class[] {long.class};
	private static final Class<?>[] _getCategoryMessagesParameterTypes13 =
		new Class[] {long.class, long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getCategoryMessagesCountParameterTypes14 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getCategoryMessagesRSSParameterTypes15 =
		new Class[] {
			long.class, long.class, int.class, int.class, String.class,
			double.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getCompanyMessagesRSSParameterTypes16 =
		new Class[] {
			long.class, int.class, int.class, String.class, double.class,
			String.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getGroupMessagesCountParameterTypes17 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getGroupMessagesRSSParameterTypes18 =
		new Class[] {
			long.class, int.class, int.class, String.class, double.class,
			String.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getGroupMessagesRSSParameterTypes19 =
		new Class[] {
			long.class, long.class, int.class, int.class, String.class,
			double.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getMessageParameterTypes20 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getMessageDisplayParameterTypes21 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getTempAttachmentNamesParameterTypes22 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getThreadAnswersCountParameterTypes23 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getThreadMessagesParameterTypes24 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _getThreadMessagesCountParameterTypes25 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getThreadMessagesRSSParameterTypes26 =
		new Class[] {
			long.class, int.class, int.class, String.class, double.class,
			String.class, String.class, String.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[]
		_moveMessageAttachmentToTrashParameterTypes27 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_restoreMessageAttachmentFromTrashParameterTypes28 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _subscribeMessageParameterTypes29 =
		new Class[] {long.class};
	private static final Class<?>[] _unsubscribeMessageParameterTypes30 =
		new Class[] {long.class};
	private static final Class<?>[] _updateAnswerParameterTypes31 =
		new Class[] {long.class, boolean.class, boolean.class};
	private static final Class<?>[] _updateDiscussionMessageParameterTypes32 =
		new Class[] {
			String.class, long.class, long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateMessageParameterTypes33 =
		new Class[] {
			long.class, String.class, String.class, java.util.List.class,
			double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}