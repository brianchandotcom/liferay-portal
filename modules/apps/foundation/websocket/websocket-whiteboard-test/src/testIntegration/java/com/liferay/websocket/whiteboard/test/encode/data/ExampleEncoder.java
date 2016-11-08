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

package com.liferay.websocket.whiteboard.test.encode.data;

import java.io.StringWriter;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * @author Cristina González
 */
public class ExampleEncoder implements Encoder.Text<Example> {

	@Override
	public void destroy() {
	}

	@Override
	public String encode(Example example) throws EncodeException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Example.class);

			Marshaller marshaller = jaxbContext.createMarshaller();

			StringWriter stringWriter = new StringWriter();

			marshaller.marshal(example, stringWriter);

			return stringWriter.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

}