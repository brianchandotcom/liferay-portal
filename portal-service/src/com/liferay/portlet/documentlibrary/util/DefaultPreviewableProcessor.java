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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.File;
import java.io.InputStream;

/**
 * @author Alexander Chow
 */
public abstract class DefaultPreviewableProcessor
	extends DLPreviewableProcessor {

	protected InputStream doGetPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return doGetPreviewAsStream(fileVersion, 0);
	}

	protected InputStream doGetPreviewAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return doGetPreviewAsStream(fileVersion, index, getPreviewType());
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, 0);
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, index, getPreviewType());
	}

	protected InputStream doGetThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		return doGetThumbnailAsStream(fileVersion, getThumbnailType());
	}

	protected long doGetThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		return doGetThumbnailFileSize(fileVersion, getThumbnailType());
	}

	protected String getPreviewFilePath(FileVersion fileVersion) {
		return getPreviewFilePath(fileVersion, 0);
	}

	protected String getPreviewFilePath(FileVersion fileVersion, int index) {
		return getPreviewFilePath(fileVersion, index, getPreviewType());
	}

	protected File getPreviewTempFile(String id) {
		return getPreviewTempFile(id, 0);
	}

	protected File getPreviewTempFile(String id, int index) {
		return getPreviewTempFile(id, index, getPreviewType());
	}

	protected int getPreviewTempFileCount(FileVersion fileVersion) {
		return getPreviewTempFileCount(fileVersion, getPreviewType());
	}

	protected String getPreviewTempFilePath(String id) {
		return getPreviewTempFilePath(id, 0);
	}

	protected String getPreviewTempFilePath(String id, int index) {
		return getPreviewTempFilePath(id, index, getPreviewType());
	}

	protected abstract String getPreviewType();

	protected String getThumbnailFilePath(FileVersion fileVersion) {
		return getThumbnailFilePath(fileVersion, getThumbnailType());
	}

	protected File getThumbnailTempFile(String id) {
		return getThumbnailTempFile(id, getThumbnailType());
	}

	protected String getThumbnailTempFilePath(String id) {
		return getThumbnailTempFilePath(id, getThumbnailType());
	}

	protected String getThumbnailType() {
		return null;
	}

}