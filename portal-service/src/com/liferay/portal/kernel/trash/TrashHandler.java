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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * Represents the interface to manage the basic operations of the Recycle Bin.
 *
 * <p>
 * The basic operations are:
 * </p>
 *
 * <ul>
 * <li>
 * Deletion of entries
 * </li>
 * <li>
 * Restore of entries
 * </li>
 * </ul>
 *
 * <p>
 * The entities that support these operations are:
 * </p>
 *
 * <ul>
 * <li>
 * BlogsEntry {@link com.liferay.portlet.blogs.trash.BlogsEntryTrashHandler}
 * </li>
 * </ul>
 * <li>
 * DLFileEntry {@link
 * com.liferay.portlet.documentlibrary.trash.DLFileEntryTrashHandler}
 * </li>
 *
 * @author Alexander Chow
 */
public interface TrashHandler {

	/**
	 * Deletes all trash entries with the primary keys.
	 *
	 * @param  classPKs the primary keys of the entries to delete
	 * @throws PortalException if an entry with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException;

	/**
	 * Deletes the entry with the primary key.
	 *
	 * @param  classPK the primary key of the entry to delete
	 * @throws PortalException if an entry with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException;

	/**
	 * Returns the class name of the entry.
	 *
	 * @return the class name of the entry
	 */
	public String getClassName();

	/**
	 * Returns the trash renderer associated to the trash entry.
	 *
	 * @param  classPK the primary key of the trash entry
	 * @return the trash renderer associated to the trash entry
	 * @throws PortalException if an entry with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException;

	/**
	 * Restores all entries with the primary keys.
	 *
	 * @param  classPKs the primary keys of the entries to restore
	 * @throws PortalException if an entry with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException;

	/**
	 * Restore the entry defined by the primary key
	 *
	 * @param  classPK the primary key of the entry to restore
	 * @throws PortalException if an entry with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntry(long classPK)
		throws PortalException, SystemException;

}