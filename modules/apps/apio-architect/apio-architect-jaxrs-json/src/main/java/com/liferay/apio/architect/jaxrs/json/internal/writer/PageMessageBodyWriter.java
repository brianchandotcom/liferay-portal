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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.functional.Try.Success;
import com.liferay.apio.architect.jaxrs.json.internal.writer.base.BaseMessageBodyWriter;
import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.message.json.PageMessageMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;
import com.liferay.apio.architect.writer.PageWriter;

import java.lang.reflect.Type;

import java.util.Optional;

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
	property = {
		"osgi.jaxrs.application.select=(liferay.apio.architect.application=true)",
		"osgi.jaxrs.extension=true"
	},
	service = MessageBodyWriter.class
)
@Provider
public class PageMessageBodyWriter<T>
	extends BaseMessageBodyWriter<Success<Page<T>>, PageMessageMapper<T>> {

	@Override
	public boolean canWrite(Class<?> clazz, Type genericType) {
		Try<Class<Object>> classTry =
			GenericUtil.getFirstGenericTypeArgumentFromTypeTry(
				genericType, Try.class);

		return classTry.filter(
			Page.class::equals
		).isSuccess();
	}

	@Override
	public Optional<PageMessageMapper<T>> getMessageMapperOptional(
		Request request) {

		return _pageMessageMapperManager.getPageMessageMapperOptional(request);
	}

	@Override
	protected String write(
		Success<Page<T>> success, PageMessageMapper<T> pageMessageMapper,
		RequestInfo requestInfo) {

		PageWriter<T> pageWriter = PageWriter.create(
			builder -> builder.page(
				success.getValue()
			).pageMessageMapper(
				pageMessageMapper
			).pathFunction(
				_pathIdentifierMapperManager::mapToPath
			).resourceNameFunction(
				nameManager::getNameOptional
			).representorFunction(
				name -> unsafeCast(
					_representableManager.getRepresentorOptional(name))
			).requestInfo(
				requestInfo
			).singleModelFunction(
				this::getSingleModelOptional
			).build());

		return pageWriter.write();
	}

	@Reference
	private PageMessageMapperManager _pageMessageMapperManager;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private RepresentableManager _representableManager;

}