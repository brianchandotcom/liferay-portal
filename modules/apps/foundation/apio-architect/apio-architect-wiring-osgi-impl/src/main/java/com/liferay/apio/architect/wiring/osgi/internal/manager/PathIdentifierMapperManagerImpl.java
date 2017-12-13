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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.identifier.RootIdentifier;
import com.liferay.apio.architect.identifier.mapper.PathIdentifierMapper;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;

import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PathIdentifierMapperManagerImpl
	extends BaseManager<PathIdentifierMapper>
	implements PathIdentifierMapperManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Identifier> Optional<T> map(Class<T> clazz, Path path) {
		if (Identifier.class == clazz) {
			return Optional.of((T)new Identifier() {});
		}

		if (RootIdentifier.class == clazz) {
			return Optional.of((T)new RootIdentifier() {});
		}

		Optional<PathIdentifierMapper> optional = getServiceOptional(clazz);

		return optional.map(
			pathIdentifierMapper ->
				(PathIdentifierMapper<T>)pathIdentifierMapper
		).map(
			pathIdentifierMapper -> pathIdentifierMapper.map(path)
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Identifier, U> Optional<Path> map(
		T identifier, Class<? extends Identifier> identifierClass,
		Class<U> modelClass) {

		if (Identifier.class == identifierClass) {
			return Optional.of(new Path());
		}

		if (RootIdentifier.class == identifierClass) {
			return Optional.of(new Path());
		}

		Optional<PathIdentifierMapper> optional = getServiceOptional(
			identifierClass);

		return optional.map(
			pathIdentifierMapper ->
				(PathIdentifierMapper<T>)pathIdentifierMapper
		).map(
			pathIdentifierMapper ->
				pathIdentifierMapper.map(identifier, modelClass)
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<PathIdentifierMapper> serviceReference) {

		addService(serviceReference);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<PathIdentifierMapper> serviceReference) {

		removeService(serviceReference);
	}

}