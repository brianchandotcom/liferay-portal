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

package com.liferay.wiki.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.wiki.asset.WikiNodeTrashRenderer;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the wiki node entity.
 *
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
@Component(
	property = {"model.class.name=com.liferay.wiki.model.WikiNode"},
	service = TrashHandler.class
)
public class WikiNodeTrashHandler extends BaseWikiTrashHandler {

	@Override
	public void checkRestorableEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(trashEntry.getClassPK());

		String originalTitle = trashEntry.getTypeSettingsProperty("title");

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		WikiNode duplicateNode = _wikiNodeLocalService.fetchNode(
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
		_wikiNodeLocalService.deleteNode(classPK);
	}

	@Override
	public String getClassName() {
		return WikiNode.class.getName();
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, false);

		WikiNode node = _wikiNodeLocalService.getNode(classPK);

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
		return _wikiPageLocalService.getPagesCount(
			classPK, true, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException {

		List<TrashRenderer> trashRenderers = new ArrayList<>();

		List<WikiPage> pages = _wikiPageLocalService.getPages(
			classPK, true, WorkflowConstants.STATUS_IN_TRASH, start, end);

		for (WikiPage page : pages) {
			if (page.isInTrashExplicitly()) {
				continue;
			}

			WikiPage redirectPage = page.getRedirectPage();

			if ((redirectPage != null) && redirectPage.isInTrash()) {
				continue;
			}

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
		WikiNode node = _wikiNodeLocalService.getNode(classPK);

		return node.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		WikiNode node = _wikiNodeLocalService.getNode(classPK);

		return new WikiNodeTrashRenderer(node);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		WikiNode node = _wikiNodeLocalService.getNode(classPK);

		return node.isInTrash();
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(classPK);

		_wikiNodeLocalService.restoreNodeFromTrash(userId, node);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		WikiNode node = _wikiNodeLocalService.getNode(classPK);

		node.setName(name);

		_wikiNodeLocalService.updateWikiNode(node);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK, boolean containerModel)
		throws PortalException {

		PortletURL portletURL = null;

		WikiNode node = _wikiNodeLocalService.getNode(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			node.getGroupId(), WikiPortletKeys.WIKI);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, WikiPortletKeys.WIKI_ADMIN,
				PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = PortletURLFactoryUtil.create(
				portletRequest, WikiPortletKeys.WIKI, plid,
				PortletRequest.RENDER_PHASE);
		}

		if (!containerModel) {
			portletURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return WikiNodePermissionChecker.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiPageLocalService _wikiPageLocalService;

}