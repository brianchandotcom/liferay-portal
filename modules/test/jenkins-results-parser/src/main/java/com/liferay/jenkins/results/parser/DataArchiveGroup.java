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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class DataArchiveGroup {

	public DataArchiveGroup(
		DataArchiveBranch dataArchiveBranch, String dataArchiveType) {

		_dataArchiveBranch = dataArchiveBranch;
		_dataArchiveType = dataArchiveType;
	}

	public void addDataArchive(DataArchive dataArchive) {
		_dataArchives.add(dataArchive);
	}

	public String getDataArchiveType() {
		return _dataArchiveType;
	}

	public boolean isUpdated() {
		for (DataArchive dataArchive : _dataArchives) {
			if (!dataArchive.isUpdated()) {
				return false;
			}
		}

		return true;
	}

	private final DataArchiveBranch _dataArchiveBranch;
	private List<DataArchive> _dataArchives = new ArrayList<>();
	private final String _dataArchiveType;

}