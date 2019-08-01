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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
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

	public static Set<String> getIpAddresses() {
		return _ipAddresses;
	}

	public static Set<String> getMacAddresses() {
		return _macAddresses;
	}

	public static int getProcessorCores() {
		Runtime runtime = Runtime.getRuntime();

		return runtime.availableProcessors();
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
		serverInfo.put("processorCores", String.valueOf(getProcessorCores()));

		return serverInfo;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void init() {
	}

	public static void registerOrder(HttpServletRequest httpServletRequest) {
		String orderUuid = ParamUtil.getString(httpServletRequest, "orderUuid");
		String productEntryName = ParamUtil.getString(
			httpServletRequest, "productEntryName");
		int maxServers = ParamUtil.getInteger(httpServletRequest, "maxServers");

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		if ((clusterNodes == null) || (clusterNodes.size() <= 1) ||
			Validator.isNull(productEntryName) || Validator.isNull(orderUuid)) {

			Map<String, Object> attributes = registerOrder(
				orderUuid, productEntryName, maxServers);

			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				httpServletRequest.setAttribute(
					entry.getKey(), entry.getValue());
			}
		}
		else {
			for (ClusterNode clusterNode : clusterNodes) {
				boolean register = ParamUtil.getBoolean(
					httpServletRequest,
					clusterNode.getClusterNodeId() + "_register");

				if (!register) {
					continue;
				}

				try {
					_registerClusterOrder(
						httpServletRequest, clusterNode, orderUuid,
						productEntryName, maxServers);
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

					httpServletRequest.setAttribute(
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
			new BasicHttpClientConnectionManager(
				RegistryBuilder.<ConnectionSocketFactory>create(
				).register(
					Http.HTTP, PlainConnectionSocketFactory.getSocketFactory()
				).register(
					Http.HTTPS,
					SSLConnectionSocketFactory.getSystemSocketFactory()
				).build());

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
						StringBundler.concat(
							"Using proxy ", _PROXY_URL, StringPool.COLON,
							_PROXY_PORT));
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
				request.getBytes(StringPool.UTF8));

			byteArrayEntity.setContentType(ContentTypes.APPLICATION_JSON);

			httpPost.setEntity(byteArrayEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();

			String response = StringUtil.read(httpEntity.getContent());

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

		jsonObject.put(
			"liferayVersion", ReleaseInfo.getBuildNumber()
		).put(
			"orderUuid", orderUuid
		).put(
			"version", 2
		);

		if (Validator.isNull(productEntryName)) {
			jsonObject.put(Constants.CMD, "QUERY");
		}
		else {
			jsonObject.put(Constants.CMD, "REGISTER");

			if (productEntryName.startsWith("basic")) {
				jsonObject.put("productEntryName", "basic");

				if (productEntryName.equals("basic-cluster")) {
					jsonObject.put(
						"cluster", true
					).put(
						"maxServers", maxServers
					);
				}
				else if (productEntryName.startsWith("basic-")) {
					String[] productNameArray = StringUtil.split(
						productEntryName, StringPool.DASH);

					if (productNameArray.length >= 3) {
						jsonObject.put(
							"clusterId", productNameArray[2]
						).put(
							"offeringEntryId", productNameArray[1]
						);
					}
				}
			}
			else {
				jsonObject.put("productEntryName", productEntryName);
			}

			jsonObject.put(
				"hostName", PortalUtil.getComputerName()
			).put(
				"ipAddresses", StringUtil.merge(getIpAddresses())
			).put(
				"macAddresses", StringUtil.merge(getMacAddresses())
			).put(
				"processorCores", getProcessorCores()
			).put(
				"serverId", Arrays.toString(getServerIdBytes())
			);
		}

		return jsonObject;
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

	private static void _registerClusterOrder(
			HttpServletRequest httpServletRequest, ClusterNode clusterNode,
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
			httpServletRequest.setAttribute(
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

	private static final MethodHandler _getServerInfoMethodHandler =
		new MethodHandler(new MethodKey(LicenseUtil.class, "getServerInfo"));
	private static final Set<String> _ipAddresses;
	private static final Set<String> _macAddresses;
	private static final MethodKey _registerOrderMethodKey = new MethodKey(
		LicenseUtil.class, "registerOrder", String.class, String.class,
		int.class);
	private static byte[] _serverIdBytes;

	static {
		Set<String> ipAddresses = new HashSet<>();

		Set<String> macAddresses = new HashSet<>();

		try {
			Enumeration<NetworkInterface> networkInterfaceEnumeration =
				NetworkInterface.getNetworkInterfaces();

			while (networkInterfaceEnumeration.hasMoreElements()) {
				NetworkInterface networkInterface =
					networkInterfaceEnumeration.nextElement();

				Enumeration<InetAddress> inetAddressEnumeration =
					networkInterface.getInetAddresses();

				while (inetAddressEnumeration.hasMoreElements()) {
					InetAddress inetAddress =
						inetAddressEnumeration.nextElement();

					if (inetAddress.isLinkLocalAddress() ||
						inetAddress.isLoopbackAddress() ||
						!(inetAddress instanceof Inet4Address)) {

						continue;
					}

					ipAddresses.add(inetAddress.getHostAddress());
				}

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

				macAddresses.add(sb.toString());
			}
		}
		catch (SocketException se) {
			_log.error("Unable to read local server network interfaces", se);
		}

		_ipAddresses = Collections.unmodifiableSet(ipAddresses);

		_macAddresses = Collections.unmodifiableSet(macAddresses);
	}

}