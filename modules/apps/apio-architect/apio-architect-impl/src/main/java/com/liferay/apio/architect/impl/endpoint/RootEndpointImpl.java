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

package com.liferay.apio.architect.impl.endpoint;

import static com.liferay.apio.architect.impl.endpoint.ExceptionSupplierUtil.notFound;
import static com.liferay.apio.architect.impl.url.URLCreator.createCollectionURL;

import com.liferay.apio.architect.documentation.APIDescription;
import com.liferay.apio.architect.documentation.APITitle;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.impl.documentation.Documentation;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.impl.url.ApplicationURL;
import com.liferay.apio.architect.impl.wiring.osgi.manager.provider.ProviderManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.representable.IdentifierClassManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.router.CollectionRouterManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.router.ItemRouterManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.router.NestedCollectionRouterManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.uri.mapper.PathIdentifierMapperManager;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component
public class RootEndpointImpl implements RootEndpoint {

	@Activate
	public void activate() {
		_documentation = new Documentation(
			() -> _provide(APITitle.class),
			() -> _provide(APIDescription.class),
			() -> _representableManager.getRepresentors(),
			() -> _collectionRouterManager.getCollectionRoutes(),
			() -> _itemRouterManager.getItemRoutes(),
			() -> _nestedCollectionRouterManager.getNestedCollectionRoutes());
	}

	@Override
	public BinaryEndpoint binaryEndpoint() {
		return new BinaryEndpoint(
			_representableManager::getRepresentorOptional,
			this::_getSingleModelTry);
	}

	@Override
	public Documentation documentation() {
		return _documentation;
	}

	@Override
	public FormEndpoint formEndpoint() {
		return new FormEndpoint(
			_collectionRouterManager::getCollectionRoutesOptional,
			_itemRouterManager::getItemRoutesOptional,
			_nestedCollectionRouterManager::getNestedCollectionRoutesOptional);
	}

	@Override
	public Response home() {
		List<String> resourceNames =
			_collectionRouterManager.getResourceNames();

		ApplicationURL applicationURL = _providerManager.provideMandatory(
			_httpServletRequest, ApplicationURL.class);

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

		resourceNames.forEach(
			name -> jsonObjectBuilder.nestedField(
				"resources", name, "href"
			).stringValue(
				createCollectionURL(applicationURL, name)
			));

		return Response.ok(
			jsonObjectBuilder.build()
		).type(
			MediaType.valueOf("application/json")
		).build();
	}

	@Override
	public PageEndpointImpl pageEndpoint(String name) {
		return new PageEndpointImpl<>(
			name, _httpServletRequest,
			_identifierClassManager::getIdentifierClassOptional,
			id -> _getSingleModelTry(name, id),
			() -> _collectionRouterManager.getCollectionRoutesOptional(name),
			() -> _representableManager.getRepresentorOptional(name),
			() -> _itemRouterManager.getItemRoutesOptional(name),
			nestedName -> _nestedCollectionRouterManager.
				getNestedCollectionRoutesOptional(name, nestedName),
			_pathIdentifierMapperManager::mapToIdentifierOrFail);
	}

	private <T, S> Try<SingleModel<T>> _getSingleModelTry(
		String name, String id) {

		return Try.success(
			name
		).<ItemRoutes<T, S>>mapOptional(
			_itemRouterManager::getItemRoutesOptional
		).mapOptional(
			ItemRoutes::getItemFunctionOptional, notFound(name, id)
		).flatMap(
			function -> function.apply(
				_httpServletRequest
			).apply(
				_pathIdentifierMapperManager.mapToIdentifierOrFail(
					new Path(name, id))
			)
		);
	}

	private <T> Optional<T> _provide(Class<T> clazz) {
		return _providerManager.provideOptional(_httpServletRequest, clazz);
	}

	@Reference
	private CollectionRouterManager _collectionRouterManager;

	private Documentation _documentation;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private IdentifierClassManager _identifierClassManager;

	@Reference
	private ItemRouterManager _itemRouterManager;

	@Reference
	private NestedCollectionRouterManager _nestedCollectionRouterManager;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

}