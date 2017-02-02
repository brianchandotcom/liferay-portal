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

package com.liferay.portal.kernel.resiliency.spi.agent;

import com.liferay.portal.kernel.process.local.LocalProcessLauncher;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalImpl;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
public class AcceptorServletTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String getPathContext() {
					return _pathContext;
				}

			});

		ConcurrentMap<String, Object> attributes =
			LocalProcessLauncher.ProcessContext.getAttributes();

		SPI spi = new MockSPI() {

			@Override
			public SPIAgent getSPIAgent() {
				return _recordSPIAgent;
			}

		};

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, spi);

		Assert.assertSame(spi, SPIUtil.getSPI());
	}

	@Test
	public void testService() throws IOException, ServletException {

		// Successfully forward

		final AtomicBoolean failOnForward = new AtomicBoolean();
		final AtomicReference<String> forwardPathReference =
			new AtomicReference<>();
		final IOException ioException = new IOException("Unable to forward");

		MockServletContext mockServletContext = new MockServletContext() {

			@Override
			public RequestDispatcher getRequestDispatcher(final String path) {
				return new RequestDispatcher() {

					@Override
					public void forward(
							ServletRequest servletRequest,
							ServletResponse servletResponse)
						throws IOException {

						forwardPathReference.set(path);

						if (failOnForward.get()) {
							throw ioException;
						}
					}

					@Override
					public void include(
						ServletRequest servletRequest,
						ServletResponse servletResponse) {
					}

				};
			}

		};

		mockServletContext.setContextPath("/");

		AcceptorServlet acceptorServlet = new AcceptorServlet();

		acceptorServlet.init(new MockServletConfig(mockServletContext));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerPort(1234);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		acceptorServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("/c/portal/resiliency", forwardPathReference.get());
		Assert.assertSame(
			mockHttpServletRequest, _recordSPIAgent._originalRequest1);
		Assert.assertSame(
			mockHttpServletRequest, _recordSPIAgent._originalRequest2);
		Assert.assertSame(
			mockHttpServletResponse, _recordSPIAgent._originalResponse);
		Assert.assertNull(_recordSPIAgent._exception);
		Assert.assertTrue(_mockHttpSession.isInvalid());

		_pathContext = "/liferay-portal";

		mockServletContext.registerContext(_pathContext, mockServletContext);

		acceptorServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("/c/portal/resiliency", forwardPathReference.get());
		Assert.assertSame(
			mockHttpServletRequest, _recordSPIAgent._originalRequest1);
		Assert.assertSame(
			mockHttpServletRequest, _recordSPIAgent._originalRequest2);
		Assert.assertSame(
			mockHttpServletResponse, _recordSPIAgent._originalResponse);
		Assert.assertNull(_recordSPIAgent._exception);
		Assert.assertTrue(_mockHttpSession.isInvalid());

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					AcceptorServlet.class.getName(), Level.SEVERE)) {

			// IOException on prepare request

			_recordSPIAgent.setIOExceptionOnPrepareRequest(true);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			try {
				acceptorServlet.service(
					mockHttpServletRequest, mockHttpServletResponse);

				Assert.fail();
			}
			catch (IOException ioe) {
				Assert.assertEquals(
					"IOException on prepare request", ioe.getMessage());
			}

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(IOException.class, throwable.getClass());
			Assert.assertEquals(
				"IOException on prepare request", throwable.getMessage());

			// RuntimeException on prepare request

			_recordSPIAgent.setIOExceptionOnPrepareRequest(false);
			_recordSPIAgent.setRuntimeExceptionOnPrepareRequest(true);

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			try {
				acceptorServlet.service(
					mockHttpServletRequest, mockHttpServletResponse);

				Assert.fail();
			}
			catch (RuntimeException re) {
				Assert.assertEquals(
					"RuntimeException on prepare request", re.getMessage());
			}

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			logRecord = logRecords.get(0);

			throwable = logRecord.getThrown();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
			Assert.assertEquals(
				"RuntimeException on prepare request", throwable.getMessage());
		}

		// Unable to forward

		_recordSPIAgent.setRuntimeExceptionOnPrepareRequest(false);

		failOnForward.set(true);

		acceptorServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals("/c/portal/resiliency", forwardPathReference.get());
		Assert.assertSame(
			mockHttpServletRequest, _recordSPIAgent._originalRequest1);
		Assert.assertSame(
			mockHttpServletRequest, _recordSPIAgent._originalRequest2);
		Assert.assertSame(
			mockHttpServletResponse, _recordSPIAgent._originalResponse);
		Assert.assertSame(ioException, _recordSPIAgent._exception);
		Assert.assertTrue(_mockHttpSession.isInvalid());
	}

	private final MockHttpSession _mockHttpSession = new MockHttpSession();
	private String _pathContext = StringPool.BLANK;
	private final RecordSPIAgent _recordSPIAgent = new RecordSPIAgent();

	private class RecordSPIAgent extends MockSPIAgent {

		public RecordSPIAgent() {
			super(null, null);
		}

		@Override
		public HttpServletRequest prepareRequest(HttpServletRequest request)
			throws IOException {

			if (_ioExceptionOnPrepareRequest) {
				throw new IOException("IOException on prepare request");
			}

			if (_runtimeExceptionOnPrepareRequest) {
				throw new RuntimeException(
					"RuntimeException on prepare request");
			}

			_originalRequest1 = request;

			_preparedRequest = new MockHttpServletRequest();

			_preparedRequest.setSession(_mockHttpSession);

			return _preparedRequest;
		}

		@Override
		public HttpServletResponse prepareResponse(
			HttpServletRequest request, HttpServletResponse response) {

			_originalRequest2 = request;
			_originalResponse = response;

			_preparedResponse = new MockHttpServletResponse();

			return _preparedResponse;
		}

		public void setIOExceptionOnPrepareRequest(
			boolean ioExceptionOnPrepareRequest) {

			_ioExceptionOnPrepareRequest = ioExceptionOnPrepareRequest;
		}

		public void setRuntimeExceptionOnPrepareRequest(
			boolean runtimeExceptionOnPrepareRequest) {

			_runtimeExceptionOnPrepareRequest =
				runtimeExceptionOnPrepareRequest;
		}

		@Override
		public void transferResponse(
			HttpServletRequest request, HttpServletResponse response,
			Exception e) {

			Assert.assertSame(_preparedRequest, request);
			Assert.assertSame(_preparedResponse, response);

			_exception = e;
		}

		private Exception _exception;
		private boolean _ioExceptionOnPrepareRequest;
		private HttpServletRequest _originalRequest1;
		private HttpServletRequest _originalRequest2;
		private HttpServletResponse _originalResponse;
		private MockHttpServletRequest _preparedRequest;
		private MockHttpServletResponse _preparedResponse;
		private boolean _runtimeExceptionOnPrepareRequest;

	}

}