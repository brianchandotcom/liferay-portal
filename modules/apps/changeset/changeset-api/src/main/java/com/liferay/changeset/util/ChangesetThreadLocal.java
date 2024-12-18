/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Gergely Mathe
 */
public class ChangesetThreadLocal {

	public static void addExportedChangesetEntryId(long changesetEntryId) {
		Set<Long> exportedChangesetEntryIds = getExportedChangesetEntryIds();

		exportedChangesetEntryIds.add(changesetEntryId);
	}

	public static void clearExportedChangesetEntryIds() {
		Set<Long> exportedChangesetEntryIds = getExportedChangesetEntryIds();

		exportedChangesetEntryIds.clear();
	}

	public static Set<Long> getExportedChangesetEntryIds() {
		return _exportedChangesetEntryIds.get();
	}

	public static void setExportedChangesetEntryIds(
		Set<Long> exportedChangesetEntryIds) {

		_exportedChangesetEntryIds.set(exportedChangesetEntryIds);
	}

	private static final ThreadLocal<Set<Long>> _exportedChangesetEntryIds =
		new CentralizedThreadLocal<>(
			ChangesetThreadLocal.class + "._exportedChangesetEntryIds",
			HashSet::new);

}