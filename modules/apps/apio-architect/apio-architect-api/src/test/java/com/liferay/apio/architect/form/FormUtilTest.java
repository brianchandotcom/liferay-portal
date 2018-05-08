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

import static co.unruly.matchers.StreamMatchers.contains;

import static com.liferay.apio.architect.form.FieldType.BOOLEAN;
import static com.liferay.apio.architect.form.FormUtil.getOptionalBoolean;
import static com.liferay.apio.architect.form.FormUtil.getOptionalBooleanList;
import static com.liferay.apio.architect.form.FormUtil.getOptionalDate;
import static com.liferay.apio.architect.form.FormUtil.getOptionalDateList;
import static com.liferay.apio.architect.form.FormUtil.getOptionalDouble;
import static com.liferay.apio.architect.form.FormUtil.getOptionalDoubleList;
import static com.liferay.apio.architect.form.FormUtil.getOptionalFile;
import static com.liferay.apio.architect.form.FormUtil.getOptionalFileList;
import static com.liferay.apio.architect.form.FormUtil.getOptionalFormFieldStream;
import static com.liferay.apio.architect.form.FormUtil.getOptionalLong;
import static com.liferay.apio.architect.form.FormUtil.getOptionalLongList;
import static com.liferay.apio.architect.form.FormUtil.getOptionalString;
import static com.liferay.apio.architect.form.FormUtil.getOptionalStringList;
import static com.liferay.apio.architect.form.FormUtil.getRequiredBoolean;
import static com.liferay.apio.architect.form.FormUtil.getRequiredBooleanList;
import static com.liferay.apio.architect.form.FormUtil.getRequiredDate;
import static com.liferay.apio.architect.form.FormUtil.getRequiredDateList;
import static com.liferay.apio.architect.form.FormUtil.getRequiredDouble;
import static com.liferay.apio.architect.form.FormUtil.getRequiredDoubleList;
import static com.liferay.apio.architect.form.FormUtil.getRequiredFile;
import static com.liferay.apio.architect.form.FormUtil.getRequiredFileList;
import static com.liferay.apio.architect.form.FormUtil.getRequiredFormFieldStream;
import static com.liferay.apio.architect.form.FormUtil.getRequiredLong;
import static com.liferay.apio.architect.form.FormUtil.getRequiredLongList;
import static com.liferay.apio.architect.form.FormUtil.getRequiredString;
import static com.liferay.apio.architect.form.FormUtil.getRequiredStringList;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.alias.form.FieldFormBiConsumer;
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.functional.Try;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FormUtilTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			FormUtil.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testGetOptionalBooleanExtractsBoolean() {
		List<Boolean> list = new ArrayList<>();

		FieldFormBiConsumer<List<Boolean>, Boolean> fieldFormBiConsumer =
			getOptionalBoolean(_valueBody("true"), list);

		fieldFormBiConsumer.accept("boolean", booleanList -> booleanList::add);

		_validateBooleanList(list);
	}

	@Test
	public void testGetOptionalBooleanListExtractsBooleans() {
		List<Boolean> list = new ArrayList<>();

		FieldFormBiConsumer<List<Boolean>, List<Boolean>> fieldFormBiConsumer =
			getOptionalBooleanList(_valueListBody("true"), list);

		fieldFormBiConsumer.accept("list", booleanList -> booleanList::addAll);

		_validateBooleanList(list);
	}

	@Test
	public void testGetOptionalDateExtractsDate() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, Date> fieldFormBiConsumer =
			getOptionalDate(_valueBody("2017-04-03T18:36Z"), list);

		fieldFormBiConsumer.accept("date", dateList -> dateList::add);

		_validateDateList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetOptionalDateFailsIfNotAnISO8601Date() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, Date> fieldFormBiConsumer =
			getOptionalDate(_valueBody("2017-04-03"), list);

		fieldFormBiConsumer.accept("date", dateList -> dateList::add);
	}

	@Test
	public void testGetOptionalDateListExtractsDates() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, List<Date>> fieldFormBiConsumer =
			getOptionalDateList(_valueListBody("2017-04-03T18:36Z"), list);

		fieldFormBiConsumer.accept("list", dateList -> dateList::addAll);

		_validateDateList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetOptionalDateListFailsIfHasANoISO8601Date() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, List<Date>> fieldFormBiConsumer =
			getOptionalDateList(_valueListBody("2017-04-03"), list);

		fieldFormBiConsumer.accept("list", dateList -> dateList::addAll);
	}

	@Test
	public void testGetOptionalDoesNotFailIfNotPresent() {
		_validateOptionalValueMethod(
			(Body body, List<Boolean> list) -> getOptionalBoolean(body, list));
		_validateOptionalValueMethod(
			(Body body, List<Boolean> list) -> getOptionalBoolean(body, list));
		_validateOptionalValueMethod(
			(Body body, List<Date> list) -> getOptionalDate(body, list));
		_validateOptionalValueMethod(
			(Body body, List<Double> list) -> getOptionalDouble(body, list));
		_validateOptionalValueMethod(
			(Body body, List<BinaryFile> list) -> getOptionalFile(body, list));
		_validateOptionalValueMethod(
			(Body body, List<Long> list) -> getOptionalLong(body, list));
		_validateOptionalValueMethod(
			(Body body, List<String> list) -> getOptionalString(body, list));
		_validateOptionalListMethod(
			(Body body, List<Boolean> list) -> getOptionalBooleanList(
				body, list));
		_validateOptionalListMethod(
			(Body body, List<Boolean> list) -> getOptionalBooleanList(
				body, list));
		_validateOptionalListMethod(
			(Body body, List<Date> list) -> getOptionalDateList(body, list));
		_validateOptionalListMethod(
			(Body body, List<Double> list) -> getOptionalDoubleList(
				body, list));
		_validateOptionalListMethod(
			(Body body, List<BinaryFile> list) -> getOptionalFileList(
				body, list));
		_validateOptionalListMethod(
			(Body body, List<Long> list) -> getOptionalLongList(body, list));
		_validateOptionalListMethod(
			(Body body, List<String> list) -> getOptionalStringList(
				body, list));
	}

	@Test
	public void testGetOptionalDoubleExtractsDouble() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, Double> fieldFormBiConsumer =
			getOptionalDouble(_valueBody("42.3"), list);

		fieldFormBiConsumer.accept("double", doubleList -> doubleList::add);

		_validateDoubleList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetOptionalDoubleFailsIfHasANoDouble() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, List<Double>> fieldFormBiConsumer =
			getOptionalDoubleList(_valueListBody("Apio"), list);

		fieldFormBiConsumer.accept("list", doubleList -> doubleList::addAll);
	}

	@Test(expected = BadRequestException.class)
	public void testGetOptionalDoubleFailsIfNotADouble() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, Double> fieldFormBiConsumer =
			getOptionalDouble(_valueBody("Apio"), list);

		fieldFormBiConsumer.accept("double", doubleList -> doubleList::add);
	}

	@Test
	public void testGetOptionalDoubleListExtractsDoubles() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, List<Double>> fieldFormBiConsumer =
			getOptionalDoubleList(_valueListBody("42.3"), list);

		fieldFormBiConsumer.accept("list", doubleList -> doubleList::addAll);

		_validateDoubleList(list);
	}

	@Test
	public void testGetOptionalFileExtractsFile() {
		List<BinaryFile> list = new ArrayList<>();

		FieldFormBiConsumer<List<BinaryFile>, BinaryFile> fieldFormBiConsumer =
			getOptionalFile(_fileBody(), list);

		fieldFormBiConsumer.accept("file", fileList -> fileList::add);

		_validateFileList(list);
	}

	@Test
	public void testGetOptionalFileListExtractsFiles() {
		List<BinaryFile> list = new ArrayList<>();

		FieldFormBiConsumer<List<BinaryFile>, List<BinaryFile>>
			fieldFormBiConsumer = getOptionalFileList(_fileBody(), list);

		fieldFormBiConsumer.accept("file", fileList -> fileList::addAll);

		_validateFileList(list);
	}

	@Test
	public void testGetOptionalFormFieldStream() {
		Map<String, String> map = new LinkedHashMap<String, String>() {
			{
				put("first", "first");
				put("second", "second");
			}
		};

		Stream<FormField> stream = getOptionalFormFieldStream(map, BOOLEAN);

		FormField firstFormField = new FormField("first", false, BOOLEAN);
		FormField secondFormField = new FormField("second", false, BOOLEAN);

		assertThat(stream, contains(firstFormField, secondFormField));
	}

	@Test
	public void testGetOptionalLongExtractsLong() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, Long> fieldFormBiConsumer =
			getOptionalLong(_valueBody("42"), list);

		fieldFormBiConsumer.accept("long", longList -> longList::add);

		_validateLongList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetOptionalLongFailsIfNotALong() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, Long> fieldFormBiConsumer =
			getOptionalLong(_valueBody("Apio"), list);

		fieldFormBiConsumer.accept("long", longList -> longList::add);
	}

	@Test
	public void testGetOptionalLongListExtractsLongs() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, List<Long>> fieldFormBiConsumer =
			getOptionalLongList(_valueListBody("42"), list);

		fieldFormBiConsumer.accept("list", longList -> longList::addAll);

		_validateLongList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetOptionalLongListFailsIfHasANoLong() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, List<Long>> fieldFormBiConsumer =
			getOptionalLongList(_valueListBody("Apio"), list);

		fieldFormBiConsumer.accept("long", longList -> longList::addAll);
	}

	@Test
	public void testGetOptionalStringExtractsString() {
		List<String> list = new ArrayList<>();

		FieldFormBiConsumer<List<String>, String> fieldFormBiConsumer =
			getOptionalString(_valueBody("Apio"), list);

		fieldFormBiConsumer.accept("string", stringList -> stringList::add);

		_validateStringList(list);
	}

	@Test
	public void testGetOptionalStringListExtractsStrings() {
		List<String> list = new ArrayList<>();

		FieldFormBiConsumer<List<String>, List<String>> fieldFormBiConsumer =
			getOptionalStringList(_valueListBody("Apio"), list);

		fieldFormBiConsumer.accept("list", stringList -> stringList::addAll);

		_validateStringList(list);
	}

	@Test
	public void testGetRequiredBooleanExtractsBoolean() {
		List<Boolean> list = new ArrayList<>();

		FieldFormBiConsumer<List<Boolean>, Boolean> fieldFormBiConsumer =
			getRequiredBoolean(_valueBody("true"), list);

		fieldFormBiConsumer.accept("boolean", booleanList -> booleanList::add);

		_validateBooleanList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredBooleanFailsIfNotPresent() {
		List<Boolean> list = new ArrayList<>();

		FieldFormBiConsumer<List<Boolean>, Boolean> fieldFormBiConsumer =
			getRequiredBoolean(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("boolean", booleanList -> booleanList::add);
	}

	@Test
	public void testGetRequiredBooleanListExtractsBooleans() {
		List<Boolean> list = new ArrayList<>();

		FieldFormBiConsumer<List<Boolean>, List<Boolean>> fieldFormBiConsumer =
			getRequiredBooleanList(_valueListBody("true"), list);

		fieldFormBiConsumer.accept("list", booleanList -> booleanList::addAll);

		_validateBooleanList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredBooleanListFailsIfNotPresent() {
		List<Boolean> list = new ArrayList<>();

		FieldFormBiConsumer<List<Boolean>, List<Boolean>> fieldFormBiConsumer =
			getRequiredBooleanList(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", booleanList -> booleanList::addAll);
	}

	@Test
	public void testGetRequiredDateExtractsDate() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, Date> fieldFormBiConsumer =
			getRequiredDate(_valueBody("2017-04-03T18:36Z"), list);

		fieldFormBiConsumer.accept("date", dateList -> dateList::add);

		_validateDateList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDateFailsIfNotAnISO8601Date() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, Date> fieldFormBiConsumer =
			getRequiredDate(_valueBody("2017-04-03"), list);

		fieldFormBiConsumer.accept("date", dateList -> dateList::add);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDateFailsIfNotPresent() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, Date> fieldFormBiConsumer =
			getRequiredDate(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("date", dateList -> dateList::add);
	}

	@Test
	public void testGetRequiredDateListExtractsDates() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, List<Date>> fieldFormBiConsumer =
			getRequiredDateList(_valueListBody("2017-04-03T18:36Z"), list);

		fieldFormBiConsumer.accept("list", dateList -> dateList::addAll);

		_validateDateList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDateListFailsIfHasANoISO8601Date() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, List<Date>> fieldFormBiConsumer =
			getRequiredDateList(_valueListBody("2017-04-03"), list);

		fieldFormBiConsumer.accept("list", dateList -> dateList::addAll);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDateListFailsIfNotPresent() {
		List<Date> list = new ArrayList<>();

		FieldFormBiConsumer<List<Date>, List<Date>> fieldFormBiConsumer =
			getRequiredDateList(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", dateList -> dateList::addAll);
	}

	@Test
	public void testGetRequiredDoubleExtractsDouble() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, Double> fieldFormBiConsumer =
			getRequiredDouble(_valueBody("42.3"), list);

		fieldFormBiConsumer.accept("double", doubleList -> doubleList::add);

		_validateDoubleList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDoubleFailsIfNotADouble() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, Double> fieldFormBiConsumer =
			getRequiredDouble(_valueBody("Apio"), list);

		fieldFormBiConsumer.accept("double", doubleList -> doubleList::add);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDoubleFailsIfNotPresent() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, Double> fieldFormBiConsumer =
			getRequiredDouble(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("double", doubleList -> doubleList::add);
	}

	@Test
	public void testGetRequiredDoubleListExtractsDoubles() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, List<Double>> fieldFormBiConsumer =
			getRequiredDoubleList(_valueListBody("42.3"), list);

		fieldFormBiConsumer.accept("list", doubleList -> doubleList::addAll);

		_validateDoubleList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDoubleListFailsIfHasANoDouble() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, List<Double>> fieldFormBiConsumer =
			getRequiredDoubleList(_valueListBody("Apio"), list);

		fieldFormBiConsumer.accept("double", doubleList -> doubleList::addAll);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredDoubleListFailsIfNotPresent() {
		List<Double> list = new ArrayList<>();

		FieldFormBiConsumer<List<Double>, List<Double>> fieldFormBiConsumer =
			getRequiredDoubleList(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", doubleList -> doubleList::addAll);
	}

	@Test
	public void testGetRequiredFileExtractsFile() {
		List<BinaryFile> list = new ArrayList<>();

		FieldFormBiConsumer<List<BinaryFile>, BinaryFile> fieldFormBiConsumer =
			getRequiredFile(_fileBody(), list);

		fieldFormBiConsumer.accept("file", fileList -> fileList::add);

		_validateFileList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredFileFailsIfNotPresent() {
		List<BinaryFile> list = new ArrayList<>();

		FieldFormBiConsumer<List<BinaryFile>, BinaryFile> fieldFormBiConsumer =
			getRequiredFile(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("file", fileList -> fileList::add);
	}

	@Test
	public void testGetRequiredFileListExtractsFiles() {
		List<BinaryFile> list = new ArrayList<>();

		FieldFormBiConsumer<List<BinaryFile>, List<BinaryFile>>
			fieldFormBiConsumer = getRequiredFileList(_fileBody(), list);

		fieldFormBiConsumer.accept("list", fileList -> fileList::addAll);

		_validateFileList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredFileListFailsIfNotPresent() {
		List<BinaryFile> list = new ArrayList<>();

		FieldFormBiConsumer<List<BinaryFile>, List<BinaryFile>>
			fieldFormBiConsumer = getRequiredFileList(
				__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", fileList -> fileList::addAll);
	}

	@Test
	public void testGetRequiredFormFieldStream() {
		Map<String, String> map = new LinkedHashMap<String, String>() {
			{
				put("first", "first");
				put("second", "second");
			}
		};

		Stream<FormField> stream = getRequiredFormFieldStream(map, BOOLEAN);

		FormField firstFormField = new FormField("first", true, BOOLEAN);
		FormField secondFormField = new FormField("second", true, BOOLEAN);

		assertThat(stream, contains(firstFormField, secondFormField));
	}

	@Test
	public void testGetRequiredLongExtractsLong() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, Long> fieldFormBiConsumer =
			getRequiredLong(_valueBody("42"), list);

		fieldFormBiConsumer.accept("long", longList -> longList::add);

		_validateLongList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredLongFailsIfNotALong() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, Long> fieldFormBiConsumer =
			getRequiredLong(_valueBody("Apio"), list);

		fieldFormBiConsumer.accept("long", longList -> longList::add);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredLongFailsIfNotPresent() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, Long> fieldFormBiConsumer =
			getRequiredLong(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("long", longList -> longList::add);
	}

	@Test
	public void testGetRequiredLongListExtractsLongs() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, List<Long>> fieldFormBiConsumer =
			getRequiredLongList(_valueListBody("42"), list);

		fieldFormBiConsumer.accept("list", longList -> longList::addAll);

		_validateLongList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredLongListFailsIfHasANoLong() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, List<Long>> fieldFormBiConsumer =
			getRequiredLongList(_valueListBody("Apio"), list);

		fieldFormBiConsumer.accept("list", longList -> longList::addAll);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredLongListFailsIfNotPresent() {
		List<Long> list = new ArrayList<>();

		FieldFormBiConsumer<List<Long>, List<Long>> fieldFormBiConsumer =
			getRequiredLongList(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", longList -> longList::addAll);
	}

	@Test
	public void testGetRequiredStringExtractsString() {
		List<String> list = new ArrayList<>();

		FieldFormBiConsumer<List<String>, String> fieldFormBiConsumer =
			getRequiredString(_valueBody("Apio"), list);

		fieldFormBiConsumer.accept("string", stringList -> stringList::add);

		_validateStringList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredStringFailsIfNotPresent() {
		List<String> list = new ArrayList<>();

		FieldFormBiConsumer<List<String>, String> fieldFormBiConsumer =
			getRequiredString(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("string", stringList -> stringList::add);
	}

	@Test
	public void testGetRequiredStringListExtractsStrings() {
		List<String> list = new ArrayList<>();

		FieldFormBiConsumer<List<String>, List<String>> fieldFormBiConsumer =
			getRequiredStringList(_valueListBody("Apio"), list);

		fieldFormBiConsumer.accept("list", stringList -> stringList::addAll);

		_validateStringList(list);
	}

	@Test(expected = BadRequestException.class)
	public void testGetRequiredStringListFailsIfHasANoPresent() {
		List<String> list = new ArrayList<>();

		FieldFormBiConsumer<List<String>, List<String>> fieldFormBiConsumer =
			getRequiredStringList(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", stringList -> stringList::addAll);
	}

	private static Body _fileBody() {
		BinaryFile binaryFile = new BinaryFile(
			new ByteArrayInputStream("content".getBytes(UTF_8)), 0L, "type");

		return Body.create(
			__ -> Optional.empty(), __ -> Optional.empty(),
			__ -> Optional.of(Collections.singletonList(binaryFile)),
			__ -> Optional.of(binaryFile));
	}

	private static Body _valueBody(String string) {
		return __ -> Optional.of(string);
	}

	private static Body _valueListBody(String... array) {
		return Body.create(
			__ -> Optional.empty(), __ -> Optional.of(Arrays.asList(array)));
	}

	private String _readBinaryFile(BinaryFile binaryFile) {
		return Try.fromFallibleWithResources(
			() -> new BufferedReader(new InputStreamReader(
				binaryFile.getInputStream())),
			BufferedReader::readLine).getUnchecked();
	}

	private void _validateBooleanList(List<Boolean> list) {
		assertThat(list, hasSize(1));

		Boolean aBoolean = list.get(0);

		assertThat(aBoolean, is(true));
	}

	private void _validateDateList(List<Date> list) {
		assertThat(list, hasSize(1));

		Date date = list.get(0);

		assertThat(date, is(new Date(1491244560000L)));
	}

	private void _validateDoubleList(List<Double> list) {
		assertThat(list, hasSize(1));

		Double aDouble = list.get(0);

		assertThat(aDouble, is(42.3D));
	}

	private void _validateFileList(List<BinaryFile> list) {
		assertThat(list, hasSize(1));

		BinaryFile binaryFile = list.get(0);

		assertThat(_readBinaryFile(binaryFile), is("content"));
		assertThat(binaryFile.getMimeType(), is("type"));
	}

	private void _validateLongList(List<Long> list) {
		assertThat(list, hasSize(1));

		Long aLong = list.get(0);

		assertThat(aLong, is(42L));
	}

	private <T> void _validateOptionalListMethod(
		BiFunction<Body, List<T>,
			FieldFormBiConsumer<List<T>, List<T>>> biFunction) {

		List<T> list = new ArrayList<>();

		FieldFormBiConsumer<List<T>, List<T>> fieldFormBiConsumer =
			biFunction.apply(__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", tList -> tList::addAll);

		assertThat(list, is(empty()));
	}

	private <T> void _validateOptionalValueMethod(
		BiFunction<Body, List<T>, FieldFormBiConsumer<List<T>, T>> biFunction) {

		List<T> list = new ArrayList<>();

		FieldFormBiConsumer<List<T>, T> fieldFormBiConsumer = biFunction.apply(
			__ -> Optional.empty(), list);

		fieldFormBiConsumer.accept("list", tList -> tList::add);

		assertThat(list, is(empty()));
	}

	private void _validateStringList(List<String> list) {
		assertThat(list, hasSize(1));

		String string = list.get(0);

		assertThat(string, is("Apio"));
	}

}