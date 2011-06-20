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

package com.liferay.portal.repository.proxy;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public abstract class RepositoryModelProxyBean {

	RepositoryModelProxyBean(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	protected FileEntryProxyBean newFileEntryProxyBean(FileEntry fileEntry) {
		if (fileEntry == null) {
			return null;
		}

		FileEntry fileEntryProxy = (FileEntry) newProxyInstance(
			fileEntry, FileEntry.class);

		return new FileEntryProxyBean(fileEntryProxy, _classLoader);
	}

	protected FileVersionProxyBean newFileVersionProxyBean(FileVersion fileVersion) {
		if (fileVersion == null) {
			return null;
		}

		FileVersion fileVersionProxy = (FileVersion) newProxyInstance(
			fileVersion, FileVersion.class);

		return new FileVersionProxyBean(fileVersionProxy, _classLoader);
	}

	protected FolderProxyBean newFolderProxyBean(Folder folder) {
		if (folder == null) {
			return null;
		}

		Folder folderProxy = (Folder) newProxyInstance(folder, Folder.class);

		return new FolderProxyBean(folderProxy, _classLoader);
	}

	protected LocalRepositoryProxyBean newLocalRepositoryProxyBean(
		LocalRepository localRepository) {

		LocalRepository localRepositoryProxy =
			(LocalRepository) newProxyInstance(
				localRepository, LocalRepository.class);

		return new LocalRepositoryProxyBean(localRepositoryProxy, _classLoader);
	}

	protected Object newProxyBean(Object bean) {
		if (bean instanceof FileEntry) {
			return newFileEntryProxyBean((FileEntry) bean);
		}
		else if (bean instanceof FileVersion) {
			return newFileVersionProxyBean((FileVersion) bean);
		}
		else if (bean instanceof Folder) {
			return newFolderProxyBean((Folder) bean);
		}
		else {
			return bean;
		}
	}

	protected Object newProxyInstance(Object bean, Class<?> clazz) {
		if (bean == null) {
			return null;
		}

		return Proxy.newProxyInstance(
			_classLoader, new Class[]{clazz},
			new ClassLoaderBeanHandler(bean, _classLoader));
	}

	protected List<FileEntry> toFileEntryProxyBeanList(
		List<FileEntry> fileEntries) {

		if ((fileEntries == null) || fileEntries.isEmpty()) {
			return fileEntries;
		}

		List<FileEntry> fileEntryProxyBeans =
			new ArrayList<FileEntry>(fileEntries.size());

		for (FileEntry fileEntry : fileEntries) {
			fileEntryProxyBeans.add(newFileEntryProxyBean(fileEntry));
		}

		if (fileEntries instanceof UnmodifiableList) {
			return new UnmodifiableList<FileEntry>(fileEntries);
		}

		return fileEntryProxyBeans;
	}

	protected List<FileVersion> toFileVersionProxyBeanList(
		List<FileVersion> fileVersionList) {

		if ((fileVersionList == null) || fileVersionList.isEmpty()) {
			return fileVersionList;
		}

		List<FileVersion> fileVersionProxyBeanList =
			new ArrayList<FileVersion>(fileVersionList.size());

		for (FileVersion fileVersion : fileVersionList) {
			fileVersionProxyBeanList.add(newFileVersionProxyBean(fileVersion));
		}

		if (fileVersionList instanceof UnmodifiableList) {
			return new UnmodifiableList<FileVersion>(fileVersionList);
		}

		return fileVersionProxyBeanList;
	}

	protected List<Folder> toFolderProxyBeanList(List<Folder> folders) {
		if ((folders == null) || folders.isEmpty()) {
			return folders;
		}

		List<Folder> folderProxyBeans = new ArrayList<Folder>(folders.size());

		for (Folder folder : folders) {
			folderProxyBeans.add(newFolderProxyBean(folder));
		}

		if (folders instanceof UnmodifiableList) {
			return new UnmodifiableList<Folder>(folderProxyBeans);
		}

		return folderProxyBeans;
	}

	protected List<Object> toObjectProxyBeanList(
		List<Object> objects) {

		if ((objects == null) || objects.isEmpty()) {
			return objects;
		}

		List<Object> objectBeans = new ArrayList<Object>();

		for (Object object : objects) {
			objectBeans.add(newProxyBean(object));
		}

		if (objects instanceof UnmodifiableList) {
			return new UnmodifiableList<Object>(objectBeans);
		}

		return objectBeans;
	}

	private ClassLoader _classLoader;

}
