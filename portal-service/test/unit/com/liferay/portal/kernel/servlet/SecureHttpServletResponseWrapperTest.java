/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.germinus.easyconf.ConfigurationSerializer;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author László Csontos
 */
@PrepareForTest({ConfigurationSerializer.class, PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class SecureHttpServletResponseWrapperTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		mockStatic(ConfigurationSerializer.class);
		mockStatic(PropsUtil.class);

		when(
			ConfigurationSerializer.getSerializer()
		).thenReturn(
			null
		);

		when(
			PropsUtil.get(Matchers.anyString())
		).thenReturn(
			StringPool.BLANK
		);

		new HttpUtil().setHttp(new HttpImpl());
	}

	@Test
	public void testForEncoded() throws IOException {
		doTest(
			new String[] {
				_VALUE_SANITIZED, _VALUE_SANITIZED, _VALUE_ENCODED,
				_VALUE_SANITIZED, _VALUE_SANITIZED},
			new String[] {
				_VALUE_SANITIZED, _VALUE_SANITIZED, _VALUE_ENCODED,
				_VALUE_SANITIZED, _VALUE_SANITIZED});
	}

	@Test
	public void testForPath() throws IOException {
		doTest(
			new String[] {null, null, _VALUE_PATH, null, null},
			new String[] {null, null, _VALUE_PATH, null, null});
	}

	@Test
	public void testForUnencoded() throws IOException {
		doTest(
			new String[] {
				_VALUE_INPUT, _VALUE_INPUT, _VALUE_INPUT, _VALUE_INPUT,
				_VALUE_INPUT
			},
			new String[] {
				_VALUE_SANITIZED, _VALUE_SANITIZED, _VALUE_SANITIZED,
				_VALUE_SANITIZED, _VALUE_SANITIZED
			});
	}

	@Test
	public void testForURL() throws IOException {
		doTest(
			new String[] {null, null, _VALUE_URL, null, null},
			new String[] {null, null, _VALUE_URL, null, null});
	}

	protected void doTest(String[] inputs, String[] expecteds)
		throws IOException {

		_responseWrapper.addHeader("Header-1", inputs[0]);
		_responseWrapper.setHeader("Header-2", inputs[1]);
		_responseWrapper.sendRedirect(inputs[2]);
		_responseWrapper.setCharacterEncoding(inputs[3]);
		_responseWrapper.setContentType(inputs[4]);

		String[] actuals = new String[] {
			_valueReference1.get(), _valueReference2.get(),
			_locationReference.get(), _characterEncodingReference.get(),
			_contentTypeReference.get(),
		};

		Assert.assertArrayEquals(expecteds, actuals);
	}

	private static final String _VALUE_ENCODED = "testURL%0A%0Daaa%0Abbb%0D";

	private static final String _VALUE_INPUT = "testURL\n\raaa\nbbb\r";

	private static final String _VALUE_PATH = "/abc/def/ghi";

	private static final String _VALUE_SANITIZED = "testURL  aaa bbb ";

	private static final String _VALUE_URL =
		"http://localhost:9080/web/guest/home";

	private final AtomicReference<String> _characterEncodingReference =
		new AtomicReference<String>();
	private final AtomicReference<String> _contentTypeReference =
		new AtomicReference<String>();
	private final AtomicReference<String> _locationReference =
		new AtomicReference<String>();

	private StubHttpServletResponse _response =
		new StubHttpServletResponse() {

		@Override
		public void addHeader(String name, String value) {
			_valueReference1.set(value);
		}

		@Override
		public void sendRedirect(String location) {
			_locationReference.set(location);
		}

		@Override
		public void setHeader(String name, String value) {
			_valueReference2.set(value);
		}

		@Override
		public void setContentType(String contentType) {
			_contentTypeReference.set(contentType);
		}

		@Override
		public void setCharacterEncoding(String characterEncoding) {
			_characterEncodingReference.set(characterEncoding);
		}
	};

	private SecureHttpServletResponseWrapper _responseWrapper =
		new SecureHttpServletResponseWrapper(_response);
	private final AtomicReference<String> _valueReference1 =
		new AtomicReference<String>();
	private final AtomicReference<String> _valueReference2 =
		new AtomicReference<String>();

}