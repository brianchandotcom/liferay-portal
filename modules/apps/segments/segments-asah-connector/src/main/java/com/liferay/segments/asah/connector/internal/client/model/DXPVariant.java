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