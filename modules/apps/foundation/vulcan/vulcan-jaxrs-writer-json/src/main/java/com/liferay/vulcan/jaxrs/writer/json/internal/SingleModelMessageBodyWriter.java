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
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.RequestInfo;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.SingleModelMessageMapper;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.wiring.osgi.ProviderManager;
import com.liferay.vulcan.wiring.osgi.RelatedModel;
import com.liferay.vulcan.wiring.osgi.ResourceManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
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
public class SingleModelMessageBodyWriter<T>
	implements MessageBodyWriter<SingleModel<T>> {

	@Override
	public long getSize(
		SingleModel<T> model, Class<?> clazz, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		if (clazz.isAssignableFrom(SingleModel.class)) {
			return true;
		}

		return false;
	}

	@Override
	public void writeTo(
			SingleModel<T> singleModel, Class<?> clazz, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws IOException, WebApplicationException {

		PrintWriter printWriter = new PrintWriter(entityStream, true);

		Class<T> modelClass = singleModel.getModelClass();

		T model = singleModel.getModel();

		Stream<SingleModelMessageMapper<T>> stream =
			_singleModelMessageMappers.stream();

		String mediaTypeString = mediaType.toString();

		RequestInfo requestInfo = new RequestInfoImpl(mediaType, httpHeaders);

		SingleModelMessageMapper<T> singleModelMessageMapper = stream.filter(
			messageMapper ->
				mediaTypeString.equals(messageMapper.getMediaType()) &&
				messageMapper.supports(model, modelClass, requestInfo)
		).findFirst().orElseThrow(
			() -> new VulcanDeveloperError.MustHaveMessageMapper(
				mediaTypeString, modelClass)
		);

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilderImpl();

		Optional<Fields> optionalFields = _providerManager.provide(
			Fields.class, _httpServletRequest);

		Optional<Embedded> optionalEmbedded = _providerManager.provide(
			Embedded.class, _httpServletRequest);

		Fields fields = optionalFields.orElseThrow(
			() -> new MustHaveProvider(Fields.class));

		Embedded embedded = optionalEmbedded.orElseThrow(
			() -> new MustHaveProvider(Embedded.class));

		_writeModel(
			singleModelMessageMapper, jsonObjectBuilder, model, modelClass,
			requestInfo, fields, embedded);

		JSONObject jsonObject = jsonObjectBuilder.build();

		printWriter.println(jsonObject.toString());

		printWriter.close();
	}

	private <U, V> void _writeEmbeddedRelatedModel(
		SingleModelMessageMapper<?> singleModelMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, RelatedModel<U, V> relatedModel,
		U parentModel, Class<U> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields,
		Embedded embedded) {

		_writerHelper.writeRelatedModel(
			relatedModel, parentModel, parentModelClass,
			parentEmbeddedPathElements, _uriInfo, fields, embedded,
			(model, modelClass, embeddedPathElements) -> {
				_writerHelper.writeFields(
					model, modelClass, fields,
					(fieldName, value) ->
						singleModelMessageMapper.mapEmbeddedResourceField(
							jsonObjectBuilder, embeddedPathElements, fieldName,
							value));

				_writerHelper.writeLinks(
					modelClass, fields,
					(fieldName, link) ->
						singleModelMessageMapper.mapEmbeddedResourceLink(
							jsonObjectBuilder, embeddedPathElements, fieldName,
							link));

				_writerHelper.writeTypes(
					modelClass,
					types -> singleModelMessageMapper.mapEmbeddedResourceTypes(
						jsonObjectBuilder, embeddedPathElements, types));

				List<RelatedModel<V, ?>> embeddedRelatedModels =
					_resourceManager.getEmbeddedRelatedModels(modelClass);

				embeddedRelatedModels.forEach(
					embeddedRelatedModel -> _writeEmbeddedRelatedModel(
						singleModelMessageMapper, jsonObjectBuilder,
						embeddedRelatedModel, model, modelClass,
						embeddedPathElements, fields, embedded));

				List<RelatedModel<V, ?>> linkedRelatedModels =
					_resourceManager.getLinkedRelatedModels(modelClass);

				linkedRelatedModels.forEach(
					linkedRelatedModel -> _writeLinkedRelatedModel(
						singleModelMessageMapper, jsonObjectBuilder,
						linkedRelatedModel, model, modelClass,
						embeddedPathElements, fields, embedded));
			},
			(url, embeddedPathElements, isEmbedded) -> {
				if (isEmbedded) {
					singleModelMessageMapper.mapEmbeddedResourceURL(
						jsonObjectBuilder, embeddedPathElements, url);
				}
				else {
					singleModelMessageMapper.mapLinkedResourceURL(
						jsonObjectBuilder, embeddedPathElements, url);
				}
			});
	}

	private <U, V> void _writeLinkedRelatedModel(
		SingleModelMessageMapper<?> singleModelMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, RelatedModel<U, V> relatedModel,
		U parentModel, Class<U> parentModelClass,
		FunctionalList<String> parentEmbeddedPathElements, Fields fields,
		Embedded embedded) {

		_writerHelper.writeLinkedRelatedModel(
			relatedModel, parentModel, parentModelClass,
			parentEmbeddedPathElements, _uriInfo, fields, embedded,
			(url, embeddedPathElements) ->
				singleModelMessageMapper.mapLinkedResourceURL(
					jsonObjectBuilder, embeddedPathElements, url));
	}

	private <U> void _writeModel(
		SingleModelMessageMapper<U> singleModelMessageMapper,
		JSONObjectBuilder jsonObjectBuilder, U model, Class<U> modelClass,
		RequestInfo requestInfo, Fields fields, Embedded embedded) {

		singleModelMessageMapper.onStart(
			jsonObjectBuilder, model, modelClass, requestInfo);

		_writerHelper.writeFields(
			model, modelClass, fields,
			(field, value) -> singleModelMessageMapper.mapField(
				jsonObjectBuilder, field, value));

		_writerHelper.writeLinks(
			modelClass, fields,
			(fieldName, link) -> singleModelMessageMapper.mapLink(
				jsonObjectBuilder, fieldName, link));

		_writerHelper.writeTypes(
			modelClass,
			types -> singleModelMessageMapper.mapTypes(
				jsonObjectBuilder, types));

		_writerHelper.writeSingleResourceURL(
			model, modelClass, _uriInfo,
			url -> singleModelMessageMapper.mapSelfURL(jsonObjectBuilder, url));

		List<RelatedModel<U, ?>> embeddedRelatedModels =
			_resourceManager.getEmbeddedRelatedModels(modelClass);

		embeddedRelatedModels.forEach(
			embeddedRelatedModel -> _writeEmbeddedRelatedModel(
				singleModelMessageMapper, jsonObjectBuilder,
				embeddedRelatedModel, model, modelClass, null, fields,
				embedded));

		List<RelatedModel<U, ?>> linkedRelatedModels =
			_resourceManager.getLinkedRelatedModels(modelClass);

		linkedRelatedModels.forEach(
			linkedRelatedModel -> _writeLinkedRelatedModel(
				singleModelMessageMapper, jsonObjectBuilder, linkedRelatedModel,
				model, modelClass, null, fields, embedded));

		singleModelMessageMapper.onFinish(
			jsonObjectBuilder, model, modelClass, requestInfo);
	}

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private ResourceManager _resourceManager;

	@Reference(cardinality = AT_LEAST_ONE, policyOption = GREEDY)
	private List<SingleModelMessageMapper<T>> _singleModelMessageMappers;

	@Context
	private UriInfo _uriInfo;

	@Reference
	private WriterHelper _writerHelper;

}