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

package com.liferay.document.library.change.tracking.service.impl;

import com.liferay.document.library.change.tracking.service.base.CTDLFolderServiceBaseImpl;
import com.liferay.document.library.change.tracking.service.persistence.CTDLFolderFinderOverride;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;

/**
 * The implementation of the ctdl folder remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.document.library.change.tracking.service.CTDLFolderService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTDLFolderServiceBaseImpl
 */
public class CTDLFolderServiceImpl extends CTDLFolderServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.document.library.change.tracking.service.CTDLFolderServiceUtil</code> to access the ctdl folder remote service.
	 */
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		if (!ModelResourcePermissionHelper.contains(
				_dlFolderModelResourcePermission, getPermissionChecker(),
				groupId, folderId, ActionKeys.VIEW)) {

			return 0;
		}

		if (queryDefinition.isIncludeOwner() &&
			(queryDefinition.getOwnerUserId() != 0)) {

			queryDefinition.setOwnerUserId(getUserId());
		}

		return _dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	private static volatile ModelResourcePermission<DLFolder>
		_dlFolderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CTDLFolderServiceImpl.class, "_dlFolderModelResourcePermission",
				DLFolder.class);

	@BeanReference(type = CTDLFolderFinderOverride.class)
	private CTDLFolderFinderOverride _dlFolderFinder;

}