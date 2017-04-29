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

package com.liferay.source.formatter.checks.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private final Map<String, List<SourceCheckConfiguration>>
		_sourceCheckConfigurationMap = new HashMap<>();

}