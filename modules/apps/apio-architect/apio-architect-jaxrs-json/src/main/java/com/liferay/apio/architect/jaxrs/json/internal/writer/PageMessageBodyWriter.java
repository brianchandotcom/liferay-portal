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

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.response.control.Embedded;
import com.liferay.apio.architect.response.control.Fields;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.url.ServerURL;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.message.json.PageMessageMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ItemRouterManager;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;
import com.liferay.apio.architect.writer.PageWriter;

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
 * Writes collection pages by using the {@link PageMessageMapper} that
 * corresponds to the media type.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.message.body.writer=true"
)
@Provider
public class PageMessageBodyWriter<T>
	implements MessageBodyWriter<Try.Success<Page<T>>> {

	@Override
	public long getSize(
		Try.Success<Page<T>> success, Class<?> clazz, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		Try<Class<Object>> classTry =
			GenericUtil.getFirstGenericTypeArgumentFromTypeTry(
				genericType, Try.class);

		return classTry.filter(
			Page.class::equals
		).isSuccess();
	}

	@Override
	public void writeTo(
			Try.Success<Page<T>> success, Class<?> clazz, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws IOException, WebApplicationException {

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			entityStream, StandardCharsets.UTF_8);

		PrintWriter printWriter = new PrintWriter(outputStreamWriter, true);

		Page<T> page = success.getValue();

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

		Optional<PageMessageMapper<T>> optional =
			_pageMessageMapperManager.getPageMessageMapperOptional(_request);

		PageMessageMapper<T> pageMessageMapper = optional.orElseThrow(
			NotSupportedException::new);

		PageWriter<T> pageWriter = PageWriter.create(
			builder -> builder.page(
				page
			).pageMessageMapper(
				pageMessageMapper
			).pathFunction(
				_pathIdentifierMapperManager::mapToPath
			).resourceNameFunction(
				_nameManager::getNameOptional
			).representorFunction(
				name -> unsafeCast(
					_representableManager.getRepresentorOptional(name))
			).requestInfo(
				requestInfo
			).singleModelFunction(
				this::_getSingleModelOptional
			).build());

		httpHeaders.put(
			CONTENT_TYPE,
			Collections.singletonList(pageMessageMapper.getMediaType()));

		printWriter.println(pageWriter.write());

		printWriter.close();
	}

	private Optional<SingleModel> _getSingleModelOptional(
		Object identifier, Class<? extends Identifier> identifierClass) {

		Optional<String> nameOptional = _nameManager.getNameOptional(
			identifierClass.getName());

		return nameOptional.flatMap(
			_itemRouterManager::getItemRoutesOptional
		).flatMap(
			ItemRoutes::getItemFunctionOptional
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(identifier)
		).flatMap(
			Try::toOptional
		).map(
			Unsafe::unsafeCast
		);
	}

	@Context
	private HttpHeaders _httpHeaders;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ItemRouterManager _itemRouterManager;

	@Reference
	private NameManager _nameManager;

	@Reference
	private PageMessageMapperManager _pageMessageMapperManager;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

	@Context
	private Request _request;

}