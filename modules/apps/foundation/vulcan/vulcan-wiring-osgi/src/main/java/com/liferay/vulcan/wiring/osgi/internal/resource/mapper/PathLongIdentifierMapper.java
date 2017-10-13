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

package com.liferay.vulcan.wiring.osgi.internal.resource.mapper;

import com.liferay.vulcan.error.VulcanDeveloperError.UnresolvableURI;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.mapper.PathIdentifierMapper;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.wiring.osgi.manager.CollectionResourceManager;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * This mapper can be used to map a {@link Path} to a {@link LongIdentifier} and
 * vice versa.
 *
 * <p>
 * The class {@link LongIdentifier} can then be provided as a parameter in
 * {@link com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true)
public class PathLongIdentifierMapper
	implements PathIdentifierMapper<LongIdentifier> {

	@Override
	public <U> Path map(LongIdentifier longIdentifier, Class<U> modelClass) {
		Optional<String> optional = _collectionResourceManager.getNameOptional(
			modelClass.getName());

		String name = optional.orElseThrow(
			() -> new UnresolvableURI(modelClass));

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