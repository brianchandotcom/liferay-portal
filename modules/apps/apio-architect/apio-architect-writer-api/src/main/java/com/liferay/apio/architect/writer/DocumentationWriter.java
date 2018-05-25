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

import static com.liferay.apio.architect.operation.Method.DELETE;
import static com.liferay.apio.architect.operation.Method.GET;
import static com.liferay.apio.architect.operation.Method.POST;
import static com.liferay.apio.architect.operation.Method.PUT;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.FormField;
import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.operation.Method;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.representor.BaseRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.representor.function.FieldFunction;
import com.liferay.apio.architect.representor.function.NestedFieldFunction;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Writes the documentation.
 *
 * @author Alejandro Hernández
 */
public class DocumentationWriter {

	/**
	 * Creates a new {@code DocumentationWriter} object, without creating the
	 * builder.
	 *
	 * @param  function the function that transforms a builder into the {@code
	 *         DocumentationWriter}
	 * @return the {@code DocumentationWriter} instance
	 */
	public static DocumentationWriter create(
		Function<Builder, DocumentationWriter> function) {

		return function.apply(new Builder());
	}

	public DocumentationWriter(Builder builder) {
		_documentation = builder._documentation;
		_documentationMessageMapper = builder._documentationMessageMapper;
		_requestInfo = builder._requestInfo;
	}

	/**
	 * Writes the {@link Documentation} to a string.
	 *
	 * @return the JSON representation of the {@code Documentation}
	 */
	public String write() {
		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

		_documentationMessageMapper.onStart(
			jsonObjectBuilder, _documentation, _requestInfo.getHttpHeaders());

		_writeDocumentationMetadata(jsonObjectBuilder);

		Map<String, Representor> representors =
			_documentation.getRepresentors();

		Map<String, ItemRoutes> itemRoutesMap = _documentation.getItemRoutes();

		itemRoutesMap.forEach(
			(name, itemRoutes) -> _writeRoute(
				jsonObjectBuilder, name, representors.get(name),
				_documentationMessageMapper::mapResource,
				this::_writeItemOperations,
				resourceJsonObjectBuilder -> _writeAllFields(
					representors.get(name), resourceJsonObjectBuilder,
					itemRoutes.getFormOptional())));

		Map<String, NestedCollectionRoutes> nestedCollectionRoutesMap =
			_documentation.getNestedCollectionRoutes();

		Map<String, CollectionRoutes> collectionRoutes =
			_documentation.getCollectionRoutes();

		Set<String> collectionResources = new HashSet<>(
			collectionRoutes.keySet());

		Set<String> nestedRoutes = new HashSet<>(itemRoutesMap.keySet());

		nestedRoutes.addAll(collectionRoutes.keySet());

		nestedRoutes.forEach(
			name -> {
				Optional<String> nestedCollectionRoute =
					_getNestedCollectionRouteOptional(
						representors, nestedCollectionRoutesMap, name);

				nestedCollectionRoute.ifPresent(collectionResources::add);
			});

		collectionResources.forEach(
			name -> _writeRoute(
				jsonObjectBuilder, name, representors.get(name),
				_documentationMessageMapper::mapResourceCollection,
				this::_writePageOperations,
				__ -> {
				}));

		_documentationMessageMapper.onFinish(
			jsonObjectBuilder, _documentation, _requestInfo.getHttpHeaders());

		JsonObject jsonObject = jsonObjectBuilder.build();

		return jsonObject.toString();
	}

	/**
	 * Creates {@code DocumentationWriter} instances.
	 */
	public static class Builder {

		/**
		 * Add information about the documentation being written to the builder.
		 *
		 * @param  documentation the documentation being written
		 * @return the updated builder
		 */
		public DocumentationMessageMapperStep documentation(
			Documentation documentation) {

			_documentation = documentation;

			return new DocumentationMessageMapperStep();
		}

		public class BuildStep {

			/**
			 * Constructs and returns a {@code DocumentationWriter} instance
			 * with the information provided to the builder.
			 *
			 * @return the {@code DocumentationWriter} instance
			 */
			public DocumentationWriter build() {
				return new DocumentationWriter(Builder.this);
			}

		}

		public class DocumentationMessageMapperStep {

			/**
			 * Adds information to the builder about the {@link
			 * DocumentationMessageMapper}.
			 *
			 * @param  documentationMessageMapper the {@code
			 *         DocumentationMessageMapper}
			 * @return the updated builder
			 */
			public RequestInfoStep documentationMessageMapper(
				DocumentationMessageMapper documentationMessageMapper) {

				_documentationMessageMapper = documentationMessageMapper;

				return new RequestInfoStep();
			}

		}

		public class RequestInfoStep {

			/**
			 * Adds information to the builder about the request.
			 *
			 * @param  requestInfo the information obtained from the request. It
			 *         can be created by using a {@link RequestInfo.Builder}.
			 * @return the updated builder
			 */
			public BuildStep requestInfo(RequestInfo requestInfo) {
				_requestInfo = requestInfo;

				return new BuildStep();
			}

		}

		private Documentation _documentation;
		private DocumentationMessageMapper _documentationMessageMapper;
		private RequestInfo _requestInfo;

	}

	private Stream<String> _calculateNestableFieldNames(
		BaseRepresentor representor) {

		List<FieldFunction> fieldFunctions = new ArrayList<>();

		fieldFunctions.addAll(representor.getBinaryFunctions());
		fieldFunctions.addAll(representor.getBooleanFunctions());
		fieldFunctions.addAll(representor.getBooleanListFunctions());
		fieldFunctions.addAll(representor.getLinkFunctions());
		fieldFunctions.addAll(representor.getLocalizedStringFunctions());
		fieldFunctions.addAll(representor.getNestedFieldFunctions());
		fieldFunctions.addAll(representor.getNumberFunctions());
		fieldFunctions.addAll(representor.getNumberListFunctions());
		fieldFunctions.addAll(representor.getStringFunctions());
		fieldFunctions.addAll(representor.getStringListFunctions());

		Stream<FieldFunction> fieldFunctionStream = fieldFunctions.stream();

		Stream<String> fieldNamesStream = fieldFunctionStream.map(
			fieldFunction -> fieldFunction.key);

		List<RelatedModel> relatedModels = representor.getRelatedModels();

		Stream<RelatedModel> relatedModelStream = relatedModels.stream();

		Stream<String> relatedModelNamesStream = relatedModelStream.map(
			RelatedModel::getKey);

		fieldNamesStream = Stream.concat(
			fieldNamesStream, relatedModelNamesStream);

		List<NestedFieldFunction> nestedFieldFunctions =
			representor.getNestedFieldFunctions();

		Stream<NestedFieldFunction> nestedFieldFunctionStream =
			nestedFieldFunctions.stream();

		Stream<String> nestedFieldNamesStream = nestedFieldFunctionStream.map(
			nestedFieldFunction -> _calculateNestableFieldNames(
				nestedFieldFunction.nestedRepresentor)
		).reduce(
			Stream::concat
		).orElseGet(
			Stream::empty
		);

		return Stream.concat(fieldNamesStream, nestedFieldNamesStream);
	}

	private Optional<FormField> _getFormField(
		String fieldName, Form<FormField> formFieldForm) {

		List<FormField> formFields = formFieldForm.getFormFields();

		Stream<FormField> stream = formFields.stream();

		return stream.filter(
			formField -> formField.name.equals(fieldName)
		).findFirst();
	}

	private Optional<String> _getNestedCollectionRouteOptional(
		Map<String, Representor> representorMap, Map<String, ?> nestedRoutesMap,
		String name) {

		Set<String> nestedRoutes = nestedRoutesMap.keySet();

		Stream<String> nestedRoutesStream = nestedRoutes.stream();

		return nestedRoutesStream.map(
			nestedRoute -> nestedRoute.split(name + "-")
		).filter(
			routes ->
				routes.length == 2 && !routes[0].equals("") &&
				!routes[0].equals(routes[1]) &&
				representorMap.containsKey(routes[1])
		).map(
			routes -> routes[1]
		).findFirst();
	}

	private Operation _getOperation(
		String operationName, Optional<Form> formOptional, Method method) {

		return formOptional.map(
			form -> new Operation(form, method, operationName)
		).orElse(
			new Operation(method, operationName)
		);
	}

	private void _writeAllFields(
		Representor representor, JSONObjectBuilder resourceJsonObjectBuilder,
		Optional<Form<FormField>> formOptional) {

		Stream<String> fieldNamesStream = _calculateNestableFieldNames(
			representor);

		Stream<RelatedCollection> relatedCollections =
			representor.getRelatedCollections();

		Stream<String> relatedCollectionsNamesStream = relatedCollections.map(
			RelatedCollection::getKey);

		fieldNamesStream = Stream.concat(
			fieldNamesStream, relatedCollectionsNamesStream);

		_writeFields(fieldNamesStream, resourceJsonObjectBuilder, formOptional);
	}

	private void _writeDocumentationMetadata(
		JSONObjectBuilder jsonObjectBuilder) {

		Optional<String> apiTitleOptional =
			_documentation.getAPITitleOptional();

		apiTitleOptional.ifPresent(
			title -> _documentationMessageMapper.mapTitle(
				jsonObjectBuilder, title));

		Optional<String> apiDescriptionOptional =
			_documentation.getAPIDescriptionOptional();

		apiDescriptionOptional.ifPresent(
			description -> _documentationMessageMapper.mapDescription(
				jsonObjectBuilder, description));
	}

	private void _writeFields(
		Stream<String> fields, JSONObjectBuilder resourceJsonObjectBuilder,
		Optional<Form<FormField>> formOptional) {

		fields.distinct().forEach(
			field -> {
				Optional<FormField> formFieldOptional = formOptional.flatMap(
					formFieldForm -> _getFormField(field, formFieldForm));

				_writeFormField(
					resourceJsonObjectBuilder, field, formFieldOptional);
			});
	}

	private void _writeFormField(
		JSONObjectBuilder resourceJsonObjectBuilder, String fieldName,
		Optional<FormField> formField) {

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

		_documentationMessageMapper.onStartProperty(
			resourceJsonObjectBuilder, jsonObjectBuilder, fieldName);

		boolean required = formField.map(
			field -> field.required
		).orElse(
			false
		);

		_documentationMessageMapper.mapProperty(
			jsonObjectBuilder, fieldName, required);

		_documentationMessageMapper.onFinishProperty(
			resourceJsonObjectBuilder, jsonObjectBuilder, fieldName);
	}

	private void _writeItemOperations(
		String name, String type, JSONObjectBuilder resourceJsonObjectBuilder) {

		Map<String, ItemRoutes> itemRoutesMap = _documentation.getItemRoutes();

		Optional.ofNullable(
			itemRoutesMap.getOrDefault(name, null)
		).ifPresent(
			itemRoutes -> {
				String getOperationName = name + "/retrieve";

				Operation getOperation = new Operation(
					GET, getOperationName, false);

				_writeOperation(
					getOperation, resourceJsonObjectBuilder, name, type);

				String updateOperationName = name + "/update";

				Operation updateOperation = _getOperation(
					updateOperationName, itemRoutes.getFormOptional(), PUT);

				_writeOperation(
					updateOperation, resourceJsonObjectBuilder, name, type);

				String deleteOperationName = name + "/delete";

				Operation deleteOperation = new Operation(
					DELETE, deleteOperationName);

				_writeOperation(
					deleteOperation, resourceJsonObjectBuilder, name, type);
			}
		);
	}

	private void _writeOperation(
		Operation operation, JSONObjectBuilder jsonObjectBuilder,
		String resourceName, String type) {

		JSONObjectBuilder operationJsonObjectBuilder = new JSONObjectBuilder();

		_documentationMessageMapper.onStartOperation(
			jsonObjectBuilder, operationJsonObjectBuilder, operation);

		_documentationMessageMapper.mapOperation(
			operationJsonObjectBuilder, resourceName, type, operation);

		_documentationMessageMapper.onFinishOperation(
			jsonObjectBuilder, operationJsonObjectBuilder, operation);
	}

	private void _writePageOperations(
		String resource, String type,
		JSONObjectBuilder resourceJsonObjectBuilder) {

		Map<String, CollectionRoutes> collectionRoutesMap =
			_documentation.getCollectionRoutes();

		Optional.ofNullable(
			collectionRoutesMap.getOrDefault(resource, null)
		).ifPresent(
			collectionRoutes -> {
				_writeOperation(
					new Operation(GET, resource, true),
					resourceJsonObjectBuilder, resource, type);

				String operationName = resource + "/create";

				Operation createOperation = _getOperation(
					operationName, collectionRoutes.getFormOptional(), POST);

				_writeOperation(
					createOperation, resourceJsonObjectBuilder, resource, type);
			}
		);
	}

	private void _writeRoute(
		JSONObjectBuilder jsonObjectBuilder, String name,
		Representor representor,
		BiConsumer<JSONObjectBuilder, String> writeResourceBiConsumer,
		TriConsumer<String, String, JSONObjectBuilder>
			writeOperationsTriConsumer,
		Consumer<JSONObjectBuilder> writeFieldsRepresentor) {

		List<String> types = representor.getTypes();

		types.forEach(
			type -> {
				JSONObjectBuilder resourceJsonObjectBuilder =
					new JSONObjectBuilder();

				_documentationMessageMapper.onStartResource(
					jsonObjectBuilder, resourceJsonObjectBuilder, type);

				writeResourceBiConsumer.accept(resourceJsonObjectBuilder, type);

				writeOperationsTriConsumer.accept(
					name, type, resourceJsonObjectBuilder);

				writeFieldsRepresentor.accept(resourceJsonObjectBuilder);

				_documentationMessageMapper.onFinishResource(
					jsonObjectBuilder, resourceJsonObjectBuilder, type);
			});
	}

	private final Documentation _documentation;
	private final DocumentationMessageMapper _documentationMessageMapper;
	private final RequestInfo _requestInfo;

}