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

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.document.library.kernel.model.DLFileVersion;

import java.util.Comparator;

/**
 * @author Bruno Farache
 */
public class DLFileVersionVersionComparator
	implements Comparator<DLFileVersion> {

	public static final DLFileVersionVersionComparator INSTANCE_ASCENDING =
		new DLFileVersionVersionComparator(Boolean.TRUE);

	public static final DLFileVersionVersionComparator INSTANCE_DESCENDING =
		new DLFileVersionVersionComparator(Boolean.FALSE);

	public static DLFileVersionVersionComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return INSTANCE_ASCENDING;
		}

		return INSTANCE_DESCENDING;
	}

	/**
	 * @deprecated As of 2.0.0, replaced by {@link #INSTANCE_DESCENDING}
	 */
	@Deprecated
	public DLFileVersionVersionComparator() {
		this(false);
	}

	/**
	 * @deprecated As of 2.0.0, replaced by {@link #getInstance(boolean)}
	 */
	@Deprecated
	public DLFileVersionVersionComparator(boolean ascending) {
		_versionNumberComparator = new VersionNumberComparator(ascending);
	}

	@Override
	public int compare(
		DLFileVersion dlFileVersion1, DLFileVersion dlFileVersion2) {

		return _versionNumberComparator.compare(
			dlFileVersion1.getVersion(), dlFileVersion2.getVersion());
	}

	public boolean isAscending() {
		return _versionNumberComparator.isAscending();
	}

	private DLFileVersionVersionComparator(Boolean ascending) {
		_versionNumberComparator = new VersionNumberComparator(ascending);
	}

	private final VersionNumberComparator _versionNumberComparator;

}