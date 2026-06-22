/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;

/**
 * @author André de Oliveira
 */
public class FacetUtil {

	public static String getAggregationName(Facet facet) {
		if (facet instanceof com.liferay.portal.search.facet.Facet) {
			com.liferay.portal.search.facet.Facet facet2 =
				(com.liferay.portal.search.facet.Facet)facet;

			return facet2.getAggregationName();
		}

		return facet.getFieldName();
	}

}