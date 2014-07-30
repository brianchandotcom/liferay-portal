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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.AnnotatedObjectInputStream;
import com.liferay.portal.kernel.io.AnnotatedObjectOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.nio.channels.ServerSocketChannel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 * @author Sherry Yang
 */
public class EhcacheStreamBootstrapHelpUtil {

	public static SocketAddress createServerSocketFromCluster(
			List<String> cacheNames)
		throws Exception {

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				ClusterLinkUtil.getBindInetAddress(),
				PropsValues.EHCACHE_SOCKET_START_PORT,
				_serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		EhcacheStreamServerThread ehcacheStreamServerThread =
			new EhcacheStreamServerThread(
				serverSocket, _getPortalCacheManager(), cacheNames);

		ehcacheStreamServerThread.start();

		return serverSocket.getLocalSocketAddress();
	}

	protected static void loadCachesFromCluster(
			String cacheManagerName, String ... cacheNames)
		throws Exception {

		List<Address> clusterNodeAddresses =
			ClusterExecutorUtil.getClusterNodeAddresses();

		if (_log.isInfoEnabled()) {
			_log.info("Cluster node addresses " + clusterNodeAddresses);
		}

		if (clusterNodeAddresses.size() <= 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not loading cache from cluster because a cluster peer " +
						"was not found");
			}

			return;
		}

		PortalCacheManager<?, ?> portalCacheManager = _getPortalCacheManager();

		if (!cacheManagerName.equals(portalCacheManager.getName())) {
			return;
		}

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_createServerSocketFromClusterMethodKey,
				Arrays.asList(cacheNames)),
			true);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		BlockingQueue<ClusterNodeResponse> clusterNodeResponses =
			futureClusterResponses.getPartialResults();

		ClusterNodeResponse clusterNodeResponse = null;

		try {
			clusterNodeResponse = clusterNodeResponses.poll(
				PropsValues.CLUSTER_LINK_NODE_BOOTUP_RESPONSE_TIMEOUT,
				TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException ie) {
			return;
		}

		if (clusterNodeResponse == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to load cache from the cluster because there " +
						"was no peer response");
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

						portalCache.putQuiet(
							cacheElement.getKey(), cacheElement.getValue());
					}
					else if (object instanceof String) {
						if (_COMMAND_SOCKET_CLOSE.equals(object)) {
							break;
						}

						EhcacheStreamBootstrapCacheLoader.setSkip();

						try {
							portalCache =
								(PortalCache<Serializable, Serializable>)
									portalCacheManager.getCache((String)object);
						}
						finally {
							EhcacheStreamBootstrapCacheLoader.resetSkip();
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

	private static PortalCacheManager<?, ?> _getPortalCacheManager() {
		if (_portalCacheManager == null) {
			_portalCacheManager =
				(PortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
					"com.liferay.portal.kernel.cache." +
						"MultiVMPortalCacheManager");
		}

		return _portalCacheManager;
	}

	private static final String _COMMAND_SOCKET_CLOSE = "${SOCKET_CLOSE}";

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapHelpUtil.class);

	private static MethodKey _createServerSocketFromClusterMethodKey =
		new MethodKey(
			EhcacheStreamBootstrapHelpUtil.class,
			"createServerSocketFromCluster", List.class);
	private static volatile PortalCacheManager<?, ?> _portalCacheManager;
	private static ServerSocketConfigurator _serverSocketConfigurator =
		new SocketCacheServerSocketConfiguration();

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

		private Serializable _key;
		private Serializable _value;

	}

	private static class EhcacheStreamServerThread extends Thread {

		public EhcacheStreamServerThread(
			ServerSocket serverSocket,
			PortalCacheManager<?, ?> portalCacheManager,
			List<String> cacheNames) {

			_serverSocket = serverSocket;
			_portalCacheManager = portalCacheManager;
			_cacheNames = cacheNames;

			setDaemon(true);
			setName(
				EhcacheStreamServerThread.class.getName() + " - " + cacheNames);
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

				ObjectOutputStream objectOutputStream =
					new AnnotatedObjectOutputStream(socket.getOutputStream());

				for (String cacheName : _cacheNames) {
					PortalCache<Serializable, Serializable> portalCache =
						(PortalCache<Serializable, Serializable>)
							_portalCacheManager.getCache(cacheName);

					if (portalCache == null) {
						EhcacheStreamBootstrapCacheLoader.setSkip();

						try {
							_portalCacheManager.getCache(cacheName);
						}
						finally {
							EhcacheStreamBootstrapCacheLoader.resetSkip();
						}

						continue;
					}

					objectOutputStream.writeObject(cacheName);

					List<Serializable> keys = portalCache.getKeys();

					for (Serializable key : keys) {
						Serializable value = portalCache.get(key);

						CacheElement cacheElement = new CacheElement(
							key, value);

						objectOutputStream.writeObject(cacheElement);
					}
				}

				objectOutputStream.writeObject(_COMMAND_SOCKET_CLOSE);

				objectOutputStream.close();
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

		private List<String> _cacheNames;
		private PortalCacheManager<?, ?> _portalCacheManager;
		private ServerSocket _serverSocket;

	}

	private static class SocketCacheServerSocketConfiguration
		implements ServerSocketConfigurator {

		@Override
		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setSoTimeout(PropsValues.EHCACHE_SOCKET_SO_TIMEOUT);
		}

	}

}