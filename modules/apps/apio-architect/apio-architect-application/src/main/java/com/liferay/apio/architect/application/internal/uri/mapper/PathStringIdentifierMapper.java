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

import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Maps a {@link Path} to a {@link String}, and vice versa.
 *
 * <p>
 * {@code String} can then be used as the identifier of a resource model.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class PathStringIdentifierMapper
	implements PathIdentifierMapper<String> {

	@Override
	public String map(Path path) {
		return path.getId();
	}

	@Override
	public Path map(String name, String string) {
		return new Path(name, string);
	}

}