/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcellus Tavares
 * @author David Arques
 */
public class DXPVariantSettings {

	public DXPVariantSettings() {
	}

	public DXPVariantSettings(
		boolean control, String dxpVariantId, Double trafficSplit) {

		_control = control;
		_dxpVariantId = dxpVariantId;
		_trafficSplit = trafficSplit;
	}

	@JsonProperty("dxpVariantId")
	public String getDXPVariantId() {
		return _dxpVariantId;
	}

	public Double getTrafficSplit() {
		return _trafficSplit;
	}

	public boolean isControl() {
		return _control;
	}

	public void setControl(boolean control) {
		_control = control;
	}

	public void setDXPVariantId(String dxpVariantId) {
		_dxpVariantId = dxpVariantId;
	}

	public void setTrafficSplit(Double trafficSplit) {
		_trafficSplit = trafficSplit;
	}

	private boolean _control;
	private String _dxpVariantId;
	private Double _trafficSplit;

}