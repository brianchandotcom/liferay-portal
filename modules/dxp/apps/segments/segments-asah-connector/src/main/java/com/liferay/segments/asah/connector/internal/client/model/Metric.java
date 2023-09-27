/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public class Metric {

	@JsonProperty("variantMetrics")
	public List<DXPVariantMetric> getDXPVariantMetrics() {
		return _dxpVariantMetrics;
	}

	public Integer getElapsedDays() {
		return _elapsedDays;
	}

	public String getId() {
		return _id;
	}

	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		shape = JsonFormat.Shape.STRING, timezone = "UTC"
	)
	public Date getProcessedDate() {
		if (_processedDate == null) {
			return null;
		}

		return new Date(_processedDate.getTime());
	}

	public void setDXPVariantMetrics(List<DXPVariantMetric> dxpVariantMetrics) {
		_dxpVariantMetrics = dxpVariantMetrics;
	}

	public void setElapsedDays(Integer elapsedDays) {
		_elapsedDays = elapsedDays;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setProcessedDate(Date processedDate) {
		if (processedDate != null) {
			_processedDate = new Date(processedDate.getTime());
		}
	}

	private List<DXPVariantMetric> _dxpVariantMetrics;
	private Integer _elapsedDays;
	private String _id;
	private Date _processedDate;

}