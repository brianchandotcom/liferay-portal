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

package com.liferay.apio.architect.application.internal.uri.mapper;

import com.liferay.apio.architect.error.ApioDeveloperError.UnresolvableURI;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Maps a {@link Path} to a {@link Long}, and vice versa.
 *
 * <p>
 * {@code Long} can then be used as the identifier of a resource.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PathLongIdentifierMapper implements PathIdentifierMapper<Long> {

	@Override
	public Path map(Class<? extends Identifier<Long>> clazz, Long aLong) {
		String className = clazz.getName();

		Optional<String> optional = nameManager.getNameOptional(className);

		return optional.map(
			name -> new Path(name, String.valueOf(aLong))
		).orElseThrow(
			() -> new UnresolvableURI(className)
		);
	}

	@Override
	public Long map(Path path) {
		Try<Long> longTry = Try.fromFallible(
			() -> Long.parseLong(path.getId()));

		return longTry.orElseThrow(BadRequestException::new);
	}

	@Reference
	protected NameManager nameManager;

}