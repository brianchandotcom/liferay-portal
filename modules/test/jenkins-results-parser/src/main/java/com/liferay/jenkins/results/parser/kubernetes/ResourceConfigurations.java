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

/**
 * @author Kenji Heigel
 */
public class ResourceConfigurations extends ResourceConfigurationFactory {

	public static final LiferayKubernetesConnection.Pod mysql55PodConfiguration;
	public static final LiferayKubernetesConnection.Pod mysql56PodConfiguration;
	public static final LiferayKubernetesConnection.Pod mysql57PodConfiguration;

	static {
		mysql55PodConfiguration = newConfiguredMySQLPod(
			"mysql55", "mysql:5.5.62");
		mysql56PodConfiguration = newConfiguredMySQLPod(
			"mysql56", "mysql:5.6.43");
		mysql57PodConfiguration = newConfiguredMySQLPod(
			"mysql57", "mysql:5.7.25");
	}

}