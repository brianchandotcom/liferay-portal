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

package com.liferay.document.library.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CTDLFolder. This utility wraps
 * <code>com.liferay.document.library.change.tracking.service.impl.CTDLFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTDLFolderService
 * @generated
 */
@ProviderType
public class CTDLFolderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.document.library.change.tracking.service.impl.CTDLFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.util.List<Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use <code>CTDLFolderServiceUtil</code> to access the ctdl folder remote service.
	*/
	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CTDLFolderService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTDLFolderService, CTDLFolderService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTDLFolderService.class);

		ServiceTracker<CTDLFolderService, CTDLFolderService> serviceTracker = new ServiceTracker<CTDLFolderService, CTDLFolderService>(bundle.getBundleContext(),
				CTDLFolderService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}