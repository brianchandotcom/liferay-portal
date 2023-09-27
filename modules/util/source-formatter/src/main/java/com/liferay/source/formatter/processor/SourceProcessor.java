/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.SourceMismatchException;
import com.liferay.source.formatter.check.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.check.configuration.SourceFormatterSuppressions;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public interface SourceProcessor {

	public void format() throws Exception;

	public String[] getIncludes();

	public List<String> getModifiedFileNames();

	public SourceFormatterArgs getSourceFormatterArgs();

	public Set<SourceFormatterMessage> getSourceFormatterMessages();

	public List<SourceMismatchException> getSourceMismatchExceptions();

	public boolean isPortalSource();

	public boolean isSubrepository();

	public void setAllFileNames(List<String> allFileNames);

	public void setPluginsInsideModulesDirectoryNames(
		List<String> pluginsInsideModulesDirectoryNames);

	public void setPortalSource(boolean portalSource);

	public void setProjectPathPrefix(String projectPathPrefix);

	public void setPropertiesMap(Map<String, Properties> propertiesMap);

	public void setSourceFormatterArgs(SourceFormatterArgs sourceFormatterArgs);

	public void setSourceFormatterConfiguration(
		SourceFormatterConfiguration sourceFormatterConfiguration);

	public void setSourceFormatterExcludes(
		SourceFormatterExcludes sourceFormatterExcludes);

	public void setSourceFormatterSuppressions(
		SourceFormatterSuppressions sourceFormatterSuppressions);

	public void setSubrepository(boolean subrepository);

}