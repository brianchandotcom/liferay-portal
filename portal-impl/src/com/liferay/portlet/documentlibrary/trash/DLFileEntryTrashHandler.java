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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.trash.DuplicateTrashEntryException;
import com.liferay.portlet.trash.model.TrashEntry;

/**
 * Represents the trash handler for the file entry entity.
 *
 * @author Alexander Chow
 * @author Manuel de la Peña
 */
public class DLFileEntryTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = DLFileEntry.class.getName();

	public void checkDuplicateEntry(TrashEntry entry)
		throws DuplicateTrashEntryException {

		DLFileEntry duplicatedFileEntry = null;

		try {
			DLFileEntry dlFileEntry = getDLFileEntry(entry.getClassPK());

			String restoredTitle = dlFileEntry.getTitle();
			String originalTitle = restoredTitle;

			if (restoredTitle.indexOf(StringPool.FORWARD_SLASH) > 0) {
				originalTitle = restoredTitle.substring(0,
					restoredTitle.indexOf(StringPool.FORWARD_SLASH));
			}

			duplicatedFileEntry =
				DLFileEntryLocalServiceUtil.fetchFileEntry(
					dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
					originalTitle);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (duplicatedFileEntry != null) {
			DuplicateTrashEntryException dtee =
				new DuplicateTrashEntryException();

			dtee.setDuplicateEntryId(entry.getEntryId());

			throw dtee;
		}
	}

	/**
	 * Deletes all file entries with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the file entries to be deleted
	 * @throws PortalException if any one of the file entries could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			DLAppServiceUtil.deleteFileEntry(classPK);
		}
	}

	/**
	 * Returns the file entry entity's class name
	 *
	 * @return the file entry entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	/**
	 * Restores all file entries with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the file entries to be deleted
	 * @throws PortalException if any one of the file entries could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			DLAppServiceUtil.restoreFileEntryFromTrash(classPK);
		}
	}

	public void updateEntryTitle(long classPK, String name)
		throws SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		if (dlFileEntry != null) {
			dlFileEntry.setTitle(name);
		}

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry, false);
	}

	protected DLFileEntry getDLFileEntry (long classPK) {

		DLFileEntry dlFileEntry = null;

		try {
			Repository repository = RepositoryServiceUtil.getRepositoryImpl(
				0, classPK, 0);

			if (!(repository instanceof LiferayRepository)) {
				throw new InvalidRepositoryException(
					"Repository " + repository.getRepositoryId() +
						" does not support trash operations");
			}

			FileEntry fileEntry = repository.getFileEntry(classPK);

			dlFileEntry = (DLFileEntry)fileEntry.getModel();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return dlFileEntry;
	}

}