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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Kenji Heigel
 */
public class ResourceConfigurations {

	public static Pod getPodConfiguration(String name) {
		if (!podConfigurationsMap.containsKey(name)) {
			throw new IllegalArgumentException(
				"Invalid pod configuration name: " + name);
		}

		return podConfigurationsMap.get(name);
	}

	public static Pod getPodConfiguration(
		String databaseName, String databaseVersion) {

		return getPodConfiguration(
			_getDatabaseConfigurationName(databaseName, databaseVersion));
	}

	private static String _getDatabaseConfigurationName(
		String databaseName, String databaseVersion) {

		if (StringUtils.countMatches(databaseVersion, ".") > 1) {
			int index = databaseVersion.lastIndexOf(".");

			databaseVersion = databaseVersion.substring(0, index);
		}

		String databaseConfigurationName = databaseName + databaseVersion;

		databaseConfigurationName = databaseConfigurationName.toLowerCase();

		databaseConfigurationName = databaseConfigurationName.replace(".", "");

		return databaseConfigurationName;
	}

	private static final Map<String, Pod> podConfigurationsMap =
		new HashMap<String, Pod>() {
			{
				put(
					"mariadb102",
					ResourceConfigurationFactory.newMySQLConfigurationPod(
						"mariadb102", "mariadb:10.2.25"));
				put(
					"mysql55",
					ResourceConfigurationFactory.newMySQLConfigurationPod(
						"mysql55", "mysql:5.5.62"));
				put(
					"mysql56",
					ResourceConfigurationFactory.newMySQLConfigurationPod(
						"mysql56", "mysql:5.6.43"));
				put(
					"mysql57",
					ResourceConfigurationFactory.newMySQLConfigurationPod(
						"mysql57", "mysql:5.7.25"));
				put(
					"postgresql10",
					ResourceConfigurationFactory.newPostgreSQLConfigurationPod(
						"postgresql10", "postgres:10.9"));
			}
		};

}