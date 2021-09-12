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

package com.liferay.digital.signature.rest.internal.graphql.query.v1_0;

import com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope;
import com.liferay.digital.signature.rest.resource.v1_0.DSEnvelopeResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
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
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class Query {

	public static void setDSEnvelopeResourceComponentServiceObjects(
		ComponentServiceObjects<DSEnvelopeResource>
			dsEnvelopeResourceComponentServiceObjects) {

		_dsEnvelopeResourceComponentServiceObjects =
			dsEnvelopeResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {dSEnvelopes(companyId: ___, groupId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DSEnvelopePage dSEnvelopes(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dsEnvelopeResourceComponentServiceObjects,
			this::_populateResourceContext,
			dsEnvelopeResource -> new DSEnvelopePage(
				dsEnvelopeResource.getDSEnvelopesPage(
					companyId, groupId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {dSEnvelope(companyId: ___, envelopeId: ___, groupId: ___){dateCreated, dateModified, dsDocument, dsRecipient, emailBlurb, emailSubject, id, name, senderEmailAddress, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves an envelope.")
	public DSEnvelope dSEnvelope(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("envelopeId") String envelopeId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dsEnvelopeResourceComponentServiceObjects,
			this::_populateResourceContext,
			dsEnvelopeResource -> dsEnvelopeResource.getDSEnvelope(
				companyId, groupId, envelopeId));
	}

	@GraphQLName("DSEnvelopePage")
	public class DSEnvelopePage {

		public DSEnvelopePage(Page dsEnvelopePage) {
			actions = dsEnvelopePage.getActions();

			items = dsEnvelopePage.getItems();
			lastPage = dsEnvelopePage.getLastPage();
			page = dsEnvelopePage.getPage();
			pageSize = dsEnvelopePage.getPageSize();
			totalCount = dsEnvelopePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<DSEnvelope> items;

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

	private void _populateResourceContext(DSEnvelopeResource dsEnvelopeResource)
		throws Exception {

		dsEnvelopeResource.setContextAcceptLanguage(_acceptLanguage);
		dsEnvelopeResource.setContextCompany(_company);
		dsEnvelopeResource.setContextHttpServletRequest(_httpServletRequest);
		dsEnvelopeResource.setContextHttpServletResponse(_httpServletResponse);
		dsEnvelopeResource.setContextUriInfo(_uriInfo);
		dsEnvelopeResource.setContextUser(_user);
		dsEnvelopeResource.setGroupLocalService(_groupLocalService);
		dsEnvelopeResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DSEnvelopeResource>
		_dsEnvelopeResourceComponentServiceObjects;

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