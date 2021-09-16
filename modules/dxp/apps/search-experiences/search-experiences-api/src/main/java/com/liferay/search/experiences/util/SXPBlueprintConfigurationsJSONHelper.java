/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface SXPBlueprintConfigurationsJSONHelper {

	public Optional<JSONObject> getAdvancedConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONObject> getAggsConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONArray> getCustomParameterConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public int getDefaultSize(SXPBlueprint sxpBlueprint);

	public Optional<JSONArray> getDefaultSortConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONObject> getHighlightConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONArray> getJSONArrayConfigurationOptional(
		SXPBlueprint sxpBlueprint, String... paths);

	public Optional<JSONObject> getJSONObjectConfigurationOptional(
		SXPBlueprint sxpBlueprint, String... paths);

	public String getKeywordParameterName(SXPBlueprint sxpBlueprint);

	public String getPageParameterName(SXPBlueprint sxpBlueprint);

	public Optional<JSONObject> getParameterConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONArray> getQueryConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public String getSizeParameterName(SXPBlueprint sxpBlueprint);

	public Optional<JSONObject> getSortConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONObject> getSortParameterConfigurationOptional(
		SXPBlueprint sxpBlueprint);

	public Optional<JSONObject> getSuggestConfigurationOptional(
		SXPBlueprint sxpBlueprint);

}