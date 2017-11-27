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

package com.liferay.vulcan.sample.liferay.portal.resource.internal;

import static org.mockito.Matchers.any;

import com.liferay.vulcan.consumer.TriConsumer;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.wiring.osgi.internal.resource.builder.RoutesBuilderImpl;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Before;

import org.mockito.Mockito;

/**
 * @author Javier Gamarra
 */
public class CollectionResourceTest {

	@Before
	public void setUp() {
		Representor.Builder builder = new Representor.Builder<>(
			null, Mockito.mock(TriConsumer.class), null);

		representorBuilderSpy = Mockito.spy(builder);

		_identifierSpy = Mockito.spy(builder.identifier(null));

		Mockito.when(
			representorBuilderSpy.identifier(any(Function.class))
		).thenReturn(
			_identifierSpy
		);

		routesBuilderMock = Mockito.mock(RoutesBuilderImpl.class);

		Mockito.when(
			routesBuilderMock.addCollectionPageGetter(
				any(BiFunction.class), any(Class.class))
		).thenReturn(
			routesBuilderMock
		);

		Mockito.when(
			routesBuilderMock.addCollectionPageItemCreator(
				any(BiFunction.class), any(Class.class))
		).thenReturn(
			routesBuilderMock
		);

		Mockito.when(
			routesBuilderMock.addCollectionPageItemGetter(any(Function.class))
		).thenReturn(
			routesBuilderMock
		);

		Mockito.when(
			routesBuilderMock.addCollectionPageItemRemover(any(Consumer.class))
		).thenReturn(
			routesBuilderMock
		);

		Mockito.when(
			routesBuilderMock.addCollectionPageItemUpdater(
				any(BiFunction.class))
		).thenReturn(
			routesBuilderMock
		);
	}

	protected Representor.Builder.FirstStep verifyIdentifier() {
		return Mockito.verify(_identifierSpy);
	}

	protected RoutesBuilderImpl verifyRoute() {
		return Mockito.verify(routesBuilderMock);
	}

	protected Representor.Builder representorBuilderSpy;
	protected RoutesBuilderImpl routesBuilderMock;

	private Representor.Builder.FirstStep _identifierSpy;

}