/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class StoreProxyBean extends BaseProxyBean implements Store {

	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	public void addFile(
		long companyId, long repositoryId, String fileName, byte[] bytes) {

		throw new UnsupportedOperationException();
	}

	public void addFile(
		long companyId, long repositoryId, String fileName, File file) {

		throw new UnsupportedOperationException();
	}

	public void addFile(
		long companyId, long repositoryId, String fileName, InputStream is) {

		throw new UnsupportedOperationException();
	}

	public void checkRoot(long companyId) {
		throw new UnsupportedOperationException();
	}

	public void copyFileVersion(
		long companyId, long repositoryId, String fileName,
		String fromVersionLabel, String toVersionLabel) {

		throw new UnsupportedOperationException();
	}

	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	public void deleteFile(long companyId, long repositoryId, String fileName) {
		throw new UnsupportedOperationException();
	}

	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	public File getFile(long companyId, long repositoryId, String fileName) {
		throw new UnsupportedOperationException();
	}

	public File getFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	public byte[] getFileAsBytes(
		long companyId, long repositoryId, String fileName) {

		throw new UnsupportedOperationException();
	}

	public byte[] getFileAsBytes(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	public InputStream getFileAsStream(
		long companyId, long repositoryId, String fileName) {

		throw new UnsupportedOperationException();
	}

	public InputStream getFileAsStream(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	public String[] getFileNames(long companyId, long repositoryId) {
		throw new UnsupportedOperationException();
	}

	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	public long getFileSize(
		long companyId, long repositoryId, String fileName) {

		throw new UnsupportedOperationException();
	}

	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	public boolean hasFile(long companyId, long repositoryId, String fileName) {
		throw new UnsupportedOperationException();
	}

	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	public void move(String srcDir, String destDir) {
		throw new UnsupportedOperationException();
	}

	public void reindex(String[] ids) {
		throw new UnsupportedOperationException();
	}

	public void updateFile(
		long companyId, long repositoryId, long newRepositoryId,
		String fileName) {

		throw new UnsupportedOperationException();
	}

	public void updateFile(
		long companyId, long repositoryId, String fileName,
		String newFileName) {

		throw new UnsupportedOperationException();
	}

	public void updateFile(
		long companyId, long repositoryId, String fileName, String versionLabel,
		byte[] bytes) {

		throw new UnsupportedOperationException();
	}

	public void updateFile(
		long companyId, long repositoryId, String fileName, String versionLabel,
		File file) {

		throw new UnsupportedOperationException();
	}

	public void updateFile(
		long companyId, long repositoryId, String fileName, String versionLabel,
		InputStream is) {

		throw new UnsupportedOperationException();
	}

	public void updateFileVersion(
		long companyId, long repositoryId, String fileName,
		String fromVersionLabel, String toVersionLabel) {

		throw new UnsupportedOperationException();
	}

}