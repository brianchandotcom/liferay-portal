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
import com.liferay.portal.kernel.atom.AtomException;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseMediaAtomCollectionAdapter;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class FileEntryAtomCollectionAdapter
	extends BaseMediaAtomCollectionAdapter<FileEntry> {

	@Override
	public String getCollectionName() {
		return COLLECTION_NAME;
	}

	public List<String> getEntryAuthors(FileEntry fileEntry) {
		List<String> authors = new ArrayList<String>();

		authors.add(fileEntry.getUserName());

		return authors;
	}

	public AtomEntryContent getEntryContent(
		FileEntry fileEntry, AtomRequestContext atomRequestContext) {

		AtomEntryContent atomEntryContent = new AtomEntryContent(
			AtomEntryContent.Type.MEDIA);

		atomEntryContent.setMimeType(fileEntry.getMimeType());

		String srcLink = AtomUtil.createEntryLink(
			atomRequestContext, COLLECTION_NAME,
			fileEntry.getFileEntryId() + ":media");

		atomEntryContent.setSrcLink(srcLink);

		return atomEntryContent;
	}

	public String getEntryId(FileEntry fileEntry) {
		return String.valueOf(fileEntry.getPrimaryKey());
	}

	public String getEntrySummary(FileEntry fileEntry) {
		return fileEntry.getDescription();
	}

	public String getEntryTitle(FileEntry fileEntry) {
		return fileEntry.getTitle();
	}

	public Date getEntryUpdated(FileEntry fileEntry) {
		return fileEntry.getModifiedDate();
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, PortletKeys.DOCUMENT_LIBRARY) + " files";
	}

	@Override
	public String getMediaContentType(FileEntry fileEntry) {
		return fileEntry.getMimeType();
	}

	@Override
	public String getMediaName(FileEntry fileEntry) {
		return fileEntry.getTitle();
	}

	@Override
	public InputStream getMediaStream(FileEntry fileEntry)
		throws AtomException {

		try {
			return fileEntry.getContentStream();
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

		DLAppServiceUtil.deleteFileEntry(fileEntryId);
	}

	@Override
	protected FileEntry doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long fileEntryId = GetterUtil.getLong(resourceName);

		return DLAppServiceUtil.getFileEntry(fileEntryId);
	}

	@Override
	protected Iterable<FileEntry> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long folderId = atomRequestContext.getLongParameter("folderId");

		long repositoryId = 0;

		if (folderId != 0) {
			Folder folder = DLAppServiceUtil.getFolder(folderId);

			repositoryId = folder.getRepositoryId();
		}
		else {
			repositoryId = atomRequestContext.getLongParameter("repositoryId");
		}

		int count = DLAppServiceUtil.getFileEntriesCount(
			repositoryId, folderId);

		AtomPager atomPager = new AtomPager(atomRequestContext, count);

		AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

		return DLAppServiceUtil.getFileEntries(
			repositoryId, folderId, atomPager.getStart(),
			atomPager.getEnd() + 1, new EntryNameComparator());
	}

	@Override
	protected FileEntry doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long folderId = atomRequestContext.getLongParameter("folderId");

		long repositoryId = 0;

		if (folderId != 0) {
			Folder folder = DLAppServiceUtil.getFolder(folderId);

			repositoryId = folder.getRepositoryId();
		}
		else {
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

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			repositoryId, folderId, title, mimeType, title, summary, null,
			contentInputStream, contentDecoded.length, serviceContext);

		return fileEntry;
	}

	@Override
	protected FileEntry doPostMedia(
			String mimeType, String slug, InputStream inputStream,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long folderId = atomRequestContext.getLongParameter("folderId");

		long repositoryId = 0;

		if (folderId != 0) {
			Folder folder = DLAppServiceUtil.getFolder(folderId);

			repositoryId = folder.getRepositoryId();
		}
		else {
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

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			repositoryId, folderId, title, mimeType, title, description, null,
			contentInputStream, content.length, serviceContext);

		return fileEntry;
	}

	@Override
	protected void doPutEntry(
			FileEntry fileEntry, String title, String summary, String content,
			Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		String mimeType = atomRequestContext.getHeader("Media-Content-Type");

		if (mimeType == null) {
			mimeType = MimeTypesUtil.getContentType(title);
		}

		byte[] contentDecoded = Base64.decode(content);

		ByteArrayInputStream contentInputStream = new ByteArrayInputStream(
			contentDecoded);

		ServiceContext serviceContext = new ServiceContext();

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), title, mimeType, title, summary, null,
			true, contentInputStream, contentDecoded.length, serviceContext);
	}

	@Override
	protected void doPutMedia(
			FileEntry fileEntry, String mimeType, String slug,
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

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), slug, mimeType, title, description,
			null, true, contentInputStream, content.length, serviceContext);
	}

	protected static final String COLLECTION_NAME = "files";

}