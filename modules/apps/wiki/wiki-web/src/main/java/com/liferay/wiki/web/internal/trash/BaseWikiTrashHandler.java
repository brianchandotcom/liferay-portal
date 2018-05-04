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

package com.liferay.wiki.web.internal.trash;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roberto Díaz
 */
public abstract class BaseWikiTrashHandler extends BaseTrashHandler {

	@Override
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.fetchPage(containerModelId);

		if (page == null) {
			return WikiNodeLocalServiceUtil.getNode(containerModelId);
		}

		return page;
	}

	@Override
	public String getContainerModelClassName(long classPK) {
		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(classPK);
		}
		catch (Exception e) {
			page = WikiPageLocalServiceUtil.fetchWikiPage(classPK);
		}

		try {
			WikiPage parentPage = page.getParentPage();

			while (parentPage != null) {
				if (parentPage.isInTrashExplicitly()) {
					return WikiPage.class.getName();
				}

				parentPage = parentPage.getParentPage();
			}
		}
		catch (Exception e) {
		}

		return WikiNode.class.getName();
	}

	@Override
	public String getContainerModelName() {
		return "wiki-node";
	}

	@Override
	public List<ContainerModel> getContainerModels(
			long classPK, long containerModelId, int start, int end)
		throws PortalException {

		List<ContainerModel> containerModels = new ArrayList<>();

		WikiPage page = null;

		String parentTitle = StringPool.BLANK;

		if (containerModelId > 0) {
			page = WikiPageLocalServiceUtil.getPage(containerModelId);

			if (page == null) {
				List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
					containerModelId, start, end);

				for (WikiPage curPage : pages) {
					containerModels.add(curPage);
				}

				return containerModels;
			}

			parentTitle = page.getTitle();
		}
		else {
			page = WikiPageLocalServiceUtil.getPage(classPK);
		}

		List<WikiPage> pages = WikiPageLocalServiceUtil.getChildren(
			page.getNodeId(), true, parentTitle, start, end);

		for (WikiPage curPage : pages) {
			containerModels.add(curPage);
		}

		return containerModels;
	}

	@Override
	public int getContainerModelsCount(long classPK, long containerModelId)
		throws PortalException {

		WikiPage page = null;

		String parentTitle = StringPool.BLANK;

		if (containerModelId > 0) {
			page = WikiPageLocalServiceUtil.fetchPage(containerModelId);

			if (page == null) {
				return WikiPageLocalServiceUtil.getPagesCount(containerModelId);
			}

			parentTitle = page.getTitle();
		}
		else {
			page = WikiPageLocalServiceUtil.getPage(classPK);
		}

		return WikiPageLocalServiceUtil.getChildrenCount(
			page.getNodeId(), true, parentTitle);
	}

	@Override
	public long getDestinationContainerModelId(
		long classPK, long destinationContainerModelId) {

		if (destinationContainerModelId == 0) {
			WikiPage page = WikiPageLocalServiceUtil.fetchPage(classPK);

			if (page != null) {
				return page.getNodeId();
			}
		}

		return destinationContainerModelId;
	}

	@Override
	public String getSubcontainerModelName() {
		return "wiki-page";
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		moveTrashEntry(userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		WikiPage parentPage = WikiPageLocalServiceUtil.fetchPage(
			containerModelId);

		if (parentPage == null) {
			WikiPageLocalServiceUtil.movePageFromTrash(
				userId, page.getNodeId(), page.getTitle(), containerModelId,
				StringPool.BLANK);

			return;
		}

		WikiPageLocalServiceUtil.movePageFromTrash(
			userId, page.getNodeId(), page.getTitle(), parentPage.getNodeId(),
			parentPage.getTitle());
	}

}