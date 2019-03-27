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

	public static void delete(String etcdServerURL, Node node) {
		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyDeleteRequest etcdKeyDeleteRequest = null;

			if (!node.isDir()) {
				etcdKeyDeleteRequest = etcdClient.delete(node.getKey());
			}
			else {
				etcdKeyDeleteRequest = etcdClient.deleteDir(node.getKey());

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

	public static Node get(String etcdServerURL, String key) {
		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyGetRequest etcdKeyGetRequest = etcdClient.get(key);

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyGetRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return new Node(etcdKeysResponse.getNode());
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
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

	public static Node put(String etcdServerURL, String key) {
		return put(etcdServerURL, key, null);
	}

	public static Node put(String etcdServerURL, String key, String value) {
		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyPutRequest etcdKeyPutRequest = null;

			if (value == null) {
				etcdKeyPutRequest = etcdClient.putDir(key);
			}
			else {
				etcdKeyPutRequest = etcdClient.put(key, value);
			}

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyPutRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return new Node(etcdKeysResponse.getNode());
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
	}

	public static class Node {

		public Node(String key, String value) {
			this(key);

			_value = value;
		}

		public void addNode(Node node) {
			if (_value != null) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Node ", getKey(), " is not a directory Node."));
			}

			_childNodes.add(node);
		}

		public String getKey() {
			return _key;
		}

		public List<Node> getNodes() {
			return _childNodes;
		}

		public String getValue() {
			return _value;
		}

		public boolean isDir() {
			if (!_childNodes.isEmpty()) {
				return true;
			}

			return false;
		}

		public void removeNode(Node node) {
			_childNodes.remove(node);
		}

		private Node(EtcdKeysResponse.EtcdNode etcdNode) {
			this(etcdNode.getKey());

			if (etcdNode.isDir()) {
				List<EtcdKeysResponse.EtcdNode> childEtcdNodes =
					etcdNode.getNodes();

				for (EtcdKeysResponse.EtcdNode childEtcdNode : childEtcdNodes) {
					addNode(new Node(childEtcdNode));
				}
			}
			else {
				_value = etcdNode.getValue();
			}
		}

		private Node(String key) {
			_key = key;
		}

		private final List<Node> _childNodes = new ArrayList<>();
		private String _key;
		private String _value;

	}

}