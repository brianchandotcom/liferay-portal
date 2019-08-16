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
import com.liferay.jenkins.results.parser.Retryable;

import com.squareup.okhttp.OkHttpClient;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	public Boolean assertPodNotFound(final Pod pod, final String namespace) {
		Retryable<Boolean> retryable = new Retryable<Boolean>() {

			public Boolean execute() {
				return _assertPodNotFound(pod, namespace);
			}

		};

		return retryable.executeWithRetries();
	}

	public Boolean assertPodRunning(Pod pod) {
		String phase = pod.getPhase();

		if (phase == null) {
			return false;
		}

		if (phase.equals("Running")) {
			return true;
		}

		return false;
	}

	public Pod createPod(Pod configurationPod) {
		return createPod(configurationPod, getNamespace());
	}

	public Pod createPod(final Pod configurationPod, final String namespace) {
		Retryable<Pod> retryable = new Retryable<Pod>() {

			public Pod execute() {
				return _createPod(configurationPod, namespace);
			}

		};

		return retryable.executeWithRetries();
	}

	public Boolean deletePod(Pod pod) {
		return deletePod(pod, getNamespace());
	}

	public Boolean deletePod(final Pod pod, final String namespace) {
		Retryable<Boolean> retryable = new Retryable<Boolean>() {

			public Boolean execute() {
				return _deletePod(pod, namespace);
			}

		};

		return retryable.executeWithRetries();
	}

	public boolean deleteSpawnedPods() {
		deleteSpawnedPods(getNamespace());

		return false;
	}

	public boolean deleteSpawnedPods(String namespace) {
		List<Pod> pods = getPods(namespace);

		for (Pod pod : pods) {
			String podName = pod.getName();

			if (podName.startsWith(
					ResourceConfigurationFactory.getPodPrefix())) {

				for (String key :
						ResourceConfigurationFactory.
							getPodConfigurationKeys()) {

					if (podName.endsWith(key)) {
						deletePod(pod, namespace);
					}
				}
			}
		}

		return false;
	}

	public String getNamespace() {
		try {
			File file = new File(
				"/var/run/secrets/kubernetes.io/serviceaccount/namespace");

			if (file.exists()) {
				String contents = JenkinsResultsParserUtil.read(file);

				return contents.trim();
			}
		}
		catch (IOException ioe) {
			System.out.println("Unable to read namespace file");
		}

		return "default";
	}

	public Pod getPod(Pod pod) {
		return getPod(pod, getNamespace());
	}

	public Pod getPod(final Pod pod, final String namespace) {
		Retryable<Pod> retryable = new Retryable<Pod>() {

			public Pod execute() {
				return _getPod(pod, namespace);
			}

		};

		return retryable.executeWithRetries();
	}

	public List<Pod> getPods() {
		Retryable<List<Pod>> retryable = new Retryable<List<Pod>>() {

			public List<Pod> execute() {
				return _getPods();
			}

		};

		return retryable.executeWithRetries();
	}

	public List<Pod> getPods(final String namespace) {
		Retryable<List<Pod>> retryable = new Retryable<List<Pod>>() {

			public List<Pod> execute() {
				return _getPods(namespace);
			}

		};

		return retryable.executeWithRetries();
	}

	public void setDebugging(boolean debugging) {
		_apiClient.setDebugging(debugging);
	}

	public Boolean waitForPodNotFound(final Pod pod, final String namespace) {
		Retryable<Boolean> retryable = new Retryable<Boolean>(
			_MAX_RETRIES, _SECONDS_RETRY_PERIOD, false) {

			public Boolean execute() {
				if (assertPodNotFound(_currentPod, namespace)) {
					return true;
				}

				_currentPod = _getPod(pod, namespace);

				throw new RuntimeException("Pod still exists");
			}

			private Pod _currentPod = pod;

		};

		long start = System.currentTimeMillis();

		boolean found = !retryable.executeWithRetries();

		if (found) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Pod still exists after waiting ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));
		}

		return !found;
	}

	public Boolean waitForPodRunning(final Pod pod) {
		Retryable<Boolean> retryable = new Retryable<Boolean>(
			_MAX_RETRIES, _SECONDS_RETRY_PERIOD, false) {

			public Boolean execute() {
				pod.refreshV1Pod();

				if (assertPodRunning(pod)) {
					return true;
				}

				throw new RuntimeException("Pod not running");
			}

		};

		long start = System.currentTimeMillis();

		boolean running = retryable.executeWithRetries();

		if (!running) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Pod not running after waiting ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));
		}

		return running;
	}

	protected Pod newPod(V1Pod v1Pod) {
		V1ObjectMeta v1ObjectMeta = v1Pod.getMetadata();

		String podName = v1ObjectMeta.getName();

		if (podName.contains("oracle")) {
			return new OraclePod(v1Pod);
		}

		return new Pod(v1Pod);
	}

	private LiferayK8sConnection() {
	}

	private Boolean _assertPodNotFound(Pod pod, String namespace) {
		try {
			newPod(
				_coreV1Api.readNamespacedPod(
					pod.getName(), namespace, null, true, false));
		}
		catch (ApiException ae) {
			String expectedMessage = "Not Found";

			if (expectedMessage.equals(ae.getMessage())) {
				return true;
			}

			throw new RuntimeException(ae);
		}

		return false;
	}

	private Pod _createPod(Pod configurationPod, String namespace) {
		Pod pod = null;

		try {
			pod = newPod(
				_coreV1Api.createNamespacedPod(
					namespace, configurationPod.getV1Pod(), null));
		}
		catch (ApiException ae) {
			System.out.println(ae);

			pod = getPod(configurationPod, namespace);
		}

		if (pod == null) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to start new pod with name '",
					configurationPod.getName(), "' in namespace '", namespace,
					"'"));
		}

		if (waitForPodRunning(pod)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Successfully created pod with name '", pod.getName(),
					"' in namespace '", namespace, "'"));
		}

		return pod;
	}

	private Boolean _deletePod(Pod pod, String namespace) {
		final String podName = pod.getName();

		try {
			_coreV1Api.deleteNamespacedPod(
				podName, namespace, new V1DeleteOptions(), null, 60, true,
				null);
		}
		catch (ApiException ae) {
			String message = ae.getMessage();

			if (message == null) {
				message = "";
			}

			if (message.equals("Not Found")) {
				return true;
			}

			throw new RuntimeException(ae);
		}
		catch (JsonSyntaxException jse) {
			String message = jse.getMessage();

			if (message == null) {
				message = "";
			}

			if (!message.contains("Expected a string but was BEGIN_OBJECT")) {
				throw jse;
			}
		}

		if (waitForPodNotFound(pod, namespace)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Successfully deleted pod with name '", podName,
					"' in namespace '", namespace, "'"));

			return true;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Unable to delete pod with name '", podName, "' in namespace '",
				namespace, "'"));

		return false;
	}

	private Pod _getPod(Pod pod, String namespace) {
		try {
			return newPod(
				_coreV1Api.readNamespacedPod(
					pod.getName(), namespace, null, true, false));
		}
		catch (ApiException ae) {
			System.out.println(ae);

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to get pod with name '", pod.getName(),
					"' in namespace '", namespace, "'"));

			throw new RuntimeException(ae);
		}
	}

	private List<Pod> _getPods() {
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
			pods.add(newPod(v1Pod));
		}

		return pods;
	}

	private List<Pod> _getPods(String namespace) {
		V1PodList v1PodList = null;

		try {
			v1PodList = _coreV1Api.listNamespacedPod(
				namespace, null, null, null, null, null, null, null, null,
				null);
		}
		catch (ApiException ae) {
			throw new RuntimeException("Unable to get pods", ae);
		}

		List<V1Pod> v1Pods = v1PodList.getItems();

		List<Pod> pods = new ArrayList<>(v1Pods.size());

		for (V1Pod v1Pod : v1Pods) {
			pods.add(newPod(v1Pod));
		}

		return pods;
	}

	private static final int _MAX_RETRIES = 30;

	private static final int _SECONDS_RETRY_PERIOD = 10;

	private static final ApiClient _apiClient;
	private static final CoreV1Api _coreV1Api;
	private static LiferayK8sConnection _liferayK8sConnection;

	static {
		try {
			_apiClient = Config.defaultClient();

			OkHttpClient okHttpClient = _apiClient.getHttpClient();

			okHttpClient.setConnectTimeout(5, TimeUnit.MINUTES);
			okHttpClient.setReadTimeout(5, TimeUnit.MINUTES);
			okHttpClient.setWriteTimeout(5, TimeUnit.MINUTES);

			Configuration.setDefaultApiClient(_apiClient);

			_coreV1Api = new CoreV1Api(_apiClient);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}