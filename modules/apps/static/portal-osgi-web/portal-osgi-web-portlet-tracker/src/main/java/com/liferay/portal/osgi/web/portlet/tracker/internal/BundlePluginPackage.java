/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.portlet.tracker.internal;

import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.Screenshot;
import com.liferay.portal.kernel.plugin.Version;

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

	public BundlePluginPackage(Bundle bundle, PortletApp portletApp) {
		_bundle = bundle;
		_portletApp = portletApp;

		_headers = bundle.getHeaders(StringPool.BLANK);
		_version = Version.getInstance(getVersion());
	}

	@Override
	public String getArtifactId() {
		return String.valueOf(_bundle.getBundleId());
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
		return String.valueOf(_bundle.getBundleId());
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
		return String.valueOf(_bundle.getVersion());
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
	}

	@Override
	public void setChangeLog(String changeLog) {
	}

	@Override
	public void setContext(String context) {
	}

	@Override
	public void setDeploymentSettings(Properties deploymentSettings) {
	}

	@Override
	public void setLicenses(List<License> licenses) {
	}

	@Override
	public void setLiferayVersions(List<String> liferayVersions) {
	}

	@Override
	public void setLongDescription(String longDescription) {
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public void setPageURL(String pageURL) {
	}

	@Override
	public void setRecommendedDeploymentContext(String deploymentContext) {
	}

	@Override
	public void setRequiredDeploymentContexts(
		List<String> requiredDeploymentContexts) {
	}

	@Override
	public void setScreenshots(List<Screenshot> screenshots) {
	}

	@Override
	public void setShortDescription(String shortDescription) {
	}

	@Override
	public void setTags(List<String> tags) {
	}

	@Override
	public void setTypes(List<String> types) {
	}

	private final Bundle _bundle;
	private final Dictionary<String, String> _headers;
	private final PortletApp _portletApp;
	private final Version _version;

}