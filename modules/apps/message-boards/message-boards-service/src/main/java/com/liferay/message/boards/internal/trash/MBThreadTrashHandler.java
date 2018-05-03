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

package com.liferay.message.boards.internal.trash;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.internal.util.MBTrashUtil;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.trash.TrashRendererFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for message boards thread entity.
 *
 * @author Zsolt Berentey
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBThread",
	service = TrashHandler.class
)
public class MBThreadTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		_mbThreadLocalService.deleteThread(classPK);
	}

	@Override
	public String getClassName() {
		return MBThread.class.getName();
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

		List<ContainerModel> containerModels = new ArrayList<>();

		MBThread thread = _mbThreadLocalService.getThread(classPK);

		List<MBCategory> categories = _mbCategoryLocalService.getCategories(
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
		throws PortalException {

		MBThread thread = _mbThreadLocalService.getThread(classPK);

		return _mbCategoryLocalService.getCategoriesCount(
			thread.getGroupId(), parentContainerModelId,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, false);

		MBThread thread = _mbThreadLocalService.getThread(classPK);

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(thread.getCategoryId()));
		portletURL.setParameter(
			"messageId", String.valueOf(thread.getRootMessageId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		MBThread thread = _mbThreadLocalService.getThread(classPK);

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, true);

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(thread.getCategoryId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		MBThread thread = _mbThreadLocalService.getThread(classPK);

		return MBTrashUtil.getAbsolutePath(
			portletRequest, thread.getCategoryId());
	}

	@Override
	public TrashedModel getTrashedModel(long classPK) {
		return _mbThreadLocalService.fetchMBThread(classPK);
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
			return ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, permissionChecker, groupId,
				classPK, ActionKeys.ADD_MESSAGE);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		MBThread thread = _mbThreadLocalService.getThread(classPK);

		if ((thread.getCategoryId() > 0) &&
			(_mbCategoryLocalService.fetchMBCategory(thread.getCategoryId()) ==
				null)) {

			return false;
		}

		return !thread.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_mbThreadLocalService.moveThread(userId, containerModelId, classPK);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_mbThreadLocalService.moveThreadFromTrash(
			userId, containerModelId, classPK);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_mbThreadLocalService.restoreThreadFromTrash(userId, classPK);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK, boolean containerModel)
		throws PortalException {

		PortletURL portletURL = null;

		MBThread thread = _mbThreadLocalService.getThread(classPK);
		String portletId = PortletProviderUtil.getPortletId(
			MBThread.class.getName(), PortletProvider.Action.EDIT);

		long plid = _portal.getPlidFromPortletId(
			thread.getGroupId(), portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletProviderUtil.getPortletId(
				MBThread.class.getName(), PortletProvider.Action.MANAGE);

			portletURL = _portal.getControlPanelPortletURL(
				portletRequest, portletId, PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = PortletURLFactoryUtil.create(
				portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);
		}

		if (containerModel) {
			String mvcRenderCommandName = "/message_boards/view";

			if (thread.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				mvcRenderCommandName = "/message_boards/view_category";
			}

			portletURL.setParameter(
				"mvcRenderCommandName", mvcRenderCommandName);
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view_message");
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		MBThread thread = _mbThreadLocalService.getThread(classPK);

		return _messageModelResourcePermission.contains(
			permissionChecker, thread.getRootMessageId(), actionId);
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
		target = "(model.class.name=com.liferay.message.boards.model.MBThread)",
		unbind = "-"
	)
	protected void setTrashRendererFactory(
		TrashRendererFactory trashRendererFactory) {

		_trashRendererFactory = trashRendererFactory;
	}

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBCategory)"
	)
	private ModelResourcePermission<MBCategory>
		_categoryModelResourcePermission;

	private MBCategoryLocalService _mbCategoryLocalService;
	private MBThreadLocalService _mbThreadLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage> _messageModelResourcePermission;

	@Reference
	private Portal _portal;

	private TrashRendererFactory _trashRendererFactory;

}