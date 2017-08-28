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

package com.liferay.modern.site.building.fragment.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link MSBFragmentEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.modern.site.building.fragment.model.MSBFragmentEntry}, that is translated to a
 * {@link com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap}. Methods that SOAP cannot
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
 * @see MSBFragmentEntryServiceHttp
 * @see com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap
 * @see MSBFragmentEntryServiceUtil
 * @generated
 */
@ProviderType
public class MSBFragmentEntryServiceSoap {
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap addMSBFragmentEntry(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentEntry returnValue =
				MSBFragmentEntryServiceUtil.addMSBFragmentEntry(groupId,
					msbFragmentCollectionId, name, css, html, js, serviceContext);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap[] deleteMSBFragmentEntries(
		long[] msbFragmentEntriesIds) throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> returnValue =
				MSBFragmentEntryServiceUtil.deleteMSBFragmentEntries(msbFragmentEntriesIds);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap deleteMSBFragmentEntry(
		long msbFragmentEntryId) throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentEntry returnValue =
				MSBFragmentEntryServiceUtil.deleteMSBFragmentEntry(msbFragmentEntryId);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap[] fetchMSBFragmentEntries(
		long msbFragmentCollectionId) throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> returnValue =
				MSBFragmentEntryServiceUtil.fetchMSBFragmentEntries(msbFragmentCollectionId);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap fetchMSBFragmentEntry(
		long msbFragmentEntryId) throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentEntry returnValue =
				MSBFragmentEntryServiceUtil.fetchMSBFragmentEntry(msbFragmentEntryId);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getMSBFragmentCollectionsCount(
		long msbFragmentCollectionId) throws RemoteException {
		try {
			int returnValue = MSBFragmentEntryServiceUtil.getMSBFragmentCollectionsCount(msbFragmentCollectionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap[] getMSBFragmentEntries(
		long msbFragmentCollectionId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> returnValue =
				MSBFragmentEntryServiceUtil.getMSBFragmentEntries(msbFragmentCollectionId,
					start, end);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap[] getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> returnValue =
				MSBFragmentEntryServiceUtil.getMSBFragmentEntries(groupId,
					msbFragmentCollectionId, start, end, orderByComparator);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap[] getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> returnValue =
				MSBFragmentEntryServiceUtil.getMSBFragmentEntries(groupId,
					msbFragmentCollectionId, name, start, end, orderByComparator);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap updateMSBFragmentEntry(
		long msbFragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js) throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentEntry returnValue =
				MSBFragmentEntryServiceUtil.updateMSBFragmentEntry(msbFragmentEntryId,
					name, css, html, js);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MSBFragmentEntryServiceSoap.class);
}