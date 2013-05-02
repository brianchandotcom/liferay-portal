/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.RemotePluginPackageRepository;
import com.liferay.portal.kernel.plugin.Screenshot;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Jorge Ferrer
 */
public class PluginPackageImpl
	implements Comparable<PluginPackage>, PluginPackage, Serializable {

	public static final String STATUS_ALL = "all";

	public static final String STATUS_INSTALLATION_IN_PROCESS =
		"installationInProcess";

	public static final String STATUS_NEWER_VERSION_INSTALLED =
		"newerVersionInstalled";

	public static final String STATUS_NOT_INSTALLED = "notInstalled";

	public static final String STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED =
		"notInstalledOrOlderVersionInstalled";

	public static final String STATUS_OLDER_VERSION_INSTALLED =
		"olderVersionInstalled";

	public static final String STATUS_SAME_VERSION_INSTALLED =
		"sameVersionInstalled";

	public PluginPackageImpl(String moduleId) {
		_moduleId = ModuleId.getInstance(moduleId);
	}

	public int compareTo(PluginPackage pluginPackage) {
		return getName().compareTo(pluginPackage.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PluginPackage)) {
			return false;
		}

		PluginPackage pluginPackage = (PluginPackage)obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();

		equalsBuilder.append(getModuleId(), pluginPackage.getModuleId());
		equalsBuilder.append(
			getRepositoryURL(), pluginPackage.getRepositoryURL());

		return equalsBuilder.isEquals();
	}

	public String getArtifactId() {
		return _moduleId.getArtifactId();
	}

	public String getArtifactURL() {
		return getRepositoryURL() + _moduleId.getArtifactPath();
	}

	public String getAuthor() {
		return _author;
	}

	public String getChangeLog() {
		return _changeLog;
	}

	public String getContext() {
		return _context;
	}

	public Properties getDeploymentSettings() {
		return _deploymentSettings;
	}

	public String getDownloadURL() {
		String useDownloadURL = getRepository().getSettings().getProperty(
			RemotePluginPackageRepository.SETTING_USE_DOWNLOAD_URL);

		if (!GetterUtil.getBoolean(useDownloadURL, true)) {
			return getArtifactURL();
		}

		if (Validator.isNotNull(_downloadURL)) {
			return _downloadURL;
		}

		return getArtifactURL();
	}

	public String getGroupId() {
		return _moduleId.getGroupId();
	}

	public List<License> getLicenses() {
		return _licenses;
	}

	public List<String> getLiferayVersions() {
		return _liferayVersions;
	}

	public String getLongDescription() {
		return _longDescription;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getModuleId() {
		return _moduleId.toString();
	}

	public String getName() {
		return _name;
	}

	public String getPackageId() {
		return _moduleId.getPackageId();
	}

	public String getPageURL() {
		return _pageURL;
	}

	public String getRecommendedDeploymentContext() {
		String context = _recommendedDeploymentContext;

		if (Validator.isNull(context)) {
			context = _moduleId.getArtifactId();
		}

		return context;
	}

	public RemotePluginPackageRepository getRepository() {
		return _repository;
	}

	public String getRepositoryURL() {
		if (_repository != null) {
			return _repository.getRepositoryURL();
		}
		else {
			return RemotePluginPackageRepository.LOCAL_URL;
		}
	}

	public List<String> getRequiredDeploymentContexts() {
		return _requiredDeploymentContexts;
	}

	public List<Screenshot> getScreenshots() {
		return _screenshots;
	}

	public String getShortDescription() {
		return _shortDescription;
	}

	public List<String> getTags() {
		return _tags;
	}

	public List<String> getTypes() {
		return _types;
	}

	public String getVersion() {
		return _moduleId.getVersion();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

		hashCodeBuilder.append(getModuleId());
		hashCodeBuilder.append(getRepositoryURL());

		return hashCodeBuilder.hashCode();
	}

	public boolean isLaterVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isLaterVersionThan(pluginPackage.getVersion());
	}

	public boolean isPreviousVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isPreviousVersionThan(pluginPackage.getVersion());
	}

	public boolean isSameVersionAs(PluginPackage pluginPackage) {
		return _moduleId.isSameVersionAs(pluginPackage.getVersion());
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setChangeLog(String changeLog) {
		_changeLog = changeLog;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setDeploymentSettings(Properties deploymentSettings) {
		_deploymentSettings = deploymentSettings;
	}

	public void setDownloadURL(String downloadURL) {
		_downloadURL = downloadURL;
	}

	public void setLicenses(List<License> licenses) {
		_licenses = licenses;
	}

	public void setLiferayVersions(List<String> liferayVersions) {
		_liferayVersions = liferayVersions;
	}

	public void setLongDescription(String longDescription) {
		_longDescription = longDescription;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public void setRecommendedDeploymentContext(
		String recommendedDeploymentContext) {

		_recommendedDeploymentContext = recommendedDeploymentContext;
	}

	public void setRepository(RemotePluginPackageRepository repository) {
		_repository = repository;
	}

	public void setRequiredDeploymentContexts(
		List<String> requiredDeploymentContexts) {

		_requiredDeploymentContexts = requiredDeploymentContexts;
	}

	public void setScreenshots(List<Screenshot> screenshots) {
		_screenshots = screenshots;
	}

	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	public void setTags(List<String> tags) {
		_tags = tags;
	}

	public void setTypes(List<String> types) {
		_types = types;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.SLASH);
		sb.append(_context);
		sb.append(StringPool.COLON);
		sb.append(_moduleId);

		return sb.toString();
	}

	private String _author;
	private String _changeLog = StringPool.BLANK;
	private String _context;
	private Properties _deploymentSettings;
	private String _downloadURL;
	private List<License> _licenses = new ArrayList<License>();
	private List<String> _liferayVersions = new ArrayList<String>();
	private String _longDescription = StringPool.BLANK;
	private Date _modifiedDate;
	private ModuleId _moduleId;
	private String _name;
	private String _pageURL;
	private String _recommendedDeploymentContext;
	private RemotePluginPackageRepository _repository;
	private List<String> _requiredDeploymentContexts = Collections.emptyList();
	private List<Screenshot> _screenshots = new ArrayList<Screenshot>();
	private String _shortDescription = StringPool.BLANK;
	private List<String> _tags = new ArrayList<String>();
	private List<String> _types = new ArrayList<String>();

}