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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.OperationModeResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchInstancePaths;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchInstancePathsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.constants.ConnectionConstants;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.SidecarVersionConstants;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch7.settings.SettingsContributor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Tina Tian
 */
@Component(enabled = true, immediate = true, service = {})
public class SidecarManager implements ElasticsearchConfigurationObserver {

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return elasticsearchConfigurationWrapper.compare(
			this, elasticsearchConfigurationObserver);
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		applyConfigurations();
	}

	@Activate
	protected void activate() {
		elasticsearchConfigurationWrapper.register(this);

		applyConfigurations();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(operation.mode=SIDECAR)"
	)
	protected void addSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.add(settingsContributor);
	}

	protected void applyConfigurations() {
		if (operationModeResolver.isProductionModeEnabled()) {
			elasticsearchConnectionManager.removeElasticsearchConnection(
				ConnectionConstants.SIDECAR_CONNECTION_ID);
		}
		else {
			_startupSuccessful = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Liferay automatically starts a child process of ",
						"Elasticsearch named sidecar for convenient ",
						"development and demonstration purposes. Do NOT use ",
						"sidecar in production. Refer to the documentation ",
						"for details on the limitations of sidecar and ",
						"instructions on configuring a remote Elasticsearch ",
						"connection in the Control Panel."));
			}

			if (_sidecar != null) {
				_sidecar.stop();
			}

			_sidecar = new Sidecar(
				clusterExecutor, elasticsearchConfigurationWrapper,
				getElasticsearchInstancePaths(), processExecutor,
				new ProcessExecutorPathsImpl(props), _settingsContributors,
				this);

			ElasticsearchConnectionBuilder elasticsearchConnectionBuilder =
				new ElasticsearchConnectionBuilder();

			elasticsearchConnectionBuilder.active(
				true
			).connectionId(
				ConnectionConstants.SIDECAR_CONNECTION_ID
			).postCloseRunnable(
				_sidecar::stop
			).preConnectElasticsearchConnectionConsumer(
				elasticsearchConnection -> {
					_sidecar.start();

					elasticsearchConnection.setNetworkHostAddresses(
						new String[] {_sidecar.getNetworkHostAddress()});
				}
			);

			elasticsearchConnectionManager.addElasticsearchConnection(
				elasticsearchConnectionBuilder.build());

			_startupSuccessful = true;
		}
	}

	@Deactivate
	protected void deactivate() {
		elasticsearchConfigurationWrapper.unregister(this);
	}

	protected ElasticsearchInstancePaths getElasticsearchInstancePaths() {
		ElasticsearchInstancePathsBuilder elasticsearchInstancePathsBuilder =
			new ElasticsearchInstancePathsBuilder();

		Path workPath = Paths.get(props.get(PropsKeys.LIFERAY_HOME));

		Path dataPath = workPath.resolve("data/elasticsearch7");

		return elasticsearchInstancePathsBuilder.dataPath(
			dataPath
		).homePath(
			resolveHomePath(workPath)
		).workPath(
			workPath
		).build();
	}

	protected boolean isStartupSuccessful() {
		return _startupSuccessful;
	}

	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.remove(settingsContributor);
	}

	protected Path resolveHomePath(Path path) {
		String sidecarHome = elasticsearchConfigurationWrapper.sidecarHome();

		if (sidecarHome.equals("elasticsearch-sidecar")) {
			String versionNumber = ResourceUtil.getResourceAsString(
				getClass(), SidecarVersionConstants.SIDECAR_VERSION_FILE_NAME);

			sidecarHome = sidecarHome + "/" + versionNumber;
		}

		Path relativeSidecarHomePath = path.resolve(sidecarHome);

		if (!Files.isDirectory(relativeSidecarHomePath)) {
			Path absoluteSidecarHomePath = Paths.get(sidecarHome);

			if (Files.isDirectory(absoluteSidecarHomePath)) {
				return absoluteSidecarHomePath;
			}
		}

		return relativeSidecarHomePath;
	}

	@Reference
	protected ClusterExecutor clusterExecutor;

	@Reference
	protected volatile ElasticsearchConfigurationWrapper
		elasticsearchConfigurationWrapper;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected OperationModeResolver operationModeResolver;

	@Reference
	protected ProcessExecutor processExecutor;

	@Reference
	protected Props props;

	private static final Log _log = LogFactoryUtil.getLog(SidecarManager.class);

	private final Set<SettingsContributor> _settingsContributors =
		new ConcurrentSkipListSet<>();
	private Sidecar _sidecar;
	private boolean _startupSuccessful;

}