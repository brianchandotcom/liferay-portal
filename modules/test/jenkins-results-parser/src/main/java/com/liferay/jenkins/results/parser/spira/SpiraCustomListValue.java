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

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraCustomListValue extends BaseSpiraArtifact {

	public static SpiraCustomListValue createSpiraCustomListValue(
		SpiraProject spiraProject, SpiraCustomList spiraCustomList,
		String value) {

		SpiraCustomListValue spiraCustomListValue =
			spiraCustomList.getSpiraCustomListValueByName(value);

		if (spiraCustomListValue != null) {
			return spiraCustomListValue;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"custom_list_id", String.valueOf(spiraCustomList.getID()));
		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("Active", true);
		requestJSONObject.put("CustomPropertyListId", spiraCustomList.getID());
		requestJSONObject.put("Name", value);
		requestJSONObject.put("ProjectId", spiraProject.getID());

		try {
			spiraCustomListValue = new SpiraCustomListValue(
				SpiraRestAPIUtil.requestJSONObject(
					"project-templates/{project_template_id}/custom-lists" +
						"/{custom_list_id}/values",
					null, urlPathReplacements,
					JenkinsResultsParserUtil.HttpRequestMethod.POST,
					requestJSONObject.toString()),
				spiraProject, spiraCustomList);

			spiraCustomList.addSpiraCustomListValue(spiraCustomListValue);

			return spiraCustomListValue;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public SpiraCustomList getSpiraCustomList() {
		return _spiraCustomList;
	}

	@Override
	public String getURL() {
		SpiraCustomList spiraCustomList = getSpiraCustomList();

		return spiraCustomList.getURL();
	}

	protected SpiraCustomListValue(
		JSONObject jsonObject, SpiraProject spiraProject,
		SpiraCustomList spiraCustomList) {

		super(jsonObject);

		jsonObject.put("ProjectId", spiraProject.getID());

		_spiraCustomList = spiraCustomList;
	}

	protected static final String KEY_ID = "CustomPropertyValueId";

	private final SpiraCustomList _spiraCustomList;

}