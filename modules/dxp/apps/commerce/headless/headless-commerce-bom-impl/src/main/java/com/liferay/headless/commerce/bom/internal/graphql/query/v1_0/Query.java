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

package com.liferay.headless.commerce.bom.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.bom.dto.v1_0.Area;
import com.liferay.headless.commerce.bom.dto.v1_0.Folder;
import com.liferay.headless.commerce.bom.dto.v1_0.Product;
import com.liferay.headless.commerce.bom.resource.v1_0.AreaResource;
import com.liferay.headless.commerce.bom.resource.v1_0.FolderResource;
import com.liferay.headless.commerce.bom.resource.v1_0.ProductResource;
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

	public static void setAreaResourceComponentServiceObjects(
		ComponentServiceObjects<AreaResource>
			areaResourceComponentServiceObjects) {

		_areaResourceComponentServiceObjects =
			areaResourceComponentServiceObjects;
	}

	public static void setFolderResourceComponentServiceObjects(
		ComponentServiceObjects<FolderResource>
			folderResourceComponentServiceObjects) {

		_folderResourceComponentServiceObjects =
			folderResourceComponentServiceObjects;
	}

	public static void setProductResourceComponentServiceObjects(
		ComponentServiceObjects<ProductResource>
			productResourceComponentServiceObjects) {

		_productResourceComponentServiceObjects =
			productResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {area(id: ___){breadcrumbs, data}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Area area(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_areaResourceComponentServiceObjects,
			this::_populateResourceContext,
			areaResource -> areaResource.getArea(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {folder(id: ___){breadcrumbs, data}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Folder folder(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.getFolder(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {products(q: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductPage products(@GraphQLName("q") String q) throws Exception {
		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> new ProductPage(
				productResource.getProductsPage(q)));
	}

	@GraphQLName("AreaPage")
	public class AreaPage {

		public AreaPage(Page areaPage) {
			actions = areaPage.getActions();

			items = areaPage.getItems();
			lastPage = areaPage.getLastPage();
			page = areaPage.getPage();
			pageSize = areaPage.getPageSize();
			totalCount = areaPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Area> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FolderPage")
	public class FolderPage {

		public FolderPage(Page folderPage) {
			actions = folderPage.getActions();

			items = folderPage.getItems();
			lastPage = folderPage.getLastPage();
			page = folderPage.getPage();
			pageSize = folderPage.getPageSize();
			totalCount = folderPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Folder> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductPage")
	public class ProductPage {

		public ProductPage(Page productPage) {
			actions = productPage.getActions();

			items = productPage.getItems();
			lastPage = productPage.getLastPage();
			page = productPage.getPage();
			pageSize = productPage.getPageSize();
			totalCount = productPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Product> items;

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

	private void _populateResourceContext(AreaResource areaResource)
		throws Exception {

		areaResource.setContextAcceptLanguage(_acceptLanguage);
		areaResource.setContextCompany(_company);
		areaResource.setContextHttpServletRequest(_httpServletRequest);
		areaResource.setContextHttpServletResponse(_httpServletResponse);
		areaResource.setContextUriInfo(_uriInfo);
		areaResource.setContextUser(_user);
		areaResource.setGroupLocalService(_groupLocalService);
		areaResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(FolderResource folderResource)
		throws Exception {

		folderResource.setContextAcceptLanguage(_acceptLanguage);
		folderResource.setContextCompany(_company);
		folderResource.setContextHttpServletRequest(_httpServletRequest);
		folderResource.setContextHttpServletResponse(_httpServletResponse);
		folderResource.setContextUriInfo(_uriInfo);
		folderResource.setContextUser(_user);
		folderResource.setGroupLocalService(_groupLocalService);
		folderResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(ProductResource productResource)
		throws Exception {

		productResource.setContextAcceptLanguage(_acceptLanguage);
		productResource.setContextCompany(_company);
		productResource.setContextHttpServletRequest(_httpServletRequest);
		productResource.setContextHttpServletResponse(_httpServletResponse);
		productResource.setContextUriInfo(_uriInfo);
		productResource.setContextUser(_user);
		productResource.setGroupLocalService(_groupLocalService);
		productResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AreaResource>
		_areaResourceComponentServiceObjects;
	private static ComponentServiceObjects<FolderResource>
		_folderResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;

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