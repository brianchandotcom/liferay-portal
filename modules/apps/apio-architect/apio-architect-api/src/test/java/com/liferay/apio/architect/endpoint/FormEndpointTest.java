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

package com.liferay.apio.architect.endpoint;

import static com.liferay.apio.architect.routes.RoutesTestUtil.COLLECTION_PERMISSION_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.FORM_BUILDER_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.REQUEST_PROVIDE_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.getNestedCollectionPermissionFunction;
import static com.liferay.apio.architect.test.util.result.TryMatchers.aFailTry;
import static com.liferay.apio.architect.test.util.result.TryMatchers.aSuccessTry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FormEndpointTest {

	@Test
	public void testEmptyFormsMethodsReturnsFailure() {
		FormEndpoint formEndpoint = new FormEndpoint(
			__ -> Optional.of(_emptyCollectionRoutes()),
			__ -> Optional.of(_emptyItemRoutes()),
			(name, nestedName) -> Optional.of(_emptyNestedCollectionRoutes()));

		Try<Form> creatorFormTry = formEndpoint.creatorForm("");
		Try<Form> nestedCreatorFormTry = formEndpoint.nestedCreatorForm("", "");
		Try<Form> updaterFormTry = formEndpoint.updaterForm("");

		assertThat(creatorFormTry, is(aFailTry()));
		assertThat(nestedCreatorFormTry, is(aFailTry()));
		assertThat(updaterFormTry, is(aFailTry()));
	}

	@Test
	public void testEmptyRoutesReturnsFailure() {
		FormEndpoint formEndpoint = new FormEndpoint(
			__ -> Optional.empty(), __ -> Optional.empty(),
			(name, nestedName) -> Optional.empty());

		Try<Form> creatorFormTry = formEndpoint.creatorForm("");
		Try<Form> nestedCreatorFormTry = formEndpoint.nestedCreatorForm("", "");
		Try<Form> updaterFormTry = formEndpoint.updaterForm("");

		assertThat(creatorFormTry, is(aFailTry()));
		assertThat(nestedCreatorFormTry, is(aFailTry()));
		assertThat(updaterFormTry, is(aFailTry()));
	}

	@Test
	public void testFunctionsReceiveResourceNames() {
		List<String> names = new ArrayList<>();

		FormEndpoint formEndpoint = new FormEndpoint(
			name -> {
				names.add(name);

				return Optional.empty();
			},
			name -> {
				names.add(name);

				return Optional.empty();
			},
			(name, nestedName) -> {
				names.add(name);
				names.add(nestedName);

				return Optional.empty();
			});

		formEndpoint.creatorForm("a");
		formEndpoint.updaterForm("b");
		formEndpoint.nestedCreatorForm("c", "d");

		assertThat(names, contains("a", "b", "c", "d"));
	}

	@Test
	public void testValidCreatorFormMethodReturnsSuccess() {
		FormEndpoint formEndpoint = new FormEndpoint(
			__ -> Optional.of(_collectionRoutes()),
			__ -> Optional.empty(), (name, nestedName) -> Optional.empty());

		Try<Form> creatorFormTry = formEndpoint.creatorForm("");

		assertThat(creatorFormTry, is(aSuccessTry()));

		Form form = creatorFormTry.getUnchecked();

		assertThat(form.id, is("c/name"));
	}

	@Test
	public void testValidNestedCreatorFormMethodReturnsSuccess() {
		FormEndpoint formEndpoint = new FormEndpoint(
			__ -> Optional.empty(), __ -> Optional.empty(),
			(name, nestedName) -> Optional.of(_nestedCollectionRoutes()));

		Try<Form> nestedCreatorFormTry = formEndpoint.nestedCreatorForm("", "");

		assertThat(nestedCreatorFormTry, is(aSuccessTry()));

		Form form = nestedCreatorFormTry.getUnchecked();

		assertThat(form.id, is("c/name/nestedName"));
	}

	@Test
	public void testValidUpdaterFormMethodReturnsSuccess() {
		FormEndpoint formEndpoint = new FormEndpoint(
			__ -> Optional.empty(), __ -> Optional.of(_itemRoutes()),
			(name, nestedName) -> Optional.empty());

		Try<Form> updaterFormTry = formEndpoint.updaterForm("");

		assertThat(updaterFormTry, is(aSuccessTry()));

		Form form = updaterFormTry.getUnchecked();

		assertThat(form.id, is("u/name"));
	}

	private static <T> CollectionRoutes<T> _collectionRoutes() {
		CollectionRoutes.Builder<T> builder = new CollectionRoutes.Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION,
			__ -> {
			});

		return builder.addCreator(
			__ -> null, COLLECTION_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).build();
	}

	private static <T> CollectionRoutes<T> _emptyCollectionRoutes() {
		return new CollectionRoutes<>(
			new CollectionRoutes.Builder<>(
				"", httpServletRequest -> aClass -> Optional.empty(),
				__ -> {
				}));
	}

	private static <T, S> ItemRoutes<T, S> _emptyItemRoutes() {
		return new ItemRoutes<>(
			new ItemRoutes.Builder<>(
				"", httpServletRequest -> aClass -> Optional.empty(),
				__ -> {
				}));
	}

	private static <T, S> NestedCollectionRoutes<T, S>
		_emptyNestedCollectionRoutes() {

		return new NestedCollectionRoutes<>(
			new NestedCollectionRoutes.Builder<>(
				"", "", httpServletRequest -> aClass -> Optional.empty(),
				__ -> {
				}));
	}

	private static <T, S> ItemRoutes<T, S> _itemRoutes() {
		ItemRoutes.Builder<T, S> builder = new ItemRoutes.Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION,
			__ -> {
			});

		return builder.addUpdater(
			(aLong, body) -> null, (credentials, s) -> true,
			FORM_BUILDER_FUNCTION
		).build();
	}

	private static <T, S> NestedCollectionRoutes<T, S>
		_nestedCollectionRoutes() {

		NestedCollectionRoutes.Builder<T, S> builder =
			new NestedCollectionRoutes.Builder<>(
				"name", "nestedName", REQUEST_PROVIDE_FUNCTION,
				__ -> {
				});

		return builder.addCreator(
			(s, body) -> null, getNestedCollectionPermissionFunction(),
			FORM_BUILDER_FUNCTION
		).build();
	}

}