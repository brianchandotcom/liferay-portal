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

package com.liferay.apio.architect.routes;

import static com.liferay.apio.architect.operation.Method.POST;
import static com.liferay.apio.architect.routes.RoutesTestUtil.FORM_BUILDER_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.PAGINATION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.REQUEST_PROVIDE_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.getNestedCollectionPermissionFunction;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.alias.routes.NestedGetPageFunction;
import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class NestedCollectionRoutesTest {

	@Test
	public void testEmptyBuilderBuildsEmptyRoutes() {
		Builder<String, Long> builder = new Builder<>(
			"name", "nested", REQUEST_PROVIDE_FUNCTION,
			__ -> {
			});

		NestedCollectionRoutes<String, Long> nestedCollectionRoutes =
			builder.build();

		Optional<NestedCreateItemFunction<String, Long>> optional1 =
			nestedCollectionRoutes.getNestedCreateItemFunctionOptional();

		assertThat(optional1, is(emptyOptional()));

		Optional<NestedGetPageFunction<String, Long>> optional2 =
			nestedCollectionRoutes.getNestedGetPageFunctionOptional();

		assertThat(optional2, is(emptyOptional()));
	}

	@Test
	public void testFiveParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", "nested", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		NestedCollectionRoutes<String, Long> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnFourParameterCreatorRoute, String.class,
				Long.class, Boolean.class, Integer.class,
				getNestedCollectionPermissionFunction(), FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnFourParameterGetterRoute, String.class,
				Long.class, Boolean.class, Integer.class
			).build();

		assertThat(
			neededProviders,
			contains(
				Boolean.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()));

		_testNestedCollectionRoutes(nestedCollectionRoutes);
	}

	@Test
	public void testFourParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", "nested", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		NestedCollectionRoutes<String, Long> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnThreeParameterCreatorRoute, String.class,
				Long.class, Boolean.class,
				getNestedCollectionPermissionFunction(), FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnThreeParameterGetterRoute, String.class,
				Long.class, Boolean.class
			).build();

		assertThat(
			neededProviders,
			contains(
				Boolean.class.getName(), Long.class.getName(),
				String.class.getName()));

		_testNestedCollectionRoutes(nestedCollectionRoutes);
	}

	@Test
	public void testOneParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", "nested", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		NestedCollectionRoutes<String, Long> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnNoParameterCreatorRoute,
				getNestedCollectionPermissionFunction(), FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnNoParameterGetterRoute
			).build();

		assertThat(neededProviders.size(), is(0));

		_testNestedCollectionRoutes(nestedCollectionRoutes);
	}

	@Test
	public void testThreeParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", "nested", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		NestedCollectionRoutes<String, Long> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnTwoParameterCreatorRoute, String.class,
				Long.class, getNestedCollectionPermissionFunction(),
				FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnTwoParameterGetterRoute, String.class,
				Long.class
			).build();

		assertThat(
			neededProviders,
			contains(Long.class.getName(), String.class.getName()));

		_testNestedCollectionRoutes(nestedCollectionRoutes);
	}

	@Test
	public void testTwoParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", "nested", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		NestedCollectionRoutes<String, Long> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnOneParameterCreatorRoute, String.class,
				getNestedCollectionPermissionFunction(), FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnOneParameterGetterRoute, String.class
			).build();

		assertThat(neededProviders, contains(String.class.getName()));

		_testNestedCollectionRoutes(nestedCollectionRoutes);
	}

	private String _testAndReturnFourParameterCreatorRoute(
		Long identifier, Map<String, Object> body, String string, Long aLong,
		Boolean aBoolean, Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterCreatorRoute(
			identifier, body, string, aLong, aBoolean);
	}

	private PageItems<String> _testAndReturnFourParameterGetterRoute(
		Pagination pagination, Long identifier, String string, Long aLong,
		Boolean aBoolean, Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterGetterRoute(
			pagination, identifier, string, aLong, aBoolean);
	}

	private String _testAndReturnNoParameterCreatorRoute(
		Long identifier, Map<String, Object> body) {

		assertThat(identifier, is(42L));

		Optional<String> optional = _body.getValueOptional("key");

		assertThat(body.get("key"), is(optional.get()));

		return "Apio";
	}

	private PageItems<String> _testAndReturnNoParameterGetterRoute(
		Pagination pagination, Long identifier) {

		assertThat(identifier, is(42L));
		assertThat(pagination, is(PAGINATION));

		return new PageItems<>(Collections.singletonList("Apio"), 1);
	}

	private String _testAndReturnOneParameterCreatorRoute(
		Long identifier, Map<String, Object> body, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterCreatorRoute(identifier, body);
	}

	private PageItems<String> _testAndReturnOneParameterGetterRoute(
		Pagination pagination, Long identifier, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterGetterRoute(pagination, identifier);
	}

	private String _testAndReturnThreeParameterCreatorRoute(
		Long identifier, Map<String, Object> body, String string, Long aLong,
		Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterCreatorRoute(
			identifier, body, string, aLong);
	}

	private PageItems<String> _testAndReturnThreeParameterGetterRoute(
		Pagination pagination, Long identifier, String string, Long aLong,
		Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterGetterRoute(
			pagination, identifier, string, aLong);
	}

	private String _testAndReturnTwoParameterCreatorRoute(
		Long identifier, Map<String, Object> body, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterCreatorRoute(identifier, body, string);
	}

	private PageItems<String> _testAndReturnTwoParameterGetterRoute(
		Pagination pagination, Long identifier, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterGetterRoute(
			pagination, identifier, string);
	}

	private void _testNestedCollectionRoutes(
		NestedCollectionRoutes<String, Long> nestedCollectionRoutes) {

		Optional<NestedCollectionRoutes<String, Long>> optional = Optional.of(
			nestedCollectionRoutes);

		Map map = optional.flatMap(
			NestedCollectionRoutes::getFormOptional
		).map(
			form -> {
				assertThat(form.id, is("c/name/nested"));

				return (Map)form.get(_body);
			}
		).get();

		Optional<String> valueOptional = _body.getValueOptional("key");

		assertThat(map.get("key"), is(valueOptional.get()));

		SingleModel<String> singleModel = optional.flatMap(
			NestedCollectionRoutes::getNestedCreateItemFunctionOptional
		).get(
		).apply(
			null
		).apply(
			42L
		).apply(
			_body
		).getUnchecked();

		assertThat(singleModel.getResourceName(), is("nested"));
		assertThat(singleModel.getModel(), is("Apio"));

		Path path = new Path("name", "42");

		Page<String> page = optional.flatMap(
			NestedCollectionRoutes::getNestedGetPageFunctionOptional
		).get(
		).apply(
			null
		).apply(
			path
		).apply(
			42L
		).getUnchecked();

		assertThat(page.getItems(), hasSize(1));
		assertThat(page.getItems(), hasItem("Apio"));
		assertThat(page.getPathOptional(), optionalWithValue(equalTo(path)));
		assertThat(page.getTotalCount(), is(1));

		List<Operation> operations = page.getOperations();

		assertThat(operations, hasSize(1));

		Operation secondOperation = operations.get(0);

		assertThat(secondOperation.getFormOptional(), is(optionalWithValue()));
		assertThat(secondOperation.method, is(POST));
		assertThat(secondOperation.name, is("name/nested/create"));
	}

	private final Body _body = __ -> Optional.of("Apio");

}