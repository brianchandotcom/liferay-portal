/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace;

import aQute.bnd.version.Version;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.portal.tools.bundle.support.commands.DownloadCommand;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

/**
 * @author Drew Brokke
 * @author Simon Jiang
 */
public class LiferayWorkspaceNodePlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new LiferayWorkspaceNodePlugin();

	public static final String NODE_LTS_PROPERTY_NAME =
		WorkspacePlugin.PROPERTY_PREFIX + "node.lts.codename";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		_configureLTS(project);
	}

	private LiferayWorkspaceNodePlugin() {
		DownloadCommand downloadCommand = new DownloadCommand();

		downloadCommand.setCacheDir(
			new File(
				System.getProperty("user.home"), _DEFAULT_NODE_CACHE_DIR_NAME));
		downloadCommand.setConnectionTimeout(5 * 1000);
		downloadCommand.setPassword(null);
		downloadCommand.setQuiet(true);
		downloadCommand.setToken(false);
		downloadCommand.setUserName(null);

		try {
			downloadCommand.setUrl(new URL(_PRODUCT_NODE_URL));

			downloadCommand.execute();
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to get node version", exception.getCause());
		}

		try (JsonReader jsonReader = new JsonReader(
				Files.newBufferedReader(downloadCommand.getDownloadPath()))) {

			Gson gson = new Gson();

			TypeToken<List<NodeInfo>> typeToken =
				new TypeToken<List<NodeInfo>>() {
				};

			_nodeInfos = gson.fromJson(jsonReader, typeToken.getType());
		}
		catch (IOException ioException) {
			throw new GradleException(
				"Could not read downloaded file", ioException.getCause());
		}
	}

	private void _configureLTS(Project project) {
		_getLTSNodeInfoOptional(
			project
		).ifPresent(
			nodeInfo -> {
				NodeExtension nodeExtension = GradleUtil.getExtension(
					project, NodeExtension.class);

				String nodeVersion = nodeInfo.getNodeVersion();
				String npmVersion = nodeInfo.getNpmVersion();

				Logger logger = project.getLogger();

				if (logger.isInfoEnabled()) {
					String lts = nodeInfo.getLts();

					logger.info(
						"Using {} LTS Node version: {}", StringUtil.quote(lts),
						nodeVersion);
					logger.info(
						"Using {} LTS NPM version: {}", StringUtil.quote(lts),
						npmVersion);
				}

				nodeExtension.setNodeVersion(nodeVersion);
				nodeExtension.setNpmVersion(npmVersion);
			}
		);
	}

	private String _getLts(Project project) {
		return GradleUtil.getProperty(
			project, NODE_LTS_PROPERTY_NAME, (String)null);
	}

	private Optional<NodeInfo> _getLTSNodeInfoOptional(Project project) {
		String lts = _getLts(project);

		if (Validator.isNull(lts)) {
			return Optional.empty();
		}

		Optional<NodeInfo> nodeInfoOptional = _nodeInfos.stream(
		).filter(
			nodeInfo -> Objects.equals(nodeInfo.getLts(), lts)
		).max(
			(first, second) -> {
				Version firstVersion = Version.parseVersion(
					first.getNodeVersion());
				Version secondVersion = Version.parseVersion(
					second.getNodeVersion());

				return firstVersion.compareTo(secondVersion);
			}
		);

		if (!nodeInfoOptional.isPresent()) {
			Logger logger = project.getLogger();

			if (logger.isErrorEnabled()) {
				logger.error(
					"Property {} must be one of: {}",
					StringUtil.quote(NODE_LTS_PROPERTY_NAME),
					_nodeInfos.stream(
					).map(
						NodeInfo::getLts
					).distinct(
					).filter(
						nodeInfoLts -> !Objects.equals(nodeInfoLts, "false")
					).sorted(
					).collect(
						Collectors.joining(", ")
					));
			}
		}

		return nodeInfoOptional;
	}

	private static final String _DEFAULT_NODE_CACHE_DIR_NAME = ".liferay/node";

	private static final String _PRODUCT_NODE_URL =
		"https://nodejs.org/dist/index.json";

	private final List<NodeInfo> _nodeInfos;

	private static class NodeInfo {

		public String getLts() {
			return _lts;
		}

		public String getNodeVersion() {
			return _nodeVersion.substring(1);
		}

		public String getNpmVersion() {
			return _npmVersion;
		}

		@SerializedName("lts")
		private String _lts;

		@SerializedName("version")
		private String _nodeVersion;

		@SerializedName("npm")
		private String _npmVersion;

	}

}