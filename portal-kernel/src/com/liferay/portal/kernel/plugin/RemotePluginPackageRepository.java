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

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jorge Ferrer
 */
public class RemotePluginPackageRepository {

	public static final String LOCAL_URL = "LOCAL_URL";

	public static final String SETTING_USE_DOWNLOAD_URL = "use-download-url";

	public RemotePluginPackageRepository(String repositoryURL) {
		_repositoryURL = repositoryURL;
	}

	public void addPluginPackage(PluginPackage pluginPackage) {

		// Avoid duplicates

		PluginPackage existingPackage = _moduleIdIndex.get(
			pluginPackage.getModuleId());

		if (existingPackage != null) {
			return;
		}

		_artifactURLIndex.put(pluginPackage.getArtifactURL(), pluginPackage);
		_moduleIdIndex.put(pluginPackage.getModuleId(), pluginPackage);
		_addToGroupAndArtifactIndex(
			pluginPackage.getGroupId(), pluginPackage.getArtifactId(),
			pluginPackage);
		_pluginPackages.add(pluginPackage);
		_tags.addAll(pluginPackage.getTags());
	}

	public PluginPackage findPluginByArtifactURL(String artifactURL) {
		return _artifactURLIndex.get(artifactURL);
	}

	public PluginPackage findPluginPackageByModuleId(String moduleId) {
		return _moduleIdIndex.get(moduleId);
	}

	public List<PluginPackage> findPluginsByGroupIdAndArtifactId(
		String groupId, String artifactId) {

		return _groupAndArtifactIndex.get(
			groupId + StringPool.SLASH + artifactId);
	}

	public List<PluginPackage> getPluginPackages() {
		return _pluginPackages;
	}

	public String getRepositoryURL() {
		return _repositoryURL;
	}

	public Properties getSettings() {
		return _settings;
	}

	public Set<String> getTags() {
		return _tags;
	}

	public void removePlugin(PluginPackage pluginPackage) {
		_artifactURLIndex.remove(pluginPackage.getArtifactURL());
		_moduleIdIndex.remove(pluginPackage.getModuleId());
		_removeFromGroupAndArtifactIndex(
			pluginPackage.getGroupId(), pluginPackage.getArtifactId(),
			pluginPackage.getModuleId());
		_pluginPackages.remove(pluginPackage);
	}

	public void setSettings(Properties settings) {
		_settings = settings;
	}

	private void _addToGroupAndArtifactIndex(
		String groupId, String artifactId, PluginPackage pluginPackage) {

		List<PluginPackage> pluginPackages = findPluginsByGroupIdAndArtifactId(
			groupId, artifactId);

		if (pluginPackages == null) {
			pluginPackages = new ArrayList<>();

			_groupAndArtifactIndex.put(
				groupId + StringPool.SLASH + artifactId, pluginPackages);
		}

		pluginPackages.add(pluginPackage);
	}

	private void _removeFromGroupAndArtifactIndex(
		String groupId, String artifactId, String moduleId) {

		List<PluginPackage> pluginPackages = findPluginsByGroupIdAndArtifactId(
			groupId, artifactId);

		if (pluginPackages != null) {
			Iterator<PluginPackage> itr = pluginPackages.iterator();

			while (itr.hasNext()) {
				PluginPackage pluginPackage = itr.next();

				String pluginPackageModuleId = pluginPackage.getModuleId();

				if (pluginPackageModuleId.equals(moduleId)) {
					itr.remove();

					break;
				}
			}
		}
	}

	private final Map<String, PluginPackage> _artifactURLIndex =
		new HashMap<>();
	private final Map<String, List<PluginPackage>> _groupAndArtifactIndex =
		new HashMap<>();
	private final Map<String, PluginPackage> _moduleIdIndex = new HashMap<>();
	private final List<PluginPackage> _pluginPackages = new ArrayList<>();
	private final String _repositoryURL;
	private Properties _settings;
	private final Set<String> _tags = new TreeSet<>();

}