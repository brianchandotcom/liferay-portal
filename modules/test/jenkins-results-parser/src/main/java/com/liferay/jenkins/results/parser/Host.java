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

import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public interface Host {

	public void cleanUpServices();

	public String getName();

	public class HostService {

		public void cleanUpService() {
			try {
				System.out.println("Clean up " + _name);

				Process process = JenkinsResultsParserUtil.executeBashCommands(
					_cleanUpCommand);

				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream()));
			}
			catch (IOException | TimeoutException e) {
				throw new RuntimeException(e);
			}
		}

		protected HostService(String name, String cleanUpCommand) {
			_name = name;
			_cleanUpCommand = cleanUpCommand;
		}

		private final String _cleanUpCommand;
		private final String _name;

	}

}