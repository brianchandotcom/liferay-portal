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
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = BlogsEntryDemoDataCreator.class)
public class BlogsEntryDemoDataCreatorImpl
	implements BlogsEntryDemoDataCreator {

	@Override
	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		String title = _getRandomElement(_entryTitles);

		String subtitle = _getRandomElement(_entrySubtitles);

		Date date = _getRandomDate();

		ImageSelector imageSelector = new ImageSelector(
			_getRandomImageBytes(userId, groupId),
			UUID.randomUUID().toString() + ".jpeg", "image/jpeg",
			StringPool.BLANK);

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			userId, title, subtitle, null, _getRandomContent(), date, false,
			false, null, null, imageSelector, null, serviceContext);

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
		List<String> paragraphs = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			List<String> lines = new ArrayList<>();

			for (int j = 0; j < 15; j++) {
				lines.add(_getRandomElement(_entryLines));
			}

			paragraphs.add(
				lines.stream().collect(Collectors.joining(StringPool.SPACE)));
		}

		return paragraphs.stream().map(paragraph -> "<p>" + paragraph + "</p>").
			collect(Collectors.joining());
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
		return Optional.of(
			list.get(RandomUtil.nextInt(list.size()))).orElse("Test");
	}

	private byte[] _getRandomImageBytes(long userId, long groupId)
		throws IOException, PortalException {

		String folderName = "Blogs Test";

		if (_blogsEntryImagesFolder == null) {
			_blogsEntryImagesFolder = _rootFolderDemoDataCreator.create(
				userId, groupId, folderName);
		}

		FileEntry fileEntry = _fileEntryDemoDataCreator.create(
			userId, _blogsEntryImagesFolder.getFolderId());

		return FileUtil.getBytes(
			fileEntry.getFileVersion().getContentStream(false));
	}

	private Folder _blogsEntryImagesFolder;
	private BlogsEntryLocalService _blogsEntryLocalService;
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;
	private final List<Long> _entryIds = new ArrayList<>();

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryDemoDataCreatorImpl.class);

	private static List<String> _entryTitles = new ArrayList<>();
	private static List<String> _entrySubtitles = new ArrayList<>();
	private static List<String> _entryLines = new ArrayList<>();
	static {
		_entryTitles.addAll(_getAllLines("dependencies/BlogsEntryTitles.txt"));
		_entrySubtitles.addAll(
			_getAllLines("dependencies/BlogsEntrySubtitles.txt"));
		_entryLines.addAll(_getAllLines("dependencies/BlogsEntryLines.txt"));
	}

	private static List<String> _getAllLines(String file) {
		List<String> dictionaryList = new ArrayList<>();

		try (InputStream is =
				BlogsEntryDemoDataCreatorImpl.class.getResourceAsStream(file);
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