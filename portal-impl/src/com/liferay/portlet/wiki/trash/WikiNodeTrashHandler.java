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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.asset.WikiNodeTrashRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;

/**
 * Implements trash handling for the wiki node entity.
 *
 * @author Eudaldo Alonso
 */
public class WikiNodeTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = WikiNode.class.getName();

	@Override
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(
			trashEntry.getClassPK());

		String restoredTitle = node.getName();

		if (Validator.isNotNull(newName)) {
			restoredTitle = newName;
		}

		String originalTitle = TrashUtil.stripTrashNamespace(restoredTitle);

		WikiNode duplicateNode = WikiNodeLocalServiceUtil.fetchWikiNode(
			node.getGroupId(), originalTitle);

		if (duplicateNode != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicateNode.getNodeId());
			dee.setOldName(duplicateNode.getName());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				WikiNodeServiceUtil.deleteNode(classPK);
			}
			else {
				WikiNodeLocalServiceUtil.deleteNode(classPK);
			}
		}
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		return new WikiNodeTrashRenderer(node);
	}

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		return node.isInTrash();
	}

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			WikiNodeServiceUtil.restoreNodeFromTrash(classPK);
		}
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		node.setName(name);

		WikiNodeLocalServiceUtil.updateWikiNode(node);
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return WikiNodePermission.contains(
			permissionChecker, classPK, actionId);
	}

}