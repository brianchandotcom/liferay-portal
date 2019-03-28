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

import io.kubernetes.client.models.V1Container;
import io.kubernetes.client.models.V1ContainerPort;
import io.kubernetes.client.models.V1EmptyDirVolumeSource;
import io.kubernetes.client.models.V1EnvVar;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodSpec;
import io.kubernetes.client.models.V1Volume;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Kenji Heigel
 */
public class ResourceConfigurationFactory {

	public static V1Pod newMySQLPodConfiguration(String baseName, String image)
		throws UnknownHostException {

		V1Pod podConfiguration = new V1Pod();

		String name = getSystemSimpleHostName() + "-" + baseName;

		podConfiguration.setMetadata(createMetaData(name));

		V1Container container = createContainer(baseName, image);

		container.setEnv(
			new ArrayList<>(
				Arrays.asList(
					createEnvVar("MYSQL_ROOT_PASSWORD", "password"))));

		container.setPorts(
			new ArrayList<>(
				Arrays.asList(createContainerPort(baseName, 3306))));

		V1PodSpec podSpec = createPodSpec(container);

		podSpec.setVolumes(
			new ArrayList<>(Arrays.asList(createEmptyDirVolume(baseName))));

		podConfiguration.setSpec(podSpec);

		return podConfiguration;
	}

	protected static V1Container createContainer(String name, String image) {
		V1Container v1Container = new V1Container();

		v1Container.setName(name);

		v1Container.setImage(image);

		return v1Container;
	}

	protected static V1ContainerPort createContainerPort(
		String name, int containerPort) {

		V1ContainerPort v1ContainerPort = new V1ContainerPort();

		v1ContainerPort.setName(name);

		v1ContainerPort.setContainerPort(containerPort);

		return v1ContainerPort;
	}

	protected static V1Volume createEmptyDirVolume(String name) {
		V1Volume v1Volume = new V1Volume();

		v1Volume.setName(name);

		V1EmptyDirVolumeSource v1EmptyDirVolumeSource =
			new V1EmptyDirVolumeSource();

		v1Volume.setEmptyDir(v1EmptyDirVolumeSource);

		return v1Volume;
	}

	protected static V1EnvVar createEnvVar(String name, String value) {
		V1EnvVar v1EnvVar = new V1EnvVar();

		v1EnvVar.setName(name);

		v1EnvVar.setValue(value);

		return v1EnvVar;
	}

	protected static V1ObjectMeta createMetaData(String name) {
		V1ObjectMeta meta = new V1ObjectMeta();

		meta.setName(name);

		return meta;
	}

	protected static V1PodSpec createPodSpec(V1Container container) {
		V1PodSpec v1PodSpec = new V1PodSpec();

		v1PodSpec.addContainersItem(container);

		return v1PodSpec;
	}

	protected static String getSystemSimpleHostName()
		throws UnknownHostException {

		InetAddress localHost = InetAddress.getLocalHost();

		String hostName = localHost.getHostName();

		if (hostName.contains(".")) {
			int index = hostName.indexOf(".");

			hostName = hostName.substring(0, index);
		}

		return hostName;
	}

}