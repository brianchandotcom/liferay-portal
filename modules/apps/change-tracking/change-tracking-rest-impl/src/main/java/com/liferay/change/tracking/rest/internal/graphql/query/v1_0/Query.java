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

import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.Process;
import com.liferay.change.tracking.rest.dto.v1_0.ProcessUser;
import com.liferay.change.tracking.rest.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessResource;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessUserResource;
import com.liferay.change.tracking.rest.resource.v1_0.SettingsResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class Query {

	public static void setCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<CollectionResource>
			collectionResourceComponentServiceObjects) {

		_collectionResourceComponentServiceObjects =
			collectionResourceComponentServiceObjects;
	}

	public static void setProcessResourceComponentServiceObjects(
		ComponentServiceObjects<ProcessResource>
			processResourceComponentServiceObjects) {

		_processResourceComponentServiceObjects =
			processResourceComponentServiceObjects;
	}

	public static void setProcessUserResourceComponentServiceObjects(
		ComponentServiceObjects<ProcessUserResource>
			processUserResourceComponentServiceObjects) {

		_processUserResourceComponentServiceObjects =
			processUserResourceComponentServiceObjects;
	}

	public static void setSettingsResourceComponentServiceObjects(
		ComponentServiceObjects<SettingsResource>
			settingsResourceComponentServiceObjects) {

		_settingsResourceComponentServiceObjects =
			settingsResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {collections(collectionType: ___, companyId: ___, page: ___, pageSize: ___, sorts: ___, userId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CollectionPage collections(
			@GraphQLName("collectionType")
				com.liferay.change.tracking.rest.constant.v1_0.CollectionType
					collectionType,
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("userId") Long userId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_collectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			collectionResource -> new CollectionPage(
				collectionResource.getCollectionsPage(
					collectionType, companyId, userId,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(collectionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {collection(collectionId: ___, companyId: ___){additionCount, companyId, dateStatus, deletionCount, description, id, modificationCount, name, statusByUserName}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Collection collection(
			@GraphQLName("collectionId") Long collectionId,
			@GraphQLName("companyId") Long companyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_collectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			collectionResource -> collectionResource.getCollection(
				collectionId, companyId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {processes(companyId: ___, keywords: ___, page: ___, pageSize: ___, processType: ___, sorts: ___, userId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProcessPage processes(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("processType")
				com.liferay.change.tracking.rest.constant.v1_0.ProcessType
					processType,
			@GraphQLName("userId") Long userId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> new ProcessPage(
				processResource.getProcessesPage(
					companyId, keywords, processType, userId,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(processResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {process(processId: ___){collection, companyId, dateCreated, id, percentage, processUser, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Process process(@GraphQLName("processId") Long processId)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> processResource.getProcess(processId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {processUsers(companyId: ___, keywords: ___, page: ___, pageSize: ___, processType: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProcessUserPage processUsers(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("processType")
				com.liferay.change.tracking.rest.constant.v1_0.ProcessType
					processType,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_processUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			processUserResource -> new ProcessUserPage(
				processUserResource.getProcessUsersPage(
					companyId, keywords, processType,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {settings(companyId: ___, userId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SettingsPage settings(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_settingsResourceComponentServiceObjects,
			this::_populateResourceContext,
			settingsResource -> new SettingsPage(
				settingsResource.getSettingsPage(companyId, userId)));
	}

	@GraphQLName("CollectionPage")
	public class CollectionPage {

		public CollectionPage(Page collectionPage) {
			items = collectionPage.getItems();
			page = collectionPage.getPage();
			pageSize = collectionPage.getPageSize();
			totalCount = collectionPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Collection> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProcessPage")
	public class ProcessPage {

		public ProcessPage(Page processPage) {
			items = processPage.getItems();
			page = processPage.getPage();
			pageSize = processPage.getPageSize();
			totalCount = processPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Process> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProcessUserPage")
	public class ProcessUserPage {

		public ProcessUserPage(Page processUserPage) {
			items = processUserPage.getItems();
			page = processUserPage.getPage();
			pageSize = processUserPage.getPageSize();
			totalCount = processUserPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ProcessUser> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SettingsPage")
	public class SettingsPage {

		public SettingsPage(Page settingsPage) {
			items = settingsPage.getItems();
			page = settingsPage.getPage();
			pageSize = settingsPage.getPageSize();
			totalCount = settingsPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Settings> items;

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

	private void _populateResourceContext(CollectionResource collectionResource)
		throws Exception {

		collectionResource.setContextAcceptLanguage(_acceptLanguage);
		collectionResource.setContextCompany(_company);
		collectionResource.setContextHttpServletRequest(_httpServletRequest);
		collectionResource.setContextHttpServletResponse(_httpServletResponse);
		collectionResource.setContextUriInfo(_uriInfo);
		collectionResource.setContextUser(_user);
	}

	private void _populateResourceContext(ProcessResource processResource)
		throws Exception {

		processResource.setContextAcceptLanguage(_acceptLanguage);
		processResource.setContextCompany(_company);
		processResource.setContextHttpServletRequest(_httpServletRequest);
		processResource.setContextHttpServletResponse(_httpServletResponse);
		processResource.setContextUriInfo(_uriInfo);
		processResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ProcessUserResource processUserResource)
		throws Exception {

		processUserResource.setContextAcceptLanguage(_acceptLanguage);
		processUserResource.setContextCompany(_company);
		processUserResource.setContextHttpServletRequest(_httpServletRequest);
		processUserResource.setContextHttpServletResponse(_httpServletResponse);
		processUserResource.setContextUriInfo(_uriInfo);
		processUserResource.setContextUser(_user);
	}

	private void _populateResourceContext(SettingsResource settingsResource)
		throws Exception {

		settingsResource.setContextAcceptLanguage(_acceptLanguage);
		settingsResource.setContextCompany(_company);
		settingsResource.setContextHttpServletRequest(_httpServletRequest);
		settingsResource.setContextHttpServletResponse(_httpServletResponse);
		settingsResource.setContextUriInfo(_uriInfo);
		settingsResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<CollectionResource>
		_collectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProcessResource>
		_processResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProcessUserResource>
		_processUserResourceComponentServiceObjects;
	private static ComponentServiceObjects<SettingsResource>
		_settingsResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}