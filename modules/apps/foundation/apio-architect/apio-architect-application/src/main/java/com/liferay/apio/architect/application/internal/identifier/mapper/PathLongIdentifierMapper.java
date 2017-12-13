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

package com.liferay.apio.architect.application.internal.identifier.mapper;

import com.liferay.apio.architect.error.ApioDeveloperError.UnresolvableURI;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.identifier.mapper.PathIdentifierMapper;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.wiring.osgi.manager.CollectionResourceManager;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Maps a {@link Path} to a {@link LongIdentifier}, and vice versa.
 *
 * <p>
 * {@code LongIdentifier} can then be provided as a parameter in the methods of
 * {@link Routes.Builder}.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PathLongIdentifierMapper
	implements PathIdentifierMapper<LongIdentifier> {

	@Override
	public <U> Path map(LongIdentifier longIdentifier, Class<U> modelClass) {
		String className = modelClass.getName();

		Optional<String> optional = _collectionResourceManager.getNameOptional(
			className);

		String name = optional.orElseThrow(
			() -> new UnresolvableURI(className));

		return new Path(name, String.valueOf(longIdentifier.getId()));
	}

	@Override
	public LongIdentifier map(Path path) {
		Try<Long> longTry = Try.fromFallible(
			() -> Long.parseLong(path.getId()));

		return () -> longTry.orElseThrow(BadRequestException::new);
	}

	@Reference
	private CollectionResourceManager _collectionResourceManager;

}