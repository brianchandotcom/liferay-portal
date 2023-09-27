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
public interface StatsRequest {

	public String getField();

	public boolean isCardinality();

	public boolean isCount();

	public boolean isMax();

	public boolean isMean();

	public boolean isMin();

	public boolean isMissing();

	public boolean isStandardDeviation();

	public boolean isSum();

	public boolean isSumOfSquares();

}