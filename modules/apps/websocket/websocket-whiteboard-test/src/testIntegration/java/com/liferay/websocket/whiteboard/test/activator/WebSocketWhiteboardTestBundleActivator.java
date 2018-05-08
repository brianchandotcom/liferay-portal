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

package com.liferay.websocket.whiteboard.test.activator;

import com.liferay.websocket.whiteboard.test.encode.data.ExampleDecoder;
import com.liferay.websocket.whiteboard.test.encode.data.ExampleEncoder;
import com.liferay.websocket.whiteboard.test.encode.endpoint.EncodeWebSocketEndpoint;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Cristina González
 */
public class WebSocketWhiteboardTestBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		List<Class<? extends Decoder>> decoders = new ArrayList<>();

		decoders.add(ExampleDecoder.class);

		List<Class<? extends Encoder>> encoders = new ArrayList<>();

		properties.put("org.osgi.http.websocket.endpoint.decoders", decoders);

		encoders.add(ExampleEncoder.class);

		properties.put("org.osgi.http.websocket.endpoint.encoders", encoders);

		properties.put(
			"org.osgi.http.websocket.endpoint.path", "/o/websocket/decoder");

		_endpointServiceRegistration = bundleContext.registerService(
			Endpoint.class, new EncodeWebSocketEndpoint(), properties);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_endpointServiceRegistration.unregister();
	}

	private ServiceRegistration<Endpoint> _endpointServiceRegistration;

}