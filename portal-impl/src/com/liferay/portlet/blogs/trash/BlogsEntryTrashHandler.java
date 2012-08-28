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
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

/**
 * Represents the trash handler for blogs entries entity.
 *
 * @author Zsolt Berentey
 */
public class BlogsEntryTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = BlogsEntry.class.getName();

	/**
	 * Deletes all blogs entries with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the blogs entries to be deleted
	 * @param  checkPermission whether to check permission before deleting each
	 *         blog entry
	 * @throws PortalException if any one of the blogs entries could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Returns the blogs entry entity's class name
	 *
	 * @return the blogs entry entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	/**
	 * Restores all blogs entries with the matching primary keys.
	 *
	 * @param  classPKs the primary key of the blogs entry to be restored
	 * @throws PortalException if any one of the blogs entries could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			BlogsEntryServiceUtil.restoreEntryFromTrash(classPK);
		}
	}

}