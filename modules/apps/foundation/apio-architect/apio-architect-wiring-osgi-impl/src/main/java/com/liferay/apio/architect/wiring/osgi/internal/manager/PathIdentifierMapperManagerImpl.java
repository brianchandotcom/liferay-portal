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

import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.SimpleBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PathIdentifierMapperManagerImpl
	extends SimpleBaseManager<PathIdentifierMapper>
	implements PathIdentifierMapperManager {

	public PathIdentifierMapperManagerImpl() {
		super(PathIdentifierMapper.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<T> map(Class<T> clazz, Path path) {
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
	public <T, U> Optional<Path> map(
		T identifier, Class<?> identifierClass, Class<U> modelClass) {

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

}