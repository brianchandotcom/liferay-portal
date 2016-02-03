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

package com.liferay.portal.kernel.trash.bundle.trashhandlerregistryutil;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestTrashHandler implements TrashHandler {

	@Override
	public SystemEvent addDeletionSystemEvent(
		long userId, long groupId, long classPK, String classUuid,
		String referrerClassName) {

		return null;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void checkDuplicateEntry(
		long classPK, long containerModelId, String newName) {
	}

	@Deprecated
	@Override
	public void checkDuplicateTrashEntry(
		TrashEntry trashEntry, long containerModelId, String newName) {
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getContainerModelClassName() {
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

	@Deprecated
	@Override
	public Query getExcludeQuery(SearchContext searchContext) {
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
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
		long classPK, int start, int end) {

		return null;
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
	public List<TrashRenderer> getTrashContainerModelTrashRenderers(
		long classPK, int start, int end) {

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
	public List<TrashRenderer> getTrashModelTrashRenderers(
		long classPK, int start, int end, OrderByComparator<?> obc) {

		return null;
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

	@Override
	public boolean isDeletable() {
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

	@Override
	public boolean isMovable() {
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