/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Marcellus Tavares
 * @author Sarai Díaz
 * @author David Arques
 */
public class DXPVariants {

	public DXPVariants() {
	}

	public DXPVariants(List<DXPVariant> dxpVariants) {
		_dxpVariants = dxpVariants;
	}

	@JsonProperty("dxpVariants")
	public List<DXPVariant> getDXPVariants() {
		return _dxpVariants;
	}

	public void setDXPVariants(List<DXPVariant> dxpVariants) {
		_dxpVariants = dxpVariants;
	}

	private List<DXPVariant> _dxpVariants;

}