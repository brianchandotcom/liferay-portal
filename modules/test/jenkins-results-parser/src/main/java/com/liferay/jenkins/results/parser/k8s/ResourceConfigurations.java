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

	public static final LiferayK8sConnection.Pod mysql55PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mysql55", "mysql:5.5.62");
	public static final LiferayK8sConnection.Pod mysql56PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mysql56", "mysql:5.6.43");
	public static final LiferayK8sConnection.Pod mysql57PodConfiguration =
		ResourceConfigurationFactory.newMySQLConfigurationPod(
			"mysql57", "mysql:5.7.25");

}