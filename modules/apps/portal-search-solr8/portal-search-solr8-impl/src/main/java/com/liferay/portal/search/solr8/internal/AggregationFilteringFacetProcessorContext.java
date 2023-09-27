/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Collection;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringFacetProcessorContext
	implements FacetProcessorContext {

	public static FacetProcessorContext newInstance(
		Map<String, Facet> facets, boolean basicFacetSelection) {

		if (basicFacetSelection) {
			return new AggregationFilteringFacetProcessorContext(
				getAllNamesString(facets));
		}

		return new AggregationFilteringFacetProcessorContext(null);
	}

	@Override
	public String getExcludeTagsString() {
		return _excludeTagsString;
	}

	protected static String getAllNamesString(Map<String, Facet> facets) {
		Collection<String> names = facets.keySet();

		return StringUtil.merge(names, StringPool.COMMA);
	}

	private AggregationFilteringFacetProcessorContext(
		String excludeTagsString) {

		_excludeTagsString = excludeTagsString;
	}

	private final String _excludeTagsString;

}