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

package com.liferay.gradle.util.copy;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.file.FileCopyDetails;

/**
 * @author Andrea Di Giorgi
 */
public class ExcludeExistingFileAction implements Action<FileCopyDetails> {

	public ExcludeExistingFileAction(File destinationDir) {
		_destinationDir = destinationDir;
	}

	@Override
	public void execute(FileCopyDetails fileCopyDetails) {
		File file = new File(_destinationDir, fileCopyDetails.getPath());

		if (file.exists()) {
			fileCopyDetails.exclude();
		}
	}

	private final File _destinationDir;

}