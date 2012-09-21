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

package com.liferay.portlet.trash.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.Date;
import java.util.List;

/**
 * @author Julio Camarero
 */
public interface Trash {

	public String appendTrashNamespace(String title);

	public String appendTrashNamespace(String title, String separator);

	public void deleteEntriesAttachments(
			long companyId, long repositoryId, Date date,
			String[] attachmentFileNames)
		throws PortalException, SystemException;

	public List<TrashEntry> getEntries(Hits hits)
		throws PortalException, SystemException;

	public OrderByComparator getEntryOrderByComparator(
		String orderByCol, String orderByType);

	public int getMaxAge(Group group) throws PortalException, SystemException;

	public String getTrashTime(String title, String separator);

	public boolean isInTrash(String className, long classPK)
		throws PortalException, SystemException;

	public boolean isTrashEnabled(long groupId)
		throws PortalException, SystemException;

	public void moveAttachmentFromTrash(
			long companyId, long repositoryId, String deletedFileName,
			String attachmentsDir)
		throws PortalException, SystemException;

	public void moveAttachmentFromTrash(
			long companyId, long repositoryId, String deletedFileName,
			String attachmentsDir, String separator)
		throws PortalException, SystemException;

	public String moveAttachmentToTrash(
			long companyId, long repositoryId, String fileName,
			String deletedAttachmentsDir)
		throws PortalException, SystemException;

	public String moveAttachmentToTrash(
			long companyId, long repositoryId, String fileName,
			String deletedAttachmentsDir, String separator)
		throws PortalException, SystemException;

	public String stripTrashNamespace(String title);

	public String stripTrashNamespace(String title, String separator);

}