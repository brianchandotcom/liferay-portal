/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.asset.MBMessageAssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

/**
 * Implements trash handling for message boards message entity.
 *
 * @author Zsolt Berentey
 */
public class MBMessageTrashHandler extends BaseTrashHandler {

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission) {
	}

	public String getClassName() {
		return MBMessageAssetRendererFactory.CLASS_NAME;
	}

	@Override
	public boolean isDeletable() {
		return false;
	}

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		MBMessage message = MBMessageLocalServiceUtil.getMBMessage(classPK);

		if (message.isInTrash() || message.isInTrashThread()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		MBMessage message = MBMessageLocalServiceUtil.getMBMessage(classPK);

		return message.isInTrashThread();
	}

	public void restoreTrashEntries(long[] classPKs) {
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return MBMessagePermission.contains(
			permissionChecker, classPK, actionId);
	}

}