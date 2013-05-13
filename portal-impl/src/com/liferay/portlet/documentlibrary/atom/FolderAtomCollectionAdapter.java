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

package com.liferay.portlet.documentlibrary.atom;

import com.liferay.portal.atom.AtomPager;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseAtomCollectionAdapter;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class FolderAtomCollectionAdapter
	extends BaseAtomCollectionAdapter<Folder> {

	@Override
	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	public List<String> getEntryAuthors(Folder folder) {
		List<String> authors = new ArrayList<String>();

		authors.add(folder.getUserName());

		return authors;
	}

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

	public String getEntryId(Folder folder) {
		return String.valueOf(folder.getPrimaryKey());
	}

	public String getEntrySummary(Folder folder) {
		return folder.getDescription();
	}

	public String getEntryTitle(Folder folder) {
		return folder.getName();
	}

	public Date getEntryUpdated(Folder folder) {
		return folder.getModifiedDate();
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, PortletKeys.DOCUMENT_LIBRARY) + " folders";
	}

	@Override
	protected void doDeleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long folderEntryId = GetterUtil.getLong(resourceName);

		DLAppServiceUtil.deleteFolder(folderEntryId);
	}

	@Override
	protected Folder doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long folderEntryId = GetterUtil.getLong(resourceName);

		return DLAppServiceUtil.getFolder(folderEntryId);
	}

	@Override
	protected Iterable<Folder> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long repositoryId = 0;

		long parentFolderId = atomRequestContext.getLongParameter(
			"parentFolderId");

		if (parentFolderId != 0) {
			Folder parentFolder = DLAppServiceUtil.getFolder(parentFolderId);

			repositoryId = parentFolder.getRepositoryId();
		}
		else {
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		int count = DLAppServiceUtil.getFoldersCount(
			repositoryId, parentFolderId);

		AtomPager atomPager = new AtomPager(atomRequestContext, count);

		AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

		return DLAppServiceUtil.getFolders(
			repositoryId, parentFolderId, atomPager.getStart(),
			atomPager.getEnd() + 1, new EntryNameComparator());
	}

	@Override
	protected Folder doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long repositoryId = 0;

		long parentFolderId = atomRequestContext.getLongParameter(
			"parentFolderId");

		if (parentFolderId != 0) {
			Folder parentFolder = DLAppServiceUtil.getFolder(parentFolderId);

			repositoryId = parentFolder.getRepositoryId();
		}
		else {
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		ServiceContext serviceContext = new ServiceContext();

		Folder folder = DLAppServiceUtil.addFolder(
			repositoryId, parentFolderId, title, summary, serviceContext);

		return folder;
	}

	@Override
	protected void doPutEntry(
			Folder folder, String title, String summary, String content,
			Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		DLAppServiceUtil.updateFolder(
			folder.getFolderId(), title, summary, serviceContext);
	}

	private static final String _COLLECTION_NAME = "folders";

}