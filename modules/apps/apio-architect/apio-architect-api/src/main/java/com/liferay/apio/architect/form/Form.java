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
import static com.liferay.apio.architect.form.FieldType.BOOLEAN_LIST;
import static com.liferay.apio.architect.form.FieldType.DATE;
import static com.liferay.apio.architect.form.FieldType.DATE_LIST;
import static com.liferay.apio.architect.form.FieldType.DOUBLE;
import static com.liferay.apio.architect.form.FieldType.DOUBLE_LIST;
import static com.liferay.apio.architect.form.FieldType.FILE;
import static com.liferay.apio.architect.form.FieldType.FILE_LIST;
import static com.liferay.apio.architect.form.FieldType.LONG;
import static com.liferay.apio.architect.form.FieldType.LONG_LIST;
import static com.liferay.apio.architect.form.FieldType.STRING;
import static com.liferay.apio.architect.form.FieldType.STRING_LIST;
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

import com.liferay.apio.architect.file.BinaryFile;
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
 * Holds information about an operation's form. The {@link #get(Body)} method
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
	public T get(Body body) {
		T t = _supplier.get();

		_optionalBooleans.forEach(getOptionalBoolean(body, t));
		_optionalDates.forEach(getOptionalDate(body, t));
		_optionalDoubles.forEach(getOptionalDouble(body, t));
		_optionalFiles.forEach(getOptionalFile(body, t));
		_optionalLongs.forEach(getOptionalLong(body, t));
		_optionalStrings.forEach(getOptionalString(body, t));
		_requiredBooleans.forEach(getRequiredBoolean(body, t));
		_requiredDates.forEach(getRequiredDate(body, t));
		_requiredDoubles.forEach(getRequiredDouble(body, t));
		_requiredFiles.forEach(getRequiredFile(body, t));
		_requiredLongs.forEach(getRequiredLong(body, t));
		_requiredStrings.forEach(getRequiredString(body, t));
		_optionalBooleanLists.forEach(getOptionalBooleanList(body, t));
		_optionalDateLists.forEach(getOptionalDateList(body, t));
		_optionalDoubleLists.forEach(getOptionalDoubleList(body, t));
		_optionalFileLists.forEach(getOptionalFileList(body, t));
		_optionalLongLists.forEach(getOptionalLongList(body, t));
		_optionalStringLists.forEach(getOptionalStringList(body, t));
		_requiredBooleanLists.forEach(getRequiredBooleanList(body, t));
		_requiredDateLists.forEach(getRequiredDateList(body, t));
		_requiredDoubleLists.forEach(getRequiredDoubleList(body, t));
		_requiredFileLists.forEach(getRequiredFileList(body, t));
		_requiredLongLists.forEach(getRequiredLongList(body, t));
		_requiredStringLists.forEach(getRequiredStringList(body, t));

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
		return Stream.of(
			getOptionalFormFieldStream(_optionalBooleans, BOOLEAN),
			getOptionalFormFieldStream(_optionalBooleanLists, BOOLEAN_LIST),
			getOptionalFormFieldStream(_optionalDates, DATE),
			getOptionalFormFieldStream(_optionalDateLists, DATE_LIST),
			getOptionalFormFieldStream(_optionalDoubles, DOUBLE),
			getOptionalFormFieldStream(_optionalDoubleLists, DOUBLE_LIST),
			getOptionalFormFieldStream(_optionalFiles, FILE),
			getOptionalFormFieldStream(_optionalFileLists, FILE_LIST),
			getOptionalFormFieldStream(_optionalLongs, LONG),
			getOptionalFormFieldStream(_optionalLongLists, LONG_LIST),
			getOptionalFormFieldStream(_optionalStrings, STRING),
			getOptionalFormFieldStream(_optionalStringLists, STRING_LIST),
			getRequiredFormFieldStream(_requiredBooleans, BOOLEAN),
			getRequiredFormFieldStream(_requiredBooleanLists, BOOLEAN_LIST),
			getRequiredFormFieldStream(_requiredDates, DATE),
			getRequiredFormFieldStream(_requiredDateLists, DATE_LIST),
			getRequiredFormFieldStream(_requiredDoubles, DOUBLE),
			getRequiredFormFieldStream(_requiredDoubleLists, DOUBLE_LIST),
			getRequiredFormFieldStream(_requiredFiles, FILE),
			getRequiredFormFieldStream(_requiredFileLists, FILE_LIST),
			getRequiredFormFieldStream(_requiredLongs, LONG),
			getRequiredFormFieldStream(_requiredLongLists, LONG_LIST),
			getRequiredFormFieldStream(_requiredStrings, STRING),
			getRequiredFormFieldStream(_requiredStringLists, STRING_LIST)
		).flatMap(
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
			 * Requests an optional boolean list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a boolean list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalBooleanList(
				String key, BiConsumer<T, List<Boolean>> biConsumer) {

				_form._optionalBooleanLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests an optional date list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a date list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalDateList(
				String key, BiConsumer<T, List<Date>> biConsumer) {

				_form._optionalDateLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests an optional double list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a double list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalDoubleList(
				String key, BiConsumer<T, List<Double>> biConsumer) {

				_form._optionalDoubleLists.put(
					key, t -> list -> biConsumer.accept(t, list));

				return this;
			}

			/**
			 * Requests an optional file from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a file.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalFile(
				String key, BiConsumer<T, BinaryFile> biConsumer) {

				_form._optionalFiles.put(
					key, t -> binaryFile -> biConsumer.accept(t, binaryFile));

				return this;
			}

			/**
			 * Requests an optional file list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a file list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalFileList(
				String key, BiConsumer<T, List<BinaryFile>> biConsumer) {

				_form._optionalFileLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests an optional long list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a long list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalLongList(
				String key, BiConsumer<T, List<Long>> biConsumer) {

				_form._optionalLongLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests an optional string list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value, if the field is present. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field is found
			 * but it isn't a string list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep addOptionalStringList(
				String key, BiConsumer<T, List<String>> biConsumer) {

				_form._optionalStringLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests a mandatory boolean list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a boolean list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredBooleanList(
				String key, BiConsumer<T, List<Boolean>> biConsumer) {

				_form._requiredBooleanLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests a mandatory date list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a date list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredDateList(
				String key, BiConsumer<T, List<Date>> biConsumer) {

				_form._requiredDateLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests a mandatory double list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a double list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredDoubleList(
				String key, BiConsumer<T, List<Double>> biConsumer) {

				_form._requiredDoubleLists.put(
					key, t -> list -> biConsumer.accept(t, list));

				return this;
			}

			/**
			 * Requests a mandatory file from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a file.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredFile(
				String key, BiConsumer<T, BinaryFile> biConsumer) {

				_form._requiredFiles.put(
					key, t -> binaryFile -> biConsumer.accept(t, binaryFile));

				return this;
			}

			/**
			 * Requests a mandatory file list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a file list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredFileList(
				String key, BiConsumer<T, List<BinaryFile>> biConsumer) {

				_form._requiredFileLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests a mandatory long list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a long list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredLongList(
				String key, BiConsumer<T, List<Long>> biConsumer) {

				_form._requiredLongLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
			 * Requests a mandatory string list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor(Supplier)}
			 * method) and the field value. A {@code
			 * javax.ws.rs.BadRequestException} is thrown if the field isn't
			 * found, or it's found but it isn't a string list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep addRequiredStringList(
				String key, BiConsumer<T, List<String>> biConsumer) {

				_form._requiredStringLists.put(
					key, t -> list -> biConsumer.accept(t, list));

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
	private final Map<String, Function<T, Consumer<List<Boolean>>>>
		_optionalBooleanLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Boolean>>>
		_optionalBooleans = new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Date>>>>
		_optionalDateLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Date>>> _optionalDates =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Double>>>>
		_optionalDoubleLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Double>>> _optionalDoubles =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<BinaryFile>>>>
		_optionalFileLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<BinaryFile>>>
		_optionalFiles = new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Long>>>>
		_optionalLongLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Long>>> _optionalLongs =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<String>>>>
		_optionalStringLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<String>>> _optionalStrings =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Boolean>>>>
		_requiredBooleanLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Boolean>>>
		_requiredBooleans = new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Date>>>>
		_requiredDateLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Date>>> _requiredDates =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Double>>>>
		_requiredDoubleLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Double>>> _requiredDoubles =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<BinaryFile>>>>
		_requiredFileLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<BinaryFile>>>
		_requiredFiles = new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Long>>>>
		_requiredLongLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Long>>> _requiredLongs =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<String>>>>
		_requiredStringLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<String>>> _requiredStrings =
		new HashMap<>();
	private Supplier<T> _supplier;
	private Function<Language, String> _titleFunction;

}