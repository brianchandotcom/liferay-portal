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

package com.liferay.apio.architect.impl.jaxrs.json.writer;

import com.liferay.apio.architect.impl.documentation.Documentation;
import com.liferay.apio.architect.impl.jaxrs.json.writer.base.BaseMessageBodyWriter;
import com.liferay.apio.architect.impl.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.impl.request.RequestInfo;
import com.liferay.apio.architect.impl.wiring.osgi.manager.message.json.DocumentationMessageMapperManager;
import com.liferay.apio.architect.impl.writer.DocumentationWriter;

import java.lang.reflect.Type;

import java.util.Optional;

import javax.ws.rs.core.Request;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Writes the API documentation by using the {@link DocumentationMessageMapper}
 * that corresponds to the media type.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(liferay.apio.architect.application=true)",
		"osgi.jaxrs.extension=true"
	},
	service = MessageBodyWriter.class
)
@Provider
public class DocumentationMessageBodyWriter
	extends BaseMessageBodyWriter<Documentation, DocumentationMessageMapper> {

	@Override
	public boolean canWrite(Class<?> clazz, Type genericType) {
		if (clazz == Documentation.class) {
			return true;
		}

		return false;
	}

	@Override
	public Optional<DocumentationMessageMapper> getMessageMapperOptional(
		Request request) {

		return _documentationMessageMapperManager.
			getDocumentationMessageMapperOptional(request);
	}

	@Override
	protected String write(
		Documentation documentation,
		DocumentationMessageMapper documentationMessageMapper,
		RequestInfo requestInfo) {

		DocumentationWriter documentationWriter = DocumentationWriter.create(
			builder -> builder.documentation(
				documentation
			).documentationMessageMapper(
				documentationMessageMapper
			).requestInfo(
				requestInfo
			).build());

		return documentationWriter.write();
	}

	@Reference
	private DocumentationMessageMapperManager
		_documentationMessageMapperManager;

}