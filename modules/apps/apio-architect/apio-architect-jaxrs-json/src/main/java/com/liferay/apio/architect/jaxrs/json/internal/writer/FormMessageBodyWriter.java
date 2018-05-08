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

import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.functional.Try.Success;
import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.message.json.FormMessageMapper;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.response.control.Embedded;
import com.liferay.apio.architect.response.control.Fields;
import com.liferay.apio.architect.url.ServerURL;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.message.json.FormMessageMapperManager;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;
import com.liferay.apio.architect.writer.FormWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.message.body.writer=true"
)
@Provider
public class FormMessageBodyWriter implements MessageBodyWriter<Success<Form>> {

	public long getSize(
		Success<Form> success, Class<?> aClass, Type type,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	public boolean isWriteable(
		Class<?> aClass, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		Try<Class<Object>> classTry =
			GenericUtil.getFirstGenericTypeArgumentFromTypeTry(
				genericType, Try.class);

		return classTry.filter(
			Form.class::equals
		).isSuccess();
	}

	@Override
	public void writeTo(
			Success<Form> success, Class<?> aClass, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream outputStream)
		throws IOException, WebApplicationException {

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			outputStream, StandardCharsets.UTF_8);

		PrintWriter printWriter = new PrintWriter(outputStreamWriter, true);

		Form form = success.getValue();

		Optional<FormMessageMapper> optional =
			_formMessageMapperManager.getFormMessageMapperOptional(_request);

		FormMessageMapper formMessageMapper = optional.orElseThrow(
			NotSupportedException::new);

		RequestInfo requestInfo = RequestInfo.create(
			builder -> builder.httpHeaders(
				_httpHeaders
			).httpServletRequest(
				_httpServletRequest
			).serverURL(
				_providerManager.provideMandatory(
					_httpServletRequest, ServerURL.class)
			).embedded(
				_providerManager.provideOptional(
					_httpServletRequest, Embedded.class
				).orElse(
					__ -> false
				)
			).fields(
				_providerManager.provideOptional(
					_httpServletRequest, Fields.class
				).orElse(
					__ -> string -> true
				)
			).language(
				_providerManager.provideOptional(
					_httpServletRequest, Language.class
				).orElse(
					Locale::getDefault
				)
			).build());

		FormWriter formWriter = FormWriter.create(
			builder -> builder.form(
				form
			).formMessageMapper(
				formMessageMapper
			).requestInfo(
				requestInfo
			).build());

		httpHeaders.put(
			CONTENT_TYPE,
			Collections.singletonList(formMessageMapper.getMediaType()));

		printWriter.println(formWriter.write());

		printWriter.close();
	}

	@Reference
	private FormMessageMapperManager _formMessageMapperManager;

	@Context
	private HttpHeaders _httpHeaders;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ProviderManager _providerManager;

	@Context
	private Request _request;

}