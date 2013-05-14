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
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Implements trash handling for the message boards category entity.
 *
 * @author Eduardo Garcia
 */
public class MBCategoryTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		MBCategoryLocalServiceUtil.deleteCategory(category, false);
	}

	@Override
	public String getClassName() {
		return MBCategory.class.getName();
	}

	@Override
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException {

		return MBCategoryLocalServiceUtil.getCategory(containerModelId);
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

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			category.getGroupId(), parentContainerModelId,
			WorkflowConstants.STATUS_APPROVED, start, end);

		List<ContainerModel> containerModels = new ArrayList<ContainerModel> ();

		for (MBCategory curCategory : categories) {
			containerModels.add(curCategory);
		}

		return containerModels;
	}

	@Override
	public int getContainerModelsCount(
			long classPK, long parentContainerModelId)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return MBCategoryLocalServiceUtil.getCategoriesCount(
			category.getGroupId(), parentContainerModelId,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getDeleteMessage() {
		return "found-in-deleted-category-x";
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long containerModelId)
		throws PortalException, SystemException {

		List<ContainerModel> containerModels = new ArrayList<ContainerModel>();

		ContainerModel containerModel = getContainerModel(containerModelId);

		while (containerModel.getParentContainerModelId() > 0) {
			containerModel = getContainerModel(
				containerModel.getParentContainerModelId());

			if (containerModel == null) {
				break;
			}

			containerModels.add(containerModel);
		}

		return containerModels;
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		String portletId = PortletKeys.MESSAGE_BOARDS;

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			category.getGroupId(), PortletKeys.MESSAGE_BOARDS);

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
			"mbCategoryId", String.valueOf(category.getParentCategoryId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return MBUtil.getAbsolutePath(
			portletRequest, category.getParentCategoryId());
	}

	@Override
	public String getRootContainerModelName() {
		return "home";
	}

	@Override
	public String getTrashContainedModelName() {
		return "threads";
	}

	@Override
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return MBThreadLocalServiceUtil.getThreadsCount(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		List<TrashRenderer> trashRenderers = new ArrayList<TrashRenderer>();

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		List<MBThread> threads = MBThreadLocalServiceUtil.getThreads(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_APPROVED,
			start, end);

		for (MBThread thread : threads) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					MBThread.class.getName());

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				thread.getPrimaryKey());

			trashRenderers.add(trashRenderer);
		}

		return trashRenderers;
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return category.getTrashContainer();
	}

	@Override
	public String getTrashContainerModelName() {
		return "categories";
	}

	@Override
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return MBCategoryLocalServiceUtil.getCategoriesCount(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<TrashRenderer> getTrashContainerModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		List<TrashRenderer> trashRenderers = new ArrayList<TrashRenderer>();

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_APPROVED,
			start, end);

		for (MBCategory curCategory : categories) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					MBCategory.class.getName());

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				curCategory.getPrimaryKey());

			trashRenderers.add(trashRenderer);
		}

		return trashRenderers;
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return new MBCategoryTrashRenderer(category);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return MBCategoryPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_FOLDER);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return category.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return category.isInTrashContainer();
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return !category.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategoryLocalServiceUtil.moveCategory(
			classPK, containerModelId, false);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategoryLocalServiceUtil.moveCategoryFromTrash(
			userId, classPK, containerModelId);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		MBCategoryLocalServiceUtil.restoreCategoryFromTrash(userId, classPK);
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		category.setName(name);

		MBCategoryLocalServiceUtil.updateMBCategory(category);
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(classPK);

		return MBCategoryPermission.contains(
			permissionChecker, category, actionId);
	}

}