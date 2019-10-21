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
 * @author André Miranda
 * @author Sarai Díaz
 * @author David Arques
 */
public class DXPVariant {

	public Integer getChanges() {
		return _changes;
	}

	@JsonProperty("dxpVariantId")
	public String getDXPVariantId() {
		return _dxpVariantId;
	}

	@JsonProperty("dxpVariantName")
	public String getDXPVariantName() {
		return _dxpVariantName;
	}

	public Double getTrafficSplit() {
		return _trafficSplit;
	}

	public Boolean isControl() {
		return _control;
	}

	public void setChanges(Integer changes) {
		_changes = changes;
	}

	public void setControl(Boolean control) {
		_control = control;
	}

	public void setDXPVariantId(String dxpVariantId) {
		_dxpVariantId = dxpVariantId;
	}

	public void setDXPVariantName(String dxpVariantName) {
		_dxpVariantName = dxpVariantName;
	}

	public void setTrafficSplit(Double trafficSplit) {
		_trafficSplit = trafficSplit;
	}

	private Integer _changes;
	private Boolean _control;
	private String _dxpVariantId;
	private String _dxpVariantName;
	private Double _trafficSplit;

}