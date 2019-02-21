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

package com.liferay.headless.web.experience.internal.jaxrs.context.resolver.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.web.experience.dto.v1_0.AggregateRating;
import com.liferay.headless.web.experience.dto.v1_0.Categories;
import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.dto.v1_0.ContentDocument;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.Creator;
import com.liferay.headless.web.experience.dto.v1_0.Fields;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.Options;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsByTemplate;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage;
import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.dto.v1_0.Values;
import com.liferay.headless.web.experience.internal.dto.v1_0.AggregateRatingImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.CategoriesImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentDocumentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentStructureImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.FieldsImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.GeoImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.OptionsImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.RenderedContentsByTemplateImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImageImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ValueImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ValuesImpl;

import javax.annotation.Generated;

import javax.ws.rs.ext.ContextResolver;
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
		"osgi.jaxrs.name=Liferay.Headless.Web.Experience.v1_0.ObjectMapperContextResolver"
	},
	service = ContextResolver.class
)
@Generated("")
@Provider
public class ObjectMapperContextResolver
	implements ContextResolver<ObjectMapper> {

	public ObjectMapper getContext(Class<?> aClass) {
		return _objectMapper;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			registerModule(
				new SimpleModule("Liferay.Headless.Web.Experience", Version.unknownVersion()) {
					{
						setAbstractTypes(
							new SimpleAbstractTypeResolver() {
								{
									addMapping(AggregateRating.class, AggregateRatingImpl.class);
									addMapping(Categories.class, CategoriesImpl.class);
									addMapping(Comment.class, CommentImpl.class);
									addMapping(ContentDocument.class, ContentDocumentImpl.class);
									addMapping(ContentStructure.class, ContentStructureImpl.class);
									addMapping(Creator.class, CreatorImpl.class);
									addMapping(Fields.class, FieldsImpl.class);
									addMapping(Geo.class, GeoImpl.class);
									addMapping(Options.class, OptionsImpl.class);
									addMapping(RenderedContentsByTemplate.class, RenderedContentsByTemplateImpl.class);
									addMapping(StructuredContent.class, StructuredContentImpl.class);
									addMapping(StructuredContentImage.class, StructuredContentImageImpl.class);
									addMapping(Value.class, ValueImpl.class);
									addMapping(Values.class, ValuesImpl.class);
	}
							});
	}
				});

			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			enable(SerializationFeature.INDENT_OUTPUT);
			setDateFormat(new ISO8601DateFormat());
	}
	};

}