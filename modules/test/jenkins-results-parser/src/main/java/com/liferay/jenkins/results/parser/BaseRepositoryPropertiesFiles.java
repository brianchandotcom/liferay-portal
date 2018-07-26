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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseRepositoryPropertiesFiles {

	public void writeRepositoryPropertiesFiles() {
		for (Map.Entry<String, Properties> entry :
				_propertiesFilesMap.entrySet()) {

			JenkinsResultsParserUtil.writePropertiesFile(
				new File(_localRepository.getDirectory(), entry.getKey()),
				entry.getValue(), true);
		}
	}

	protected BaseRepositoryPropertiesFiles(LocalRepository localRepository) {
		_localRepository = localRepository;
	}

	protected LocalRepository getLocalRepository() {
		return _localRepository;
	}

	protected Properties getProperties(String filePath) {
		return _propertiesFilesMap.get(filePath);
	}

	protected void setProperties(String filePath, Properties properties) {
		if (!_propertiesFilesMap.containsKey(filePath)) {
			_propertiesFilesMap.put(filePath, new Properties());
		}

		Properties fileProperties = _propertiesFilesMap.get(filePath);

		fileProperties.putAll(properties);

		_propertiesFilesMap.put(filePath, fileProperties);
	}

	private final LocalRepository _localRepository;
	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();

}