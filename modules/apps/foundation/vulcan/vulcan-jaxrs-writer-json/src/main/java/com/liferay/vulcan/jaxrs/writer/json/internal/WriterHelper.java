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
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.representor.Resource;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.uri.CollectionResourceURITransformer;
import com.liferay.vulcan.wiring.osgi.RelatedModel;
import com.liferay.vulcan.wiring.osgi.ResourceManager;

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
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = WriterHelper.class)
public class WriterHelper {

	public String getAbsoluteURL(UriInfo uriInfo, String relativeURI) {
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

		uriBuilder = uriBuilder.clone();

		uriBuilder.path(relativeURI);

		URI uri = uriBuilder.build();

		return uri.toString();
	}

	public <T> Optional<String> getCollectionURL(
		Class<T> modelClass, UriInfo uriInfo) {

		Optional<Resource<T>> optional = _resourceManager.getResourceOptional(
			modelClass);

		return optional.map(
			Resource::getPath
		).map(
			path -> "/p/" + path
		).map(
			_transformURI(
				(uri, transformer) -> transformer.transformPageURI(
					uri, modelClass))
		).map(
			uri -> getAbsoluteURL(uriInfo, uri)
		);
	}

	public <T> Optional<String> getSingleURL(
		Class<T> modelClass, T model, UriInfo uriInfo) {

		Optional<Resource<T>> optional = _resourceManager.getResourceOptional(
			modelClass);

		String identifier = _resourceManager.getIdentifier(modelClass, model);

		return optional.map(
			Resource::getPath
		).map(
			path -> "/p/" + path + "/" + identifier
		).map(
			_transformURI(
				(uri, transformer) ->
					transformer.transformCollectionItemSingleResourceURI(
						uri, modelClass, model))
		).map(
			uri -> getAbsoluteURL(uriInfo, uri)
		);
	}

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

		Optional<String> urlOptional = getSingleURL(modelClass, model, uriInfo);

		urlOptional.ifPresent(
			url -> {
				Predicate<String> embeddedPredicate =
					embedded.getEmbeddedPredicate();

				FunctionalList<String> embeddedPathElements =
					new StringFunctionalList(parentEmbeddedPathElements, key);

				Stream<String> stream = Stream.concat(
					Stream.of(embeddedPathElements.head()),
					embeddedPathElements.tail());

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

	public <T> void writeSingleResourceURL(
		T model, Class<T> modelClass, UriInfo uriInfo,
		Consumer<String> consumer) {

		String url = getSingleURL(modelClass, model, uriInfo).orElseThrow(
			() -> new VulcanDeveloperError.UnresolvableURI(modelClass));

		consumer.accept(url);
	}

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

	private Function<String, String> _transformURI(
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
	private ResourceManager _resourceManager;

}