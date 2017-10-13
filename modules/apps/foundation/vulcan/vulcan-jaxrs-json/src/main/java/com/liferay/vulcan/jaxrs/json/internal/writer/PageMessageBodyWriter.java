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

import static org.osgi.service.component.annotations.ReferenceCardinality.AT_LEAST_ONE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.google.gson.JsonObject;

import com.liferay.vulcan.alias.BinaryFunction;
import com.liferay.vulcan.error.VulcanDeveloperError;
import com.liferay.vulcan.jaxrs.json.internal.JSONObjectBuilderImpl;
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.PageMessageMapper;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.RelatedCollection;
import com.liferay.vulcan.resource.RelatedModel;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.wiring.osgi.manager.CollectionResourceManager;
import com.liferay.vulcan.wiring.osgi.manager.ProviderManager;
import com.liferay.vulcan.wiring.osgi.util.GenericUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Gives Vulcan the ability to write collection pages. For that end it uses the
 * right {@link PageMessageMapper} in accordance with the media type.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
@Component(
	immediate = true, property = "liferay.vulcan.message.body.writer=true"
)
@Provider
public class PageMessageBodyWriter<T>
	implements MessageBodyWriter<Try.Success<Page<T>>> {

	@Override
	public long getSize(
		Try.Success<Page<T>> success, Class<?> clazz, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		Try<Class<Object>> classTry =
			GenericUtil.getFirstGenericTypeArgumentTry(genericType);

		return classTry.filter(
			Page.class::equals
		).isSuccess();
	}

	@Override
	public void writeTo(
			Try.Success<Page<T>> success, Class<?> clazz, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws IOException, WebApplicationException {

		PrintWriter printWriter = new PrintWriter(entityStream, true);

		Stream<PageMessageMapper<T>> stream = _pageMessageMappers.stream();

		String mediaTypeString = mediaType.toString();

		Page<T> page = success.getValue();

		PageMessageMapper<T> pageMessageMapper = stream.filter(
			bodyWriter ->
				mediaTypeString.equals(bodyWriter.getMediaType()) &&
				bodyWriter.supports(page, _httpHeaders)
		).findFirst(
		).orElseThrow(
			() -> new VulcanDeveloperError.MustHaveMessageMapper(
				mediaTypeString, page.getModelClass())
		);

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilderImpl();

		pageMessageMapper.onStart(jsonObjectBuilder, page, _httpHeaders);

		Optional<Fields> fieldsOptional = _providerManager.provide(
			Fields.class, _httpServletRequest);

		Fields fields = fieldsOptional.orElseThrow(
			() -> new VulcanDeveloperError.MustHaveProvider(Fields.class));

		Optional<Embedded> embeddedOptional = _providerManager.provide(
			Embedded.class, _httpServletRequest);

		Embedded embedded = embeddedOptional.orElseThrow(
			() -> new VulcanDeveloperError.MustHaveProvider(Embedded.class));

		_writeItems(
			pageMessageMapper, jsonObjectBuilder, page, fields, embedded);

		_writeItemTotalCount(pageMessageMapper, jsonObjectBuilder, page);

		_writePageCount(pageMessageMapper, jsonObjectBuilder, page);

		_writePageURLs(pageMessageMapper, jsonObjectBuilder, page);

		_writeCollectionURL(pageMessageMapper, jsonObjectBuilder, page);

		pageMessageMapper.onFinish(jsonObjectBuilder, page, _httpHeaders);

		JsonObject jsonObject = jsonObjectBuilder.build();

		printWriter.println(jsonObject.toString());

		printWriter.close();
	}

	private String _getCollectionURL(Page<T> page) {
		Optional<String> optional = _writerHelper.getCollectionURLOptional(
			page, _httpServletRequest);

		return optional.orElseThrow(
			() -> new VulcanDeveloperError.UnresolvableURI(
				page.getModelClass()));
	}

	private String _getPageURL(Page<T> page, int pageNumber, int itemsPerPage) {
		String url = _getCollectionURL(page);

		return url + "?page=" + pageNumber + "&per_page=" + itemsPerPage;
	}

	private void _writeCollectionURL(
		PageMessageMapper<T> pageMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, Page<T> page) {

		String url = _getCollectionURL(page);

		pageMessageMapper.mapCollectionURL(jsonObjectBuilder, url);
	}

	private <U, V> void _writeEmbeddedRelatedModel(
		PageMessageMapper<?> pageMessageMapper,
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		RelatedModel<U, V> relatedModel, SingleModel<U> parentSingleModel,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields,
		Embedded embedded) {

		_writerHelper.writeRelatedModel(
			relatedModel, parentSingleModel, parentEmbeddedPathElements,
			_httpServletRequest, fields, embedded,
			(singleModel, embeddedPathElements) -> {
				Class<V> modelClass = singleModel.getModelClass();

				_writerHelper.writeBooleanFields(
					singleModel.getModel(), modelClass, fields,
					(fieldName, value) ->
						pageMessageMapper.mapItemEmbeddedResourceBooleanField(
							pageJSONObjectBuilder, itemJSONObjectBuilder,
							embeddedPathElements, fieldName, value));

				_writerHelper.writeNumberFields(
					singleModel.getModel(), modelClass, fields,
					(fieldName, value) ->
						pageMessageMapper.mapItemEmbeddedResourceNumberField(
							pageJSONObjectBuilder, itemJSONObjectBuilder,
							embeddedPathElements, fieldName, value));

				_writerHelper.writeStringFields(
					singleModel.getModel(), modelClass, fields,
					(fieldName, value) ->
						pageMessageMapper.mapItemEmbeddedResourceStringField(
							pageJSONObjectBuilder, itemJSONObjectBuilder,
							embeddedPathElements, fieldName, value));

				_writerHelper.writeLinks(
					modelClass, fields,
					(fieldName, link) ->
						pageMessageMapper.mapItemEmbeddedResourceLink(
							pageJSONObjectBuilder, itemJSONObjectBuilder,
							embeddedPathElements, fieldName, link));

				_writerHelper.writeTypes(
					modelClass,
					types -> pageMessageMapper.mapItemEmbeddedResourceTypes(
						pageJSONObjectBuilder, itemJSONObjectBuilder,
						embeddedPathElements, types));

				Optional<Representor<V, Identifier>> representorOptional =
					_collectionResourceManager.getRepresentorOptional(
						modelClass);

				representorOptional.ifPresent(
					representor -> {
						Map<String, BinaryFunction<V>> binaryFunctions =
							representor.getBinaryFunctions();

						_writerHelper.writeBinaries(
							binaryFunctions, singleModel, _httpServletRequest,
							(fieldName, value) ->
								pageMessageMapper.mapItemStringField(
									pageJSONObjectBuilder,
									itemJSONObjectBuilder, fieldName, value));

						List<RelatedModel<V, ?>> embeddedRelatedModels =
							representor.getEmbeddedRelatedModels();

						embeddedRelatedModels.forEach(
							embeddedRelatedModel -> _writeEmbeddedRelatedModel(
								pageMessageMapper, pageJSONObjectBuilder,
								itemJSONObjectBuilder, embeddedRelatedModel,
								singleModel, embeddedPathElements, fields,
								embedded));

						List<RelatedModel<V, ?>> linkedRelatedModels =
							representor.getLinkedRelatedModels();

						linkedRelatedModels.forEach(
							linkedRelatedModel -> _writeLinkedRelatedModel(
								pageMessageMapper, pageJSONObjectBuilder,
								itemJSONObjectBuilder, linkedRelatedModel,
								singleModel, embeddedPathElements, fields,
								embedded));

						Stream<RelatedCollection<V, ?>> stream =
							representor.getRelatedCollections();

						stream.forEach(
							relatedCollection -> _writeRelatedCollection(
								pageMessageMapper, pageJSONObjectBuilder,
								itemJSONObjectBuilder, relatedCollection,
								singleModel, embeddedPathElements, fields));
					});
			},
			(url, embeddedPathElements, isEmbedded) -> {
				if (isEmbedded) {
					pageMessageMapper.mapItemEmbeddedResourceURL(
						pageJSONObjectBuilder, itemJSONObjectBuilder,
						embeddedPathElements, url);
				}
				else {
					pageMessageMapper.mapItemLinkedResourceURL(
						pageJSONObjectBuilder, itemJSONObjectBuilder,
						embeddedPathElements, url);
				}
			});
	}

	private void _writeItems(
		PageMessageMapper<T> pageMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, Page<T> page, Fields fields,
		Embedded embedded) {

		Collection<T> items = page.getItems();

		items.forEach(
			item -> {
				JSONObjectBuilder itemJSONObjectBuilder =
					new JSONObjectBuilderImpl();

				Class<T> modelClass = page.getModelClass();

				pageMessageMapper.onStartItem(
					jsonObjectBuilder, itemJSONObjectBuilder, item, modelClass,
					_httpHeaders);

				_writerHelper.writeBooleanFields(
					item, modelClass, fields,
					(field, value) -> pageMessageMapper.mapItemBooleanField(
						jsonObjectBuilder, itemJSONObjectBuilder, field,
						value));

				_writerHelper.writeNumberFields(
					item, modelClass, fields,
					(field, value) -> pageMessageMapper.mapItemNumberField(
						jsonObjectBuilder, itemJSONObjectBuilder, field,
						value));

				_writerHelper.writeStringFields(
					item, modelClass, fields,
					(field, value) -> pageMessageMapper.mapItemStringField(
						jsonObjectBuilder, itemJSONObjectBuilder, field,
						value));

				_writerHelper.writeLinks(
					modelClass, fields,
					(fieldName, link) -> pageMessageMapper.mapItemLink(
						jsonObjectBuilder, itemJSONObjectBuilder, fieldName,
						link));

				_writerHelper.writeTypes(
					modelClass,
					types -> pageMessageMapper.mapItemTypes(
						jsonObjectBuilder, itemJSONObjectBuilder, types));

				SingleModel<T> singleModel = new SingleModel<>(
					item, modelClass);

				Optional<String> optional = _writerHelper.getSingleURLOptional(
					singleModel, _httpServletRequest);

				optional.ifPresent(
					url -> pageMessageMapper.mapItemSelfURL(
						jsonObjectBuilder, itemJSONObjectBuilder, url));

				Optional<Representor<T, Identifier>> representorOptional =
					_collectionResourceManager.getRepresentorOptional(
						modelClass);

				representorOptional.ifPresent(
					representor -> {
						Map<String, BinaryFunction<T>> binaryFunctions =
							representor.getBinaryFunctions();

						_writerHelper.writeBinaries(
							binaryFunctions, singleModel, _httpServletRequest,
							(fieldName, value) ->
								pageMessageMapper.mapItemStringField(
									jsonObjectBuilder, itemJSONObjectBuilder,
									fieldName, value));

						List<RelatedModel<T, ?>> embeddedRelatedModels =
							representor.getEmbeddedRelatedModels();

						embeddedRelatedModels.forEach(
							embeddedRelatedModel -> _writeEmbeddedRelatedModel(
								pageMessageMapper, jsonObjectBuilder,
								itemJSONObjectBuilder, embeddedRelatedModel,
								singleModel, null, fields, embedded));

						List<RelatedModel<T, ?>> linkedRelatedModels =
							representor.getLinkedRelatedModels();

						linkedRelatedModels.forEach(
							linkedRelatedModel -> _writeLinkedRelatedModel(
								pageMessageMapper, jsonObjectBuilder,
								itemJSONObjectBuilder, linkedRelatedModel,
								singleModel, null, fields, embedded));

						Stream<RelatedCollection<T, ?>> stream =
							representor.getRelatedCollections();

						stream.forEach(
							relatedCollection -> _writeRelatedCollection(
								pageMessageMapper, jsonObjectBuilder,
								itemJSONObjectBuilder, relatedCollection,
								singleModel, null, fields));
					});

				pageMessageMapper.onFinishItem(
					jsonObjectBuilder, itemJSONObjectBuilder, item, modelClass,
					_httpHeaders);
			});
	}

	private void _writeItemTotalCount(
		PageMessageMapper<T> pageMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, Page<T> page) {

		pageMessageMapper.mapItemTotalCount(
			jsonObjectBuilder, page.getTotalCount());
	}

	private <U, V> void _writeLinkedRelatedModel(
		PageMessageMapper<?> pageMessageMapper,
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		RelatedModel<U, V> relatedModel, SingleModel<U> parentSingleModel,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields,
		Embedded embedded) {

		_writerHelper.writeLinkedRelatedModel(
			relatedModel, parentSingleModel, parentEmbeddedPathElements,
			_httpServletRequest, fields, embedded,
			(url, embeddedPathElements) ->
				pageMessageMapper.mapItemLinkedResourceURL(
					pageJSONObjectBuilder, itemJSONObjectBuilder,
					embeddedPathElements, url));
	}

	private void _writePageCount(
		PageMessageMapper<T> pageMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, Page<T> page) {

		Collection<T> items = page.getItems();

		pageMessageMapper.mapPageCount(jsonObjectBuilder, items.size());
	}

	private void _writePageURLs(
		PageMessageMapper<T> pageMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, Page<T> page) {

		pageMessageMapper.mapCurrentPageURL(
			jsonObjectBuilder,
			_getPageURL(page, page.getPageNumber(), page.getItemsPerPage()));

		pageMessageMapper.mapFirstPageURL(
			jsonObjectBuilder, _getPageURL(page, 1, page.getItemsPerPage()));

		if (page.hasPrevious()) {
			pageMessageMapper.mapPreviousPageURL(
				jsonObjectBuilder,
				_getPageURL(
					page, page.getPageNumber() - 1, page.getItemsPerPage()));
		}

		if (page.hasNext()) {
			pageMessageMapper.mapNextPageURL(
				jsonObjectBuilder,
				_getPageURL(
					page, page.getPageNumber() + 1, page.getItemsPerPage()));
		}

		pageMessageMapper.mapLastPageURL(
			jsonObjectBuilder,
			_getPageURL(
				page, page.getLastPageNumber(), page.getItemsPerPage()));
	}

	private <U, V> void _writeRelatedCollection(
		PageMessageMapper<?> pageMessageMapper,
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		RelatedCollection<U, V> relatedCollection,
		SingleModel<U> parentSingleModel,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields) {

		_writerHelper.writeRelatedCollection(
			relatedCollection, parentSingleModel, parentEmbeddedPathElements,
			_httpServletRequest, fields,
			(url, embeddedPathElements) ->
				pageMessageMapper.mapItemLinkedResourceURL(
					pageJSONObjectBuilder, itemJSONObjectBuilder,
					embeddedPathElements, url));
	}

	@Reference
	private CollectionResourceManager _collectionResourceManager;

	@Context
	private HttpHeaders _httpHeaders;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference(cardinality = AT_LEAST_ONE, policyOption = GREEDY)
	private List<PageMessageMapper<T>> _pageMessageMappers;

	@Reference
	private ProviderManager _providerManager;

	@Context
	private ResourceInfo _resourceInfo;

	@Reference
	private WriterHelper _writerHelper;

}