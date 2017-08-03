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

package com.liferay.vulcan.jaxrs.writer.json.internal;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.error.VulcanDeveloperError;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveFilterProvider;
import com.liferay.vulcan.filter.FilterProvider;
import com.liferay.vulcan.filter.QueryParamFilterType;
import com.liferay.vulcan.function.TriConsumer;
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.uri.CollectionResourceURITransformer;
import com.liferay.vulcan.wiring.osgi.manager.FilterProviderManager;
import com.liferay.vulcan.wiring.osgi.manager.ResourceManager;
import com.liferay.vulcan.wiring.osgi.model.RelatedCollection;
import com.liferay.vulcan.wiring.osgi.model.RelatedModel;

import java.net.URI;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provide methods to help {@link javax.ws.rs.ext.MessageBodyWriter} write
 * Hypermedia resources.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = WriterHelper.class)
public class WriterHelper {

	/**
	 * Returns the absolute URL from a relative URI.
	 *
	 * @param  uriInfo uri info of the actual request.
	 * @param  relativeURI a relative URI.
	 * @return the absolute URL.
	 */
	public String getAbsoluteURL(UriInfo uriInfo, String relativeURI) {
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

		uriBuilder = uriBuilder.clone();

		uriBuilder.path(relativeURI);

		URI uri = uriBuilder.build();

		return uri.toString();
	}

	/**
	 * Returns the collection URL of a model class. If a {@link Resource} for
	 * that model class cannot be found, returns <code>Optional#empty()</code>.
	 *
	 * @param  modelClass the model class of the {@link Resource}.
	 * @param  uriInfo uri info of the actual request.
	 * @return the collection URL if a {@link Resource} for the model class can
	 *         be found; <code>Optional#empty()</code> otherwise.
	 */
	public <T> Optional<String> getCollectionURLOptional(
		Class<T> modelClass, UriInfo uriInfo) {

		Optional<Resource<T>> optional = _resourceManager.getResourceOptional(
			modelClass);

		return optional.map(
			Resource::getPath
		).map(
			path -> "/p/" + path
		).map(
			_getTransformURIFunction(
				(uri, transformer) -> transformer.transformPageURI(
					uri, modelClass))
		).map(
			uri -> getAbsoluteURL(uriInfo, uri)
		);
	}

	/**
	 * Returns the filtered collection URL of a model class. If a {@link
	 * Resource} for that model class cannot be found, returns
	 * <code>Optional#empty()</code>.
	 *
	 * @param  modelClass the model class of the {@link Resource}.
	 * @param  queryParamFilterType the filter type applied to this collection.
	 * @param  uriInfo uri info of the actual request.
	 * @return the collection URL if a {@link Resource} for the model class can
	 *         be found; <code>Optional#empty()</code> otherwise.
	 */
	public <T, Q extends QueryParamFilterType> Optional<String>
		getFilteredCollectionURLOptional(
			Class<T> modelClass, Q queryParamFilterType, UriInfo uriInfo) {

		Optional<String> optional = getCollectionURLOptional(
			modelClass, uriInfo);

		return optional.map(
			url -> {
				Optional<FilterProvider<Q>> filterProviderOptional =
					_filterProviderManager.getFilterProviderOptional(
						queryParamFilterType);

				FilterProvider<Q> filterProvider =
					filterProviderOptional.orElseThrow(
						() -> new MustHaveFilterProvider(queryParamFilterType));

				UriBuilder uriBuilder = UriBuilder.fromUri(url);

				Map<String, String> queryParamMap =
					filterProvider.getQueryParamMap(queryParamFilterType);

				for (String key : queryParamMap.keySet()) {
					uriBuilder = uriBuilder.queryParam(
						key, queryParamMap.get(key));
				}

				String filterName = filterProvider.getFilterName();

				return uriBuilder.queryParam(
					"filterName", filterName
				).build();
			}
		).map(
			URI::toString
		);
	}

	/**
	 * Returns the URL to the resource of a certain model. If a {@link Resource}
	 * for that model class cannot be found, returns
	 * <code>Optional#empty()</code>.
	 *
	 * @param  modelClass the model class of the {@link Resource}.
	 * @param  model an instance of the model
	 * @param  uriInfo uri info of the actual request.
	 * @return the single URL if a {@link Resource} for the model class can be
	 *         found; <code>Optional#empty()</code> otherwise.
	 */
	public <T> Optional<String> getSingleURLOptional(
		Class<T> modelClass, T model, UriInfo uriInfo) {

		Optional<Resource<T>> optional = _resourceManager.getResourceOptional(
			modelClass);

		String identifier = _resourceManager.getIdentifier(modelClass, model);

		return optional.map(
			Resource::getPath
		).map(
			path -> "/p/" + path + "/" + identifier
		).map(
			_getTransformURIFunction(
				(uri, transformer) ->
					transformer.transformCollectionItemSingleResourceURI(
						uri, modelClass, model))
		).map(
			uri -> getAbsoluteURL(uriInfo, uri)
		);
	}

	/**
	 * Helper method to write a model fields. It uses a consumer so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write each field differently.
	 *
	 * @param model an instance of the model.
	 * @param modelClass the model class.
	 * @param fields the requested fields.
	 * @param biConsumer the consumer that will be called to write each field.
	 */
	public <T> void writeFields(
		T model, Class<T> modelClass, Fields fields,
		BiConsumer<String, Object> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Map<String, Function<T, Object>> fieldFunctions =
			_resourceManager.getFieldFunctions(modelClass);

		for (String field : fieldFunctions.keySet()) {
			if (fieldsPredicate.test(field)) {
				Function<T, Object> fieldFunction = fieldFunctions.get(field);

				Object data = fieldFunction.apply(model);

				if (data != null) {
					biConsumer.accept(field, data);
				}
			}
		}
	}

	/**
	 * Helper method to write a model linked related models. It uses a consumer
	 * so each {@link javax.ws.rs.ext.MessageBodyWriter} can write the related
	 * model differently.
	 *
	 * @param relatedModel the instance of the related model.
	 * @param parentModel the instance of the parent model.
	 * @param parentModelClass the parent model class.
	 * @param parentEmbeddedPathElements list of embedded path elements.
	 * @param uriInfo uri info of the actual request.
	 * @param fields the requested fields.
	 * @param embedded the embedded resources info.
	 * @param biConsumer the consumer that will be called to write the related
	 *        model.
	 */
	public <T, U> void writeLinkedRelatedModel(
		RelatedModel<T, U> relatedModel, T parentModel,
		Class<T> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, UriInfo uriInfo,
		Fields fields, Embedded embedded,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		writeRelatedModel(
			relatedModel, parentModel, parentModelClass,
			parentEmbeddedPathElements, uriInfo, fields, embedded,
			(model, modelClass, embeddedPathElements) -> {
			},
			(url, embeddedPathElements, isEmbedded) -> biConsumer.accept(
				url, embeddedPathElements));
	}

	/**
	 * Helper method to write a model links. It uses a consumer so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write each link differently.
	 *
	 * @param modelClass the model class.
	 * @param fields the requested fields.
	 * @param biConsumer the consumer that will be called to write each link.
	 */
	public <T> void writeLinks(
		Class<T> modelClass, Fields fields,
		BiConsumer<String, String> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			modelClass, fields);

		Map<String, String> links = _resourceManager.getLinks(modelClass);

		for (String key : links.keySet()) {
			if (fieldsPredicate.test(key)) {
				biConsumer.accept(key, links.get(key));
			}
		}
	}

	/**
	 * Helper method to write a model related collection. It uses a consumer for
	 * writing the URL.
	 *
	 * @param relatedCollection the instance of the related collection.
	 * @param parentModel the instance of the parent model.
	 * @param parentModelClass the parent model class.
	 * @param parentEmbeddedPathElements list of embedded path elements.
	 * @param uriInfo uri info of the actual request.
	 * @param fields the requested fields.
	 * @param biConsumer the consumer that will be called to write the related
	 *        collection URL.
	 */
	public <U, V> void writeRelatedCollection(
		RelatedCollection<U, V> relatedCollection, U parentModel,
		Class<U> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, UriInfo uriInfo,
		Fields fields, BiConsumer<String, FunctionalList<String>> biConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			parentModelClass, fields);

		String key = relatedCollection.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		Function<U, QueryParamFilterType> filterFunction =
			relatedCollection.getFilterFunction();

		QueryParamFilterType queryParamFilterType = filterFunction.apply(
			parentModel);

		Class<V> modelClass = relatedCollection.getModelClass();

		Optional<String> optional = getFilteredCollectionURLOptional(
			modelClass, queryParamFilterType, uriInfo);

		optional.ifPresent(
			url -> {
				FunctionalList<String> embeddedPathElements =
					new StringFunctionalList(parentEmbeddedPathElements, key);

				biConsumer.accept(url, embeddedPathElements);
			});
	}

	/**
	 * Helper method to write a model related models. It uses two consumers (one
	 * for writing the model info, and another for writing its URL) so each
	 * {@link javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param relatedModel the instance of the related model.
	 * @param parentModel the instance of the parent model.
	 * @param parentModelClass the parent model class.
	 * @param parentEmbeddedPathElements list of embedded path elements.
	 * @param uriInfo uri info of the actual request.
	 * @param fields the requested fields.
	 * @param embedded the embedded resources info.
	 * @param modelTriConsumer the consumer that will be called to write the
	 *        related model info.
	 * @param urlTriConsumer the consumer that will be called to write the
	 *        related model URL.
	 */
	public <T, U> void writeRelatedModel(
		RelatedModel<T, U> relatedModel, T parentModel,
		Class<T> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, UriInfo uriInfo,
		Fields fields, Embedded embedded,
		TriConsumer<U, Class<U>, FunctionalList<String>> modelTriConsumer,
		TriConsumer<String, FunctionalList<String>, Boolean> urlTriConsumer) {

		Predicate<String> fieldsPredicate = _getFieldsPredicate(
			parentModelClass, fields);

		String key = relatedModel.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		Function<T, Optional<U>> modelFunction =
			relatedModel.getModelFunction();

		Optional<U> modelOptional = modelFunction.apply(parentModel);

		if (!modelOptional.isPresent()) {
			return;
		}

		U model = modelOptional.get();

		Class<U> modelClass = relatedModel.getModelClass();

		Optional<String> singleURLOptional = getSingleURLOptional(
			modelClass, model, uriInfo);

		singleURLOptional.ifPresent(
			url -> {
				Predicate<String> embeddedPredicate =
					embedded.getEmbeddedPredicate();

				FunctionalList<String> embeddedPathElements =
					new StringFunctionalList(parentEmbeddedPathElements, key);

				Stream<String> stream = Stream.concat(
					Stream.of(embeddedPathElements.head()),
					embeddedPathElements.tailStream());

				String embeddedPath = String.join(
					".", stream.collect(Collectors.toList()));

				boolean isEmbedded = embeddedPredicate.test(embeddedPath);

				urlTriConsumer.accept(url, embeddedPathElements, isEmbedded);

				if (isEmbedded) {
					modelTriConsumer.accept(
						model, modelClass, embeddedPathElements);
				}
			});
	}

	/**
	 * Helper method to write a model url. It uses a consumer so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write the url differently.
	 *
	 * @param model the instance of the model.
	 * @param modelClass the model class.
	 * @param uriInfo uri info of the actual request.
	 * @param consumer the consumer that will be called to write the URL.
	 */
	public <T> void writeSingleResourceURL(
		T model, Class<T> modelClass, UriInfo uriInfo,
		Consumer<String> consumer) {

		Optional<String> singleURLOptional = getSingleURLOptional(
			modelClass, model, uriInfo);

		String singleURL = singleURLOptional.orElseThrow(
			() -> new VulcanDeveloperError.UnresolvableURI(modelClass));

		consumer.accept(singleURL);
	}

	/**
	 * Helper method to write a model types. It uses a consumer so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write the types differently.
	 *
	 * @param modelClass the model class.
	 * @param consumer the consumer that will be called to write the types.
	 */
	public <U> void writeTypes(
		Class<U> modelClass, Consumer<List<String>> consumer) {

		List<String> types = _resourceManager.getTypes(modelClass);

		consumer.accept(types);
	}

	private <T> Predicate<String> _getFieldsPredicate(
		Class<T> modelClass, Fields fields) {

		List<String> types = _resourceManager.getTypes(modelClass);

		return fields.getFieldsPredicate(types);
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

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private CollectionResourceURITransformer _collectionResourceURITransformer;

	@Reference
	private FilterProviderManager _filterProviderManager;

	@Reference
	private ResourceManager _resourceManager;

}