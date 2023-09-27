/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;

/**
 * @author André de Oliveira
 */
public interface SimilarResultsRoute {

	public SimilarResultsContributor getContributor();

	public Object getRouteParameter(String name);

}