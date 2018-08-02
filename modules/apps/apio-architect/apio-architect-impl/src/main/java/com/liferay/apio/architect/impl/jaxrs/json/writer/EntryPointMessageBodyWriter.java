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

import com.liferay.apio.architect.impl.entrypoint.EntryPoint;
import com.liferay.apio.architect.impl.jaxrs.json.writer.base.BaseMessageBodyWriter;
import com.liferay.apio.architect.impl.message.json.EntryPointMessageMapper;
import com.liferay.apio.architect.impl.request.RequestInfo;
import com.liferay.apio.architect.impl.wiring.osgi.manager.message.json.EntryPointMessageMapperManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.apio.architect.impl.writer.EntryPointWriter;
import com.liferay.apio.architect.impl.writer.EntryPointWriter.Builder;
import com.liferay.apio.architect.representor.Representor;

import java.lang.reflect.Type;

import java.util.Optional;

import javax.ws.rs.core.Request;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Writes the API entry point by using the {@link EntryPointMessageMapper} that
 * corresponds to the media type.
 *
 * @author Alejandro Hernández
 * @author Zoltán Takács
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
public class EntryPointMessageBodyWriter
	extends BaseMessageBodyWriter<EntryPoint, EntryPointMessageMapper> {

	@Override
	public boolean canWrite(Class<?> clazz, Type genericType) {
		if (genericType == EntryPoint.class) {
			return true;
		}

		return false;
	}

	@Override
	public Optional<EntryPointMessageMapper> getMessageMapperOptional(
		Request request) {

		return _entryPointMessageMapperManager.
			getEntryPointMessageMapperOptional(request);
	}

	@Override
	protected String write(
		EntryPoint entryPoint, EntryPointMessageMapper entryPointMessageMapper,
		RequestInfo requestInfo) {

		EntryPointWriter entryPointWriter = Builder.entryPoint(
			entryPoint
		).entryPointMessageMapper(
			entryPointMessageMapper
		).requestInfo(
			requestInfo
		).typeFunction(
			name -> _representableManager.getRepresentorOptional(
				name
			).map(
				Representor::getPrimaryType
			)
		).build();

		return entryPointWriter.write();
	}

	@Reference
	private EntryPointMessageMapperManager _entryPointMessageMapperManager;

	@Reference
	private RepresentableManager _representableManager;

}