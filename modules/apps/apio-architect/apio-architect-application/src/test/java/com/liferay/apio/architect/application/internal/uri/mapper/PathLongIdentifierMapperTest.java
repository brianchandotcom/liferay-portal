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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PathLongIdentifierMapperTest {

	@Test
	public void testMapReturnsLongIfValidPathId() {
		Long identifier = _pathIdentifierMapper.map(new Path("name", "42"));

		assertThat(identifier, is(42L));
	}

	@Test
	public void testMapReturnsPath() {
		Path path = _pathIdentifierMapper.map("name", 42L);

		assertThat(path.getName(), is("name"));
		assertThat(path.getId(), is("42"));
	}

	@Test(expected = BadRequestException.class)
	public void testMapThrowsExceptionIfNoLongPathId() {
		_pathIdentifierMapper.map(new Path("name", "wrong"));
	}

	private final PathIdentifierMapper<Long> _pathIdentifierMapper =
		new PathLongIdentifierMapper();

}