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
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.model.TrashEntryConstants;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.wiki.asset.WikiPageAssetRenderer;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageResourceLocalService;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.util.WikiPageAttachmentsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the wiki page entity.
 *
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
@Component(
	property = {"model.class.name=com.liferay.wiki.model.WikiPage"},
	service = TrashHandler.class
)
public class WikiPageTrashHandler extends BaseWikiTrashHandler {

	@Override
	public SystemEvent addDeletionSystemEvent(
			long userId, long groupId, long classPK, String classUuid,
			String referrerClassName)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		return super.addDeletionSystemEvent(
			userId, groupId, page.getPageId(), classUuid, referrerClassName);
	}

	@Override
	public void checkRestorableEntry(
			long classPK, long containerModelId, String newName)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		checkRestorableEntry(
			classPK, 0, containerModelId, page.getTitle(), newName);
	}

	@Override
	public void checkRestorableEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException {

		checkRestorableEntry(
			trashEntry.getClassPK(), trashEntry.getEntryId(), containerModelId,
			trashEntry.getTypeSettingsProperty("title"), newName);
	}

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		_wikiPageLocalService.deletePage(page);
	}

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		return getParentContainerModel(page);
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel) {
		WikiPage page = (WikiPage)trashedModel;

		if (Validator.isNotNull(page.getParentTitle())) {
			try {
				WikiPage parentPage = page.getParentPage();

				while (parentPage.isInTrashImplicitly()) {
					parentPage = parentPage.getParentPage();
				}

				if (parentPage.isInTrashExplicitly()) {
					return parentPage;
				}
			}
			catch (Exception e) {
			}
		}

		return page.getNode();
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long classPK)
		throws PortalException {

		List<ContainerModel> containerModels = new ArrayList<>();

		containerModels.add(getParentContainerModel(classPK));

		return containerModels;
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, false);

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		WikiNode node = page.getNode();

		portletURL.setParameter("nodeName", node.getName());
		portletURL.setParameter("title", HtmlUtil.unescape(page.getTitle()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		WikiNode node = page.getNode();

		PortletURL portletURL = getRestoreURL(portletRequest, classPK, true);

		portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		WikiNode node = page.getNode();

		return node.getName();
	}

	@Override
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getPage(classPK);

		return _wikiPageLocalService.getChildrenCount(
			page.getNodeId(), true, page.getTitle(),
			WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public String getTrashContainerModelName() {
		return "children-pages";
	}

	@Override
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getPage(classPK);

		return _wikiPageLocalService.getChildrenCount(
			page.getNodeId(), true, page.getTitle(),
			WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<TrashRenderer> getTrashContainerModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException {

		List<TrashRenderer> trashRenderers = new ArrayList<>();

		WikiPage page = _wikiPageLocalService.getPage(classPK);

		List<WikiPage> pages = _wikiPageLocalService.getChildren(
			page.getNodeId(), true, page.getTitle(),
			WorkflowConstants.STATUS_IN_TRASH);

		for (WikiPage curPage : pages) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					WikiPage.class.getName());

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				curPage.getResourcePrimKey());

			trashRenderers.add(trashRenderer);
		}

		return trashRenderers;
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		return page.getTrashEntry();
	}

	@Override
	public List<TrashRenderer> getTrashModelTrashRenderers(
			long classPK, int start, int end, OrderByComparator obc)
		throws PortalException {

		List<TrashRenderer> trashRenderers = new ArrayList<>();

		WikiPage page = _wikiPageLocalService.getPage(classPK);

		List<WikiPage> pages = _wikiPageLocalService.getChildren(
			page.getNodeId(), true, page.getTitle(),
			WorkflowConstants.STATUS_IN_TRASH, start, end, obc);

		for (WikiPage curPage : pages) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					WikiPage.class.getName());

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				curPage.getResourcePrimKey());

			trashRenderers.add(trashRenderer);
		}

		return trashRenderers;
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		return new WikiPageAssetRenderer(page);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			WikiPage page = _wikiPageLocalService.fetchLatestPage(
				classPK, WorkflowConstants.STATUS_ANY, true);

			if (page != null) {
				WikiPagePermissionChecker.check(
					permissionChecker, page.getNodeId(), page.getTitle(),
					ActionKeys.DELETE);

				classPK = page.getNodeId();
			}

			return WikiNodePermissionChecker.contains(
				permissionChecker, classPK, ActionKeys.ADD_PAGE);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		return page.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		return page.isInTrashContainer();
	}

	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	public void restoreRelatedTrashEntry(String className, long classPK)
		throws PortalException {

		if (!className.equals(DLFileEntry.class.getName())) {
			return;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			classPK);

		WikiPage page = WikiPageAttachmentsUtil.getPage(classPK);

		_wikiPageService.restorePageAttachmentFromTrash(
			page.getNodeId(), page.getTitle(), fileEntry.getTitle());
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		_wikiPageLocalService.restorePageFromTrash(userId, page);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		page.setTitle(name);

		_wikiPageLocalService.updateWikiPage(page);

		WikiPageResource pageResource =
			_wikiPageResourceLocalService.getPageResource(
				page.getResourcePrimKey());

		pageResource.setTitle(name);

		_wikiPageResourceLocalService.updateWikiPageResource(pageResource);
	}

	protected void checkRestorableEntry(
			long classPK, long trashEntryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = page.getNodeId();
		}

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		WikiPageResource duplicatePageResource =
			_wikiPageResourceLocalService.fetchPageResource(
				containerModelId, originalTitle);

		if (duplicatePageResource != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			WikiPage duplicatePage = _wikiPageLocalService.getLatestPage(
				duplicatePageResource.getResourcePrimKey(),
				WorkflowConstants.STATUS_ANY, false);

			ree.setDuplicateEntryId(duplicatePage.getResourcePrimKey());
			ree.setOldName(duplicatePage.getTitle());
			ree.setTrashEntryId(trashEntryId);

			throw ree;
		}
		else {
			List<WikiPage> pages = _wikiPageLocalService.getDependentPages(
				page.getNodeId(), true, page.getTitle(),
				WorkflowConstants.STATUS_IN_TRASH);

			for (WikiPage curPage : pages) {
				checkRestorableEntry(
					curPage.getResourcePrimKey(), 0, containerModelId,
					curPage.getTitle(),
					TrashUtil.getOriginalTitle(curPage.getTitle()));
			}
		}
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK, boolean containerModel)
		throws PortalException {

		PortletURL portletURL = null;

		WikiPage page = _wikiPageLocalService.getLatestPage(
			classPK, WorkflowConstants.STATUS_ANY, false);

		long plid = PortalUtil.getPlidFromPortletId(
			page.getGroupId(), WikiPortletKeys.WIKI);

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

		if (containerModel) {
			portletURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");
		}
		else {
			portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return WikiPagePermissionChecker.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {

		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private WikiPageLocalService _wikiPageLocalService;
	private WikiPageResourceLocalService _wikiPageResourceLocalService;
	private WikiPageService _wikiPageService;

}