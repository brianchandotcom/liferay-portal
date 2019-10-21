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