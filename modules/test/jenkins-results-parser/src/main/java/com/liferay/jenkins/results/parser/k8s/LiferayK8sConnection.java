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

package com.liferay.jenkins.results.parser.k8s;

import com.google.gson.JsonSyntaxException;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1Status;
import io.kubernetes.client.util.Config;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class LiferayK8sConnection {

	public static LiferayK8sConnection getInstance() {
		if (_liferayK8sConnection == null) {
			_liferayK8sConnection = new LiferayK8sConnection();
		}

		return _liferayK8sConnection;
	}

	public Pod createPod(Pod configurationPod) {
		return createPod(configurationPod, getNamespace());
	}

	public Pod createPod(Pod configurationPod, String namespace) {
		try {
			return new Pod(
				_coreV1Api.createNamespacedPod(
					namespace, configurationPod.getV1Pod(), null));
		}
		catch (ApiException ae) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to create new pod with name '",
					configurationPod.getName(), "' in namespace '", namespace,
					"'"));

			ae.printStackTrace();

			return null;
		}
	}

	public boolean deletePod(Pod pod) {
		return deletePod(pod, getNamespace());
	}

	public boolean deletePod(Pod pod, String namespace) {
		V1Status v1Status = null;

		try {
			v1Status = _coreV1Api.deleteNamespacedPod(
				pod.getName(), namespace, new V1DeleteOptions(), null, 60, true,
				null);
		}
		catch (ApiException ae) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete pod with name '", pod.getName(),
					"' in namespace '", namespace, "'"));

			ae.printStackTrace();

			return false;
		}
		catch (JsonSyntaxException jse) {
			String message = jse.getMessage();

			if (message == null) {
				return false;
			}

			if (message.contains("Expected a string but was BEGIN_OBJECT")) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Successfully deleted pod with name '", pod.getName(),
						"' in namespace '", namespace, "'"));

				return true;
			}

			return false;
		}

		if (v1Status != null) {
			String status = v1Status.getStatus();

			if (status.equals("Success")) {
				return true;
			}
		}

		return false;
	}

	public String getNamespace() {
		String namespace = null;

		try {
			File file = new File(
				"/var/run/secrets/kubernetes.io/serviceaccount/namespace");

			if (file.exists()) {
				namespace = JenkinsResultsParserUtil.read(file);
			}
		}
		catch (IOException ioe) {
			System.out.println("Unable to read namespace file");
		}

		if (namespace == null) {
			namespace = "default";
		}

		return namespace.trim();
	}

	public Pod getPod(Pod pod, String namespace) {
		try {
			return new Pod(
				_coreV1Api.readNamespacedPod(
					pod.getName(), namespace, null, true, false));
		}
		catch (ApiException ae) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to get pod with name '", pod.getName(),
					"' in namespace '", namespace, "'"));

			ae.printStackTrace();

			return null;
		}
	}

	public List<Pod> getPods() {
		V1PodList v1PodList = null;

		try {
			v1PodList = _coreV1Api.listPodForAllNamespaces(
				null, null, null, null, null, null, null, null, null);
		}
		catch (ApiException ae) {
			throw new RuntimeException("Unable to get pods", ae);
		}

		List<V1Pod> v1Pods = v1PodList.getItems();

		List<Pod> pods = new ArrayList<>(v1Pods.size());

		for (V1Pod v1Pod : v1Pods) {
			pods.add(new Pod(v1Pod));
		}

		return pods;
	}

	private LiferayK8sConnection() {
	}

	private static final ApiClient _apiClient;
	private static final CoreV1Api _coreV1Api;
	private static LiferayK8sConnection _liferayK8sConnection;

	static {
		try {
			_apiClient = Config.defaultClient();

			Configuration.setDefaultApiClient(_apiClient);

			_apiClient.setDebugging(true);

			_coreV1Api = new CoreV1Api(_apiClient);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}