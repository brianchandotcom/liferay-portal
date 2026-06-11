/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.data.masking.internal.jaxrs.writer.interceptor;

import com.liferay.headless.data.masking.service.v1_0.DataMaskingService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.OutputStream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jose Luis Navarro
 */
public class DataMaskingWriterInterceptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_dataMaskingWriterInterceptor, "_dataMaskingService",
			_dataMaskingService);
		ReflectionTestUtil.setFieldValue(
			_dataMaskingWriterInterceptor, "_httpServletRequest",
			_httpServletRequest);
		ReflectionTestUtil.setFieldValue(
			_dataMaskingWriterInterceptor, "_portal", _portal);
	}

	@Test
	public void testRedactionIsSkippedForUnsupportedMediaType()
		throws Exception {

		Mockito.when(
			_httpServletRequest.getHeader("X-Liferay-Data-Masks")
		).thenReturn(
			"L_DATA_MASK_EMAIL_ADDRESS"
		);

		Mockito.when(
			_writerInterceptorContext.getMediaType()
		).thenReturn(
			MediaType.APPLICATION_OCTET_STREAM_TYPE
		);

		_dataMaskingWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setOutputStream(
			Mockito.any(OutputStream.class)
		);

		Mockito.verify(
			_dataMaskingService, Mockito.never()
		).redact(
			Mockito.anyLong(), Mockito.anyList(), Mockito.anyString()
		);
	}

	@Test
	public void testRedactionIsSkippedWhenDataMasksHeaderIsMissing()
		throws Exception {

		Mockito.when(
			_httpServletRequest.getHeader("X-Liferay-Data-Masks")
		).thenReturn(
			null
		);

		_dataMaskingWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setOutputStream(
			Mockito.any(OutputStream.class)
		);

		Mockito.verify(
			_dataMaskingService, Mockito.never()
		).redact(
			Mockito.anyLong(), Mockito.anyList(), Mockito.anyString()
		);
	}

	private final DataMaskingService _dataMaskingService = Mockito.mock(
		DataMaskingService.class);
	private final DataMaskingWriterInterceptor _dataMaskingWriterInterceptor =
		new DataMaskingWriterInterceptor();
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final WriterInterceptorContext _writerInterceptorContext =
		Mockito.mock(WriterInterceptorContext.class);

}