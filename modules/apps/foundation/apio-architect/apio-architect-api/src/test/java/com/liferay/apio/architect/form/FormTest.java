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

package com.liferay.apio.architect.form;

import static com.liferay.apio.architect.form.FieldType.BOOLEAN;
import static com.liferay.apio.architect.form.FieldType.DATE;
import static com.liferay.apio.architect.form.FieldType.DOUBLE;
import static com.liferay.apio.architect.form.FieldType.LONG;
import static com.liferay.apio.architect.form.FieldType.STRING;

import static java.util.Collections.emptyList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.form.Form.Builder;
import com.liferay.apio.architect.language.Language;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FormTest {

	@Test
	public void testEmptyCreatesEmptyPathBuilder() {
		Builder<Object> builder = Builder.empty();

		Form<Object> form = builder.title(
			__ -> ""
		).description(
			__ -> ""
		).constructor(
			Object::new
		).build();

		assertThat(form.id, is(emptyString()));
	}

	@Test
	public void testFormCreatesValidForm() {
		Builder<Map<String, Object>> builder = new Builder<>(
			Arrays.asList("1", "2", "3"));

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalBoolean(
			"boolean1", (map, aBoolean) -> map.put("b1", aBoolean)
		).addOptionalDate(
			"date1", (map, date) -> map.put("d1", date)
		).addOptionalDouble(
			"double1", (map, aDouble) -> map.put("do1", aDouble)
		).addOptionalLong(
			"long1", (map, aLong) -> map.put("l1", aLong)
		).addOptionalString(
			"string1", (map, string) -> map.put("s1", string)
		).addRequiredBoolean(
			"boolean2", (map, aBoolean) -> map.put("b2", aBoolean)
		).addRequiredDate(
			"date2", (map, date) -> map.put("d2", date)
		).addRequiredDouble(
			"double2", (map, aDouble) -> map.put("do2", aDouble)
		).addRequiredLong(
			"long2", (map, aLong) -> map.put("l2", aLong)
		).addRequiredString(
			"string2", (map, string) -> map.put("s2", string)
		).build();

		assertThat(form.id, is("1/2/3"));

		Language language = Locale::getDefault;

		String title = form.getTitle(language);
		String description = form.getDescription(language);

		List<FormField> formFields = form.getFormFields();

		assertThat(formFields, hasSize(10));
		assertThat(
			formFields,
			contains(
				new FormField("boolean1", false, BOOLEAN),
				new FormField("date1", false, DATE),
				new FormField("double1", false, DOUBLE),
				new FormField("long1", false, LONG),
				new FormField("string1", false, STRING),
				new FormField("boolean2", true, BOOLEAN),
				new FormField("date2", true, DATE),
				new FormField("double2", true, DOUBLE),
				new FormField("long2", true, LONG),
				new FormField("string2", true, STRING)));

		assertThat(title, is("title"));
		assertThat(description, is("description"));

		Map<String, Object> map = form.get(_body);

		assertThat(map.size(), is(10));
		assertThat(map, hasEntry(equalTo("b1"), equalTo(true)));
		assertThat(map, hasEntry(equalTo("b2"), equalTo(false)));
		assertThat(
			map, hasEntry(equalTo("d1"), equalTo(new Date(1465981200000L))));
		assertThat(
			map, hasEntry(equalTo("d2"), equalTo(new Date(1491244560000L))));
		assertThat(map, hasEntry(equalTo("l1"), equalTo(42L)));
		assertThat(map, hasEntry(equalTo("l2"), equalTo(2017L)));
		assertThat(map, hasEntry(equalTo("do1"), equalTo(3.5D)));
		assertThat(map, hasEntry(equalTo("do2"), equalTo(25.2D)));
		assertThat(map, hasEntry(equalTo("s1"), equalTo("Apio")));
		assertThat(map, hasEntry(equalTo("s2"), equalTo("Hypermedia")));
	}

	@Test
	public void testFormDoesNotAddMissingOptionals() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalBoolean(
			"boolean3", (map, string) -> map.put("b2", string)
		).addOptionalDate(
			"date3", (map, string) -> map.put("d2", string)
		).addRequiredString(
			"string1", (map, string) -> map.put("s1", string)
		).addOptionalString(
			"string3", (map, string) -> map.put("s2", string)
		).build();

		Map<String, Object> map = form.get(_body);

		assertThat(map.size(), is(1));
		assertThat(map, hasEntry(equalTo("s1"), equalTo("Apio")));
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfOptionalBooleanIsNotBoolean() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalBoolean(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfOptionalDateIsNotDate() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalDate(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfOptionalDoubleIsNotDouble() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalDouble(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfOptionalLongIsNotLong() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalLong(
			"string1", (map, string) -> map.put("s1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfOptionalStringIsNotString() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addOptionalString(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfRequiredBooleanIsNotBoolean() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addRequiredBoolean(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfRequiredDateIsNotDate() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addRequiredDate(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfRequiredDoubleIsNotDouble() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addRequiredDouble(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfRequiredIsNotPresent() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addRequiredString(
			"string1", (map, string) -> map.put("s1", string)
		).addRequiredString(
			"string3", (map, string) -> map.put("s2", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfRequiredLongIsNotLong() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addRequiredLong(
			"string1", (map, string) -> map.put("s1", string)
		).build();

		form.get(_body);
	}

	@Test(expected = BadRequestException.class)
	public void testFormFailsIfRequiredStringIsNotString() {
		Builder<Map<String, Object>> builder = new Builder<>(emptyList());

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).addRequiredString(
			"long1", (map, string) -> map.put("l1", string)
		).build();

		form.get(_body);
	}

	private final Map<String, Object> _body = new HashMap<String, Object>() {
		{
			put("boolean1", true);
			put("boolean2", false);
			put("date1", "2016-06-15T09:00Z");
			put("date2", "2017-04-03T18:36Z");
			put("double1", 3.5D);
			put("double2", 25.2D);
			put("long1", 42L);
			put("long2", 2017L);
			put("string1", "Apio");
			put("string2", "Hypermedia");
		}
	};

}