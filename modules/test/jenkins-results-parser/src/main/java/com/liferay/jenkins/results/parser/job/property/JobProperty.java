/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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