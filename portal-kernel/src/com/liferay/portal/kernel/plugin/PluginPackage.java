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

package com.liferay.portal.kernel.plugin;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public interface PluginPackage {

	public static final String REPOSITORY_XML_FILENAME_EXTENSION = "xml";

	public static final String REPOSITORY_XML_FILENAME_PREFIX =
		"liferay-plugin-repository";

	public String getArtifactId();

	public String getArtifactURL();

	public String getAuthor();

	public String getChangeLog();

	public String getContext();

	public Properties getDeploymentSettings();

	public String getDownloadURL();

	public String getGroupId();

	public List<License> getLicenses();

	public List<String> getLiferayVersions();

	public String getLongDescription();

	public Date getModifiedDate();

	public String getModuleId();

	public String getName();

	public String getPackageId();

	public String getPageURL();

	public String getRecommendedDeploymentContext();

	public RemotePluginPackageRepository getRepository();

	public String getRepositoryURL();

	public List<String> getRequiredDeploymentContexts();

	public List<Screenshot> getScreenshots();

	public String getShortDescription();

	public List<String> getTags();

	public List<String> getTypes();

	public String getVersion();

	public boolean isLaterVersionThan(PluginPackage pluginPackage);

	public boolean isPreviousVersionThan(PluginPackage pluginPackage);

	public boolean isSameVersionAs(PluginPackage pluginPackage);

	public void setAuthor(String author);

	public void setChangeLog(String changeLog);

	public void setContext(String context);

	public void setDeploymentSettings(Properties properties);

	public void setDownloadURL(String downloadURL);

	public void setLicenses(List<License> licenses);

	public void setLiferayVersions(List<String> liferayVersions);

	public void setLongDescription(String longDescription);

	public void setModifiedDate(Date modifiedDate);

	public void setName(String name);

	public void setPageURL(String pageURL);

	public void setRecommendedDeploymentContext(String deploymentContext);

	public void setRepository(RemotePluginPackageRepository repository);

	public void setRequiredDeploymentContexts(
		List<String> requiredDeploymentContexts);

	public void setScreenshots(List<Screenshot> screenshots);

	public void setShortDescription(String shortDescription);

	public void setTags(List<String> tags);

	public void setTypes(List<String> types);

}