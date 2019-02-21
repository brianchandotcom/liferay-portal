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

package com.liferay.headless.document.library.internal.jaxrs.context.resolver.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.document.library.dto.v1_0.AdaptedImages;
import com.liferay.headless.document.library.dto.v1_0.AggregateRating;
import com.liferay.headless.document.library.dto.v1_0.Categories;
import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Creator;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.internal.dto.v1_0.AdaptedImagesImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.AggregateRatingImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.CategoriesImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.DocumentImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.FolderImpl;

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
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Headless.Document.Library)",
		"osgi.jaxrs.name=Liferay.Headless.Document.Library.v1_0.ObjectMapperContextResolver"
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
				new SimpleModule("Liferay.Headless.Document.Library", Version.unknownVersion()) {
					{
						setAbstractTypes(
							new SimpleAbstractTypeResolver() {
								{
									addMapping(AdaptedImages.class, AdaptedImagesImpl.class);
									addMapping(AggregateRating.class, AggregateRatingImpl.class);
									addMapping(Categories.class, CategoriesImpl.class);
									addMapping(Comment.class, CommentImpl.class);
									addMapping(Creator.class, CreatorImpl.class);
									addMapping(Document.class, DocumentImpl.class);
									addMapping(Folder.class, FolderImpl.class);
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