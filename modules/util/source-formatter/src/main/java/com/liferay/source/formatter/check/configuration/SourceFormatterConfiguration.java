/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterConfiguration {

	public void addSourceCheckConfiguration(
		String sourceProcessorName,
		SourceCheckConfiguration sourceCheckConfiguration) {

		List<SourceCheckConfiguration> sourceCheckConfigurations =
			_sourceCheckConfigurationMap.get(sourceProcessorName);

		if (sourceCheckConfigurations == null) {
			sourceCheckConfigurations = new ArrayList<>();
		}

		sourceCheckConfigurations.add(sourceCheckConfiguration);

		_sourceCheckConfigurationMap.put(
			sourceProcessorName, sourceCheckConfigurations);
	}

	public List<SourceCheckConfiguration> getSourceCheckConfigurations(
		String sourceProcessorName) {

		return _sourceCheckConfigurationMap.get(sourceProcessorName);
	}

	public Set<String> getSourceProcessorNames() {
		return _sourceCheckConfigurationMap.keySet();
	}

	private final Map<String, List<SourceCheckConfiguration>>
		_sourceCheckConfigurationMap = new HashMap<>();

}