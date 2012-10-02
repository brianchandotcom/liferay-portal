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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.webdav.methods.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * Based on <a href="http://www.webdav.org/neon/litmus/">litmus</a> 0.12.1
 * "basic" test.
 * </p>
 *
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {WebDAVEnviornmentConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WebDAVLitmusBasicTest extends BaseWebDAVTestCase {

	@Test
	public void test02Options() {
		Tuple tuple = service(Method.OPTIONS, StringPool.BLANK, null, null);

		assertCode(HttpServletResponse.SC_OK, tuple);

		Map<String, String> headers = getHeaders(tuple);

		String allowMethods = headers.get("Allow");

		for (String method : Method.SUPPORTED_METHODS_ARRAY) {
			Assert.assertTrue(
				"Does not allow " + method, allowMethods.contains(method));
		}
	}

	@Test
	public void test03PutGet() {
		putGet("res");
	}

	@Test
	public void test04PutGetUTF8() {
		putGet("res-\u20AC");
	}

	@Test
	public void test05PutNoParent() {
		assertCode(
			HttpServletResponse.SC_CONFLICT,
			service(Method.MKCOL, "409me/noparent", null, null));
		assertCode(
			HttpServletResponse.SC_CONFLICT,
			servicePut("409me/noparent.txt", _TEST_CONTENT.getBytes()));
	}

	@Test
	public void test06MkcolOverPlain() {
		assertCode(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED,
			service(Method.MKCOL, "res-\u20AC", null, null));
	}

	@Test
	public void test07Delete() {
		assertCode(
			HttpServletResponse.SC_NO_CONTENT, serviceDelete("res-\u20AC"));
	}

	@Test
	public void test08DeleteNull() {
		assertCode(HttpServletResponse.SC_NOT_FOUND, serviceDelete("404me"));
	}

	@Test
	public void test09DeleteFragment() {
		assertCode(
			HttpServletResponse.SC_CREATED,
			service(Method.MKCOL, "frag", null, null));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, serviceDelete("frag/#ment"));
		assertCode(HttpServletResponse.SC_NO_CONTENT, serviceDelete("frag"));
	}

	@Test
	public void test10Mkcol() {
		assertCode(
			HttpServletResponse.SC_CREATED,
			service(Method.MKCOL, "col", null, null));
	}

	@Test
	public void test11MkcolAgain() {
		assertCode(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED,
			service(Method.MKCOL, "col", null, null));
	}

	@Test
	public void test12DeleteColl() {
		assertCode(HttpServletResponse.SC_NO_CONTENT, serviceDelete("col"));
	}

	@Test
	public void test13MkcolNoParent() {
		assertCode(
			HttpServletResponse.SC_CONFLICT,
			service(Method.MKCOL, "409me/col", null, null));
	}

	@Test
	public void test14MkcolWithBody() {
		Map<String, String> headers = new HashMap<String, String>();

		headers.put(HttpHeaders.CONTENT_TYPE, "xyz-foo/bar-512");

		assertCode(
			HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
			service(
				Method.MKCOL, "mkcolbody", headers, _TEST_CONTENT.getBytes()));
	}

	protected void putGet(String fileName) {
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(fileName, _TEST_CONTENT.getBytes()));

		Tuple tuple = serviceGet(fileName);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(
			_TEST_CONTENT.getBytes(), getResponseBody(tuple));
	}

	private static final String _TEST_CONTENT =
		"LIFERAY\nEnterprise. Open Source. For Life.";

}