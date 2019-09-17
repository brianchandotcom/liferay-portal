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