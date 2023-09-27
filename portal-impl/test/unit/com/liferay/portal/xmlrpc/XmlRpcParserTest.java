/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xmlrpc;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.Success;
import com.liferay.portal.kernel.xmlrpc.XmlRpcParser;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class XmlRpcParserTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());
	}

	@Test
	public void testFaultResponseGenerator() throws Exception {
		Fault fault = new Fault(1234, "Fault");

		Response response = XmlRpcParser.parseResponse(fault.toXml());

		Assert.assertTrue(response instanceof Fault);

		Fault faultResponse = (Fault)response;

		Assert.assertEquals("Fault", faultResponse.getDescription());
		Assert.assertEquals(1234, faultResponse.getCode());
	}

	@Test
	public void testFaultResponseParser() throws Exception {
		for (String xml : _FAULT_RESPONSES) {
			Response response = XmlRpcParser.parseResponse(xml);

			Assert.assertTrue(response instanceof Fault);

			Fault fault = (Fault)response;

			Assert.assertEquals(4, fault.getCode());
			Assert.assertEquals("Too many parameters.", fault.getDescription());
		}
	}

	@Test
	public void testMethodBuilder() throws Exception {
		String xml = XmlRpcParser.buildMethod(
			"method.name", new Object[] {"hello", "world"});

		Tuple tuple = XmlRpcParser.parseMethod(xml);

		Assert.assertEquals("method.name", tuple.getObject(0));

		Object[] arguments = (Object[])tuple.getObject(1);

		Assert.assertEquals(Arrays.toString(arguments), 2, arguments.length);
		Assert.assertEquals("hello", arguments[0]);
		Assert.assertEquals("world", arguments[1]);
	}

	@Test
	public void testMethodParser() throws Exception {
		Tuple parameterizedMethodTuple = XmlRpcParser.parseMethod(
			_PARAMETERIZED_METHOD);

		Assert.assertEquals("params", parameterizedMethodTuple.getObject(0));

		Object[] parameterizedMethodArguments =
			(Object[])parameterizedMethodTuple.getObject(1);

		Assert.assertEquals(
			Arrays.toString(parameterizedMethodArguments), 3,
			parameterizedMethodArguments.length);
		Assert.assertEquals(1024, parameterizedMethodArguments[0]);
		Assert.assertEquals("hello", parameterizedMethodArguments[1]);
		Assert.assertEquals("world", parameterizedMethodArguments[2]);

		for (String xml : _NONPARAMETERIZED_METHODS) {
			Tuple nonparameterizedMethodTuple = XmlRpcParser.parseMethod(xml);

			Assert.assertEquals(
				"noParams", nonparameterizedMethodTuple.getObject(0));

			Object[] nonparameterizedMethodArguments =
				(Object[])nonparameterizedMethodTuple.getObject(1);

			Assert.assertEquals(
				Arrays.toString(nonparameterizedMethodArguments), 0,
				nonparameterizedMethodArguments.length);
		}
	}

	@Test
	public void testSuccessResponseGenerator() throws Exception {
		Success success = new Success("Success");

		Response response = XmlRpcParser.parseResponse(success.toXml());

		Assert.assertTrue(response instanceof Success);

		Success successResponse = (Success)response;

		Assert.assertEquals("Success", successResponse.getDescription());
	}

	@Test
	public void testSuccessResponseParser() throws Exception {
		for (String xml : _SUCCESS_RESPONSES) {
			Response response = XmlRpcParser.parseResponse(xml);

			Assert.assertTrue(response instanceof Success);
			Assert.assertEquals("South Dakota", response.getDescription());
		}
	}

	// Skip JavaParser

	private static final String[] _FAULT_RESPONSES = {
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodResponse>",
			"<fault>",
			"<value>",
			"<struct>",
			"<member>",
			"<name>faultCode</name>",
			"<value><int>4</int></value>",
			"</member>",
			"<member>",
			"<name>faultString</name>",
			"<value><string>Too many parameters.</string></value>",
			"</member>",
			"</struct>",
			"</value>",
			"</fault>",
			"</methodResponse>"),
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodResponse>",
			"<fault>",
			"<value>",
			"<struct>",
			"<member>",
			"<name>faultCode</name>",
			"<value><i4>4</i4></value>",
			"</member>",
			"<member>",
			"<name>faultString</name>",
			"<value>Too many parameters.</value>",
			"</member>",
			"</struct>",
			"</value>",
			"</fault>",
			"</methodResponse>")
	};

	// Skip JavaParser

	private static final String[] _NONPARAMETERIZED_METHODS = {
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodCall>",
			"<methodName>noParams</methodName>",
			"<params>",
			"</params>",
			"</methodCall>"),
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodCall>",
			"<methodName>noParams</methodName>",
			"</methodCall>")
	};

	// Skip JavaParser

	private static final String _PARAMETERIZED_METHOD =
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodCall>",
			"<methodName>params</methodName>",
			"<params>",
			"<param><value><i4>1024</i4></value></param>",
			"<param><value>hello</value></param>",
			"<param><value><string>world</string></value></param>",
			"</params>",
			"</methodCall>");

	// Skip JavaParser

	private static final String[] _SUCCESS_RESPONSES = {
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodResponse>",
			"<params>",
			"<param>",
			"<value><string>South Dakota</string></value>",
			"</param>",
			"</params>",
			"</methodResponse>"),
		StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<methodResponse>",
			"<params>",
			"<param>",
			"<value>South Dakota</value>",
			"</param>",
			"</params>",
			"</methodResponse>")
	};

}