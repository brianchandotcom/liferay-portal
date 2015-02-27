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

package com.liferay.portal.soap.service.sample;

import javax.jws.WebService;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true, property = {"address=/app1/calculator", "jaxws=true"},
	service = Object.class
)
@WebService(
	endpointInterface = "com.liferay.portal.soap.service.sample.Calculator"
)
public class CalculatorImpl implements Calculator {

	@Override
	public int calcula(int a, int b) {
		return a + b;
	}

	@Override
	public ComplicatedThing getComplicatedThing() {
		ComplicatedThing complicatedThing = new ComplicatedThing();

		complicatedThing.setName("very complicated");
		complicatedThing.setSomething("thing!");

		return complicatedThing;
	}

	@Override
	public int notExportedInWSDL() {
		return 0;
	}

	private static class MyHandler implements Handler {

		@Override
		public void close(MessageContext context) {
		}

		@Override
		public boolean handleFault(MessageContext context) {
			System.out.println("FAULT!!!!!");

			return true;
		}

		@Override
		public boolean handleMessage(MessageContext context) {
			System.out.println("HANDLE!!!!!");

			return true;
		}

	}

}