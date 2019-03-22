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