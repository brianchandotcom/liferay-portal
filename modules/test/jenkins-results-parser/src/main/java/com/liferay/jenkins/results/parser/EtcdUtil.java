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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import mousio.etcd4j.EtcdClient;
import mousio.etcd4j.promises.EtcdResponsePromise;
import mousio.etcd4j.requests.EtcdKeyDeleteRequest;
import mousio.etcd4j.requests.EtcdKeyGetRequest;
import mousio.etcd4j.requests.EtcdKeyPutRequest;
import mousio.etcd4j.responses.EtcdAuthenticationException;
import mousio.etcd4j.responses.EtcdException;
import mousio.etcd4j.responses.EtcdKeysResponse;

/**
 * @author Michael Hashimoto
 */
public class EtcdUtil {

	public static synchronized void delete(String etcdServerURL, String key) {
		Node node = get(etcdServerURL, key);

		if (node != null) {
			node.delete();
		}
	}

	public static synchronized Node get(String etcdServerURL, String key) {
		EtcdKeysResponse.EtcdNode etcdNode = _getEtcdNode(etcdServerURL, key);

		if (etcdNode != null) {
			return new Node(etcdServerURL, etcdNode);
		}

		return null;
	}

	public static EtcdClient getEtcdClient(String url) {
		try {
			return new EtcdClient(new URI(url));
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create an Etcd client using url ", url),
				urise);
		}
	}

	public static synchronized boolean has(String etcdServerURL, String key) {
		Node node = get(etcdServerURL, key);

		if (node == null) {
			return false;
		}

		return true;
	}

	public static synchronized Node put(String etcdServerURL, String key) {
		return put(etcdServerURL, key, null);
	}

	public static synchronized Node put(
		String etcdServerURL, String key, String value) {

		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyPutRequest etcdKeyPutRequest = null;

			if (value == null) {
				etcdKeyPutRequest = etcdClient.putDir(key);
			}
			else {
				etcdKeyPutRequest = etcdClient.put(key, value);
			}

			if (has(etcdServerURL, key)) {
				etcdKeyPutRequest.prevExist(true);
			}

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyPutRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return new Node(etcdServerURL, etcdKeysResponse.getNode());
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
	}

	public static class Node {

		public void delete() {
			try (EtcdClient etcdClient = getEtcdClient(_etcdServerURL)) {
				EtcdKeyDeleteRequest etcdKeyDeleteRequest = null;

				if (!isDir()) {
					etcdKeyDeleteRequest = etcdClient.delete(getKey());
				}
				else {
					etcdKeyDeleteRequest = etcdClient.deleteDir(getKey());

					etcdKeyDeleteRequest.recursive();
				}

				EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
					etcdKeyDeleteRequest.send();

				etcdResponsePromise.get();
			}
			catch (EtcdAuthenticationException | EtcdException | IOException |
				   TimeoutException e) {

				throw new RuntimeException(e);
			}
		}

		public boolean exists() {
			_refreshEtcdNode(true);

			if (_etcdNode != null) {
				return true;
			}

			return false;
		}

		public long getCreatedIndex() {
			return _etcdNode.getCreatedIndex();
		}

		public String getEtcdServerURL() {
			return _etcdServerURL;
		}

		public String getKey() {
			return _etcdNode.getKey();
		}

		public long getModifiedIndex() {
			_refreshEtcdNode();

			return _etcdNode.getModifiedIndex();
		}

		public int getNodeCount() {
			List<Node> nodes = getNodes();

			return nodes.size();
		}

		public List<Node> getNodes() {
			_refreshEtcdNode();

			List<Node> nodes = new ArrayList<>();

			List<EtcdKeysResponse.EtcdNode> childEtcdNodes =
				_etcdNode.getNodes();

			for (EtcdKeysResponse.EtcdNode childEtcdNode : childEtcdNodes) {
				nodes.add(new Node(_etcdServerURL, childEtcdNode));
			}

			return nodes;
		}

		public String getValue() {
			if (!isDir()) {
				_refreshEtcdNode();

				return _etcdNode.getValue();
			}

			return null;
		}

		public boolean isDir() {
			return _etcdNode.isDir();
		}

		private Node(String etcdServerURL, EtcdKeysResponse.EtcdNode etcdNode) {
			_etcdServerURL = etcdServerURL;
			_etcdNode = etcdNode;
		}

		private synchronized void _refreshEtcdNode() {
			_refreshEtcdNode(false);
		}

		private synchronized void _refreshEtcdNode(boolean exceptionOnFail) {
			int retryCount = 0;

			while (true) {
				_etcdNode = _getEtcdNode(_etcdServerURL, getKey());

				if (!exceptionOnFail || (_etcdNode != null)) {
					return;
				}

				retryCount++;

				if (retryCount > _RETRIES_SIZE_MAX_DEFAULT) {
					throw new RuntimeException(
						"Could not find " + getKey() + " from " +
							getEtcdServerURL());
				}

				System.out.println(
					"Retrying node refresh in " +
						_SECONDS_RETRY_PERIOD_DEFAULT + " seconds");

				JenkinsResultsParserUtil.sleep(
					1000 * _SECONDS_RETRY_PERIOD_DEFAULT);
			}
		}

		private static final int _RETRIES_SIZE_MAX_DEFAULT = 3;

		private static final int _SECONDS_RETRY_PERIOD_DEFAULT = 5;

		private EtcdKeysResponse.EtcdNode _etcdNode;
		private final String _etcdServerURL;

	}

	private static synchronized EtcdKeysResponse.EtcdNode _getEtcdNode(
		String etcdServerURL, String key) {

		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyGetRequest etcdKeyGetRequest = etcdClient.get(key);

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyGetRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return etcdKeysResponse.getNode();
		}
		catch (EtcdException ee) {
			return null;
		}
		catch (EtcdAuthenticationException | IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

}