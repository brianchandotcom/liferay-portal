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

package com.liferay.portal.plugin;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.RemotePluginPackageRepository;
import com.liferay.portal.kernel.plugin.Screenshot;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
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

	@Override
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

	@Override
	public String getArtifactId() {
		return _moduleId.getArtifactId();
	}

	@Override
	public String getArtifactURL() {
		return getRepositoryURL() + _moduleId.getArtifactPath();
	}

	@Override
	public String getAuthor() {
		return _author;
	}

	@Override
	public String getChangeLog() {
		return _changeLog;
	}

	@Override
	public String getContext() {
		return _context;
	}

	@Override
	public Properties getDeploymentSettings() {
		return _deploymentSettings;
	}

	@Override
	public String getDownloadURL() {
		RemotePluginPackageRepository remotePluginPackageRepository =
			getRepository();

		if (remotePluginPackageRepository != null) {
			Properties settings = remotePluginPackageRepository.getSettings();

			String useDownloadURL = settings.getProperty(
				RemotePluginPackageRepository.SETTING_USE_DOWNLOAD_URL);

			if (!GetterUtil.getBoolean(useDownloadURL, true)) {
				return getArtifactURL();
			}
		}

		if (Validator.isNotNull(_downloadURL)) {
			return _downloadURL;
		}

		return getArtifactURL();
	}

	@Override
	public String getGroupId() {
		return _moduleId.getGroupId();
	}

	@Override
	public List<License> getLicenses() {
		return _licenses;
	}

	@Override
	public List<String> getLiferayVersions() {
		return _liferayVersions;
	}

	@Override
	public String getLongDescription() {
		return _longDescription;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public String getModuleId() {
		return _moduleId.toString();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getPackageId() {
		return _moduleId.getPackageId();
	}

	@Override
	public String getPageURL() {
		return _pageURL;
	}

	@Override
	public String getRecommendedDeploymentContext() {
		String context = _recommendedDeploymentContext;

		if (Validator.isNull(context)) {
			context = _moduleId.getArtifactId();
		}

		return context;
	}

	@Override
	public RemotePluginPackageRepository getRepository() {
		return _repository;
	}

	@Override
	public String getRepositoryURL() {
		if (_repository != null) {
			return _repository.getRepositoryURL();
		}

		return RemotePluginPackageRepository.LOCAL_URL;
	}

	@Override
	public List<String> getRequiredDeploymentContexts() {
		return _requiredDeploymentContexts;
	}

	@Override
	public List<Screenshot> getScreenshots() {
		return _screenshots;
	}

	@Override
	public String getShortDescription() {
		return _shortDescription;
	}

	@Override
	public List<String> getTags() {
		return _tags;
	}

	@Override
	public List<String> getTypes() {
		return _types;
	}

	@Override
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

	@Override
	public boolean isLaterVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isLaterVersionThan(pluginPackage.getVersion());
	}

	@Override
	public boolean isPreviousVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isPreviousVersionThan(pluginPackage.getVersion());
	}

	@Override
	public boolean isSameVersionAs(PluginPackage pluginPackage) {
		return _moduleId.isSameVersionAs(pluginPackage.getVersion());
	}

	@Override
	public void setAuthor(String author) {
		_author = author;
	}

	@Override
	public void setChangeLog(String changeLog) {
		_changeLog = changeLog;
	}

	@Override
	public void setContext(String context) {
		_context = context;
	}

	@Override
	public void setDeploymentSettings(Properties deploymentSettings) {
		_deploymentSettings = deploymentSettings;
	}

	@Override
	public void setDownloadURL(String downloadURL) {
		_downloadURL = downloadURL;
	}

	@Override
	public void setLicenses(List<License> licenses) {
		_licenses = licenses;
	}

	@Override
	public void setLiferayVersions(List<String> liferayVersions) {
		_liferayVersions = liferayVersions;
	}

	@Override
	public void setLongDescription(String longDescription) {
		_longDescription = longDescription;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	@Override
	public void setRecommendedDeploymentContext(
		String recommendedDeploymentContext) {

		_recommendedDeploymentContext = recommendedDeploymentContext;
	}

	@Override
	public void setRepository(RemotePluginPackageRepository repository) {
		_repository = repository;
	}

	@Override
	public void setRequiredDeploymentContexts(
		List<String> requiredDeploymentContexts) {

		_requiredDeploymentContexts = requiredDeploymentContexts;
	}

	@Override
	public void setScreenshots(List<Screenshot> screenshots) {
		_screenshots = screenshots;
	}

	@Override
	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	@Override
	public void setTags(List<String> tags) {
		_tags = tags;
	}

	@Override
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
	private List<License> _licenses = new ArrayList<>();
	private List<String> _liferayVersions = new ArrayList<>();
	private String _longDescription = StringPool.BLANK;
	private Date _modifiedDate;
	private final ModuleId _moduleId;
	private String _name;
	private String _pageURL;
	private String _recommendedDeploymentContext;
	private RemotePluginPackageRepository _repository;
	private List<String> _requiredDeploymentContexts = Collections.emptyList();
	private List<Screenshot> _screenshots = new ArrayList<>();
	private String _shortDescription = StringPool.BLANK;
	private List<String> _tags = new ArrayList<>();
	private List<String> _types = new ArrayList<>();

}