/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.data.processor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.AggregationContextProvider;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.FilterContextProvider;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.SortContextProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	immediate = true, service = GraphQLDTOContributorDataFetchingProcessor.class
)
public class GraphQLDTOContributorDataFetchingProcessor {

	public Object create(
			Object dto, GraphQLDTOContributor graphQLDTOContributor,
			HttpServletRequest httpServletRequest, String scopeKey)
		throws Exception {

		return graphQLDTOContributor.createDTO(
			dto,
			_getDTOConverterContext(
				httpServletRequest,
				HashMapBuilder.<String, Serializable>put(
					"scopeKey", scopeKey
				).build()));
	}

	public boolean delete(GraphQLDTOContributor graphQLDTOContributor, long id)
		throws Exception {

		return graphQLDTOContributor.deleteDTO(id);
	}

	public Object get(
			GraphQLDTOContributor graphQLDTOContributor,
			HttpServletRequest httpServletRequest, long id)
		throws Exception {

		return graphQLDTOContributor.getDTO(
			_getDTOConverterContext(httpServletRequest, null), id);
	}

	public Object getRelationship(
			GraphQLDTOContributor graphQLDTOContributor,
			GraphQLDTOProperty graphQLDTOProperty,
			HttpServletRequest httpServletRequest, long id)
		throws Exception {

		return graphQLDTOContributor.getRelationshipValue(
			_getDTOConverterContext(httpServletRequest, null), id,
			graphQLDTOProperty.getTypeClass(), graphQLDTOProperty.getName());
	}

	public Page<Object> list(
			List<String> aggregations, String filter,
			GraphQLDTOContributor graphQLDTOContributor,
			HttpServletRequest httpServletRequest, int page, int pageSize,
			String scopeKey, String search, String sort)
		throws Exception {

		Aggregation aggregation = null;

		AcceptLanguage acceptLanguage = new AcceptLanguageImpl(
			httpServletRequest, _language, _portal);

		if (aggregations != null) {
			aggregation = _getAggregation(
				acceptLanguage, aggregations,
				graphQLDTOContributor.getEntityModel());
		}

		return graphQLDTOContributor.getDTOs(
			aggregation,
			_getDTOConverterContext(
				httpServletRequest,
				HashMapBuilder.<String, Serializable>put(
					"companyId", CompanyThreadLocal.getCompanyId()
				).put(
					"filter", filter
				).put(
					"scopeKey", scopeKey
				).build()),
			_getFilter(
				acceptLanguage, graphQLDTOContributor.getEntityModel(), filter),
			Pagination.of(page, pageSize), search,
			_getSorts(
				acceptLanguage, graphQLDTOContributor.getEntityModel(), sort));
	}

	public Object update(
			Object dto, GraphQLDTOContributor graphQLDTOContributor,
			HttpServletRequest httpServletRequest, long id)
		throws Exception {

		return graphQLDTOContributor.updateDTO(
			dto, _getDTOConverterContext(httpServletRequest, null), id);
	}

	private Aggregation _getAggregation(
		AcceptLanguage acceptLanguage, List<String> aggregationStrings,
		EntityModel entityModel) {

		if (aggregationStrings == null) {
			return null;
		}

		AggregationContextProvider aggregationContextProvider =
			new AggregationContextProvider(_language, _portal);

		return aggregationContextProvider.createContext(
			acceptLanguage, aggregationStrings.toArray(new String[0]),
			entityModel);
	}

	private DTOConverterContext _getDTOConverterContext(
			HttpServletRequest httpServletRequest,
			Map<String, Serializable> attributes)
		throws Exception {

		AcceptLanguage acceptLanguage = new AcceptLanguageImpl(
			httpServletRequest, _language, _portal);

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				acceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, null,
				acceptLanguage.getPreferredLocale(), null,
				_portal.getUser(httpServletRequest));

		if (attributes != null) {
			defaultDTOConverterContext.setAttributes(attributes);
		}

		return defaultDTOConverterContext;
	}

	private Filter _getFilter(
			AcceptLanguage acceptLanguage, EntityModel entityModel,
			String filterString)
		throws Exception {

		FilterContextProvider filterContextProvider = new FilterContextProvider(
			_expressionConvert, _filterParserProvider, _language, _portal);

		return filterContextProvider.createContext(
			acceptLanguage, entityModel, filterString);
	}

	private Sort[] _getSorts(
		AcceptLanguage acceptLanguage, EntityModel entityModel,
		String sortsString) {

		SortContextProvider sortContextProvider = new SortContextProvider(
			_language, _portal, _sortParserProvider);

		return sortContextProvider.createContext(
			acceptLanguage, entityModel, sortsString);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SortParserProvider _sortParserProvider;

}