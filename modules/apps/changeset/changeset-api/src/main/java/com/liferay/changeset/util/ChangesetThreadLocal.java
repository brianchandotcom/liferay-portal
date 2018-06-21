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