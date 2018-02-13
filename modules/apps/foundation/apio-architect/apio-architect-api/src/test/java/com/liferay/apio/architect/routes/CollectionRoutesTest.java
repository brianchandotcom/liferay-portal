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
import static com.liferay.apio.architect.routes.RoutesTestUtil.COLLECTION_PERMISSION_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.FORM_BUILDER_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.PAGINATION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.REQUEST_PROVIDE_FUNCTION;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static java.util.Collections.singletonMap;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.alias.routes.CreateItemFunction;
import com.liferay.apio.architect.alias.routes.GetPageFunction;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class CollectionRoutesTest {

	@Test
	public void testEmptyBuilderBuildsEmptyRoutes() {
		Builder<String> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION);

		CollectionRoutes<String> collectionRoutes = builder.build();

		Optional<CreateItemFunction<String>> createItemFunctionOptional =
			collectionRoutes.getCreateItemFunctionOptional();

		assertThat(createItemFunctionOptional, is(emptyOptional()));

		Optional<GetPageFunction<String>> getPageFunctionOptional =
			collectionRoutes.getGetPageFunctionOptional();

		assertThat(getPageFunctionOptional, is(emptyOptional()));
	}

	@Test
	public void testFiveParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION);

		CollectionRoutes<String> collectionRoutes = builder.addCreator(
			this::_testAndReturnFourParameterCreatorRoute, String.class,
			Long.class, Boolean.class, Integer.class,
			COLLECTION_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnFourParameterGetterRoute, String.class,
			Long.class, Boolean.class, Integer.class
		).build();

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testFourParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION);

		CollectionRoutes<String> collectionRoutes = builder.addCreator(
			this::_testAndReturnThreeParameterCreatorRoute, String.class,
			Long.class, Boolean.class, COLLECTION_PERMISSION_FUNCTION,
			FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnThreeParameterGetterRoute, String.class,
			Long.class, Boolean.class
		).build();

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testOneParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION);

		CollectionRoutes<String> collectionRoutes = builder.addCreator(
			this::_testAndReturnNoParameterCreatorRoute,
			COLLECTION_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnNoParameterGetterRoute
		).build();

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testThreeParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION);

		CollectionRoutes<String> collectionRoutes = builder.addCreator(
			this::_testAndReturnTwoParameterCreatorRoute, String.class,
			Long.class, COLLECTION_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnTwoParameterGetterRoute, String.class,
			Long.class
		).build();

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testTwoParameterBuilderMethodsCreatesValidRoutes() {
		Builder<String> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION);

		CollectionRoutes<String> collectionRoutes = builder.addCreator(
			this::_testAndReturnOneParameterCreatorRoute, String.class,
			COLLECTION_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnOneParameterGetterRoute, String.class
		).build();

		_testCollectionRoutes(collectionRoutes);
	}

	private String _testAndReturnFourParameterCreatorRoute(
		Map<String, Object> body, String string, Long aLong, Boolean aBoolean,
		Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterCreatorRoute(
			body, string, aLong, aBoolean);
	}

	private PageItems<String> _testAndReturnFourParameterGetterRoute(
		Pagination pagination, String string, Long aLong, Boolean aBoolean,
		Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterGetterRoute(
			pagination, string, aLong, aBoolean);
	}

	private String _testAndReturnNoParameterCreatorRoute(
		Map<String, Object> body) {

		assertThat(body, is(_body));

		return "Apio";
	}

	private PageItems<String> _testAndReturnNoParameterGetterRoute(
		Pagination pagination) {

		assertThat(pagination, is(PAGINATION));

		return new PageItems<>(Collections.singletonList("Apio"), 1);
	}

	private String _testAndReturnOneParameterCreatorRoute(
		Map<String, Object> body, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterCreatorRoute(body);
	}

	private PageItems<String> _testAndReturnOneParameterGetterRoute(
		Pagination pagination, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterGetterRoute(pagination);
	}

	private String _testAndReturnThreeParameterCreatorRoute(
		Map<String, Object> body, String string, Long aLong, Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterCreatorRoute(body, string, aLong);
	}

	private PageItems<String> _testAndReturnThreeParameterGetterRoute(
		Pagination pagination, String string, Long aLong, Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterGetterRoute(pagination, string, aLong);
	}

	private String _testAndReturnTwoParameterCreatorRoute(
		Map<String, Object> body, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterCreatorRoute(body, string);
	}

	private PageItems<String> _testAndReturnTwoParameterGetterRoute(
		Pagination pagination, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterGetterRoute(pagination, string);
	}

	private void _testCollectionRoutes(
		CollectionRoutes<String> collectionRoutes) {

		Optional<CollectionRoutes<String>> optional = Optional.of(
			collectionRoutes);

		Map body = optional.flatMap(
			CollectionRoutes::getFormOptional
		).map(
			form -> {
				assertThat(form.id, is("c/name"));

				return (Map)form.get(_body);
			}
		).get();

		assertThat(body, is(_body));

		SingleModel<String> singleModel = optional.flatMap(
			CollectionRoutes::getCreateItemFunctionOptional
		).get(
		).apply(
			null
		).apply(
			_body
		);

		assertThat(singleModel.getResourceName(), is("name"));
		assertThat(singleModel.getModel(), is("Apio"));

		Page<String> page = optional.flatMap(
			CollectionRoutes::getGetPageFunctionOptional
		).get(
		).apply(
			null
		);

		assertThat(page.getItems(), hasSize(1));
		assertThat(page.getItems(), hasItem("Apio"));
		assertThat(page.getTotalCount(), is(1));

		List<Operation> operations = page.getOperations();

		assertThat(operations, hasSize(1));

		Operation operation = operations.get(0);

		assertThat(operation.getFormOptional(), is(optionalWithValue()));
		assertThat(operation.method, is(POST));
		assertThat(operation.name, is("name/create"));
	}

	private final Map<String, Object> _body = singletonMap("key", "value");

}