/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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