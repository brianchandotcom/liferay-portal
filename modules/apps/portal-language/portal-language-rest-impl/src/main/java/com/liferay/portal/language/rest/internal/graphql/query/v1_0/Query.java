/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.graphql.query.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.language.rest.dto.v1_0.Language;
import com.liferay.portal.language.rest.resource.v1_0.LanguageResource;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Thiago Buarque
 * @generated
 */
@Generated("")
public class Query {

	public static void setLanguageResourceComponentServiceObjects(
		ComponentServiceObjects<LanguageResource>
			languageResourceComponentServiceObjects) {

		_languageResourceComponentServiceObjects =
			languageResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {languages(keys: ___, languageId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public LanguagePage languages(
			@GraphQLName("keys") String[] keys,
			@GraphQLName("languageId") String languageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_languageResourceComponentServiceObjects,
			this::_populateResourceContext,
			languageResource -> new LanguagePage(
				languageResource.getLanguagesPage(keys, languageId)));
	}

	@GraphQLName("LanguagePage")
	public class LanguagePage {

		public LanguagePage(Page languagePage) {
			actions = languagePage.getActions();

			items = languagePage.getItems();
			lastPage = languagePage.getLastPage();
			page = languagePage.getPage();
			pageSize = languagePage.getPageSize();
			totalCount = languagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Language> items;

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

	private void _populateResourceContext(LanguageResource languageResource)
		throws Exception {

		languageResource.setContextAcceptLanguage(_acceptLanguage);
		languageResource.setContextCompany(_company);
		languageResource.setContextHttpServletRequest(_httpServletRequest);
		languageResource.setContextHttpServletResponse(_httpServletResponse);
		languageResource.setContextUriInfo(_uriInfo);
		languageResource.setContextUser(_user);
		languageResource.setGroupLocalService(_groupLocalService);
		languageResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<LanguageResource>
		_languageResourceComponentServiceObjects;

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