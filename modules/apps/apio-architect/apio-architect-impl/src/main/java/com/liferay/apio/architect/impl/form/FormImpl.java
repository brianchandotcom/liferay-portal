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

import static com.liferay.apio.architect.form.FieldType.BOOLEAN;
import static com.liferay.apio.architect.form.FieldType.BOOLEAN_LIST;
import static com.liferay.apio.architect.form.FieldType.DATE;
import static com.liferay.apio.architect.form.FieldType.DATE_LIST;
import static com.liferay.apio.architect.form.FieldType.DOUBLE;
import static com.liferay.apio.architect.form.FieldType.DOUBLE_LIST;
import static com.liferay.apio.architect.form.FieldType.FILE;
import static com.liferay.apio.architect.form.FieldType.FILE_LIST;
import static com.liferay.apio.architect.form.FieldType.LINKED_MODEL;
import static com.liferay.apio.architect.form.FieldType.LINKED_MODEL_LIST;
import static com.liferay.apio.architect.form.FieldType.LONG;
import static com.liferay.apio.architect.form.FieldType.LONG_LIST;
import static com.liferay.apio.architect.form.FieldType.STRING;
import static com.liferay.apio.architect.form.FieldType.STRING_LIST;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalBoolean;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalBooleanList;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalDate;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalDateList;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalDouble;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalDoubleList;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalFile;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalFileList;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalFormFieldStream;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalLinkedModel;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalLinkedModelList;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalLong;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalLongList;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalString;
import static com.liferay.apio.architect.impl.form.FormUtil.getOptionalStringList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredBoolean;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredBooleanList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredDate;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredDateList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredDouble;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredDoubleList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredFile;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredFileList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredFormFieldStream;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredLinkedModel;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredLinkedModelList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredLong;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredLongList;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredString;
import static com.liferay.apio.architect.impl.form.FormUtil.getRequiredStringList;

import com.liferay.apio.architect.alias.IdentifierFunction;
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.FormField;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.language.AcceptLanguage;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

/**
 * @author Alejandro Hernández
 */
public class FormImpl<T> implements Form<T> {

	@Override
	public T get(Body body) {
		T t = _supplier.get();

		_optionalBooleans.forEach(getOptionalBoolean(body, t));
		_optionalDates.forEach(getOptionalDate(body, t));
		_optionalDoubles.forEach(getOptionalDouble(body, t));
		_optionalFiles.forEach(getOptionalFile(body, t));
		_optionalLinkedModel.forEach(
			getOptionalLinkedModel(body, t, _pathToIdentifierFunction));
		_optionalLinkedModelList.forEach(
			getOptionalLinkedModelList(body, t, _pathToIdentifierFunction));
		_optionalLongs.forEach(getOptionalLong(body, t));
		_optionalStrings.forEach(getOptionalString(body, t));
		_requiredBooleans.forEach(getRequiredBoolean(body, t));
		_requiredDates.forEach(getRequiredDate(body, t));
		_requiredDoubles.forEach(getRequiredDouble(body, t));
		_requiredFiles.forEach(getRequiredFile(body, t));
		_requiredLinkedModel.forEach(
			getRequiredLinkedModel(body, t, _pathToIdentifierFunction));
		_requiredLinkedModelList.forEach(
			getRequiredLinkedModelList(body, t, _pathToIdentifierFunction));
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

	@Override
	public String getDescription(AcceptLanguage acceptLanguage) {
		return _descriptionFunction.apply(acceptLanguage);
	}

	@Override
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
			getOptionalFormFieldStream(_optionalLinkedModel, LINKED_MODEL),
			getOptionalFormFieldStream(
				_optionalLinkedModelList, LINKED_MODEL_LIST),
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
			getRequiredFormFieldStream(_requiredLinkedModel, LINKED_MODEL),
			getRequiredFormFieldStream(
				_requiredLinkedModelList, LINKED_MODEL_LIST),
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

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public List<T> getList(Body body) {
		Optional<List<Body>> optional = body.getBodyMembersOptional();

		List<Body> bodyMembers = optional.orElseThrow(
			() -> new BadRequestException("Body does not contain members"));

		Stream<Body> stream = bodyMembers.stream();

		return stream.map(
			this::get
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public String getTitle(AcceptLanguage acceptLanguage) {
		return _titleFunction.apply(acceptLanguage);
	}

	public static class BuilderImpl<T>
		implements Form.Builder<T>, Form.Builder.FieldStep<T>,
				   Form.Builder.ConstructorStep<T>,
				   Form.Builder.DescriptionStep<T> {

		/**
		 * Creates a new builder with empty paths.
		 *
		 * @return the new builder
		 */
		public static <T> BuilderImpl<T> empty() {
			return new BuilderImpl<>(Collections.emptyList(), __ -> null);
		}

		public BuilderImpl(
			List<String> paths,
			IdentifierFunction<?> pathToIdentifierFunction) {

			_form = new FormImpl<>(paths, pathToIdentifierFunction);
		}

		@Override
		public FieldStep<T> addOptionalBoolean(
			String key, BiConsumer<T, Boolean> biConsumer) {

			_form._optionalBooleans.put(
				key, t -> aBoolean -> biConsumer.accept(t, aBoolean));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalBooleanList(
			String key, BiConsumer<T, List<Boolean>> biConsumer) {

			_form._optionalBooleanLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalDate(
			String key, BiConsumer<T, Date> biConsumer) {

			_form._optionalDates.put(
				key, t -> date -> biConsumer.accept(t, date));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalDateList(
			String key, BiConsumer<T, List<Date>> biConsumer) {

			_form._optionalDateLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalDouble(
			String key, BiConsumer<T, Double> biConsumer) {

			_form._optionalDoubles.put(
				key, t -> aDouble -> biConsumer.accept(t, aDouble));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalDoubleList(
			String key, BiConsumer<T, List<Double>> biConsumer) {

			_form._optionalDoubleLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalFile(
			String key, BiConsumer<T, BinaryFile> biConsumer) {

			_form._optionalFiles.put(
				key, t -> binaryFile -> biConsumer.accept(t, binaryFile));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalFileList(
			String key, BiConsumer<T, List<BinaryFile>> biConsumer) {

			_form._optionalFileLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public <C> FieldStep<T> addOptionalLinkedModel(
			String key, Class<? extends Identifier<C>> aClass,
			BiConsumer<T, C> biConsumer) {

			_form._optionalLinkedModel.put(
				key, t -> c -> biConsumer.accept(t, (C)c));

			return this;
		}

		@Override
		public <C> FieldStep<T> addOptionalLinkedModelList(
			String key, Class<? extends Identifier<C>> aClass,
			BiConsumer<T, List<C>> biConsumer) {

			_form._optionalLinkedModelList.put(
				key, t -> c -> biConsumer.accept(t, (List)c));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalLong(
			String key, BiConsumer<T, Long> biConsumer) {

			_form._optionalLongs.put(
				key, t -> aLong -> biConsumer.accept(t, aLong));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalLongList(
			String key, BiConsumer<T, List<Long>> biConsumer) {

			_form._optionalLongLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalString(
			String key, BiConsumer<T, String> biConsumer) {

			_form._optionalStrings.put(
				key, t -> string -> biConsumer.accept(t, string));

			return this;
		}

		@Override
		public FieldStep<T> addOptionalStringList(
			String key, BiConsumer<T, List<String>> biConsumer) {

			_form._optionalStringLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredBoolean(
			String key, BiConsumer<T, Boolean> biConsumer) {

			_form._requiredBooleans.put(
				key, t -> aBoolean -> biConsumer.accept(t, aBoolean));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredBooleanList(
			String key, BiConsumer<T, List<Boolean>> biConsumer) {

			_form._requiredBooleanLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredDate(
			String key, BiConsumer<T, Date> biConsumer) {

			_form._requiredDates.put(
				key, t -> date -> biConsumer.accept(t, date));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredDateList(
			String key, BiConsumer<T, List<Date>> biConsumer) {

			_form._requiredDateLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredDouble(
			String key, BiConsumer<T, Double> biConsumer) {

			_form._requiredDoubles.put(
				key, t -> aDouble -> biConsumer.accept(t, aDouble));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredDoubleList(
			String key, BiConsumer<T, List<Double>> biConsumer) {

			_form._requiredDoubleLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredFile(
			String key, BiConsumer<T, BinaryFile> biConsumer) {

			_form._requiredFiles.put(
				key, t -> binaryFile -> biConsumer.accept(t, binaryFile));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredFileList(
			String key, BiConsumer<T, List<BinaryFile>> biConsumer) {

			_form._requiredFileLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public <C> FieldStep<T> addRequiredLinkedModel(
			String key, Class<? extends Identifier<C>> aClass,
			BiConsumer<T, C> biConsumer) {

			_form._requiredLinkedModel.put(
				key, t -> c -> biConsumer.accept(t, (C)c));

			return this;
		}

		@Override
		public <C> FieldStep<T> addRequiredLinkedModelList(
			String key, Class<? extends Identifier<C>> aClass,
			BiConsumer<T, List<C>> biConsumer) {

			_form._requiredLinkedModelList.put(
				key, t -> c -> biConsumer.accept(t, (List)c));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredLong(
			String key, BiConsumer<T, Long> biConsumer) {

			_form._requiredLongs.put(
				key, t -> aLong -> biConsumer.accept(t, aLong));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredLongList(
			String key, BiConsumer<T, List<Long>> biConsumer) {

			_form._requiredLongLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredString(
			String key, BiConsumer<T, String> biConsumer) {

			_form._requiredStrings.put(
				key, t -> string -> biConsumer.accept(t, string));

			return this;
		}

		@Override
		public FieldStep<T> addRequiredStringList(
			String key, BiConsumer<T, List<String>> biConsumer) {

			_form._requiredStringLists.put(
				key, t -> list -> biConsumer.accept(t, list));

			return this;
		}

		@Override
		public Form<T> build() {
			return _form;
		}

		@Override
		public FieldStep<T> constructor(Supplier<T> supplier) {
			_form._supplier = supplier;

			return this;
		}

		@Override
		public ConstructorStep<T> description(
			Function<AcceptLanguage, String> descriptionFunction) {

			_form._descriptionFunction = descriptionFunction;

			return this;
		}

		@Override
		public DescriptionStep<T> title(
			Function<AcceptLanguage, String> titleFunction) {

			_form._titleFunction = titleFunction;

			return this;
		}

		private final FormImpl<T> _form;

	}

	private FormImpl(
		List<String> paths, IdentifierFunction<?> pathToIdentifierFunction) {

		_id = String.join("/", paths);
		_pathToIdentifierFunction = pathToIdentifierFunction;
	}

	private Function<AcceptLanguage, String> _descriptionFunction;
	private final String _id;
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
	private final Map<String, Function<T, Consumer<?>>> _optionalLinkedModel =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<?>>>>
		_optionalLinkedModelList = new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Long>>>>
		_optionalLongLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Long>>> _optionalLongs =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<String>>>>
		_optionalStringLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<String>>> _optionalStrings =
		new HashMap<>();
	private final IdentifierFunction<?> _pathToIdentifierFunction;
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
	private final Map<String, Function<T, Consumer<?>>> _requiredLinkedModel =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<?>>>>
		_requiredLinkedModelList = new HashMap<>();
	private final Map<String, Function<T, Consumer<List<Long>>>>
		_requiredLongLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<Long>>> _requiredLongs =
		new HashMap<>();
	private final Map<String, Function<T, Consumer<List<String>>>>
		_requiredStringLists = new HashMap<>();
	private final Map<String, Function<T, Consumer<String>>> _requiredStrings =
		new HashMap<>();
	private Supplier<T> _supplier;
	private Function<AcceptLanguage, String> _titleFunction;

}