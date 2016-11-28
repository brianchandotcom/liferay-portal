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

package com.liferay.sync.engine.lan.session;

import com.liferay.sync.engine.document.library.event.LanDownloadFileEvent;
import com.liferay.sync.engine.document.library.handler.LanDownloadFileHandler;
import com.liferay.sync.engine.lan.util.LanClientUtil;
import com.liferay.sync.engine.lan.util.LanPEMParserUtil;
import com.liferay.sync.engine.lan.util.LanTokenUtil;
import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncLanClient;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncLanClientService;
import com.liferay.sync.engine.service.SyncLanEndpointService;
import com.liferay.sync.engine.util.GetterUtil;
import com.liferay.sync.engine.util.PropsValues;

import java.io.IOException;

import java.net.Socket;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanSession {

	public static ExecutorService getExecutorService() {
		if (_queryExecutorService != null) {
			return _queryExecutorService;
		}

		_queryExecutorService = new ThreadPoolExecutor(
			PropsValues.SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE,
			PropsValues.SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE, 60,
			TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		_queryExecutorService.allowCoreThreadTimeOut(true);

		return _queryExecutorService;
	}

	public static LanSession getLanSession() {
		if (_lanSession == null) {
			_lanSession = new LanSession();
		}

		return _lanSession;
	}

	public static ModelListener<SyncAccount> getSyncAccountListener() {
		if (_syncAccountListener != null) {
			return _syncAccountListener;
		}

		_syncAccountListener = new ModelListener<SyncAccount>() {

			@Override
			public void onCreate(SyncAccount syncAccount) {
				createLanSession(syncAccount);
			}

			@Override
			public void onRemove(SyncAccount syncAccount) {
				createLanSession(syncAccount);
			}

			@Override
			public void onUpdate(
				SyncAccount syncAccount, Map<String, Object> originalValues) {

				if (originalValues.containsKey("active") ||
					originalValues.containsKey("lanCertificate") ||
					originalValues.containsKey("lanKey")) {

					createLanSession(syncAccount);
				}
			}

			protected void createLanSession(SyncAccount syncAccount) {
				if (syncAccount.isLanEnabled()) {
					_lanSession = new LanSession();
				}
			}

		};

		return _syncAccountListener;
	}

	public LanSession() {
		_downloadHttpClient = _createHttpClient(
			PropsValues.SYNC_LAN_SESSION_DOWNLOAD_CONNECT_TIMEOUT,
			PropsValues.SYNC_LAN_SESSION_DOWNLOAD_MAX_PER_ROUTE,
			PropsValues.SYNC_LAN_SESSION_DOWNLOAD_MAX_TOTAL,
			PropsValues.SYNC_LAN_SESSION_DOWNLOAD_SOCKET_TIMEOUT);

		_queryHttpClient = _createHttpClient(
			PropsValues.SYNC_LAN_SESSION_QUERY_CONNECT_TIMEOUT,
			PropsValues.SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE,
			PropsValues.SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE,
			PropsValues.SYNC_LAN_SESSION_QUERY_SOCKET_TIMEOUT);
	}

	public HttpGet downloadFile(LanDownloadFileEvent lanDownloadFileEvent)
		throws Exception {

		final SyncFile syncFile =
			(SyncFile)lanDownloadFileEvent.getParameterValue("syncFile");

		SyncLanClientQueryResult syncLanClientQueryResult = findSyncLanClient(
			syncFile);

		final LanDownloadFileHandler lanDownloadFileHandler =
			(LanDownloadFileHandler)lanDownloadFileEvent.getHandler();

		if (syncLanClientQueryResult == null) {
			lanDownloadFileHandler.handleException(
				new NoSuchSyncLanClientException());

			return null;
		}

		if (syncLanClientQueryResult.getConnectionsCount() >=
				syncLanClientQueryResult.getMaxConnections()) {

			lanDownloadFileHandler.queueDownload();

			return null;
		}

		final String url = _getUrl(
			syncLanClientQueryResult.getSyncLanClient(), syncFile);

		final HttpGet httpGet = new HttpGet(url);

		httpGet.addHeader(
			"lanToken",
			LanTokenUtil.decryptLanToken(
				syncFile.getLanTokenKey(),
				syncLanClientQueryResult.getEncryptedToken()));

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					if (_logger.isTraceEnabled()) {
						_logger.trace(
							"Downloading {} from {}",
							syncFile.getFilePathName(), url);
					}

					_downloadHttpClient.execute(
						httpGet, lanDownloadFileHandler,
						HttpClientContext.create());
				}
				catch (Exception e) {
					lanDownloadFileHandler.handleException(e);
				}
			}

		};

		_downloadExecutorService.execute(runnable);

		return httpGet;
	}

	protected Callable<SyncLanClientQueryResult>
		createSyncLanClientQueryResultCallable(
			final SyncLanClient syncLanClient, SyncFile syncFile) {

		String url = _getUrl(syncLanClient, syncFile);

		final HttpHead httpHead = new HttpHead(url);

		return new Callable<SyncLanClientQueryResult>() {

			@Override
			public SyncLanClientQueryResult call() throws Exception {
				SyncLanClientQueryResult syncLanClientQueryResult =
					new SyncLanClientQueryResult();

				syncLanClientQueryResult.setSyncLanClient(syncLanClient);

				HttpResponse httpResponse = _queryHttpClient.execute(
					httpHead, HttpClientContext.create());

				Header connectionsCountHeader = httpResponse.getFirstHeader(
					"connectionsCount");

				if (connectionsCountHeader == null) {
					return null;
				}

				syncLanClientQueryResult.setConnectionsCount(
					GetterUtil.getInteger(connectionsCountHeader.getValue()));

				Header downloadRateHeader = httpResponse.getFirstHeader(
					"downloadRate");

				if (downloadRateHeader == null) {
					return null;
				}

				syncLanClientQueryResult.setDownloadRate(
					GetterUtil.getInteger(downloadRateHeader.getValue()));

				Header encryptedTokenHeader = httpResponse.getFirstHeader(
					"encryptedToken");

				if (encryptedTokenHeader == null) {
					return null;
				}

				syncLanClientQueryResult.setEncryptedToken(
					encryptedTokenHeader.getValue());

				Header maxConnectionsHeader = httpResponse.getFirstHeader(
					"maxConnections");

				if (maxConnectionsHeader == null) {
					return null;
				}

				syncLanClientQueryResult.setMaxConnections(
					GetterUtil.getInteger(maxConnectionsHeader.getValue()));

				return syncLanClientQueryResult;
			}

		};
	}

	protected SyncLanClientQueryResult findSyncLanClient(SyncFile syncFile)
		throws Exception {

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncFile.getSyncAccountId());

		List<String> syncLanClientUuids =
			SyncLanEndpointService.findSyncLanClientUuids(
				syncAccount.getLanServerUuid(), syncFile.getRepositoryId());

		if (syncLanClientUuids.isEmpty()) {
			return null;
		}

		final List<Callable<SyncLanClientQueryResult>>
			syncLanClientQueryResultCallables = Collections.synchronizedList(
				new ArrayList<Callable<SyncLanClientQueryResult>>(
					syncLanClientUuids.size()));

		for (String syncLanClientUuid : syncLanClientUuids) {
			SyncLanClient syncLanClient =
				SyncLanClientService.fetchSyncLanClient(syncLanClientUuid);

			syncLanClientQueryResultCallables.add(
				createSyncLanClientQueryResultCallable(
					syncLanClient, syncFile));
		}

		int queryPoolSize = Math.min(
			syncLanClientUuids.size(),
			PropsValues.SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE);

		List<Future<SyncLanClientQueryResult>>
			pendingSyncLanClientQueryResults = new ArrayList<>(queryPoolSize);

		ExecutorCompletionService<SyncLanClientQueryResult>
			executorCompletionService = new ExecutorCompletionService<>(
				getExecutorService());

		for (int i = 0; i < queryPoolSize; i++) {
			Callable<SyncLanClientQueryResult> callable =
				new Callable<SyncLanClientQueryResult>() {

				@Override
				public synchronized SyncLanClientQueryResult call()
					throws Exception {

					if (syncLanClientQueryResultCallables.isEmpty()) {
						return null;
					}

					Callable<SyncLanClientQueryResult>
						syncLanClientQueryResultCallable =
							syncLanClientQueryResultCallables.remove(0);

					try {
						return syncLanClientQueryResultCallable.call();
					}
					catch (Exception e) {
						return call();
					}
				}

			};

			pendingSyncLanClientQueryResults.add(
				executorCompletionService.submit(callable));
		}

		List<Future<SyncLanClientQueryResult>>
			completedSyncLanClientQueryResult = new ArrayList<>(queryPoolSize);

		long timeout = PropsValues.SYNC_LAN_SESSION_QUERY_TOTAL_TIMEOUT;

		long endTime = System.currentTimeMillis() + timeout;

		for (int i = 0; i < queryPoolSize; i++) {
			Future<SyncLanClientQueryResult> future =
				executorCompletionService.poll(timeout, TimeUnit.MILLISECONDS);

			if (future == null) {
				for (Future<SyncLanClientQueryResult>
					pendingSyncLanClientQueryResult :
						pendingSyncLanClientQueryResults) {

					if (!pendingSyncLanClientQueryResult.isDone()) {
						pendingSyncLanClientQueryResult.cancel(true);
					}
				}

				break;
			}

			completedSyncLanClientQueryResult.add(future);

			timeout = endTime - System.currentTimeMillis();
		}

		SyncLanClientQueryResult candidateSyncLanClientQueryResult = null;
		int candidateDownloadRatePerConnection = 0;

		for (Future<SyncLanClientQueryResult> completedFuture :
				completedSyncLanClientQueryResult) {

			SyncLanClientQueryResult syncLanClientQueryResult = null;

			try {
				syncLanClientQueryResult = completedFuture.get();
			}
			catch (Exception e) {
				continue;
			}

			if (syncLanClientQueryResult == null) {
				continue;
			}

			if (syncLanClientQueryResult.getConnectionsCount() >=
					syncLanClientQueryResult.getMaxConnections()) {

				if (candidateSyncLanClientQueryResult == null) {
					candidateSyncLanClientQueryResult =
						syncLanClientQueryResult;
				}

				continue;
			}

			if (syncLanClientQueryResult.getConnectionsCount() == 0) {
				return syncLanClientQueryResult;
			}

			int downloadRatePerConnection =
				syncLanClientQueryResult.getDownloadRate() /
					(syncLanClientQueryResult.getConnectionsCount() + 1);

			if (downloadRatePerConnection >=
					candidateDownloadRatePerConnection) {

				candidateDownloadRatePerConnection = downloadRatePerConnection;
				candidateSyncLanClientQueryResult = syncLanClientQueryResult;
			}
		}

		return candidateSyncLanClientQueryResult;
	}

	private static HttpClient _createHttpClient(
		int connectTimeout, int maxPerRoute, int maxTotal, int socketTimeout) {

		RegistryBuilder<ConnectionSocketFactory> registryBuilder =
			RegistryBuilder.create();

		try {
			registryBuilder.register("https", _getSSLSocketFactory());
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}

		PoolingHttpClientConnectionManager connectionManager =
			new PoolingHttpClientConnectionManager(registryBuilder.build());

		connectionManager.setDefaultMaxPerRoute(maxPerRoute);
		connectionManager.setMaxTotal(maxTotal);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		RequestConfig.Builder builder = RequestConfig.custom();

		builder.setConnectTimeout(connectTimeout);
		builder.setSocketTimeout(socketTimeout);

		httpClientBuilder.setDefaultRequestConfig(builder.build());

		httpClientBuilder.setConnectionManager(connectionManager);

		return httpClientBuilder.build();
	}

	private static SSLConnectionSocketFactory _getSSLSocketFactory()
		throws Exception {

		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

		keyStore.load(null, null);

		for (SyncAccount syncAccount : SyncAccountService.findAll()) {
			if (!syncAccount.isActive() || !syncAccount.isLanEnabled()) {
				continue;
			}

			try {
				PrivateKey privateKey = LanPEMParserUtil.parsePrivateKey(
					syncAccount.getLanKey());

				if (privateKey == null) {
					_logger.error(
						"SyncAccount {} missing valid private key",
						syncAccount.getSyncAccountId());

					continue;
				}

				X509Certificate x509Certificate =
					LanPEMParserUtil.parseX509Certificate(
						syncAccount.getLanCertificate());

				if (x509Certificate == null) {
					_logger.error(
						"SyncAccount {} missing valid certificate",
						syncAccount.getSyncAccountId());

					continue;
				}

				keyStore.setCertificateEntry(
					syncAccount.getLanServerUuid(), x509Certificate);

				keyStore.setKeyEntry(
					syncAccount.getLanServerUuid(), privateKey,
					"".toCharArray(), new Certificate[] {x509Certificate});
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
			KeyManagerFactory.getDefaultAlgorithm());

		keyManagerFactory.init(keyStore, "".toCharArray());

		TrustManagerFactory trustManagerFactory =
			TrustManagerFactory.getInstance(
				TrustManagerFactory.getDefaultAlgorithm());

		trustManagerFactory.init(keyStore);

		SSLContext sslContext = SSLContext.getInstance("TLS");

		sslContext.init(
			keyManagerFactory.getKeyManagers(),
			trustManagerFactory.getTrustManagers(), null);

		return new SSLConnectionSocketFactory(
			sslContext, new NoopHostnameVerifier()) {

			@Override
			public Socket createLayeredSocket(
					Socket socket, String target, int port,
					HttpContext httpContext)
				throws IOException {

				HttpClientContext httpClientContext =
					(HttpClientContext)httpContext;

				HttpRequest httpRequest = httpClientContext.getRequest();

				RequestLine requestLine = httpRequest.getRequestLine();

				String[] parts = StringUtils.split(requestLine.getUri(), "/");

				String sniCompliantLanServerUuid = LanClientUtil.getSNIHostname(
					parts[0]);

				return super.createLayeredSocket(
					socket, sniCompliantLanServerUuid, port, httpContext);
			}

		};
	}

	private static String _getUrl(
		SyncLanClient syncLanClient, SyncFile syncFile) {

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncFile.getSyncAccountId());

		StringBuilder sb = new StringBuilder();

		sb.append("https://");
		sb.append(syncLanClient.getHostname());
		sb.append(":");
		sb.append(syncLanClient.getPort());
		sb.append("/");
		sb.append(syncAccount.getLanServerUuid());
		sb.append("/");
		sb.append(syncFile.getRepositoryId());
		sb.append("/");
		sb.append(syncFile.getTypePK());
		sb.append("/");
		sb.append(syncFile.getVersionId());

		return sb.toString();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanSession.class);

	private static LanSession _lanSession;
	private static ThreadPoolExecutor _queryExecutorService;
	private static ModelListener<SyncAccount> _syncAccountListener;

	private final ExecutorService _downloadExecutorService =
		Executors.newCachedThreadPool();
	private final HttpClient _downloadHttpClient;
	private final HttpClient _queryHttpClient;

}