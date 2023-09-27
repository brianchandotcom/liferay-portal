/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet;

import com.liferay.portal.kernel.search.SearchContext;

/**
 * @author André de Oliveira
 */
public interface FacetFactory
	extends com.liferay.portal.kernel.search.facet.util.FacetFactory {

	@Override
	public Facet newInstance(SearchContext searchContext);

}