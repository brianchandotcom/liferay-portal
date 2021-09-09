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

package com.liferay.headless.commerce.shop.by.diagram.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Diagram;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramEntryResource;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramResource;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.PinResource;
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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Query {

	public static void setDiagramResourceComponentServiceObjects(
		ComponentServiceObjects<DiagramResource>
			diagramResourceComponentServiceObjects) {

		_diagramResourceComponentServiceObjects =
			diagramResourceComponentServiceObjects;
	}

	public static void setDiagramEntryResourceComponentServiceObjects(
		ComponentServiceObjects<DiagramEntryResource>
			diagramEntryResourceComponentServiceObjects) {

		_diagramEntryResourceComponentServiceObjects =
			diagramEntryResourceComponentServiceObjects;
	}

	public static void setPinResourceComponentServiceObjects(
		ComponentServiceObjects<PinResource>
			pinResourceComponentServiceObjects) {

		_pinResourceComponentServiceObjects =
			pinResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeDiagram(externalReferenceCode: ___){color, diagramEntries, id, imageId, imageURL, pins, radius, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Diagram productByExternalReferenceCodeDiagram(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramResource ->
				diagramResource.getProductByExternalReferenceCodeDiagram(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdDiagram(productId: ___){color, diagramEntries, id, imageId, imageURL, pins, radius, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Diagram productIdDiagram(@GraphQLName("productId") Long productId)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramResource -> diagramResource.getProductIdDiagram(productId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeDiagramEntries(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DiagramEntryPage productByExternalReferenceCodeDiagramEntries(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource -> new DiagramEntryPage(
				diagramEntryResource.
					getProductByExternalReferenceCodeDiagramEntriesPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdDiagramEntries(page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DiagramEntryPage productIdDiagramEntries(
			@GraphQLName("productId") Long productId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource -> new DiagramEntryPage(
				diagramEntryResource.getProductIdDiagramEntriesPage(
					productId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodePins(externalReferenceCode: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PinPage productByExternalReferenceCodePins(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> new PinPage(
				pinResource.getProductByExternalReferenceCodePinsPage(
					externalReferenceCode, search,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(pinResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdPins(page: ___, pageSize: ___, productId: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PinPage productIdPins(
			@GraphQLName("productId") Long productId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> new PinPage(
				pinResource.getProductIdPinsPage(
					productId, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(pinResource, sortsString))));
	}

	@GraphQLName("DiagramPage")
	public class DiagramPage {

		public DiagramPage(Page diagramPage) {
			actions = diagramPage.getActions();

			items = diagramPage.getItems();
			lastPage = diagramPage.getLastPage();
			page = diagramPage.getPage();
			pageSize = diagramPage.getPageSize();
			totalCount = diagramPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Diagram> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DiagramEntryPage")
	public class DiagramEntryPage {

		public DiagramEntryPage(Page diagramEntryPage) {
			actions = diagramEntryPage.getActions();

			items = diagramEntryPage.getItems();
			lastPage = diagramEntryPage.getLastPage();
			page = diagramEntryPage.getPage();
			pageSize = diagramEntryPage.getPageSize();
			totalCount = diagramEntryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<DiagramEntry> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PinPage")
	public class PinPage {

		public PinPage(Page pinPage) {
			actions = pinPage.getActions();

			items = pinPage.getItems();
			lastPage = pinPage.getLastPage();
			page = pinPage.getPage();
			pageSize = pinPage.getPageSize();
			totalCount = pinPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Pin> items;

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

	private void _populateResourceContext(DiagramResource diagramResource)
		throws Exception {

		diagramResource.setContextAcceptLanguage(_acceptLanguage);
		diagramResource.setContextCompany(_company);
		diagramResource.setContextHttpServletRequest(_httpServletRequest);
		diagramResource.setContextHttpServletResponse(_httpServletResponse);
		diagramResource.setContextUriInfo(_uriInfo);
		diagramResource.setContextUser(_user);
		diagramResource.setGroupLocalService(_groupLocalService);
		diagramResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			DiagramEntryResource diagramEntryResource)
		throws Exception {

		diagramEntryResource.setContextAcceptLanguage(_acceptLanguage);
		diagramEntryResource.setContextCompany(_company);
		diagramEntryResource.setContextHttpServletRequest(_httpServletRequest);
		diagramEntryResource.setContextHttpServletResponse(
			_httpServletResponse);
		diagramEntryResource.setContextUriInfo(_uriInfo);
		diagramEntryResource.setContextUser(_user);
		diagramEntryResource.setGroupLocalService(_groupLocalService);
		diagramEntryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(PinResource pinResource)
		throws Exception {

		pinResource.setContextAcceptLanguage(_acceptLanguage);
		pinResource.setContextCompany(_company);
		pinResource.setContextHttpServletRequest(_httpServletRequest);
		pinResource.setContextHttpServletResponse(_httpServletResponse);
		pinResource.setContextUriInfo(_uriInfo);
		pinResource.setContextUser(_user);
		pinResource.setGroupLocalService(_groupLocalService);
		pinResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DiagramResource>
		_diagramResourceComponentServiceObjects;
	private static ComponentServiceObjects<DiagramEntryResource>
		_diagramEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<PinResource>
		_pinResourceComponentServiceObjects;

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