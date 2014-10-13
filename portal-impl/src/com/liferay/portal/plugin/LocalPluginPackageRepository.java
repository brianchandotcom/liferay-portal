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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.PluginPackageNameAndContextComparator;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class LocalPluginPackageRepository {

	public LocalPluginPackageRepository() {
	}

	public void addPluginPackage(PluginPackage pluginPackage) {
		if (pluginPackage.getContext() == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Plugin package cannot be registered because it does not " +
						"have an installation context");
			}

			return;
		}

		_pendingPackages.remove(pluginPackage.getContext());
		_pendingPackages.remove(pluginPackage.getModuleId());

		_pluginPackages.remove(pluginPackage.getContext());
		_pluginPackages.put(pluginPackage.getContext(), pluginPackage);
	}

	public PluginPackage getInstallingPluginPackage(String context) {
		return _pendingPackages.get(context);
	}

	public PluginPackage getLatestPluginPackage(
		String groupId, String artifactId) {

		PluginPackage latestPluginPackage = null;

		for (PluginPackage pluginPackage : _pluginPackages.values()) {
			if (pluginPackage.getGroupId().equals(groupId) &&
				pluginPackage.getArtifactId().equals(artifactId) &&
				((latestPluginPackage == null) ||
				 pluginPackage.isLaterVersionThan(latestPluginPackage))) {

				latestPluginPackage = pluginPackage;
			}
		}

		return latestPluginPackage;
	}

	public PluginPackage getPluginPackage(String context) {
		return _pluginPackages.get(context);
	}

	public List<PluginPackage> getPluginPackages() {
		return new ArrayList<PluginPackage>(_pluginPackages.values());
	}

	public List<PluginPackage> getPluginPackages(
		String groupId, String artifactId) {

		List<PluginPackage> pluginPackages = new ArrayList<PluginPackage>();

		for (PluginPackage pluginPackage : _pluginPackages.values()) {
			if (pluginPackage.getGroupId().equals(groupId) &&
				pluginPackage.getArtifactId().equals(artifactId)) {

				pluginPackages.add(pluginPackage);
			}
		}

		return pluginPackages;
	}

	public List<PluginPackage> getSortedPluginPackages() {
		List<PluginPackage> pluginPackages = new ArrayList<PluginPackage>();

		pluginPackages.addAll(_pluginPackages.values());

		return ListUtil.sort(
			pluginPackages, new PluginPackageNameAndContextComparator());
	}

	public void registerPluginPackageInstallation(PluginPackage pluginPackage) {
		if (pluginPackage.getContext() != null) {
			PluginPackage previousPluginPackage = _pluginPackages.get(
				pluginPackage.getContext());

			if (previousPluginPackage == null) {
				addPluginPackage(pluginPackage);
			}
		}

		String key = pluginPackage.getContext();

		if (key == null) {
			key = pluginPackage.getModuleId();
		}

		_pendingPackages.put(key, pluginPackage);
	}

	public void registerPluginPackageInstallation(String deploymentContext) {
		PluginPackage pluginPackage = getPluginPackage(deploymentContext);

		if (pluginPackage == null) {
			String moduleId =
				deploymentContext + StringPool.SLASH + deploymentContext +
					StringPool.SLASH + Version.UNKNOWN + StringPool.SLASH +
						"war";

			pluginPackage = new PluginPackageImpl(moduleId);

			pluginPackage.setName(deploymentContext);
			pluginPackage.setContext(deploymentContext);
		}

		registerPluginPackageInstallation(pluginPackage);
	}

	public void removePluginPackage(PluginPackage pluginPackage)
		throws PortalException {

		_pluginPackages.remove(pluginPackage.getContext());

		Indexer indexer = IndexerRegistryUtil.getIndexer(PluginPackage.class);

		indexer.delete(pluginPackage);
	}

	public void removePluginPackage(String context) {
		_pluginPackages.remove(context);
	}

	public void unregisterPluginPackageInstallation(String context) {
		_pluginPackages.remove(context);
		_pendingPackages.remove(context);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalPluginPackageRepository.class);

	private final Map<String, PluginPackage> _pendingPackages =
		new HashMap<String, PluginPackage>();
	private final Map<String, PluginPackage> _pluginPackages =
		new HashMap<String, PluginPackage>();

}