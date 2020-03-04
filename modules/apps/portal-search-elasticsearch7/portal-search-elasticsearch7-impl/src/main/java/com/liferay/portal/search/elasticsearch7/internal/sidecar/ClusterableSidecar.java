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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.cluster.ClusterModule;
import org.elasticsearch.cluster.coordination.CoordinationMetaData;
import org.elasticsearch.cluster.metadata.Manifest;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.env.NodeEnvironment;
import org.elasticsearch.gateway.MetaDataStateFormat;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Tina Tian
 */
public class ClusterableSidecar
	extends Sidecar implements IdentifiableOSGiService {

	public ClusterableSidecar(
		ElasticsearchConfiguration elasticsearchConfiguration,
		ClusterExecutor clusterExecutor,
		ClusterMasterExecutor clusterMasterExecutor, JSONFactory jsonFactory,
		ProcessExecutor processExecutor, Props props) {

		super(elasticsearchConfiguration, processExecutor, props);

		_clusterExecutor = clusterExecutor;
		_clusterMasterExecutor = clusterMasterExecutor;
		_jsonFactory = jsonFactory;

		ClusterNode clusterNode = _clusterExecutor.getLocalClusterNode();

		_nodeName = StringBundler.concat(
			super.getNodeName(), StringPool.DASH,
			clusterNode.getClusterNodeId());
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return ClusterableSidecar.class.getName();
	}

	@Override
	public void start() {
		_initialMasterNodeTransportAddress = _getMasterNodeTransportAddress();

		if (_initialMasterNodeTransportAddress == null) {
			try {
				_cleanUpClusterMetaData();
			}
			catch (Exception exception) {
				_log.error("Unable to clean up cluster meta data", exception);
			}
		}
		else {
			deleteDir(getDataHome());
		}

		super.start();

		RestClientBuilder restClientBuilder = RestClient.builder(
			HttpHost.create(getNetworkHostAddress()));

		_restClient = restClientBuilder.build();

		_clusterEventListener = new SidecarClusterEventListener();

		_clusterExecutor.addClusterEventListener(_clusterEventListener);

		_startCountDownLatch.countDown();
	}

	@Override
	public void stop() {
		if (_restClient != null) {
			try {
				while (!_isOneNodeCluster()) {
					if (!_nodeName.equals(_getMasterNodeId())) {
						break;
					}

					_restClient.performRequest(
						new Request(
							"POST",
							"_cluster/voting_config_exclusions/" + _nodeName));
				}
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to add master node to voting exclusions",
						ioException);
				}
			}

			try {
				_restClient.close();
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to close rest client", ioException);
				}
			}

			_restClient = null;
		}

		_clusterExecutor.removeClusterEventListener(_clusterEventListener);

		super.stop();

		_stopCountDownLatch.countDown();
	}

	protected String getLogProperties() {
		return StringBundler.concat(
			"logger.cluster.name=org.elasticsearch.cluster\n",
			"logger.cluster.level=error\n",
			"logger.transport.name=org.elasticsearch.transport\n",
			"logger.transport.level=error\n");
	}

	@Override
	protected String getNodeName() {
		return _nodeName;
	}

	@Override
	protected void setClusterDiscoverySettings(
		SettingsBuilder settingsBuilder) {

		ClusterNode clusterNode = _clusterExecutor.getLocalClusterNode();

		InetAddress inetAddress = clusterNode.getBindInetAddress();

		if (!inetAddress.isLoopbackAddress()) {
			settingsBuilder.put("network.host", inetAddress.getHostAddress());
		}

		if (_initialMasterNodeTransportAddress == null) {
			super.setClusterDiscoverySettings(settingsBuilder);
		}
		else {
			settingsBuilder.put(
				"discovery.seed_hosts", _initialMasterNodeTransportAddress);
		}
	}

	private static String _getMasterNodeTransportAddress(
			String osgiServiceIdentifier)
		throws Exception {

		ClusterableSidecar clusterableSidecar =
			(ClusterableSidecar)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		RestClient restClient = clusterableSidecar._restClient;
		JSONFactory jsonFactory = clusterableSidecar._jsonFactory;

		Response response = restClient.performRequest(
			new Request(
				"GET", "_nodes/_master?filter_path=nodes.*.transport_address"));

		JSONObject responseJSONObject = jsonFactory.createJSONObject(
			EntityUtils.toString(response.getEntity()));

		JSONObject nodesJSONObject = responseJSONObject.getJSONObject("nodes");

		Iterator<String> iterator = nodesJSONObject.keys();

		JSONObject jsonObject = nodesJSONObject.getJSONObject(iterator.next());

		return GetterUtil.getString(jsonObject.get("transport_address"));
	}

	private static void _syncStart() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(ClusterableSidecar.class);

		ServiceTracker<ClusterableSidecar, ClusterableSidecar> serviceTracker =
			ServiceTrackerFactory.open(
				bundle.getBundleContext(), ClusterableSidecar.class, null);

		try {
			SidecarComponentUtil.enableSidecarElasticsearchConnectionManager();

			ClusterableSidecar clusterableSidecar =
				serviceTracker.waitForService(0);

			CountDownLatch countDownLatch =
				clusterableSidecar._startCountDownLatch;

			countDownLatch.await();
		}
		finally {
			serviceTracker.close();
		}
	}

	private static void _syncStop(String osgiServiceIdentifier)
		throws Exception {

		SidecarComponentUtil.disableSidecarElasticsearchConnectionManager();

		ClusterableSidecar clusterableSidecar =
			(ClusterableSidecar)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		CountDownLatch countDownLatch = clusterableSidecar._stopCountDownLatch;

		countDownLatch.await();
	}

	private void _cleanUpClusterMetaData() throws Exception {
		Path nodePath = NodeEnvironment.resolveNodePath(getPathData(), 0);

		Path statePath = nodePath.resolve(MetaDataStateFormat.STATE_DIR_NAME);

		if (Files.notExists(statePath)) {
			return;
		}

		MetaDataStateFormat<MetaData> metaDataMetaDataStateFormat =
			MetaData.FORMAT;
		MetaDataStateFormat<Manifest> manifestMetaDataStateFormat =
			Manifest.FORMAT;

		Path globalPath = null;
		Path manifestPath = null;

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				statePath)) {

			Iterator<Path> iterator = directoryStream.iterator();

			while (iterator.hasNext()) {
				Path path = iterator.next();

				String fileName = String.valueOf(path.getFileName());

				if (fileName.startsWith(
						metaDataMetaDataStateFormat.getPrefix())) {

					globalPath = path;
				}
				else if (fileName.startsWith(
							manifestMetaDataStateFormat.getPrefix())) {

					manifestPath = path;
				}
			}
		}

		if ((globalPath == null) || (manifestPath == null)) {
			return;
		}

		NamedXContentRegistry namedXContentRegistry = new NamedXContentRegistry(
			ClusterModule.getNamedXWriteables());

		MetaData metaData = metaDataMetaDataStateFormat.read(
			namedXContentRegistry, globalPath);

		CoordinationMetaData coordinationMetaData =
			metaData.coordinationMetaData();

		CoordinationMetaData.Builder coordinationMetaDataBuilder =
			CoordinationMetaData.builder();

		coordinationMetaDataBuilder.term(coordinationMetaData.term());

		MetaData.Builder metaDataBuilder = MetaData.builder(metaData);

		metaDataBuilder.coordinationMetaData(
			coordinationMetaDataBuilder.build());

		Manifest manifest = manifestMetaDataStateFormat.read(
			namedXContentRegistry, manifestPath);

		manifestMetaDataStateFormat.write(
			new Manifest(
				manifest.getCurrentTerm(), manifest.getClusterStateVersion(),
				metaDataMetaDataStateFormat.write(
					metaDataBuilder.build(), nodePath),
				manifest.getIndexGenerations()),
			nodePath);
	}

	private String _getMasterNodeId() {
		try {
			Response response = _restClient.performRequest(
				new Request("GET", "_cat/master?h=node"));

			return StringUtil.removeSubstring(
				EntityUtils.toString(response.getEntity()),
				StringPool.NEW_LINE);
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find master node in elasticsearch cluster");
			}

			return null;
		}
	}

	private String _getMasterNodeTransportAddress() {
		if (_isOneNodeCluster()) {
			return null;
		}

		FutureClusterResponses futureClusterResponses =
			_clusterExecutor.execute(
				ClusterRequest.createMulticastRequest(
					new MethodHandler(
						_getMasterNodeTransportAddressMethodKey,
						getOSGiServiceIdentifier()),
					true));

		BlockingQueue<ClusterNodeResponse> blockingQueue =
			futureClusterResponses.getPartialResults();

		try {
			ClusterNodeResponse clusterNodeResponse = blockingQueue.take();

			return (String)clusterNodeResponse.getResult();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get master node transport address, will " +
						"create a new elasticsearch cluster",
					exception);
			}

			return null;
		}
	}

	private boolean _isOneNodeCluster() {
		List<ClusterNode> clusterNodes = _clusterExecutor.getClusterNodes();

		if (clusterNodes.size() == 1) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterableSidecar.class);

	private static final MethodKey _getMasterNodeTransportAddressMethodKey =
		new MethodKey(
			ClusterableSidecar.class, "_getMasterNodeTransportAddress",
			String.class);
	private static final MethodKey _syncStartMethodKey = new MethodKey(
		ClusterableSidecar.class, "_syncStart");
	private static final MethodKey _syncStopMethodKey = new MethodKey(
		ClusterableSidecar.class, "_syncStop", String.class);

	private ClusterEventListener _clusterEventListener;
	private final ClusterExecutor _clusterExecutor;
	private final ClusterMasterExecutor _clusterMasterExecutor;
	private String _initialMasterNodeTransportAddress;
	private final JSONFactory _jsonFactory;
	private final String _nodeName;
	private RestClient _restClient;
	private final CountDownLatch _startCountDownLatch = new CountDownLatch(1);
	private final CountDownLatch _stopCountDownLatch = new CountDownLatch(1);

	private class SidecarClusterEventListener implements ClusterEventListener {

		@Override
		public void processClusterEvent(ClusterEvent clusterEvent) {
			if (!_clusterMasterExecutor.isMaster() ||
				(clusterEvent.getClusterEventType() !=
					ClusterEventType.DEPART)) {

				return;
			}

			String address = getNetworkHostAddress();

			if (!_isAddressReachable(address)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to connect to sidecar at ", address,
							", there might be a network issue causing cluster ",
							"node leaving, skip restarting and wait for ",
							"sidecar recovering by itself when network issue ",
							"is fixed"));
				}

				return;
			}

			String masterNodeId = _getMasterNodeId();

			if (masterNodeId != null) {
				for (ClusterNode clusterNode : clusterEvent.getClusterNodes()) {
					if (masterNodeId.endsWith(clusterNode.getClusterNodeId())) {
						masterNodeId = null;

						break;
					}
				}

				if (masterNodeId != null) {
					return;
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to elect new master node in elasticsearch " +
						"cluster when node leaving, indexes may be broken, " +
							"will remove all data and restart");
			}

			try {
				_syncStop(getOSGiServiceIdentifier());

				if (!_isOneNodeCluster()) {
					FutureClusterResponses futureClusterResponses =
						_clusterExecutor.execute(
							ClusterRequest.createMulticastRequest(
								new MethodHandler(
									_syncStopMethodKey,
									getOSGiServiceIdentifier()),
								true));

					futureClusterResponses.get();
				}

				deleteDir(getDataHome());

				_syncStart();

				if (!_isOneNodeCluster()) {
					FutureClusterResponses futureClusterResponses =
						_clusterExecutor.execute(
							ClusterRequest.createMulticastRequest(
								new MethodHandler(_syncStartMethodKey), true));

					futureClusterResponses.get();
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to restart sidecar", exception);
				}
			}
		}

		private boolean _isAddressReachable(String address) {
			int index = address.indexOf(CharPool.COLON);

			if (index == -1) {
				throw new IllegalStateException(
					"Unable to parse address " + address);
			}

			try (Socket socket = new Socket()) {
				socket.connect(
					new InetSocketAddress(
						InetAddress.getByName(address.substring(0, index)),
						GetterUtil.getIntegerStrict(
							address.substring(index + 1))));

				return true;
			}
			catch (IOException ioException) {
				return false;
			}
		}

	}

}