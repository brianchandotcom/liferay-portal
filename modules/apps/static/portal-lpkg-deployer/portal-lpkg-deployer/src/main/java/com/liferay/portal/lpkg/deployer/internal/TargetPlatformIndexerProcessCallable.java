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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.target.platform.indexer.internal.TargetPlatformIndexerUtil;

import java.io.File;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class TargetPlatformIndexerProcessCallable
	implements ProcessCallable<byte[]> {

	public TargetPlatformIndexerProcessCallable(
		List<File> additionalJarFiles, long stopWaitTimeout,
		String... dirNames) {

		_additionalJarFiles = additionalJarFiles;
		_stopWaitTimeout = stopWaitTimeout;
		_dirNames = dirNames;
	}

	@Override
	public byte[] call() throws ProcessException {
		try {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			TargetPlatformIndexerUtil.indexTargetPlatform(
				unsyncByteArrayOutputStream, _additionalJarFiles,
				_stopWaitTimeout, _dirNames);

			return unsyncByteArrayOutputStream.toByteArray();
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
	}

	@Override
	public String toString() {
		return "Target Platform Indexer";
	}

	private static final long serialVersionUID = 1L;

	private final List<File> _additionalJarFiles;
	private final String[] _dirNames;
	private final long _stopWaitTimeout;

}