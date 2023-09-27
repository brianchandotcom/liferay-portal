/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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