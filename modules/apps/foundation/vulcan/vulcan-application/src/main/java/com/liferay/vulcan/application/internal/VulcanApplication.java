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

import static org.osgi.service.component.annotations.ReferenceCardinality.AT_LEAST_ONE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.endpoint.RootEndpoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra
 * @author Jorge Ferrer
 */
@ApplicationPath("/")
@Component(immediate = true, service = Application.class)
public class VulcanApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_rootEndpoint);

		singletons.addAll(_contextProviders);
		singletons.addAll(_messageBodyWriters);

		return singletons;
	}

	@Reference(
		cardinality = AT_LEAST_ONE, policyOption = GREEDY,
		target = "(liferay.vulcan.message.body.writer=true)"
	)
	public <T> void setServiceReference(
		ServiceReference<MessageBodyWriter<T>> serviceReference,
		MessageBodyWriter<T> messageBodyWriter) {

		_messageBodyWriters.add(messageBodyWriter);
	}

	public <T> void unsetServiceReference(
		ServiceReference<MessageBodyWriter<T>> serviceReference,
		MessageBodyWriter<T> messageBodyWriter) {

		_messageBodyWriters.remove(messageBodyWriter);
	}

	@Reference(
		policyOption = GREEDY, target = "(liferay.vulcan.context.provider=true)"
	)
	private List<ContextProvider> _contextProviders;

	private final List<MessageBodyWriter> _messageBodyWriters =
		new ArrayList<>();

	@Reference
	private RootEndpoint _rootEndpoint;

}