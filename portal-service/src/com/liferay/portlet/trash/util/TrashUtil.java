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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class TrashUtil {

	public static final String TRASH_ATTACHMENTS_DIR = ".trashed_";

	public static final int TRASH_DEFAULT_VALUE = -1;

	public static final int TRASH_DISABLED = 0;

	public static final int TRASH_DISABLED_BY_DEFAULT = 1;

	public static final int TRASH_ENABLED = 3;

	public static final int TRASH_ENABLED_BY_DEFAULT = 2;

	public static final String TRASH_TIME_SEPARATOR = "_TRASH_TIME_";

	public static void addContainerModelBreadcrumbEntries(
			HttpServletRequest request, TrashHandler trashHandler,
			ContainerModel containerModel, PortletURL containerModelURL)
		throws PortalException, SystemException {

		getTrash().addContainerModelBreadcrumbEntries(
			request, trashHandler, containerModel, containerModelURL);
	}

	public static String appendTrashNamespace(String title) {
		return getTrash().appendTrashNamespace(title);
	}

	public static String appendTrashNamespace(String title, String separator) {
		return getTrash().appendTrashNamespace(title, separator);
	}

	public static void deleteEntriesAttachments(
			long companyId, long repositoryId, Date date,
			String[] attachmentFileNames)
		throws PortalException, SystemException {

		getTrash().deleteEntriesAttachments(
			companyId, repositoryId, date, attachmentFileNames);
	}

	public static List<TrashEntry> getEntries(Hits hits)
		throws PortalException, SystemException {

		return getTrash().getEntries(hits);
	}

	public static OrderByComparator getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		return getTrash().getEntryOrderByComparator(orderByCol, orderByType);
	}

	public static int getMaxAge(Group group)
		throws PortalException, SystemException {

		return getTrash().getMaxAge(group);
	}

	public static String getNewName(ThemeDisplay themeDisplay, String oldName) {
		return getTrash().getNewName(themeDisplay, oldName);
	}

	public static Trash getTrash() {
		PortalRuntimePermission.checkGetBeanProperty(TrashUtil.class);

		return _trash;
	}

	public static String getTrashTime(String title, String separator) {
		return getTrash().getTrashTime(title, separator);
	}

	public static boolean isInTrash(String className, long classPK)
		throws PortalException, SystemException {

		return getTrash().isInTrash(className, classPK);
	}

	public static boolean isTrashEnabled(long groupId)
		throws PortalException, SystemException {

		return getTrash().isTrashEnabled(groupId);
	}

	public static String stripTrashNamespace(String title) {
		return getTrash().stripTrashNamespace(title);
	}

	public static String stripTrashNamespace(String title, String separator) {
		return getTrash().stripTrashNamespace(title, separator);
	}

	public void setTrash(Trash trash) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_trash = trash;
	}

	private static Trash _trash;

}