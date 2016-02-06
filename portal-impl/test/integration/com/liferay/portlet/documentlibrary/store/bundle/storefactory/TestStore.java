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

package com.liferay.portlet.documentlibrary.store.bundle.storefactory;

import com.liferay.document.library.kernel.store.BaseStore;
import com.liferay.document.library.kernel.store.Store;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true, property = "store.type=test", service = Store.class
)
public class TestStore extends BaseStore {

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	@Override
	public void addFile(
		long companyId, long repositoryId, String fileName, InputStream is) {
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {
	}

	@Override
	public InputStream getFileAsStream(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return null;
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		return new String[] {"TestStore"};
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		return new String[0];
	}

	@Override
	public long getFileSize(
		long companyId, long repositoryId, String fileName) {

		return 0;
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return false;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return false;
	}

	@Override
	public void move(String srcDir, String destDir) {
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, long newRepositoryId,
		String fileName) {
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, String fileName,
		String newFileName) {
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, String fileName, String versionLabel,
		InputStream is) {
	}

}