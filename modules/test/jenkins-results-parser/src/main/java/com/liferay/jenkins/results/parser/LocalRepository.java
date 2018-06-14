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

import java.io.File;

/**
 * @author Peter Yoo
 */
public class LocalRepository extends BaseRepository {

	public File getDirectory() {
		return directory;
	}

	protected LocalRepository(File directory, String name) {
		super(name);

		if ((directory == null) || !directory.exists()) {
			throw new IllegalArgumentException("Unable to find repository");
		}

		File dotGit = new File(directory, ".git");

		if (!dotGit.exists()) {
			throw new IllegalArgumentException(
				directory + " is not a valid repository");
		}

		this.directory = directory;
	}

	protected final File directory;

}