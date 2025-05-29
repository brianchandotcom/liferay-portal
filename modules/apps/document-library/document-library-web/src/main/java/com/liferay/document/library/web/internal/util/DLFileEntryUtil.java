/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.kernel.util.comparator.FileVersionVersionComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Vamshi Krishna
 */
public class DLFileEntryUtil {

	public static List<FileVersion> getFileEntryVersionsSorted(
		FileEntry fileEntry, int status, int start, int end) {

		List<FileVersion> fileVersions = ListUtil.sort(
			fileEntry.getFileVersions(
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			new FileVersionVersionComparator(false));

		return fileVersions.subList(start, Math.min(end, fileVersions.size()));
	}

}