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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseProductVersion extends ListSpiraCustomPropertyValue {

	public static SpiraTestCaseProductVersion createSpiraTestCaseProductVersion(
		SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass,
		String productVersion) {

		String key = JenkinsResultsParserUtil.combine(
			String.valueOf(spiraProject.getID()), "_",
			getArtifactTypeName(spiraArtifactClass), "_", productVersion);

		SpiraTestCaseProductVersion spiraTestCaseProductVersion =
			_spiraTestCaseProductVersions.get(key);

		if (spiraTestCaseProductVersion != null) {
			return spiraTestCaseProductVersion;
		}

		spiraTestCaseProductVersion = new SpiraTestCaseProductVersion(
			spiraProject, spiraArtifactClass, productVersion);

		_spiraTestCaseProductVersions.put(key, spiraTestCaseProductVersion);

		return spiraTestCaseProductVersion;
	}

	public String getProductVersion() {
		return getValueString();
	}

	protected SpiraTestCaseProductVersion(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject, spiraCustomProperty);
	}

	protected SpiraTestCaseProductVersion(
		SpiraCustomProperty spiraCustomProperty, String productVersion) {

		super(
			_getSpiraCustomListValue(spiraCustomProperty, productVersion),
			spiraCustomProperty);
	}

	protected SpiraTestCaseProductVersion(
		SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass,
		String productVersion) {

		this(
			_getSpiraCustomProperty(spiraProject, spiraArtifactClass),
			productVersion);
	}

	protected static final String CUSTOM_PROPERTY_NAME = "Product Version";

	private static SpiraCustomListValue _getSpiraCustomListValue(
		SpiraCustomProperty spiraCustomProperty, String productVersion) {

		return SpiraCustomListValue.createSpiraCustomListValue(
			spiraCustomProperty.getSpiraProject(),
			spiraCustomProperty.getSpiraCustomList(), productVersion);
	}

	private static SpiraCustomProperty _getSpiraCustomProperty(
		SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		return SpiraCustomProperty.createSpiraCustomProperty(
			spiraProject, spiraArtifactClass, CUSTOM_PROPERTY_NAME,
			SpiraCustomProperty.Type.LIST);
	}

	private static final Map<String, SpiraTestCaseProductVersion>
		_spiraTestCaseProductVersions = new HashMap<>();

}