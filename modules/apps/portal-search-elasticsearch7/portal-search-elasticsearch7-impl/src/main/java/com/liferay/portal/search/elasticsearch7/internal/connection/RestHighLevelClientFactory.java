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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.util.ClassLoaderUtil;

import java.io.IOException;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.http.HttpHost;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;

/**
 * @author André de Oliveira
 */
public class RestHighLevelClientFactory {

	public static Builder builder() {
		return new Builder();
	}

	public Optional<RestHighLevelClient> getRestHighLevelClientOptional() {
		return IntStream.rangeClosed(
			_portFrom, _portTo
		).mapToObj(
			this::newRestHighLevelClient
		).filter(
			this::isMatch
		).findFirst();
	}

	public static class Builder {

		public RestHighLevelClientFactory build() {
			return new RestHighLevelClientFactory(_restHighLevelClientFactory);
		}

		public Builder clusterName(String clusterName) {
			_restHighLevelClientFactory._clusterName = clusterName;

			return this;
		}

		public Builder hostName(String hostName) {
			_restHighLevelClientFactory._hostName = hostName;

			return this;
		}

		public Builder httpPortRange(HttpPortRange httpPortRange) {
			int[] ports = getPorts(httpPortRange.toSettingsString());

			_restHighLevelClientFactory._portFrom = ports[0];
			_restHighLevelClientFactory._portTo = ports[1];

			return this;
		}

		public Builder nodeName(String nodeName) {
			_restHighLevelClientFactory._nodeName = nodeName;

			return this;
		}

		public Builder scheme(String scheme) {
			_restHighLevelClientFactory._scheme = scheme;

			return this;
		}

		protected int[] getPorts(String portRange) {
			String[] split = StringUtil.split(portRange, CharPool.DASH);

			if (split.length >= 2) {
				return new int[] {
					Integer.valueOf(split[0]), Integer.valueOf(split[1])
				};
			}

			return new int[] {
				Integer.valueOf(split[0]), Integer.valueOf(split[0])
			};
		}

		private final RestHighLevelClientFactory _restHighLevelClientFactory =
			new RestHighLevelClientFactory();

	}

	protected static boolean isClusterName(
		String clusterName, MainResponse mainResponse) {

		if (Validator.isBlank(clusterName)) {
			return true;
		}

		if (Objects.equals(clusterName, mainResponse.getClusterName())) {
			return true;
		}

		return false;
	}

	protected static boolean isNodeName(
		String nodeName, MainResponse mainResponse) {

		if (Validator.isBlank(nodeName)) {
			return true;
		}

		if (Objects.equals(nodeName, mainResponse.getNodeName())) {
			return true;
		}

		return false;
	}

	protected boolean isMatch(MainResponse mainResponse) {
		if (Validator.isBlank(_clusterName) && Validator.isBlank(_nodeName)) {
			return true;
		}

		if (!isClusterName(_clusterName, mainResponse)) {
			return false;
		}

		if (!isNodeName(_nodeName, mainResponse)) {
			return false;
		}

		return true;
	}

	protected boolean isMatch(RestHighLevelClient restHighLevelClient) {
		try {
			MainResponse mainResponse = restHighLevelClient.info(
				RequestOptions.DEFAULT);

			if (isMatch(mainResponse)) {
				return true;
			}

			return false;
		}
		catch (IOException ioException) {
			return false;
		}
	}

	protected RestHighLevelClient newRestHighLevelClient(int port) {
		return ClassLoaderUtil.getWithContextClassLoader(
			() -> new RestHighLevelClient(
				RestClient.builder(
					new HttpHost(_hostName, port, _scheme)
				).setRequestConfigCallback(
					requestConfigBuilder ->
						requestConfigBuilder.setSocketTimeout(120000)
				)),
			getClass());
	}

	private RestHighLevelClientFactory() {
	}

	private RestHighLevelClientFactory(
		RestHighLevelClientFactory restHighLevelClientFactory) {

		_clusterName = restHighLevelClientFactory._clusterName;
		_hostName = restHighLevelClientFactory._hostName;
		_nodeName = restHighLevelClientFactory._nodeName;
		_portFrom = restHighLevelClientFactory._portFrom;
		_portTo = restHighLevelClientFactory._portTo;
		_scheme = restHighLevelClientFactory._scheme;
	}

	private String _clusterName;
	private String _hostName;
	private String _nodeName;
	private int _portFrom;
	private int _portTo;
	private String _scheme;

}