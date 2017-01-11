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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.sql.Timestamp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = BlogsEntryDemoDataCreator.class)
public class CreativeCommonsBlogsEntryDemoDataCreatorImpl
	implements BlogsEntryDemoDataCreator {

	@Activate
	public void activate(BundleContext bundleContext) {
		_availableIndexes.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		Collections.shuffle(_availableIndexes);
	}

	@Override
	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		serviceContext.setScopeGroupId(groupId);

		ImageSelector imageSelector = new ImageSelector(
			_getRandomImageBytes(userId, groupId),
			StringUtil.randomString() + ".jpeg", "image/jpeg",
			StringPool.BLANK);

		int index = _getNextIndex();

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			userId, _getRandomTitle(index), _getRandomSubtitle(index), null,
			_getRandomContent(index), _getRandomDate(), false, false, null,
			null, imageSelector, null, serviceContext);

		_entryIds.add(blogsEntry.getEntryId());

		return blogsEntry;
	}

	@Override
	public void delete() throws PortalException {
		try {
			for (long entryId : _entryIds) {
				_blogsEntryLocalService.deleteEntry(entryId);

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

	private int _getNextIndex() {
		int index = _atomicInteger.getAndIncrement();

		if (index == (_availableIndexes.size() - 1)) {
			_atomicInteger.set(0);
		}

		return _availableIndexes.get(index);
	}

	private String _getRandomContent(int index) throws IOException {
		Class<?> clazz = getClass();

		String titlePath =
			"com/liferay/blogs/demo/data/creator/internal/dependencies/" +
				"creative/commons/content" + index + ".txt";

		return StringUtil.read(clazz.getClassLoader(), titlePath, false);
	}

	private Date _getRandomDate() {
		long start = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
		long end = new Date().getTime();

		long diff = end - start + 1;

		Timestamp timestamp = new Timestamp(
			start + (long)(Math.random() * diff));

		return new Date(timestamp.getTime());
	}

	private byte[] _getRandomImageBytes(long userId, long groupId)
		throws IOException, PortalException {

		if (_blogsEntryImagesFolder == null) {
			_blogsEntryImagesFolder = _rootFolderDemoDataCreator.create(
				userId, groupId, "Blogs Images");
		}

		FileEntry fileEntry = _fileEntryDemoDataCreator.create(
			userId, _blogsEntryImagesFolder.getFolderId());

		FileVersion fileVersion = fileEntry.getFileVersion();

		return FileUtil.getBytes(fileVersion.getContentStream(false));
	}

	private String _getRandomSubtitle(int index) throws IOException {
		Class<?> clazz = getClass();

		String titlePath =
			"com/liferay/blogs/demo/data/creator/internal/dependencies/" +
				"creative/commons/subtitle" + index + ".txt";

		return StringUtil.read(clazz.getClassLoader(), titlePath, false);
	}

	private String _getRandomTitle(int index) throws IOException {
		Class<?> clazz = getClass();

		String titlePath =
			"com/liferay/blogs/demo/data/creator/internal/dependencies/" +
				"creative/commons/title" + index + ".txt";

		return StringUtil.read(clazz.getClassLoader(), titlePath, false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CreativeCommonsBlogsEntryDemoDataCreatorImpl.class);

	private final AtomicInteger _atomicInteger = new AtomicInteger(0);
	private final List<Integer> _availableIndexes =
		new CopyOnWriteArrayList<>();
	private Folder _blogsEntryImagesFolder;
	private BlogsEntryLocalService _blogsEntryLocalService;
	private final List<Long> _entryIds = new CopyOnWriteArrayList<>();
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;

}