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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/search-response.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchResponseResource.class
)
public class SearchResponseResourceImpl extends BaseSearchResponseResourceImpl {

	@Override
	public SearchResponse postSearch(
			String queryString, Pagination pagination,
			SXPBlueprint sxpBlueprint)
		throws Exception {

		try {
			return toSearchResponse(
				_searcher.search(
					_searchRequestBuilderFactory.builder(
					).companyId(
						contextCompany.getCompanyId()
					).emptySearchEnabled(
						true
					).explain(
						true
					).includeResponseString(
						true
					).from(
						pagination.getStartPosition()
					).queryString(
						queryString
					).size(
						pagination.getPageSize()
					).withSearchContext(
						searchContext -> searchContext.setAttribute(
							"search.experiences.ip.address",
							contextHttpServletRequest.getRemoteAddr())
					).withSearchContext(
						searchContext -> searchContext.setUserId(
							contextUser.getUserId())
					).withSearchRequestBuilder(
						searchRequestBuilder -> {
							if (sxpBlueprint != null) {
								_sxpBlueprintSearchRequestEnhancer.enhance(
									searchRequestBuilder,
									String.valueOf(
										SXPBlueprintUtil.unpack(sxpBlueprint)));
							}
						}
					).build()));
		}
		catch (RuntimeException runtimeException) {
			if ((runtimeException.getClass() == RuntimeException.class) &&
				Validator.isBlank(runtimeException.getMessage()) &&
				ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {

				OutputStream outputStream = new ByteArrayOutputStream();

				runtimeException.printStackTrace(new PrintStream(outputStream));

				throw new RuntimeException(outputStream.toString());
			}

			throw runtimeException;
		}
	}

	protected SearchResponse toSearchResponse(
			com.liferay.portal.search.searcher.SearchResponse searchResponse)
		throws Exception {

		SearchRequest portalSearchRequest = searchResponse.getRequest();

		return new SearchResponse() {
			{
				page = portalSearchRequest.getFrom();
				pageSize = portalSearchRequest.getSize();
				requestString = searchResponse.getRequestString();
				responseString = searchResponse.getResponseString();
				totalHits = searchResponse.getTotalHits();
			}
		};
	}

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}