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

import static org.osgi.service.component.annotations.ReferenceCardinality.AT_LEAST_ONE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.vulcan.error.VulcanDeveloperError;
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.RequestInfo;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.PageMessageMapper;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.wiring.osgi.ProviderManager;
import com.liferay.vulcan.wiring.osgi.RelatedModel;
import com.liferay.vulcan.wiring.osgi.ResourceManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = "liferay.vulcan.message.body.writer=true"
)
@Provider
public class PageMessageBodyWriter<T> implements MessageBodyWriter<Page<T>> {

	@Override
	public long getSize(
		Page<T> page, Class<?> clazz, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		Method resourceMethod = _resourceInfo.getResourceMethod();

		Class<?> returnType = resourceMethod.getReturnType();

		if (!returnType.isAssignableFrom(Page.class)) {
			return false;
		}

		return true;
	}

	@Override
	public void writeTo(
			Page<T> page, Class<?> clazz, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws IOException, WebApplicationException {

		PrintWriter printWriter = new PrintWriter(entityStream, true);

		Stream<PageMessageMapper<T>> stream = _pageMessageMappers.stream();

		String mediaTypeString = mediaType.toString();

		Class<T> modelClass = page.getModelClass();

		RequestInfo requestInfo = new RequestInfoImpl(mediaType, httpHeaders);

		PageMessageMapper<T> pageMessageMapper = stream.filter(
			bodyWriter ->
				mediaTypeString.equals(bodyWriter.getMediaType()) &&
				bodyWriter.supports(page, modelClass, requestInfo)
		).findFirst().orElseThrow(
			() -> new VulcanDeveloperError.MustHaveMessageMapper(
				mediaTypeString, modelClass)
		);

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilderImpl();

		pageMessageMapper.onStart(
			jsonObjectBuilder, page, modelClass, requestInfo);

		Optional<Fields> optionalFields = _providerManager.provide(
			Fields.class, _httpServletRequest);

		Optional<Embedded> optionalEmbedded = _providerManager.provide(
			Embedded.class, _httpServletRequest);

		Fields fields = optionalFields.orElseThrow(
			() -> new VulcanDeveloperError.MustHaveProvider(Fields.class));

		Embedded embedded = optionalEmbedded.orElseThrow(
			() -> new VulcanDeveloperError.MustHaveProvider(Embedded.class));

		_writeItems(
			pageMessageMapper, jsonObjectBuilder, page, modelClass, requestInfo,
			fields, embedded);

		_writeItemTotalCount(pageMessageMapper, jsonObjectBuilder, page);

		_writePageCount(pageMessageMapper, jsonObjectBuilder, page);

		_writePageURLs(pageMessageMapper, jsonObjectBuilder, page, modelClass);

		_writeCollectionURL(pageMessageMapper, jsonObjectBuilder, modelClass);

		pageMessageMapper.onFinish(
			jsonObjectBuilder, page, modelClass, requestInfo);

		JSONObject jsonObject = jsonObjectBuilder.build();

		printWriter.println(jsonObject.toString());

		printWriter.close();
	}

	private String _getCollectionURL(Class<T> modelClass) {
		Optional<String> optional = _writerHelper.getCollectionURL(
			modelClass, _uriInfo);

		return optional.orElseThrow(
			() -> new VulcanDeveloperError.UnresolvableURI(modelClass));
	}

	private String _getPageURL(
		Class<T> modelClass, int page, int itemsPerPage) {

		String url = _getCollectionURL(modelClass);

		return url + "?page=" + page + "&per_page=" + itemsPerPage;
	}

	private void _writeCollectionURL(
		PageMessageMapper<T> pageMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, Class<T> modelClass) {

		String url = _getCollectionURL(modelClass);

		pageMessageMapper.mapCollectionURL(jsonObjectBuilder, url);
	}

	private <U, V> void _writeEmbeddedRelatedModel(
		PageMessageMapper<?> pageMessageMapper,
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		RelatedModel<U, V> relatedModel, U parentModel,
		Class<U> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields,
		Embedded embedded) {

		_writerHelper.writeRelatedModel(
			relatedModel, parentModel, parentModelClass,
			parentEmbeddedPathElements, _uriInfo, fields, embedded,
			(model, modelClass, embeddedPathElements) -> {
				_writerHelper.writeFields(
					model, modelClass, fields,
					(fieldName, value) ->
						pageMessageMapper.mapItemEmbeddedResourceField(
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

				List<RelatedModel<V, ?>> embeddedRelatedModels =
					_resourceManager.getEmbeddedRelatedModels(modelClass);

				embeddedRelatedModels.forEach(
					embeddedRelatedModel -> _writeEmbeddedRelatedModel(
						pageMessageMapper, pageJSONObjectBuilder,
						itemJSONObjectBuilder, embeddedRelatedModel, model,
						modelClass, embeddedPathElements, fields, embedded));

				List<RelatedModel<V, ?>> linkedRelatedModels =
					_resourceManager.getLinkedRelatedModels(modelClass);

				linkedRelatedModels.forEach(
					linkedRelatedModel -> _writeLinkedRelatedModel(
						pageMessageMapper, pageJSONObjectBuilder,
						itemJSONObjectBuilder, linkedRelatedModel, model,
						modelClass, embeddedPathElements, fields, embedded));
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
		JSONObjectBuilder jsonObjectBuilder, Page<T> page, Class<T> modelClass,
		RequestInfo requestInfo, Fields fields, Embedded embedded) {

		Collection<T> items = page.getItems();

		items.forEach(
			item -> {
				JSONObjectBuilder itemJSONObjectBuilder =
					new JSONObjectBuilderImpl();

				pageMessageMapper.onStartItem(
					jsonObjectBuilder, itemJSONObjectBuilder, item, modelClass,
					requestInfo);

				_writerHelper.writeFields(
					item, modelClass, fields,
					(field, value) -> pageMessageMapper.mapItemField(
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

				_writerHelper.writeSingleResourceURL(
					item, modelClass, _uriInfo,
					url -> pageMessageMapper.mapItemSelfURL(
						jsonObjectBuilder, itemJSONObjectBuilder, url));

				List<RelatedModel<T, ?>> embeddedRelatedModels =
					_resourceManager.getEmbeddedRelatedModels(modelClass);

				embeddedRelatedModels.forEach(
					embeddedRelatedModel -> _writeEmbeddedRelatedModel(
						pageMessageMapper, jsonObjectBuilder,
						itemJSONObjectBuilder, embeddedRelatedModel, item,
						modelClass, null, fields, embedded));

				List<RelatedModel<T, ?>> linkedRelatedModels =
					_resourceManager.getLinkedRelatedModels(modelClass);

				linkedRelatedModels.forEach(
					linkedRelatedModel -> _writeLinkedRelatedModel(
						pageMessageMapper, jsonObjectBuilder,
						itemJSONObjectBuilder, linkedRelatedModel, item,
						modelClass, null, fields, embedded));

				pageMessageMapper.onFinishItem(
					jsonObjectBuilder, itemJSONObjectBuilder, item, modelClass,
					requestInfo);
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
		RelatedModel<U, V> relatedModel, U parentModel,
		Class<U> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields,
		Embedded embedded) {

		_writerHelper.writeLinkedRelatedModel(
			relatedModel, parentModel, parentModelClass,
			parentEmbeddedPathElements, _uriInfo, fields, embedded,
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
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		Class<T> modelClass) {

		pageMessageMapper.mapCurrentPageURL(
			jsonObjectBuilder,
			_getPageURL(
				modelClass, page.getPageNumber(), page.getItemsPerPage()));

		pageMessageMapper.mapFirstPageURL(
			jsonObjectBuilder,
			_getPageURL(modelClass, 1, page.getItemsPerPage()));

		if (page.hasPrevious()) {
			pageMessageMapper.mapPreviousPageURL(
				jsonObjectBuilder,
				_getPageURL(
					modelClass, page.getPageNumber() - 1,
					page.getItemsPerPage()));
		}

		if (page.hasNext()) {
			pageMessageMapper.mapNextPageURL(
				jsonObjectBuilder,
				_getPageURL(
					modelClass, page.getPageNumber() + 1,
					page.getItemsPerPage()));
		}

		pageMessageMapper.mapLastPageURL(
			jsonObjectBuilder,
			_getPageURL(
				modelClass, page.getLastPageNumber(), page.getItemsPerPage()));
	}

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference(cardinality = AT_LEAST_ONE, policyOption = GREEDY)
	private List<PageMessageMapper<T>> _pageMessageMappers;

	@Reference
	private ProviderManager _providerManager;

	@Context
	private ResourceInfo _resourceInfo;

	@Reference
	private ResourceManager _resourceManager;

	@Context
	private UriInfo _uriInfo;

	@Reference
	private WriterHelper _writerHelper;

}