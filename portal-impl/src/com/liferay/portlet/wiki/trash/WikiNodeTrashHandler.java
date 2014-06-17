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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.trash.RestoreEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.wiki.asset.WikiNodeTrashRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Implements trash handling for the wiki node entity.
 *
 * @author Eudaldo Alonso
 */
public class WikiNodeTrashHandler extends BaseTrashHandler {

	@Override
	public void checkRestorableEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(
			trashEntry.getClassPK());

		String originalTitle = trashEntry.getTypeSettingsProperty("title");

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		WikiNode duplicateNode = WikiNodeLocalServiceUtil.fetchNode(
			node.getGroupId(), originalTitle);

		if (duplicateNode != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			ree.setDuplicateEntryId(duplicateNode.getNodeId());
			ree.setOldName(duplicateNode.getName());
			ree.setTrashEntryId(trashEntry.getEntryId());

			throw ree;
		}
	}

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		WikiNodeLocalServiceUtil.deleteNode(classPK);
	}

	@Override
	public String getClassName() {
		return WikiNode.class.getName();
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, false);

		portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, true);

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(
		PortletRequest portletRequest, long classPK) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.translate("wiki");
	}

	@Override
	public String getTrashContainedModelName() {
		return "wiki-pages";
	}

	@Override
	public int getTrashContainedModelsCount(long classPK) {
		return WikiPageLocalServiceUtil.getPagesCount(
			classPK, true, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException {

		List<TrashRenderer> trashRenderers = new ArrayList<TrashRenderer>();

		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			classPK, true, WorkflowConstants.STATUS_IN_TRASH, start, end);

		for (WikiPage page : pages) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					WikiPage.class.getName());

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				page.getResourcePrimKey());

			trashRenderers.add(trashRenderer);
		}

		return trashRenderers;
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		return node.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		return new WikiNodeTrashRenderer(node);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		return node.isInTrash();
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		WikiNodeLocalServiceUtil.restoreNodeFromTrash(userId, node);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		node.setName(name);

		WikiNodeLocalServiceUtil.updateWikiNode(node);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK,
			boolean isContainerModel)
		throws PortalException {

		String portletId = PortletKeys.WIKI;

		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			node.getGroupId(), PortletKeys.WIKI);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletKeys.WIKI_ADMIN;

			plid = PortalUtil.getControlPanelPlid(portletRequest);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		if (!isContainerModel) {
			if (portletId.equals(PortletKeys.WIKI)) {
				portletURL.setParameter(
					"struts_action", "/wiki/view_all_pages");
			}
			else {
				portletURL.setParameter(
					"struts_action", "/wiki_admin/view_all_pages");
			}
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return WikiNodePermission.contains(
			permissionChecker, classPK, actionId);
	}

}