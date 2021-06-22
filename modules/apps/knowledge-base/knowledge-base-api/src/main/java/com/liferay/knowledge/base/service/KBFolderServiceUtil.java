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

package com.liferay.knowledge.base.service;

import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for KBFolder. This utility wraps
 * <code>com.liferay.knowledge.base.service.impl.KBFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolderService
 * @generated
 */
public class KBFolderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static KBFolder addKBFolder(
			String externalReferenceCode, long groupId,
			long parentResourceClassNameId, long parentResourcePrimKey,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKBFolder(
			externalReferenceCode, groupId, parentResourceClassNameId,
			parentResourcePrimKey, name, description, serviceContext);
	}

	public static KBFolder deleteKBFolder(long kbFolderId)
		throws PortalException {

		return getService().deleteKBFolder(kbFolderId);
	}

	public static KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId)
		throws PortalException {

		return getService().fetchFirstChildKBFolder(groupId, kbFolderId);
	}

	public static KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId,
			OrderByComparator<KBFolder> orderByComparator)
		throws PortalException {

		return getService().fetchFirstChildKBFolder(
			groupId, kbFolderId, orderByComparator);
	}

	public static KBFolder fetchKBFolder(long kbFolderId)
		throws PortalException {

		return getService().fetchKBFolder(kbFolderId);
	}

	public static KBFolder fetchKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws PortalException {

		return getService().fetchKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	public static KBFolder getKBFolder(long kbFolderId) throws PortalException {
		return getService().getKBFolder(kbFolderId);
	}

	public static KBFolder getKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws PortalException {

		return getService().getKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	public static List<KBFolder> getKBFolders(
			long groupId, long parentKBFolderId, int start, int end)
		throws PortalException {

		return getService().getKBFolders(groupId, parentKBFolderId, start, end);
	}

	public static List<Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end, OrderByComparator<?> orderByComparator) {

		return getService().getKBFoldersAndKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	public static int getKBFoldersAndKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return getService().getKBFoldersAndKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	public static int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws PortalException {

		return getService().getKBFoldersCount(groupId, parentKBFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws PortalException {

		getService().moveKBFolder(kbFolderId, parentKBFolderId);
	}

	public static KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateKBFolder(
			parentResourceClassNameId, parentResourcePrimKey, kbFolderId, name,
			description, serviceContext);
	}

	public static KBFolderService getService() {
		return _service;
	}

	private static volatile KBFolderService _service;

}