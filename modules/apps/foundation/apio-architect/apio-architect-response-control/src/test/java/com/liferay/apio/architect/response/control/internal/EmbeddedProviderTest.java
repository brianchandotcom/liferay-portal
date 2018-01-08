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

package com.liferay.apio.architect.response.control.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class EmbeddedProviderTest {

	@Test
	public void testEmbeddedProviderReturnsAlwaysFalseIfMissingParam() {
		Predicate<String> predicate = _getPredicate(null);

		assertThat(predicate.test("embedded"), is(false));
		assertThat(predicate.test("embedded.inner"), is(false));
		assertThat(predicate.test("randomThing"), is(false));
	}

	@Test
	public void testEmbeddedProviderReturnValidEmbedded() {
		Predicate<String> predicate = _getPredicate("embedded,embedded.inner");

		assertThat(predicate.test("embedded"), is(true));
		assertThat(predicate.test("embedded.inner"), is(true));
		assertThat(predicate.test("randomThing"), is(false));
	}

	private Predicate<String> _getPredicate(String embedded) {
		EmbeddedProvider embeddedProvider = new EmbeddedProvider();

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("embedded")
		).thenReturn(
			embedded
		);

		return embeddedProvider.createContext(httpServletRequest);
	}

}