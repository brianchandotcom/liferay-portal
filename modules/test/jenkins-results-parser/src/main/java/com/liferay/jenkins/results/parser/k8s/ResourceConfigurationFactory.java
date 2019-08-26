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

import io.kubernetes.client.models.V1Affinity;
import io.kubernetes.client.models.V1Container;
import io.kubernetes.client.models.V1ContainerPort;
import io.kubernetes.client.models.V1EmptyDirVolumeSource;
import io.kubernetes.client.models.V1EnvVar;
import io.kubernetes.client.models.V1LabelSelector;
import io.kubernetes.client.models.V1LabelSelectorRequirement;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodAffinity;
import io.kubernetes.client.models.V1PodAffinityTerm;
import io.kubernetes.client.models.V1PodSpec;
import io.kubernetes.client.models.V1SecretVolumeSource;
import io.kubernetes.client.models.V1SecurityContext;
import io.kubernetes.client.models.V1Volume;
import io.kubernetes.client.models.V1VolumeMount;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenji Heigel
 */
public class ResourceConfigurationFactory {

	public static Pod getConfigurationPod(String name) {
		if (!_podConfigurationsMap.containsKey(name)) {
			throw new IllegalArgumentException(
				"Invalid configuration pod name: " + name);
		}

		return _podConfigurationsMap.get(name);
	}

	public static Pod getConfigurationPod(
		String databaseName, String databaseVersion) {

		return getConfigurationPod(
			_getDatabaseConfigurationName(databaseName, databaseVersion));
	}

	public static Set<String> getPodConfigurationKeys() {
		return _podConfigurationsMap.keySet();
	}

	protected static String getPodPrefix() {
		String hostname = JenkinsResultsParserUtil.getHostName(null);

		if (hostname == null) {
			throw new RuntimeException("Unable to determine hostname");
		}

		hostname = hostname.toLowerCase();

		return hostname.replaceAll("([^\\.]+)(\\..*|)", "$1");
	}

	private static String _getDatabaseConfigurationName(
		String databaseName, String databaseVersion) {

		Matcher matcher = _databaseVersionPattern.matcher(databaseVersion);

		if (matcher.matches()) {
			databaseVersion = matcher.group(1);
		}

		String databaseConfigurationName = databaseName + databaseVersion;

		databaseConfigurationName = databaseConfigurationName.toLowerCase();

		databaseConfigurationName = databaseConfigurationName.replace(".", "");

		return databaseConfigurationName;
	}

	private static String _getKubernetesDockerRegistryHostname() {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			return buildProperties.getProperty(
				"kubernetes.docker.registry.hostname");
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get Docker registry URL");
		}
	}

	private static V1Affinity _newConfigurationAffinity() {
		V1Affinity v1Affinity = new V1Affinity();

		V1PodAffinity v1PodAffinity = new V1PodAffinity();

		V1PodAffinityTerm v1PodAffinityTerm = new V1PodAffinityTerm();

		V1LabelSelector v1LabelSelector = new V1LabelSelector();

		V1LabelSelectorRequirement v1LabelSelectorRequirement =
			new V1LabelSelectorRequirement();

		v1LabelSelectorRequirement.setKey("app");
		v1LabelSelectorRequirement.setOperator("In");
		v1LabelSelectorRequirement.setValues(
			new ArrayList<>(Arrays.asList(getPodPrefix())));

		v1LabelSelector.setMatchExpressions(
			new ArrayList<>(Arrays.asList(v1LabelSelectorRequirement)));

		v1PodAffinityTerm.setLabelSelector(v1LabelSelector);
		v1PodAffinityTerm.setTopologyKey("kubernetes.io/hostname");

		v1PodAffinity.setRequiredDuringSchedulingIgnoredDuringExecution(
			new ArrayList<>(Arrays.asList(v1PodAffinityTerm)));

		v1Affinity.setPodAffinity(v1PodAffinity);

		return v1Affinity;
	}

	private static V1Container _newConfigurationContainer(
		String dockerBaseImageName, String dockerImageName) {

		V1Container v1Container = new V1Container();

		v1Container.setImage(dockerImageName);
		v1Container.setName(dockerBaseImageName);

		return v1Container;
	}

	private static V1ContainerPort _newConfigurationContainerPort(
		String dockerBaseImageName, int containerPort) {

		V1ContainerPort v1ContainerPort = new V1ContainerPort();

		v1ContainerPort.setContainerPort(containerPort);
		v1ContainerPort.setName(dockerBaseImageName);

		return v1ContainerPort;
	}

	private static V1EnvVar _newConfigurationEnvVar(
		String variableName, String variableValue) {

		V1EnvVar v1EnvVar = new V1EnvVar();

		v1EnvVar.setName(variableName);
		v1EnvVar.setValue(variableValue);

		return v1EnvVar;
	}

	private static V1ObjectMeta _newConfigurationMetaData(String metaDataName) {
		V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();

		v1ObjectMeta.setName(metaDataName.toLowerCase());

		return v1ObjectMeta;
	}

	private static V1PodSpec _newConfigurationPodSpec(V1Container v1Container) {
		V1PodSpec v1PodSpec = new V1PodSpec();

		v1PodSpec.addContainersItem(v1Container);

		return v1PodSpec;
	}

	private static V1SecurityContext _newConfigurationSecurityContext(
		Boolean privileged) {

		V1SecurityContext v1SecurityContext = new V1SecurityContext();

		v1SecurityContext.setPrivileged(privileged);

		return v1SecurityContext;
	}

	private static Pod _newDatabaseConfigurationPod(
		String dockerBaseImageName, String dockerImageName,
		List<V1ContainerPort> v1ContainerPorts, List<V1EnvVar> v1EnvVars) {

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars,
			Arrays.asList(_newEmptyDirConfigurationVolume()), null, false);
	}

	private static Pod _newDatabaseConfigurationPod(
		String dockerBaseImageName, String dockerImageName,
		List<V1ContainerPort> v1ContainerPorts, List<V1EnvVar> v1EnvVars,
		Boolean privileged) {

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars,
			Arrays.asList(_newEmptyDirConfigurationVolume()), null, privileged);
	}

	private static Pod _newDatabaseConfigurationPod(
		String dockerBaseImageName, String dockerImageName,
		List<V1ContainerPort> v1ContainerPorts, List<V1EnvVar> v1EnvVars,
		List<V1Volume> v1Volumes, List<V1VolumeMount> v1VolumeMounts) {

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars,
			v1Volumes, v1VolumeMounts, false);
	}

	private static Pod _newDatabaseConfigurationPod(
		String dockerBaseImageName, String dockerImageName,
		List<V1ContainerPort> v1ContainerPorts, List<V1EnvVar> v1EnvVars,
		List<V1Volume> v1Volumes, List<V1VolumeMount> v1VolumeMounts,
		Boolean privileged) {

		V1Pod v1Pod = new V1Pod();

		String hostname = getPodPrefix() + "-" + dockerBaseImageName;

		V1ObjectMeta v1ObjectMeta = _newConfigurationMetaData(hostname);

		v1ObjectMeta.putLabelsItem("app", dockerBaseImageName);

		v1Pod.setMetadata(v1ObjectMeta);

		V1Container v1Container = _newConfigurationContainer(
			dockerBaseImageName, dockerImageName);

		v1Container.setEnv(v1EnvVars);

		v1Container.setPorts(v1ContainerPorts);

		v1Container.setSecurityContext(
			_newConfigurationSecurityContext(privileged));

		if (v1VolumeMounts != null) {
			v1Container.setVolumeMounts(v1VolumeMounts);
		}

		V1PodSpec v1PodSpec = _newConfigurationPodSpec(v1Container);

		v1PodSpec.setAffinity(_newConfigurationAffinity());
		v1PodSpec.setHostname(hostname.toLowerCase());
		v1PodSpec.setSubdomain("database");
		v1PodSpec.setVolumes(new ArrayList<>(v1Volumes));

		v1Pod.setSpec(v1PodSpec);

		LiferayK8sConnection liferayK8sConnection =
			LiferayK8sConnection.getInstance();

		return liferayK8sConnection.newPod(v1Pod);
	}

	private static Pod _newDB2ConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				_newConfigurationContainerPort(dockerBaseImageName, 50000)));

		String db2Password = "password";

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			db2Password = buildProperties.getProperty(
				"portal.test.properties[database.db2.password]", db2Password);
		}
		catch (IOException ioe) {
			System.out.println("Unable to get DB2 password");
		}

		List<V1EnvVar> v1EnvVars = new ArrayList<>(
			Arrays.asList(
				_newConfigurationEnvVar("DB2INST1_PASSWORD", db2Password),
				_newConfigurationEnvVar("DB2INSTANCE", "db2inst1"),
				_newConfigurationEnvVar("LICENSE", "accept")));

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars,
			true);
	}

	private static V1Volume _newEmptyDirConfigurationVolume() {
		V1Volume v1Volume = new V1Volume();

		v1Volume.setEmptyDir(new V1EmptyDirVolumeSource());
		v1Volume.setName("empty-dir");

		return v1Volume;
	}

	private static Pod _newMySQLConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				_newConfigurationContainerPort(dockerBaseImageName, 3306)));

		List<V1EnvVar> v1EnvVars = new ArrayList<>(
			Arrays.asList(
				_newConfigurationEnvVar("MYSQL_ALLOW_EMPTY_PASSWORD", "yes")));

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars);
	}

	private static Pod _newOracleConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				_newConfigurationContainerPort(dockerBaseImageName, 1521)));

		List<V1EnvVar> v1EnvVars = new ArrayList<>(
			Arrays.asList(
				_newConfigurationEnvVar("DB_DOMAIN", ""),
				_newConfigurationEnvVar("DB_PDB", "oracl")));

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String oraclePassword = buildProperties.getProperty(
				"portal.sql.properties[oracle.admin.password]");

			v1EnvVars.add(_newConfigurationEnvVar("DB_PASSWD", oraclePassword));
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get Oracle database password");
		}

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars,
			Arrays.asList(
				_newEmptyDirConfigurationVolume(), _newSSHSecretVolume()),
			Arrays.asList(_newSSHSecretVolumeMount()));
	}

	private static Pod _newPostgreSQLConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				_newConfigurationContainerPort(dockerBaseImageName, 5432)));

		List<V1EnvVar> v1EnvVars = new ArrayList<>(
			Arrays.asList(_newConfigurationEnvVar("POSTGRES_USER", "root")));

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts, v1EnvVars);
	}

	private static V1Volume _newSSHSecretVolume() {
		V1SecretVolumeSource v1SecretVolumeSource = new V1SecretVolumeSource();

		v1SecretVolumeSource.secretName("ssh-secret");

		V1Volume v1Volume = new V1Volume();

		v1Volume.setSecret(v1SecretVolumeSource);
		v1Volume.setName("ssh-secret");

		return v1Volume;
	}

	private static V1VolumeMount _newSSHSecretVolumeMount() {
		V1VolumeMount v1VolumeMount = new V1VolumeMount();

		v1VolumeMount.setMountPath("/mnt/ssh-secret-volume");
		v1VolumeMount.setName("ssh-secret");

		return v1VolumeMount;
	}

	private static Pod _newSybaseConfigurationPod(
		String dockerBaseImageName, String dockerImageName) {

		List<V1ContainerPort> v1ContainerPorts = new ArrayList<>(
			Arrays.asList(
				_newConfigurationContainerPort(dockerBaseImageName, 5000)));

		return _newDatabaseConfigurationPod(
			dockerBaseImageName, dockerImageName, v1ContainerPorts,
			new ArrayList<V1EnvVar>());
	}

	private static final Pattern _databaseVersionPattern = Pattern.compile(
		"([^\\.]*\\.[^\\.]*)\\..*");
	private static final Map<String, Pod> _podConfigurationsMap =
		new HashMap<String, Pod>() {
			{
				put(
					"db2111",
					ResourceConfigurationFactory._newDB2ConfigurationPod(
						"db2111",
						_getKubernetesDockerRegistryHostname() +
							"/store/ibmcorp/db2_developer_c:11.1.4.4-x86_64"));
				put(
					"mariadb102",
					ResourceConfigurationFactory._newMySQLConfigurationPod(
						"mariadb102", "mariadb:10.2.25"));
				put(
					"mysql55",
					ResourceConfigurationFactory._newMySQLConfigurationPod(
						"mysql55", "mysql:5.5.62"));
				put(
					"mysql56",
					ResourceConfigurationFactory._newMySQLConfigurationPod(
						"mysql56", "mysql:5.6.43"));
				put(
					"mysql57",
					ResourceConfigurationFactory._newMySQLConfigurationPod(
						"mysql57", "mysql:5.7.25"));
				put(
					"oracle122",
					ResourceConfigurationFactory._newOracleConfigurationPod(
						"oracle122",
						_getKubernetesDockerRegistryHostname() +
							"/store/oracle/database-enterprise:12.2.0.1-slim"));
				put(
					"postgresql10",
					ResourceConfigurationFactory._newPostgreSQLConfigurationPod(
						"postgresql10", "postgres:10.9"));
				put(
					"sybase160",
					ResourceConfigurationFactory._newSybaseConfigurationPod(
						"sybase160",
						_getKubernetesDockerRegistryHostname() +
							"/liferay-ci-slave-db-sybase"));
			}
		};

}