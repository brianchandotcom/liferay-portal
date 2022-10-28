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

package com.liferay.portal.search.rest.internal.graphql.mutation.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorResults;
import com.liferay.portal.search.rest.resource.v1_0.SuggestionResource;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setSuggestionResourceComponentServiceObjects(
		ComponentServiceObjects<SuggestionResource>
			suggestionResourceComponentServiceObjects) {

		_suggestionResourceComponentServiceObjects =
			suggestionResourceComponentServiceObjects;
	}

	@GraphQLField
	public java.util.Collection<SuggestionsContributorResults>
			createSuggestionsPage(
				@GraphQLName("currentURL") String currentURL,
				@GraphQLName("destinationFriendlyURL") String
					destinationFriendlyURL,
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("keywordsParameterName") String
					keywordsParameterName,
				@GraphQLName("plid") Long plid,
				@GraphQLName("scope") String scope,
				@GraphQLName("search") String search,
				@GraphQLName("suggestionsContributorConfigurations")
					SuggestionsContributorConfiguration[]
						suggestionsContributorConfigurations)
		throws Exception {

		return _applyComponentServiceObjects(
			_suggestionResourceComponentServiceObjects,
			this::_populateResourceContext,
			suggestionResource -> {
				Page paginationPage = suggestionResource.postSuggestionsPage(
					currentURL, destinationFriendlyURL, groupId,
					keywordsParameterName, plid, scope, search,
					suggestionsContributorConfigurations);

				return paginationPage.getItems();
			});
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(SuggestionResource suggestionResource)
		throws Exception {

		suggestionResource.setContextAcceptLanguage(_acceptLanguage);
		suggestionResource.setContextCompany(_company);
		suggestionResource.setContextHttpServletRequest(_httpServletRequest);
		suggestionResource.setContextHttpServletResponse(_httpServletResponse);
		suggestionResource.setContextUriInfo(_uriInfo);
		suggestionResource.setContextUser(_user);
		suggestionResource.setGroupLocalService(_groupLocalService);
		suggestionResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<SuggestionResource>
		_suggestionResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}