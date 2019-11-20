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

package com.liferay.layout.page.template.service.http;

import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>LayoutPageTemplateEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.layout.page.template.model.LayoutPageTemplateEntry</code>, that is translated to a
 * <code>com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap</code>. Methods that SOAP
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
 * @see LayoutPageTemplateEntryServiceHttp
 * @generated
 */
public class LayoutPageTemplateEntryServiceSoap {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, String, int, long,
	 int, ServiceContext)}
	 */
	@Deprecated
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				addLayoutPageTemplateEntry(
					long groupId, long layoutPageTemplateCollectionId,
					String name, int type, int status,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						addLayoutPageTemplateEntry(
							groupId, layoutPageTemplateCollectionId, name, type,
							status, serviceContext);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				addLayoutPageTemplateEntry(
					long groupId, long layoutPageTemplateCollectionId,
					String name, int type, long masterLayoutPlid, int status,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						addLayoutPageTemplateEntry(
							groupId, layoutPageTemplateCollectionId, name, type,
							masterLayoutPlid, status, serviceContext);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				addLayoutPageTemplateEntry(
					long groupId, long layoutPageTemplateCollectionId,
					String name, int status, long classNameId, long classTypeId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						addLayoutPageTemplateEntry(
							groupId, layoutPageTemplateCollectionId, name,
							status, classNameId, classTypeId, serviceContext);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				copyLayoutPageTemplateEntry(
					long groupId, long layoutPageTemplateCollectionId,
					long layoutPageTemplateEntryId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						copyLayoutPageTemplateEntry(
							groupId, layoutPageTemplateCollectionId,
							layoutPageTemplateEntryId, serviceContext);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLayoutPageTemplateEntries(
			long[] layoutPageTemplateEntryIds)
		throws RemoteException {

		try {
			LayoutPageTemplateEntryServiceUtil.deleteLayoutPageTemplateEntries(
				layoutPageTemplateEntryIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				deleteLayoutPageTemplateEntry(long layoutPageTemplateEntryId)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						deleteLayoutPageTemplateEntry(
							layoutPageTemplateEntryId);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				fetchDefaultLayoutPageTemplateEntry(
					long groupId, long classNameId, long classTypeId)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						fetchDefaultLayoutPageTemplateEntry(
							groupId, classNameId, classTypeId);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				fetchLayoutPageTemplateEntry(long layoutPageTemplateEntryId)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				fetchLayoutPageTemplateEntryByUuidAndGroupId(
					String uuid, long groupId)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						fetchLayoutPageTemplateEntryByUuidAndGroupId(
							uuid, groupId);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, int type, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, type, status, start, end,
								orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, type, start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, int type,
					boolean defaultTemplate)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, type, defaultTemplate);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long layoutPageTemplateCollectionId,
					int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, layoutPageTemplateCollectionId, start,
								end);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long layoutPageTemplateCollectionId,
					int status, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, layoutPageTemplateCollectionId, status,
								start, end);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long layoutPageTemplateCollectionId,
					int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, layoutPageTemplateCollectionId, status,
								start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long layoutPageTemplateCollectionId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, layoutPageTemplateCollectionId, start,
								end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, long classTypeId, int type)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, classTypeId, type);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, long classTypeId, int type,
					int status)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, classTypeId, type,
								status);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, long classTypeId, int type,
					int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, classTypeId, type, status,
								start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, long classTypeId, int type,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, classTypeId, type, start,
								end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, long classTypeId,
					String name, int type, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, classTypeId, name, type,
								status, start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long classNameId, long classTypeId,
					String name, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, classNameId, classTypeId, name, type,
								start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long layoutPageTemplateCollectionId,
					String name, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, layoutPageTemplateCollectionId, name,
								status, start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, long layoutPageTemplateCollectionId,
					String name, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, layoutPageTemplateCollectionId, name,
								start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, String name, int type, int status, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, name, type, status, start, end,
								orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntries(
					long groupId, String name, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								groupId, name, type, start, end,
								orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[]
				getLayoutPageTemplateEntriesByType(
					long groupId, long layoutPageTemplateCollectionId, int type,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.layout.page.template.model.
							LayoutPageTemplateEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
					returnValue =
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntriesByType(
								groupId, layoutPageTemplateCollectionId, type,
								start, end, orderByComparator);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId, int type)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(groupId, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, int type, int status)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(groupId, type, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long layoutPageTemplateCollectionId)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, layoutPageTemplateCollectionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long layoutPageTemplateCollectionId, int status)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, layoutPageTemplateCollectionId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long classNameId, long classTypeId, int type)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, classNameId, classTypeId, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long classNameId, long classTypeId, int type,
			int status)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, classNameId, classTypeId, type, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long classNameId, long classTypeId, String name,
			int type)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, classNameId, classTypeId, name, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long classNameId, long classTypeId, String name,
			int type, int status)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, classNameId, classTypeId, name, type, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long layoutPageTemplateCollectionId, String name)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, layoutPageTemplateCollectionId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, long layoutPageTemplateCollectionId, String name,
			int status)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, layoutPageTemplateCollectionId, name, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, String name, int type)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(groupId, name, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(
			long groupId, String name, int type, int status)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						groupId, name, type, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCountByType(
			long groupId, long layoutPageTemplateCollectionId, int type)
		throws RemoteException {

		try {
			int returnValue =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCountByType(
						groupId, layoutPageTemplateCollectionId, type);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				updateLayoutPageTemplateEntry(
					long layoutPageTemplateEntryId, boolean defaultTemplate)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntryId, defaultTemplate);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				updateLayoutPageTemplateEntry(
					long layoutPageTemplateEntryId, long previewFileEntryId)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntryId, previewFileEntryId);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				updateLayoutPageTemplateEntry(
					long layoutPageTemplateEntryId, long[] fragmentEntryIds,
					String editableValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntryId, fragmentEntryIds,
							editableValues, serviceContext);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				updateLayoutPageTemplateEntry(
					long layoutPageTemplateEntryId, String name)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntryId, name);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				updateLayoutPageTemplateEntry(
					long layoutPageTemplateEntryId, String name,
					long[] fragmentEntryIds,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue =
					LayoutPageTemplateEntryServiceUtil.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntryId, name, fragmentEntryIds,
							serviceContext);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
				updateStatus(long layoutPageTemplateEntryId, int status)
			throws RemoteException {

		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				returnValue = LayoutPageTemplateEntryServiceUtil.updateStatus(
					layoutPageTemplateEntryId, status);

			return com.liferay.layout.page.template.model.
				LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryServiceSoap.class);

}