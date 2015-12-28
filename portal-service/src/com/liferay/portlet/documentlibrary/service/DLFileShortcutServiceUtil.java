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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for DLFileShortcut. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileShortcutServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutServiceImpl
 * @generated
 */
@ProviderType
public class DLFileShortcutServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long groupId, long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFileShortcut(groupId, repositoryId, folderId,
			toFileEntryId, serviceContext);
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileShortcut(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileShortcut(fileShortcutId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long fileShortcutId, long repositoryId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFileShortcut(fileShortcutId, repositoryId, folderId,
			toFileEntryId, serviceContext);
	}

	public static void updateFileShortcuts(long oldToFileEntryId,
		long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateFileShortcuts(oldToFileEntryId, newToFileEntryId);
	}

	public static DLFileShortcutService getService() {
		if (_service == null) {
			_service = (DLFileShortcutService)PortalBeanLocatorUtil.locate(DLFileShortcutService.class.getName());

			ReferenceRegistry.registerReference(DLFileShortcutServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static DLFileShortcutService _service;
}