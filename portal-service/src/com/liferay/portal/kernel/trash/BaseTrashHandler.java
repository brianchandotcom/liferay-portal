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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Provides the base implementation of {@link TrashHandler}.
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 * @see    {@link TrashHandler}
 */
public abstract class BaseTrashHandler implements TrashHandler {

	@Override
	@SuppressWarnings("unused")
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {
	}

	@Override
	@SuppressWarnings("unused")
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException {

		return null;
	}

	@Override
	public String getContainerModelClassName() {
		return StringPool.BLANK;
	}

	@Override
	public String getContainerModelName() {
		return StringPool.BLANK;
	}

	@Override
	@SuppressWarnings("unused")
	public List<ContainerModel> getContainerModels(
			long classPK, long containerModelId, int start, int end)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	@Override
	@SuppressWarnings("unused")
	public int getContainerModelsCount(long classPK, long containerModelId)
		throws PortalException, SystemException {

		return 0;
	}

	@Override
	public String getDeleteMessage() {
		return "deleted-in-x";
	}

	@Override
	@SuppressWarnings("unused")
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		return null;
	}

	@Override
	@SuppressWarnings("unused")
	public List<ContainerModel> getParentContainerModels(long classPK)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	@Override
	@SuppressWarnings("unused")
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		return StringPool.BLANK;
	}

	@Override
	@SuppressWarnings("unused")
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		return StringPool.BLANK;
	}

	@Override
	public String getRootContainerModelName() {
		return StringPool.BLANK;
	}

	@Override
	public String getSubcontainerModelName() {
		return StringPool.BLANK;
	}

	@Override
	public String getTrashContainedModelName() {
		return StringPool.BLANK;
	}

	@Override
	@SuppressWarnings("unused")
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException, SystemException {

		return 0;
	}

	@Override
	@SuppressWarnings("unused")
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	@Override
	@SuppressWarnings("unused")
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		return null;
	}

	@Override
	public String getTrashContainerModelName() {
		return StringPool.BLANK;
	}

	@Override
	@SuppressWarnings("unused")
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException, SystemException {

		return 0;
	}

	@Override
	@SuppressWarnings("unused")
	public List<TrashRenderer> getTrashContainerModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory != null) {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				classPK);

			if (assetRenderer instanceof TrashRenderer) {
				return (TrashRenderer)assetRenderer;
			}
		}

		return null;
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		String actionId = trashActionId;

		if (trashActionId.equals(ActionKeys.DELETE)) {
			actionId = ActionKeys.DELETE;
		}
		else if (trashActionId.equals(TrashActionKeys.OVERWRITE)) {
			actionId = ActionKeys.DELETE;
		}
		else if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return false;
		}
		else if (trashActionId.equals(TrashActionKeys.RENAME)) {
			actionId = ActionKeys.UPDATE;
		}
		else if (trashActionId.equals(TrashActionKeys.RESTORE)) {
			actionId = ActionKeys.DELETE;
		}

		return hasPermission(permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isContainerModel() {
		return false;
	}

	@Override
	public boolean isDeletable() {
		return true;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		return false;
	}

	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		return true;
	}

	@Override
	@SuppressWarnings("unused")
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (isRestorable(classPK)) {
			restoreTrashEntry(userId, classPK);
		}

		_log.error(
			"moveTrashEntry() is not implemented in " + getClass().getName());

		throw new SystemException();
	}

	@Override
	@SuppressWarnings("unused")
	public void restoreRelatedTrashEntry(String className, long classPK)
		throws PortalException, SystemException {
	}

	@Override
	@SuppressWarnings("unused")
	public void updateTitle(long classPK, String title)
		throws PortalException, SystemException {
	}

	protected AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	protected abstract boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException;

	private static Log _log = LogFactoryUtil.getLog(BaseTrashHandler.class);

}