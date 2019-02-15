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

package com.liferay.headless.web.experience.internal.jaxrs.message.body.v1_0;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.web.experience.dto.v1_0.AggregateRating;
import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.dto.v1_0.ContentDocument;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.Creator;
import com.liferay.headless.web.experience.dto.v1_0.Fields;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.Options;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsByTemplate;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.dto.v1_0.Values;
import com.liferay.headless.web.experience.internal.dto.v1_0.AggregateRatingImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentDocumentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentStructureImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.FieldsImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.GeoImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.OptionsImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.RenderedContentsByTemplateImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ValueImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ValuesImpl;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Headless.Web.Experience)",
		"osgi.jaxrs.name=Liferay.Headless.Web.Experience.v1_0.JSONMessageBodyReader"
	},
	service = MessageBodyReader.class
)
@Consumes(MediaType.APPLICATION_JSON)
@Generated("")
@Provider
public class JSONMessageBodyReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

			if (clazz.equals(AggregateRating.class)) {
				return true;
	}
			if (clazz.equals(Comment.class)) {
				return true;
	}
			if (clazz.equals(ContentDocument.class)) {
				return true;
	}
			if (clazz.equals(ContentStructure.class)) {
				return true;
	}
			if (clazz.equals(Creator.class)) {
				return true;
	}
			if (clazz.equals(Fields.class)) {
				return true;
	}
			if (clazz.equals(Geo.class)) {
				return true;
	}
			if (clazz.equals(Options.class)) {
				return true;
	}
			if (clazz.equals(RenderedContentsByTemplate.class)) {
				return true;
	}
			if (clazz.equals(StructuredContent.class)) {
				return true;
	}
			if (clazz.equals(Value.class)) {
				return true;
	}
			if (clazz.equals(Values.class)) {
				return true;
	}

		return false;
	}

	@Override
	public Object readFrom(
			Class<Object> clazz, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> multivaluedMap,
			InputStream inputStream)
		throws IOException, WebApplicationException {

			if (clazz.equals(AggregateRating.class)) {
				return _objectMapper.readValue(inputStream, AggregateRatingImpl.class);
	}
			if (clazz.equals(Comment.class)) {
				return _objectMapper.readValue(inputStream, CommentImpl.class);
	}
			if (clazz.equals(ContentDocument.class)) {
				return _objectMapper.readValue(inputStream, ContentDocumentImpl.class);
	}
			if (clazz.equals(ContentStructure.class)) {
				return _objectMapper.readValue(inputStream, ContentStructureImpl.class);
	}
			if (clazz.equals(Creator.class)) {
				return _objectMapper.readValue(inputStream, CreatorImpl.class);
	}
			if (clazz.equals(Fields.class)) {
				return _objectMapper.readValue(inputStream, FieldsImpl.class);
	}
			if (clazz.equals(Geo.class)) {
				return _objectMapper.readValue(inputStream, GeoImpl.class);
	}
			if (clazz.equals(Options.class)) {
				return _objectMapper.readValue(inputStream, OptionsImpl.class);
	}
			if (clazz.equals(RenderedContentsByTemplate.class)) {
				return _objectMapper.readValue(inputStream, RenderedContentsByTemplateImpl.class);
	}
			if (clazz.equals(StructuredContent.class)) {
				return _objectMapper.readValue(inputStream, StructuredContentImpl.class);
	}
			if (clazz.equals(Value.class)) {
				return _objectMapper.readValue(inputStream, ValueImpl.class);
	}
			if (clazz.equals(Values.class)) {
				return _objectMapper.readValue(inputStream, ValuesImpl.class);
	}

		return null;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			setDateFormat(new ISO8601DateFormat());
	}
	};

}