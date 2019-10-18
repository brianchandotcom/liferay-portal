/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.portlet.search;

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