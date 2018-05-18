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

package com.liferay.apio.architect.application.internal;

import static org.osgi.service.component.annotations.ReferenceCardinality.AT_LEAST_ONE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.endpoint.RootEndpoint;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Registers the application's root endpoint, writers, and mappers in JAX-RS.
 *
 * <p>
 * The default {@link RootEndpoint} already deploys
 * all components that implement the representor. Developers only need to
 * provide a valid {@code RootEndpoint} via this class if they want to customize
 * the default behavior.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra
 * @author Jorge Ferrer
 */
@ApplicationPath("/")
@Component(
	property = "liferay.apio.architect.application=true",
	service = Application.class
)
public class ApioApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return _singletons;
	}

	@Reference(
		cardinality = AT_LEAST_ONE, policy = DYNAMIC, policyOption = GREEDY,
		target = "(liferay.apio.architect.container.response.filter=true)"
	)
	public void setContainerResponseFilter(
		ContainerResponseFilter containerResponseFilter) {

		_singletons.add(containerResponseFilter);
	}

	@Reference(
		cardinality = AT_LEAST_ONE, policy = DYNAMIC, policyOption = GREEDY,
		target = "(liferay.apio.architect.exception.mapper=true)"
	)
	public void setExceptionMapper(ExceptionMapper exceptionMapper) {
		_singletons.add(exceptionMapper);
	}

	@Reference(
		cardinality = AT_LEAST_ONE, policy = DYNAMIC, policyOption = GREEDY,
		target = "(liferay.apio.architect.message.body.reader=true)"
	)
	public <T> void setMessageBodyReader(
		MessageBodyReader<T> messageBodyReader) {

		_singletons.add(messageBodyReader);
	}

	@Reference(
		cardinality = AT_LEAST_ONE, policy = DYNAMIC, policyOption = GREEDY,
		target = "(liferay.apio.architect.message.body.writer=true)"
	)
	public <T> void setMessageBodyWriter(
		MessageBodyWriter<T> messageBodyWriter) {

		_singletons.add(messageBodyWriter);
	}

	@Reference
	public void setRootEndpoint(RootEndpoint rootEndpoint) {
		_singletons.add(rootEndpoint);
	}

	@SuppressWarnings("unused")
	public <T> void unsetContainerResponseFilter(
		ContainerResponseFilter containerResponseFilter) {

		_singletons.remove(containerResponseFilter);
	}

	@SuppressWarnings("unused")
	public void unsetExceptionMapper(ExceptionMapper exceptionMapper) {
		_singletons.remove(exceptionMapper);
	}

	@SuppressWarnings("unused")
	public <T> void unsetMessageBodyReader(
		MessageBodyReader<T> messageBodyReader) {

		_singletons.remove(messageBodyReader);
	}

	@SuppressWarnings("unused")
	public <T> void unsetMessageBodyWriter(
		MessageBodyWriter<T> messageBodyWriter) {

		_singletons.remove(messageBodyWriter);
	}

	private final Set<Object> _singletons = new HashSet<>();

}