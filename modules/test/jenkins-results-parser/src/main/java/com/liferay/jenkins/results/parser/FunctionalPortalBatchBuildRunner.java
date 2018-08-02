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

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class FunctionalPortalBatchBuildRunner extends PortalBatchBuildRunner {

	protected FunctionalPortalBatchBuildRunner(
		Job job, String batchName, String htmlURL) {

		super(job, batchName, htmlURL);

		_setPortalBuildProperties();
	}

	private void _setPortalBuildProperties() {
		Properties properties = new Properties();

		properties.put("jsp.precompile", "on");
		properties.put("jsp.precompile.parallel", "on");

		PortalLocalRepository portalLocalRepository =
			getPortalLocalRepository();

		portalLocalRepository.setBuildProperties(properties);
	}

}