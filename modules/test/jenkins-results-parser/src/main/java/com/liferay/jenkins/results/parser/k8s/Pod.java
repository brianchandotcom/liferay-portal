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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.Exec;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodStatus;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kenji Heigel
 */
public class Pod {

	public void copyFileToPod(
		String sourceFilePath, String sourceHostname, String destFilePath) {

		throw new UnsupportedOperationException();
	}

	public void exec(String... commands) {
		Process process = null;

		try {
			Exec exec = new Exec();

			process = exec.exec(
				getNamespace(), getName(), commands, true,
				System.console() != null);

			try (InputStream inputStream = process.getInputStream()) {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						inputStream, true));
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to read process input stream", ioe);
			}

			if (process.exitValue() != 0) {
				try (InputStream errorStream = process.getErrorStream()) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.readInputStream(errorStream));
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to read process error stream", ioe);
				}
			}
		}
		catch (ApiException | IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

	public String getIP() {
		V1Pod v1Pod = getV1Pod();

		V1PodStatus v1PodStatus = v1Pod.getStatus();

		String ip = v1PodStatus.getPodIP();

		if (ip == null) {
			throw new RuntimeException(
				"Unable to get ip of pod '" + getName() + "'");
		}

		return ip;
	}

	public String getName() {
		V1ObjectMeta v1ObjectMeta = _v1Pod.getMetadata();

		return v1ObjectMeta.getName();
	}

	public String getNamespace() {
		V1ObjectMeta v1ObjectMeta = _v1Pod.getMetadata();

		return v1ObjectMeta.getNamespace();
	}

	public String getPhase() {
		V1PodStatus v1PodStatus = _v1Pod.getStatus();

		return v1PodStatus.getPhase();
	}

	protected Pod(V1Pod v1Pod) {
		_v1Pod = v1Pod;
	}

	protected V1Pod getV1Pod() {
		return _v1Pod;
	}

	protected void refreshV1Pod() {
		LiferayK8sConnection liferayK8sConnection =
			LiferayK8sConnection.getInstance();

		Pod pod = liferayK8sConnection.getPod(this, getNamespace());

		_v1Pod = pod.getV1Pod();
	}

	private V1Pod _v1Pod;

}