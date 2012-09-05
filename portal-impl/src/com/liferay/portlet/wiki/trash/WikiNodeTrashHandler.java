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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.wiki.asset.WikiNodeTrashRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
/*
 * @author Eudaldo Alonso
 */
public class WikiNodeTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = WikiNode.class.getName();

	@Override
	public void checkDuplicateTrashEntry(TrashEntry trashEntry, String newName)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(
			trashEntry.getClassPK());

		String restoredTitle = node.getName();

		if (Validator.isNotNull(newName)) {
			restoredTitle = newName;
		}

		String originalTitle = restoredTitle;

		if (restoredTitle.indexOf(StringPool.FORWARD_SLASH) > 0) {
			originalTitle = restoredTitle.substring(
				0, restoredTitle.indexOf(StringPool.FORWARD_SLASH));
		}

		WikiNode duplicatedNode = WikiNodeLocalServiceUtil.fetchWikiNode(
			node.getGroupId(), originalTitle);

		if (duplicatedNode != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicatedNode.getNodeId());
			dee.setOldName(duplicatedNode.getName());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	/**
	 * Deletes all wiki nodes with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the wiki nodes to be deleted
	 * @param  checkPermission whether to check permission before deleting each
	 *         folder
	 * @throws PortalException if any one of the wiki nodes could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Returns the wiki node entity's class name
	 *
	 * @return the wiki node entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	/**
	 * Returns the trash renderer associated to the trash entry.
	 *
	 * @param  classPK the primary key of the wiki node
	 * @return the trash renderer associated to the wiki node
	 * @throws PortalException if the wiki node could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		return new WikiNodeTrashRenderer(node);
	}

	/**
	 * Restores all wiki nodes with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the wiki nodes to be deleted
	 * @throws PortalException if any one of the wiki nodes could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

		WikiNodeLocalServiceUtil.updateWikiNode(node, false);
	}

}