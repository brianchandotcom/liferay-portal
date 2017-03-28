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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil;

/**
 * @author Zsolt Berentey
 */
@ProviderType
public interface TrashedModel {

	public static final Log log = LogFactoryUtil.getLog(TrashedModel.class);

	public String getModelClassName();

	public long getPrimaryKey();

	public default int getStatus() {
		return 0;
	}

	public default TrashEntry getTrashEntry() throws PortalException {
		if (!isInTrash()) {
			return null;
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
			getModelClassName(), getTrashEntryClassPK());

		if (trashEntry != null) {
			return trashEntry;
		}

		TrashHandler trashHandler = getTrashHandler();

		if (Validator.isNotNull(
				trashHandler.getContainerModelClassName(getPrimaryKey()))) {

			ContainerModel containerModel = null;

			try {
				containerModel = trashHandler.getParentContainerModel(this);
			}
			catch (NoSuchModelException nsme) {
				if (log.isDebugEnabled()) {
					log.debug("Unable to get parent container model ", nsme);
				}
			}

			while (containerModel != null) {
				if (containerModel instanceof TrashedModel) {
					TrashedModel trashedModel = (TrashedModel)containerModel;

					return trashedModel.getTrashEntry();
				}

				String containerModelClassName =
					trashHandler.getContainerModelClassName(
						containerModel.getContainerModelId());

				trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
					containerModelClassName);

				if (trashHandler == null) {
					return null;
				}

				containerModel = trashHandler.getContainerModel(
					containerModel.getParentContainerModelId());
			}
		}

		return null;
	}

	public default long getTrashEntryClassPK() {
		return getPrimaryKey();
	}

	public default TrashHandler getTrashHandler() {
		return TrashHandlerRegistryUtil.getTrashHandler(getModelClassName());
	}

	public default boolean isInTrash() {
		if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
			return true;
		}

		return false;
	}

	public default boolean isInTrashContainer() {
		TrashHandler trashHandler = getTrashHandler();

		if (trashHandler == null) {
			return false;
		}

		String containerModelClassName =
			trashHandler.getContainerModelClassName(getPrimaryKey());

		if (Validator.isNull(containerModelClassName)) {
			return false;
		}

		ContainerModel containerModel = null;

		try {
			containerModel = trashHandler.getParentContainerModel(this);
		}
		catch (PortalException pe) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to get parent container model ", pe);
			}
		}

		if (containerModel == null) {
			return false;
		}

		if (containerModel instanceof TrashedModel) {
			TrashedModel trashedModel = (TrashedModel)containerModel;

			return trashedModel.isInTrash();
		}

		return false;
	}

	public default boolean isInTrashExplicitly() {
		if (!isInTrash()) {
			return false;
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
			getModelClassName(), getTrashEntryClassPK());

		if (trashEntry != null) {
			return true;
		}

		return false;
	}

	public default boolean isInTrashImplicitly() {
		if (!isInTrash()) {
			return false;
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
			getModelClassName(), getTrashEntryClassPK());

		if (trashEntry != null) {
			return false;
		}

		return true;
	}

}