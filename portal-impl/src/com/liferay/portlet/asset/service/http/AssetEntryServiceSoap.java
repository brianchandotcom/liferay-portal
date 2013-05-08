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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.asset.service.AssetEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.asset.model.AssetEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.asset.model.AssetEntry}, that is translated to a
 * {@link com.liferay.portlet.asset.model.AssetEntrySoap}. Methods that SOAP cannot
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
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryServiceHttp
 * @see       com.liferay.portlet.asset.model.AssetEntrySoap
 * @see       com.liferay.portlet.asset.service.AssetEntryServiceUtil
 * @generated
 */
public class AssetEntryServiceSoap {
	public static com.liferay.portlet.asset.model.AssetEntrySoap[] getCompanyEntries(
		long companyId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetEntry> returnValue =
				AssetEntryServiceUtil.getCompanyEntries(companyId, start, end);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanyEntriesCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.getCompanyEntriesCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap[] getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetEntry> returnValue =
				AssetEntryServiceUtil.getEntries(entryQuery);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.getEntriesCount(entryQuery);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap getEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.getEntry(entryId);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap incrementViewCounter(
		java.lang.String className, long classPK) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.incrementViewCounter(className,
					classPK);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap updateEntry(
		long groupId, java.util.Date createDate, java.util.Date modifiedDate,
		java.lang.String className, long classPK, java.lang.String classUuid,
		long classTypeId, long[] categoryIds, java.lang.String[] tagNames,
		boolean visible, java.util.Date startDate, java.util.Date endDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.updateEntry(groupId,
					createDate, modifiedDate, className, classPK, classUuid,
					classTypeId, categoryIds, tagNames, visible, startDate,
					endDate, expirationDate, mimeType, title, description,
					summary, url, layoutUuid, height, width, priority, sync);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, String,
	long, String, long, long[], String[], boolean, Date, Date,
	Date, String, String, String, String, String, String, int,
	int, Integer, boolean)}
	*/
	public static com.liferay.portlet.asset.model.AssetEntrySoap updateEntry(
		long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.updateEntry(groupId,
					className, classPK, classUuid, classTypeId, categoryIds,
					tagNames, visible, startDate, endDate, publishDate,
					expirationDate, mimeType, title, description, summary, url,
					layoutUuid, height, width, priority, sync);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, Date,
	Date. String, long, String, long, long[], String[], boolean,
	Date, Date, Date, String, String, String, String, String,
	String, int, int, Integer, boolean)}
	*/
	public static com.liferay.portlet.asset.model.AssetEntrySoap updateEntry(
		long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Integer priority, boolean sync)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.updateEntry(groupId,
					className, classPK, classUuid, classTypeId, categoryIds,
					tagNames, visible, startDate, endDate, expirationDate,
					mimeType, title, description, summary, url, layoutUuid,
					height, width, priority, sync);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntryServiceSoap.class);
}