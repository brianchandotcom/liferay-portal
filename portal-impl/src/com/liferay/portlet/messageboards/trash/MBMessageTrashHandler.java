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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.util.MBMessageAttachmentsUtil;

/**
 * Implements trash handling for message boards message entity.
 *
 * @author Zsolt Berentey
 */
public class MBMessageTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) {
	}

	@Override
	public String getClassName() {
		return MBMessage.class.getName();
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		MBMessage message = MBMessageLocalServiceUtil.getMBMessage(classPK);

		return message.getTrashContainer();
	}

	@Override
	public boolean isDeletable() {
		return false;
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		MBMessage message = MBMessageLocalServiceUtil.getMBMessage(classPK);

		return message.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		MBMessage message = MBMessageLocalServiceUtil.getMBMessage(classPK);

		return message.isInTrashThread();
	}

	@Override
	public void restoreRelatedTrashEntry(String className, long classPK)
		throws PortalException, SystemException {

		if (!className.equals(DLFileEntry.class.getName())) {
			return;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			classPK);

		MBMessage message = MBMessageAttachmentsUtil.getMessage(classPK);

		MBMessageServiceUtil.restoreMessageAttachmentFromTrash(
			message.getMessageId(), fileEntry.getTitle());
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK) {
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return MBMessagePermission.contains(
			permissionChecker, classPK, actionId);
	}

}