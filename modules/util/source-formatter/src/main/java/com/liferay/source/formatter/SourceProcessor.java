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

package com.liferay.source.formatter;

import com.liferay.source.formatter.checks.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceFormatterSuppressions;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * @author Hugo Huijser
 */
public interface SourceProcessor {

	public void format() throws Exception;

	public String[] getIncludes();

	public List<String> getModifiedFileNames();

	public Set<SourceFormatterMessage> getSourceFormatterMessages();

	public List<File> getSourceFormatterSuppressionsFiles();

	public List<SourceMismatchException> getSourceMismatchExceptions();

	public void setAllFileNames(List<String> allFileNames);

	public void setPluginsInsideModulesDirectoryNames(
		List<String> pluginsInsideModulesDirectoryNames);

	public void setPortalSource(boolean portalSource);

	public void setProgressStatusQueue(
		BlockingQueue<ProgressStatusUpdate> progressStatusQueue);

	public void setProjectPathPrefix(String projectPathPrefix);

	public void setPropertiesMap(Map<String, Properties> propertiesMap);

	public void setSourceFormatterArgs(SourceFormatterArgs sourceFormatterArgs);

	public void setSourceFormatterConfiguration(
		SourceFormatterConfiguration sourceFormatterConfiguration);

	public void setSourceFormatterExcludes(
		SourceFormatterExcludes sourceFormatterExcludes);

	public void setSourceFormatterSuppressions(
		SourceFormatterSuppressions sourceFormatterSuppressions);

	public void setSourceFormatterSuppressionsFiles(
		List<File> sourceFormatterSuppressionsFiles);

	public void setSubrepository(boolean subrepository);

}