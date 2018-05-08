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

import static com.liferay.apio.architect.operation.Method.DELETE;
import static com.liferay.apio.architect.operation.Method.PUT;
import static com.liferay.apio.architect.routes.RoutesTestUtil.FORM_BUILDER_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.ITEM_PERMISSION_FUNCTION;
import static com.liferay.apio.architect.routes.RoutesTestUtil.REQUEST_PROVIDE_FUNCTION;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.alias.routes.DeleteItemConsumer;
import com.liferay.apio.architect.alias.routes.GetItemFunction;
import com.liferay.apio.architect.alias.routes.UpdateItemFunction;
import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.routes.ItemRoutes.Builder;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class ItemRoutesTest {

	@Test
	public void testEmptyBuilderBuildsEmptyRoutes() {
		Builder<String, Long> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION,
			__ -> {
			});

		ItemRoutes<String, Long> itemRoutes = builder.build();

		Optional<DeleteItemConsumer<Long>> deleteItemConsumerOptional =
			itemRoutes.getDeleteConsumerOptional();

		assertThat(deleteItemConsumerOptional, is(emptyOptional()));

		Optional<GetItemFunction<String, Long>> getItemFunctionOptional =
			itemRoutes.getItemFunctionOptional();

		assertThat(getItemFunctionOptional, is(emptyOptional()));

		Optional<UpdateItemFunction<String, Long>> updateItemFunctionOptional =
			itemRoutes.getUpdateItemFunctionOptional();

		assertThat(updateItemFunctionOptional, is(emptyOptional()));
	}

	@Test
	public void testFiveParameterBuilderMethodsCreatesValidRoutes()
		throws Exception {

		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		ItemRoutes<String, Long> itemRoutes = builder.addGetter(
			this::_testAndReturnFourParameterGetterRoute, String.class,
			Long.class, Boolean.class, Integer.class
		).addRemover(
			this::_testFourParameterRemoverRoute, String.class, Long.class,
			Boolean.class, Integer.class, ITEM_PERMISSION_FUNCTION
		).addUpdater(
			this::_testAndReturnFourParameterUpdaterRoute, String.class,
			Long.class, Boolean.class, Integer.class, ITEM_PERMISSION_FUNCTION,
			FORM_BUILDER_FUNCTION
		).build();

		assertThat(
			neededProviders,
			contains(
				Boolean.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()));

		_testItemRoutes(itemRoutes);
	}

	@Test
	public void testFourParameterBuilderMethodsCreatesValidRoutes()
		throws Exception {

		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		ItemRoutes<String, Long> itemRoutes = builder.addGetter(
			this::_testAndReturnThreeParameterGetterRoute, String.class,
			Long.class, Boolean.class
		).addRemover(
			this::_testThreeParameterRemoverRoute, String.class, Long.class,
			Boolean.class, ITEM_PERMISSION_FUNCTION
		).addUpdater(
			this::_testAndReturnThreeParameterUpdaterRoute, String.class,
			Long.class, Boolean.class, ITEM_PERMISSION_FUNCTION,
			FORM_BUILDER_FUNCTION
		).build();

		assertThat(
			neededProviders,
			contains(
				Boolean.class.getName(), Long.class.getName(),
				String.class.getName()));

		_testItemRoutes(itemRoutes);
	}

	@Test
	public void testOneParameterBuilderMethodsCreatesValidRoutes()
		throws Exception {

		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		ItemRoutes<String, Long> itemRoutes = builder.addGetter(
			this::_testAndReturnNoParameterGetterRoute
		).addRemover(
			this::_testAndReturnNoParameterRemoverRoute,
			ITEM_PERMISSION_FUNCTION
		).addUpdater(
			this::_testAndReturnNoParameterUpdaterRoute,
			ITEM_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).build();

		assertThat(neededProviders.size(), is(0));

		_testItemRoutes(itemRoutes);
	}

	@Test
	public void testThreeParameterBuilderMethodsCreatesValidRoutes()
		throws Exception {

		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		ItemRoutes<String, Long> itemRoutes = builder.addGetter(
			this::_testAndReturnTwoParameterGetterRoute, String.class,
			Long.class
		).addRemover(
			this::_testTwoParameterRemoverRoute, String.class, Long.class,
			ITEM_PERMISSION_FUNCTION
		).addUpdater(
			this::_testAndReturnTwoParameterUpdaterRoute, String.class,
			Long.class, ITEM_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).build();

		assertThat(
			neededProviders,
			contains(Long.class.getName(), String.class.getName()));

		_testItemRoutes(itemRoutes);
	}

	@Test
	public void testTwoParameterBuilderMethodsCreatesValidRoutes()
		throws Exception {

		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new Builder<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add);

		ItemRoutes<String, Long> itemRoutes = builder.addGetter(
			this::_testAndReturnOneParameterGetterRoute, String.class
		).addRemover(
			this::_testOneParameterRemoverRoute, String.class,
			ITEM_PERMISSION_FUNCTION
		).addUpdater(
			this::_testAndReturnOneParameterUpdaterRoute, String.class,
			ITEM_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).build();

		assertThat(neededProviders, contains(String.class.getName()));

		_testItemRoutes(itemRoutes);
	}

	private String _testAndReturnFourParameterGetterRoute(
		Long identifier, String string, Long aLong, Boolean aBoolean,
		Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterGetterRoute(
			identifier, string, aLong, aBoolean);
	}

	private String _testAndReturnFourParameterUpdaterRoute(
		Long identifier, Map<String, Object> body, String string, Long aLong,
		Boolean aBoolean, Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterUpdaterRoute(
			identifier, body, string, aLong, aBoolean);
	}

	private String _testAndReturnNoParameterGetterRoute(Long identifier) {
		assertThat(identifier, is(42L));

		return "Apio";
	}

	private void _testAndReturnNoParameterRemoverRoute(Long identifier) {
		assertThat(identifier, is(42L));
	}

	private String _testAndReturnNoParameterUpdaterRoute(
		Long identifier, Map<String, Object> body) {

		assertThat(identifier, is(42L));

		Optional<String> optional = _body.getValueOptional("key");

		assertThat(body.get("key"), is(optional.get()));

		return "Updated";
	}

	private String _testAndReturnOneParameterGetterRoute(
		Long identifier, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterGetterRoute(identifier);
	}

	private String _testAndReturnOneParameterUpdaterRoute(
		Long identifier, Map<String, Object> body, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterUpdaterRoute(identifier, body);
	}

	private String _testAndReturnThreeParameterGetterRoute(
		Long identifier, String string, Long aLong, Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterGetterRoute(identifier, string, aLong);
	}

	private String _testAndReturnThreeParameterUpdaterRoute(
		Long identifier, Map<String, Object> body, String string, Long aLong,
		Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterUpdaterRoute(
			identifier, body, string, aLong);
	}

	private String _testAndReturnTwoParameterGetterRoute(
		Long identifier, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterGetterRoute(identifier, string);
	}

	private String _testAndReturnTwoParameterUpdaterRoute(
		Long identifier, Map<String, Object> body, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterUpdaterRoute(identifier, body, string);
	}

	private void _testFourParameterRemoverRoute(
		Long identifier, String string, Long aLong, Boolean aBoolean,
		Integer integer) {

		assertThat(integer, is(2017));

		_testThreeParameterRemoverRoute(identifier, string, aLong, aBoolean);
	}

	private void _testItemRoutes(ItemRoutes<String, Long> itemRoutes)
		throws Exception {

		Optional<ItemRoutes<String, Long>> optional = Optional.of(itemRoutes);

		Map map = optional.flatMap(
			ItemRoutes::getFormOptional
		).map(
			form -> {
				assertThat(form.id, is("u/name"));

				return (Map)form.get(_body);
			}
		).get();

		Optional<String> valueOptional = _body.getValueOptional("key");

		assertThat(map.get("key"), is(valueOptional.get()));

		SingleModel<String> singleModel = optional.flatMap(
			ItemRoutes::getItemFunctionOptional
		).get(
		).apply(
			null
		).apply(
			42L
		).getUnchecked();

		assertThat(singleModel.getResourceName(), is("name"));
		assertThat(singleModel.getModel(), is("Apio"));

		optional.flatMap(
			ItemRoutes::getDeleteConsumerOptional
		).get(
		).apply(
			null
		).accept(
			42L
		);

		SingleModel<String> updatedSingleModel = optional.flatMap(
			ItemRoutes::getUpdateItemFunctionOptional
		).get(
		).apply(
			null
		).apply(
			42L
		).apply(
			_body
		).getUnchecked();

		assertThat(updatedSingleModel.getResourceName(), is("name"));
		assertThat(updatedSingleModel.getModel(), is("Updated"));

		List<Operation> operations = updatedSingleModel.getOperations();

		assertThat(operations, hasSize(2));

		Operation firstOperation = operations.get(0);

		assertThat(firstOperation.getFormOptional(), is(emptyOptional()));
		assertThat(firstOperation.method, is(DELETE));
		assertThat(firstOperation.name, is("name/delete"));

		Operation secondOperation = operations.get(1);

		assertThat(secondOperation.getFormOptional(), is(optionalWithValue()));
		assertThat(secondOperation.method, is(PUT));
		assertThat(secondOperation.name, is("name/update"));
	}

	private void _testOneParameterRemoverRoute(Long identifier, String string) {
		assertThat(string, is("Apio"));

		_testAndReturnNoParameterRemoverRoute(identifier);
	}

	private void _testThreeParameterRemoverRoute(
		Long identifier, String string, Long aLong, Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		_testTwoParameterRemoverRoute(identifier, string, aLong);
	}

	private void _testTwoParameterRemoverRoute(
		Long identifier, String string, Long aLong) {

		assertThat(aLong, is(42L));

		_testOneParameterRemoverRoute(identifier, string);
	}

	private final Body _body = __ -> Optional.of("Apio");

}