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

package com.liferay.vulcan.application.internal;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.endpoint.RootEndpoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The VulcanApplication is the center piece of a Vulcan API. Developers only
 * need to provide a valid {@link RootEndpoint}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra
 * @author Jorge Ferrer
 * @review
 */
@ApplicationPath("/")
@Component(
	immediate = true, property = "liferay.vulcan.application=true",
	service = Application.class
)
public class VulcanApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_rootEndpoint);

		singletons.addAll(_messageBodyReaders);

		singletons.addAll(_messageBodyWriters);

		singletons.addAll(_containerResponseFilters);

		singletons.addAll(_exceptionMappers);

		return singletons;
	}

	@Reference(
		cardinality = MULTIPLE, policyOption = GREEDY,
		target = "(liferay.vulcan.container.response.filter=true)"
	)
	public void setContainerResponseFilter(
		ServiceReference<ContainerResponseFilter> serviceReference,
		ContainerResponseFilter containerResponseFilter) {

		_containerResponseFilters.add(containerResponseFilter);
	}

	@Reference(
		cardinality = MULTIPLE, policyOption = GREEDY,
		target = "(liferay.vulcan.exception.mapper=true)"
	)
	public void setExceptionMapper(
		ServiceReference<ExceptionMapper> serviceReference,
		ExceptionMapper exceptionMapper) {

		_exceptionMappers.add(exceptionMapper);
	}

	@Reference(
		cardinality = MULTIPLE, policyOption = GREEDY,
		target = "(liferay.vulcan.message.body.reader=true)"
	)
	public <T> void setMessageBodyReader(
		ServiceReference<MessageBodyReader<T>> serviceReference,
		MessageBodyReader<T> messageBodyReader) {

		_messageBodyReaders.add(messageBodyReader);
	}

	@Reference(
		cardinality = MULTIPLE, policyOption = GREEDY,
		target = "(liferay.vulcan.message.body.writer=true)"
	)
	public <T> void setMessageBodyWriter(
		ServiceReference<MessageBodyWriter<T>> serviceReference,
		MessageBodyWriter<T> messageBodyWriter) {

		_messageBodyWriters.add(messageBodyWriter);
	}

	@SuppressWarnings("unused")
	public <T> void unsetContainerResponseFilter(
		ServiceReference<ContainerResponseFilter> serviceReference,
		ContainerResponseFilter containerResponseFilter) {

		_containerResponseFilters.remove(containerResponseFilter);
	}

	@SuppressWarnings("unused")
	public void unsetExceptionMapper(
		ServiceReference<ExceptionMapper> serviceReference,
		ExceptionMapper exceptionMapper) {

		_exceptionMappers.remove(exceptionMapper);
	}

	@SuppressWarnings("unused")
	public <T> void unsetMessageBodyReader(
		ServiceReference<MessageBodyReader<T>> serviceReference,
		MessageBodyReader<T> messageBodyReader) {

		_messageBodyReaders.remove(messageBodyReader);
	}

	@SuppressWarnings("unused")
	public <T> void unsetMessageBodyWriter(
		ServiceReference<MessageBodyWriter<T>> serviceReference,
		MessageBodyWriter<T> messageBodyWriter) {

		_messageBodyWriters.remove(messageBodyWriter);
	}

	private final List<ContainerResponseFilter> _containerResponseFilters =
		new ArrayList<>();
	private final List<ExceptionMapper> _exceptionMappers = new ArrayList<>();
	private final List<MessageBodyReader> _messageBodyReaders =
		new ArrayList<>();
	private final List<MessageBodyWriter> _messageBodyWriters =
		new ArrayList<>();

	@Reference
	private RootEndpoint _rootEndpoint;

}