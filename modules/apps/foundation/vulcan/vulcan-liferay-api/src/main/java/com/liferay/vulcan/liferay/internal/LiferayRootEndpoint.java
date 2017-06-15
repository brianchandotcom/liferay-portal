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

package com.liferay.vulcan.liferay.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.vulcan.endpoint.RootEndpoint;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.representor.Resource;
import com.liferay.vulcan.representor.Routes;
import com.liferay.vulcan.wiring.osgi.GenericUtil;
import com.liferay.vulcan.wiring.osgi.ProviderManager;
import com.liferay.vulcan.wiring.osgi.ResourceManager;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class LiferayRootEndpoint implements RootEndpoint {

	@Activate
	public void activate(BundleContext bundleContext) {
		ServiceReferenceMapper<String, Resource> serviceReferenceMapper =
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(service, emitter) -> emitter.emit(service.getPath()));

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Resource.class, null, serviceReferenceMapper);
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	public <T> SingleModel<T> getCollectionItemSingleModel(
		String path, String id) {

		Resource<T> resource = (Resource<T>)_serviceTrackerMap.getService(path);

		Class<T> modelClass = GenericUtil.getGenericClass(
			resource, Resource.class);

		Routes<T> routes = _resourceManager.getRoutes(modelClass);

		Function<String, T> modelFunction =
			routes.getModelFunction().apply(this::provide);

		T model = modelFunction.apply(id);

		return new SingleModel<>(model, modelClass);
	}

	@Override
	public <T> Page<T> getCollectionPage(String path) {
		Resource<T> resource = (Resource<T>)_serviceTrackerMap.getService(path);

		Class<T> modelClass = GenericUtil.getGenericClass(
			resource, Resource.class);

		Routes<T> routes = _resourceManager.getRoutes(modelClass);

		Function<Function<Class<?>, Optional<?>>, PageItems<T>>
			pageItemsFunction = routes.getPageItemsFunction();

		PageItems<T> pageItems = pageItemsFunction.apply(this::provide);

		Optional<Pagination> optional = provide(Pagination.class);

		Pagination pagination = optional.orElseThrow(
			() -> new MustHaveProvider(Pagination.class));

		return new DefaultPage<>(
			modelClass, pageItems.getItems(), pagination.getItemsPerPage(),
			pagination.getPageNumber(), pageItems.getTotalCount());
	}

	@Path("/group/{id}/")
	public LiferayRootEndpoint getGroupLiferayRootEndpoint(
		@PathParam("id") long id) {

		GroupThreadLocal.setGroupId(id);

		return this;
	}

	public <U> Optional<U> provide(Class<U> clazz) {
		return _providerManager.provide(clazz, _httpServletRequest);
	}

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private ResourceManager _resourceManager;

	private ServiceTrackerMap<String, Resource> _serviceTrackerMap;

	private static class DefaultPage<T> implements Page<T> {

		public DefaultPage(
			Class<T> modelClass, Collection<T> items, int itemsPerPage,
			int pageNumber, int totalCount) {

			_modelClass = modelClass;
			_items = items;
			_itemsPerPage = itemsPerPage;
			_pageNumber = pageNumber;
			_totalCount = totalCount;
		}

		@Override
		public Collection<T> getItems() {
			return _items;
		}

		@Override
		public int getItemsPerPage() {
			return _itemsPerPage;
		}

		@Override
		public int getLastPageNumber() {
			return -Math.floorDiv(-_totalCount, _itemsPerPage);
		}

		@Override
		public Class<T> getModelClass() {
			return _modelClass;
		}

		@Override
		public int getPageNumber() {
			return _pageNumber;
		}

		@Override
		public int getTotalCount() {
			return _totalCount;
		}

		@Override
		public boolean hasNext() {
			if (getLastPageNumber() > _pageNumber) {
				return true;
			}

			return false;
		}

		@Override
		public boolean hasPrevious() {
			if (_pageNumber > 1) {
				return true;
			}

			return false;
		}

		private final Collection<T> _items;
		private final int _itemsPerPage;
		private final Class<T> _modelClass;
		private final int _pageNumber;
		private final int _totalCount;

	}

}