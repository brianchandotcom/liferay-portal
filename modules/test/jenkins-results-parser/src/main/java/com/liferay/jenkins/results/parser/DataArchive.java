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
 * @author Michael Hashimoto
 */
public class DataArchive {

	public DataArchive(
		DataArchiveBranch dataArchiveBranch, String dataArchiveType,
		String databaseName, String portalVersion) {

		_dataArchiveBranch = dataArchiveBranch;
		_dataArchiveType = dataArchiveType;
		_databaseName = databaseName;
		_portalVersion = portalVersion;

		File portalLegacyWorkingDirectory =
			dataArchiveBranch.getPortalLegacyWorkingDirectory();

		_committedDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				portalLegacyWorkingDirectory.toString(), "/", _portalVersion,
				"/data-archive/", _dataArchiveType, "-", _databaseName,
				".zip"));

		File generatedDataArchiveDirectory =
			dataArchiveBranch.getGeneratedDataArchiveDirectory();

		_generatedDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				generatedDataArchiveDirectory.toString(), "/", _portalVersion,
				"/", _dataArchiveType, "-", _databaseName, ".zip"));
	}

	private final File _committedDataArchiveFile;
	private final DataArchiveBranch _dataArchiveBranch;
	private final String _dataArchiveType;
	private final String _databaseName;
	private final File _generatedDataArchiveFile;
	private final String _portalVersion;

}