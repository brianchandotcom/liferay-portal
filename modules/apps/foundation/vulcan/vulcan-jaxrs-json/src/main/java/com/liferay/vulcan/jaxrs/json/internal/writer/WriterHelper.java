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

package com.liferay.vulcan.jaxrs.json.internal.writer;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.google.gson.JsonObject;

import com.liferay.vulcan.alias.BinaryFunction;
import com.liferay.vulcan.consumer.TriConsumer;
import com.liferay.vulcan.jaxrs.json.internal.JSONObjectBuilderImpl;
import com.liferay.vulcan.jaxrs.json.internal.StringFunctionalList;
import com.liferay.vulcan.language.Language;
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.json.ErrorMessageMapper;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.provider.ServerURLProvider;
import com.liferay.vulcan.resource.RelatedCollection;
import com.liferay.vulcan.resource.RelatedModel;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.result.APIError;
import com.liferay.vulcan.uri.CollectionResourceURITransformer;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.wiring.osgi.manager.CollectionResourceManager;
import com.liferay.vulcan.wiring.osgi.manager.PathIdentifierMapperManager;

import java.net.URI;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to help {@code javax.ws.rs.ext.MessageBodyWriter} write
 * hypermedia resources.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = WriterHelper.class)
public class WriterHelper {

	/**
	 * Writes an {@link APIError} to a JSON object.
	 *
	 * @param  errorMessageMapper the {@link ErrorMessageMapper} that matches
	 *         the {@code apiError} and {@code httpHeaders} parameters
	 * @param  apiError the API error
	 * @param  httpHeaders the current request's HTTP headers
	 * @return the API error, as a JSON string
	 */
	public static String writeError(
		ErrorMessageMapper errorMessageMapper, APIError apiError,
		HttpHeaders httpHeaders) {

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilderImpl();

		errorMessageMapper.onStart(jsonObjectBuilder, apiError, httpHeaders);

		Optional<String> optional = apiError.getDescription();

		optional.ifPresent(
			description -> errorMessageMapper.mapDescription(
				jsonObjectBuilder, description));

		errorMessageMapper.mapStatusCode(
			jsonObjectBuilder, apiError.getStatusCode());
		errorMessageMapper.mapTitle(jsonObjectBuilder, apiError.getTitle());
		errorMessageMapper.mapType(jsonObjectBuilder, apiError.getType());
		errorMessageMapper.onFinish(jsonObjectBuilder, apiError, httpHeaders);

		JsonObject jsonObject = jsonObjectBuilder.build();

		return jsonObject.toString();
	}

	/**
	 * Returns the absolute URL from a relative URI.
	 *
	 * @param  httpServletRequest the HTTP servlet request
	 * @param  relativeURI the relative URI
	 * @return the absolute URL
	 */
	public String getAbsoluteURL(
		HttpServletRequest httpServletRequest, String relativeURI) {

		String serverURL = _serverURLProvider.getServerURL(httpServletRequest);

		UriBuilder uriBuilder = UriBuilder.fromPath(serverURL);

		uriBuilder = uriBuilder.clone();

		uriBuilder.path(relativeURI);

		URI uri = uriBuilder.build();

		return uri.toString();
	}

	/**
	 * Returns the page collection URL, if a {@link
	 * com.liferay.vulcan.resource.CollectionResource} for the model class
	 * exists. Otherwise, this method returns {@code Optional#empty()}.
	 *
	 * @param  page the page of the collection resource's collection
	 * @param  httpServletRequest the HTTP servlet request
	 * @return the page collection URL, if a collection resource for the model
	 *         class exists; {@code Optional#empty()} otherwise
	 */
	public <T> Optional<String> getCollectionURLOptional(
		Page<T> page, HttpServletRequest httpServletRequest) {

		Path path = page.getPath();

		String pathString = "/p" + path.asURI() + "/";

		Class<T> modelClass = page.getModelClass();

		Optional<String> optional = _collectionResourceManager.getNameOptional(
			modelClass.getName());

		return optional.map(
			pathString::concat
		).map(
			_getTransformURIFunction(
				(uri, transformer) -> transformer.transformPageURI(uri, page))
		).map(
			uri -> getAbsoluteURL(httpServletRequest, uri)
		);
	}

	/**
	 * Returns the model's resource URL, if a @link
	 * com.liferay.vulcan.resource.CollectionResource} for the model class
	 * exists. Otherwise, this method returns {@code Optional#empty()}.
	 *
	 * @param  singleModel the single model
	 * @param  httpServletRequest the HTTP servlet request
	 * @return the model's resource URL, if a collection resource for the model
	 *         class exists; {@code Optional#empty()} otherwise
	 */
	public <T> Optional<String> getSingleURLOptional(
		SingleModel<T> singleModel, HttpServletRequest httpServletRequest) {

		Class<T> modelClass = singleModel.getModelClass();

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		return optional.flatMap(
			representor -> {
				Identifier identifier = representor.getIdentifier(
					singleModel.getModel());

				Class<Identifier> identifierClass =
					representor.getIdentifierClass();

				return _pathIdentifierMapperManager.map(
					identifier, identifierClass, modelClass);
			}
		).map(
			Path::asURI
		).map(
			"/p/"::concat
		).map(
			_getTransformURIFunction(
				(uri, transformer) ->
					transformer.transformCollectionItemSingleResourceURI(
						uri, singleModel))
		).map(
			uri -> getAbsoluteURL(httpServletRequest, uri)
		);
	}

	/**
	 * Writes binary resources. This method uses a {@code BiConsumer} so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write each binary
	 * differently.
	 *
	 * @param binaryFunctions the functions that obtain the binaries
	 * @param singleModel the single model
	 * @param httpServletRequest the HTTP request
	 * @param biConsumer the {@code BiConsumer} called to write each binary
	 */
	public <T> void writeBinaries(
		Map<String, BinaryFunction<T>> binaryFunctions,
		SingleModel<T> singleModel, HttpServletRequest httpServletRequest,
		BiConsumer<String, String> biConsumer) {

		Class<T> modelClass = singleModel.getModelClass();

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.flatMap(
			representor -> {
				Identifier identifier = representor.getIdentifier(
					singleModel.getModel());

				Class<Identifier> identifierClass =
					representor.getIdentifierClass();

				return _pathIdentifierMapperManager.map(
					identifier, identifierClass, modelClass);
			}
		).map(
			Path::asURI
		).map(
			"/b/"::concat
		).ifPresent(
			resourceURI -> {
				for (String binaryId : binaryFunctions.keySet()) {
					String binaryURI = resourceURI + binaryId;

					Function<String, String> transformURIFunction =
						_getTransformURIFunction(
							(uri, transformer) ->
								transformer.transformBinaryURI(
									uri, singleModel, binaryId));

					String transformedURI = transformURIFunction.apply(
						binaryURI);

					String url = getAbsoluteURL(
						httpServletRequest, transformedURI);

					biConsumer.accept(binaryId, url);
				}
			}
		);
	}

	/**
	 * Writes the model's boolean fields. This method uses a {@code BiConsumer}
	 * so each {@code javax.ws.rs.ext.MessageBodyWriter} can write each field
	 * differently.
	 *
	 * @param model the model
	 * @param modelClass the model class
	 * @param fields the requested fields
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public <T> void writeBooleanFields(
		T model, Class<T> modelClass, Fields fields,
		BiConsumer<String, Boolean> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.map(
			Representor::getBooleanFunctions
		).map(
			Map::entrySet
		).map(
			Set::stream
		).ifPresent(
			stream -> stream.filter(
				entry -> fieldsPredicate.test(entry.getKey())
			).forEach(
				entry -> {
					Function<T, Boolean> fieldFunction = entry.getValue();

					Boolean data = fieldFunction.apply(model);

					if (data != null) {
						biConsumer.accept(entry.getKey(), data);
					}
				}
			)
		);
	}

	/**
	 * Writes a linked model. This method uses a {@code BiConsumer} so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param relatedModel the related model
	 * @param parentSingleModel the parent model
	 * @param parentEmbeddedPathElements the list of embedded path elements
	 * @param httpServletRequest the HTTP servlet request
	 * @param fields the requested fields
	 * @param embedded the information about the embedded resources
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public <T, U> void writeLinkedRelatedModel(
		RelatedModel<T, U> relatedModel, SingleModel<T> parentSingleModel,
		FunctionalList<String> parentEmbeddedPathElements,
		HttpServletRequest httpServletRequest, Fields fields, Embedded embedded,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		BiConsumer<SingleModel<U>, FunctionalList<String>> emptyConsumer =
			(singleModel, embeddedPathElements) -> {
			};

		writeRelatedModel(
			relatedModel, parentSingleModel, parentEmbeddedPathElements,
			httpServletRequest, fields, embedded, emptyConsumer,
			(url, embeddedPathElements, isEmbedded) -> biConsumer.accept(
				url, embeddedPathElements));
	}

	/**
	 * Writes the model's links. This method uses a {@code BiConsumer} so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write each link
	 * differently.
	 *
	 * @param modelClass the model class
	 * @param fields the requested fields
	 * @param biConsumer the {@code BiConsumer} called to write each link
	 */
	public <T> void writeLinks(
		Class<T> modelClass, Fields fields,
		BiConsumer<String, String> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.map(
			Representor::getLinks
		).map(
			Map::entrySet
		).map(
			Set::stream
		).ifPresent(
			stream -> stream.filter(
				entry -> fieldsPredicate.test(entry.getKey())
			).forEach(
				entry -> {
					String link = entry.getValue();

					if (link != null) {
						biConsumer.accept(entry.getKey(), link);
					}
				}
			)
		);
	}

	/**
	 * Writes a model's localized string fields. This method uses a {@code
	 * BiConsumer} so each {@code javax.ws.rs.ext.MessageBodyWriter} can write
	 * each field differently.
	 *
	 * @param model the model
	 * @param modelClass the model class
	 * @param fields the requested fields
	 * @param language the language requested by the user
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public <T> void writeLocalizedStringFields(
		T model, Class<T> modelClass, Fields fields, Language language,
		BiConsumer<String, String> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.map(
			Representor::getLocalizedStringFunctions
		).map(
			Map::entrySet
		).map(
			Set::stream
		).ifPresent(
			stream -> stream.filter(
				entry -> fieldsPredicate.test(entry.getKey())
			).forEach(
				entry -> {
					BiFunction<T, Language, String> fieldFunction =
						entry.getValue();

					String data = fieldFunction.apply(model, language);

					if ((data != null) && !data.isEmpty()) {
						biConsumer.accept(entry.getKey(), data);
					}
				}
			)
		);
	}

	/**
	 * Writes a model's number fields. This method uses a {@code BiConsumer} so
	 * each {@code javax.ws.rs.ext.MessageBodyWriter} can write each field
	 * differently.
	 *
	 * @param model the model
	 * @param modelClass the model class
	 * @param fields the requested fields
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public <T> void writeNumberFields(
		T model, Class<T> modelClass, Fields fields,
		BiConsumer<String, Number> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.map(
			Representor::getNumberFunctions
		).map(
			Map::entrySet
		).map(
			Set::stream
		).ifPresent(
			stream -> stream.filter(
				entry -> fieldsPredicate.test(entry.getKey())
			).forEach(
				entry -> {
					Function<T, Number> fieldFunction = entry.getValue();

					Number data = fieldFunction.apply(model);

					if (data != null) {
						biConsumer.accept(entry.getKey(), data);
					}
				}
			)
		);
	}

	/**
	 * Writes the related collection's URL, using a {@code BiConsumer}.
	 *
	 * @param relatedCollection the related collection
	 * @param parentSingleModel the parent single model
	 * @param parentEmbeddedPathElements the list of embedded path elements
	 * @param httpServletRequest the HTTP servlet request
	 * @param fields the requested fields
	 * @param biConsumer the {@code BiConsumer} that writes the related
	 *        collection URL
	 */
	public <U, V> void writeRelatedCollection(
		RelatedCollection<U, V> relatedCollection,
		SingleModel<U> parentSingleModel,
		FunctionalList<String> parentEmbeddedPathElements,
		HttpServletRequest httpServletRequest, Fields fields,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			parentSingleModel.getModelClass(), fields);

		String key = relatedCollection.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		Optional<String> singleURLOptional = getSingleURLOptional(
			parentSingleModel, httpServletRequest);

		Class<V> modelClass = relatedCollection.getModelClass();

		Optional<String> nameOptional =
			_collectionResourceManager.getNameOptional(modelClass.getName());

		nameOptional.flatMap(
			name -> singleURLOptional.map(singleURL -> singleURL + "/" + name)
		).ifPresent(
			url -> {
				FunctionalList<String> embeddedPathElements =
					new StringFunctionalList(parentEmbeddedPathElements, key);

				biConsumer.accept(url, embeddedPathElements);
			}
		);
	}

	/**
	 * Writes related models. This method uses two consumers (one that writes
	 * the model's info, and one that writes its URL), so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param relatedModel the related model instance
	 * @param parentSingleModel the parent single model
	 * @param parentEmbeddedPathElements list of embedded path elements
	 * @param httpServletRequest the HTTP servlet request
	 * @param fields the requested fields
	 * @param embedded the information about the embedded resources
	 * @param modelBiConsumer the consumer that writes the related model's
	 *        information
	 * @param urlTriConsumer the consumer that writes the related model's URL
	 */
	public <T, U> void writeRelatedModel(
		RelatedModel<T, U> relatedModel, SingleModel<T> parentSingleModel,
		FunctionalList<String> parentEmbeddedPathElements,
		HttpServletRequest httpServletRequest, Fields fields, Embedded embedded,
		BiConsumer<SingleModel<U>, FunctionalList<String>> modelBiConsumer,
		TriConsumer<String, FunctionalList<String>, Boolean> urlTriConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			parentSingleModel.getModelClass(), fields);

		String key = relatedModel.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		Function<T, Optional<U>> modelFunction =
			relatedModel.getModelFunction();

		Optional<U> modelOptional = modelFunction.apply(
			parentSingleModel.getModel());

		if (!modelOptional.isPresent()) {
			return;
		}

		U model = modelOptional.get();

		Class<U> modelClass = relatedModel.getModelClass();

		SingleModel<U> singleModel = new SingleModel<>(model, modelClass);

		Predicate<String> embeddedPredicate = embedded.getEmbeddedPredicate();

		FunctionalList<String> embeddedPathElements = new StringFunctionalList(
			parentEmbeddedPathElements, key);

		Stream<String> stream = Stream.concat(
			Stream.of(embeddedPathElements.head()),
			embeddedPathElements.tailStream());

		String embeddedPath = String.join(
			".", stream.collect(Collectors.toList()));

		boolean isEmbedded = embeddedPredicate.test(embeddedPath);

		Optional<String> optional = getSingleURLOptional(
			singleModel, httpServletRequest);

		optional.ifPresent(
			url -> {
				urlTriConsumer.accept(url, embeddedPathElements, isEmbedded);

				if (isEmbedded) {
					modelBiConsumer.accept(singleModel, embeddedPathElements);
				}
			});
	}

	/**
	 * Writes the model's string fields. This method uses a consumer so each
	 * {@link javax.ws.rs.ext.MessageBodyWriter} can write each field
	 * differently.
	 *
	 * @param model the model
	 * @param modelClass the model's class
	 * @param fields the requested fields
	 * @param biConsumer the consumer that writes each field
	 */
	public <T> void writeStringFields(
		T model, Class<T> modelClass, Fields fields,
		BiConsumer<String, String> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.map(
			Representor::getStringFunctions
		).map(
			Map::entrySet
		).map(
			Set::stream
		).ifPresent(
			stream -> stream.filter(
				entry -> fieldsPredicate.test(entry.getKey())
			).forEach(
				entry -> {
					Function<T, String> fieldFunction = entry.getValue();

					String data = fieldFunction.apply(model);

					if ((data != null) && !data.isEmpty()) {
						biConsumer.accept(entry.getKey(), data);
					}
				}
			)
		);
	}

	/**
	 * Writes the model's types. This method uses a consumer so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write the types differently.
	 *
	 * @param modelClass the model class
	 * @param consumer the consumer that writes the types
	 */
	public <U> void writeTypes(
		Class<U> modelClass, Consumer<List<String>> consumer) {

		Optional<Representor<U, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		optional.map(
			Representor::getTypes
		).ifPresent(
			consumer
		);
	}

	private <T> Predicate<String> _getFieldsPredicate(
		Class<T> modelClass, Fields fields) {

		Optional<Representor<T, Identifier>> optional =
			_collectionResourceManager.getRepresentorOptional(modelClass);

		return optional.map(
			Representor::getTypes
		).map(
			fields::getFieldsPredicate
		).orElseGet(
			() -> field -> true
		);
	}

	private Function<String, String> _getTransformURIFunction(
		BiFunction<String, CollectionResourceURITransformer, String>
			biFunction) {

		return uri -> {
			Optional<CollectionResourceURITransformer>
				collectionResourceURITransformerOptional = Optional.ofNullable(
					_collectionResourceURITransformer);

			return collectionResourceURITransformerOptional.map(
				transformer -> biFunction.apply(uri, transformer)
			).orElse(
				uri
			);
		};
	}

	@Reference
	private CollectionResourceManager _collectionResourceManager;

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private CollectionResourceURITransformer _collectionResourceURITransformer;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ServerURLProvider _serverURLProvider;

}