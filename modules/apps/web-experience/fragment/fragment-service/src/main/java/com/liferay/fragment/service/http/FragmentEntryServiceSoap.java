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

package com.liferay.fragment.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.service.FragmentEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link FragmentEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.fragment.model.FragmentEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.fragment.model.FragmentEntry}, that is translated to a
 * {@link com.liferay.fragment.model.FragmentEntrySoap}. Methods that SOAP cannot
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
 * @see FragmentEntryServiceHttp
 * @see com.liferay.fragment.model.FragmentEntrySoap
 * @see FragmentEntryServiceUtil
 * @generated
 */
@ProviderType
public class FragmentEntryServiceSoap {
	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.addFragmentEntry(groupId,
					fragmentCollectionId, name, status, serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
		long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.addFragmentEntry(groupId,
					fragmentCollectionId, fragmentEntryKey, name, status,
					serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.addFragmentEntry(groupId,
					fragmentCollectionId, name, css, html, js, status,
					serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
		long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.addFragmentEntry(groupId,
					fragmentCollectionId, fragmentEntryKey, name, css, html,
					js, status, serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws RemoteException {
		try {
			FragmentEntryServiceUtil.deleteFragmentEntries(fragmentEntriesIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap deleteFragmentEntry(
		long fragmentEntryId) throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.deleteFragmentEntry(fragmentEntryId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap fetchFragmentEntry(
		long fragmentEntryId) throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.fetchFragmentEntry(fragmentEntryId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId) throws RemoteException {
		try {
			int returnValue = FragmentEntryServiceUtil.getFragmentCollectionsCount(groupId,
					fragmentCollectionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId, java.lang.String name)
		throws RemoteException {
		try {
			int returnValue = FragmentEntryServiceUtil.getFragmentCollectionsCount(groupId,
					fragmentCollectionId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[] getFragmentEntries(
		long fragmentCollectionId) throws RemoteException {
		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry> returnValue =
				FragmentEntryServiceUtil.getFragmentEntries(fragmentCollectionId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[] getFragmentEntries(
		long fragmentCollectionId, int status) throws RemoteException {
		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry> returnValue =
				FragmentEntryServiceUtil.getFragmentEntries(fragmentCollectionId,
					status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[] getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry> returnValue =
				FragmentEntryServiceUtil.getFragmentEntries(groupId,
					fragmentCollectionId, start, end);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[] getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry> returnValue =
				FragmentEntryServiceUtil.getFragmentEntries(groupId,
					fragmentCollectionId, start, end, orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[] getFragmentEntries(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry> returnValue =
				FragmentEntryServiceUtil.getFragmentEntries(groupId,
					fragmentCollectionId, name, start, end, orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getTempFileNames(long groupId,
		java.lang.String folderName) throws RemoteException {
		try {
			java.lang.String[] returnValue = FragmentEntryServiceUtil.getTempFileNames(groupId,
					folderName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap updateFragmentEntry(
		long fragmentEntryId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.updateFragmentEntry(fragmentEntryId,
					name);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap updateFragmentEntry(
		long fragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.fragment.model.FragmentEntry returnValue = FragmentEntryServiceUtil.updateFragmentEntry(fragmentEntryId,
					name, css, html, js, status, serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FragmentEntryServiceSoap.class);
}