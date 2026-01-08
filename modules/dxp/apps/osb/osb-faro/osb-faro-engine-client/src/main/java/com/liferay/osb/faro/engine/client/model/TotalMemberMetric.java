/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Nilton Vieira
 */
public class TotalMemberMetric extends Metric {

	public long getTotalIndividuals() {
		return _totalIndividuals;
	}

	public void setTotalIndividuals(long totalIndividuals) {
		_totalIndividuals = totalIndividuals;
	}

	private long _totalIndividuals;

}