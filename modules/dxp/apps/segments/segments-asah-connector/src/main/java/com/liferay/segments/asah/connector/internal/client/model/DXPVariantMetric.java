/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Riccardo Ferrari
 */
public class DXPVariantMetric {

	@JsonProperty("dxpVariantId")
	public String getDXPVariantId() {
		return _dxpVariantId;
	}

	@JsonProperty("improvement")
	public Float getImprovement() {
		return _improvement;
	}

	public Boolean isControl() {
		return _control;
	}

	public void setControl(Boolean control) {
		_control = control;
	}

	public void setDXPVariantId(String dxpVariantId) {
		_dxpVariantId = dxpVariantId;
	}

	public void setImprovement(Float improvement) {
		_improvement = improvement;
	}

	private Boolean _control;
	private String _dxpVariantId;
	private Float _improvement;

}