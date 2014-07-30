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
package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.RemotePluginPackageRepository;
import com.liferay.portal.kernel.plugin.Screenshot;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.model.PortletApp;
import com.liferay.registry.util.StringPlus;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * @author Raymond Augé
 */
public class BundlePluginPackage implements PluginPackage {

	public BundlePluginPackage(PortletApp portletApp, Bundle bundle) {
		_portletApp = portletApp;
		_bundle = bundle;

		_headers = _bundle.getHeaders();
		_version = Version.getInstance(getVersion());
	}

	@Override
	public String getArtifactId() {
		return String.valueOf(_bundle.getBundleId());
	}

	@Override
	public String getArtifactURL() {
		return _bundle.getLocation();
	}

	@Override
	public String getAuthor() {
		return _headers.get(Constants.BUNDLE_VENDOR);
	}

	@Override
	public String getChangeLog() {
		return null;
	}

	@Override
	public String getContext() {
		return _portletApp.getContextPath();
	}

	@Override
	public Properties getDeploymentSettings() {
		return null;
	}

	@Override
	public String getDownloadURL() {
		return _headers.get(Constants.BUNDLE_UPDATELOCATION);
	}

	@Override
	public String getGroupId() {
		return null;
	}

	@Override
	public List<License> getLicenses() {
		return null;
	}

	@Override
	public List<String> getLiferayVersions() {
		return null;
	}

	@Override
	public String getLongDescription() {
		return _headers.get(Constants.BUNDLE_DESCRIPTION);
	}

	@Override
	public Date getModifiedDate() {
		return new Date(_bundle.getLastModified());
	}

	@Override
	public String getModuleId() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getName() {
		return _headers.get(Constants.BUNDLE_NAME);
	}

	@Override
	public String getPackageId() {
		return  String.valueOf(_bundle.getBundleId());
	}

	@Override
	public String getPageURL() {
		return _headers.get(Constants.BUNDLE_DOCURL);
	}

	@Override
	public String getRecommendedDeploymentContext() {
		return null;
	}

	@Override
	public RemotePluginPackageRepository getRepository() {
		return null;
	}

	@Override
	public String getRepositoryURL() {
		return _headers.get(Constants.BUNDLE_UPDATELOCATION);
	}

	@Override
	public List<String> getRequiredDeploymentContexts() {
		return null;
	}

	@Override
	public List<Screenshot> getScreenshots() {
		return null;
	}

	@Override
	public String getShortDescription() {
		return _headers.get(Constants.BUNDLE_DESCRIPTION);
	}

	@Override
	public List<String> getTags() {
		return StringPlus.asList(_headers.get(Constants.BUNDLE_CATEGORY));
	}

	@Override
	public List<String> getTypes() {
		return StringPlus.asList("osgi bundle");
	}

	@Override
	public String getVersion() {
		return _bundle.getVersion().toString();
	}

	@Override
	public boolean isLaterVersionThan(PluginPackage pluginPackage) {
		return _version.isLaterVersionThan(pluginPackage.getVersion());
	}

	@Override
	public boolean isPreviousVersionThan(PluginPackage pluginPackage) {
		return _version.isPreviousVersionThan(pluginPackage.getVersion());
	}

	@Override
	public boolean isSameVersionAs(PluginPackage pluginPackage) {
		return _version.isSameVersionAs(pluginPackage.getVersion());
	}

	@Override
	public void setAuthor(String author) {
		// Unsupported
	}

	@Override
	public void setChangeLog(String changeLog) {
		// Unsupported
	}

	@Override
	public void setContext(String context) {
		// Unsupported
	}

	@Override
	public void setDeploymentSettings(Properties properties) {
		// Unsupported
	}

	@Override
	public void setDownloadURL(String downloadURL) {
		// Unsupported
	}

	@Override
	public void setLicenses(List<License> licenses) {
		// Unsupported
	}

	@Override
	public void setLiferayVersions(List<String> liferayVersions) {
		// Unsupported
	}

	@Override
	public void setLongDescription(String longDescription) {
		// Unsupported
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		// Unsupported
	}

	@Override
	public void setName(String name) {
		// Unsupported
	}

	@Override
	public void setPageURL(String pageURL) {
		// Unsupported
	}

	@Override
	public void setRecommendedDeploymentContext(String deploymentContext) {
		// Unsupported
	}

	@Override
	public void setRepository(RemotePluginPackageRepository repository) {
		// Unsupported
	}

	@Override
	public void setRequiredDeploymentContexts(
		List<String> requiredDeploymentContexts) {

		// Unsupported
	}

	@Override
	public void setScreenshots(List<Screenshot> screenshots) {
		// Unsupported
	}

	@Override
	public void setShortDescription(String shortDescription) {
		// Unsupported
	}

	@Override
	public void setTags(List<String> tags) {
		// Unsupported
	}

	@Override
	public void setTypes(List<String> types) {
		// Unsupported
	}

	private Bundle _bundle;
	private Dictionary<String, String> _headers;
	private PortletApp _portletApp;
	private Version _version;

}