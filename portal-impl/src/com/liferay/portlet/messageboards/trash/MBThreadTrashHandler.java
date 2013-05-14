/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Implements trash handling for message boards thread entity.
 *
 * @author Zsolt Berentey
 */
public class MBThreadTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		MBThreadLocalServiceUtil.deleteThread(classPK);
	}

	@Override
	public String getClassName() {
		return MBThread.class.getName();
	}

	@Override
	public String getContainerModelClassName() {
		return MBCategory.class.getName();
	}

	@Override
	public String getContainerModelName() {
		return "category";
	}

	@Override
	public List<ContainerModel> getContainerModels(
			long classPK, long parentContainerModelId, int start, int end)
		throws PortalException, SystemException {

		List<ContainerModel> containerModels = new ArrayList<ContainerModel>();

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			thread.getGroupId(), parentContainerModelId,
			WorkflowConstants.STATUS_APPROVED, start, end);

		for (MBCategory category : categories) {
			containerModels.add(category);
		}

		return containerModels;
	}

	@Override
	public int getContainerModelsCount(
			long classPK, long parentContainerModelId)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return MBCategoryLocalServiceUtil.getCategoriesCount(
			thread.getGroupId(), parentContainerModelId,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		String portletId = PortletKeys.MESSAGE_BOARDS;

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			thread.getGroupId(), PortletKeys.MESSAGE_BOARDS);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletKeys.MESSAGE_BOARDS_ADMIN;

			plid = PortalUtil.getControlPanelPlid(portletRequest);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		if (portletId.equals(PortletKeys.MESSAGE_BOARDS)) {
			portletURL.setParameter("struts_action", "/message_boards/view");
		}
		else {
			portletURL.setParameter(
				"struts_action", "/message_boards_admin/view");
		}

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(thread.getCategoryId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return MBUtil.getAbsolutePath(portletRequest, thread.getCategoryId());
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return thread.getTrashContainer();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return new MBThreadTrashRenderer(thread);
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return thread.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
			throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return thread.isInTrashContainer();
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBThreadLocalServiceUtil.moveThread(userId, containerModelId, classPK);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBThreadLocalServiceUtil.moveThreadFromTrash(
			userId, containerModelId, classPK);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		MBThreadLocalServiceUtil.restoreThreadFromTrash(userId, classPK);
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return MBMessagePermission.contains(
			permissionChecker, thread.getRootMessageId(), actionId);
	}

}