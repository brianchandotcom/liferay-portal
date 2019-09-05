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

package com.liferay.knowledge.base.service.http;

import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>KBFolderServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.knowledge.base.model.KBFolderSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.knowledge.base.model.KBFolder</code>, that is translated to a
 * <code>com.liferay.knowledge.base.model.KBFolderSoap</code>. Methods that SOAP
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
 * @see KBFolderServiceHttp
 * @generated
 */
public class KBFolderServiceSoap {

	public static com.liferay.knowledge.base.model.KBFolderSoap addKBFolder(
			long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.addKBFolder(
					groupId, parentResourceClassNameId, parentResourcePrimKey,
					name, description, serviceContext);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap deleteKBFolder(
			long kbFolderId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.deleteKBFolder(kbFolderId);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap
			fetchFirstChildKBFolder(long groupId, long kbFolderId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.fetchFirstChildKBFolder(
					groupId, kbFolderId);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap
			fetchFirstChildKBFolder(
				long groupId, long kbFolderId,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBFolder> obc)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.fetchFirstChildKBFolder(
					groupId, kbFolderId, obc);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap fetchKBFolder(
			long kbFolderId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.fetchKBFolder(kbFolderId);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap
			fetchKBFolderByUrlTitle(
				long groupId, long parentKbFolderId, String urlTitle)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.fetchKBFolderByUrlTitle(
					groupId, parentKbFolderId, urlTitle);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap getKBFolder(
			long kbFolderId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.getKBFolder(kbFolderId);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap
			getKBFolderByUrlTitle(
				long groupId, long parentKbFolderId, String urlTitle)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.getKBFolderByUrlTitle(
					groupId, parentKbFolderId, urlTitle);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap[] getKBFolders(
			long groupId, long parentKBFolderId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBFolder>
				returnValue = KBFolderServiceUtil.getKBFolders(
					groupId, parentKBFolderId, start, end);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getKBFoldersAndKBArticlesCount(
			long groupId, long parentResourcePrimKey, int status)
		throws RemoteException {

		try {
			int returnValue =
				KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(
					groupId, parentResourcePrimKey, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws RemoteException {

		try {
			int returnValue = KBFolderServiceUtil.getKBFoldersCount(
				groupId, parentKBFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws RemoteException {

		try {
			KBFolderServiceUtil.moveKBFolder(kbFolderId, parentKBFolderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #updateKBFolder(long, long, long, String, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.knowledge.base.model.KBFolderSoap updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.updateKBFolder(
					parentResourceClassNameId, parentResourcePrimKey,
					kbFolderId, name, description);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBFolderSoap updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBFolder returnValue =
				KBFolderServiceUtil.updateKBFolder(
					parentResourceClassNameId, parentResourcePrimKey,
					kbFolderId, name, description, serviceContext);

			return com.liferay.knowledge.base.model.KBFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(KBFolderServiceSoap.class);

}