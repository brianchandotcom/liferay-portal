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
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Maps a {@link Path} to a {@link String}, and vice versa.
 *
 * <p>
 * {@code String} can then be used as the identifier of a resource.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PathStringIdentifierMapper
	implements PathIdentifierMapper<String> {

	@Override
	public Path map(Class<? extends Identifier<String>> clazz, String string) {
		String className = clazz.getName();

		Optional<String> optional = nameManager.getNameOptional(className);

		String name = optional.orElseThrow(
			() -> new UnresolvableURI(className));

		return new Path(name, string);
	}

	@Override
	public String map(Path path) {
		return path.getId();
	}

	@Reference
	protected NameManager nameManager;

}