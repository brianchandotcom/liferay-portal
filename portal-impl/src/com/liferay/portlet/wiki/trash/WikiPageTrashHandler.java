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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
/*
 * @author Eudaldo Alonso
 */
public class WikiPageTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = WikiPage.class.getName();

	@Override
	public void checkDuplicateTrashEntry(TrashEntry trashEntry, String newName)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			trashEntry.getClassPK());

		String restoredTitle = page.getTitle();

		if (Validator.isNotNull(newName)) {
			restoredTitle = newName;
		}

		String originalTitle = TrashUtil.stripTrashNamespace(restoredTitle);

		WikiPage duplicatePage = WikiPageLocalServiceUtil.fetchPage(
			page.getNodeId(), originalTitle, page.getVersion());

		if (duplicatePage != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicatePage.getPageId());
			dee.setOldName(duplicatePage.getTitle());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	/**
	 * Deletes trash attachments from all the wiki pages from a group that were
	 * deleted after a given date.
	 *
	 * @param  groupId the primary key of the group
	 * @param  date the date from which attachments will be deleted
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteTrashAttachments(Group group, Date date)
		throws PortalException, SystemException {

		long repositoryId = CompanyConstants.SYSTEM;

		String[] fileNames = null;

		try {
			fileNames = DLStoreUtil.getFileNames(
				group.getCompanyId(), repositoryId, "wiki");
		}
		catch (NoSuchDirectoryException nsde) {
			return;
		}

		for (String fileName : fileNames) {
			String fileTitle = StringUtil.extractLast(
				fileName, StringPool.FORWARD_SLASH);

			if (fileTitle.startsWith(TrashUtil.TRASH_ATTACHMENTS_DIR)) {
				String[] attachmentFileNames = DLStoreUtil.getFileNames(
					group.getCompanyId(), repositoryId,
					WikiPageConstants.BASE_ATTACHMENTS_DIR + fileTitle);

				TrashUtil.deleteEntriesAttachments(
					group.getCompanyId(), repositoryId, date,
					attachmentFileNames);
			}
		}
	}

	/**
	 * Deletes all wiki page with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the wiki pages to be deleted
	 * @param  checkPermission whether to check permission before deleting each
	 *         folder
	 * @throws PortalException if any one of the wiki pages could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

			if (checkPermission) {
				WikiPageServiceUtil.deletePage(
					page.getNodeId(), page.getTitle());
			}
			else {
				WikiPageLocalServiceUtil.deletePage(
					page.getNodeId(), page.getTitle());
			}
		}
	}

	/**
	 * Returns the wiki page entity's class name
	 *
	 * @return the wiki page entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		long plid = PortalUtil.getPlidFromPortletId(
			page.getGroupId(), PortletKeys.WIKI);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = PortalUtil.getControlPanelPlid(portletRequest);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.WIKI, plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/wiki/view");
		portletURL.setParameter("nodeName", page.getNode().getName());
		portletURL.setParameter("title", HtmlUtil.unescape(page.getTitle()));

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return page.getTitle();
	}

	/**
	 * Returns the trash renderer associated to the trash entry.
	 *
	 * @param  classPK the primary key of the wiki page
	 * @return the trash renderer associated to the wiki page
	 * @throws PortalException if the wiki page could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return new WikiPageAssetRenderer(page);
	}

	/**
	 * Restores all wiki pages with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the wiki pages to be deleted
	 * @throws PortalException if any one of the wiki pages could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			WikiPageServiceUtil.restorePageFromTrash(classPK);
		}
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		page.setTitle(name);

		WikiPageLocalServiceUtil.updateWikiPage(page, false);

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				page.getResourcePrimKey());

		pageResource.setTitle(name);

		WikiPageResourceLocalServiceUtil.updateWikiPageResource(
			pageResource, false);
	}

}