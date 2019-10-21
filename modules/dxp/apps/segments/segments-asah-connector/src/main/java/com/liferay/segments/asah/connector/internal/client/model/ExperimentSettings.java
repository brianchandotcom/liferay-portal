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

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author David Arques
 */
public final class ExperimentSettings {

	public Double getConfidenceLevel() {
		return _confidenceLevel;
	}

	@JsonProperty("dxpVariantsSettings")
	public List<DXPVariantSettings> getDXPVariantsSettings() {
		return _dxpVariantsSettings;
	}

	@JsonIgnore
	public Map<String, DXPVariantSettings> getDXPVariantsSettingsMap() {
		Map<String, DXPVariantSettings> dxpVariantSettingsMap = new HashMap<>();

		for (DXPVariantSettings dxpVariantSettings : _dxpVariantsSettings) {
			dxpVariantSettingsMap.put(
				dxpVariantSettings.getDXPVariantId(), dxpVariantSettings);
		}

		return dxpVariantSettingsMap;
	}

	public void setConfidenceLevel(Double confidenceLevel) {
		_confidenceLevel = confidenceLevel;
	}

	public void setDXPVariantsSettings(
		List<DXPVariantSettings> dxpVariantsSettings) {

		_dxpVariantsSettings = dxpVariantsSettings;
	}

	private Double _confidenceLevel;
	private List<DXPVariantSettings> _dxpVariantsSettings;

}