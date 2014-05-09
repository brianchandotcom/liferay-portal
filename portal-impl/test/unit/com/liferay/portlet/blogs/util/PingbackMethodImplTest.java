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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.XmlRpc;
import com.liferay.portal.kernel.xmlrpc.XmlRpcConstants;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.blogs.pingback.DuplicateCommentException;
import com.liferay.portlet.blogs.pingback.InvalidSourceURIException;
import com.liferay.portlet.blogs.pingback.Pingback;
import com.liferay.portlet.blogs.pingback.PingbackDisabledException;
import com.liferay.portlet.blogs.pingback.UnavailableSourceURIException;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author André de Oliveira
 */
public class PingbackMethodImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpXmlRpc();
	}

	@Test
	public void testConvertDuplicateCommentExceptionToXmlRpcFault()
		throws Exception {

		whenAddPingbackThrow(new DuplicateCommentException());

		execute();

		verifyFault(
			PingbackMethodImpl.PINGBACK_ALREADY_REGISTERED,
			"Pingback previously registered");
	}

	@Test
	public void testConvertInvalidSourceURIExceptionToXmlRpcFault()
		throws Exception {

		whenAddPingbackThrow(
			new InvalidSourceURIException(
				"Could not find target URI in source"));

		execute();

		verifyFault(
			PingbackMethodImpl.SOURCE_URI_INVALID,
			"Could not find target URI in source");
	}

	@Test
	public void testConvertNullPointerExceptionToXmlRpcFault()
		throws Exception {

		whenAddPingbackThrow(new NullPointerException());

		execute();

		verifyFault(
			PingbackMethodImpl.TARGET_URI_INVALID, "Error parsing target URI");
	}

	@Test
	public void testConvertPingbackDisabledExceptionToXmlRpcFault()
		throws Exception {

		whenAddPingbackThrow(
			new PingbackDisabledException("Pingbacks are disabled"));

		execute();

		verifyFault(
			XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
			"Pingbacks are disabled");
	}

	@Test
	public void testConvertUnavailableSourceURIExceptionToXmlRpcFault()
		throws Exception {

		whenAddPingbackThrow(
			new UnavailableSourceURIException(
				"Error accessing source URI", new NullPointerException()));

		execute();

		verifyFault(
			PingbackMethodImpl.SOURCE_URI_DOES_NOT_EXIST,
			"Error accessing source URI");
	}

	@Test
	public void testExecuteWithSuccess() throws Exception {
		execute();

		Mockito.verify(
			_xmlRpc
		).createSuccess(
			"Pingback accepted"
		);
	}

	@Test
	public void testSetArgumentsConvertsArrayToSetters() throws Exception {
		PingbackMethodImpl method = new PingbackMethodImpl(_pingback);

		method.setArguments(new Object[]{"__sourceURI__", "__targetURI__"});

		Mockito.verify(
			_pingback
		).setSourceUri(
			"__sourceURI__"
		);

		Mockito.verify(
			_pingback
		).setTargetUri(
			"__targetURI__"
		);
	}

	protected void execute() throws Exception {
		PingbackMethodImpl pingbackMethod = new PingbackMethodImpl(_pingback);

		pingbackMethod.execute(ServiceTestUtil.randomLong());
	}

	protected void setUpXmlRpc() {
		Fault fault = Mockito.mock(Fault.class);

		when(
			_xmlRpc.createFault(Matchers.anyInt(), Matchers.anyString())
		).thenReturn(
			fault
		);

		XmlRpcUtil xmlRpcUtil = new XmlRpcUtil();

		xmlRpcUtil.setXmlRpc(_xmlRpc);
	}

	protected void verifyFault(int code, String description) {
		Mockito.verify(
			_xmlRpc
		).createFault(
			code, description
		);
	}

	protected void whenAddPingbackThrow(Throwable toBeThrown) throws Exception {
		Mockito.doThrow(
			toBeThrown
		).when(
			_pingback
		).addPingback(
			Matchers.anyLong()
		);
	}

	@Mock
	private Pingback _pingback;

	@Mock
	private XmlRpc _xmlRpc;

}