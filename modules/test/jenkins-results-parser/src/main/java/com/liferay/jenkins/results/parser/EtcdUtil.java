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

	public static void delete(
		EtcdClient etcdClient, EtcdKeysResponse.EtcdNode node) {

		try {
			EtcdKeyDeleteRequest etcdKeyDeleteRequest = null;

			List<EtcdKeysResponse.EtcdNode> nodes = node.getNodes();

			if (nodes.size() > 0) {
				etcdKeyDeleteRequest = etcdClient.deleteDir(node.getKey());

				etcdKeyDeleteRequest.recursive();
			}
			else {
				etcdKeyDeleteRequest = etcdClient.delete(node.getKey());
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

	public static EtcdKeysResponse.EtcdNode get(
		EtcdClient etcdClient, String key) {

		try {
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
	
	public static EtcdClient getEtcdClient(String url) {
		try {
			return new EtcdClient(new URI(url));
		}
		catch (URISyntaxException se) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create an Etcd client using url " , url),
				se);
		}
	}

	public static EtcdKeysResponse.EtcdNode put(
		EtcdClient etcdClient, String key) {

		return put(etcdClient, key, null);
	}

	public static EtcdKeysResponse.EtcdNode put(
		EtcdClient etcdClient, String key, String value) {

		try {
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

			return etcdKeysResponse.getNode();
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
	}

}