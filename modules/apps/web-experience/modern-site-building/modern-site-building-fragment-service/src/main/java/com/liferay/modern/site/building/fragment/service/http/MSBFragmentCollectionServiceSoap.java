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

import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link MSBFragmentCollectionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.modern.site.building.fragment.model.MSBFragmentCollection}, that is translated to a
 * {@link com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap}. Methods that SOAP cannot
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
 * @see MSBFragmentCollectionServiceHttp
 * @see com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap
 * @see MSBFragmentCollectionServiceUtil
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionServiceSoap {
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap addMSBFragmentCollection(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentCollection returnValue =
				MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(groupId,
					name, description, serviceContext);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap deleteMSBFragmentCollection(
		long msbFragmentCollectionId) throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentCollection returnValue =
				MSBFragmentCollectionServiceUtil.deleteMSBFragmentCollection(msbFragmentCollectionId);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap[] deleteMSBFragmentCollections(
		long[] msbFragmentCollectionIds) throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> returnValue =
				MSBFragmentCollectionServiceUtil.deleteMSBFragmentCollections(msbFragmentCollectionIds);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap fetchMSBFragmentCollection(
		long msbFragmentCollectionId) throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentCollection returnValue =
				MSBFragmentCollectionServiceUtil.fetchMSBFragmentCollection(msbFragmentCollectionId);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap[] getMSBFragmentCollections(
		long groupId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> returnValue =
				MSBFragmentCollectionServiceUtil.getMSBFragmentCollections(groupId,
					start, end);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap[] getMSBFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> returnValue =
				MSBFragmentCollectionServiceUtil.getMSBFragmentCollections(groupId,
					start, end, orderByComparator);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap[] getMSBFragmentCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> returnValue =
				MSBFragmentCollectionServiceUtil.getMSBFragmentCollections(groupId,
					name, start, end, orderByComparator);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getMSBFragmentCollectionsCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = MSBFragmentCollectionServiceUtil.getMSBFragmentCollectionsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getMSBFragmentCollectionsCount(long groupId,
		java.lang.String name) throws RemoteException {
		try {
			int returnValue = MSBFragmentCollectionServiceUtil.getMSBFragmentCollectionsCount(groupId,
					name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap updateMSBFragmentCollection(
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String description) throws RemoteException {
		try {
			com.liferay.modern.site.building.fragment.model.MSBFragmentCollection returnValue =
				MSBFragmentCollectionServiceUtil.updateMSBFragmentCollection(msbFragmentCollectionId,
					name, description);

			return com.liferay.modern.site.building.fragment.model.MSBFragmentCollectionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MSBFragmentCollectionServiceSoap.class);
}