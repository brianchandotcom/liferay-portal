/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.server.eleflow.util;

import io.vertx.core.json.JsonObject;

import java.math.BigDecimal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowUtil {

	public static <D extends Enum<D>, E extends Enum<E>> D fromEleflow(
		E value, Class<D> clazz) {

		String name = value.toString();

		name = name.replace('-', '_');
		name = name.toUpperCase();

		return Enum.valueOf(clazz, name);
	}

	public static String getDateString(long time) {
		LocalDate localDate = LocalDate.ofEpochDay(
			TimeUnit.MILLISECONDS.toDays(time));

		return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	public static String getHost(JsonObject configJsonObject) {
		return configJsonObject.getString(
			"ELEFLOW_HOST", _DEFAULT_ELEFLOW_HOST);
	}

	public static int getInteger(BigDecimal bigDecimal) {
		return bigDecimal.intValue();
	}

	public static long getLong(String s) {
		if ((s == null) || s.isEmpty()) {
			return 0;
		}

		return Long.parseLong(s);
	}

	public static OffsetDateTime getOffsetDateTime(long time) {
		Instant instant = Instant.ofEpochMilli(time);

		return OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"));
	}

	public static String getPath(JsonObject configJsonObject) {
		return configJsonObject.getString(
			"ELEFLOW_PATH", _DEFAULT_ELEFLOW_PATH);
	}

	public static String getString(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return null;
		}

		return bigDecimal.toPlainString();
	}

	public static long getTime(LocalDate localDate) {
		return TimeUnit.DAYS.toMillis(localDate.toEpochDay());
	}

	public static long getTime(String dateString) {
		LocalDate localDate = LocalDate.parse(
			dateString, DateTimeFormatter.ISO_LOCAL_DATE);

		return getTime(localDate);
	}

	public static <T, R> List<R> map(List<T> values, Function<T, R> function) {
		if (values == null) {
			return null;
		}

		Stream<T> stream = values.stream();

		return stream.map(
			function
		).collect(
			Collectors.toList()
		);
	}

	public static <D extends Enum<D>, E extends Enum<E>> E toEleflow(
		D value, Function<String, E> function) {

		String name = value.toString();

		name = name.replace('_', '-');
		name = name.toLowerCase();

		return function.apply(name);
	}

	private static final String _DEFAULT_ELEFLOW_HOST =
		"b2l9j0pz3i.execute-api.us-west-2.amazonaws.com";

	private static final String _DEFAULT_ELEFLOW_PATH = "/Prod";

}