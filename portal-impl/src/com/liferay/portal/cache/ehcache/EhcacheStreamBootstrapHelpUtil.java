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
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
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
import java.net.SocketTimeoutException;

import java.nio.channels.ServerSocketChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author Shuyang Zhou
 * @author Sherry Yang
 */
public class EhcacheStreamBootstrapHelpUtil {

	public static SocketAddress createServerSocketFromCluster(
			List<String> cacheNames)
		throws Exception {

		ServerSocket serverSocket = createServerSocket(
			PropsValues.EHCACHE_SOCKET_START_PORT);

		EhcacheStreamServerThread ehcacheStreamServerThread =
			new EhcacheStreamServerThread(serverSocket, cacheNames);

		ehcacheStreamServerThread.start();

		return serverSocket.getLocalSocketAddress();
	}

	public static List<String> getCacheNamesFromCluster() {
		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
				_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER);

		CacheManager _portalCacheManager =
			ehcachePortalCacheManager.getEhcacheManager();

		String[] cacheNames = _portalCacheManager.getCacheNames();

		return Arrays.asList(cacheNames);
	}

	protected static int acquireCachePeers() {
		List<Address> clusterNodeAddresses =
			ClusterExecutorUtil.getClusterNodeAddresses();

		if (_log.isInfoEnabled()) {
			_log.info("Cluster node addresses " + clusterNodeAddresses);
		}

		int clusterNodeAddressesCount = clusterNodeAddresses.size();

		if (clusterNodeAddressesCount <= 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find peers because there is only one portal " +
						"instance in the cluster");
			}
		}

		return clusterNodeAddressesCount;
	}

	protected static ServerSocket createServerSocket(int startPort)
		throws Exception {

		InetAddress inetAddress = ClusterLinkUtil.getBindInetAddress();

		ServerSocketConfigurator serverSocketConfigurator =
			new SocketCacheServerSocketConfiguration();

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				inetAddress, startPort, serverSocketConfigurator);

		return serverSocketChannel.socket();
	}

	protected static List<String> loadCacheNamesFromCluster() throws Exception {
		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(_getCacheNamesFromClusterMethodKey), true);

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
			return null;
		}

		if (clusterNodeResponse == null) {
			return null;
		}

		return (List<String>)clusterNodeResponse.getResult();
	}

	protected static void loadCachesFromCluster(
			boolean synchronizeCaches, Ehcache... ehcaches)
		throws Exception {

		if (acquireCachePeers() < 2) {
			return;
		}

		List<String> ehcacheNames = new ArrayList<String>();

		if (synchronizeCaches) {
			for (Ehcache ehcache : synchronizeCaches()) {
				ehcacheNames.add(ehcache.getName());
			}
		}

		for (Ehcache ehcache : ehcaches) {
			ehcacheNames.add(ehcache.getName());
		}

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_createServerSocketFromClusterMethodKey, ehcacheNames),
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

			EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
				(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
					_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER);

			CacheManager portalCacheManager =
				ehcachePortalCacheManager.getEhcacheManager();

			Ehcache ehcache = null;

			while (true) {
				Object object = objectInputStream.readObject();

				if (object instanceof EhcacheElement) {
					EhcacheElement ehcacheElement = (EhcacheElement)object;

					Element element = ehcacheElement.toElement();

					ehcache.put(element, true);
				}
				else if (object instanceof String) {
					String command = (String)object;

					if (command.equals(_COMMAND_CACHE_TX_START)) {
						String cacheName =
							(String)objectInputStream.readObject();

						ehcache = portalCacheManager.getCache(cacheName);

						if (ehcache == null) {
							EhcacheStreamBootstrapCacheLoader.setSkip();

							try {
								portalCacheManager.addCache(cacheName);

								ehcache = portalCacheManager.getCache(
									cacheName);
							}
							finally {
								EhcacheStreamBootstrapCacheLoader.resetSkip();
							}
						}
					}
					else if (command.equals(_COMMAND_SOCKET_CLOSE)) {
						break;
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

			if (socket != null) {
				socket.close();
			}
		}
	}

	private static List<Ehcache> synchronizeCaches() {
		List<String> remoteCacheNames = null;

		try {
			remoteCacheNames = loadCacheNamesFromCluster();
		}
		catch (Exception e) {
			throw new CacheException(e);
		}

		if ((remoteCacheNames == null) || remoteCacheNames.isEmpty()) {
			return Collections.emptyList();
		}

		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
				_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER);

		CacheManager portalCacheManager =
			ehcachePortalCacheManager.getEhcacheManager();

		List<String> localCacheNames = Arrays.asList(
			portalCacheManager.getCacheNames());

		List<String> cacheNames = new ArrayList<String>();

		cacheNames.addAll(remoteCacheNames);

		cacheNames.removeAll(localCacheNames);

		EhcacheStreamBootstrapCacheLoader.setSkip();

		List<Ehcache> caches = new ArrayList<Ehcache>(cacheNames.size());

		for (String cacheName : cacheNames) {
			portalCacheManager.addCache(cacheName);

			caches.add(portalCacheManager.getCache(cacheName));
		}

		EhcacheStreamBootstrapCacheLoader.resetSkip();

		return caches;
	}

	private static final String _BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private static final String _COMMAND_CACHE_TX_START = "${CACHE_TX_START}";

	private static final String _COMMAND_SOCKET_CLOSE = "${SOCKET_CLOSE}";

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapHelpUtil.class);

	private static MethodKey _createServerSocketFromClusterMethodKey =
		new MethodKey(
			EhcacheStreamBootstrapHelpUtil.class,
			"createServerSocketFromCluster", List.class);
	private static MethodKey _getCacheNamesFromClusterMethodKey =
		new MethodKey(
			EhcacheStreamBootstrapHelpUtil.class, "getCacheNamesFromCluster");

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

	private static class EhcacheStreamServerThread extends Thread {

		public EhcacheStreamServerThread(
			ServerSocket serverSocket, List<String> ehcacheNames) {

			_serverSocket = serverSocket;
			_ehcacheNames = ehcacheNames;

			EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
				(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
					_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER);

			_portalCacheManager = ehcachePortalCacheManager.getEhcacheManager();

			setDaemon(true);

			setName(
				EhcacheStreamServerThread.class.getName() + " - " +
				ehcacheNames);

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

				_serverSocket.close();

				socket.shutdownInput();

				ObjectOutputStream objectOutputStream =
					new AnnotatedObjectOutputStream(socket.getOutputStream());

				for (String cacheName : _ehcacheNames) {
					objectOutputStream.writeObject(_COMMAND_CACHE_TX_START);

					Ehcache ehcache = _portalCacheManager.getCache(cacheName);

					if (ehcache == null) {
						EhcacheStreamBootstrapCacheLoader.setSkip();

						try {
							_portalCacheManager.addCache(cacheName);
						}
						finally {
							EhcacheStreamBootstrapCacheLoader.resetSkip();
						}
					}
					else {
						objectOutputStream.writeObject(cacheName);

						List<Object> keys = ehcache.getKeys();

						for (Object key : keys) {
							if (!(key instanceof Serializable)) {
								if (_log.isWarnEnabled()) {
									_log.warn(
										"Key " + key + " is not serializable");
								}

								continue;
							}

							Element element = ehcache.get(key);

							if (element == null) {
								continue;
							}

							Object value = element.getObjectValue();

							if (!(value instanceof Serializable)) {
								if (_log.isWarnEnabled() && (value != null)) {
									_log.warn(
										"Value " + value +
										" is not serializable");
								}

								continue;
							}

							EhcacheElement ehcacheElement = new EhcacheElement(
								(Serializable)key, (Serializable)value);

							objectOutputStream.writeObject(ehcacheElement);
						}
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

		private List<String> _ehcacheNames;
		private CacheManager _portalCacheManager;
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