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

import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>FragmentEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.fragment.model.FragmentEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.fragment.model.FragmentEntry</code>, that is translated to a
 * <code>com.liferay.fragment.model.FragmentEntrySoap</code>. Methods that SOAP
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
 * @see FragmentEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FragmentEntryServiceSoap {

	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.addFragmentEntry(
					groupId, fragmentCollectionId, fragmentEntryKey, name,
					previewFileEntryId, type, status, serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, boolean cacheable,
			String configuration, long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.addFragmentEntry(
					groupId, fragmentCollectionId, fragmentEntryKey, name, css,
					html, js, cacheable, configuration, previewFileEntryId,
					type, status, serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js,
			String configuration, long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.addFragmentEntry(
					groupId, fragmentCollectionId, fragmentEntryKey, name, css,
					html, js, configuration, previewFileEntryId, type, status,
					serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			copyFragmentEntry(
				long groupId, long fragmentEntryId, long fragmentCollectionId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.copyFragmentEntry(
					groupId, fragmentEntryId, fragmentCollectionId,
					serviceContext);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws RemoteException {

		try {
			FragmentEntryServiceUtil.deleteFragmentEntries(fragmentEntriesIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			deleteFragmentEntry(long fragmentEntryId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.deleteFragmentEntry(fragmentEntryId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap fetchDraft(
			long primaryKey)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.fetchDraft(primaryKey);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			fetchFragmentEntry(long fragmentEntryId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.fetchFragmentEntry(fragmentEntryId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap getDraft(
			long primaryKey)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.getDraft(primaryKey);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentCompositionsAndFragmentEntriesCount(
			long groupId, long fragmentCollectionId, int status)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.
					getFragmentCompositionsAndFragmentEntriesCount(
						groupId, fragmentCollectionId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentCompositionsAndFragmentEntriesCount(
			long groupId, long fragmentCollectionId, String name, int status)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.
					getFragmentCompositionsAndFragmentEntriesCount(
						groupId, fragmentCollectionId, name, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntries(long fragmentCollectionId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue = FragmentEntryServiceUtil.getFragmentEntries(
					fragmentCollectionId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntries(
				long groupId, long fragmentCollectionId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue = FragmentEntryServiceUtil.getFragmentEntries(
					groupId, fragmentCollectionId, start, end);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntries(
				long groupId, long fragmentCollectionId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue = FragmentEntryServiceUtil.getFragmentEntries(
					groupId, fragmentCollectionId, start, end,
					orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByName(
				long groupId, long fragmentCollectionId, String name, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue = FragmentEntryServiceUtil.getFragmentEntriesByName(
					groupId, fragmentCollectionId, name, start, end,
					orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByNameAndStatus(
				long groupId, long fragmentCollectionId, String name,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue =
					FragmentEntryServiceUtil.getFragmentEntriesByNameAndStatus(
						groupId, fragmentCollectionId, name, status, start, end,
						orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByStatus(
				long groupId, long fragmentCollectionId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue =
					FragmentEntryServiceUtil.getFragmentEntriesByStatus(
						groupId, fragmentCollectionId, status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByStatus(
				long groupId, long fragmentCollectionId, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue =
					FragmentEntryServiceUtil.getFragmentEntriesByStatus(
						groupId, fragmentCollectionId, status, start, end,
						orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByType(
				long groupId, long fragmentCollectionId, int type, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue = FragmentEntryServiceUtil.getFragmentEntriesByType(
					groupId, fragmentCollectionId, type, start, end,
					orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByTypeAndStatus(
				long groupId, long fragmentCollectionId, int type, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue =
					FragmentEntryServiceUtil.getFragmentEntriesByTypeAndStatus(
						groupId, fragmentCollectionId, type, status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap[]
			getFragmentEntriesByTypeAndStatus(
				long groupId, long fragmentCollectionId, int type, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentEntry>
				returnValue =
					FragmentEntryServiceUtil.getFragmentEntriesByTypeAndStatus(
						groupId, fragmentCollectionId, type, status, start, end,
						orderByComparator);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentEntriesCount(
			long groupId, long fragmentCollectionId)
		throws RemoteException {

		try {
			int returnValue = FragmentEntryServiceUtil.getFragmentEntriesCount(
				groupId, fragmentCollectionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentEntriesCountByName(
			long groupId, long fragmentCollectionId, String name)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.getFragmentEntriesCountByName(
					groupId, fragmentCollectionId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentEntriesCountByNameAndStatus(
			long groupId, long fragmentCollectionId, String name, int status)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.getFragmentEntriesCountByNameAndStatus(
					groupId, fragmentCollectionId, name, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentEntriesCountByStatus(
			long groupId, long fragmentCollectionId, int status)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.getFragmentEntriesCountByStatus(
					groupId, fragmentCollectionId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentEntriesCountByType(
			long groupId, long fragmentCollectionId, int type)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.getFragmentEntriesCountByType(
					groupId, fragmentCollectionId, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentEntriesCountByTypeAndStatus(
			long groupId, long fragmentCollectionId, int type, int status)
		throws RemoteException {

		try {
			int returnValue =
				FragmentEntryServiceUtil.getFragmentEntriesCountByTypeAndStatus(
					groupId, fragmentCollectionId, type, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String[] getTempFileNames(long groupId, String folderName)
		throws RemoteException {

		try {
			String[] returnValue = FragmentEntryServiceUtil.getTempFileNames(
				groupId, folderName);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			moveFragmentEntry(long fragmentEntryId, long fragmentCollectionId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.moveFragmentEntry(
					fragmentEntryId, fragmentCollectionId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap publishDraft(
			com.liferay.fragment.model.FragmentEntrySoap draftFragmentEntry)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.publishDraft(
					com.liferay.fragment.model.impl.FragmentEntryModelImpl.
						toModel(draftFragmentEntry));

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap updateDraft(
			com.liferay.fragment.model.FragmentEntrySoap draftFragmentEntry)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateDraft(
					com.liferay.fragment.model.impl.FragmentEntryModelImpl.
						toModel(draftFragmentEntry));

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(
				com.liferay.fragment.model.FragmentEntrySoap fragmentEntry)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					com.liferay.fragment.model.impl.FragmentEntryModelImpl.
						toModel(fragmentEntry));

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(long fragmentEntryId, long previewFileEntryId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					fragmentEntryId, previewFileEntryId);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(long fragmentEntryId, String name)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					fragmentEntryId, name);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(
				long fragmentEntryId, String name, String css, String html,
				String js, boolean cacheable, String configuration, int status)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					fragmentEntryId, name, css, html, js, cacheable,
					configuration, status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(
				long fragmentEntryId, String name, String css, String html,
				String js, boolean cacheable, String configuration,
				long previewFileEntryId, int status)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					fragmentEntryId, name, css, html, js, cacheable,
					configuration, previewFileEntryId, status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(
				long fragmentEntryId, String name, String css, String html,
				String js, String configuration, int status)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					fragmentEntryId, name, css, html, js, configuration,
					status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntrySoap
			updateFragmentEntry(
				long fragmentEntryId, String name, String css, String html,
				String js, String configuration, long previewFileEntryId,
				int status)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntry returnValue =
				FragmentEntryServiceUtil.updateFragmentEntry(
					fragmentEntryId, name, css, html, js, configuration,
					previewFileEntryId, status);

			return com.liferay.fragment.model.FragmentEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FragmentEntryServiceSoap.class);

}