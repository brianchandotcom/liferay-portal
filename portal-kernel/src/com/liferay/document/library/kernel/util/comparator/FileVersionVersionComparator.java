/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Comparator;

/**
 * @author Iván Zaera
 */
public class FileVersionVersionComparator implements Comparator<FileVersion> {

	public FileVersionVersionComparator() {
		this(false);
	}

	public FileVersionVersionComparator(boolean ascending) {
		_versionNumberComparator = new VersionNumberComparator(ascending);
	}

	@Override
	public int compare(FileVersion fileVersion1, FileVersion fileVersion2) {
		return _versionNumberComparator.compare(
			fileVersion1.getVersion(), fileVersion2.getVersion());
	}

	public boolean isAscending() {
		return _versionNumberComparator.isAscending();
	}

	private final VersionNumberComparator _versionNumberComparator;

}