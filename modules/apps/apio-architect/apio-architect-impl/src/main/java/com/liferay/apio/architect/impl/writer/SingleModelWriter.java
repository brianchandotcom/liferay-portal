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

package com.liferay.apio.architect.impl.writer;

import static com.liferay.apio.architect.impl.url.URLCreator.createFormURL;
import static com.liferay.apio.architect.impl.writer.util.WriterUtil.getFieldsWriter;
import static com.liferay.apio.architect.impl.writer.util.WriterUtil.getPathOptional;

import com.liferay.apio.architect.alias.representor.NestedListFieldFunction;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.impl.alias.BaseRepresentorFunction;
import com.liferay.apio.architect.impl.alias.PathFunction;
import com.liferay.apio.architect.impl.alias.RepresentorFunction;
import com.liferay.apio.architect.impl.alias.ResourceNameFunction;
import com.liferay.apio.architect.impl.alias.SingleModelFunction;
import com.liferay.apio.architect.impl.list.FunctionalList;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.impl.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.impl.request.RequestInfo;
import com.liferay.apio.architect.impl.single.model.SingleModelImpl;
import com.liferay.apio.architect.impl.unsafe.Unsafe;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.representor.BaseRepresentor;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

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
	 * com.liferay.apio.architect.representor.Representor}. If no {@code
	 * Representor} or {@code Path} exists for the model, this method returns
	 * {@code Optional#empty()}.
	 *
	 * @return the string representation of the {@code SingleModel}, if the
	 *         model's {@code Representor} and {@code Path} exist; returns
	 *         {@code Optional#empty()} otherwise
	 */
	public Optional<String> write() {
		Optional<Path> pathOptional = getPathOptional(
			_singleModel, _pathFunction, _representorFunction::apply);

		if (!pathOptional.isPresent()) {
			return Optional.empty();
		}

		Optional<FieldsWriter<T>> fieldsWriterOptional = getFieldsWriter(
			_singleModel, null, _requestInfo, _representorFunction::apply,
			_singleModelFunction, pathOptional.get());

		if (!fieldsWriterOptional.isPresent()) {
			return Optional.empty();
		}

		FieldsWriter<T> fieldsWriter = fieldsWriterOptional.get();

		_writeBasicFields(fieldsWriter, _jsonObjectBuilder);

		fieldsWriter.writeSingleURL(
			url -> _singleModelMessageMapper.mapSelfURL(
				_jsonObjectBuilder, url));

		List<Operation> operations = _singleModel.getOperations();

		OperationWriter operationWriter = new OperationWriter(
			_singleModelMessageMapper, _requestInfo, _jsonObjectBuilder);

		operations.forEach(operationWriter::write);

		fieldsWriter.writeRelatedModels(
			singleModel -> getPathOptional(
				singleModel, _pathFunction, _representorFunction::apply),
			(singleModel, embeddedPathElements) -> writeEmbeddedModelFields(
				singleModel, _jsonObjectBuilder, embeddedPathElements),
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

		fieldsWriter.writeNestedResources(
			_representorFunction::apply, _singleModel, null,
			(nestedSingleModel, nestedPathElements, nestedRepresentorFunction)
				-> writeEmbeddedModelFields(
				nestedSingleModel, _jsonObjectBuilder, nestedPathElements,
				nestedRepresentorFunction));

		fieldsWriter.writeNestedLists(
			_representorFunction::apply, _singleModel,
			(nestedListFieldFunction, list) -> _writeNestedList(
				nestedListFieldFunction, list, _jsonObjectBuilder, null));

		_singleModelMessageMapper.onFinish(_jsonObjectBuilder, _singleModel);

		return Optional.of(_jsonObjectBuilder.build());
	}

	public <S> void writeEmbeddedModelFields(
		SingleModel<S> singleModel, JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements) {

		writeEmbeddedModelFields(
			singleModel, jsonObjectBuilder, embeddedPathElements,
			_representorFunction::apply);
	}

	/**
	 * Writes a related {@link SingleModel} with the {@link
	 * SingleModelMessageMapper}. This method uses a {@link FieldsWriter} to
	 * write the different fields of its {@link
	 * com.liferay.apio.architect.representor.Representor}. If no {@code
	 * Representor} or {@link Path} exists for the model, this method doesn't do
	 * anything.
	 *
	 * @param singleModel the {@code SingleModel} to write
	 * @param embeddedPathElements the related model's embedded path elements
	 */
	public <S> void writeEmbeddedModelFields(
		SingleModel<S> singleModel, JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements,
		BaseRepresentorFunction baseRepresentorFunction) {

		Optional<Path> pathOptional = getPathOptional(
			singleModel, _pathFunction, baseRepresentorFunction,
			_representorFunction, _singleModel);

		if (!pathOptional.isPresent()) {
			return;
		}

		Optional<FieldsWriter<S>> fieldsWriterOptional = getFieldsWriter(
			singleModel, embeddedPathElements, _requestInfo,
			baseRepresentorFunction, _singleModelFunction, pathOptional.get());

		if (!fieldsWriterOptional.isPresent()) {
			return;
		}

		FieldsWriter<S> fieldsWriter = fieldsWriterOptional.get();

		_writeEmbeddedBasicFields(
			fieldsWriter, jsonObjectBuilder, embeddedPathElements);

		List<Operation> operations = singleModel.getOperations();

		operations.forEach(
			operation -> {
				JSONObjectBuilder operationJSONObjectBuilder =
					new JSONObjectBuilder();

				Optional<Form> formOptional = operation.getFormOptional();

				formOptional.ifPresent(
					form -> {
						String url = createFormURL(
							_requestInfo.getApplicationURL(), form);

						_singleModelMessageMapper.mapEmbeddedOperationFormURL(
							jsonObjectBuilder, operationJSONObjectBuilder,
							embeddedPathElements, url);
					});

				_singleModelMessageMapper.mapEmbeddedOperationMethod(
					jsonObjectBuilder, operationJSONObjectBuilder,
					embeddedPathElements, operation.getHttpMethod());

				_singleModelMessageMapper.onFinishEmbeddedOperation(
					jsonObjectBuilder, operationJSONObjectBuilder,
					embeddedPathElements, operation);
			});

		fieldsWriter.writeRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction,
				_representorFunction::apply),
			(singleModel1, stringFunctionalList) -> writeEmbeddedModelFields(
				singleModel1, jsonObjectBuilder, stringFunctionalList),
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					jsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL),
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapEmbeddedResourceURL(
					jsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					jsonObjectBuilder, resourceEmbeddedPathElements, url));

		fieldsWriter.writeNestedResources(
			baseRepresentorFunction, singleModel, embeddedPathElements,
			(nestedSingleModel, nestedPathElements, nestedRepresentorFunction)
				-> writeEmbeddedModelFields(
				nestedSingleModel, jsonObjectBuilder, nestedPathElements,
				nestedRepresentorFunction));

		fieldsWriter.writeNestedLists(
			baseRepresentorFunction, singleModel,
			(nestedListFieldFunction, list) -> _writeNestedList(
				nestedListFieldFunction, list, _jsonObjectBuilder,
				embeddedPathElements));
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
			 * an identifier to a {@link Path}.
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
			 * class's {@link
			 * com.liferay.apio.architect.representor.Representor}.
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

	private Consumer<BaseRepresentor> _mapPageSemantics(
		JSONObjectBuilder jsonObjectBuilder) {

		return baseRepresentor -> {
			String type = baseRepresentor.getPrimaryType();

			_singleModelMessageMapper.mapNestedPageSemantics(
				jsonObjectBuilder, type);
		};
	}

	private void _writeBasicFields(
		FieldsWriter<?> fieldsWriter, JSONObjectBuilder jsonObjectBuilder) {

		fieldsWriter.writeApplicationRelativeURLFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeBooleanFields(
			(field, value) -> _singleModelMessageMapper.mapBooleanField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeBooleanListFields(
			(field, value) -> _singleModelMessageMapper.mapBooleanListField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) -> _singleModelMessageMapper.mapNumberField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeNumberListFields(
			(field, value) -> _singleModelMessageMapper.mapNumberListField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeRelativeURLFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeStringFields(
			(field, value) -> _singleModelMessageMapper.mapStringField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeStringListFields(
			(field, value) -> _singleModelMessageMapper.mapStringListField(
				jsonObjectBuilder, field, value));

		fieldsWriter.writeLinks(
			(fieldName, link) -> _singleModelMessageMapper.mapLink(
				jsonObjectBuilder, fieldName, link));

		fieldsWriter.writeTypes(
			types -> _singleModelMessageMapper.mapTypes(
				jsonObjectBuilder, types));

		fieldsWriter.writeBinaries(
			(field, value) -> _singleModelMessageMapper.mapLink(
				jsonObjectBuilder, field, value));
	}

	private void _writeEmbeddedBasicFields(
		FieldsWriter<?> fieldsWriter, JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements) {

		fieldsWriter.writeApplicationRelativeURLFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeBooleanFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceBooleanField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeBooleanListFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceBooleanListField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceNumberField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeNumberListFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceNumberListField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeRelativeURLFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					_jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeStringFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeStringListFields(
			(field, value) ->
				_singleModelMessageMapper.mapEmbeddedResourceStringListField(
					jsonObjectBuilder, embeddedPathElements, field, value));

		fieldsWriter.writeLinks(
			(fieldName, link) -> _singleModelMessageMapper.
				mapEmbeddedResourceLink(
					jsonObjectBuilder, embeddedPathElements, fieldName, link));

		fieldsWriter.writeTypes(
			types -> _singleModelMessageMapper.mapEmbeddedResourceTypes(
				jsonObjectBuilder, embeddedPathElements, types));

		fieldsWriter.writeBinaries(
			(field, value) -> _singleModelMessageMapper.mapEmbeddedResourceLink(
				jsonObjectBuilder, embeddedPathElements, field, value));
	}

	private <U> void _writeItem(
		JSONObjectBuilder collectionJSONObjectBuilder,
		SingleModel<U> singleModel, FunctionalList<String> embeddedPathElements,
		BaseRepresentorFunction baseRepresentorFunction) {

		Optional<Path> pathOptional = getPathOptional(
			singleModel, _pathFunction, baseRepresentorFunction,
			_representorFunction, _singleModel);

		if (!pathOptional.isPresent()) {
			return;
		}

		Optional<FieldsWriter<U>> fieldsWriterOptional = getFieldsWriter(
			singleModel, embeddedPathElements, _requestInfo,
			baseRepresentorFunction, _singleModelFunction, pathOptional.get());

		if (!fieldsWriterOptional.isPresent()) {
			return;
		}

		FieldsWriter<U> fieldsWriter = fieldsWriterOptional.get();

		JSONObjectBuilder itemJsonObjectBuilder = new JSONObjectBuilder();

		_writeBasicFields(fieldsWriter, itemJsonObjectBuilder);

		Optional<FieldsWriter<U>> relatedModelsFieldsWriterOptional =
			getFieldsWriter(
				singleModel, null, _requestInfo, baseRepresentorFunction,
				_singleModelFunction, pathOptional.get());

		relatedModelsFieldsWriterOptional.ifPresent(
			relatedModelFieldsWriter ->
				relatedModelFieldsWriter.writeRelatedModels(
					embeddedSingleModel -> getPathOptional(
						embeddedSingleModel, _pathFunction,
						_representorFunction::apply),
					(embeddedSingleModel, embeddedPathElements1) ->
						_writeItemEmbeddedModelFields(
							embeddedSingleModel, embeddedPathElements1,
							itemJsonObjectBuilder, baseRepresentorFunction),
					(resourceURL, embeddedPathElements1) ->
						_singleModelMessageMapper.mapLinkedResourceURL(
							itemJsonObjectBuilder, embeddedPathElements1,
							resourceURL),
					(resourceURL, embeddedPathElements1) ->
						_singleModelMessageMapper.mapEmbeddedResourceURL(
							itemJsonObjectBuilder, embeddedPathElements1,
							resourceURL)));

		_writePageNestedResources(
			baseRepresentorFunction, singleModel, itemJsonObjectBuilder);

		fieldsWriter.writeNestedLists(
			baseRepresentorFunction, singleModel,
			(nestedListFieldFunction, list) -> _writeNestedList(
				nestedListFieldFunction, list, _jsonObjectBuilder, null));

		_singleModelMessageMapper.onFinishNestedCollectionItem(
			collectionJSONObjectBuilder, itemJsonObjectBuilder, singleModel);
	}

	private <S> void _writeItemEmbeddedModelFields(
		SingleModel<S> singleModel, FunctionalList<String> embeddedPathElements,
		JSONObjectBuilder itemJsonObjectBuilder,
		BaseRepresentorFunction baseRepresentorFunction) {

		Optional<Path> pathOptional = getPathOptional(
			singleModel, _pathFunction, baseRepresentorFunction,
			_representorFunction, _singleModel);

		if (!pathOptional.isPresent()) {
			return;
		}

		Optional<FieldsWriter<S>> fieldsWriterOptional = getFieldsWriter(
			singleModel, embeddedPathElements, _requestInfo,
			baseRepresentorFunction, _singleModelFunction, pathOptional.get());

		if (!fieldsWriterOptional.isPresent()) {
			return;
		}

		FieldsWriter<S> fieldsWriter = fieldsWriterOptional.get();

		_writeEmbeddedBasicFields(
			fieldsWriter, itemJsonObjectBuilder, embeddedPathElements);

		fieldsWriter.writeRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction,
				_representorFunction::apply),
			(embeddedSingleModel, embeddedModelEmbeddedPathElements) ->
				_writeItemEmbeddedModelFields(
					embeddedSingleModel, embeddedModelEmbeddedPathElements,
					itemJsonObjectBuilder, baseRepresentorFunction),
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapLinkedResourceURL(
					itemJsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL),
			(resourceURL, resourceEmbeddedPathElements) ->
				_singleModelMessageMapper.mapEmbeddedResourceURL(
					itemJsonObjectBuilder, resourceEmbeddedPathElements,
					resourceURL));

		fieldsWriter.writeNestedResources(
			baseRepresentorFunction, singleModel, embeddedPathElements,
			(nestedSingleModel, nestedPathElements, nestedRepresentorFunction)
				-> writeEmbeddedModelFields(
				nestedSingleModel, itemJsonObjectBuilder, nestedPathElements,
				nestedRepresentorFunction));

		fieldsWriter.writeNestedLists(
			baseRepresentorFunction, singleModel,
			(nestedListFieldFunction, list) -> _writeNestedList(
				nestedListFieldFunction, list, itemJsonObjectBuilder,
				embeddedPathElements));
	}

	private <U> void _writeNestedList(
		NestedListFieldFunction nestedListFieldFunction, List<U> list,
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedNestedPathElements) {

		String key = nestedListFieldFunction.getKey();

		FunctionalList<String> embeddedPathElements = new FunctionalList<>(
			embeddedNestedPathElements, key);

		BaseRepresentorFunction baseRepresentorFunction =
			__ -> (Optional)Optional.of(
				nestedListFieldFunction.getNestedRepresentor());

		JSONObjectBuilder pageJSONObjectBuilder = new JSONObjectBuilder();

		_singleModelMessageMapper.mapNestedPageItemTotalCount(
			pageJSONObjectBuilder, list.size());

		baseRepresentorFunction.apply(
			""
		).ifPresent(
			_mapPageSemantics(pageJSONObjectBuilder)
		);

		list.forEach(
			model -> _writeItem(
				pageJSONObjectBuilder,
				new SingleModelImpl<>(model, "", Collections.emptyList()),
				embeddedPathElements, baseRepresentorFunction));

		_singleModelMessageMapper.onFinishNestedCollection(
			jsonObjectBuilder, pageJSONObjectBuilder, key, list,
			embeddedPathElements);
	}

	private <U> void _writePageNestedResources(
		BaseRepresentorFunction baseRepresentorFunction,
		SingleModel<U> singleModel, JSONObjectBuilder itemJsonObjectBuilder) {

		baseRepresentorFunction.apply(
			singleModel.getResourceName()
		).<BaseRepresentor<U>>map(
			Unsafe::unsafeCast
		).map(
			BaseRepresentor::getNestedFieldFunctions
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).forEach(
			nestedFieldFunction -> {
				Object mappedModel = nestedFieldFunction.apply(
					singleModel.getModel());

				if (mappedModel == null) {
					return;
				}

				FunctionalList<String> embeddedNestedPathElements =
					new FunctionalList<>(null, nestedFieldFunction.getKey());

				_writeItemEmbeddedModelFields(
					new SingleModelImpl<>(
						mappedModel, "", Collections.emptyList()),
					embeddedNestedPathElements, itemJsonObjectBuilder,
					__ -> Optional.of(
						nestedFieldFunction.getNestedRepresentor()));
			}
		);
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