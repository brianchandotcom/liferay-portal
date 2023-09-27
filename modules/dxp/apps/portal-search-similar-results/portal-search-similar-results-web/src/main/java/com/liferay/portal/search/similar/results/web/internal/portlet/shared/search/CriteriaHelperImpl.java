/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.portlet.shared.search;

import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;

/**
 * @author André de Oliveira
 */
public class CriteriaHelperImpl implements CriteriaHelper {

	public CriteriaHelperImpl(
		long groupId, SimilarResultsRoute similarResultsRoute) {

		_groupId = groupId;
		_similarResultsRoute = similarResultsRoute;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public Object getRouteParameter(String name) {
		return _similarResultsRoute.getRouteParameter(name);
	}

	private final long _groupId;
	private final SimilarResultsRoute _similarResultsRoute;

}