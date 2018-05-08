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

package com.liferay.apio.architect.writer;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;
import static com.liferay.apio.architect.writer.url.URLCreator.createFormURL;
import static com.liferay.apio.architect.writer.util.WriterUtil.getFieldsWriter;
import static com.liferay.apio.architect.writer.util.WriterUtil.getPathOptional;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.writer.alias.PathFunction;
import com.liferay.apio.architect.writer.alias.RepresentorFunction;
import com.liferay.apio.architect.writer.alias.ResourceNameFunction;
import com.liferay.apio.architect.writer.alias.SingleModelFunction;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Writes a single model.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
public class SingleModelWriter<T> {

	/**
	 * Creates a new {@code SingleModelWriter} object, without creating the
	 * builder.
	 *
	 * @param  function the function that converts a builder to a {@code
	 *         SingleModelWriter}
	 * @return the {@code SingleModelWriter} instance
	 */
	public static <T> SingleModelWriter<T> create(
		Function<Builder<T>, SingleModelWriter<T>> function) {

		return function.apply(new Builder<>());
	}

	public SingleModelWriter(Builder<T> builder) {
		_pathFunction = builder._pathFunction;
		_representorFunction = builder._representorFunction;
		_requestInfo = builder._requestInfo;
		_resourceNameFunction = builder._resourceNameFunction;
		_singleModel = builder._singleModel;
		_singleModelMessageMapper = builder._singleModelMessageMapper;
		_singleModelFunction = builder._singleModelFunction;

		_jsonObjectBuilder = new JSONObjectBuilder();
	}

	/**
	 * Writes the handled {@link SingleModel} to a string. This method uses a
	 * {@link FieldsWriter} to write the different fields of its {@link
	 * Representor}. If no {@code Representor} or {@code Path} exists for the
	 * model, this method returns {@code Optional#empty()}.
	 *
	 * @return the string representation of the {@code SingleModel}, if the
	 *         model's {@code Representor} and {@code Path} exist; returns
	 *         {@code Optional#empty()} otherwise
	 */
	@SuppressWarnings("Duplicates")
	public Optional<String> write() {
		Optional<FieldsWriter<T, ?>> optional = getFieldsWriter(
			_singleModel, null, _requestInfo, _pathFunction,
			_representorFunction, _singleModelFunction);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		FieldsWriter<T, ?> fieldsWriter = optional.get();

		_singleModelMessageMapper.onStart(
			_jsonObjectBuilder, _singleModel, _requestInfo.getHttpHeaders());

		fieldsWriter.writeBooleanFields(
			(field, value) -> _singleModelMessageMapper.mapBooleanField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeBooleanListFields(
			(field, value) -> _singleModelMessageMapper.mapBooleanListField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) -> _singleModelMessageMapper.mapNumberField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeNumberListFields(
			(field, value) -> _singleModelMessageMapper.mapNumberListField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeStringFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeStringListFields(
			(field, value) -> _singleModelMessageMapper.mapStringListField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeLinks(
			(fieldName, link) -> _singleModelMessageMapper.mapLink(
				_jsonObjectBuilder, fieldName, link));

		fieldsWriter.writeTypes(
			types -> _singleModelMessageMapper.mapTypes(
				_jsonObjectBuilder, types));

		fieldsWriter.writeBinaries(
			(field, value) -> _singleModelMessageMapper.mapLink(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeSingleURL(
			url -> _singleModelMessageMapper.mapSelfURL(
				_jsonObjectBuilder, url));

		List<Operation> operations = _singleModel.getOperations();

		operations.forEach(
			operation -> {
				JSONObjectBuilder operationJSONObjectBuilder =
					new JSONObjectBuilder();

				_singleModelMessageMapper.onStartOperation(
					_jsonObjectBuilder, operationJSONObjectBuilder, operation);

				Optional<Form> formOptional = operation.getFormOptional();

				formOptional.map(
					form -> createFormURL(_requestInfo.getServerURL(), form)
				).ifPresent(
					url -> _singleModelMessageMapper.mapOperationFormURL(
						_jsonObjectBuilder, operationJSONObjectBuilder, url)
				);

				_singleModelMessageMapper.mapOperationMethod(
					_jsonObjectBuilder, operationJSONObjectBuilder,
					operation.method);

				_singleModelMessageMapper.onFinishOperation(
					_jsonObjectBuilder, operationJSONObjectBuilder, operation);
			});

		fieldsWriter.writeRelatedModels(
			singleModel -> getPathOptional(
				singleModel, _pathFunction, _representorFunction),
			this::writeEmbeddedModelFields,
			(resourceURL, embeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, resourceURL),
			(resourceURL, embeddedPathElements) ->
				_singleModelMessageMapper.mapEmbeddedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, resourceURL));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, embeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, url));

		_writeNestedResources(_representorFunction, _singleModel, null);

		_singleModelMessageMapper.onFinish(
			_jsonObjectBuilder, _singleModel, _requestInfo.getHttpHeaders());

		JsonObject jsonObject = _jsonObjectBuilder.build();

		return Optional.of(jsonObject.toString());
	}

	public <S> void writeEmbeddedModelFields(
		SingleModel<S> singleModel,
		FunctionalList<String> embeddedPathElements) {

		writeEmbeddedModelFields(
			singleModel, embeddedPathElements, _representorFunction);
	}

	/**
	 * Writes a related {@link SingleModel} with the {@link
	 * SingleModelMessageMapper}. This method uses a {@link FieldsWriter} to
	 * write the different fields of its {@link Representor}. If no {@code
	 * Representor} or {@link com.liferay.apio.architect.uri.Path} exists for
	 * the model, this method doesn't do anything.
	 *
	 * @param singleModel the {@code SingleModel} to write
	 * @param embeddedPathElements the related model's embedded path elements
	 */
	public <S> void writeEmbeddedModelFields(
		SingleModel<S> singleModel, FunctionalList<String> embeddedPathElements,
		RepresentorFunction representorFunction) {

		Optional<FieldsWriter<S, ?>> optional = getFieldsWriter(
			singleModel, embeddedPathElements, _requestInfo, _pathFunction,
			representorFunction, _representorFunction, _singleModelFunction,
			_singleModel);

		if (!optional.isPresent()) {
			return;
		}

		FieldsWriter<S, ?> fieldsWriter = optional.get();

		fieldsWriter.writeBooleanFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceBooleanField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeBooleanListFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceBooleanListField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceNumberField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeNumberListFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceNumberListField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeStringFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeStringListFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringListField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeLinks(
			(fieldName, link) ->
				_singleModelMessageMapper.mapEmbeddedResourceLink(
					_jsonObjectBuilder, embeddedPathElements, fieldName, link));

		fieldsWriter.writeTypes(
			types -> _singleModelMessageMapper.mapEmbeddedResourceTypes(
				_jsonObjectBuilder, embeddedPathElements, types));

		fieldsWriter.writeBinaries(
			(field, value) -> _singleModelMessageMapper.mapEmbeddedResourceLink(
				_jsonObjectBuilder, embeddedPathElements, field, value));

		List<Operation> operations = singleModel.getOperations();

		operations.forEach(
			operation -> {
				JSONObjectBuilder operationJSONObjectBuilder =
					new JSONObjectBuilder();

				_singleModelMessageMapper.onStartEmbeddedOperation(
					_jsonObjectBuilder, operationJSONObjectBuilder,
					embeddedPathElements, operation);

				Optional<Form> formOptional = operation.getFormOptional();

				formOptional.ifPresent(
					form -> {
						String url = createFormURL(
							_requestInfo.getServerURL(), form);

						_singleModelMessageMapper.mapEmbeddedOperationFormURL(
							_jsonObjectBuilder, operationJSONObjectBuilder,
							embeddedPathElements, url);
					});

				_singleModelMessageMapper.mapEmbeddedOperationMethod(
					_jsonObjectBuilder, operationJSONObjectBuilder,
					embeddedPathElements, operation.method);

				_singleModelMessageMapper.onFinishEmbeddedOperation(
					_jsonObjectBuilder, operationJSONObjectBuilder,
					embeddedPathElements, operation);
			});

		fieldsWriter.writeRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction, _representorFunction),
			this::writeEmbeddedModelFields,
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL),
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapEmbeddedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements, url));

		_writeNestedResources(
			representorFunction, singleModel, embeddedPathElements);
	}

	/**
	 * Creates {@code SingleModelWriter} instances.
	 *
	 * @param <T> the model's type
	 */
	public static class Builder<T> {

		/**
		 * Adds information to the builder about the single model being written.
		 *
		 * @param  singleModel the single model
		 * @return the updated builder
		 */
		public SingleModelMessageMapperStep singleModel(
			SingleModel<T> singleModel) {

			_singleModel = singleModel;

			return new SingleModelMessageMapperStep();
		}

		public class BuildStep {

			/**
			 * Constructs and returns a {@link SingleModelWriter} instance by
			 * using the builder's information.
			 *
			 * @return the {@code SingleModelWriter} instance
			 */
			public SingleModelWriter<T> build() {
				return new SingleModelWriter<>(Builder.this);
			}

		}

		public class PathFunctionStep {

			/**
			 * Adds information to the builder about the function that converts
			 * an identifier to a {@link com.liferay.apio.architect.uri.Path}.
			 *
			 * @param  pathFunction the function that converts an identifier to
			 *         a {@code Path}
			 * @return the updated builder
			 */
			public ResourceNameFunctionStep pathFunction(
				PathFunction pathFunction) {

				_pathFunction = pathFunction;

				return new ResourceNameFunctionStep();
			}

		}

		public class RepresentorFunctionStep {

			/**
			 * Adds information to the builder about the function that gets a
			 * class's {@link Representor}.
			 *
			 * @param  representorFunction the function that gets a class's
			 *         {@code Representor}
			 * @return the updated builder
			 */
			public RequestInfoStep representorFunction(
				RepresentorFunction representorFunction) {

				_representorFunction = representorFunction;

				return new RequestInfoStep();
			}

		}

		public class RequestInfoStep {

			/**
			 * Adds information to the builder about the request.
			 *
			 * @param  requestInfo the information about the request. It can be
			 *         created by using a {@link RequestInfo.Builder}
			 * @return the updated builder
			 */
			public SingleModelFunctionStep requestInfo(
				RequestInfo requestInfo) {

				_requestInfo = requestInfo;

				return new SingleModelFunctionStep();
			}

		}

		public class ResourceNameFunctionStep {

			/**
			 * Adds information to the builder about the function that gets the
			 * name of a class's {@code
			 * com.liferay.apio.architect.resource.Representor}.
			 *
			 * @param  resourceNameFunction the function that gets the name of a
			 *         class's {@code Representor}
			 * @return the updated builder
			 */
			public RepresentorFunctionStep resourceNameFunction(
				ResourceNameFunction resourceNameFunction) {

				_resourceNameFunction = resourceNameFunction;

				return new RepresentorFunctionStep();
			}

		}

		public class SingleModelFunctionStep {

			/**
			 * Adds information to the builder about the function that gets the
			 * {@code SingleModel} from a class using its identifier.
			 *
			 * @param  singleModelFunction the function that gets the {@code
			 *         SingleModel} of a class
			 * @return the updated builder
			 */
			public BuildStep singleModelFunction(
				SingleModelFunction singleModelFunction) {

				_singleModelFunction = singleModelFunction;

				return new BuildStep();
			}

		}

		public class SingleModelMessageMapperStep {

			/**
			 * Adds information to the builder about the {@link
			 * SingleModelMessageMapper}.
			 *
			 * @param  singleModelMessageMapper the {@code
			 *         SingleModelMessageMapper} headers
			 * @return the updated builder
			 */
			public PathFunctionStep modelMessageMapper(
				SingleModelMessageMapper<T> singleModelMessageMapper) {

				_singleModelMessageMapper = singleModelMessageMapper;

				return new PathFunctionStep();
			}

		}

		private PathFunction _pathFunction;
		private RepresentorFunction _representorFunction;
		private RequestInfo _requestInfo;
		private ResourceNameFunction _resourceNameFunction;
		private SingleModel<T> _singleModel;
		private SingleModelFunction _singleModelFunction;
		private SingleModelMessageMapper<T> _singleModelMessageMapper;

	}

	private <S> void _writeNestedResources(
		RepresentorFunction representorFunction, SingleModel<S> singleModel,
		FunctionalList<String> embeddedPathElements) {

		Optional<Representor<S, ?>> representorOptional = unsafeCast(
			representorFunction.apply(singleModel.getResourceName()));

		representorOptional.ifPresent(
			_representor -> {
				Map<String, Representor<?, ?>> nested =
					_representor.getNested();

				nested.forEach(
					(key, value) -> {
						Map<String, Function<S, ?>> nestedFunctions =
							_representor.getNestedFunctions();

						Function<S, ?> nestedMapper = nestedFunctions.get(key);

						Object mappedModel = nestedMapper.apply(
							singleModel.getModel());

						FunctionalList<String> embeddedNestedPathElements =
							new FunctionalList<>(embeddedPathElements, key);

						writeEmbeddedModelFields(
							new SingleModel<>(
								mappedModel, "", Collections.emptyList()),
							embeddedNestedPathElements,
							__ -> Optional.of(value));
					});
			});
	}

	private final JSONObjectBuilder _jsonObjectBuilder;
	private final PathFunction _pathFunction;
	private final RepresentorFunction _representorFunction;
	private final RequestInfo _requestInfo;
	private final ResourceNameFunction _resourceNameFunction;
	private final SingleModel<T> _singleModel;
	private final SingleModelFunction _singleModelFunction;
	private final SingleModelMessageMapper<T> _singleModelMessageMapper;

}