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

import static com.liferay.apio.architect.routes.RoutesTestUtil.FORM_BUILDER_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.PAGINATION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.PROVIDE_FUNCTION;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static java.util.Collections.singletonMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.alias.routes.NestedGetPageFunction;
import com.liferay.apio.architect.error.ApioDeveloperError.MustUseSameIdentifier;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class NestedCollectionRoutesTest {

	@Test
	public void testEmptyBuilderBuildsEmptyRoutes() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes = builder.build();

		Optional<NestedCreateItemFunction<String>>
			nestedCreateItemFunctionOptional =
				nestedCollectionRoutes.getNestedCreateItemFunctionOptional();

		assertThat(nestedCreateItemFunctionOptional, is(emptyOptional()));

		Optional<NestedGetPageFunction<String>> nestedGetPageFunctionOptional =
			nestedCollectionRoutes.getNestedGetPageFunctionOptional();

		assertThat(nestedGetPageFunctionOptional, is(emptyOptional()));
	}

	@Test
	public void testFiveParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnFourParameterCreatorRoute, String.class,
				Long.class, Boolean.class, Integer.class, FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnFourParameterGetterRoute, String.class,
				Long.class, Boolean.class, Integer.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, 42L);
	}

	@Test(expected = MustUseSameIdentifier.class)
	public void testFiveParameterBuilderMethodsFailIfDifferentIdentifier() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnFourParameterCreatorRoute, String.class,
				Long.class, Boolean.class, Integer.class, FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnFourParameterGetterRoute, String.class,
				Long.class, Boolean.class, Integer.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, "Wrong");
	}

	@Test
	public void testFourParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnThreeParameterCreatorRoute, String.class,
				Long.class, Boolean.class, FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnThreeParameterGetterRoute, String.class,
				Long.class, Boolean.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, 42L);
	}

	@Test(expected = MustUseSameIdentifier.class)
	public void testFourParameterBuilderMethodsFailIfDifferentIdentifier() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnThreeParameterCreatorRoute, String.class,
				Long.class, Boolean.class, FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnThreeParameterGetterRoute, String.class,
				Long.class, Boolean.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, "Wrong");
	}

	@Test
	public void testOneParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnNoParameterCreatorRoute,
				FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnNoParameterGetterRoute
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, 42L);
	}

	@Test(expected = MustUseSameIdentifier.class)
	public void testOneParameterBuilderMethodsFailIfDifferentIdentifier() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnNoParameterCreatorRoute,
				FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnNoParameterGetterRoute
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, "Wrong");
	}

	@Test
	public void testThreeParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnTwoParameterCreatorRoute, String.class,
				Long.class, FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnTwoParameterGetterRoute, String.class,
				Long.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, 42L);
	}

	@Test(expected = MustUseSameIdentifier.class)
	public void testThreeParameterBuilderMethodsFailIfDifferentIdentifier() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnTwoParameterCreatorRoute, String.class,
				Long.class, FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnTwoParameterGetterRoute, String.class,
				Long.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, "Wrong");
	}

	@Test
	public void testTwoParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnOneParameterCreatorRoute, String.class,
				FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnOneParameterGetterRoute, String.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, 42L);
	}

	@Test(expected = MustUseSameIdentifier.class)
	public void testTwoParameterBuilderMethodsFailIfDifferentIdentifier() {
		Builder<String, Long> builder = new Builder<>(
			String.class, "name", "nested", Long.class, PROVIDE_FUNCTION);

		NestedCollectionRoutes<String> nestedCollectionRoutes =
			builder.addCreator(
				this::_testAndReturnOneParameterCreatorRoute, String.class,
				FORM_BUILDER_FUNCTION
			).addGetter(
				this::_testAndReturnOneParameterGetterRoute, String.class
			).build();

		_testNestedCollectionRoutes(nestedCollectionRoutes, "Wrong");
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
		assertThat(body, is(_body));

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
		NestedCollectionRoutes<String> nestedCollectionRoutes,
		Object identifier) {

		Optional<Form> optional = nestedCollectionRoutes.getForm();

		Form form = optional.get();

		assertThat(form.id, is("c/name/nested"));

		Map body = (Map)form.get(_body);

		assertThat(body, is(_body));

		Optional<NestedCreateItemFunction<String>>
			nestedCreateItemFunctionOptional =
				nestedCollectionRoutes.getNestedCreateItemFunctionOptional();

		Function<HttpServletRequest, Function<Object,
			Function<Map<String, Object>, SingleModel<String>>>>
				nestedCreateItemFunction =
					nestedCreateItemFunctionOptional.get();

		SingleModel<String> singleModel = nestedCreateItemFunction.apply(
			null
		).apply(
			identifier
		).apply(
			_body
		);

		assertThat(singleModel.getModelClass(), is(String.class));
		assertThat(singleModel.getModel(), is("Apio"));

		Optional<NestedGetPageFunction<String>> nestedGetPageFunctionOptional =
			nestedCollectionRoutes.getNestedGetPageFunctionOptional();

		NestedGetPageFunction<String> nestedGetPageFunction =
			nestedGetPageFunctionOptional.get();

		Path path = new Path("name", "42");

		Page<String> page = nestedGetPageFunction.apply(
			null
		).apply(
			path
		).apply(
			identifier
		);

		assertThat(page.getItems(), hasSize(1));
		assertThat(page.getItems(), hasItem("Apio"));
		assertThat(page.getPathOptional(), optionalWithValue(equalTo(path)));
		assertThat(page.getTotalCount(), is(1));
	}

	private final Map<String, Object> _body = singletonMap("key", "value");

}