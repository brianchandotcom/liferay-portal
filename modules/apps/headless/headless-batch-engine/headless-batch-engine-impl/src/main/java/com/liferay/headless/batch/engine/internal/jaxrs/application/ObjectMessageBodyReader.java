/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.internal.jaxrs.application;

import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.nio.charset.StandardCharsets;

import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Batch.Engine)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Batch.Engine.ObjectMessageBodyReader"
	},
	service = MessageBodyReader.class
)
@Consumes({"application/x-ndjson", "text/csv"})
@Provider
public class ObjectMessageBodyReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class clazz, Type type, Annotation[] annotations, MediaType mediaType) {

		if (mediaType.equals(MediaType.valueOf("application/x-ndjson")) ||
			mediaType.equals(MediaType.valueOf("text/csv"))) {

			return true;
		}

		return false;
	}

	@Override
	public Object readFrom(
			Class clazz, Type type, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap multivaluedMap,
			InputStream inputStream)
		throws WebApplicationException {

		String text = null;

		try (Scanner scanner = new Scanner(
				inputStream, StandardCharsets.UTF_8.name())) {

			scanner.useDelimiter("\\A");

			text = scanner.next();
		}

		return text;
	}

}