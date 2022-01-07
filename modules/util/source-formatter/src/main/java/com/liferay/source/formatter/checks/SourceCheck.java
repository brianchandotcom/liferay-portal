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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.SourceProcessor;

import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public interface SourceCheck {

	public Set<SourceFormatterMessage> getSourceFormatterMessages(
		String fileName);

	public int getWeight();

	public boolean isEnabled(String absolutePath);

	public boolean isJavaSource(String content, int pos);

	public boolean isJavaSource(
		String content, int pos, boolean checkInsideTags);

	public boolean isLiferaySourceCheck();

	public boolean isModuleSourceCheck();

	public void setAllFileNames(List<String> allFileNames);

	public void setAttributes(String attributes) throws JSONException;

	public void setBaseDirName(String baseDirName);

	public void setExcludes(String excludes) throws JSONException;

	public void setFileExtensions(List<String> fileExtenstions);

	public void setFilterCheckNames(List<String> filterCheckNames);

	public void setMaxDirLevel(int maxDirLevel);

	public void setMaxLineLength(int maxLineLength);

	public void setPluginsInsideModulesDirectoryNames(
		List<String> pluginsInsideModulesDirectoryNames);

	public void setPortalSource(boolean portalSource);

	public void setProjectPathPrefix(String projectPathPrefix);

	public void setSourceFormatterExcludes(
		SourceFormatterExcludes sourceFormatterExcludes);

	public void setSourceProcessor(SourceProcessor sourceProcessor);

	public void setSubrepository(boolean subrepository);

	public void setWeight(int weight);

}