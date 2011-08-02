/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class DLFolderAtomCollectionAdapter
	extends BaseAtomCollectionAdapter<DLFolder> {

	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	public List<String> getEntryAuthors(DLFolder dlFolder) {
		List<String> authors = new ArrayList<String>();

		authors.add(dlFolder.getUserName());

		return authors;
	}

	public AtomEntryContent getEntryContent(
		DLFolder dlFolder, AtomRequestContext atomRequestContext) {

		AtomEntryContent atomEntryContent = new AtomEntryContent(
			AtomEntryContent.Type.XML);

		String srcLink = AtomUtil.createCollectionLink(
			atomRequestContext,
			DLFileEntryAtomCollectionAdapter._COLLECTION_NAME) + "?folderId=" +
				dlFolder.getFolderId();

		atomEntryContent.setSrcLink(srcLink);

		return atomEntryContent;
	}

	public String getEntryId(DLFolder dlFolder) {
		return String.valueOf(dlFolder.getPrimaryKey());
	}

	public String getEntrySummary(DLFolder dlFolder) {
		return dlFolder.getDescription();
	}

	public String getEntryTitle(DLFolder dlFolder) {
		return dlFolder.getName();
	}

	public Date getEntryUpdated(DLFolder dlFolder) {
		return dlFolder.getModifiedDate();
	}

	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, PortletKeys.DOCUMENT_LIBRARY) + " folders";
	}

	@Override
	protected void doDeleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long folderEntryId = GetterUtil.getLong(resourceName);

		DLFolderServiceUtil.deleteFolder(folderEntryId);
	}

	@Override
	protected DLFolder doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long folderEntryId = GetterUtil.getLong(resourceName);

		return DLFolderServiceUtil.getFolder(folderEntryId);
	}

	@Override
	protected Iterable<DLFolder> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = 0;

		long parentFolderId = atomRequestContext.getLongParameter(
			"parentFolderId");

		if (parentFolderId != 0) {
			DLFolder dlParentFolder = DLFolderServiceUtil.getFolder(
				parentFolderId);

			groupId = dlParentFolder.getGroupId();
		}
		else {
			groupId = atomRequestContext.getLongParameter("groupId");
		}

		int count = DLFolderServiceUtil.getFoldersCount(
			groupId, parentFolderId);

		AtomPager atomPager = new AtomPager(atomRequestContext, count);

		AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

		return DLFolderServiceUtil.getFolders(
			groupId, parentFolderId, atomPager.getStart(),
			atomPager.getEnd() + 1, new EntryNameComparator());
	}

	@Override
	protected DLFolder doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = 0;
		long repositoryId = 0;

		long parentFolderId = atomRequestContext.getLongParameter(
			"parentFolderId");

		if (parentFolderId != 0) {
			DLFolder dlParentFolder = DLFolderServiceUtil.getFolder(
				parentFolderId);

			groupId = dlParentFolder.getGroupId();
			repositoryId = dlParentFolder.getRepositoryId();
		}
		else {
			groupId = atomRequestContext.getLongParameter("groupId");
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		ServiceContext serviceContext = new ServiceContext();

		DLFolder dlFolder = DLFolderServiceUtil.addFolder(
			groupId, repositoryId, false, parentFolderId, title, summary,
			serviceContext);

		return dlFolder;
	}

	@Override
	protected void doPutEntry(
			DLFolder dlFolder, String title, String summary,
			String content, Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		long defaultFileEntryTypeId = 0;
		List<Long> fileEntryTypeIds = new ArrayList<Long>();
		boolean overrideFileEntryTypes = false;

		ServiceContext serviceContext = new ServiceContext();

		DLFolderServiceUtil.updateFolder(
			dlFolder.getFolderId(), title, summary, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);
	}

	private static final String _COLLECTION_NAME = "folders";

}