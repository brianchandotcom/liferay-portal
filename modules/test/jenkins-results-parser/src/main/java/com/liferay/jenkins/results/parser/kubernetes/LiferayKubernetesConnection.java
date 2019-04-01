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

package com.liferay.jenkins.results.parser.kubernetes;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1Status;
import io.kubernetes.client.util.Config;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class LiferayKubernetesConnection {

	public static AppsV1Api apps;
	public static CoreV1Api core;

	public static Pod createPod(
		String dockerBaseImageName, String dockerImageName, String namespace) {

		Pod configuredMySQLPod =
			ResourceConfigurationFactory.newConfiguredMySQLPod(
				dockerBaseImageName, dockerImageName);

		try {
			return new Pod(
				core.createNamespacedPod(
					namespace, configuredMySQLPod.getV1Pod(), null));
		}
		catch (ApiException ae) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to create new pod with name '",
					configuredMySQLPod.getName(), "' in namespace '", namespace,
					"'"));

			ae.printStackTrace();

			return null;
		}
	}

	public static LiferayKubernetesConnection getInstance() {
		if (_liferayKubernetesApi == null) {
			_liferayKubernetesApi = new LiferayKubernetesConnection();
		}

		return _liferayKubernetesApi;
	}

	public Pod createPod(String dockerBaseImageName, String dockerImageName) {
		return createPod(dockerBaseImageName, dockerImageName, "default");
	}

	public boolean deletePod(Pod pod) {
		return deletePod(pod, "default");
	}

	public boolean deletePod(Pod pod, String namespace) {
		V1Status v1Status;

		try {
			v1Status = core.deleteNamespacedPod(
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

		String status = v1Status.getStatus();

		if (status.equals("Success")) {
			return true;
		}

		return false;
	}

	public Pod getPod(
		String dockerBaseImageName, String dockerImageName, String namespace) {

		Pod configuredMySQLPod =
			ResourceConfigurationFactory.newConfiguredMySQLPod(
				dockerBaseImageName, dockerImageName);

		try {
			return new Pod(
				core.readNamespacedPod(
					configuredMySQLPod.getName(), namespace, null, true,
					false));
		}
		catch (ApiException ae) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to get pod with name '",
					configuredMySQLPod.getName(), "' in namespace '", namespace,
					"'"));

			ae.printStackTrace();

			return null;
		}
	}

	public List<Pod> getPods() {
		V1PodList v1PodList;

		try {
			v1PodList = core.listPodForAllNamespaces(
				null, null, null, null, null, null, null, null, null);
		}
		catch (ApiException ae) {
			throw new RuntimeException("Unable to get pods", ae);
		}

		List<V1Pod> v1Pods = v1PodList.getItems();

		List<LiferayKubernetesConnection.Pod> pods = new ArrayList<>(
			v1Pods.size());

		for (V1Pod v1Pod : v1Pods) {
			pods.add(new Pod(v1Pod));
		}

		return pods;
	}

	public static class Pod {

		public String getName() {
			V1ObjectMeta meta = _v1Pod.getMetadata();

			return meta.getName();
		}

		protected Pod(V1Pod v1Pod) {
			_v1Pod = v1Pod;
		}

		protected V1Pod getV1Pod() {
			return _v1Pod;
		}

		private final V1Pod _v1Pod;

	}

	private static final ApiClient _apiClient;
	private static LiferayKubernetesConnection _liferayKubernetesApi;

	static {
		try {
			_apiClient = Config.defaultClient();

			Configuration.setDefaultApiClient(_apiClient);

			_apiClient.setDebugging(true);

			apps = new AppsV1Api(_apiClient);
			core = new CoreV1Api(_apiClient);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}