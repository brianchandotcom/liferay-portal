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

package com.liferay.apio.architect.jaxrs.json.internal.writer;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * Writes an input stream as a binary output stream.
 *
 * @author Javier Gamarra
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.message.body.writer=true"
)
@Provider
public class BinaryResourceBodyWriter
	implements MessageBodyWriter<Try.Success<InputStream>> {

	public long getSize(
		Try.Success<InputStream> singleModelSuccess, Class<?> aClass, Type type,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	public boolean isWriteable(
		Class<?> aClass, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		Try<Class<Object>> classTry =
			GenericUtil.getFirstGenericTypeArgumentTry(genericType);

		return classTry.filter(
			InputStream.class::equals
		).isSuccess();
	}

	@Override
	public void writeTo(
			Try.Success<InputStream> success, Class<?> aClass, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream)
		throws IOException, WebApplicationException {

		byte[] bytes = new byte[1024];
		InputStream inputStream = success.getValue();
		int value = -1;

		while ((value = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, value);
		}

		outputStream.close();
	}

}