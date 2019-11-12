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

package com.liferay.trash.service.test.trashhandlerresgistryutil;

import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = TrashHandler.class
)
public class TestTrashHandler implements TrashHandler {

	@Override
	public SystemEvent addDeletionSystemEvent(
		long userId, long groupId, long classPK, String classUuid,
		String referrerClassName) {

		return null;
	}

	@Override
	public void checkRestorableEntry(
		long classPK, long containerModelId, String newName) {
	}

	@Override
	public void checkRestorableEntry(
		TrashEntry trashEntry, long containerModelId, String newName) {
	}

	@Override
	public void deleteTrashEntry(long classPK) {
	}

	@Override
	public String getClassName() {
		return TestTrashHandler.class.getName();
	}

	@Override
	public ContainerModel getContainerModel(long containerModelId) {
		return null;
	}

	@Override
	public String getContainerModelClassName(long classPK) {
		return null;
	}

	@Override
	public String getContainerModelName() {
		return null;
	}

	@Override
	public List<ContainerModel> getContainerModels(
		long classPK, long containerModelId, int start, int end) {

		return null;
	}

	@Override
	public int getContainerModelsCount(long classPK, long containerModelId) {
		return 0;
	}

	@Override
	public String getDeleteMessage() {
		return null;
	}

	@Override
	public long getDestinationContainerModelId(
		long classPK, long destinationContainerModelId) {

		return 0;
	}

	@Override
	public Filter getExcludeFilter(SearchContext searchContext) {
		return null;
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK) {
		return null;
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel) {
		return null;
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long classPK) {
		return null;
	}

	@Override
	public String getRestoreContainedModelLink(
		PortletRequest portletRequest, long classPK) {

		return null;
	}

	@Override
	public String getRestoreContainerModelLink(
		PortletRequest portletRequest, long classPK) {

		return null;
	}

	@Override
	public String getRestoreMessage(
		PortletRequest portletRequest, long classPK) {

		return null;
	}

	@Override
	public String getRootContainerModelName() {
		return null;
	}

	@Override
	public String getSubcontainerModelName() {
		return null;
	}

	@Override
	public String getSystemEventClassName() {
		return null;
	}

	@Override
	public String getTrashContainedModelName() {
		return null;
	}

	@Override
	public int getTrashContainedModelsCount(long classPK) {
		return 0;
	}

	@Override
	public String getTrashContainerModelName() {
		return null;
	}

	@Override
	public int getTrashContainerModelsCount(long classPK) {
		return 0;
	}

	@Override
	public TrashedModel getTrashedModel(long classPK) {
		return null;
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) {
		return null;
	}

	@Override
	public int getTrashModelsCount(long classPK) {
		return 0;
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) {
		return null;
	}

	@Override
	public boolean hasTrashPermission(
		PermissionChecker permissionChecker, long groupId, long classPK,
		String trashActionId) {

		return false;
	}

	@Override
	public boolean isContainerModel() {
		return false;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #isDeletable(long)}
	 */
	@Deprecated
	@Override
	public boolean isDeletable() {
		return false;
	}

	@Override
	public boolean isDeletable(long classPK) {
		return false;
	}

	@Override
	public boolean isInTrash(long classPK) {
		return false;
	}

	@Override
	public boolean isInTrashContainer(long classPK) {
		return false;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #isMovable(long)}
	 */
	@Deprecated
	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	public boolean isMovable(long classPK) {
		return false;
	}

	@Override
	public boolean isRestorable(long classPK) {
		return false;
	}

	@Override
	public void moveEntry(
		long userId, long classPK, long containerModelId,
		ServiceContext serviceContext) {
	}

	@Override
	public void moveTrashEntry(
		long userId, long classPK, long containerModelId,
		ServiceContext serviceContext) {
	}

	@Override
	public void restoreRelatedTrashEntry(String className, long classPK) {
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK) {
	}

	@Override
	public void updateTitle(long classPK, String title) {
	}

}