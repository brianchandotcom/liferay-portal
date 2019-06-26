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

import io.kubernetes.client.models.V1Container;
import io.kubernetes.client.models.V1ContainerPort;
import io.kubernetes.client.models.V1EmptyDirVolumeSource;
import io.kubernetes.client.models.V1EnvVar;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodSpec;
import io.kubernetes.client.models.V1Volume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class ResourceConfigurationFactory {

	public static Pod newDatabaseConfigurationPod(
		String dockerBaseImageName, String dockerImageName,
		List<V1ContainerPort> v1ContainerPorts, List<V1EnvVar> v1EnvVars) {

		V1Pod v1Pod = new V1Pod();

		String hostname = JenkinsResultsParserUtil.getHostName(null);

		if (hostname == null) {
			throw new RuntimeException("Unable to determine hostname");
		}

		hostname = JenkinsResultsParserUtil.combine(
			hostname.replaceFirst("\\..*", ""), "-", dockerBaseImageName);

		V1ObjectMeta v1ObjectMeta = newConfigurationMetaData(hostname);

		String serviceName = "database";

		v1ObjectMeta.putLabelsItem("app", serviceName);

		v1Pod.setMetadata(v1ObjectMeta);

		V1Container v1Container = newConfigurationContainer(
			dockerBaseImageName, dockerImageName);

		v1Container.setEnv(v1EnvVars);

		v1Container.setPorts(v1ContainerPorts);

		V1PodSpec v1PodSpec = newConfigurationPodSpec(v1Container);

		v1PodSpec.setHostname(hostname);
		v1PodSpec.setSubdomain(serviceName);
		v1PodSpec.setVolumes(
			new ArrayList<>(
				Arrays.asList(
					newEmptyDirConfigurationVolume(dockerBaseImageName))));

		v1Pod.setSpec(v1PodSpec);

		return new Pod(v1Pod);
	}

	public static Pod newMySQLConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				newConfigurationContainerPort(dockerBaseImageName, 3306)));

		List<V1EnvVar> v1EnvVars = new ArrayList<>(
			Arrays.asList(
				newConfigurationEnvVar("MYSQL_ALLOW_EMPTY_PASSWORD", "yes")));

		return newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars);
	}

	public static Pod newPostgreSQLConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				newConfigurationContainerPort(dockerBaseImageName, 5432)));

		List<V1EnvVar> v1EnvVars = new ArrayList<>(
			Arrays.asList(newConfigurationEnvVar("POSTGRES_USER", "root")));

		return newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars);
	}

	protected static V1Container newConfigurationContainer(
		String dockerBaseImageName, String dockerImageName) {

		V1Container v1Container = new V1Container();

		v1Container.setImage(dockerImageName);
		v1Container.setName(dockerBaseImageName);

		return v1Container;
	}

	protected static V1ContainerPort newConfigurationContainerPort(
		String dockerBaseImageName, int containerPort) {

		V1ContainerPort v1ContainerPort = new V1ContainerPort();

		v1ContainerPort.setContainerPort(containerPort);
		v1ContainerPort.setName(dockerBaseImageName);

		return v1ContainerPort;
	}

	protected static V1EnvVar newConfigurationEnvVar(
		String variableName, String variableValue) {

		V1EnvVar v1EnvVar = new V1EnvVar();

		v1EnvVar.setName(variableName);
		v1EnvVar.setValue(variableValue);

		return v1EnvVar;
	}

	protected static V1ObjectMeta newConfigurationMetaData(
		String metaDataName) {

		V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();

		v1ObjectMeta.setName(metaDataName);

		return v1ObjectMeta;
	}

	protected static V1PodSpec newConfigurationPodSpec(
		V1Container v1Container) {

		V1PodSpec v1PodSpec = new V1PodSpec();

		v1PodSpec.addContainersItem(v1Container);

		return v1PodSpec;
	}

	protected static V1Volume newEmptyDirConfigurationVolume(
		String dockerImageName) {

		V1Volume v1Volume = new V1Volume();

		v1Volume.setEmptyDir(new V1EmptyDirVolumeSource());
		v1Volume.setName(dockerImageName);

		return v1Volume;
	}

}