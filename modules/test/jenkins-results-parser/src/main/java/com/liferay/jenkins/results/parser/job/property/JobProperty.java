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

package com.liferay.jenkins.results.parser.job.property;

import com.liferay.jenkins.results.parser.Job;

/**
 * @author Michael Hashimoto
 */
public interface JobProperty {

	public String getBasePropertyName();

	public Job getJob();

	public String getName();

	public String getPropertiesFilePath();

	public Type getType();

	public String getValue();

	public static enum Type {

		DEFAULT, DEFAULT_TEST_DIR, EXCLUDE_GLOB, FILTER_GLOB, INCLUDE_GLOB,
		MODULE_EXCLUDE_GLOB, MODULE_INCLUDE_GLOB, MODULE_TEST_DIR,
		PLUGIN_TEST_DIR, QA_WEBSITES_TEST_DIR

	}

}