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

package com.liferay.portal.cache.multiple.internal.bootstrap;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.cache.multiple.internal.PortalCacheManagerUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.AnnotatedObjectInputStream;
import com.liferay.portal.kernel.io.AnnotatedObjectOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SocketUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.nio.channels.ServerSocketChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 * @author Sherry Yang
 */
@Component(enabled = false, immediate = true, service = {})
public class ClusterLinkBootstrapLoaderHelperUtil {

	public static SocketAddress createServerSocketFromCluster(
			String portalCacheManagerName, List<String> portalCacheNames)
		throws Exception {

		ClusterNode localClusterNode =
			ClusterExecutorUtil.getLocalClusterNode();

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				localClusterNode.getBindInetAddress(),
				GetterUtil.getInteger(
					PropsUtil.get(PropsKeys.EHCACHE_SOCKET_START_PORT), 32454),
				_serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		ClusterLinkBootstrapLoaderServerThread
			clusterLinkBootstrapLoaderServerThread =
				new ClusterLinkBootstrapLoaderServerThread(
					serverSocket, portalCacheManagerName, portalCacheNames);

		clusterLinkBootstrapLoaderServerThread.start();

		return serverSocket.getLocalSocketAddress();
	}

	public static boolean isSkipped() {
		return _skipBootstrapLoaderThreadLocal.get();
	}

	public static void loadCachesFromCluster(
			String portalCacheManagerName, String... portalCacheNames)
		throws Exception {

		synchronized (ClusterLinkBootstrapLoaderHelperUtil.class) {
			if (!_started) {
				List<String> portalCaches = _deferredPortalCaches.get(
					portalCacheManagerName);

				if (portalCaches == null) {
					portalCaches = new ArrayList<>();

					_deferredPortalCaches.put(
						portalCacheManagerName, portalCaches);
				}

				Collections.addAll(portalCaches, portalCacheNames);

				return;
			}
		}

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		if (_log.isInfoEnabled()) {
			_log.info("Cluster nodes " + clusterNodes);
		}

		if (clusterNodes.size() <= 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not loading cache from cluster because a cluster peer " +
						"was not found");
			}

			return;
		}

		PortalCacheManager<? extends Serializable, ?> portalCacheManager =
			PortalCacheManagerUtil.getPortalCacheManager(
				portalCacheManagerName);

		if (!portalCacheManager.isClusterAware()) {
			return;
		}

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_createServerSocketFromClusterMethodKey, portalCacheManagerName,
				Arrays.asList(portalCacheNames)),
			true);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		BlockingQueue<ClusterNodeResponse> clusterNodeResponses =
			futureClusterResponses.getPartialResults();

		ClusterNodeResponse clusterNodeResponse = null;

		try {
			clusterNodeResponse = clusterNodeResponses.poll(
				GetterUtil.getLong(
					PropsUtil.get(
						PropsKeys.CLUSTER_LINK_NODE_BOOTUP_RESPONSE_TIMEOUT),
					10000),
				TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException ie) {
			return;
		}

		if (clusterNodeResponse == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to load cache from the cluster because there was " +
						"no peer response");
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Load cache data from cluster node " +
					clusterNodeResponse.getClusterNode());
		}

		Socket socket = null;

		try {
			SocketAddress remoteSocketAddress =
				(SocketAddress)clusterNodeResponse.getResult();

			if (remoteSocketAddress == null) {
				_log.error(
					"Cluster peer " + clusterNodeResponse.getClusterNode() +
						" responded with a null socket address");

				return;
			}

			socket = new Socket();

			socket.connect(remoteSocketAddress);

			socket.shutdownOutput();

			ObjectInputStream objectInputStream =
				new AnnotatedObjectInputStream(socket.getInputStream());

			PortalCache<Serializable, Serializable> portalCache = null;

			try {
				while (true) {
					Object object = objectInputStream.readObject();

					if (object instanceof CacheElement) {
						CacheElement cacheElement = (CacheElement)object;

						PortalCacheHelperUtil.putWithoutReplicator(
							portalCache, cacheElement.getKey(),
							cacheElement.getValue());
					}
					else if (object instanceof String) {
						if (_COMMAND_SOCKET_CLOSE.equals(object)) {
							break;
						}

						_skipBootstrapLoaderThreadLocal.set(Boolean.TRUE);

						try {
							portalCache =
								(PortalCache<Serializable, Serializable>)
									portalCacheManager.getPortalCache(
										(String)object);
						}
						finally {
							_skipBootstrapLoaderThreadLocal.remove();
						}
					}
					else {
						throw new SystemException(
							"Socket input stream returned invalid object " +
								object);
					}
				}
			}
			finally {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			}
		}
		catch (Exception e) {
			throw new Exception(
				"Unable to load cache data from cluster node " +
					clusterNodeResponse.getClusterNode(),
				e);
		}
		finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

	@Activate
	protected void activate() {
		if (!_started) {
			_started = true;
		}

		if (_deferredPortalCaches.isEmpty()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading deferred caches");
		}

		try {
			for (Map.Entry<String, List<String>> entry :
					_deferredPortalCaches.entrySet()) {

				List<String> portalCacheNames = entry.getValue();

				if (portalCacheNames.isEmpty()) {
					continue;
				}

				loadCachesFromCluster(
					entry.getKey(), portalCacheNames.toArray(new String[0]));
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to load cache data from the cluster", e);
			}
		}
		finally {
			_deferredPortalCaches.clear();
		}
	}

	private static final String _COMMAND_SOCKET_CLOSE = "${SOCKET_CLOSE}";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkBootstrapLoaderHelperUtil.class);

	private static final MethodKey _createServerSocketFromClusterMethodKey =
		new MethodKey(
			ClusterLinkBootstrapLoaderHelperUtil.class,
			"createServerSocketFromCluster", String.class, List.class);
	private static final Map<String, List<String>> _deferredPortalCaches =
		new HashMap<>();
	private static final SocketUtil.ServerSocketConfigurator
		_serverSocketConfigurator = new SocketCacheServerSocketConfiguration();
	private static final ThreadLocal<Boolean> _skipBootstrapLoaderThreadLocal =
		new CentralizedThreadLocal<>(
			ClusterLinkBootstrapLoaderHelperUtil.class +
				"._skipBootstrapLoaderThreadLocal",
			() -> Boolean.FALSE, false);
	private static boolean _started;

	private static class CacheElement implements Serializable {

		public CacheElement(Serializable key, Serializable value) {
			_key = key;
			_value = value;
		}

		public Serializable getKey() {
			return _key;
		}

		public Serializable getValue() {
			return _value;
		}

		private final Serializable _key;
		private final Serializable _value;

	}

	private static class ClusterLinkBootstrapLoaderServerThread extends Thread {

		public ClusterLinkBootstrapLoaderServerThread(
			ServerSocket serverSocket, String portalCacheManagerName,
			List<String> portalCacheNames) {

			_serverSocket = serverSocket;
			_portalCacheManagerName = portalCacheManagerName;
			_portalCacheNames = portalCacheNames;

			setDaemon(true);
			setName(
				ClusterLinkBootstrapLoaderServerThread.class.getName() + " - " +
					portalCacheNames);
			setPriority(Thread.NORM_PRIORITY);
		}

		@Override
		public void run() {
			Socket socket = null;

			try {
				try {
					socket = _serverSocket.accept();
				}
				catch (SocketTimeoutException ste) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Terminating the socket thread " + getName() +
								" that the client requested but never used");
					}

					return;
				}
				finally {
					_serverSocket.close();
				}

				socket.shutdownInput();

				try (OutputStream outputStream = socket.getOutputStream();
					ObjectOutputStream objectOutputStream =
						new AnnotatedObjectOutputStream(outputStream)) {

					PortalCacheManager<? extends Serializable, ?>
						portalCacheManager =
							PortalCacheManagerUtil.getPortalCacheManager(
								_portalCacheManagerName);

					for (String portalCacheName : _portalCacheNames) {
						PortalCache<Serializable, Serializable> portalCache =
							(PortalCache<Serializable, Serializable>)
								portalCacheManager.getPortalCache(
									portalCacheName);

						if (portalCache == null) {
							_skipBootstrapLoaderThreadLocal.set(Boolean.TRUE);

							try {
								portalCacheManager.getPortalCache(
									portalCacheName);
							}
							finally {
								_skipBootstrapLoaderThreadLocal.remove();
							}

							continue;
						}

						objectOutputStream.writeObject(portalCacheName);

						List<Serializable> keys = portalCache.getKeys();

						for (Serializable key : keys) {
							Serializable value = portalCache.get(key);

							if (value != null) {
								CacheElement cacheElement = new CacheElement(
									key, value);

								objectOutputStream.writeObject(cacheElement);
							}
						}
					}

					objectOutputStream.writeObject(_COMMAND_SOCKET_CLOSE);
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				if (socket != null) {
					try {
						socket.close();
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}
			}
		}

		private final String _portalCacheManagerName;
		private final List<String> _portalCacheNames;
		private final ServerSocket _serverSocket;

	}

	private static class SocketCacheServerSocketConfiguration
		implements SocketUtil.ServerSocketConfigurator {

		@Override
		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setSoTimeout(
				GetterUtil.getInteger(
					PropsUtil.get(PropsKeys.EHCACHE_SOCKET_SO_TIMEOUT), 10000));
		}

	}

}