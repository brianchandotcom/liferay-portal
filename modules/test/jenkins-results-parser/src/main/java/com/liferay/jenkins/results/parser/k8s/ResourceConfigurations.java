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

/**
 * @author Kenji Heigel
 */
public class ResourceConfigurations {

	public static final Pod mariadb102PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mariadb102", "mariadb:10.2.25");
	public static final Pod mysql55PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mysql55", "mysql:5.5.62");
	public static final Pod mysql56PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mysql56", "mysql:5.6.43");
	public static final Pod mysql57PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mysql57", "mysql:5.7.25");
	public static final Pod postgresql10PodConfiguration =
		ResourceConfigurationFactory.newPostgreSQLConfigurationPod(
			"postgresql10", "postgres:10.9");

	public static Pod getPodConfiguration(String name) {
		if (name.equals("mariadb102")) {
			return mariadb102PodConfiguration;
		}
		else if (name.equals("mysql55")) {
			return mysql55PodConfiguration;
		}
		else if (name.equals("mysql56")) {
			return mysql56PodConfiguration;
		}
		else if (name.equals("mysql57")) {
			return mysql57PodConfiguration;
		}
		else if (name.equals("postgresql10")) {
			return postgresql10PodConfiguration;
		}

		throw new IllegalArgumentException("Invalid pod configuration name");
	}

	public static Pod getPodConfiguration(
		String databaseName, String databaseVersion) {

		String name = databaseName + databaseVersion;

		name = name.toLowerCase();

		name = name.replace(".", "");

		return getPodConfiguration(name);
	}

}