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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.security.pacl.permission.PortalSocketPermission;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.DoesNothing;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({PortalSocketPermission.class})
@RunWith(PowerMockRunner.class)
public class PingbackExcerptExtractorImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpHttp();
	}

	@Test
	public void testGetExcerpt() throws Exception {
		_pingbackExcerptExtractor = new PingbackExcerptExtractorImpl(4);

		whenURLToStringSourceURIThenReturn(
			"<body><a href='__targetURI__'>12345</a></body>");

		execute();

		verifyExcerpt("1...");
	}

	@Test
	public void testGetExcerptWhenAnchorHasParent() throws Exception {
		whenURLToStringSourceURIThenReturn(
			"<body><p>" +
			"Visit <a href='__targetURI__'>Liferay</a> to learn more" +
			"</p></body>");

		execute();

		verifyExcerpt("Visit Liferay to learn more");
	}

	@Test
	public void testGetExcerptWhenAnchorHasTwoParents() throws Exception {
		_pingbackExcerptExtractor = new PingbackExcerptExtractorImpl(18);

		whenURLToStringSourceURIThenReturn(
			"<body>_____<p>12345<span>67890" +
			"<a href='__targetURI__'>Liferay</a>" +
			"12345</span>67890</p>_____</body>");

		execute();

		verifyExcerpt("1234567890Lifer...");
	}

	@Test(expected = NullPointerException.class)
	public void testGetExcerptWhenAnchorIsMalformed() throws Exception {
		whenURLToStringSourceURIThenReturn("<a href='MALFORMED' />");

		execute("MALFORMED");
	}

	@Test(expected = InvalidSourceURIException.class)
	public void testGetExcerptWhenAnchorIsMissing() throws Exception {
		whenURLToStringSourceURIThenReturn("");

		execute();
	}

	@Test(expected = UnavailableSourceURIException.class)
	public void testGetExcerptWhenReferrerIsUnavailable() throws Exception {
		Mockito.doThrow(
			IOException.class
		).when(
			_http
		).URLtoString(
			"__sourceURI__"
		);

		execute();
	}

	protected void execute() throws Exception {
		execute("__targetURI__");
	}

	protected void execute(String targetURI) throws Exception {
		_pingbackExcerptExtractor.setSourceURI("__sourceURI__");
		_pingbackExcerptExtractor.setTargetURI(targetURI);
		_pingbackExcerptExtractor.validateSource();

		_excerpt = _pingbackExcerptExtractor.getExcerpt();
	}

	protected void setUpHttp() throws Exception {
		mockStatic(PortalSocketPermission.class, new DoesNothing());

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(_http);
	}

	protected void verifyExcerpt(String excerpt) throws Exception {
		Assert.assertEquals(excerpt, _excerpt);
	}

	protected void whenURLToStringSourceURIThenReturn(String toBeReturned)
		throws Exception {

		when(
			_http.URLtoString("__sourceURI__")
		).thenReturn(
			toBeReturned
		);
	}

	private String _excerpt;

	@Mock
	private Http _http;

	private PingbackExcerptExtractor _pingbackExcerptExtractor =
		new PingbackExcerptExtractorImpl(200);

}