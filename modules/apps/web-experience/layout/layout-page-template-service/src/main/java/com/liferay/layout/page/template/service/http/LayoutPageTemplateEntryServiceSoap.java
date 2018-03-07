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

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link LayoutPageTemplateEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.layout.page.template.model.LayoutPageTemplateEntry}, that is translated to a
 * {@link com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap}. Methods that SOAP cannot
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
 * @see LayoutPageTemplateEntryServiceHttp
 * @see com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap
 * @see LayoutPageTemplateEntryServiceUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryServiceSoap {
	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap addLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.addLayoutPageTemplateEntry(groupId,
					layoutPageTemplateCollectionId, name, fragmentEntryIds,
					serviceContext);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[] deleteLayoutPageTemplateEntries(
		long[] layoutPageTemplateEntryIds) throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> returnValue =
				LayoutPageTemplateEntryServiceUtil.deleteLayoutPageTemplateEntries(layoutPageTemplateEntryIds);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap deleteLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId) throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId) throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.fetchDefaultLayoutPageTemplateEntry(groupId,
					classNameId);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap fetchLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId) throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId) throws RemoteException {
		try {
			int returnValue = LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateCollectionsCount(groupId,
					layoutPageTemplateCollectionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name)
		throws RemoteException {
		try {
			int returnValue = LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateCollectionsCount(groupId,
					layoutPageTemplateCollectionId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[] getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> returnValue =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(groupId,
					layoutPageTemplateCollectionId, start, end);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[] getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> returnValue =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(groupId,
					layoutPageTemplateCollectionId, start, end,
					orderByComparator);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap[] getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> returnValue =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(groupId,
					layoutPageTemplateCollectionId, name, start, end,
					orderByComparator);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder) throws RemoteException {
		try {
			int returnValue = LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntriesCount(groupId,
					layoutPageTemplateFolder);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder, java.lang.String name)
		throws RemoteException {
		try {
			int returnValue = LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntriesCount(groupId,
					layoutPageTemplateFolder, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, long[] fragmentEntryIds,
		java.lang.String editableValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
					fragmentEntryIds, editableValues, serviceContext);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
					name);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, java.lang.String name,
		long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry returnValue =
				LayoutPageTemplateEntryServiceUtil.updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
					name, fragmentEntryIds, serviceContext);

			return com.liferay.layout.page.template.model.LayoutPageTemplateEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPageTemplateEntryServiceSoap.class);
}