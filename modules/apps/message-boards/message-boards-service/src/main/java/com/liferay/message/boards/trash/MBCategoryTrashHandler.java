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

package com.liferay.message.boards.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.trash.TrashRendererFactory;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the message boards category entity.
 *
 * @author Eduardo Garcia
 */
@Component(
	property = {"model.class.name=com.liferay.portlet.messageboards.model.MBCategory"},
	service = TrashHandler.class
)
public class MBCategoryTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		_mbCategoryLocalService.deleteCategory(category, false);
	}

	@Override
	public String getClassName() {
		return MBCategory.class.getName();
	}

	@Override
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException {

		return _mbCategoryLocalService.getCategory(containerModelId);
	}

	@Override
	public String getContainerModelClassName(long classPK) {
		return MBCategory.class.getName();
	}

	@Override
	public String getContainerModelName() {
		return "category";
	}

	@Override
	public List<ContainerModel> getContainerModels(
			long classPK, long parentContainerModelId, int start, int end)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		List<MBCategory> categories = _mbCategoryLocalService.getCategories(
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
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return _mbCategoryLocalService.getCategoriesCount(
			category.getGroupId(), parentContainerModelId,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getDeleteMessage() {
		return "found-in-deleted-category-x";
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long containerModelId)
		throws PortalException {

		List<ContainerModel> containerModels = new ArrayList<>();

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
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		PortletURL portletURL = getRestoreURL(portletRequest, classPK);

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(category.getCategoryId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(portletRequest, classPK);

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(category.getParentCategoryId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return MBUtil.getAbsolutePath(
			portletRequest, category.getParentCategoryId());
	}

	@Override
	public String getRootContainerModelName() {
		return "category";
	}

	@Override
	public String getSubcontainerModelName() {
		return "category";
	}

	@Override
	public String getTrashContainedModelName() {
		return "threads";
	}

	@Override
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return _mbThreadLocalService.getThreadsCount(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException {

		List<TrashRenderer> trashRenderers = new ArrayList<>();

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		List<MBThread> threads = _mbThreadLocalService.getThreads(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH,
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
	public String getTrashContainerModelName() {
		return "categories";
	}

	@Override
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return _mbCategoryLocalService.getCategoriesCount(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<TrashRenderer> getTrashContainerModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException {

		List<TrashRenderer> trashRenderers = new ArrayList<>();

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		List<MBCategory> categories = _mbCategoryLocalService.getCategories(
			category.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH,
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
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return category.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		return _trashRendererFactory.getTrashRenderer(classPK);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return MBCategoryPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_CATEGORY);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return category.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return category.isInTrashContainer();
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		if ((category.getParentCategoryId() > 0) &&
			(_mbCategoryLocalService.fetchMBCategory(
				category.getParentCategoryId()) == null)) {

			return false;
		}

		return !category.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_mbCategoryLocalService.moveCategory(classPK, containerModelId, false);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_mbCategoryLocalService.moveCategoryFromTrash(
			userId, classPK, containerModelId);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_mbCategoryLocalService.restoreCategoryFromTrash(userId, classPK);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		category.setName(name);

		_mbCategoryLocalService.updateMBCategory(category);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = null;

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);
		String portletId = PortletProviderUtil.getPortletId(
			MBCategory.class.getName(), PortletProvider.Action.EDIT);

		long plid = PortalUtil.getPlidFromPortletId(
			category.getGroupId(), portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletProviderUtil.getPortletId(
				MBCategory.class.getName(), PortletProvider.Action.MANAGE);

			portletURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, portletId, PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = PortletURLFactoryUtil.create(
				portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);
		}

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_category");

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(classPK);

		return MBCategoryPermission.contains(
			permissionChecker, category, actionId);
	}

	@Reference(unbind = "-")
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		_mbCategoryLocalService = mbCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portlet.messageboards.model.MBCategory)",
		unbind = "-"
	)
	protected void setTrashRendererFactory(
		TrashRendererFactory trashRendererFactory) {

		_trashRendererFactory = trashRendererFactory;
	}

	private MBCategoryLocalService _mbCategoryLocalService;
	private MBThreadLocalService _mbThreadLocalService;
	private TrashRendererFactory _trashRendererFactory;

}