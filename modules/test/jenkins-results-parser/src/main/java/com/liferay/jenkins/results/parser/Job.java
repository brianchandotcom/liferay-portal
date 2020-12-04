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

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface Job {

	public List<AxisTestClassGroup> getAxisTestClassGroups();

	public Set<String> getBatchNames();

	public List<BatchTestClassGroup> getBatchTestClassGroups();

	public List<Build> getBuildHistory(JenkinsMaster jenkinsMaster);

	public BuildProfile getBuildProfile();

	public Set<String> getDistTypes();

	public Set<String> getDistTypesExcludingTomcat();

	public String getJobName();

	public Properties getJobProperties();

	public String getJobProperty(String key);

	public String getJobURL(JenkinsMaster jenkinsMaster);

	public Set<String> getSegmentNames();

	public List<SegmentTestClassGroup> getSegmentTestClassGroups();

	public String getTestPropertiesContent();

	public boolean isSegmentEnabled();

	public boolean isValidationRequired();

	public void readJobProperties();

	public static enum BuildProfile {

		DXP {

			private static final String _TEXT = "dxp";

			@Override
			public String toString() {
				return _TEXT;
			}

		},
		PORTAL {

			private static final String _TEXT = "portal";

			@Override
			public String toString() {
				return _TEXT;
			}

		}

	}

}