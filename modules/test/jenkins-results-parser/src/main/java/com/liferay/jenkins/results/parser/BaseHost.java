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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseHost implements Host {

	@Override
	public void cleanUpServices() {
		for (HostService hostService : _hostServices) {
			try {
				hostService.cleanUpService();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	protected BaseHost(String name) {
		_name = name;

		try {
			Properties jenkinsProperties =
				JenkinsResultsParserUtil.getJenkinsProperties();

			if (!jenkinsProperties.containsKey(name)) {
				return;
			}

			String serviceNames = jenkinsProperties.getProperty(name);

			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties(false);

			for (String serviceID : serviceNames.split(",")) {
				String serviceCleanCommand = buildProperties.getProperty(
					"service.clean.command[" + serviceID + "]", "");
				String serviceName = buildProperties.getProperty(
					"service.name[" + serviceID + "]", "");

				if (serviceCleanCommand.isEmpty() || serviceName.isEmpty()) {
					continue;
				}

				_hostServices.add(
					new HostService(serviceName, serviceCleanCommand));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private final List<HostService> _hostServices = new ArrayList<>();
	private final String _name;

}