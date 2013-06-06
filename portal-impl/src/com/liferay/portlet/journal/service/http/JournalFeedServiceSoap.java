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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.journal.service.JournalFeedServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.journal.service.JournalFeedServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.journal.model.JournalFeedSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.journal.model.JournalFeed}, that is translated to a
 * {@link com.liferay.portlet.journal.model.JournalFeedSoap}. Methods that SOAP cannot
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
 * @see JournalFeedServiceHttp
 * @see com.liferay.portlet.journal.model.JournalFeedSoap
 * @see com.liferay.portlet.journal.service.JournalFeedServiceUtil
 * @generated
 */
public class JournalFeedServiceSoap {
	public static com.liferay.portlet.journal.model.JournalFeedSoap addFeed(
		long groupId, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.addFeed(groupId,
					feedId, autoFeedId, name, description, type, structureId,
					templateId, rendererTemplateId, delta, orderByCol,
					orderByType, targetLayoutFriendlyUrl, targetPortletId,
					contentField, feedType, feedVersion, serviceContext);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFeed(long groupId, long feedId)
		throws RemoteException {
		try {
			JournalFeedServiceUtil.deleteFeed(groupId, feedId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFeed(long groupId, java.lang.String feedId)
		throws RemoteException {
		try {
			JournalFeedServiceUtil.deleteFeed(groupId, feedId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeedSoap getFeed(
		long groupId, long feedId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.getFeed(groupId,
					feedId);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeedSoap getFeed(
		long groupId, java.lang.String feedId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.getFeed(groupId,
					feedId);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeedSoap updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.updateFeed(groupId,
					feedId, name, description, type, structureId, templateId,
					rendererTemplateId, delta, orderByCol, orderByType,
					targetLayoutFriendlyUrl, targetPortletId, contentField,
					feedType, feedVersion, serviceContext);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFeedServiceSoap.class);
}