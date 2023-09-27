/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(enabled = true, service = {})
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
				_getElasticsearchInstancePaths(), processExecutor,
				new ProcessExecutorPathsImpl(props), this);

			ElasticsearchConnectionBuilder elasticsearchConnectionBuilder =
				new ElasticsearchConnectionBuilder();

			elasticsearchConnectionBuilder.active(
				true
			).connectionId(
				ConnectionConstants.SIDECAR_CONNECTION_ID
			).maxConnections(
				elasticsearchConfigurationWrapper.maxConnections()
			).maxConnectionsPerRoute(
				elasticsearchConfigurationWrapper.maxConnectionsPerRoute()
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

	protected boolean isStartupSuccessful() {
		return _startupSuccessful;
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

	private ElasticsearchInstancePaths _getElasticsearchInstancePaths() {
		ElasticsearchInstancePathsBuilder elasticsearchInstancePathsBuilder =
			new ElasticsearchInstancePathsBuilder();

		Path workPath = Paths.get(props.get(PropsKeys.LIFERAY_HOME));

		Path dataPath = workPath.resolve("data/elasticsearch7");

		return elasticsearchInstancePathsBuilder.dataPath(
			dataPath
		).homePath(
			_resolveHomePath(workPath)
		).workPath(
			workPath
		).build();
	}

	private Path _resolveHomePath(Path path) {
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

	private static final Log _log = LogFactoryUtil.getLog(SidecarManager.class);

	private Sidecar _sidecar;
	private boolean _startupSuccessful;

}