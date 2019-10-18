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

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class RouteBuilderImpl implements RouteBuilder {

	@Override
	public RouteBuilder addAttribute(String name, Object value) {
		_similarResultsRouteImpl._routeAttributes.put(name, value);

		return this;
	}

	public SimilarResultsRoute build() {
		return new SimilarResultsRouteImpl(_similarResultsRouteImpl);
	}

	public void contributor(
		SimilarResultsContributor similarResultsContributor) {

		_similarResultsRouteImpl._similarResultsContributor =
			similarResultsContributor;
	}

	public boolean hasNoAttributes() {
		return _similarResultsRouteImpl._routeAttributes.isEmpty();
	}

	public static class SimilarResultsRouteImpl implements SimilarResultsRoute {

		@Override
		public SimilarResultsContributor getContributor() {
			return _similarResultsContributor;
		}

		@Override
		public Object getRouteParameter(String name) {
			return _routeAttributes.get(name);
		}

		private SimilarResultsRouteImpl() {
			_similarResultsContributor = null;
			_routeAttributes = new HashMap<>();
		}

		private SimilarResultsRouteImpl(
			SimilarResultsRouteImpl similarResultsRouteImpl) {

			_similarResultsContributor =
				similarResultsRouteImpl._similarResultsContributor;
			_routeAttributes = new HashMap<>(
				similarResultsRouteImpl._routeAttributes);
		}

		private final Map<String, Object> _routeAttributes;
		private SimilarResultsContributor _similarResultsContributor;

	}

	private final SimilarResultsRouteImpl _similarResultsRouteImpl =
		new SimilarResultsRouteImpl();

}