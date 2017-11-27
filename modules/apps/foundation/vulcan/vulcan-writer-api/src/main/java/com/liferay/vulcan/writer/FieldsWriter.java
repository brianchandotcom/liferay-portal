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

import static com.liferay.vulcan.writer.url.URLCreator.createBinaryURL;
import static com.liferay.vulcan.writer.url.URLCreator.createCollectionURL;
import static com.liferay.vulcan.writer.url.URLCreator.createSingleURL;

import com.liferay.vulcan.language.Language;
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.request.RequestInfo;
import com.liferay.vulcan.resource.RelatedCollection;
import com.liferay.vulcan.resource.RelatedModel;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.uri.Path;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An instance of this class can be used to write the different fields declared
 * on a {@link Representor}.
 *
 * @author Alejandro Hernández
 * @review
 */
public class FieldsWriter<T, U extends Identifier> {

	/**
	 * Returns the {@link SingleModel} version of a {@link RelatedModel}.
	 *
	 * @param  relatedModel the related model to convert
	 * @param  parentSingleModel the parent single model of the related model
	 * @return the single model version of the related model
	 * @review
	 */
	public static <T, V> Optional<SingleModel<V>> getSingleModel(
		RelatedModel<T, V> relatedModel, SingleModel<T> parentSingleModel) {

		Optional<V> optional = relatedModel.getModelFunction(
		).apply(
			parentSingleModel.getModel()
		);

		Class<V> modelClass = relatedModel.getModelClass();

		return optional.map(model -> new SingleModel<>(model, modelClass));
	}

	public FieldsWriter(
		SingleModel<T> singleModel, RequestInfo requestInfo,
		Representor<T, U> representor, Path path,
		FunctionalList<String> embeddedPathElements) {

		_singleModel = singleModel;
		_requestInfo = requestInfo;
		_representor = representor;
		_path = path;
		_embeddedPathElements = embeddedPathElements;
	}

	/**
	 * Returns the embedded predicate out of the internal {@link RequestInfo}.
	 * If no {@link Embedded} information is provided to the {@code RequestInfo}
	 * an always-unsuccessful predicate is returned.
	 *
	 * @return the embedded predicate if {@code Embedded} information can be
	 *         found; an always-unsuccessful predicate otherwise.
	 * @review
	 */
	public Predicate<String> getEmbeddedPredicate() {
		Optional<Embedded> embeddedOptional =
			_requestInfo.getEmbeddedOptional();

		return embeddedOptional.map(
			Embedded::getEmbeddedPredicate
		).orElseGet(
			() -> field -> false
		);
	}

	/**
	 * Returns the fields predicate out of the internal {@link RequestInfo}. If
	 * no {@link Fields} information is provided to the {@code RequestInfo} an
	 * always-successful predicate is returned.
	 *
	 * @return the fields predicate if {@code Fields} information can be found;
	 *         an always-successful predicate otherwise.
	 * @review
	 */
	public Predicate<String> getFieldsPredicate() {
		Optional<Fields> optional = _requestInfo.getFieldsOptional();

		return optional.map(
			fields -> fields.getFieldsPredicate(_representor.getTypes())
		).orElseGet(
			() -> field -> true
		);
	}

	/**
	 * Writes binary resources. This method uses a {@code BiConsumer} so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write each binary
	 * differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each binary
	 */
	public void writeBinaries(BiConsumer<String, String> biConsumer) {
		Function<String, String> urlFunction = binaryId -> createBinaryURL(
			_requestInfo.getServerURL(), binaryId, _path);

		writeFields(
			Representor::getBinaryFunctions,
			entry -> biConsumer.accept(
				entry.getKey(), urlFunction.apply(entry.getKey())));
	}

	/**
	 * Writes the model's boolean fields. This method uses a {@code BiConsumer}
	 * so each {@code javax.ws.rs.ext.MessageBodyWriter} can write each field
	 * differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public void writeBooleanFields(BiConsumer<String, Boolean> biConsumer) {
		writeFields(Representor::getBooleanFunctions, writeField(biConsumer));
	}

	/**
	 * Writes an embedded related model. This method uses three consumers (one
	 * that writes the model's info, one that writes its URL in the case is
	 * linked, and one that writes its URL in the case is embedded), so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param  relatedModel the related model instance
	 * @param  pathFunction a function that can be used to get a path of a
	 *         {@code SingleModel}
	 * @param  modelBiConsumer the consumer that writes the related model's
	 *         information
	 * @param  linkedURLBiConsumer the consumer that writes a linked related
	 *         model's url
	 * @param  embeddedURLBiConsumer the consumer that writes an embedded
	 *         related model's url
	 * @review
	 */
	public <V> void writeEmbeddedRelatedModel(
		RelatedModel<T, V> relatedModel,
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<SingleModel<?>, FunctionalList<String>> modelBiConsumer,
		BiConsumer<String, FunctionalList<String>> linkedURLBiConsumer,
		BiConsumer<String, FunctionalList<String>> embeddedURLBiConsumer) {

		writeRelatedModel(
			relatedModel, pathFunction,
			(url, embeddedPathElements) -> {
				Optional<SingleModel<V>> singleModelOptional = getSingleModel(
					relatedModel, _singleModel);

				if (!singleModelOptional.isPresent()) {
					return;
				}

				SingleModel<V> singleModel = singleModelOptional.get();

				Stream<String> stream = Stream.concat(
					Stream.of(embeddedPathElements.head()),
					embeddedPathElements.tailStream());

				String embeddedPath = String.join(
					".", stream.collect(Collectors.toList()));

				if (getEmbeddedPredicate().test(embeddedPath)) {
					embeddedURLBiConsumer.accept(url, embeddedPathElements);
					modelBiConsumer.accept(singleModel, embeddedPathElements);
				}
				else {
					linkedURLBiConsumer.accept(url, embeddedPathElements);
				}
			});
	}

	/**
	 * Writes the embedded related models contained in the {@link Representor}
	 * this writer handles. This method uses three consumers (one that writes
	 * the model's info, one that writes its URL in the case is linked, and one
	 * that writes its URL in the case is embedded), so each {@code
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param  pathFunction a function that can be used to get a path of a
	 *         {@code SingleModel}
	 * @param  modelBiConsumer the consumer that writes the related model's
	 *         information
	 * @param  linkedURLBiConsumer the consumer that writes a linked related
	 *         model's url
	 * @param  embeddedURLBiConsumer the consumer that writes an embedded
	 *         related model's url
	 * @review
	 */
	public void writeEmbeddedRelatedModels(
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<SingleModel<?>, FunctionalList<String>> modelBiConsumer,
		BiConsumer<String, FunctionalList<String>> linkedURLBiConsumer,
		BiConsumer<String, FunctionalList<String>> embeddedURLBiConsumer) {

		List<RelatedModel<T, ?>> embeddedRelatedModels =
			_representor.getEmbeddedRelatedModels();

		embeddedRelatedModels.forEach(
			relatedModel -> writeEmbeddedRelatedModel(
				relatedModel, pathFunction, modelBiConsumer,
				linkedURLBiConsumer, embeddedURLBiConsumer));
	}

	/**
	 * Returns a consumer for entries of a {@code Map<String, Function<T, V>}.
	 * The consumer uses value function to get the final value and then uses the
	 * bi-consumer provided as the second parameter to process the key and the
	 * final value. This consumer is called only in the case the final data is
	 * not empty or {@code null}.
	 *
	 * @param  biConsumer the consumer used to process the key-value pair
	 * @return a consumer for entries of a {@code Map<String, V>}
	 * @review
	 */
	public <V> Consumer<Entry<String, Function<T, V>>> writeField(
		BiConsumer<String, V> biConsumer) {

		return writeField(
			function -> function.apply(_singleModel.getModel()), biConsumer);
	}

	/**
	 * Returns a consumer for entries of a {@code Map<String, V>}. The consumer
	 * uses the function provided as the first parameter to get the final value
	 * and then uses the bi-consumer provided as the second parameter to process
	 * the key and the final value. This consumer is called only in the case the
	 * final data is not empty or {@code null}.
	 *
	 * @param  function the function used to obtain the final value
	 * @param  biConsumer the consumer used to process the key-value pair
	 * @return a consumer for entries of a {@code Map<String, V>}
	 * @review
	 */
	public <V, W> Consumer<Entry<String, V>> writeField(
		Function<V, W> function, BiConsumer<String, W> biConsumer) {

		return entry -> {
			W data = function.apply(entry.getValue());

			if (data instanceof String) {
				if ((data != null) && !((String)data).isEmpty()) {
					biConsumer.accept(entry.getKey(), data);
				}
			}
			else if (data != null) {
				biConsumer.accept(entry.getKey(), data);
			}
		};
	}

	/**
	 * Helper method that can be used to write a {@code Map<String, V>} returned
	 * by a {@link Representor}'s function. This method uses a consumer so each
	 * caller can decide what to do with each entry. Each member of the map is
	 * filtered using the {@link Fields} predicate provided by {@link
	 * #getFieldsPredicate()}.
	 *
	 * @param  representorFunction the {@code Representor} function that returns
	 *         the map being written
	 * @param  consumer the consumer used to process each filtered entry
	 * @review
	 */
	public <V> void writeFields(
		Function<Representor<T, U>, Map<String, V>> representorFunction,
		Consumer<Entry<String, V>> consumer) {

		Map<String, V> map = representorFunction.apply(_representor);

		Set<Entry<String, V>> entries = map.entrySet();

		Stream<Entry<String, V>> stream = entries.stream();

		stream.filter(
			entry -> getFieldsPredicate().test(entry.getKey())
		).forEach(
			consumer
		);
	}

	/**
	 * Writes a linked related model. This method uses a consumer so each {@code
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param  relatedModel the related model instance
	 * @param  pathFunction a function that can be used to get a path of a
	 *         {@code SingleModel}
	 * @param  linkedURLBiConsumer the consumer that writes a linked related
	 *         model's url
	 * @review
	 */
	public <V> void writeLinkedRelatedModel(
		RelatedModel<T, V> relatedModel,
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<String, FunctionalList<String>> linkedURLBiConsumer) {

		writeRelatedModel(relatedModel, pathFunction, linkedURLBiConsumer);
	}

	/**
	 * Writes the linked related models contained in the {@link Representor}
	 * this writer handles. This method uses a consumer so each {@code
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param  pathFunction a function that can be used to get a path of a
	 *         {@code SingleModel}
	 * @param  linkedURLBiConsumer the consumer that writes a linked related
	 *         model's url
	 * @review
	 */
	public void writeLinkedRelatedModels(
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<String, FunctionalList<String>> linkedURLBiConsumer) {

		List<RelatedModel<T, ?>> linkedRelatedModels =
			_representor.getLinkedRelatedModels();

		linkedRelatedModels.forEach(
			relatedModel -> writeLinkedRelatedModel(
				relatedModel, pathFunction, linkedURLBiConsumer));
	}

	/**
	 * Writes the model's links. This method uses a {@code BiConsumer} so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write each link
	 * differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each link
	 */
	public void writeLinks(BiConsumer<String, String> biConsumer) {
		writeFields(
			Representor::getLinks, writeField(Function.identity(), biConsumer));
	}

	/**
	 * Writes a model's localized string fields. This method uses a {@code
	 * BiConsumer} so each {@code javax.ws.rs.ext.MessageBodyWriter} can write
	 * each field differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public void writeLocalizedStringFields(
		BiConsumer<String, String> biConsumer) {

		Optional<Language> languageOptional =
			_requestInfo.getLanguageOptional();

		if (!languageOptional.isPresent()) {
			return;
		}

		Language language = languageOptional.get();

		writeFields(
			Representor::getLocalizedStringFunctions,
			writeField(
				biFunction -> biFunction.apply(
					_singleModel.getModel(), language),
				biConsumer));
	}

	/**
	 * Writes a model's number fields. This method uses a {@code BiConsumer} so
	 * each {@code javax.ws.rs.ext.MessageBodyWriter} can write each field
	 * differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public void writeNumberFields(BiConsumer<String, Number> biConsumer) {
		writeFields(Representor::getNumberFunctions, writeField(biConsumer));
	}

	/**
	 * Writes the related collection's URL, using a {@code BiConsumer}.
	 *
	 * @param relatedCollection the related collection
	 * @param parentEmbeddedPathElements the list of embedded path elements
	 * @param biConsumer the {@code BiConsumer} that writes the related
	 *        collection URL
	 */
	public <V> void writeRelatedCollection(
		RelatedCollection<T, V> relatedCollection, String resourceName,
		FunctionalList<String> parentEmbeddedPathElements,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Predicate<String> fieldsPredicate = getFieldsPredicate();

		String key = relatedCollection.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		String url = createCollectionURL(
			_requestInfo.getServerURL(), _path, resourceName);

		FunctionalList<String> embeddedPathElements = new FunctionalList<>(
			parentEmbeddedPathElements, key);

		biConsumer.accept(url, embeddedPathElements);
	}

	/**
	 * Writes the related collections contained in the {@link Representor} this
	 * writer handles. This method uses a consumer so each {@code
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param  nameFunction a function that can be used to get the name of the
	 *         {@code com.liferay.vulcan.resource.CollectionResource} of a
	 *         certain class name
	 * @param  biConsumer the consumer that writes a linked related model's url
	 * @review
	 */
	public void writeRelatedCollections(
		Function<String, Optional<String>> nameFunction,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Stream<RelatedCollection<T, ?>> stream =
			_representor.getRelatedCollections();

		stream.forEach(
			relatedCollection -> {
				Class<?> modelClass = relatedCollection.getModelClass();

				Optional<String> optional = nameFunction.apply(
					modelClass.getName());

				optional.ifPresent(
					name -> writeRelatedCollection(
						relatedCollection, name, _embeddedPathElements,
						biConsumer));
			});
	}

	/**
	 * Writes a related model. This method uses a consumer so each {@code
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param  relatedModel the related model instance
	 * @param  pathFunction a function that can be used to get a path of a
	 *         {@code SingleModel}
	 * @param  biConsumer the consumer that writes a related model's
	 *         url/embedded path elements
	 * @review
	 */
	public <V> void writeRelatedModel(
		RelatedModel<T, V> relatedModel,
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Predicate<String> fieldsPredicate = getFieldsPredicate();

		String key = relatedModel.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		Optional<SingleModel<V>> optional = getSingleModel(
			relatedModel, _singleModel);

		FunctionalList<String> embeddedPathElements = new FunctionalList<>(
			_embeddedPathElements, key);

		optional.flatMap(
			pathFunction
		).map(
			path -> createSingleURL(_requestInfo.getServerURL(), path)
		).ifPresent(
			url -> biConsumer.accept(url, embeddedPathElements)
		);
	}

	/**
	 * Writes the single URL of the handled resource. This method uses a
	 * consumer so each {@code javax.ws.rs.ext.MessageBodyWriter} can write each
	 * the url differently.
	 *
	 * @param  urlConsumer the consumer that writes the url
	 * @review
	 */
	public void writeSingleURL(Consumer<String> urlConsumer) {
		String url = createSingleURL(_requestInfo.getServerURL(), _path);

		urlConsumer.accept(url);
	}

	/**
	 * Writes the model's string fields. This method uses a consumer so each
	 * {@code javax.ws.rs.ext.MessageBodyWriter} can write each field
	 * differently.
	 *
	 * @param biConsumer the consumer that writes each field
	 */
	public void writeStringFields(BiConsumer<String, String> biConsumer) {
		writeFields(Representor::getStringFunctions, writeField(biConsumer));
	}

	/**
	 * Writes the model's types. This method uses a consumer so each {@link
	 * javax.ws.rs.ext.MessageBodyWriter} can write the types differently.
	 *
	 * @param consumer the consumer that writes the types
	 */
	public void writeTypes(Consumer<List<String>> consumer) {
		consumer.accept(_representor.getTypes());
	}

	private final FunctionalList<String> _embeddedPathElements;
	private final Path _path;
	private final Representor<T, U> _representor;
	private final RequestInfo _requestInfo;
	private final SingleModel<T> _singleModel;

}