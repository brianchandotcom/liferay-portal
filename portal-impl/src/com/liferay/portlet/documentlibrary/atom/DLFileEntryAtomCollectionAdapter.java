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
import com.liferay.portal.kernel.atom.AtomException;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseMediaAtomCollectionAdapter;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class DLFileEntryAtomCollectionAdapter
	extends BaseMediaAtomCollectionAdapter<DLFileEntry> {

	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	public List<String> getEntryAuthors(DLFileEntry dlFileEntry) {
		List<String> authors = new ArrayList<String>();

		authors.add(dlFileEntry.getUserName());

		return authors;
	}

	public AtomEntryContent getEntryContent(
		DLFileEntry dlFileEntry, AtomRequestContext atomRequestContext) {

		AtomEntryContent atomEntryContent = new AtomEntryContent(
			AtomEntryContent.Type.MEDIA);

		atomEntryContent.setMimeType(dlFileEntry.getMimeType());

		String srcLink = AtomUtil.createEntryLink(
			atomRequestContext, _COLLECTION_NAME,
			dlFileEntry.getFileEntryId() + ":media");

		atomEntryContent.setSrcLink(srcLink);

		return atomEntryContent;
	}

	public String getEntryId(DLFileEntry dlFileEntry) {
		return String.valueOf(dlFileEntry.getPrimaryKey());
	}

	public String getEntrySummary(DLFileEntry dlFileEntry) {
		return dlFileEntry.getDescription();
	}

	public String getEntryTitle(DLFileEntry dlFileEntry) {
		return dlFileEntry.getTitle();
	}

	public Date getEntryUpdated(DLFileEntry dlFileEntry) {
		return dlFileEntry.getModifiedDate();
	}

	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, PortletKeys.DOCUMENT_LIBRARY) + " files";
	}

	@Override
	public String getMediaContentType(DLFileEntry dlFileEntry) {
		return dlFileEntry.getMimeType();
	}

	@Override
	public String getMediaName(DLFileEntry dlFileEntry) {
		return dlFileEntry.getTitle();
	}

	@Override
	public InputStream getMediaStream(DLFileEntry dlFileEntry)
		throws AtomException {

		try {
			return dlFileEntry.getContentStream();
		}
		catch (Exception ex) {
			throw new AtomException(SC_INTERNAL_SERVER_ERROR, ex);
		}
	}

	@Override
	protected void doDeleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long fileEntryId = GetterUtil.getLong(resourceName);

		DLFileEntryServiceUtil.deleteFileEntry(fileEntryId);
	}

	@Override
	protected DLFileEntry doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long fileEntryId = GetterUtil.getLong(resourceName);

		return DLFileEntryServiceUtil.getFileEntry(fileEntryId);
	}

	@Override
	protected Iterable<DLFileEntry> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = 0;

		long folderId = atomRequestContext.getLongParameter("folderId");

		if (folderId != 0) {
			DLFolder dlFolder = DLFolderServiceUtil.getFolder(folderId);

			groupId = dlFolder.getGroupId();
		}
		else {
			groupId = atomRequestContext.getLongParameter("groupId");
		}

		int count = DLFileEntryServiceUtil.getFileEntriesCount(
			groupId, folderId);

		AtomPager atomPager = new AtomPager(
			atomRequestContext, count);

		AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

		return DLFileEntryServiceUtil.getFileEntries(
			groupId, folderId, atomPager.getStart(), atomPager.getEnd() + 1,
			new EntryNameComparator());
	}

	@Override
	protected DLFileEntry doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = 0;
		long repositoryId = 0;

		long folderId = atomRequestContext.getLongParameter("folderId");

		if (folderId != 0) {
			DLFolder dlFolder = DLFolderServiceUtil.getFolder(folderId);

			groupId = dlFolder.getGroupId();
			repositoryId = dlFolder.getRepositoryId();
		}
		else {
			groupId = atomRequestContext.getLongParameter("groupId");
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		String mimeType = atomRequestContext.getHeader("Media-Content-Type");

		if (mimeType == null) {
			mimeType = MimeTypesUtil.getContentType(title);
		}

		byte[] contentDecoded = Base64.decode(content);

		ByteArrayInputStream contentInputStream = new ByteArrayInputStream(
			contentDecoded);

		ServiceContext serviceContext = new ServiceContext();

		DLFileEntry dlFileEntry = DLFileEntryServiceUtil.addFileEntry(
			groupId, repositoryId, folderId, title, mimeType, title, summary,
			null, 0, null, contentInputStream, contentDecoded.length,
			serviceContext);

		return dlFileEntry;
	}

	@Override
	protected DLFileEntry doPostMedia(
			String mimeType, String slug, InputStream inputStream,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = 0;
		long repositoryId = 0;

		long folderId = atomRequestContext.getLongParameter("folderId");

		if (folderId != 0) {
			DLFolder dlFolder = DLFolderServiceUtil.getFolder(folderId);

			groupId = dlFolder.getGroupId();
			repositoryId = dlFolder.getRepositoryId();
		}
		else {
			groupId = atomRequestContext.getLongParameter("groupId");
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		String title = atomRequestContext.getHeader("Title");
		String description = atomRequestContext.getHeader("Summary");

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		StreamUtil.transfer(inputStream, byteArrayOutputStream);

		byte[] content = byteArrayOutputStream.toByteArray();

		ByteArrayInputStream contentInputStream = new ByteArrayInputStream(
			content);

		ServiceContext serviceContext = new ServiceContext();

		DLFileEntry dlFileEntry = DLFileEntryServiceUtil.addFileEntry(
			groupId, repositoryId, folderId, title, mimeType, title,
			description, null, 0, null, contentInputStream, content.length,
			serviceContext);

		return dlFileEntry;
	}

	@Override
	protected void doPutEntry(
			DLFileEntry dlFileEntry, String title, String summary,
			String content, Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		String mimeType = atomRequestContext.getHeader("Media-Content-Type");

		if (mimeType == null) {
			mimeType = MimeTypesUtil.getContentType(title);
		}

		byte[] contentDecoded = Base64.decode(content);

		ByteArrayInputStream contentInputStream = new ByteArrayInputStream(
			contentDecoded);

		ServiceContext serviceContext = new ServiceContext();

		DLFileEntryServiceUtil.updateFileEntry(dlFileEntry.getFileEntryId(),
			title, mimeType, title, summary, null, true, 0, null,
			contentInputStream, contentDecoded.length, serviceContext);
	}

	@Override
	protected void doPutMedia(
			DLFileEntry dlFileEntry, String mimeType, String slug,
			InputStream inputStream, AtomRequestContext atomRequestContext)
		throws Exception {

		String title = atomRequestContext.getHeader("Title");
		String description = atomRequestContext.getHeader("Summary");

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		StreamUtil.transfer(inputStream, byteArrayOutputStream);

		byte[] content = byteArrayOutputStream.toByteArray();

		ByteArrayInputStream contentInputStream = new ByteArrayInputStream(
			content);

		ServiceContext serviceContext = new ServiceContext();

		DLFileEntryServiceUtil.updateFileEntry(dlFileEntry.getFileEntryId(),
			slug, mimeType, title, description, null, true, 0, null,
			contentInputStream, content.length, serviceContext);
	}

	static final String _COLLECTION_NAME = "files";

}