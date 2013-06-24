/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

import java.nio.channels.ServerSocketChannel;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author Shuyang Zhou
 * @author Sherry Yang
 */
public class EhcacheStreamBootstrapHelpUtil {

	public static void acquireCachePeers(Ehcache cache) throws Exception {
		List<Address> clusterNodeAddresses =
			ClusterExecutorUtil.getClusterNodeAddresses();

		if (_log.isInfoEnabled()) {
			_log.info("Cluster node addresses: " + clusterNodeAddresses);
		}

		int clusterNodeAddressesCount = clusterNodeAddresses.size();

		if (clusterNodeAddressesCount <= 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Cannot get caches because there is one or less portal " +
						"instances in the cluster");
			}

			return;
		}

		loadCachesFromCluster(cache);
	}

	public static ServerSocket createServerSocket(int startPort)
		throws Exception {

		InetAddress inetAddress = ClusterLinkUtil.getBindInetAddress();

		ServerSocketConfigurator serverSocketConfigurator =
			new SocketCacheServerSocketConfiguration();

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				inetAddress, startPort, serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		return serverSocket;
	}

	public static SocketAddress createServerSocketFromCluster(String cacheName)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Remote node executed");
		}

		ServerSocket serverSocket = createServerSocket(_START_PORT);

		CacheStreamRunnable cacheStreamRunnable = new CacheStreamRunnable(
			serverSocket, cacheName);

		Thread thread = new Thread(cacheStreamRunnable);

		thread.setDaemon(true);
		thread.start();

		return serverSocket.getLocalSocketAddress();
	}

	protected static void loadCachesFromCluster(Ehcache ehcache)
		throws Exception {

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_createServerSocketFromClusterMethodKey, ehcache.getName()),
			true);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		BlockingQueue<ClusterNodeResponse> clusterNodeResponses =
			futureClusterResponses.getPartialResults();

		ClusterNodeResponse clusterNodeResponse = null;

		try {
			clusterNodeResponse = clusterNodeResponses.poll(
				_CLUSTER_LINK_NODE_BOOTUP_RESPONSE_TIMEOUT,
				TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException ie) {
			return;
		}

		if (clusterNodeResponse == null) {
			return;
		}

		ObjectInputStream objectInputStream = null;
		Socket socket = null;

		try {
			SocketAddress remoteSocketAddress =
				(SocketAddress)clusterNodeResponse.getResult();

			if (remoteSocketAddress == null) {
				return;
			}

			socket = new Socket();

			socket.connect(remoteSocketAddress);
			socket.shutdownOutput();

			objectInputStream = new AnnotatedObjectInputStream(
				socket.getInputStream());

			while (true) {
				Object object = objectInputStream.readObject();

				if (object instanceof EhcacheElement) {
					EhcacheElement ehcacheElement = (EhcacheElement)object;

					Element element = ehcacheElement.toElement();

					ehcache.put(element, true);
				}
				else if (object instanceof String) {
					String command = (String)object;

					if (command.equals(_SOCKET_CLOSE)) {
						break;
					}
					else if (command.equals(_CACHE_TX_START)) {
						String cacheName =
							(String)objectInputStream.readObject();

						if (!cacheName.equals(ehcache.getName())) {
							break;
						}
					}
				}
				else {
					throw new SystemException("Socket transaction failed");
				}
			}
		}
		finally {
			if (objectInputStream != null) {
				objectInputStream.close();
			}

			if (socket != null) {
				socket.close();
			}
		}
	}

	private static final String _CACHE_TX_START = "${CACHE_TX_START}";

	private static final int _CLUSTER_LINK_NODE_BOOTUP_RESPONSE_TIMEOUT =
		PropsValues.CLUSTER_LINK_NODE_BOOTUP_RESPONSE_TIMEOUT;

	private static final String _MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private static final int _SO_TIMEOUT =
		PropsValues.EHCACHE_SOCKET_SO_TIMEOUT;

	private static final String _SOCKET_CLOSE = "${SOCKET_CLOSE}";

	private static final int _START_PORT =
		PropsValues.EHCACHE_SOCKET_START_PORT;

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapHelpUtil.class);

	private static MethodKey _createServerSocketFromClusterMethodKey =
		new MethodKey(
			EhcacheStreamBootstrapHelpUtil.class,
			"createServerSocketFromCluster", String.class);

	private static class CacheStreamRunnable implements Runnable {

		@SuppressWarnings("rawtypes")
		public CacheStreamRunnable(
			ServerSocket serverSocket, String cacheName) {

			_serverSocket = serverSocket;
			_cacheName = cacheName;

			EhcachePortalCacheManager ehcachePortalCacheManager =
				(EhcachePortalCacheManager)PortalBeanLocatorUtil.locate(
					_MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME);

			_portalCacheManager = ehcachePortalCacheManager.getEhcacheManager();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			Socket socket = null;

			try {
				socket = _serverSocket.accept();

				_serverSocket.close();

				socket.shutdownInput();

				ObjectOutputStream objectOutputStream =
					new AnnotatedObjectOutputStream(socket.getOutputStream());

				objectOutputStream.writeObject(_CACHE_TX_START);

				Ehcache ehcache = _portalCacheManager.getCache(_cacheName);

				if (ehcache == null) {
					EhcacheStreamBootstrapCacheLoader.setSkip();

					try {
						_portalCacheManager.addCache(_cacheName);
					}
					finally {
						EhcacheStreamBootstrapCacheLoader.resetSkip();
					}
				}
				else {
					objectOutputStream.writeObject(_cacheName);

					List<Object> keys = ehcache.getKeys();

					for (Object key : keys) {
						if (!(key instanceof Serializable)) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Key " + key.toString() +
										" cannot be serializable");
							}

							continue;
						}

						Element element = ehcache.get(key);

						Object value = element.getObjectValue();

						if (value == null) {
							continue;
						}
						else {
							if (!(value instanceof Serializable)) {
								if (_log.isWarnEnabled()) {
									_log.warn(
										"Value " + value.toString() +
											" cannot be serializable");
								}

								continue;
							}
						}

						EhcacheElement ehcacheElement = new EhcacheElement(
							(Serializable)key, (Serializable)value);

						objectOutputStream.writeObject(ehcacheElement);
					}
				}

				objectOutputStream.writeObject(_SOCKET_CLOSE);

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

		private CacheManager _portalCacheManager;
		private ServerSocket _serverSocket;
		private String _cacheName;

	}

	@SuppressWarnings("serial")
	private static class EhcacheElement implements Serializable {

		public EhcacheElement(Serializable key, Serializable value) {
			_key = key;
			_value = value;
		}

		public Element toElement() {
			return new Element(_key, _value);
		}

		private Serializable _key;
		private Serializable _value;

	}

	private static class SocketCacheServerSocketConfiguration
		implements ServerSocketConfigurator {

		@Override
		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setSoTimeout(_SO_TIMEOUT);
		}

	}

}