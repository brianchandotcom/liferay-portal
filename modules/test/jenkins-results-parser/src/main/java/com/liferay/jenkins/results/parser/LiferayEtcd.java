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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import mousio.etcd4j.EtcdClient;
import mousio.etcd4j.promises.EtcdResponsePromise;
import mousio.etcd4j.requests.EtcdKeyGetRequest;
import mousio.etcd4j.responses.EtcdAuthenticationException;
import mousio.etcd4j.responses.EtcdException;
import mousio.etcd4j.responses.EtcdKeysResponse;

/**
 * @author Michael Hashimoto
 */
public class LiferayEtcd {

	public LiferayEtcd() {
		this("http://127.0.0.1:2379");
	}

	public LiferayEtcd(String url) {
		setURL(url);
	}

	public void setURL(String url) {
		_uri = URI.create(url);
	}

	public static class Dir extends Node {

		public Dir(LiferayEtcd liferayEtcd, String key) {
			this(liferayEtcd, liferayEtcd.getEtcdNode(key));
		}

		public List<LiferayEtcd.Node> getLiferayEtcdNodes() {
			List<Node> liferayEtcdNodes = new ArrayList<>();

			EtcdKeysResponse.EtcdNode etcdNode = liferayEtcd.getEtcdNode(key);

			for (EtcdKeysResponse.EtcdNode childEtcdNode :
					etcdNode.getNodes()) {

				if (childEtcdNode.isDir()) {
					liferayEtcdNodes.add(new Dir(liferayEtcd, childEtcdNode));
				}
				else {
					liferayEtcdNodes.add(new Value(liferayEtcd, childEtcdNode));
				}
			}

			return liferayEtcdNodes;
		}

		@Override
		public String toString() {
			return key + "={dir}";
		}

		protected Dir(
			LiferayEtcd liferayEtcd, EtcdKeysResponse.EtcdNode etcdNode) {

			super(liferayEtcd, etcdNode.getKey());

			if (!etcdNode.isDir()) {
				throw new RuntimeException(key + " is not a dir");
			}
		}

	}

	public abstract static class Node {

		public String getKey() {
			return key;
		}

		protected Node(LiferayEtcd liferayEtcd, String key) {
			this.liferayEtcd = liferayEtcd;
			this.key = key;
		}

		protected final String key;
		protected final LiferayEtcd liferayEtcd;

	}

	public static class Value extends Node {

		public Value(LiferayEtcd liferayEtcd, String key) {
			this(liferayEtcd, liferayEtcd.getEtcdNode(key));
		}

		public String getValue() {
			EtcdKeysResponse.EtcdNode etcdNode = liferayEtcd.getEtcdNode(key);

			return etcdNode.getValue();
		}

		@Override
		public String toString() {
			return key + "=" + getValue();
		}

		protected Value(
			LiferayEtcd liferayEtcd, EtcdKeysResponse.EtcdNode etcdNode) {

			super(liferayEtcd, etcdNode.getKey());

			if (etcdNode.isDir()) {
				throw new RuntimeException(key + " is not a value");
			}
		}

	}

	protected EtcdClient getEtcdClient() {
		return new EtcdClient(_uri);
	}

	protected EtcdKeysResponse.EtcdNode getEtcdNode(String key) {
		try (EtcdClient etcdClient = getEtcdClient()) {
			EtcdKeyGetRequest etcdKeyGetRequest = etcdClient.get(key);

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyGetRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return etcdKeysResponse.getNode();
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
	}

	private URI _uri;

}