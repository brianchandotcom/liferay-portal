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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_PRINCIPAL_TYPE_ARGUMENT;

import static java.lang.reflect.Modifier.isStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import com.liferay.apio.architect.functional.Try;

import java.lang.reflect.Field;

import java.util.HashSet;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class TypeArgumentPropertiesTest {

	@Test
	public void testPrincipalTypeArgumentShouldBeEqualToIdentifierClass() {
		assertThat(
			KEY_IDENTIFIER_CLASS, is(equalTo(KEY_PRINCIPAL_TYPE_ARGUMENT)));
	}

	@Test
	public void testThereIsOnlyOneDuplicateConstant() {
		Stream<Field> stream = Stream.of(
			TypeArgumentProperties.class.getDeclaredFields());

		boolean noDuplicates = stream.filter(
			field -> isStatic(field.getModifiers())
		).filter(
			field -> "KEY_PRINCIPAL_TYPE_ARGUMENT".equals(field.getName())
		).filter(
			field -> "KEY_IDENTIFIER_CLASS".equals(field.getName())
		).map(
			field -> Try.fromFallible(() -> (String)field.get(null)).orElse("")
		).allMatch(
			new HashSet<>()::add
		);

		assertThat(noDuplicates, is(true));
	}

}