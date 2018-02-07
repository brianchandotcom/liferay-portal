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

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PathStringIdentifierMapperTest {

	@Test
	public void testMapReturnsPathIfNameManagerReturnsValidName() {
		PathStringIdentifierMapper pathStringIdentifierMapper =
			new PathStringIdentifierMapper();

		pathStringIdentifierMapper.nameManager = __ -> Optional.of("name");

		Path path = pathStringIdentifierMapper.map(
			StringIdentifier.class, "id");

		assertThat(path.getName(), is("name"));
		assertThat(path.getId(), is("id"));
	}

	@Test
	public void testMapReturnsString() {
		PathStringIdentifierMapper pathStringIdentifierMapper =
			new PathStringIdentifierMapper();

		String identifier = pathStringIdentifierMapper.map(
			new Path("name", "id"));

		assertThat(identifier, is("id"));
	}

	@Test(expected = UnresolvableURI.class)
	public void testMapThrowsExceptionIfNameManagerReturnsEmptyOptional() {
		PathStringIdentifierMapper pathStringIdentifierMapper =
			new PathStringIdentifierMapper();

		pathStringIdentifierMapper.nameManager = __ -> Optional.empty();

		pathStringIdentifierMapper.map(StringIdentifier.class, "id");
	}

	private interface StringIdentifier extends Identifier<String> {
	}

}