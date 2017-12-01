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

package com.liferay.jenkins.results.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class CommitFactory {

	public static Commit newCommit(
		String gitLogEntity, GitWorkingDirectory gitWorkingDirectory) {

		Matcher matcher = _pattern.matcher(gitLogEntity);

		if (!matcher.matches()) {
			throw new RuntimeException("Unable to find Git SHA");
		}

		String message = matcher.group("message");
		String sha = matcher.group("sha");
		Commit.Type type = Commit.Type.MANUAL;

		if (message.startsWith("archive:ignore")) {
			type = Commit.Type.LEGACY_ARCHIVE;
		}

		return new BaseCommit(gitWorkingDirectory, message, sha, type);
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?<sha>[0-9a-f]{40}) (?<message>.*)");

}