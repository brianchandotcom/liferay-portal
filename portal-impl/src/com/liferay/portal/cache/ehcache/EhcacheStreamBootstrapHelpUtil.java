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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.SocketUtil;
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
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
public class EhcacheStreamBootstrapHelpUtil {

	public static void acquireCachePeers(Ehcache cache) throws Exception {
		List<Address> clusterNodeAddresses =
			ClusterExecutorUtil.getClusterNodeAddresses();

		if (_log.isInfoEnabled()) {
			_log.info("##########" + clusterNodeAddresses);
		}

		int clusterNodeAddressesCount = clusterNodeAddresses.size();

		if (clusterNodeAddressesCount <= 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Can not get caches because there is either one portal" +
						" instance or no portal instances in the cluster");
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

	public static Element toEhcacheElement(
		EhcacheElement liferayEhcacheElementHelper) {

		Object key = liferayEhcacheElementHelper.getKey();
		Object value = liferayEhcacheElementHelper.getValue();

		return new Element(key, value);
	}

	public static SocketAddress createServerSocketFromCluster(String cacheName)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("remote node executed");
		}

		ServerSocket serverSocket = createServerSocket(startPort);

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

		Set<Address> addresses =
			futureClusterResponses.getExpectedReplyAddress();

		ClusterNodeResponse clusterNodeResponse = null;

		try {
			clusterNodeResponse = clusterNodeResponses.poll(
				_BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT,
				TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException ie) {
			return;
		}

		if (clusterNodeResponse == null) {
			return;
		}

		Socket socket = null;

		ObjectInputStream objectInputStream = null;

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

			while(true) {

				//read cache from remote

				Object object = objectInputStream.readObject();

				if (object instanceof EhcacheElement){
					EhcacheElement ehcacheElement = (EhcacheElement)object;

					Element element = ehcacheElement.toElement();

					ehcache.put(element, true);
				}
				else if(object instanceof String) {
					String command = (String)object;

					if(command.equals(_socket_close)) {
						break;
					}
					else if(command.equals(_cache_tx_start)) {
						String cacheName =
							(String)objectInputStream.readObject();

						if(!cacheName.equals(ehcache.getName())) {
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

	private static final long _BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT = 1000000;

	private static final String _cache_tx_start = "${CACHE_TX_START}";
	
	private static final String _socket_close = "${SOCKET_CLOSE}";

	private static final String _MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapHelpUtil.class);

	private static MethodKey _createServerSocketFromClusterMethodKey =
		new MethodKey(
			EhcacheStreamBootstrapHelpUtil.class,
			"createServerSocketFromCluster", String.class);

	private final static int soTimeout =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.EHCACHE_SOCKET_SO_TIMEOUT));

	private final static int startPort =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.EHCACHE_SOCKET_START_PORT));

	private static class EhcacheElement implements Serializable {
		
		public EhcacheElement (Serializable key, Serializable value) {
			_key = key;
			_value = value;
		}		

		public Serializable getKey() {
			return _key;
		}
		
		public Serializable getValue() {
			return _value;
		}

		public Element toElement() {
			return new Element(_key, _value);
		}

		private Serializable _key;
		private Serializable _value;
		
	}

	private static class CacheStreamRunnable implements Runnable {

		public CacheStreamRunnable(ServerSocket serverSocket, String cacheName) {
			_serverSocket = serverSocket;
			_cacheName = cacheName;
			EhcachePortalCacheManager ehcachePortalCacheManager =
				(EhcachePortalCacheManager)PortalBeanLocatorUtil.locate(
					_MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME);
	
			 _portalCacheManager =
				ehcachePortalCacheManager.getEhcacheManager();
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

				objectOutputStream.writeObject(_cache_tx_start);

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
				else  {
					objectOutputStream.writeObject(_cacheName);

					List<Object> keys = ehcache.getKeys();

					for (Object key : keys) {
						if (!(key instanceof Serializable)) {
							// TODO log about it
							continue;
						}

						Object value = ehcache.get(key);

						if (!(value instanceof Serializable)) {
							// TODO log about it
							continue;
						}

						EhcacheElement ehcacheElement = new EhcacheElement(
							(Serializable)key, (Serializable)value);

						objectOutputStream.writeObject(ehcacheElement);
					}
				}

				objectOutputStream.writeObject(_socket_close);

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

		private ServerSocket _serverSocket;
		private String _cacheName;
		private CacheManager _portalCacheManager;
	}

	private static class SocketCacheServerSocketConfiguration
		implements ServerSocketConfigurator {

		@Override
		public void configure(ServerSocket serverSocket)
				throws SocketException {

			serverSocket.setSoTimeout(soTimeout);
		}
	}

}