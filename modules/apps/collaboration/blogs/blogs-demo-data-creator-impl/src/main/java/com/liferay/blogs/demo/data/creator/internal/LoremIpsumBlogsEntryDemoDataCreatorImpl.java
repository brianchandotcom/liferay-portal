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

package com.liferay.blogs.demo.data.creator.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = BlogsEntryDemoDataCreator.class)
public class LoremIpsumBlogsEntryDemoDataCreatorImpl
	implements BlogsEntryDemoDataCreator {

	@Override
	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		String title = _getRandomElement(_entryTitles);

		String subtitle = _getRandomElement(_entrySubtitles);

		ImageSelector imageSelector = new ImageSelector(
			_getRandomImageBytes(userId, groupId),
			StringUtil.randomString() + ".jpeg", "image/jpeg",
			StringPool.BLANK);

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			userId, title, subtitle, null, _getRandomContent(),
			_getRandomDate(), false, false, null, null, imageSelector, null,
			serviceContext);

		_entryIds.add(blogsEntry.getEntryId());

		return blogsEntry;
	}

	@Override
	public void delete() throws PortalException {
		try {
			for (long entryId : _entryIds) {
				_blogsEntryLocalService.deleteBlogsEntry(entryId);

				_entryIds.remove(entryId);
			}
		}
		catch (NoSuchEntryException nsee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsee);
			}
		}

		_fileEntryDemoDataCreator.delete();
		_rootFolderDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setBlogLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setFileEntryDemoDataCreator(
		FileEntryDemoDataCreator fileEntryDemoDataCreator) {

		_fileEntryDemoDataCreator = fileEntryDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setRootFolderDemoDataCreator(
		RootFolderDemoDataCreator rootFolderDemoDataCreator) {

		_rootFolderDemoDataCreator = rootFolderDemoDataCreator;
	}

	private String _getRandomContent() {
		int numberOfParagraphs = RandomUtil.nextInt(5) + 3;

		StringBundler sb = new StringBundler(numberOfParagraphs * 3);

		for (int i = 0; i < numberOfParagraphs; i++) {
			sb.append("<p>");
			sb.append(_getRandomElement(_entryParagraphs));
			sb.append("</p>");
		}

		return sb.toString();
	}

	private Date _getRandomDate() {
		long start = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
		long end = new Date().getTime();

		long diff = end - start + 1;

		Timestamp timestamp = new Timestamp(
			start + (long)(Math.random() * diff));

		return new Date(timestamp.getTime());
	}

	private String _getRandomElement(List<String> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private byte[] _getRandomImageBytes(long userId, long groupId)
		throws IOException, PortalException {

		if (_blogsEntryImagesFolder == null) {
			_blogsEntryImagesFolder = _rootFolderDemoDataCreator.create(
				userId, groupId, "Blogs Images");
		}

		FileEntry fileEntry = _fileEntryDemoDataCreator.create(
			userId, _blogsEntryImagesFolder.getFolderId());

		return FileUtil.getBytes(fileEntry.getContentStream());
	}

	private Folder _blogsEntryImagesFolder;
	private BlogsEntryLocalService _blogsEntryLocalService;
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;
	private final List<Long> _entryIds = new CopyOnWriteArrayList<>();

	private static final Log _log = LogFactoryUtil.getLog(
		LoremIpsumBlogsEntryDemoDataCreatorImpl.class);

	private static List<String> _entryTitles = new ArrayList<>();
	private static List<String> _entrySubtitles = new ArrayList<>();
	private static List<String> _entryParagraphs = new ArrayList<>();
	static {
		_entryTitles.addAll(
			_getAllLines("dependencies/lorem/ipsum/titles.txt"));
		_entrySubtitles.addAll(
			_getAllLines("dependencies/lorem/ipsum/subtitles.txt"));
		_entryParagraphs.addAll(
			_getAllLines("dependencies/lorem/ipsum/paragraphs.txt"));
	}

	private static List<String> _getAllLines(String file) {
		List<String> dictionaryList = new ArrayList<>();

		try (InputStream is =
				LoremIpsumBlogsEntryDemoDataCreatorImpl.class.
					getResourceAsStream(file);

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new InputStreamReader(is))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				dictionaryList.add(line);
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}
		}

		return dictionaryList;
	}

}