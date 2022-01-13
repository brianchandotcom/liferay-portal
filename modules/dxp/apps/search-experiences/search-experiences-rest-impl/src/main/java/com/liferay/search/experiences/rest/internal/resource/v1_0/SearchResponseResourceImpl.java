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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.exception.SXPExceptionUtil;
import com.liferay.search.experiences.rest.dto.v1_0.DocumentField;
import com.liferay.search.experiences.rest.dto.v1_0.Hit;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SearchHits;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 * @author André de Oliveira
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
			return search(pagination, queryString, sxpBlueprint);
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

	protected SearchResponse search(
		Pagination pagination, String queryString, SXPBlueprint sxpBlueprint) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				contextCompany.getCompanyId()
			).from(
				pagination.getStartPosition()
			).queryString(
				queryString
			).size(
				pagination.getPageSize()
			).withSearchContext(
				searchContext -> {
					searchContext.setAttribute(
						"search.experiences.ip.address",
						contextHttpServletRequest.getRemoteAddr());
					searchContext.setTimeZone(contextUser.getTimeZone());
					searchContext.setUserId(contextUser.getUserId());
				}
			);

		RuntimeException runtimeException = new RuntimeException();

		try {
			if (sxpBlueprint != null) {
				_sxpBlueprintSearchRequestEnhancer.enhance(
					searchRequestBuilder,
					String.valueOf(SXPBlueprintUtil.unpack(sxpBlueprint)));
			}
		}
		catch (Exception exception) {
			runtimeException.addSuppressed(exception);
		}

		if (SXPExceptionUtil.hasErrors(runtimeException)) {
			throw runtimeException;
		}

		try {
			SearchResponse searchResponse = toSearchResponse(
				_searcher.search(searchRequestBuilder.build()));

			// TODO Add warnings to search response DTO for client side
			// rendering

			if (ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {
				if (_log.isWarnEnabled()) {
					_log.warn(runtimeException);
				}
			}

			return searchResponse;
		}
		catch (Exception exception) {
			runtimeException.addSuppressed(exception);
		}

		throw runtimeException;
	}

	protected SearchResponse toSearchResponse(
			com.liferay.portal.search.searcher.SearchResponse searchResponse)
		throws Exception {

		SearchRequest portalSearchRequest = searchResponse.getRequest();

		return SearchResponse.unsafeToDTO(
			String.valueOf(
				new SearchResponse() {
					{
						page = portalSearchRequest.getFrom();
						pageSize = portalSearchRequest.getSize();
						request = _createJSONObject(
							searchResponse.getRequestString());
						requestString = searchResponse.getRequestString();
						response = _createJSONObject(
							searchResponse.getResponseString());
						responseString = searchResponse.getResponseString();
						searchHits = _toSearchHits(
							_getLocale(searchResponse),
							searchResponse.getSearchHits());
					}
				}));
	}

	private JSONObject _createJSONObject(String string) {
		try {
			return JSONFactoryUtil.createJSONObject(string);
		}
		catch (JSONException jsonException) {
			return null;
		}
	}

	private AssetRenderer<?> _getAssetRenderer(Map<String, Field> fields) {
		try {
			Field entryClassNameField = fields.get(
				com.liferay.portal.kernel.search.Field.ENTRY_CLASS_NAME);

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						GetterUtil.getString(entryClassNameField.getValue()));

			Field entryClassPKField = fields.get(
				com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK);

			return assetRendererFactory.getAssetRenderer(
				GetterUtil.getLong(entryClassPKField.getValue()));
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private Locale _getLocale(
		com.liferay.portal.search.searcher.SearchResponse searchResponse) {

		Locale locale = searchResponse.withSearchContextGet(
			SearchContext::getLocale);

		if (locale != null) {
			return locale;
		}

		return contextAcceptLanguage.getPreferredLocale();
	}

	private Float _getScore(float score) {
		if (Float.isNaN(score)) {
			return null;
		}

		return score;
	}

	private Map<String, DocumentField> _toDocumentFields(
		Document document, Locale locale) {

		Map<String, Field> fields = document.getFields();

		Map<String, DocumentField> documentFields = new LinkedHashMap<>();

		MapUtil.isNotEmptyForEach(
			fields,
			(name, field) -> {
				List<Object> valuesList = field.getValues();

				documentFields.put(
					name,
					new DocumentField() {
						{
							values = valuesList.toArray();
						}
					});
			});

		if (MapUtil.isEmpty(fields)) {
			return documentFields;
		}

		AssetRenderer<?> assetRenderer = _getAssetRenderer(fields);

		if (assetRenderer == null) {
			return documentFields;
		}

		documentFields.put(
			"assetSearchSummary",
			new DocumentField() {
				{
					values = new String[] {
						assetRenderer.getSearchSummary(locale)
					};
				}
			});
		documentFields.put(
			"assetTitle",
			new DocumentField() {
				{
					values = new String[] {assetRenderer.getTitle(locale)};
				}
			});

		return documentFields;
	}

	private Hit[] _toHits(Locale locale, List<SearchHit> searchHits) {
		List<Hit> hits = new ArrayList<>();

		for (SearchHit searchHit : searchHits) {
			hits.add(
				new Hit() {
					{
						documentFields = _toDocumentFields(
							searchHit.getDocument(), locale);
						explanation = searchHit.getExplanation();
						id = searchHit.getId();
						score = _getScore(searchHit.getScore());
						version = searchHit.getVersion();
					}
				});
		}

		return hits.toArray(new Hit[0]);
	}

	private SearchHits _toSearchHits(
		Locale locale, com.liferay.portal.search.hits.SearchHits searchHits) {

		return new SearchHits() {
			{
				hits = _toHits(locale, searchHits.getSearchHits());
				maxScore = _getScore(searchHits.getMaxScore());
				totalHits = searchHits.getTotalHits();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResponseResourceImpl.class);

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}