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
import static com.liferay.apio.architect.writer.url.URLCreator.createBinaryURL;
import static com.liferay.apio.architect.writer.url.URLCreator.createNestedCollectionURL;
import static com.liferay.apio.architect.writer.url.URLCreator.createSingleURL;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.response.control.Fields;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.writer.alias.SingleModelFunction;

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
 * Writes the different fields declared on a {@link Representor}.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @param  <S> the model identifier's type ({@link Long}, {@link String}, etc.)
 */
public class FieldsWriter<T, S> {

	/**
	 * Returns the {@link SingleModel} version of a {@link RelatedModel}.
	 *
	 * @param  relatedModel the related model
	 * @param  parentSingleModel the related model's parent single model
	 * @return the single model version of the related model
	 */
	public static <T, S, U> Optional<SingleModel<U>> getSingleModel(
		RelatedModel<T, S> relatedModel, SingleModel<T> parentSingleModel,
		SingleModelFunction singleModelFunction) {

		Function<T, S> identifierFunction =
			relatedModel.getIdentifierFunction();

		return identifierFunction.andThen(
			s -> singleModelFunction.apply(s, relatedModel.getIdentifierClass())
		).apply(
			parentSingleModel.getModel()
		).map(
			Unsafe::unsafeCast
		);
	}

	public FieldsWriter(
		SingleModel<T> singleModel, RequestInfo requestInfo,
		Representor<T, S> representor, Path path,
		FunctionalList<String> embeddedPathElements,
		SingleModelFunction singleModelFunction) {

		_singleModel = singleModel;
		_requestInfo = requestInfo;
		_representor = representor;
		_path = path;
		_embeddedPathElements = embeddedPathElements;
		_singleModelFunction = singleModelFunction;
	}

	/**
	 * Returns the {@link Fields} predicate from the internal {@link
	 * RequestInfo}. If no {@code Fields} information is provided to the {@code
	 * RequestInfo}, this method returns an always-successful predicate.
	 *
	 * @return the {@code Fields} predicate, if {@code Fields} information
	 *         exists; an always-successful predicate otherwise
	 */
	public Predicate<String> getFieldsPredicate() {
		Fields fields = _requestInfo.getFields();

		return fields.apply(_representor.getTypes());
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
	 * Writes the model's boolean list fields. This method uses a {@code
	 * BiConsumer} so each {@code javax.ws.rs.ext.MessageBodyWriter} can write
	 * each field differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public void writeBooleanListFields(
		BiConsumer<String, List<Boolean>> biConsumer) {

		writeFields(
			Representor::getBooleanListFunctions, writeField(biConsumer));
	}

	/**
	 * Returns a consumer for entries of a {@code Map<String, Function<T, S>}.
	 * The consumer uses a value function to get the final value, then uses the
	 * bi-consumer provided as the second parameter to process the key and the
	 * final value. This consumer is called only when the final data isn't empty
	 * or {@code null}.
	 *
	 * @param  biConsumer the consumer used to process the key-value pair
	 * @return the consumer for entries of a {@code Map<String, Function<T, S>}
	 */
	public <U> Consumer<Entry<String, Function<T, U>>> writeField(
		BiConsumer<String, U> biConsumer) {

		return writeField(
			function -> function.apply(_singleModel.getModel()), biConsumer);
	}

	/**
	 * Returns a consumer for entries of a {@code Map<String, S>}. The consumer
	 * uses the function provided as the first parameter to get the final value,
	 * then uses the bi-consumer provided as the second parameter to process the
	 * key and the final value. This consumer is called only when the final data
	 * isn't empty or {@code null}.
	 *
	 * @param  function the function used to get the final value
	 * @param  biConsumer the consumer used to process the key-value pair
	 * @return the consumer for entries of a {@code Map<String, S>}
	 */
	public <U, V> Consumer<Entry<String, U>> writeField(
		Function<U, V> function, BiConsumer<String, V> biConsumer) {

		return entry -> {
			V data = function.apply(entry.getValue());

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
	 * Writes a {@code Map<String, S>} returned by a {@link Representor}
	 * function. This method uses a consumer so each caller can decide what to
	 * do with each entry. Each member of the map is filtered using the {@link
	 * Fields} predicate provided by {@link #getFieldsPredicate()}.
	 *
	 * @param representorFunction the {@code Representor} function that returns
	 *        the map being written
	 * @param consumer the consumer used to process each filtered entry
	 */
	public <U> void writeFields(
		Function<Representor<T, S>, Map<String, U>> representorFunction,
		Consumer<Entry<String, U>> consumer) {

		Map<String, U> map = representorFunction.apply(_representor);

		Set<Entry<String, U>> entries = map.entrySet();

		Stream<Entry<String, U>> stream = entries.stream();

		stream.filter(
			entry -> {
				Predicate<String> fieldsPredicate = getFieldsPredicate();

				return fieldsPredicate.test(entry.getKey());
			}
		).forEach(
			consumer
		);
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

		writeFields(
			Representor::getLocalizedStringFunctions,
			writeField(
				biFunction -> biFunction.apply(
					_singleModel.getModel(), _requestInfo.getLanguage()),
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
	 * Writes the model's number list fields. This method uses a {@code
	 * BiConsumer} so each {@code javax.ws.rs.ext.MessageBodyWriter} can write
	 * each field differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public void writeNumberListFields(
		BiConsumer<String, List<Number>> biConsumer) {

		writeFields(
			Representor::getNumberListFunctions, writeField(biConsumer));
	}

	/**
	 * Writes the related collection's URL, using a {@code BiConsumer}.
	 *
	 * @param relatedCollection the related collection
	 * @param parentEmbeddedPathElements the list of embedded path elements
	 * @param biConsumer the {@code BiConsumer} that writes the related
	 *        collection URL
	 */
	public <U extends Identifier> void writeRelatedCollection(
		RelatedCollection<U> relatedCollection, String resourceName,
		FunctionalList<String> parentEmbeddedPathElements,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Predicate<String> fieldsPredicate = getFieldsPredicate();

		String key = relatedCollection.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		String url = createNestedCollectionURL(
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
	 * @param nameFunction the function that gets a class's {@code
	 *        com.liferay.apio.architect.resource.CollectionResource} name
	 * @param biConsumer the consumer that writes a linked related model's URL
	 */
	public void writeRelatedCollections(
		Function<String, Optional<String>> nameFunction,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Stream<RelatedCollection<?>> stream =
			_representor.getRelatedCollections();

		stream.forEach(
			relatedCollection -> {
				Class<?> identifierClass =
					relatedCollection.getIdentifierClass();

				Optional<String> optional = nameFunction.apply(
					identifierClass.getName());

				optional.ifPresent(
					name -> writeRelatedCollection(
						relatedCollection, name, _embeddedPathElements,
						biConsumer));
			});
	}

	/**
	 * Writes a related model. This method uses three consumers: one that writes
	 * the model's info, one that writes its URL if it's a linked related model,
	 * and one that writes its URL if it's an embedded related model. Therefore,
	 * each {@code javax.ws.rs.ext.MessageBodyWriter} can write the related
	 * model differently.
	 *
	 * @param relatedModel the related model instance
	 * @param pathFunction the function that gets a single model's path
	 * @param modelBiConsumer the consumer that writes the related model's
	 *        information
	 * @param linkedURLBiConsumer the consumer that writes a linked related
	 *        model's URL
	 * @param embeddedURLBiConsumer the consumer that writes an embedded related
	 *        model's url
	 */
	public <U> void writeRelatedModel(
		RelatedModel<T, U> relatedModel,
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<SingleModel<?>, FunctionalList<String>> modelBiConsumer,
		BiConsumer<String, FunctionalList<String>> linkedURLBiConsumer,
		BiConsumer<String, FunctionalList<String>> embeddedURLBiConsumer) {

		writeRelatedModel(
			relatedModel, pathFunction,
			(url, embeddedPathElements) -> {
				Optional<SingleModel<U>> singleModelOptional = getSingleModel(
					relatedModel, _singleModel,
					unsafeCast(_singleModelFunction));

				if (!singleModelOptional.isPresent()) {
					return;
				}

				Predicate<String> embedded = _requestInfo.getEmbedded();

				SingleModel<U> singleModel = singleModelOptional.get();

				Stream<String> stream = Stream.concat(
					Stream.of(embeddedPathElements.head()),
					embeddedPathElements.tailStream());

				String embeddedPath = String.join(
					".", stream.collect(Collectors.toList()));

				if (embedded.test(embeddedPath)) {
					embeddedURLBiConsumer.accept(url, embeddedPathElements);
					modelBiConsumer.accept(singleModel, embeddedPathElements);
				}
				else {
					linkedURLBiConsumer.accept(url, embeddedPathElements);
				}
			});
	}

	/**
	 * Writes a related model. This method uses a consumer so each {@code
	 * javax.ws.rs.ext.MessageBodyWriter} can write the related model
	 * differently.
	 *
	 * @param relatedModel the related model instance
	 * @param pathFunction the function that gets the path of a {@link
	 *        SingleModel}
	 * @param biConsumer the consumer that writes a related model's URL and
	 *        embedded path elements
	 */
	public <U> void writeRelatedModel(
		RelatedModel<T, U> relatedModel,
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<String, FunctionalList<String>> biConsumer) {

		Predicate<String> fieldsPredicate = getFieldsPredicate();

		String key = relatedModel.getKey();

		if (!fieldsPredicate.test(key)) {
			return;
		}

		Optional<SingleModel<U>> optional = getSingleModel(
			relatedModel, _singleModel, unsafeCast(_singleModelFunction));

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
	 * Writes the related models contained in the {@link Representor} this
	 * writer handles. This method uses three consumers: one that writes the
	 * model's info, one that writes its URL if it's a linked related model, and
	 * one that writes its URL if it's an embedded related model. Therefore,
	 * each {@code javax.ws.rs.ext.MessageBodyWriter} can write the related
	 * model differently.
	 *
	 * @param pathFunction the function that gets a single model's path
	 * @param modelBiConsumer the consumer that writes the related model's
	 *        information
	 * @param linkedURLBiConsumer the consumer that writes a linked related
	 *        model's URL
	 * @param embeddedURLBiConsumer the consumer that writes an embedded related
	 *        model's URL
	 */
	public void writeRelatedModels(
		Function<SingleModel<?>, Optional<Path>> pathFunction,
		BiConsumer<SingleModel<?>, FunctionalList<String>> modelBiConsumer,
		BiConsumer<String, FunctionalList<String>> linkedURLBiConsumer,
		BiConsumer<String, FunctionalList<String>> embeddedURLBiConsumer) {

		List<RelatedModel<T, ?>> embeddedRelatedModels =
			_representor.getRelatedModels();

		embeddedRelatedModels.forEach(
			relatedModel -> writeRelatedModel(
				relatedModel, pathFunction, modelBiConsumer,
				linkedURLBiConsumer, embeddedURLBiConsumer));
	}

	/**
	 * Writes the the handled resource's single URL. This method uses a consumer
	 * so each {@code javax.ws.rs.ext.MessageBodyWriter} can write the URL
	 * differently.
	 *
	 * @param urlConsumer the consumer that writes the URL
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
	 * Writes the model's string list fields. This method uses a {@code
	 * BiConsumer} so each {@code javax.ws.rs.ext.MessageBodyWriter} can write
	 * each field differently.
	 *
	 * @param biConsumer the {@code BiConsumer} called to write each field
	 */
	public void writeStringListFields(
		BiConsumer<String, List<String>> biConsumer) {

		writeFields(
			Representor::getStringListFunctions, writeField(biConsumer));
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
	private final Representor<T, S> _representor;
	private final RequestInfo _requestInfo;
	private final SingleModel<T> _singleModel;
	private final SingleModelFunction _singleModelFunction;

}