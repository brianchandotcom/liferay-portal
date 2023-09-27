/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getArtifactId();

	public String getAuthor();

	public String getChangeLog();

	public String getContext();

	public Properties getDeploymentSettings();

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

	public void setLicenses(List<License> licenses);

	public void setLiferayVersions(List<String> liferayVersions);

	public void setLongDescription(String longDescription);

	public void setModifiedDate(Date modifiedDate);

	public void setName(String name);

	public void setPageURL(String pageURL);

	public void setRecommendedDeploymentContext(String deploymentContext);

	public void setRequiredDeploymentContexts(
		List<String> requiredDeploymentContexts);

	public void setScreenshots(List<Screenshot> screenshots);

	public void setShortDescription(String shortDescription);

	public void setTags(List<String> tags);

	public void setTypes(List<String> types);

}