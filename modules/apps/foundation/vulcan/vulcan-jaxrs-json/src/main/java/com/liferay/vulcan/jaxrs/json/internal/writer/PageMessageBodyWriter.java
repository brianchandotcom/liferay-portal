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

package com.liferay.vulcan.jaxrs.json.internal.writer;

import static org.osgi.service.component.annotations.ReferenceCardinality.AT_LEAST_ONE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveMessageMapper;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.language.Language;
import com.liferay.vulcan.message.json.PageMessageMapper;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.request.RequestInfo;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.url.ServerURL;
import com.liferay.vulcan.wiring.osgi.manager.CollectionResourceManager;
import com.liferay.vulcan.wiring.osgi.manager.PathIdentifierMapperManager;
import com.liferay.vulcan.wiring.osgi.manager.ProviderManager;
import com.liferay.vulcan.wiring.osgi.util.GenericUtil;
import com.liferay.vulcan.writer.PageWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
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
	immediate = true, property = "liferay.vulcan.message.body.writer=true"
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
			GenericUtil.getFirstGenericTypeArgumentTry(genericType);

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
			).serverURL(
				getServerURL()
			).embedded(
				_providerManager.provideOrNull(
					Embedded.class, _httpServletRequest)
			).fields(
				_providerManager.provideOrNull(
					Fields.class, _httpServletRequest)
			).language(
				_providerManager.provideOrNull(
					Language.class, _httpServletRequest)
			).build());

		PageWriter<T> pageWriter = PageWriter.create(
			builder -> builder.page(
				page
			).pageMessageMapper(
				getPageMessageMapper(mediaType, page)
			).pathFunction(
				_pathIdentifierMapperManager::map
			).resourceNameFunction(
				_collectionResourceManager::getNameOptional
			).representorFunction(
				_collectionResourceManager::getRepresentorOptional
			).requestInfo(
				requestInfo
			).build());

		printWriter.println(pageWriter.write());

		printWriter.close();
	}

	/**
	 * Returns the right {@link PageMessageMapper} for the provided {@link
	 * MediaType} that supports writing the provided {@link Page}.
	 *
	 * @param  mediaType the request media type
	 * @param  page the page to write
	 * @return the {@code PageMessageMapper} that writes the {@code Page} in the
	 *         media type
	 * @review
	 */
	protected PageMessageMapper<T> getPageMessageMapper(
		MediaType mediaType, Page<T> page) {

		Stream<PageMessageMapper<T>> stream = _pageMessageMappers.stream();

		String mediaTypeString = mediaType.toString();

		return stream.filter(
			bodyWriter ->
				mediaTypeString.equals(bodyWriter.getMediaType()) &&
				bodyWriter.supports(page, _httpHeaders)
		).findFirst(
		).orElseThrow(
			() -> new MustHaveMessageMapper(
				mediaTypeString, page.getModelClass())
		);
	}

	/**
	 * Returns the server URL, or throws a {@link MustHaveProvider} developer
	 * error.
	 *
	 * @return the server URL.
	 * @review
	 */
	protected ServerURL getServerURL() {
		Optional<ServerURL> optional = _providerManager.provideOptional(
			ServerURL.class, _httpServletRequest);

		return optional.orElseThrow(
			() -> new MustHaveProvider(ServerURL.class));
	}

	@Reference
	private CollectionResourceManager _collectionResourceManager;

	@Context
	private HttpHeaders _httpHeaders;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference(cardinality = AT_LEAST_ONE, policyOption = GREEDY)
	private List<PageMessageMapper<T>> _pageMessageMappers;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

}