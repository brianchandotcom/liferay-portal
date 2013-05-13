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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.TrashEntryConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;
import com.liferay.portlet.wiki.util.WikiPageAttachmentsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Implements trash handling for the wiki page entity.
 *
 * @author Eudaldo Alonso
 */
public class WikiPageTrashHandler extends BaseTrashHandler {

	@Override
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			trashEntry.getClassPK());

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = page.getNodeId();
		}

		String originalTitle = trashEntry.getTypeSettingsProperty("title");

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.fetchPageResource(
				containerModelId, originalTitle);

		if (pageResource != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			WikiPage duplicatePage = WikiPageLocalServiceUtil.getPage(
				pageResource.getResourcePrimKey());

			dee.setDuplicateEntryId(duplicatePage.getResourcePrimKey());
			dee.setOldName(duplicatePage.getTitle());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		WikiPageLocalServiceUtil.deletePage(page.getNodeId(), page.getTitle());
	}

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public String getContainerModelClassName() {
		return WikiNode.class.getName();
	}

	@Override
	public String getContainerModelName() {
		return "wiki-node";
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return page.getNode();
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long classPK)
		throws PortalException, SystemException {

		List<ContainerModel> containerModels = new ArrayList<ContainerModel>();

		containerModels.add(getParentContainerModel(classPK));

		return containerModels;
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		String portletId = PortletKeys.WIKI;

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			page.getGroupId(), PortletKeys.WIKI);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletKeys.WIKI_ADMIN;

			plid = PortalUtil.getControlPanelPlid(portletRequest);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		WikiNode node = page.getNode();

		if (portletId.equals(PortletKeys.WIKI)) {
			portletURL.setParameter("struts_action", "/wiki/view");
		}
		else {
			portletURL.setParameter("struts_action", "/wiki_admin/view");
		}

		portletURL.setParameter("nodeName", node.getName());
		portletURL.setParameter("title", HtmlUtil.unescape(page.getTitle()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return page.getTitle();
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return page.getTrashContainer();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return new WikiPageAssetRenderer(page);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			WikiNodePermission.check(
				permissionChecker, classPK, ActionKeys.ADD_PAGE);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return page.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return page.isInTrashContainer();
	}

	@Override
	public void restoreRelatedTrashEntry(String className, long classPK)
		throws PortalException, SystemException {

		if (!className.equals(DLFileEntry.class.getName())) {
			return;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			classPK);

		WikiPage page = WikiPageAttachmentsUtil.getPage(classPK);

		WikiPageServiceUtil.restorePageAttachmentFromTrash(
			page.getNodeId(), page.getTitle(), fileEntry.getTitle());

	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		WikiPageLocalServiceUtil.restorePageFromTrash(userId, page);
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		page.setTitle(name);

		WikiPageLocalServiceUtil.updateWikiPage(page);

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				page.getResourcePrimKey());

		pageResource.setTitle(name);

		WikiPageResourceLocalServiceUtil.updateWikiPageResource(pageResource);
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return WikiPagePermission.contains(
			permissionChecker, classPK, actionId);
	}

}