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

package com.liferay.document.library.internal.atom;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.comparator.FolderNameComparator;
import com.liferay.portal.atom.AtomPager;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseAtomCollectionAdapter;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Spasic
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.Folder",
	service = AtomCollectionAdapter.class
)
public class FolderAtomCollectionAdapter
	extends BaseAtomCollectionAdapter<Folder> {

	@Override
	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	@Override
	public List<String> getEntryAuthors(Folder folder) {
		return ListUtil.fromArray(folder.getUserName());
	}

	@Override
	public AtomEntryContent getEntryContent(
		Folder folder, AtomRequestContext atomRequestContext) {

		AtomEntryContent atomEntryContent = new AtomEntryContent(
			AtomEntryContent.Type.XML);

		String srcLink = AtomUtil.createCollectionLink(
			atomRequestContext, FileEntryAtomCollectionAdapter.COLLECTION_NAME);

		srcLink += "?folderId=" + folder.getFolderId();

		atomEntryContent.setSrcLink(srcLink);

		return atomEntryContent;
	}

	@Override
	public String getEntryId(Folder folder) {
		return String.valueOf(folder.getPrimaryKey());
	}

	@Override
	public String getEntrySummary(Folder folder) {
		return folder.getDescription();
	}

	@Override
	public String getEntryTitle(Folder folder) {
		return folder.getName();
	}

	@Override
	public Date getEntryUpdated(Folder folder) {
		return folder.getModifiedDate();
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		String portletId = PortletProviderUtil.getPortletId(
			Folder.class.getName(), PortletProvider.Action.VIEW);

		String feedTitleFromPortletName =
			AtomUtil.createFeedTitleFromPortletName(
				atomRequestContext, portletId);

		return feedTitleFromPortletName + " folders";
	}

	@Override
	protected void doDeleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long folderEntryId = GetterUtil.getLong(resourceName);

		_dlAppService.deleteFolder(folderEntryId);
	}

	@Override
	protected Folder doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long folderEntryId = GetterUtil.getLong(resourceName);

		return _dlAppService.getFolder(folderEntryId);
	}

	@Override
	protected Iterable<Folder> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long repositoryId = 0L;

		long parentFolderId = atomRequestContext.getLongParameter(
			"parentFolderId");

		if (parentFolderId != 0) {
			Folder parentFolder = _dlAppService.getFolder(parentFolderId);

			repositoryId = parentFolder.getRepositoryId();
		}
		else {
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		int count = _dlAppService.getFoldersCount(repositoryId, parentFolderId);

		AtomPager atomPager = new AtomPager(atomRequestContext, count);

		AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

		return _dlAppService.getFolders(
			repositoryId, parentFolderId, atomPager.getStart(),
			atomPager.getEnd() + 1, new FolderNameComparator());
	}

	@Override
	protected Folder doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long repositoryId = 0L;

		long parentFolderId = atomRequestContext.getLongParameter(
			"parentFolderId");

		if (parentFolderId != 0) {
			Folder parentFolder = _dlAppService.getFolder(parentFolderId);

			repositoryId = parentFolder.getRepositoryId();
		}
		else {
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		ServiceContext serviceContext = new ServiceContext();

		return _dlAppService.addFolder(
			repositoryId, parentFolderId, title, summary, serviceContext);
	}

	@Override
	protected void doPutEntry(
			Folder folder, String title, String summary, String content,
			Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		_dlAppService.updateFolder(
			folder.getFolderId(), title, summary, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	private static final String _COLLECTION_NAME = "folders";

	private DLAppService _dlAppService;

}