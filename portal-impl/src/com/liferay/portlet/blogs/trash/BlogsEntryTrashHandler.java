/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;

/**
 * Implements trash handling for the blogs entry entity.
 *
 * @author Zsolt Berentey
 */
public class BlogsEntryTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = BlogsEntry.class.getName();

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				BlogsEntryServiceUtil.deleteEntry(classPK);
			}
			else {
				BlogsEntryLocalServiceUtil.deleteEntry(classPK);
			}
		}
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryServiceUtil.getEntry(classPK);

		return entry.isInTrash();
	}

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			BlogsEntryServiceUtil.restoreEntryFromTrash(classPK);
		}
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return BlogsEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

}