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
		_deleteEtcdNode(etcdServerURL, key);
	}

	public static synchronized Node get(String etcdServerURL, String key) {
		EtcdKeysResponse.EtcdNode etcdNode = _getEtcdNode(etcdServerURL, key);

		if (etcdNode != null) {
			return new Node(etcdServerURL, etcdNode);
		}

		return null;
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

		return new Node(etcdServerURL, _putEtcdNode(etcdServerURL, key, value));
	}

	public static class Node {

		public void delete() {
			_deleteEtcdNode(getEtcdServerURL(), getKey());
		}

		public boolean exists() {
			_refreshEtcdNode();

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
			return _key;
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
				nodes.add(new Node(getEtcdServerURL(), childEtcdNode));
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
			_key = etcdNode.getKey();
		}

		private synchronized void _refreshEtcdNode() {
			_etcdNode = _getEtcdNode(getEtcdServerURL(), getKey());

			if (_etcdNode != null) {
				return;
			}

			for (int i = 0; i < _RETRIES_SIZE_MAX_DEFAULT; i++) {
				JenkinsResultsParserUtil.sleep(_SECONDS_RETRY_PERIOD_DEFAULT);

				_etcdNode = _getEtcdNode(getEtcdServerURL(), getKey());

				if (_etcdNode != null) {
					return;
				}
			}
		}

		private EtcdKeysResponse.EtcdNode _etcdNode;
		private final String _etcdServerURL;
		private final String _key;

	}

	private static synchronized void _deleteEtcdNode(
		String etcdServerURL, String key) {

		EtcdKeysResponse.EtcdNode etcdNode = _getEtcdNode(etcdServerURL, key);

		if (etcdNode == null) {
			return;
		}

		int retryCount = 0;

		while (true) {
			try (EtcdClient etcdClient = _getEtcdClient(etcdServerURL)) {
				EtcdKeyDeleteRequest etcdKeyDeleteRequest = null;

				if (!etcdNode.isDir()) {
					etcdKeyDeleteRequest = etcdClient.delete(key);
				}
				else {
					etcdKeyDeleteRequest = etcdClient.deleteDir(key);

					etcdKeyDeleteRequest.recursive();
				}

				EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
					etcdKeyDeleteRequest.send();

				etcdResponsePromise.get();

				return;
			}
			catch (EtcdAuthenticationException | EtcdException | IOException |
				   TimeoutException e) {

				e.printStackTrace();
			}

			retryCount++;

			if (retryCount > _RETRIES_SIZE_MAX_DEFAULT) {
				throw new RuntimeException(
					"Unable to delete " + key + " from " + etcdServerURL);
			}

			System.out.println(
				"Retrying in " + _SECONDS_RETRY_PERIOD_DEFAULT + " seconds");

			JenkinsResultsParserUtil.sleep(
				1000 * _SECONDS_RETRY_PERIOD_DEFAULT);
		}
	}

	private static EtcdClient _getEtcdClient(String url) {
		try {
			return new EtcdClient(new URI(url));
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create an etcd client for ", url),
				urise);
		}
	}

	private static synchronized EtcdKeysResponse.EtcdNode _getEtcdNode(
		String etcdServerURL, String key) {

		int retryCount = 0;

		while (true) {
			try (EtcdClient etcdClient = _getEtcdClient(etcdServerURL)) {
				EtcdKeyGetRequest etcdKeyGetRequest = etcdClient.get(key);

				EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
					etcdKeyGetRequest.send();

				EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

				return etcdKeysResponse.getNode();
			}
			catch (EtcdAuthenticationException | EtcdException | IOException |
				   TimeoutException e) {

				String errorMessage = e.getMessage();

				if (errorMessage.contains("Key not found")) {
					return null;
				}
			}

			retryCount++;

			if (retryCount > _RETRIES_SIZE_MAX_DEFAULT) {
				throw new RuntimeException(
					"Unable to find " + key + " from " + etcdServerURL);
			}

			System.out.println(
				"Retrying in " + _SECONDS_RETRY_PERIOD_DEFAULT + " seconds");

			JenkinsResultsParserUtil.sleep(
				1000 * _SECONDS_RETRY_PERIOD_DEFAULT);
		}
	}

	private static synchronized EtcdKeysResponse.EtcdNode _putEtcdNode(
		String etcdServerURL, String key, String value) {

		int retryCount = 0;

		while (true) {
			try (EtcdClient etcdClient = _getEtcdClient(etcdServerURL)) {
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

				return etcdKeysResponse.getNode();
			}
			catch (EtcdAuthenticationException | EtcdException | IOException |
				   TimeoutException e) {

				e.printStackTrace();
			}

			retryCount++;

			if (retryCount > _RETRIES_SIZE_MAX_DEFAULT) {
				throw new RuntimeException(
					"Unable to put " + key + " into " + etcdServerURL);
			}

			System.out.println(
				"Retrying in " + _SECONDS_RETRY_PERIOD_DEFAULT + " seconds");

			JenkinsResultsParserUtil.sleep(
				1000 * _SECONDS_RETRY_PERIOD_DEFAULT);
		}
	}

	private static final int _RETRIES_SIZE_MAX_DEFAULT = 5;

	private static final int _SECONDS_RETRY_PERIOD_DEFAULT = 5;

}