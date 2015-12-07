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

package com.liferay.portal.util;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Encryptor;

import java.io.File;
import java.io.InputStream;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.URL;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

/**
 * @author Amos Fong
 */
public class LicenseUtil {

	public static final String LICENSE_REPOSITORY_DIR =
		PropsValues.LIFERAY_HOME.concat("/data/license");

	public static final String LICENSE_SERVER_URL = GetterUtil.get(
		PropsUtil.get("license.server.url"), "https://www.liferay.com");

	public static Map<String, String> getClusterServerInfo(String clusterNodeId)
		throws Exception {

		ClusterNode localClusterNode =
			ClusterExecutorUtil.getLocalClusterNode();

		String localClusterNodeId = localClusterNode.getClusterNodeId();

		if (clusterNodeId.equals(localClusterNodeId)) {
			return getServerInfo();
		}

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		ClusterNode clusterNode = null;

		for (ClusterNode curClusterNode : clusterNodes) {
			String curClusterNodeId = curClusterNode.getClusterNodeId();

			if (curClusterNodeId.equals(clusterNodeId)) {
				clusterNode = curClusterNode;

				break;
			}
		}

		if (clusterNode == null) {
			return null;
		}

		try {
			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				_getServerInfoMethodHandler, clusterNodeId);

			FutureClusterResponses futureClusterResponses =
				ClusterExecutorUtil.execute(clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get(20000, TimeUnit.MILLISECONDS);

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(
					clusterNode.getClusterNodeId());

			return (Map<String, String>)clusterNodeResponse.getResult();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link PortalUtil#getComputerName()}
	 */
	@Deprecated
	public static String getHostName() {
		return PortalUtil.getComputerName();
	}

	public static Set<String> getIpAddresses() {
		if (_ipAddresses != null) {
			return new HashSet<>(_ipAddresses);
		}

		_ipAddresses = new HashSet<>();

		try {
			List<NetworkInterface> networkInterfaces = Collections.list(
				NetworkInterface.getNetworkInterfaces());

			for (NetworkInterface networkInterface : networkInterfaces) {
				List<InetAddress> inetAddresses = Collections.list(
					networkInterface.getInetAddresses());

				for (InetAddress inetAddress : inetAddresses) {
					if (inetAddress.isLinkLocalAddress() ||
						inetAddress.isLoopbackAddress() ||
						!(inetAddress instanceof Inet4Address)) {

						continue;
					}

					_ipAddresses.add(inetAddress.getHostAddress());
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to read local server's IP addresses");

			_log.error(e, e);
		}

		return new HashSet<>(_ipAddresses);
	}

	public static Set<String> getMacAddresses() {
		if (_macAddresses != null) {
			return new HashSet<>(_macAddresses);
		}

		_macAddresses = new HashSet<>();

		try {
			List<NetworkInterface> networkInterfaces = Collections.list(
				NetworkInterface.getNetworkInterfaces());

			for (NetworkInterface networkInterface : networkInterfaces) {
				byte[] hardwareAddress = networkInterface.getHardwareAddress();

				if (ArrayUtil.isEmpty(hardwareAddress)) {
					continue;
				}

				StringBuilder sb = new StringBuilder(
					(hardwareAddress.length * 3) - 1);

				String hexString = StringUtil.bytesToHexString(hardwareAddress);

				for (int i = 0; i < hexString.length(); i += 2) {
					if (i != 0) {
						sb.append(CharPool.COLON);
					}

					sb.append(Character.toLowerCase(hexString.charAt(i)));
					sb.append(Character.toLowerCase(hexString.charAt(i + 1)));
				}

				_macAddresses.add(sb.toString());
			}
		}
		catch (Exception e) {
			_log.error("Unable to read local server's MAC addresses");

			_log.error(e, e);
		}

		return new HashSet<>(_macAddresses);
	}

	public static byte[] getServerIdBytes() throws Exception {
		if (_serverIdBytes != null) {
			return _serverIdBytes;
		}

		File serverIdFile = new File(
			LICENSE_REPOSITORY_DIR + "/server/serverId");

		if (!serverIdFile.exists()) {
			return new byte[0];
		}

		_serverIdBytes = FileUtil.getBytes(serverIdFile);

		return _serverIdBytes;
	}

	public static Map<String, String> getServerInfo() {
		Map<String, String> serverInfo = new HashMap<>();

		serverInfo.put("hostName", PortalUtil.getComputerName());
		serverInfo.put("ipAddresses", StringUtil.merge(getIpAddresses()));
		serverInfo.put("macAddresses", StringUtil.merge(getMacAddresses()));

		return serverInfo;
	}

	public static void registerOrder(HttpServletRequest request) {
		String orderUuid = ParamUtil.getString(request, "orderUuid");
		String productEntryName = ParamUtil.getString(
			request, "productEntryName");
		int maxServers = ParamUtil.getInteger(request, "maxServers");

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		if ((clusterNodes.size() <= 1) || Validator.isNull(productEntryName) ||
			Validator.isNull(orderUuid)) {

			Map<String, Object> attributes = registerOrder(
				orderUuid, productEntryName, maxServers);

			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}
		else {
			for (ClusterNode clusterNode : clusterNodes) {
				boolean register = ParamUtil.getBoolean(
					request, clusterNode.getClusterNodeId() + "_register");

				if (!register) {
					continue;
				}

				try {
					_registerClusterOrder(
						request, clusterNode, orderUuid, productEntryName,
						maxServers);
				}
				catch (Exception e) {
					_log.error(e, e);

					InetAddress inetAddress = clusterNode.getBindInetAddress();

					String message =
						"Error contacting " + inetAddress.getHostName();

					if (clusterNode.getPortalPort() != -1) {
						message +=
							StringPool.COLON + clusterNode.getPortalPort();
					}

					request.setAttribute(
						clusterNode.getClusterNodeId() + "_ERROR_MESSAGE",
						message);
				}
			}
		}
	}

	public static Map<String, Object> registerOrder(
		String orderUuid, String productEntryName, int maxServers) {

		Map<String, Object> attributes = new HashMap<>();

		if (Validator.isNull(orderUuid)) {
			return attributes;
		}

		try {
			JSONObject jsonObject = _createRequest(
				orderUuid, productEntryName, maxServers);

			String response = sendRequest(jsonObject.toString());

			JSONObject responseJSONObject = new JSONObjectImpl(response);

			attributes.put(
				"ORDER_PRODUCT_ID", responseJSONObject.getString("productId"));
			attributes.put(
				"ORDER_PRODUCTS", _getOrderProducts(responseJSONObject));

			String errorMessage = responseJSONObject.getString("errorMessage");

			if (Validator.isNotNull(errorMessage)) {
				attributes.put("ERROR_MESSAGE", errorMessage);

				return attributes;
			}

			String licenseXML = responseJSONObject.getString("licenseXML");

			if (Validator.isNotNull(licenseXML)) {
				LicenseManagerUtil.registerLicense(responseJSONObject);

				attributes.clear();
				attributes.put(
					"SUCCESS_MESSAGE",
					"Your license has been successfully registered.");
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			attributes.put(
				"ERROR_MESSAGE",
				"There was an error contacting " + LICENSE_SERVER_URL);
		}

		return attributes;
	}

	public static String sendRequest(String request) throws Exception {
		HttpClient httpClient = null;

		HttpClientConnectionManager httpClientConnectionManager =
			new BasicHttpClientConnectionManager();

		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

			httpClientBuilder.setConnectionManager(httpClientConnectionManager);

			String serverURL = LICENSE_SERVER_URL;

			if (!serverURL.endsWith(StringPool.SLASH)) {
				serverURL += StringPool.SLASH;
			}

			serverURL += "osb-portlet/license";

			URI uri = new URI(serverURL);

			HttpPost httpPost = new HttpPost(uri);

			CredentialsProvider credentialsProvider =
				new BasicCredentialsProvider();

			HttpHost proxyHttpHost = null;

			if (Validator.isNotNull(_PROXY_URL)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Using proxy " + _PROXY_URL + StringPool.COLON +
							_PROXY_PORT);
				}

				proxyHttpHost = new HttpHost(_PROXY_URL, _PROXY_PORT);

				if (Validator.isNotNull(_PROXY_USER_NAME)) {
					credentialsProvider.setCredentials(
						new AuthScope(_PROXY_URL, _PROXY_PORT),
						new UsernamePasswordCredentials(
							_PROXY_USER_NAME, _PROXY_PASSWORD));
				}
			}

			httpClientBuilder.setDefaultCredentialsProvider(
				credentialsProvider);
			httpClientBuilder.setProxy(proxyHttpHost);

			httpClient = httpClientBuilder.build();

			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(
				_encryptRequest(serverURL, request));

			byteArrayEntity.setContentType(ContentTypes.APPLICATION_JSON);

			httpPost.setEntity(byteArrayEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();

			String response = _decryptResponse(
				serverURL, httpEntity.getContent());

			if (_log.isDebugEnabled()) {
				_log.debug("Server response: " + response);
			}

			if (Validator.isNull(response)) {
				throw new Exception("Server response is null");
			}

			return response;
		}
		finally {
			if (httpClient != null) {
				httpClientConnectionManager.shutdown();
			}
		}
	}

	public static void writeServerProperties(byte[] serverIdBytes)
		throws Exception {

		File serverIdFile = new File(
			LICENSE_REPOSITORY_DIR + "/server/serverId");

		FileUtil.write(serverIdFile, serverIdBytes);
	}

	private static JSONObject _createRequest(
			String orderUuid, String productEntryName, int maxServers)
		throws Exception {

		JSONObject jsonObject = new JSONObjectImpl();

		jsonObject.put("version", 2);
		jsonObject.put("orderUuid", orderUuid);
		jsonObject.put("liferayVersion", ReleaseInfo.getBuildNumber());

		if (Validator.isNull(productEntryName)) {
			jsonObject.put(Constants.CMD, "QUERY");
		}
		else {
			jsonObject.put(Constants.CMD, "REGISTER");

			if (productEntryName.startsWith("basic")) {
				jsonObject.put("productEntryName", "basic");

				if (productEntryName.equals("basic-cluster")) {
					jsonObject.put("cluster", true);
					jsonObject.put("maxServers", maxServers);
				}
				else if (productEntryName.startsWith("basic-")) {
					String[] productNameArray = StringUtil.split(
						productEntryName, StringPool.DASH);

					if (productNameArray.length >= 3) {
						jsonObject.put("offeringEntryId", productNameArray[1]);
						jsonObject.put("clusterId", productNameArray[2]);
					}
				}
			}
			else {
				jsonObject.put("productEntryName", productEntryName);
			}

			jsonObject.put("hostName", PortalUtil.getComputerName());
			jsonObject.put("ipAddresses", StringUtil.merge(getIpAddresses()));
			jsonObject.put("macAddresses", StringUtil.merge(getMacAddresses()));
			jsonObject.put("serverId", Arrays.toString(getServerIdBytes()));
		}

		return jsonObject;
	}

	private static String _decryptResponse(
			String serverURL, InputStream inputStream)
		throws Exception {

		if (serverURL.startsWith(Http.HTTPS)) {
			return StringUtil.read(inputStream);
		}

		byte[] bytes = IOUtils.toByteArray(inputStream);

		if ((bytes == null) || (bytes.length <= 0)) {
			return null;
		}

		bytes = Encryptor.decryptUnencodedAsBytes(_symmetricKey, bytes);

		return new String(bytes, StringPool.UTF8);
	}

	private static byte[] _encryptRequest(String serverURL, String request)
		throws Exception {

		byte[] bytes = request.getBytes(StringPool.UTF8);

		if (serverURL.startsWith(Http.HTTPS)) {
			return bytes;
		}

		JSONObject jsonObject = new JSONObjectImpl();

		bytes = Encryptor.encryptUnencoded(_symmetricKey, bytes);

		jsonObject.put("content", Base64.objectToString(bytes));
		jsonObject.put("key", _encryptedSymmetricKey);

		return jsonObject.toString().getBytes(StringPool.UTF8);
	}

	private static Map<String, String> _getOrderProducts(
		JSONObject jsonObject) {

		JSONObject productsJSONObject = jsonObject.getJSONObject(
			"productsJSONObject");

		if (productsJSONObject == null) {
			return null;
		}

		Map<String, String> sortedMap = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

		Iterator<String> itr = productsJSONObject.keys();

		while (itr.hasNext()) {
			String key = itr.next();

			sortedMap.put(key, productsJSONObject.getString(key));
		}

		return sortedMap;
	}

	private static void _initKeys() {
		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		if ((classLoader == null) || (_encryptedSymmetricKey != null)) {
			return;
		}

		try {
			URL url = classLoader.getResource(
				"com/liferay/portal/license/public.key");

			byte[] bytes = IOUtils.toByteArray(url.openStream());

			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				bytes);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

			keyGenerator.init(128, new SecureRandom());

			_symmetricKey = keyGenerator.generateKey();

			byte[] encryptedSymmetricKey = Encryptor.encryptUnencoded(
				publicKey, _symmetricKey.getEncoded());

			_encryptedSymmetricKey = Base64.objectToString(
				encryptedSymmetricKey);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static void _registerClusterOrder(
			HttpServletRequest request, ClusterNode clusterNode,
			String orderUuid, String productEntryName, int maxServers)
		throws Exception {

		MethodHandler methodHandler = new MethodHandler(
			_registerOrderMethodKey, orderUuid, productEntryName, maxServers);

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			methodHandler, clusterNode.getClusterNodeId());

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		ClusterNodeResponses clusterNodeResponses = futureClusterResponses.get(
			20000, TimeUnit.MILLISECONDS);

		ClusterNodeResponse clusterNodeResponse =
			clusterNodeResponses.getClusterResponse(
				clusterNode.getClusterNodeId());

		Map<String, Object> attributes =
			(Map<String, Object>)clusterNodeResponse.getResult();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			request.setAttribute(
				clusterNode.getClusterNodeId() + StringPool.UNDERLINE +
					entry.getKey(),
				entry.getValue());
		}
	}

	private static final String _PROXY_PASSWORD = GetterUtil.getString(
		PropsUtil.get("license.proxy.password"));

	private static final int _PROXY_PORT = GetterUtil.getInteger(
		PropsUtil.get("license.proxy.port"), 80);

	private static final String _PROXY_URL = PropsUtil.get("license.proxy.url");

	private static final String _PROXY_USER_NAME = GetterUtil.getString(
		PropsUtil.get("license.proxy.username"));

	private static final Log _log = LogFactoryUtil.getLog(LicenseUtil.class);

	private static String _encryptedSymmetricKey;
	private static final MethodHandler _getServerInfoMethodHandler =
		new MethodHandler(new MethodKey(LicenseUtil.class, "getServerInfo"));
	private static Set<String> _ipAddresses;
	private static Set<String> _macAddresses;
	private static final MethodKey _registerOrderMethodKey = new MethodKey(
		LicenseUtil.class, "registerOrder", String.class, String.class,
		int.class);
	private static byte[] _serverIdBytes;
	private static Key _symmetricKey;

	static {
		_initKeys();
	}

}