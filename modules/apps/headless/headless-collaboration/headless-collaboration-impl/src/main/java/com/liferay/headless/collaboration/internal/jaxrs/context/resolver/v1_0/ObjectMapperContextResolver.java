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

package com.liferay.headless.collaboration.internal.jaxrs.context.resolver.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.collaboration.dto.v1_0.AggregateRating;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.dto.v1_0.Categories;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.dto.v1_0.Creator;
import com.liferay.headless.collaboration.dto.v1_0.Image;
import com.liferay.headless.collaboration.internal.dto.v1_0.AggregateRatingImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImageImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.CategoriesImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.ImageImpl;

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
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Headless.Collaboration)",
		"osgi.jaxrs.name=Liferay.Headless.Collaboration.v1_0.ObjectMapperContextResolver"
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
				new SimpleModule("Liferay.Headless.Collaboration", Version.unknownVersion()) {
					{
						setAbstractTypes(
							new SimpleAbstractTypeResolver() {
								{
									addMapping(AggregateRating.class, AggregateRatingImpl.class);
									addMapping(BlogPosting.class, BlogPostingImpl.class);
									addMapping(BlogPostingImage.class, BlogPostingImageImpl.class);
									addMapping(Categories.class, CategoriesImpl.class);
									addMapping(Comment.class, CommentImpl.class);
									addMapping(Creator.class, CreatorImpl.class);
									addMapping(Image.class, ImageImpl.class);
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