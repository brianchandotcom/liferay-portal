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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
@ProviderType
public class TrashUtil {

	public static void addBaseModelBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException {

		getTrash().addBaseModelBreadcrumbEntries(
			request, liferayPortletResponse, className, classPK,
			containerModelURL);
	}

	public static void addContainerModelBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException {

		getTrash().addContainerModelBreadcrumbEntries(
			request, liferayPortletResponse, className, classPK,
			containerModelURL);
	}

	public static void addTrashSessionMessages(
		ActionRequest actionRequest, List<TrashedModel> trashedModels) {

		getTrash().addTrashSessionMessages(actionRequest, trashedModels);
	}

	public static void addTrashSessionMessages(
		ActionRequest actionRequest, List<TrashedModel> trashedModels,
		String cmd) {

		getTrash().addTrashSessionMessages(actionRequest, trashedModels, cmd);
	}

	public static void addTrashSessionMessages(
		ActionRequest actionRequest, TrashedModel trashedModel) {

		getTrash().addTrashSessionMessages(actionRequest, trashedModel);
	}

	public static void addTrashSessionMessages(
		ActionRequest actionRequest, TrashedModel trashedModel, String cmd) {

		getTrash().addTrashSessionMessages(actionRequest, trashedModel, cmd);
	}

	public static void deleteEntriesAttachments(
		long companyId, long repositoryId, Date date,
		String[] attachmentFileNames) {

		getTrash().deleteEntriesAttachments(
			companyId, repositoryId, date, attachmentFileNames);
	}

	public static Group disableTrash(Group group) {
		return getTrash().disableTrash(group);
	}

	public static List<TrashEntry> getEntries(Hits hits)
		throws PortalException {

		return getTrash().getEntries(hits);
	}

	public static OrderByComparator<TrashEntry> getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		return getTrash().getEntryOrderByComparator(orderByCol, orderByType);
	}

	public static int getMaxAge(Group group) throws PortalException {
		return getTrash().getMaxAge(group);
	}

	public static String getNewName(String oldName, String token) {
		return getTrash().getNewName(oldName, token);
	}

	public static String getNewName(
			ThemeDisplay themeDisplay, String className, long classPK,
			String oldName)
		throws PortalException {

		return getTrash().getNewName(themeDisplay, className, classPK, oldName);
	}

	public static String getOriginalTitle(String title) {
		return getTrash().getOriginalTitle(title);
	}

	public static String getOriginalTitle(String title, String paramName) {
		return getTrash().getOriginalTitle(title, paramName);
	}

	public static Trash getTrash() {
		PortalRuntimePermission.checkGetBeanProperty(TrashUtil.class);

		return _trash;
	}

	public static String getTrashTime(String title, String separator) {
		return getTrash().getTrashTime(title, separator);
	}

	public static String getTrashTitle(long trashEntryId) {
		return getTrash().getTrashTitle(trashEntryId);
	}

	public static PortletURL getViewContentURL(
			HttpServletRequest request, long trashEntryId)
		throws PortalException {

		return getTrash().getViewContentURL(request, trashEntryId);
	}

	public static PortletURL getViewContentURL(
			HttpServletRequest request, String className, long classPK)
		throws PortalException {

		return getTrash().getViewContentURL(request, className, classPK);
	}

	public static PortletURL getViewURL(HttpServletRequest request)
		throws PortalException {

		return getTrash().getViewURL(request);
	}

	public static boolean isInTrash(String className, long classPK)
		throws PortalException {

		return getTrash().isInTrash(className, classPK);
	}

	public static boolean isTrashEnabled(Group group) {
		return getTrash().isTrashEnabled(group);
	}

	public static boolean isTrashEnabled(long groupId) throws PortalException {
		return getTrash().isTrashEnabled(groupId);
	}

	public static boolean isValidTrashTitle(String title) {
		return getTrash().isValidTrashTitle(title);
	}

	public void setTrash(Trash trash) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_trash = trash;
	}

	private static Trash _trash;

}