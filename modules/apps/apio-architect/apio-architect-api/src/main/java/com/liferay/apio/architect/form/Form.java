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

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.language.AcceptLanguage;

import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
@ProviderType
public interface Form<T> {

	/**
	 * Returns this form's information in a class of type {@code T}, where type
	 * {@code T} matches the type parameter of the {@link Builder} that created
	 * the form.
	 *
	 * @param  body the HTTP request body
	 * @return the form's information in a class of type {@code T}
	 */
	public T get(Body body);

	/**
	 * Returns this form's description, which depends on the HTTP request accept
	 * language.
	 *
	 * @param  acceptLanguage the HTTP request accept language information
	 * @return the form's description
	 */
	public String getDescription(AcceptLanguage acceptLanguage);

	/**
	 * Returns the list of fields from this {@code Form}.
	 *
	 * @return the list of form fields.
	 */
	public List<FormField> getFormFields();

	/**
	 * The form's ID.
	 */
	public String getId();

	/**
	 * Returns multiple form's information in list of classes of type {@code T},
	 * where type {@code T} matches the type parameter of the {@link Builder}
	 * that created the form.
	 *
	 * @param  body the HTTP request body
	 * @return multiple form's information in a list of classes of type {@code
	 *         T}
	 */
	public List<T> getList(Body body);

	/**
	 * Returns the form's title, which depends on the HTTP request accept
	 * language.
	 *
	 * @param  acceptLanguage the HTTP request accept language information
	 * @return the form's title
	 */
	public String getTitle(AcceptLanguage acceptLanguage);

	/**
	 * Creates and populates a {@code Form} of type {@code T}.
	 *
	 * @param <T> the type used to store the form's information
	 */
	@ProviderType
	public interface Builder<T> {

		/**
		 * Adds a function that receives the HTTP request language and returns
		 * the form's title.
		 *
		 * @param  titleFunction the function that receives the HTTP request
		 *         language and returns the form's title
		 * @return the updated builder
		 */
		public DescriptionStep<T> title(
			Function<AcceptLanguage, String> titleFunction);

		@ProviderType
		public interface ConstructorStep<T> {

			/**
			 * Adds a supplier that provides an instance of the class that
			 * stores the form values.
			 *
			 * @param  supplier the supplier
			 * @return the updated builder
			 */
			public FieldStep<T> constructor(Supplier<T> supplier);

		}

		@ProviderType
		public interface DescriptionStep<T> {

			/**
			 * Adds a function that receives the HTTP request language and
			 * returns the form's description.
			 *
			 * @param  descriptionFunction the function that receives the HTTP
			 *         request language and returns the form's description
			 * @return the updated builder
			 */
			public ConstructorStep<T> description(
				Function<AcceptLanguage, String> descriptionFunction);

		}

		@ProviderType
		public interface FieldStep<T> {

			/**
			 * Requests an optional boolean from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a boolean.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalBoolean(
				String key, BiConsumer<T, Boolean> biConsumer);

			/**
			 * Requests an optional boolean list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a boolean list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalBooleanList(
				String key, BiConsumer<T, List<Boolean>> biConsumer);

			/**
			 * Requests an optional date from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a date.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalDate(
				String key, BiConsumer<T, Date> biConsumer);

			/**
			 * Requests an optional date list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a date list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalDateList(
				String key, BiConsumer<T, List<Date>> biConsumer);

			/**
			 * Requests an optional double from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a double.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalDouble(
				String key, BiConsumer<T, Double> biConsumer);

			/**
			 * Requests an optional double list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a double list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalDoubleList(
				String key, BiConsumer<T, List<Double>> biConsumer);

			/**
			 * Requests an optional file from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a file.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalFile(
				String key, BiConsumer<T, BinaryFile> biConsumer);

			/**
			 * Requests an optional file list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a file list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalFileList(
				String key, BiConsumer<T, List<BinaryFile>> biConsumer);

			/**
			 * Requests an optional linked model from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a linked model.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  aClass the identifier class to extract the class ID and
			 *         return it
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 * @review
			 */
			public <C> FieldStep<T> addOptionalLinkedModel(
				String key, Class<? extends Identifier<C>> aClass,
				BiConsumer<T, C> biConsumer);

			/**
			 * Requests an optional list of linked models from the HTTP request
			 * body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a linked model
			 * list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  aClass the identifier class to extract the class ID and
			 *         return it
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 * @review
			 */
			public <C> FieldStep<T> addOptionalLinkedModelList(
				String key, Class<? extends Identifier<C>> aClass,
				BiConsumer<T, List<C>> biConsumer);

			/**
			 * Requests an optional long from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a long.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalLong(
				String key, BiConsumer<T, Long> biConsumer);

			/**
			 * Requests an optional long list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a long list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalLongList(
				String key, BiConsumer<T, List<Long>> biConsumer);

			/**
			 * Requests an optional string from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a string.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalString(
				String key, BiConsumer<T, String> biConsumer);

			/**
			 * Requests an optional string list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value, if the field is present. A {@code
			 * BadRequestException} is thrown if the field is found but it isn't
			 * a string list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call if the field is found
			 * @return the updated builder
			 */
			public FieldStep<T> addOptionalStringList(
				String key, BiConsumer<T, List<String>> biConsumer);

			/**
			 * Requests a mandatory boolean from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a boolean.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredBoolean(
				String key, BiConsumer<T, Boolean> biConsumer);

			/**
			 * Requests a mandatory boolean list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a boolean list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredBooleanList(
				String key, BiConsumer<T, List<Boolean>> biConsumer);

			/**
			 * Requests a mandatory date from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a date.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredDate(
				String key, BiConsumer<T, Date> biConsumer);

			/**
			 * Requests a mandatory date list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a date list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredDateList(
				String key, BiConsumer<T, List<Date>> biConsumer);

			/**
			 * Requests a mandatory double from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a double.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredDouble(
				String key, BiConsumer<T, Double> biConsumer);

			/**
			 * Requests a mandatory double list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a double list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredDoubleList(
				String key, BiConsumer<T, List<Double>> biConsumer);

			/**
			 * Requests a mandatory file from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a file.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredFile(
				String key, BiConsumer<T, BinaryFile> biConsumer);

			/**
			 * Requests a mandatory file list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a file list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredFileList(
				String key, BiConsumer<T, List<BinaryFile>> biConsumer);

			/**
			 * Requests a mandatory linked model from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a required
			 * linked model.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  aClass the identifier class to extract the class ID and
			 *         return it
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 * @review
			 */
			public <C> FieldStep<T> addRequiredLinkedModel(
				String key, Class<? extends Identifier<C>> aClass,
				BiConsumer<T, C> biConsumer);

			/**
			 * Requests a mandatory list of linked models from the HTTP request
			 * body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a required
			 * linked model list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  aClass the identifier class to extract the class ID and
			 *         return it
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 * @review
			 */
			public <C> FieldStep<T> addRequiredLinkedModelList(
				String key, Class<? extends Identifier<C>> aClass,
				BiConsumer<T, List<C>> biConsumer);

			/**
			 * Requests a mandatory long from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a long.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredLong(
				String key, BiConsumer<T, Long> biConsumer);

			/**
			 * Requests a mandatory long list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a long list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredLongList(
				String key, BiConsumer<T, List<Long>> biConsumer);

			/**
			 * Requests a mandatory string from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a string.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredString(
				String key, BiConsumer<T, String> biConsumer);

			/**
			 * Requests a mandatory string list from the HTTP request body.
			 *
			 * <p>
			 * This method calls the provided consumer with the store instance
			 * (provided with the {@link ConstructorStep#constructor} method)
			 * and the field value. A {@code BadRequestException} is thrown if
			 * the field isn't found, or it's found but it isn't a string list.
			 * </p>
			 *
			 * @param  key the field's key
			 * @param  biConsumer the consumer to call
			 * @return the updated builder
			 */
			public FieldStep<T> addRequiredStringList(
				String key, BiConsumer<T, List<String>> biConsumer);

			/**
			 * Creates and returns the {@link Form} instance, using the
			 * information provided to the builder.
			 *
			 * @return the {@code Form} instance
			 */
			public Form<T> build();

		}

	}

}