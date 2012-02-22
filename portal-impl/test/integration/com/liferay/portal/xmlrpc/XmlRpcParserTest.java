/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.xmlrpc;

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.Success;
import com.liferay.portal.util.BaseTestCase;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class XmlRpcParserTest extends BaseTestCase {

	public void testFaultResponseGenerator() throws Exception {
		Fault fault = new FaultImpl(1234, "Fault");

		Response response = XmlRpcParser.parseResponse(fault.toXml());

		assertTrue(response instanceof Fault);

		fault = (Fault)response;

		assertEquals("Fault", fault.getDescription());
		assertEquals(1234, fault.getCode());
	}

	public void testFaultResponseParser() throws Exception {
		for (String xml : _FAULT_RESPONSES) {
			Response response = XmlRpcParser.parseResponse(xml);

			assertTrue(response instanceof Fault);

			Fault fault = (Fault)response;

			assertEquals(4, fault.getCode());
			assertEquals("Too many parameters.", fault.getDescription());
		}
	}

	public void testMethodBuilder() throws Exception {
		String xml = XmlRpcParser.buildMethod(
			"method.name", new Object[] {"hello", "world"});

		Tuple tuple = XmlRpcParser.parseMethod(xml);

		String methodName = (String)tuple.getObject(0);
		Object[] arguments = (Object[])tuple.getObject(1);

		assertEquals("method.name", methodName);
		assertEquals(2, arguments.length);
		assertEquals("hello", arguments[0]);
		assertEquals("world", arguments[1]);
	}

	public void testMethodParser() throws Exception {
		Tuple tuple = XmlRpcParser.parseMethod(_PARAMETERIZED_METHOD);

		String methodName = (String)tuple.getObject(0);
		Object[] arguments = (Object[])tuple.getObject(1);

		assertEquals("params", methodName);
		assertEquals(3, arguments.length);
		assertEquals(1024, arguments[0]);
		assertEquals("hello", arguments[1]);
		assertEquals("world", arguments[2]);

		for (String xml : _NON_PARAMETERIZED_METHODS) {
			tuple = XmlRpcParser.parseMethod(xml);

			methodName = (String)tuple.getObject(0);
			arguments = (Object[])tuple.getObject(1);

			assertEquals("noParams", methodName);
			assertEquals(0, arguments.length);
		}
	}

	public void testSuccessResponseGenerator() throws Exception {
		Success success = new SuccessImpl("Success");

		Response response = XmlRpcParser.parseResponse(success.toXml());

		assertTrue(response instanceof Success);

		success = (Success)response;

		assertEquals("Success", success.getDescription());
	}

	public void testSuccessResponseParser() throws Exception {
		for (String xml : _SUCCESS_RESPONSES) {
			Response response = XmlRpcParser.parseResponse(xml);

			assertTrue(response instanceof Success);
			assertEquals("South Dakota", response.getDescription());
		}
	}

	private static final String[] _FAULT_RESPONSES = new String[] {
		"<?xml version=\"1.0\"?>" +
		"<methodResponse>" +
		"<fault>" +
		"<value>" +
		"<struct>" +
		"<member>" +
		"<name>faultCode</name>" +
		"<value><int>4</int></value>" +
		"</member>" +
		"<member>" +
		"<name>faultString</name>" +
		"<value><string>Too many parameters.</string></value>" +
		"</member>" +
		"</struct>" +
		"</value>" +
		"</fault>" +
		"</methodResponse>"
		,
		"<?xml version=\"1.0\"?>" +
		"<methodResponse>" +
		"<fault>" +
		"<value>" +
		"<struct>" +
		"<member>" +
		"<name>faultCode</name>" +
		"<value><i4>4</i4></value>" +
		"</member>" +
		"<member>" +
		"<name>faultString</name>" +
		"<value>Too many parameters.</value>" +
		"</member>" +
		"</struct>" +
		"</value>" +
		"</fault>" +
		"</methodResponse>"
	};

	private static final String[] _NON_PARAMETERIZED_METHODS = new String[] {
		"<?xml version=\"1.0\"?>" +
		"<methodCall>" +
		"<methodName>noParams</methodName>" +
		"<params>" +
		"</params>" +
		"</methodCall>"
		,
		"<?xml version=\"1.0\"?>" +
		"<methodCall>" +
		"<methodName>noParams</methodName>" +
		"</methodCall>"
	};

	private static final String _PARAMETERIZED_METHOD =
		"<?xml version=\"1.0\"?>" +
		"<methodCall>" +
		"<methodName>params</methodName>" +
		"<params>" +
		"<param><value><i4>1024</i4></value></param>" +
		"<param><value>hello</value></param>" +
		"<param><value><string>world</string></value></param>" +
		"</params>" +
		"</methodCall>";

	private static final String[] _SUCCESS_RESPONSES = new String[] {
		"<?xml version=\"1.0\"?>" +
		"<methodResponse>" +
		"<params>" +
		"<param>" +
		"<value><string>South Dakota</string></value>" +
		"</param>" +
		"</params>" +
		"</methodResponse>"
		,
		"<?xml version=\"1.0\"?>" +
		"<methodResponse>" +
		"<params>" +
		"<param>" +
		"<value>South Dakota</value>" +
		"</param>" +
		"</params>" +
		"</methodResponse>"
	};

}