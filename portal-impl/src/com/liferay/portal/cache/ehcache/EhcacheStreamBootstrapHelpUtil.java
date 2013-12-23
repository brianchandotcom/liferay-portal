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

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.nio.channels.ServerSocketChannel;

import java.util.ArrayList;
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

	public static SocketAddress createServerSocketFromCluster(
			List<String> cacheNames)
		throws Exception {

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				ClusterLinkUtil.getBindInetAddress(),
				PropsValues.EHCACHE_SOCKET_START_PORT,
				_serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
				_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER);

		CacheManager cacheManager =
			ehcachePortalCacheManager.getEhcacheManager();

		EhcacheStreamServerThread ehcacheStreamServerThread =
			new EhcacheStreamServerThread(
				serverSocket, cacheManager, cacheNames);

		ehcacheStreamServerThread.start();

		return serverSocket.getLocalSocketAddress();
	}

	protected static void loadCachesFromCluster(Ehcache... ehcaches)
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

		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
				_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER);

		List<String> cacheNames = new ArrayList<String>();

		for (Ehcache ehcache : ehcaches) {
			CacheManager cacheManager = ehcache.getCacheManager();

			if (_BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER.equals(
					cacheManager.getName())) {

				cacheNames.add(ehcache.getName());
			}
		}

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_createServerSocketFromClusterMethodKey, cacheNames),
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

		Socket socket = null;
		ObjectInputStream objectInputStream = null;

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

			objectInputStream = new AnnotatedObjectInputStream(
				socket.getInputStream());

			CacheManager cacheManager =
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
					if (_COMMAND_SOCKET_CLOSE.equals(object)) {
						break;
					}

					EhcacheStreamBootstrapCacheLoader.setSkip();

					try {
						ehcache = cacheManager.addCacheIfAbsent((String)object);
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

			if (socket != null) {
				socket.close();
			}
		}
	}

	private static final String _BEAN_NAME_MULTI_VM_PORTAL_CACHE_MANAGER =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private static final String _COMMAND_SOCKET_CLOSE = "${SOCKET_CLOSE}";

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapHelpUtil.class);

	private static MethodKey _createServerSocketFromClusterMethodKey =
		new MethodKey(
			EhcacheStreamBootstrapHelpUtil.class,
			"createServerSocketFromCluster", List.class);
	private static ServerSocketConfigurator _serverSocketConfigurator =
		new SocketCacheServerSocketConfiguration();

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
			ServerSocket serverSocket, CacheManager cacheManager,
			List<String> cacheNames) {

			_serverSocket = serverSocket;
			_cacheManager = cacheManager;
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

				_serverSocket.close();

				socket.shutdownInput();

				ObjectOutputStream objectOutputStream =
					new AnnotatedObjectOutputStream(socket.getOutputStream());

				for (String cacheName : _cacheNames) {
					Ehcache ehcache = _cacheManager.getCache(cacheName);

					if (ehcache == null) {
						EhcacheStreamBootstrapCacheLoader.setSkip();

						try {
							_cacheManager.addCache(cacheName);
						}
						finally {
							EhcacheStreamBootstrapCacheLoader.resetSkip();
						}

						continue;
					}

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
									"Value " + value + " is not serializable");
							}

							continue;
						}

						EhcacheElement ehcacheElement = new EhcacheElement(
							(Serializable)key, (Serializable)value);

						objectOutputStream.writeObject(ehcacheElement);
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

		private CacheManager _cacheManager;
		private List<String> _cacheNames;
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