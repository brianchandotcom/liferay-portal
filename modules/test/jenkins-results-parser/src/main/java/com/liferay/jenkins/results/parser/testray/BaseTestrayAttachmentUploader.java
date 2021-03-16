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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayAttachmentUploader
	implements TestrayAttachmentUploader {

	protected BaseTestrayAttachmentUploader(Build build) {
		if (build == null) {
			throw new RuntimeException("Please set a build");
		}

		_build = build;

		_testrayAttachmentRecorder =
			TestrayFactory.newTestrayAttachmentRecorder(build);
	}

	protected Build getBuild() {
		return _build;
	}

	protected TestrayAttachmentRecorder getTestrayAttachmentRecorder() {
		return _testrayAttachmentRecorder;
	}

	private final Build _build;
	private final TestrayAttachmentRecorder _testrayAttachmentRecorder;

}