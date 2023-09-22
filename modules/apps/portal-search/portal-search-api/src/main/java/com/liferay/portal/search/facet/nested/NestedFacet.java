/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.nested;

import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.facet.Facet;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Díaz
 * @author Petteri Karttunen
 */
@ProviderType
public interface NestedFacet extends Facet {

	public Aggregation getChildAggregation();

	public Filter getChildAggregationValuesFilter();

	public String getFilterField();

	public String getFilterValue();

	public String getPath();

}