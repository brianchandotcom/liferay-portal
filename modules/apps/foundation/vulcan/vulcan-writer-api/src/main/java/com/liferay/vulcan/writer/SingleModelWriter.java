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

package com.liferay.vulcan.writer;

import static com.liferay.vulcan.writer.util.WriterUtil.getFieldsWriter;
import static com.liferay.vulcan.writer.util.WriterUtil.getPathOptional;

import com.google.gson.JsonObject;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.SingleModelMessageMapper;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.request.RequestInfo;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.writer.alias.PathFunction;
import com.liferay.vulcan.writer.alias.RepresentorFunction;
import com.liferay.vulcan.writer.alias.ResourceNameFunction;

import java.util.Optional;
import java.util.function.Function;

/**
 * An instance of this class can be used to write a single model.
 *
 * @author Alejandro Hernández
 * @review
 */
public class SingleModelWriter<T> {

	/**
	 * This method can be used to create a new {@code SingleModelWriter} object,
	 * without creating the builder.
	 *
	 * @param  function the function that transforms a builder into a {@code
	 *         SingleModelWriter}
	 * @return the {@code SingleModelWriter} instance
	 */
	public static <T> SingleModelWriter<T> create(
		Function<Builder<T>, SingleModelWriter<T>> function) {

		return function.apply(new Builder<>());
	}

	public SingleModelWriter(Builder<T> builder) {
		_singleModelMessageMapper = builder._singleModelMessageMapper;
		_singleModel = builder._singleModel;
		_jsonObjectBuilder = new JSONObjectBuilder();
		_requestInfo = builder._requestInfo;
		_representorFunction = builder._representorFunction;
		_pathFunction = builder._pathFunction;
		_resourceNameFunction = builder._resourceNameFunction;
	}

	/**
	 * Write the handled {@link SingleModel} to a String. It uses a {@link
	 * FieldsWriter} in order to write the different fields of its {@link
	 * com.liferay.vulcan.resource.Representor}. If no {@code Representor} or
	 * {@code Path} can be found for the model, {@code Optional#empty()} is
	 * returned.
	 *
	 * @return the representation of the {@code SingleModel} if both its {@code
	 *         Representor} and {@code Path} are available; returns {@code
	 *         Optional#empty()} otherwise
	 * @review
	 */
	public Optional<String> write() {
		Optional<FieldsWriter<T, Identifier>> optional = getFieldsWriter(
			_singleModel, null, _requestInfo, _pathFunction,
			_representorFunction);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		FieldsWriter<T, Identifier> fieldsWriter = optional.get();

		_singleModelMessageMapper.onStart(
			_jsonObjectBuilder, _singleModel.getModel(),
			_singleModel.getModelClass(), _requestInfo.getHttpHeaders());

		fieldsWriter.writeBooleanFields(
			(field, value) -> _singleModelMessageMapper.mapBooleanField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) -> _singleModelMessageMapper.mapNumberField(
				_jsonObjectBuilder, field, value));

		fieldsWriter.writeStringFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
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

		fieldsWriter.writeEmbeddedRelatedModels(
			singleModel -> getPathOptional(
				singleModel, _pathFunction, _representorFunction),
			this::_writeEmbeddedModelFields,
			(resourceURL, embeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, resourceURL),
			(resourceURL, embeddedPathElements) ->
				_singleModelMessageMapper.mapEmbeddedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, resourceURL));

		fieldsWriter.writeLinkedRelatedModels(
			singleModel -> getPathOptional(
				singleModel, _pathFunction, _representorFunction),
			(url, embeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, url));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, embeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, embeddedPathElements, url));

		_singleModelMessageMapper.onFinish(
			_jsonObjectBuilder, _singleModel.getModel(),
			_singleModel.getModelClass(), _requestInfo.getHttpHeaders());

		JsonObject jsonObject = _jsonObjectBuilder.build();

		return Optional.of(jsonObject.toString());
	}

	/**
	 * Use instances of this builder to create {@link SingleModelWriter}
	 * instances.
	 *
	 * @review
	 */
	public static class Builder<T> {

		/**
		 * Add information about the single model being written to the builder.
		 *
		 * @param  singleModel the single model being written
		 * @return the updated builder
		 */
		public SingleModelMessageMapperStep singleModel(
			SingleModel<T> singleModel) {

			_singleModel = singleModel;

			return new SingleModelMessageMapperStep();
		}

		public class BuildStep {

			/**
			 * Constructs and returns a {@link SingleModelWriter} instance with
			 * the information provided to the builder.
			 *
			 * @return the {@code SingleModelWriter} instance
			 * @review
			 */
			public SingleModelWriter<T> build() {
				return new SingleModelWriter<>(Builder.this);
			}

		}

		public class PathFunctionStep {

			/**
			 * Add information to the builder about the function that can be
			 * used to convert an {@code Identifier} into a {@code Path}.
			 *
			 * @param  pathFunction the function to map an {@code Identifier}
			 *         into a {@code Path}
			 * @return the updated builder.
			 * @review
			 */
			public ResourceNameFunctionStep pathFunction(
				PathFunction pathFunction) {

				_pathFunction = pathFunction;

				return new ResourceNameFunctionStep();
			}

		}

		public class RepresentorFunctionStep {

			/**
			 * Add information to the builder about the function that can be
			 * used to obtain the {@code Representor} of a certain class.
			 *
			 * @param  representorFunction the function to obtain the {@code
			 *         Representor} of a class
			 * @return the updated builder.
			 * @review
			 */
			public RequestInfoStep representorFunction(
				RepresentorFunction representorFunction) {

				_representorFunction = representorFunction;

				return new RequestInfoStep();
			}

		}

		public class RequestInfoStep {

			/**
			 * Add information about the request info to the builder.
			 *
			 * @param  requestInfo the information obtained from the request. It
			 *         can be created by using a {@link RequestInfo.Builder}
			 * @return the updated builder.
			 * @review
			 */
			public BuildStep requestInfo(RequestInfo requestInfo) {
				_requestInfo = requestInfo;

				return new BuildStep();
			}

		}

		public class ResourceNameFunctionStep {

			/**
			 * Add information to the builder about the function that can be
			 * used to obtain the name of the {@code Representor} of a certain
			 * class name.
			 *
			 * @param  resourceNameFunction the function to obtain the name of
			 *         the {@code Representor} of a certain class name
			 * @return the updated builder.
			 * @review
			 */
			public RepresentorFunctionStep resourceNameFunction(
				ResourceNameFunction resourceNameFunction) {

				_resourceNameFunction = resourceNameFunction;

				return new RepresentorFunctionStep();
			}

		}

		public class SingleModelMessageMapperStep {

			/**
			 * Add information about the {@code SingleModelMessageMapper} to the
			 * builder.
			 *
			 * @param  singleModelMessageMapper the {@code
			 *         SingleModelMessageMapper} headers.
			 * @return the updated builder.
			 * @review
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
		private SingleModelMessageMapper<T> _singleModelMessageMapper;

	}

	private <V> void _writeEmbeddedModelFields(
		SingleModel<V> singleModel,
		FunctionalList<String> embeddedPathElements) {

		Optional<FieldsWriter<V, Identifier>> optional = getFieldsWriter(
			singleModel, embeddedPathElements, _requestInfo, _pathFunction,
			_representorFunction);

		if (!optional.isPresent()) {
			return;
		}

		FieldsWriter<V, Identifier> fieldsWriter = optional.get();

		fieldsWriter.writeBooleanFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceBooleanField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceNumberField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeStringFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
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

		fieldsWriter.writeEmbeddedRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction, _representorFunction),
			this::_writeEmbeddedModelFields,
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL),
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapEmbeddedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL));

		fieldsWriter.writeLinkedRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction, _representorFunction),
			(url, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements, url));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					_jsonObjectBuilder, resourceEmbeddedPathElements, url));
	}

	private final JSONObjectBuilder _jsonObjectBuilder;
	private final PathFunction _pathFunction;
	private final RepresentorFunction _representorFunction;
	private final RequestInfo _requestInfo;
	private final ResourceNameFunction _resourceNameFunction;
	private final SingleModel<T> _singleModel;
	private final SingleModelMessageMapper<T> _singleModelMessageMapper;

}