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

package com.liferay.change.tracking.rest.internal.graphql.query.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.CTProcess;
import com.liferay.change.tracking.rest.resource.v1_0.CTCollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.CTEntryResource;
import com.liferay.change.tracking.rest.resource.v1_0.CTProcessResource;
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
 * @author David Truong
 * @generated
 */
@Generated("")
public class Query {

	public static void setCTCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<CTCollectionResource>
			ctCollectionResourceComponentServiceObjects) {

		_ctCollectionResourceComponentServiceObjects =
			ctCollectionResourceComponentServiceObjects;
	}

	public static void setCTEntryResourceComponentServiceObjects(
		ComponentServiceObjects<CTEntryResource>
			ctEntryResourceComponentServiceObjects) {

		_ctEntryResourceComponentServiceObjects =
			ctEntryResourceComponentServiceObjects;
	}

	public static void setCTProcessResourceComponentServiceObjects(
		ComponentServiceObjects<CTProcessResource>
			ctProcessResourceComponentServiceObjects) {

		_ctProcessResourceComponentServiceObjects =
			ctProcessResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cTCollections(page: ___, pageSize: ___, search: ___, sorts: ___, status: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CTCollectionPage cTCollections(
			@GraphQLName("status") Integer[] status,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> new CTCollectionPage(
				ctCollectionResource.getCTCollectionsPage(
					status, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						ctCollectionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cTCollection(ctCollectionId: ___){actions, dateCreated, dateModified, dateScheduled, description, id, name, ownerName, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CTCollection cTCollection(
			@GraphQLName("ctCollectionId") Long ctCollectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.getCTCollection(
				ctCollectionId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {ctCollectionCTEntries(ctCollectionId: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CTEntryPage ctCollectionCTEntries(
			@GraphQLName("ctCollectionId") Long ctCollectionId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctEntryResource -> new CTEntryPage(
				ctEntryResource.getCtCollectionCTEntriesPage(
					ctCollectionId, search,
					_filterBiFunction.apply(ctEntryResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(ctEntryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cTEntry(ctEntryId: ___){actions, changeType, ctCollectionId, dateCreated, dateModified, hideable, id, modelClassNameId, modelClassPK, ownerName, siteName, status, title, typeName}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CTEntry cTEntry(@GraphQLName("ctEntryId") Long ctEntryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctEntryResource -> ctEntryResource.getCTEntry(ctEntryId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cTProcesses(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___, status: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CTProcessPage cTProcesses(
			@GraphQLName("status") Integer[] status,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctProcessResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctProcessResource -> new CTProcessPage(
				ctProcessResource.getCTProcessesPage(
					status, search,
					_filterBiFunction.apply(ctProcessResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(ctProcessResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cTProcess(ctProcessId: ___){actions, datePublished, description, id, name, ownerName, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CTProcess cTProcess(@GraphQLName("ctProcessId") Long ctProcessId)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctProcessResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctProcessResource -> ctProcessResource.getCTProcess(ctProcessId));
	}

	@GraphQLTypeExtension(CTEntry.class)
	public class GetCTCollectionTypeExtension {

		public GetCTCollectionTypeExtension(CTEntry cTEntry) {
			_cTEntry = cTEntry;
		}

		@GraphQLField
		public CTCollection cTCollection() throws Exception {
			return _applyComponentServiceObjects(
				_ctCollectionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				ctCollectionResource -> ctCollectionResource.getCTCollection(
					_cTEntry.getCtCollectionId()));
		}

		private CTEntry _cTEntry;

	}

	@GraphQLTypeExtension(CTCollection.class)
	public class GetCtCollectionCTEntriesPageTypeExtension {

		public GetCtCollectionCTEntriesPageTypeExtension(
			CTCollection cTCollection) {

			_cTCollection = cTCollection;
		}

		@GraphQLField
		public CTEntryPage ctCollectionCTEntries(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_ctEntryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				ctEntryResource -> new CTEntryPage(
					ctEntryResource.getCtCollectionCTEntriesPage(
						_cTCollection.getId(), search,
						_filterBiFunction.apply(ctEntryResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(ctEntryResource, sortsString))));
		}

		private CTCollection _cTCollection;

	}

	@GraphQLName("CTCollectionPage")
	public class CTCollectionPage {

		public CTCollectionPage(Page ctCollectionPage) {
			actions = ctCollectionPage.getActions();

			items = ctCollectionPage.getItems();
			lastPage = ctCollectionPage.getLastPage();
			page = ctCollectionPage.getPage();
			pageSize = ctCollectionPage.getPageSize();
			totalCount = ctCollectionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<CTCollection> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CTEntryPage")
	public class CTEntryPage {

		public CTEntryPage(Page ctEntryPage) {
			actions = ctEntryPage.getActions();

			items = ctEntryPage.getItems();
			lastPage = ctEntryPage.getLastPage();
			page = ctEntryPage.getPage();
			pageSize = ctEntryPage.getPageSize();
			totalCount = ctEntryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<CTEntry> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CTProcessPage")
	public class CTProcessPage {

		public CTProcessPage(Page ctProcessPage) {
			actions = ctProcessPage.getActions();

			items = ctProcessPage.getItems();
			lastPage = ctProcessPage.getLastPage();
			page = ctProcessPage.getPage();
			pageSize = ctProcessPage.getPageSize();
			totalCount = ctProcessPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<CTProcess> items;

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

	private void _populateResourceContext(
			CTCollectionResource ctCollectionResource)
		throws Exception {

		ctCollectionResource.setContextAcceptLanguage(_acceptLanguage);
		ctCollectionResource.setContextCompany(_company);
		ctCollectionResource.setContextHttpServletRequest(_httpServletRequest);
		ctCollectionResource.setContextHttpServletResponse(
			_httpServletResponse);
		ctCollectionResource.setContextUriInfo(_uriInfo);
		ctCollectionResource.setContextUser(_user);
		ctCollectionResource.setGroupLocalService(_groupLocalService);
		ctCollectionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(CTEntryResource ctEntryResource)
		throws Exception {

		ctEntryResource.setContextAcceptLanguage(_acceptLanguage);
		ctEntryResource.setContextCompany(_company);
		ctEntryResource.setContextHttpServletRequest(_httpServletRequest);
		ctEntryResource.setContextHttpServletResponse(_httpServletResponse);
		ctEntryResource.setContextUriInfo(_uriInfo);
		ctEntryResource.setContextUser(_user);
		ctEntryResource.setGroupLocalService(_groupLocalService);
		ctEntryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(CTProcessResource ctProcessResource)
		throws Exception {

		ctProcessResource.setContextAcceptLanguage(_acceptLanguage);
		ctProcessResource.setContextCompany(_company);
		ctProcessResource.setContextHttpServletRequest(_httpServletRequest);
		ctProcessResource.setContextHttpServletResponse(_httpServletResponse);
		ctProcessResource.setContextUriInfo(_uriInfo);
		ctProcessResource.setContextUser(_user);
		ctProcessResource.setGroupLocalService(_groupLocalService);
		ctProcessResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<CTCollectionResource>
		_ctCollectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<CTEntryResource>
		_ctEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<CTProcessResource>
		_ctProcessResourceComponentServiceObjects;

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