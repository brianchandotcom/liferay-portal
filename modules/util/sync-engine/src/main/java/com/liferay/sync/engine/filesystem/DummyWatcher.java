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

package com.liferay.sync.engine.filesystem;

import java.nio.file.Path;

/**
 * @author Shinn Lok
 */
public class DummyWatcher extends Watcher {

	public DummyWatcher() {
		super(null, null);
	}

	@Override
	public void addDeletedFilePathName(String filePathName) {
	}

	@Override
	public void addDownloadedFilePathName(String filePathName) {
	}

	@Override
	public void close() {
	}

	@Override
	public void registerFilePath(Path filePath) {
	}

	@Override
	public void removeDeletedFilePathName(String filePathName) {
	}

	@Override
	public void removeDownloadedFilePathName(String filePathName) {
	}

	@Override
	public void run() {
	}

	@Override
	public void unregisterFilePath(Path filePath) {
	}

	@Override
	public void walkFileTree(Path filePath) {
	}

	@Override
	protected void init() {
	}

}