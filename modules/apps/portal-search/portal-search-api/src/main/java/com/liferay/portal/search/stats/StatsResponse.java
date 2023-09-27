/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.stats;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface StatsResponse {

	public long getCardinality();

	public long getCount();

	public String getField();

	public double getMax();

	public double getMean();

	public double getMin();

	public long getMissing();

	public double getStandardDeviation();

	public double getSum();

	public double getSumOfSquares();

}