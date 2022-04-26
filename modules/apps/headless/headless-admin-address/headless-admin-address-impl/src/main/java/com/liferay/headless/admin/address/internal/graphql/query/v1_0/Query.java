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

package com.liferay.headless.admin.address.internal.graphql.query.v1_0;

import com.liferay.headless.admin.address.dto.v1_0.Country;
import com.liferay.headless.admin.address.dto.v1_0.Region;
import com.liferay.headless.admin.address.resource.v1_0.CountryResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public class Query {

	public static void setCountryResourceComponentServiceObjects(
		ComponentServiceObjects<CountryResource>
			countryResourceComponentServiceObjects) {

		_countryResourceComponentServiceObjects =
			countryResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {countries(active: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CountryPage countries(
			@GraphQLName("active") Boolean active,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_countryResourceComponentServiceObjects,
			this::_populateResourceContext,
			countryResource -> new CountryPage(
				countryResource.getCountriesPage(
					active, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(countryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {countryByA2(a2: ___){a2, a3, active, billingAllowed, groupFilterEnabled, id, idd, name, number, position, regions, shippingAllowed, subjectToVAT, title_i18n, zipRequired}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Country countryByA2(@GraphQLName("a2") String a2) throws Exception {
		return _applyComponentServiceObjects(
			_countryResourceComponentServiceObjects,
			this::_populateResourceContext,
			countryResource -> countryResource.getCountryByA2(a2));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {countryByA3(a3: ___){a2, a3, active, billingAllowed, groupFilterEnabled, id, idd, name, number, position, regions, shippingAllowed, subjectToVAT, title_i18n, zipRequired}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Country countryByA3(@GraphQLName("a3") String a3) throws Exception {
		return _applyComponentServiceObjects(
			_countryResourceComponentServiceObjects,
			this::_populateResourceContext,
			countryResource -> countryResource.getCountryByA3(a3));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {countryByName(name: ___){a2, a3, active, billingAllowed, groupFilterEnabled, id, idd, name, number, position, regions, shippingAllowed, subjectToVAT, title_i18n, zipRequired}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Country countryByName(@GraphQLName("name") String name)
		throws Exception {

		return _applyComponentServiceObjects(
			_countryResourceComponentServiceObjects,
			this::_populateResourceContext,
			countryResource -> countryResource.getCountryByName(name));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {countryByNumber(number: ___){a2, a3, active, billingAllowed, groupFilterEnabled, id, idd, name, number, position, regions, shippingAllowed, subjectToVAT, title_i18n, zipRequired}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Country countryByNumber(@GraphQLName("number") Integer number)
		throws Exception {

		return _applyComponentServiceObjects(
			_countryResourceComponentServiceObjects,
			this::_populateResourceContext,
			countryResource -> countryResource.getCountryByNumber(number));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {country(countryId: ___){a2, a3, active, billingAllowed, groupFilterEnabled, id, idd, name, number, position, regions, shippingAllowed, subjectToVAT, title_i18n, zipRequired}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Country country(@GraphQLName("countryId") Long countryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_countryResourceComponentServiceObjects,
			this::_populateResourceContext,
			countryResource -> countryResource.getCountry(countryId));
	}

	@GraphQLTypeExtension(Region.class)
	public class GetCountryTypeExtension {

		public GetCountryTypeExtension(Region region) {
			_region = region;
		}

		@GraphQLField
		public Country country() throws Exception {
			return _applyComponentServiceObjects(
				_countryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				countryResource -> countryResource.getCountry(
					_region.getCountryId()));
		}

		private Region _region;

	}

	@GraphQLName("CountryPage")
	public class CountryPage {

		public CountryPage(Page countryPage) {
			actions = countryPage.getActions();

			items = countryPage.getItems();
			lastPage = countryPage.getLastPage();
			page = countryPage.getPage();
			pageSize = countryPage.getPageSize();
			totalCount = countryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Country> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(CountryResource countryResource)
		throws Exception {

		countryResource.setContextAcceptLanguage(_acceptLanguage);
		countryResource.setContextCompany(_company);
		countryResource.setContextHttpServletRequest(_httpServletRequest);
		countryResource.setContextHttpServletResponse(_httpServletResponse);
		countryResource.setContextUriInfo(_uriInfo);
		countryResource.setContextUser(_user);
		countryResource.setGroupLocalService(_groupLocalService);
		countryResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<CountryResource>
		_countryResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}