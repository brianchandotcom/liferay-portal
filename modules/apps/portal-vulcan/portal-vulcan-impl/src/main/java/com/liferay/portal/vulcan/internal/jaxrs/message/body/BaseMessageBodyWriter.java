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

package com.liferay.portal.vulcan.internal.jaxrs.message.body;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.vulcan.fields.FieldsQueryParam;
import com.liferay.portal.vulcan.fields.RestrictFieldsQueryParam;
import com.liferay.portal.vulcan.internal.jackson.databind.ser.DynamicPropertyFilter;
import com.liferay.portal.vulcan.internal.jackson.databind.ser.ExtendedEntityPropertyFilter;
import com.liferay.portal.vulcan.internal.jackson.databind.ser.VulcanDynamicPropertyFilter;
import com.liferay.portal.vulcan.internal.jackson.databind.ser.VulcanPropertyFilter;
import com.liferay.portal.vulcan.internal.jaxrs.serializer.JSONArrayStdSerializer;
import com.liferay.portal.vulcan.internal.jaxrs.serializer.PageJsonSerializer;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Optional;
import java.util.Set;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Providers;

/**
 * @author Alejandro Hernández
 * @author Ivica Cardic
 */
public abstract class BaseMessageBodyWriter
	implements MessageBodyWriter<Object> {

	public BaseMessageBodyWriter(
		Class<? extends ObjectMapper> contextType, MediaType mediaType) {

		_contextType = contextType;
		_mediaType = mediaType;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		ObjectMapper objectMapper = _getObjectMapper(clazz);

		return objectMapper.canSerialize(clazz);
	}

	@Override
	public void writeTo(
			Object object, Class<?> clazz, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream)
		throws IOException, WebApplicationException {

		ObjectMapper objectMapper = _getObjectMapper(clazz);

		SimpleModule simpleModule = new SimpleModule();

		simpleModule.addSerializer(
			JSONArray.class, new JSONArrayStdSerializer(JSONArray.class));

		if (mediaType.equals(MediaType.APPLICATION_XML_TYPE)) {
			simpleModule.addSerializer(
				(Class<Page<Object>>)(Class<?>)Page.class,
				new PageJsonSerializer());
		}

		objectMapper.registerModule(simpleModule);

		ObjectWriter objectWriter = objectMapper.writerFor(
			objectMapper.constructType(genericType));

		objectWriter.writeValue(outputStream, object);

		outputStream.flush();
	}

	private ObjectMapper _addFilter(ObjectMapper objectMapper) {
		objectMapper.setFilterProvider(
			new SimpleFilterProvider() {
				{
					PropertyFilter propertyFilter = null;

					Set<String> fieldNames = _fieldsQueryParam.getFieldNames();
					Set<String> restrictFieldNames =
						_restrictFieldsQueryParam.getRestrictFieldNames();

					if ((fieldNames == null) && (restrictFieldNames == null)) {
						propertyFilter =
							SimpleBeanPropertyFilter.serializeAll();
					}
					else {
						propertyFilter = VulcanPropertyFilter.of(
							fieldNames, restrictFieldNames);
					}

					DynamicPropertyFilter dynamicPropertyFilter =
						VulcanDynamicPropertyFilter.of(propertyFilter);

					addFilter("Liferay.Vulcan", dynamicPropertyFilter);
					addFilter(
						"ExtendedEntityPropertyFilter",
						ExtendedEntityPropertyFilter.with(
							dynamicPropertyFilter));
				}
			});

		return objectMapper;
	}

	private ObjectMapper _getObjectMapper(Class<?> clazz) {
		return Optional.ofNullable(
			_providers.getContextResolver(_contextType, _mediaType)
		).map(
			contextResolver -> contextResolver.getContext(clazz)
		).map(
			this::_addFilter
		).orElseThrow(
			() -> new InternalServerErrorException(
				"Unable to generate object mapper for class " + clazz)
		);
	}

	private final Class<? extends ObjectMapper> _contextType;

	@Context
	private FieldsQueryParam _fieldsQueryParam;

	private final MediaType _mediaType;

	@Context
	private Providers _providers;

	@Context
	private RestrictFieldsQueryParam _restrictFieldsQueryParam;

}