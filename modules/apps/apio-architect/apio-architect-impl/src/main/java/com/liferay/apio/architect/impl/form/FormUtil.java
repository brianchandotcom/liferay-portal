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

package com.liferay.apio.architect.impl.form;

import static com.liferay.apio.architect.impl.date.DateTransformer.asDate;
import static com.liferay.apio.architect.impl.url.URLCreator.getPath;

import com.liferay.apio.architect.alias.IdentifierFunction;
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.form.FieldType;
import com.liferay.apio.architect.form.FormField;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.impl.alias.form.FieldFormBiConsumer;
import com.liferay.apio.architect.impl.date.DateTransformer;
import com.liferay.apio.architect.impl.url.URLCreator;
import com.liferay.apio.architect.uri.Path;

import java.text.NumberFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

/**
 * Provides utility functions for forms.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class FormUtil {

	/**
	 * Returns a field form consumer that tries to extract a boolean from the
	 * HTTP request body and store it in the provided {@code T} instance. If the
	 * field isn't a boolean, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Boolean> getOptionalBoolean(
		Body body, T t) {

		return (key, function) -> _getBoolean(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a boolean list from
	 * the HTTP request body and store it in the provided {@code T} instance. If
	 * the field isn't a boolean list, a {@code javax.ws.rs.BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Boolean>>
		getOptionalBooleanList(Body body, T t) {

		return (key, function) -> _getBooleanList(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a date from the HTTP
	 * request body and store it in the provided {@code T} instance. If the
	 * field isn't an ISO-8601 date, a {@code javax.ws.rs.BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Date> getOptionalDate(
		Body body, T t) {

		return (key, function) -> _getDate(body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a date list from the
	 * HTTP request body and store it in the provided {@code T} instance. If the
	 * field isn't an ISO-8601 date list, a {@code BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Date>> getOptionalDateList(
		Body body, T t) {

		return (key, function) -> _getDateList(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a double from the
	 * HTTP request body and store it in the provided {@code T} instance. If the
	 * field isn't a double, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Double> getOptionalDouble(
		Body body, T t) {

		return (key, function) -> _getDouble(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a double list from
	 * the HTTP request body and store it in the provided {@code T} instance. If
	 * the field isn't a double list, a {@code javax.ws.rs.BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Double>>
		getOptionalDoubleList(Body body, T t) {

		return (key, function) -> _getDoubleList(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a file from the HTTP
	 * request body and store it in the provided {@code T} instance. If the
	 * field isn't a file, a {@code javax.ws.rs.BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, BinaryFile> getOptionalFile(
		Body body, T t) {

		return (key, function) -> _getFile(body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a file list from the
	 * HTTP request body and store it in the provided {@code T} instance. If the
	 * field isn't a file list, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<BinaryFile>>
		getOptionalFileList(Body body, T t) {

		return (key, function) -> _getFileList(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a stream that contains the optional {@code FormField} extracted
	 * from a map whose keys are the {@code FormField} names.
	 *
	 * @param  map the map
	 * @param  fieldType the {@code FieldType} of the map's fields
	 * @return the stream
	 */
	public static Stream<FormField> getOptionalFormFieldStream(
		Map<String, ?> map, FieldType fieldType) {

		return _getFormFieldStream(map, false, fieldType);
	}

	/**
	 * Returns a field form consumer that tries to extract a linked model from
	 * the HTTP request body and store it in the provided {@code T} instance. If
	 * the field isn't an URL, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form function, that maps a path with its identifier
	 * @review
	 */
	public static <T> BiConsumer<String, Function<T, Consumer<?>>>
		getOptionalLinkedModel(
			Body body, T t, IdentifierFunction<?> pathToIdentifierFunction) {

		return (key, function) -> _getLinkedModelValueField(
			body, key, false, function.apply(t), pathToIdentifierFunction);
	}

	/**
	 * Returns a field form consumer that tries to extract a list of linked
	 * models from the HTTP request body and store it in the provided {@code T}
	 * instance. If the field isn't an URL, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form function, that maps a path with its identifier
	 * @review
	 */
	public static <T> BiConsumer<String, Function<T, Consumer<List<?>>>>
		getOptionalLinkedModelList(
			Body body, T t, IdentifierFunction identifierFunction) {

		return (key, function) -> _getLinkedModelListValueField(
			body, key, false, function.apply(t), identifierFunction);
	}

	/**
	 * Returns a field form consumer that tries to extract a long from the HTTP
	 * request body and store it in the provided {@code T} instance. If the
	 * field isn't a long, a {@code javax.ws.rs.BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Long> getOptionalLong(
		Body body, T t) {

		return (key, function) -> _getLong(body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a long list from the
	 * HTTP request body and store it in the provided {@code T} instance. If the
	 * field isn't a long list, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Long>> getOptionalLongList(
		Body body, T t) {

		return (key, function) -> _getLongList(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a string from the
	 * HTTP request body and store it in the provided {@code T} instance. If the
	 * field isn't a string, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, String> getOptionalString(
		Body body, T t) {

		return (key, function) -> _getString(
			body, key, false, function.apply(t));
	}

	/**
	 * Returns a field form consumer that tries to extract a string list from
	 * the HTTP request body and store it in the provided {@code T} instance. If
	 * the field isn't a string list, a {@code javax.ws.rs.BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<String>>
		getOptionalStringList(Body body, T t) {

		return (key, function) -> _getListField(
			body, key, false, function.apply(t), Function.identity());
	}

	/**
	 * Returns a field form consumer that extracts a boolean from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a boolean, a {@code BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Boolean> getRequiredBoolean(
		Body body, T t) {

		return (key, function) -> _getBoolean(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a boolean list from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a boolean list, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Boolean>>
		getRequiredBooleanList(Body body, T t) {

		return (key, function) -> _getBooleanList(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a date from the HTTP request
	 * body and stores it in the provided {@code T} instance. If the field isn't
	 * found, or it isn't an ISO-8601 date, a {@code BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Date> getRequiredDate(
		Body body, T t) {

		return (key, function) -> _getDate(body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a date list from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't an ISO-8601 date list, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Date>> getRequiredDateList(
		Body body, T t) {

		return (key, function) -> _getDateList(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a double from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a double, a {@code BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Double> getRequiredDouble(
		Body body, T t) {

		return (key, function) -> _getDouble(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a double list from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a double list, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Double>>
		getRequiredDoubleList(Body body, T t) {

		return (key, function) -> _getDoubleList(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a file from the HTTP request
	 * body and stores it in the provided {@code T} instance. If the field isn't
	 * found, or it isn't a file, a {@code BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, BinaryFile> getRequiredFile(
		Body body, T t) {

		return (key, function) -> _getFile(body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a file list from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a file list, a {@code BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<BinaryFile>>
		getRequiredFileList(Body body, T t) {

		return (key, function) -> _getFileList(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a stream that contains the required {@code FormField} extracted
	 * from a map whose keys are the {@code FormField} names.
	 *
	 * @param  map the map
	 * @param  fieldType the {@code FieldType} of the map's fields
	 * @return the stream
	 */
	public static Stream<FormField> getRequiredFormFieldStream(
		Map<String, ?> map, FieldType fieldType) {

		return _getFormFieldStream(map, true, fieldType);
	}

	/**
	 * Returns a required {@code FormField} consumer that tries to extract a
	 * linked model from the HTTP request body and store it in the provided
	 * {@code T} instance. If the field isn't an URL, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form function, that maps a path with its identifier
	 * @review
	 */
	public static <T> BiConsumer<String, Function<T, Consumer<?>>>
		getRequiredLinkedModel(
			Body body, T t, IdentifierFunction<?> pathToIdentifierFunction) {

		return (key, function) -> _getLinkedModelValueField(
			body, key, true, function.apply(t), pathToIdentifierFunction);
	}

	/**
	 * Returns a required {@code FormField} consumer that tries to extract a
	 * list of linked models from the HTTP request body and store it in the
	 * provided {@code T} instance. If the field isn't an URL, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form function, that maps a path with its identifier
	 * @review
	 */
	public static <T> BiConsumer<String, Function<T, Consumer<List<?>>>>
		getRequiredLinkedModelList(
			Body body, T t, IdentifierFunction identifierFunction) {

		return (key, function) -> _getLinkedModelListValueField(
			body, key, true, function.apply(t), identifierFunction);
	}

	/**
	 * Returns a field form consumer that extracts a long from the HTTP request
	 * body and stores it in the provided {@code T} instance. If the field isn't
	 * found, or it isn't a long, a {@code javax.ws.rs.BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, Long> getRequiredLong(
		Body body, T t) {

		return (key, function) -> _getLong(body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a long list from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a long list, a {@code BadRequestException}
	 * is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<Long>> getRequiredLongList(
		Body body, T t) {

		return (key, function) -> _getLongList(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a string from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a string, a {@code BadRequestException} is
	 * thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, String> getRequiredString(
		Body body, T t) {

		return (key, function) -> _getString(
			body, key, true, function.apply(t));
	}

	/**
	 * Returns a field form consumer that extracts a string list from the HTTP
	 * request body and stores it in the provided {@code T} instance. If the
	 * field isn't found, or it isn't a string list, a {@code
	 * BadRequestException} is thrown.
	 *
	 * @param  body the HTTP request body
	 * @param  t the form values store
	 * @return the field form consumer
	 */
	public static <T> FieldFormBiConsumer<T, List<String>>
		getRequiredStringList(Body body, T t) {

		return (key, function) -> _getListField(
			body, key, true, function.apply(t), Function.identity());
	}

	private static void _getBoolean(
		Body body, String key, boolean required, Consumer<Boolean> consumer) {

		_getValueField(
			body, key, required,
			value -> consumer.accept(Boolean.valueOf(value)));
	}

	private static void _getBooleanList(
		Body body, String key, boolean required,
		Consumer<List<Boolean>> consumer) {

		_getListField(
			body, key, required, consumer,
			stream -> stream.map(Boolean::valueOf));
	}

	private static void _getDate(
		Body body, String key, boolean required, Consumer<Date> consumer) {

		String message = _getWrongDateMessage(key);

		_getString(
			body, key, required,
			string -> {
				Try<Date> dateTry = asDate(string);

				Date date = dateTry.orElseThrow(
					() -> new BadRequestException(message));

				consumer.accept(date);
			});
	}

	private static void _getDateList(
		Body body, String key, boolean required,
		Consumer<List<Date>> consumer) {

		String message = _getWrongDateMessage(key);

		_getListField(
			body, key, required, consumer,
			(Stream<String> stream) -> stream.map(
				Try::success
			).map(
				(Try<String> stringTry) -> stringTry.flatMap(
					DateTransformer::asDate
				).orElseThrow(
					() -> new BadRequestException(message)
				)
			));
	}

	private static void _getDouble(
		Body body, String key, boolean required, Consumer<Double> consumer) {

		_getNumber(body, key, required, Number::doubleValue, consumer);
	}

	private static void _getDoubleList(
		Body body, String key, boolean required,
		Consumer<List<Double>> consumer) {

		_getNumberList(body, key, required, Number::doubleValue, consumer);
	}

	private static void _getFile(
		Body body, String key, boolean required,
		Consumer<BinaryFile> consumer) {

		Optional<BinaryFile> optional = body.getFileOptional(key);

		if (optional.isPresent()) {
			consumer.accept(optional.get());
		}
		else if (required) {
			throw new BadRequestException("Field \"" + key + "\" is required");
		}
	}

	private static void _getFileList(
		Body body, String key, boolean required,
		Consumer<List<BinaryFile>> consumer) {

		Optional<List<BinaryFile>> optional = body.getFileListOptional(key);

		if (optional.isPresent()) {
			consumer.accept(optional.get());
		}
		else if (required) {
			throw new BadRequestException("Field \"" + key + "\" is required");
		}
	}

	private static Stream<FormField> _getFormFieldStream(
		Map<String, ?> map, Boolean required, FieldType fieldType) {

		Set<String> keys = map.keySet();

		Stream<String> stream = keys.stream();

		return stream.map(name -> new FormFieldImpl(name, required, fieldType));
	}

	private static void _getLinkedModelListValueField(
		Body body, String key, boolean required, Consumer<List<?>> consumer,
		IdentifierFunction<?> identifierFunction) {

		Optional<List<String>> optional = body.getValueListOptional(key);

		if (optional.isPresent()) {
			List<String> urls = optional.get();

			Stream<String> urlStream = urls.stream();

			List<?> list = urlStream.map(
				URLCreator::getPath
			).map(
				identifierFunction::apply
			).collect(
				Collectors.toList()
			);

			consumer.accept(list);
		}
		else if (required) {
			throw new BadRequestException("Field \"" + key + "\" is required");
		}
	}

	private static void _getLinkedModelValueField(
		Body body, String key, boolean required, Consumer consumer,
		IdentifierFunction<?> pathToIdentifierFunction) {

		Optional<String> optional = body.getValueOptional(key);

		if (optional.isPresent()) {
			String url = optional.get();

			Path path = getPath(url);

			Object object = pathToIdentifierFunction.apply(path);

			consumer.accept(object);
		}
		else if (required) {
			throw new BadRequestException("Field \"" + key + "\" is required");
		}
	}

	private static <T> void _getListField(
		Body body, String key, boolean required, Consumer<List<T>> consumer,
		Function<Stream<String>, Stream<T>> function) {

		Optional<List<String>> optional = body.getValueListOptional(key);

		optional.map(
			List::stream
		).map(
			function
		).map(
			stream -> stream.collect(Collectors.toList())
		).ifPresent(
			consumer
		);

		if (!optional.isPresent() && required) {
			throw new BadRequestException("Field \"" + key + "\" is required");
		}
	}

	private static void _getLong(
		Body body, String key, boolean required, Consumer<Long> consumer) {

		_getNumber(body, key, required, Number::longValue, consumer);
	}

	private static void _getLongList(
		Body body, String key, boolean required,
		Consumer<List<Long>> consumer) {

		_getNumberList(body, key, required, Number::longValue, consumer);
	}

	private static <T extends Number> void _getNumber(
		Body body, String key, boolean required, Function<Number, T> function,
		Consumer<T> consumer) {

		_getValueField(
			body, key, required,
			value -> Try.success(
				value
			).map(
				NumberFormat.getInstance()::parse
			).map(
				function::apply
			).voidFold(
				__ -> {
					throw new BadRequestException(
						"Field \"" + key + "\" should be a number");
				},
				consumer::accept
			));
	}

	private static <T extends Number> void _getNumberList(
		Body body, String key, boolean required, Function<Number, T> function,
		Consumer<List<T>> consumer) {

		_getListField(
			body, key, required, consumer,
			(Stream<String> stream) -> stream.map(
				Try::success
			).map(
				(Try<String> stringTry) -> stringTry.map(
					NumberFormat.getInstance()::parse
				).map(
					function::apply
				)
			).map(
				tTry -> tTry.orElseThrow(
					() -> new BadRequestException(
						"Field \"" + key + "\" should be a number"))
			));
	}

	private static void _getString(
		Body body, String key, boolean required, Consumer<String> consumer) {

		_getValueField(body, key, required, consumer);
	}

	private static void _getValueField(
		Body body, String key, boolean required, Consumer<String> consumer) {

		Optional<String> optional = body.getValueOptional(key);

		if (optional.isPresent()) {
			consumer.accept(optional.get());
		}
		else if (required) {
			throw new BadRequestException("Field \"" + key + "\" is required");
		}
	}

	private static String _getWrongDateMessage(String key) {
		StringBuilder stringBuilder = new StringBuilder();

		return stringBuilder.append(
			"Field \""
		).append(
			key
		).append(
			"\" should be a string date in ISO-8601 format: "
		).append(
			"yyyy-MM-dd'T'HH:mm'Z'"
		).toString();
	}

	private FormUtil() {
		throw new UnsupportedOperationException();
	}

}