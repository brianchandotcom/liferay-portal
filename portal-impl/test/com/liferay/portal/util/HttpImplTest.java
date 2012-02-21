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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PortalUtil.class})
@PowerMockIgnore("javax.xml.datatype.*")
public class HttpImplTest {

	@Test
	public void addBooleanParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(Boolean.TRUE));
	}

	@Test
	public void addDoubleParameter() {
		double d = 111.1;
		_addParameter("http://foo.com", "p", String.valueOf(d));
	}

	@Test
	public void addIntParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(1));
	}

	@Test
	public void addLongParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(111111L));
	}

	@Test
	public void addShortParameter() {
		short s = 1;
		_addParameter("http://foo.com", "p", String.valueOf(s));
	}

	@Test
	public void addStringParameter() {
		_addParameter("http://foo.com", "p", new String("foo"));
	}

	@Test
	public void decodeMultipleCharacterEncodedPath() {
		Assert.assertEquals(
				"http://foo?p=$param",
				_httpImpl.decodePath("http://foo%3Fp%3D%24param"));
	}

	@Test
	public void decodeNoCharacterEncodedPath() {
		Assert.assertEquals("http://foo", _httpImpl.decodePath("http://foo"));
	}

	@Test
	public void decodeSingleCharacterEncodedPath() {
		Assert.assertEquals(
			"http://foo#anchor", _httpImpl.decodePath("http://foo%23anchor"));
	}

	@Test
	public void encodeMultipleCharacterEncodedPath() {
		Assert.assertEquals(
			"http%3A//foo%3Fp%3D%24param",
			_httpImpl.encodePath("http://foo?p=$param"));
	}

	@Test
	public void encodeNoCharacterEncodedPath() {
		Assert.assertEquals("http%3A//foo", _httpImpl.encodePath("http://foo"));
	}

	@Test
	public void encodeSingleCharacterEncodedPath() {
		Assert.assertEquals(
			"http%3A//foo%23anchor",
			_httpImpl.encodePath("http://foo#anchor"));
	}

	private void _addParameter(
		String url, String parameterName, String parameterValue) {

		mockStatic(PortalUtil.class);

		when(PortalUtil.stripURLAnchor(
			url, StringPool.POUND)).thenReturn(
				new String[] {url, StringPool.BLANK});

		String composedUrl = _httpImpl.addParameter(
				url, parameterName, parameterValue);

		verifyStatic();

		StringBundler stringBundler = new StringBundler(5);

		stringBundler.append(url);
		stringBundler.append(StringPool.QUESTION);
		stringBundler.append(parameterName);
		stringBundler.append(StringPool.EQUAL);
		stringBundler.append(parameterValue);

		Assert.assertEquals(stringBundler.toString(), composedUrl);
	}

	private HttpImpl _httpImpl = new HttpImpl();

}