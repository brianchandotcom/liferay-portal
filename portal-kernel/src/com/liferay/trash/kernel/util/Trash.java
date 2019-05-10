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

package com.liferay.trash.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author     Julio Camarero
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
@ProviderType
public interface Trash {

	public static final String TRASH_TIME_SEPARATOR = "_TRASH_TIME_";

	public void addBaseModelBreadcrumbEntries(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException;

	public void addContainerModelBreadcrumbEntries(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException;

	public void addTrashSessionMessages(
		ActionRequest actionRequest, List<TrashedModel> trashedModels);

	public void addTrashSessionMessages(
		ActionRequest actionRequest, List<TrashedModel> trashedModels,
		String cmd);

	public void addTrashSessionMessages(
		ActionRequest actionRequest, TrashedModel trashedModel);

	public void addTrashSessionMessages(
		ActionRequest actionRequest, TrashedModel trashedModel, String cmd);

	public void deleteEntriesAttachments(
		long companyId, long repositoryId, Date date,
		String[] attachmentFileNames);

	public Group disableTrash(Group group);

	public List<TrashEntry> getEntries(Hits hits) throws PortalException;

	public OrderByComparator<TrashEntry> getEntryOrderByComparator(
		String orderByCol, String orderByType);

	public int getMaxAge(Group group) throws PortalException;

	public String getNewName(String oldName, String token);

	public String getNewName(
			ThemeDisplay themeDisplay, String className, long classPK,
			String oldName)
		throws PortalException;

	public String getOriginalTitle(String title);

	public String getOriginalTitle(String title, String paramName);

	public String getTrashTime(String title, String separator);

	public String getTrashTitle(long trashEntryId);

	public PortletURL getViewContentURL(
			HttpServletRequest httpServletRequest, long trashEntryId)
		throws PortalException;

	public PortletURL getViewContentURL(
			HttpServletRequest httpServletRequest, String className,
			long classPK)
		throws PortalException;

	public PortletURL getViewURL(HttpServletRequest httpServletRequest)
		throws PortalException;

	public boolean isInTrash(String className, long classPK)
		throws PortalException;

	public boolean isTrashEnabled(Group group);

	public boolean isTrashEnabled(long groupId) throws PortalException;

	public boolean isValidTrashTitle(String title);

}