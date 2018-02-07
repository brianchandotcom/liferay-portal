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

import com.liferay.apio.architect.error.ApioDeveloperError.UnresolvableURI;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.uri.Path;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PathLongIdentifierMapperTest {

	@Test
	public void testMapReturnsLongIfValidPathId() {
		PathLongIdentifierMapper pathLongIdentifierMapper =
			new PathLongIdentifierMapper();

		Long identifier = pathLongIdentifierMapper.map(new Path("name", "42"));

		assertThat(identifier, is(42L));
	}

	@Test
	public void testMapReturnsPathIfNameManagerReturnsValidName() {
		PathLongIdentifierMapper pathLongIdentifierMapper =
			new PathLongIdentifierMapper();

		pathLongIdentifierMapper.nameManager = __ -> Optional.of("name");

		Path path = pathLongIdentifierMapper.map(LongIdentifier.class, 42L);

		assertThat(path.getName(), is("name"));
		assertThat(path.getId(), is("42"));
	}

	@Test(expected = UnresolvableURI.class)
	public void testMapThrowsExceptionIfNameManagerReturnsEmptyOptional() {
		PathLongIdentifierMapper pathLongIdentifierMapper =
			new PathLongIdentifierMapper();

		pathLongIdentifierMapper.nameManager = __ -> Optional.empty();

		pathLongIdentifierMapper.map(LongIdentifier.class, 42L);
	}

	@Test(expected = BadRequestException.class)
	public void testMapThrowsExceptionIfNoLongPathId() {
		PathLongIdentifierMapper pathLongIdentifierMapper =
			new PathLongIdentifierMapper();

		pathLongIdentifierMapper.map(new Path("name", "wrong"));
	}

	private interface LongIdentifier extends Identifier<Long> {
	}

}