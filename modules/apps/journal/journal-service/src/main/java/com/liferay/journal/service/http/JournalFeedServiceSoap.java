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

package com.liferay.journal.service.http;

import com.liferay.journal.service.JournalFeedServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>JournalFeedServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.journal.model.JournalFeedSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.journal.model.JournalFeed</code>, that is translated to a
 * <code>com.liferay.journal.model.JournalFeedSoap</code>. Methods that SOAP
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
 * @see JournalFeedServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class JournalFeedServiceSoap {

	public static com.liferay.journal.model.JournalFeedSoap addFeed(
			long groupId, String feedId, boolean autoFeedId, String name,
			String description, String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFeed returnValue =
				JournalFeedServiceUtil.addFeed(
					groupId, feedId, autoFeedId, name, description,
					ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey,
					delta, orderByCol, orderByType, targetLayoutFriendlyUrl,
					targetPortletId, contentField, feedType, feedVersion,
					serviceContext);

			return com.liferay.journal.model.JournalFeedSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteFeed(long feedId) throws RemoteException {
		try {
			JournalFeedServiceUtil.deleteFeed(feedId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteFeed(long groupId, String feedId)
		throws RemoteException {

		try {
			JournalFeedServiceUtil.deleteFeed(groupId, feedId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFeedSoap getFeed(long feedId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFeed returnValue =
				JournalFeedServiceUtil.getFeed(feedId);

			return com.liferay.journal.model.JournalFeedSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFeedSoap getFeed(
			long groupId, String feedId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFeed returnValue =
				JournalFeedServiceUtil.getFeed(groupId, feedId);

			return com.liferay.journal.model.JournalFeedSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFeedSoap updateFeed(
			long groupId, String feedId, String name, String description,
			String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFeed returnValue =
				JournalFeedServiceUtil.updateFeed(
					groupId, feedId, name, description, ddmStructureKey,
					ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
					orderByType, targetLayoutFriendlyUrl, targetPortletId,
					contentField, feedType, feedVersion, serviceContext);

			return com.liferay.journal.model.JournalFeedSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalFeedServiceSoap.class);

}