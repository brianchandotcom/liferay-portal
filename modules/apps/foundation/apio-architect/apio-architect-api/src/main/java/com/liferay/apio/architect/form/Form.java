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
import static com.liferay.apio.architect.form.FormUtil.getOptionalBoolean;
import static com.liferay.apio.architect.form.FormUtil.getOptionalDate;
import static com.liferay.apio.architect.form.FormUtil.getOptionalDouble;
import static com.liferay.apio.architect.form.FormUtil.getOptionalFormFieldStream;
import static com.liferay.apio.architect.form.FormUtil.getOptionalLong;
import static com.liferay.apio.architect.form.FormUtil.getOptionalString;
import static com.liferay.apio.architect.form.FormUtil.getRequiredBoolean;
import static com.liferay.apio.architect.form.FormUtil.getRequiredDate;
import static com.liferay.apio.architect.form.FormUtil.getRequiredDouble;
import static com.liferay.apio.architect.form.FormUtil.getRequiredFormFieldStream;
import static com.liferay.apio.architect.form.FormUtil.getRequiredLong;
import static com.liferay.apio.architect.form.FormUtil.getRequiredString;

import com.liferay.apio.architect.language.Language;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Holds information about an operation's form. The {@link #get(Map)} method
 * method uses the HTTP request body to extract the form values as detailed in
 * the {@link Builder}.
 *
 * <p>
 * You should always use a {@link Builder} to create instances of this class.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the type used to store the {@code Form} information
 */
public class Form<T> {

	/**
	 * Returns this form's information in a class of type {@code T}, where type
	 * {@code T} matches the type parameter of the {@link Builder} that created
	 * the form.
	 *
	 * @param  body the HTTP request body
	 * @return the form's information in a class of type {@code T}
	 */
	public T get(Map<String, Object> body) {
		T t = _supplier.get();

		_optionalBooleans.forEach(getOptionalBoolean(body, t));
		_optionalDates.forEach(getOptionalDate(body, t));
		_optionalDoubles.forEach(getOptionalDouble(body, t));
		_optionalLongs.forEach(getOptionalLong(body, t));
		_optionalStrings.forEach(getOptionalString(body, t));
		_requiredBooleans.forEach(getRequiredBoolean(body, t));
		_requiredDates.forEach(getRequiredDate(body, t));
		_requiredDoubles.forEach(getRequiredDouble(body, t));
		_requiredLongs.forEach(getRequiredLong(body, t));
		_requiredStrings.forEach(getRequiredString(body, t));

		return t;
	}

	/**
	 * Returns this form's description, which depends on the HTTP request
	 * language.
	 *
	 * @param  language the HTTP request language information
	 * @return the form's description
	 */
	public String getDescription(Language language) {
		return _descriptionFunction.apply(language);
	}

	/**
	 * Returns the list of fields from this {@code Form}.
	 *
	 * @return the list of form fields.
	 */
	public List<FormField> getFormFields() {
		Stream<Stream<FormField>> stream = Stream.of(
			getOptionalFormFieldStream(_optionalBooleans, BOOLEAN),
			getOptionalFormFieldStream(_optionalDates, DATE),
			getOptionalFormFieldStream(_optionalDoubles, DOUBLE),
			getOptionalFormFieldStream(_optionalLongs, LONG),
			getOptionalFormFieldStream(_optionalStrings, STRING),
			getRequiredFormFieldStream(_requiredBooleans, BOOLEAN),
			getRequiredFormFieldStream(_requiredDates, DATE),
			getRequiredFormFieldStream(_requiredDoubles, DOUBLE),
			getRequiredFormFieldStream(_requiredLongs, LONG),
			getRequiredFormFieldStream(_requiredStrings, STRING));

		return stream.flatMap(
			Function.identity()
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Returns the form's title, which depends on the HTTP request language.
	 *
	 * @param  language the HTTP request language information
	 * @return the form's title
	 */
	public String getTitle(Language language) {
		return _titleFunction.apply(language);
	}

	/**
	 * The form's ID.
	 */
	public final String id;

	/**
	 * Creates and populates a {@code Form} of type {@code T}.
	 *
	 * @param <T> the type used to store the form's information
	 */
	public static class Builder<T> {

		/**
		 * Creates a new builder with empty paths.
		 *
		 * @return the new builder
		 */
		public static <T> Builder<T> empty() {
			return new Builder<>(Collections.emptyList());
		}

		public Builder(List<String> paths) {
			_form = new Form<>(paths);
		}

		/**
		 * Adds a function that receives the HTTP request language and returns
		 * the form's title.
		 *
		 * @param  titleFunction the function that receives the HTTP request
		 *         language and returns the form's title
		 * @return the updated builder
		 */
		public DescriptionStep title(Function<Language, String> titleFunction) {
			_form._titleFunction = titleFunction;

			return new DescriptionStep();
		}

		public class ConstructorStep {

			/**
			 * Adds a supplier that provides an instance of the class that
			 * stores the form values.
			 *
			 * @param  supplier the supplier
			 * @return the updated builder
			 */
			public FieldStep constructor(Supplier<T> supplier) {
				_form._supplier = supplier;

				return new FieldStep();
			}

		}

		public class DescriptionStep {

			/**
			 * Adds a function that receives the HTTP request language and
			 * returns the form's description.
			 *
			 * @param  descriptionFunction the function that receives the HTTP
			 *         request language and returns the form's description
			 * @return the updated builder
			 */
			public ConstructorStep description(
				Function<Language, String> descriptionFunction) {

				_form._descriptionFunction = descriptionFunction;

				return new ConstructorStep();
			}

		}

		public class FieldStep {

			/**
			 * Requests an optional boolean from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a boolean.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalBoolean(
				String key, BiConsumer<T, Boolean> biConsumer) {

				_form._optionalBooleans.put(
					key, t -> aBoolean -> biConsumer.accept(t, aBoolean));

				return this;
			}

			/**
			 * Requests an optional date from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a date.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalDate(
				String key, BiConsumer<T, Date> biConsumer) {

				_form._optionalDates.put(
					key, t -> date -> biConsumer.accept(t, date));

				return this;
			}

			/**
			 * Requests an optional double from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a double.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalDouble(
				String key, BiConsumer<T, Double> biConsumer) {

				_form._optionalDoubles.put(
					key, t -> aDouble -> biConsumer.accept(t, aDouble));

				return this;
			}

			/**
			 * Requests an optional long from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a long.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalLong(
				String key, BiConsumer<T, Long> biConsumer) {

				_form._optionalLongs.put(
					key, t -> aLong -> biConsumer.accept(t, aLong));

				return this;
			}

			/**
			 * Requests an optional string from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a string.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalString(
				String key, BiConsumer<T, String> biConsumer) {

				_form._optionalStrings.put(
					key, t -> string -> biConsumer.accept(t, string));

				return this;
			}

			/**
			 * Requests a mandatory boolean from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a boolean.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredBoolean(
				String key, BiConsumer<T, Boolean> biConsumer) {

				_form._requiredBooleans.put(
					key, t -> aBoolean -> biConsumer.accept(t, aBoolean));

				return this;
			}

			/**
			 * Requests a mandatory date from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a date.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredDate(
				String key, BiConsumer<T, Date> biConsumer) {

				_form._requiredDates.put(
					key, t -> date -> biConsumer.accept(t, date));

				return this;
			}

			/**
			 * Requests a mandatory double from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a double.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredDouble(
				String key, BiConsumer<T, Double> biConsumer) {

				_form._requiredDoubles.put(
					key, t -> aDouble -> biConsumer.accept(t, aDouble));

				return this;
			}

			/**
			 * Requests a mandatory long from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a long.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredLong(
				String key, BiConsumer<T, Long> biConsumer) {

				_form._requiredLongs.put(
					key, t -> aLong -> biConsumer.accept(t, aLong));

				return this;
			}

			/**
			 * Requests a mandatory string from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a string.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredString(
				String key, BiConsumer<T, String> biConsumer) {

				_form._requiredStrings.put(
					key, t -> string -> biConsumer.accept(t, string));

				return this;
			}

			/**
			 * Creates and returns the {@link Form} instance, using the
			 * information provided to the builder.
			 *
			 * @return the {@code Form} instance
			 */
			public Form<T> build() {
				return _form;
			}

		}

		private final Form<T> _form;

	}

	private Form(List<String> paths) {
		id = String.join("/", paths);
	}

	private Function<Language, String> _descriptionFunction;
	private final Map<String, Function<T, Consumer<Boolean>>>
		_optionalBooleans = new HashMap<>();
	private final Map<String, Function<T, Consumer<Date>>> _optionalDates =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<Double>>> _optionalDoubles =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<Long>>> _optionalLongs =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<String>>> _optionalStrings =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<Boolean>>>
		_requiredBooleans = new HashMap<>();
	private final Map<String, Function<T, Consumer<Date>>> _requiredDates =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<Double>>> _requiredDoubles =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<Long>>> _requiredLongs =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<String>>> _requiredStrings =
		new HashMap<>();
	private Supplier<T> _supplier;
	private Function<Language, String> _titleFunction;

}