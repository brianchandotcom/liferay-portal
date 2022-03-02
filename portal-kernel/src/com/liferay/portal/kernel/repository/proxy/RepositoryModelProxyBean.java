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

package com.liferay.portal.kernel.repository.proxy;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Mika Koivisto
 */
public abstract class RepositoryModelProxyBean {

	public RepositoryModelProxyBean(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	protected FileEntryProxyBean newFileEntryProxyBean(FileEntry fileEntry) {
		if (fileEntry == null) {
			return null;
		}

		FileEntry fileEntryProxy = newProxyInstance(
			fileEntry, _fileEntryProxyProviderFunction);

		return new FileEntryProxyBean(fileEntryProxy, _classLoader);
	}

	protected FileShortcutProxyBean newFileShortcutProxyBean(
		FileShortcut fileShortcut) {

		if (fileShortcut == null) {
			return null;
		}

		FileShortcut fileShortcutProxy = newProxyInstance(
			fileShortcut, _fileShortcutProxyProviderFunction);

		return new FileShortcutProxyBean(fileShortcutProxy, _classLoader);
	}

	protected FileVersionProxyBean newFileVersionProxyBean(
		FileVersion fileVersion) {

		if (fileVersion == null) {
			return null;
		}

		FileVersion fileVersionProxy = newProxyInstance(
			fileVersion, _fileVersionProxyProviderFunction);

		return new FileVersionProxyBean(fileVersionProxy, _classLoader);
	}

	protected FolderProxyBean newFolderProxyBean(Folder folder) {
		if (folder == null) {
			return null;
		}

		Folder folderProxy = newProxyInstance(
			folder, _folderProxyProviderFunction);

		return new FolderProxyBean(folderProxy, _classLoader);
	}

	protected LocalRepositoryProxyBean newLocalRepositoryProxyBean(
		LocalRepository localRepository) {

		LocalRepository localRepositoryProxy = newProxyInstance(
			localRepository, _localRepositoryProxyProviderFunction);

		return new LocalRepositoryProxyBean(localRepositoryProxy, _classLoader);
	}

	protected Object newProxyBean(Object bean) {
		if (bean instanceof FileEntry) {
			return newFileEntryProxyBean((FileEntry)bean);
		}
		else if (bean instanceof FileVersion) {
			return newFileVersionProxyBean((FileVersion)bean);
		}
		else if (bean instanceof Folder) {
			return newFolderProxyBean((Folder)bean);
		}

		return bean;
	}

	protected <T> T newProxyInstance(
		Object bean, Function<InvocationHandler, T> proxyProviderFunction) {

		if (bean == null) {
			return null;
		}

		return proxyProviderFunction.apply(
			new ClassLoaderBeanHandler(bean, _classLoader));
	}

	protected List<FileEntry> toFileEntryProxyBeans(
		List<FileEntry> fileEntries) {

		if (ListUtil.isEmpty(fileEntries)) {
			return fileEntries;
		}

		List<FileEntry> fileEntryProxyBeans = new ArrayList<>(
			fileEntries.size());

		for (FileEntry fileEntry : fileEntries) {
			fileEntryProxyBeans.add(newFileEntryProxyBean(fileEntry));
		}

		if (ListUtil.isUnmodifiableList(fileEntries)) {
			return Collections.unmodifiableList(fileEntryProxyBeans);
		}

		return fileEntryProxyBeans;
	}

	protected List<FileVersion> toFileVersionProxyBeans(
		List<FileVersion> fileVersions) {

		if (ListUtil.isEmpty(fileVersions)) {
			return fileVersions;
		}

		List<FileVersion> fileVersionProxyBeans = new ArrayList<>(
			fileVersions.size());

		for (FileVersion fileVersion : fileVersions) {
			fileVersionProxyBeans.add(newFileVersionProxyBean(fileVersion));
		}

		if (ListUtil.isUnmodifiableList(fileVersions)) {
			return Collections.unmodifiableList(fileVersionProxyBeans);
		}

		return fileVersionProxyBeans;
	}

	protected List<Folder> toFolderProxyBeans(List<Folder> folders) {
		if (ListUtil.isEmpty(folders)) {
			return folders;
		}

		List<Folder> folderProxyBeans = new ArrayList<>(folders.size());

		for (Folder folder : folders) {
			folderProxyBeans.add(newFolderProxyBean(folder));
		}

		if (ListUtil.isUnmodifiableList(folders)) {
			return Collections.unmodifiableList(folderProxyBeans);
		}

		return folderProxyBeans;
	}

	protected List<RepositoryEntry> toObjectProxyBeans(
		List<RepositoryEntry> repositoryEntries) {

		if (ListUtil.isEmpty(repositoryEntries)) {
			return repositoryEntries;
		}

		List<RepositoryEntry> objectProxyBeans = new ArrayList<>();

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			objectProxyBeans.add(
				(RepositoryEntry)newProxyBean(repositoryEntry));
		}

		if (ListUtil.isUnmodifiableList(repositoryEntries)) {
			return Collections.unmodifiableList(objectProxyBeans);
		}

		return objectProxyBeans;
	}

	private static final Function<InvocationHandler, FileEntry>
		_fileEntryProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			FileEntry.class);
	private static final Function<InvocationHandler, FileShortcut>
		_fileShortcutProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			FileShortcut.class);
	private static final Function<InvocationHandler, FileVersion>
		_fileVersionProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			FileVersion.class);
	private static final Function<InvocationHandler, Folder>
		_folderProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			Folder.class);
	private static final Function<InvocationHandler, LocalRepository>
		_localRepositoryProxyProviderFunction =
			ProxyUtil.getProxyProviderFunction(LocalRepository.class);

	private final ClassLoader _classLoader;

}