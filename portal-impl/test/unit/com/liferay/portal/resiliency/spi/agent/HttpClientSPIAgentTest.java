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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxException;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxUtil;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.AcceptorServlet;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ReadOnlyServletResponse;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.UnknownHostException;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class HttpClientSPIAgentTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		PropsUtil.setProps(new PropsImpl());

		_portlet = new PortletImpl() {

			@Override
			public String getContextName() {
				return _SERVLET_CONTEXT_NAME;
			}

		};

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, _portlet);
	}

	@Test
	public void testBorrowSocket() throws Exception {

		// Create on empty

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(),
				_spiConfiguration.getConnectorPort(), null);

		serverSocketChannel.configureBlocking(true);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			SPIConfiguration spiConfiguration = new SPIConfiguration(
				null, null, serverSocket.getLocalPort(),
				_spiConfiguration.getBaseDir(), null, null, null);

			HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
				spiConfiguration,
				new MockRegistrationReference(new MockIntraband()));

			Socket socket = httpClientSPIAgent.borrowSocket();

			closePeers(socket, serverSocket);

			// Clean up when closed

			Queue<Socket> socketBlockingQueue =
				httpClientSPIAgent.socketBlockingQueue;

			socketBlockingQueue.add(socket);

			socket = httpClientSPIAgent.borrowSocket();

			closePeers(socket, serverSocket);

			// Clean up not connected

			socketBlockingQueue.add(new Socket());

			socket = httpClientSPIAgent.borrowSocket();

			closePeers(socket, serverSocket);

			// Clean up when input is shutdown

			socket = httpClientSPIAgent.borrowSocket();

			socket.shutdownInput();

			socketBlockingQueue.add(socket);

			socket = httpClientSPIAgent.borrowSocket();

			closePeers(socket, serverSocket);

			socket = serverSocket.accept();

			socket.close();

			CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					HttpClientSPIAgent.class.getName(), Level.OFF);

			try {

				// Clean up when input is shutdown, failed without log

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				socket.shutdownInput();

				SocketImpl socketImpl = swapSocketImpl(socket, null);

				socketBlockingQueue.add(socket);

				socket = httpClientSPIAgent.borrowSocket();

				swapSocketImpl(socket, socketImpl);

				closePeers(socket, serverSocket);

				socket = serverSocket.accept();

				socket.close();

				Assert.assertTrue(logRecords.isEmpty());

				// Clean up when input is shutdown, failed with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				socket.shutdownInput();

				socketImpl = swapSocketImpl(socket, null);

				socketBlockingQueue.add(socket);

				socket = httpClientSPIAgent.borrowSocket();

				swapSocketImpl(socket, socketImpl);

				closePeers(socket, serverSocket);

				socket = serverSocket.accept();

				socket.close();

				Assert.assertEquals(1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Throwable throwable = logRecord.getThrown();

				Assert.assertSame(IOException.class, throwable.getClass());
			}
			finally {
				captureHandler.close();
			}

			// Clean up when output is shutdown()

			socket = httpClientSPIAgent.borrowSocket();

			socket.shutdownOutput();

			socketBlockingQueue.add(socket);

			socket = httpClientSPIAgent.borrowSocket();

			closePeers(socket, serverSocket);

			socket = serverSocket.accept();

			socket.close();

			// Reuse socket

			socket = httpClientSPIAgent.borrowSocket();

			socketBlockingQueue.add(socket);

			Assert.assertSame(socket, httpClientSPIAgent.borrowSocket());

			closePeers(socket, serverSocket);
		}
	}

	@Test
	public void testConstructor() throws Exception {
		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(new MockIntraband());

		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration, mockRegistrationReference);

		Assert.assertSame(
			mockRegistrationReference,
			httpClientSPIAgent.registrationReference);
		Assert.assertEquals(
			new InetSocketAddress(
				InetAddressUtil.getLoopbackInetAddress(),
				_spiConfiguration.getConnectorPort()),
			httpClientSPIAgent.socketAddress);

		BlockingQueue<Socket> socketBlockingQueue =
			httpClientSPIAgent.socketBlockingQueue;

		Assert.assertTrue(socketBlockingQueue.isEmpty());
		Assert.assertEquals(
			PropsValues.PORTAL_RESILIENCY_SPI_AGENT_CLIENT_POOL_MAX_SIZE,
			socketBlockingQueue.remainingCapacity());

		StringBundler sb = new StringBundler();

		sb.append("POST ");
		sb.append(HttpClientSPIAgent.SPI_AGENT_CONTEXT_PATH);
		sb.append(HttpClientSPIAgent.MAPPING_PATTERN);
		sb.append(" HTTP/1.1\r\nHost: localhost:");
		sb.append(_spiConfiguration.getConnectorPort());
		sb.append("\r\nContent-Length: 8\r\n\r\n");

		String httpServletRequestContentString = sb.toString();

		Assert.assertArrayEquals(
			httpServletRequestContentString.getBytes("US-ASCII"),
			httpClientSPIAgent.httpServletRequestContent);
	}

	@Test
	public void testConsumeHttpResponseHead() throws Exception {

		// Wrong status line

		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration,
			new MockRegistrationReference(new MockIntraband()));

		String wrongStatusLine = "Wrong status line";

		try {
			httpClientSPIAgent.consumeHttpResponseHead(
				new DataInputStream(
					new UnsyncByteArrayInputStream(
						wrongStatusLine.getBytes("US-ASCII"))));

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				"Error status line: " + wrongStatusLine, ioe.getMessage());
		}

		// Wrong ending

		String responseContentWrongEnding = "HTTP/1.1 200 OK\n";

		Assert.assertFalse(
			httpClientSPIAgent.consumeHttpResponseHead(
				new DataInputStream(
					new UnsyncByteArrayInputStream(
						responseContentWrongEnding.getBytes("US-ASCII")))));

		// Keep alive

		String responseContentKeepAlive = "HTTP/1.1 200 OK\n\n";

		Assert.assertFalse(
			httpClientSPIAgent.consumeHttpResponseHead(
				new DataInputStream(
					new UnsyncByteArrayInputStream(
						responseContentKeepAlive.getBytes("US-ASCII")))));

		// Wrong close

		String responseContentWrongClose =
			"HTTP/1.1 200 OK\nconnection:wrong\n\n";

		Assert.assertFalse(
			httpClientSPIAgent.consumeHttpResponseHead(
				new DataInputStream(
					new UnsyncByteArrayInputStream(
						responseContentWrongClose.getBytes("US-ASCII")))));

		// Correct close

		String responseContentCorrectClose =
			"HTTP/1.1 200 OK\nkey:value\nconnection:close\n\n";

		Assert.assertTrue(
			httpClientSPIAgent.consumeHttpResponseHead(
				new DataInputStream(
					new UnsyncByteArrayInputStream(
						responseContentCorrectClose.getBytes("US-ASCII")))));
	}

	@Test
	public void testDestroy() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			HttpClientSPIAgent.class.getName(), Level.OFF);

		try {

			// Error without log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			ServerSocketChannel serverSocketChannel =
				SocketUtil.createServerSocketChannel(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort(), null);

			serverSocketChannel.configureBlocking(true);

			try (ServerSocket serverSocket = serverSocketChannel.socket()) {
				Socket socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				SocketImpl socketImpl = swapSocketImpl(socket, null);

				HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
					_spiConfiguration,
					new MockRegistrationReference(new MockIntraband()));

				Queue<Socket> socketBlockingQueue =
					httpClientSPIAgent.socketBlockingQueue;

				socketBlockingQueue.add(socket);

				httpClientSPIAgent.destroy();

				swapSocketImpl(socket, socketImpl);

				closePeers(socket, serverSocket);

				Assert.assertTrue(logRecords.isEmpty());

				// Error with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				httpClientSPIAgent = new HttpClientSPIAgent(
					_spiConfiguration,
					new MockRegistrationReference(new MockIntraband()));

				socketBlockingQueue = httpClientSPIAgent.socketBlockingQueue;

				socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				socketImpl = swapSocketImpl(socket, null);

				socketBlockingQueue.add(socket);

				httpClientSPIAgent.destroy();

				swapSocketImpl(socket, socketImpl);

				closePeers(socket, serverSocket);

				Assert.assertEquals(1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Throwable throwable = logRecord.getThrown();

				Assert.assertSame(IOException.class, throwable.getClass());

				// Successfully destroy

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				httpClientSPIAgent = new HttpClientSPIAgent(
					_spiConfiguration,
					new MockRegistrationReference(new MockIntraband()));

				socketBlockingQueue = httpClientSPIAgent.socketBlockingQueue;

				socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				socketBlockingQueue.add(socket);

				httpClientSPIAgent.destroy();

				closePeers(socket, serverSocket);

				Assert.assertTrue(logRecords.isEmpty());
			}
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testInit() throws Exception {
		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration,
			new MockRegistrationReference(new MockIntraband()));

		final AtomicBoolean throwException = new AtomicBoolean();

		SPI spi = new MockSPI() {

			@Override
			public void addServlet(
				String contextPath, String docBasePath, String mappingPattern,
				String servletClassName) {

				if (throwException.get()) {
					throw new RuntimeException();
				}

				Assert.assertEquals(
					HttpClientSPIAgent.SPI_AGENT_CONTEXT_PATH, contextPath);
				Assert.assertEquals(
					_spiConfiguration.getBaseDir(), docBasePath);
				Assert.assertEquals(
					HttpClientSPIAgent.MAPPING_PATTERN, mappingPattern);
				Assert.assertEquals(
					AcceptorServlet.class.getName(), servletClassName);
			}

			@Override
			public SPIConfiguration getSPIConfiguration() {
				return _spiConfiguration;
			}

		};

		httpClientSPIAgent.init(spi);

		throwException.set(true);

		try {
			httpClientSPIAgent.init(spi);

			Assert.fail();
		}
		catch (PortalResiliencyException pre) {
			Throwable throwable = pre.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
		}
	}

	@AdviseWith(
		adviceClasses = {PropsUtilAdvice.class}
	)
	@Test
	public void testPrepareRequest() throws Exception {
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED,
			Boolean.FALSE.toString());
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE,
			String.valueOf(Long.MAX_VALUE));

		Serializer serializer = new Serializer();

		serializer.writeString(_SERVLET_CONTEXT_NAME);
		serializer.writeObject(new SPIAgentRequest(_mockHttpServletRequest));

		long receipt = ReflectionTestUtil.invoke(
			MailboxUtil.class, "depositMail", new Class<?>[] {ByteBuffer.class},
			serializer.toByteBuffer());

		byte[] data = new byte[8];

		BigEndianCodec.putLong(data, 0, receipt);

		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration,
			new MockRegistrationReference(new MockIntraband()));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setContent(data);

		HttpServletRequest httpServletRequest =
			httpClientSPIAgent.prepareRequest(mockHttpServletRequest);

		Assert.assertNotNull(
			httpServletRequest.getAttribute(WebKeys.SPI_AGENT_REQUEST));
	}

	@Test
	public void testPrepareResponse() throws UnknownHostException {
		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration,
			new MockRegistrationReference(new MockIntraband()));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, _portlet);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		HttpServletResponse httpServletResponse =
			httpClientSPIAgent.prepareResponse(
				mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertSame(
			mockHttpServletResponse,
			mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_ORIGINAL_RESPONSE));
		Assert.assertNotNull(
			mockHttpServletRequest.getAttribute(WebKeys.SPI_AGENT_RESPONSE));

		Assert.assertSame(
			BufferCacheServletResponse.class, httpServletResponse.getClass());

		BufferCacheServletResponse bufferCacheServletResponse =
			(BufferCacheServletResponse)httpServletResponse;

		ServletResponse servletResponse =
			bufferCacheServletResponse.getResponse();

		Assert.assertSame(
			ReadOnlyServletResponse.class, servletResponse.getClass());

		ReadOnlyServletResponse readOnlyServletResponse =
			(ReadOnlyServletResponse)servletResponse;

		Assert.assertSame(
			mockHttpServletResponse, readOnlyServletResponse.getResponse());
	}

	@Test
	public void testReturnSocket() throws Exception {

		// Force close, successfully

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(),
				_spiConfiguration.getConnectorPort(), null);

		serverSocketChannel.configureBlocking(true);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
				new SPIConfiguration(
					null, null, serverSocket.getLocalPort(),
					_spiConfiguration.getBaseDir(), null, null, null),
				new MockRegistrationReference(new MockIntraband()));

			SocketChannel socketChannel = SocketChannel.open(
				httpClientSPIAgent.socketAddress);

			Socket socket = socketChannel.socket();

			httpClientSPIAgent.returnSocket(socket, true);

			Queue<Socket> socketBlockingQueue =
				httpClientSPIAgent.socketBlockingQueue;

			Assert.assertTrue(socketBlockingQueue.isEmpty());

			closePeers(socket, serverSocket);

			CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					HttpClientSPIAgent.class.getName(), Level.OFF);

			try {

				// Force close, failed without log

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				SocketImpl socketImpl = swapSocketImpl(socket, null);

				httpClientSPIAgent.returnSocket(socket, true);

				Assert.assertTrue(socketBlockingQueue.isEmpty());

				swapSocketImpl(socket, socketImpl);

				closePeers(socket, serverSocket);

				Assert.assertTrue(logRecords.isEmpty());

				// Force close, failed with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				socket = new Socket(
					InetAddressUtil.getLoopbackInetAddress(),
					_spiConfiguration.getConnectorPort());

				socketImpl = swapSocketImpl(socket, null);

				httpClientSPIAgent.returnSocket(socket, true);

				Assert.assertTrue(socketBlockingQueue.isEmpty());

				swapSocketImpl(socket, socketImpl);

				closePeers(socket, serverSocket);

				Assert.assertEquals(1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Throwable throwable = logRecord.getThrown();

				Assert.assertSame(IOException.class, throwable.getClass());
			}
			finally {
				captureHandler.close();
			}

			// socket.isConnected()

			httpClientSPIAgent.returnSocket(new Socket(), false);

			Assert.assertTrue(socketBlockingQueue.isEmpty());

			// socket.isInputShutdown()

			socketChannel = SocketChannel.open(
				httpClientSPIAgent.socketAddress);

			socket = socketChannel.socket();

			socket.shutdownInput();

			httpClientSPIAgent.returnSocket(socket, false);

			Assert.assertTrue(socketBlockingQueue.isEmpty());

			closePeers(socket, serverSocket);

			// socket.isOutputShutdown()

			socketChannel = SocketChannel.open(
				httpClientSPIAgent.socketAddress);

			socket = socketChannel.socket();

			socket.shutdownOutput();

			httpClientSPIAgent.returnSocket(socket, false);

			Assert.assertTrue(socketBlockingQueue.isEmpty());

			closePeers(socket, serverSocket);

			// Successfully return

			socketChannel = SocketChannel.open(
				httpClientSPIAgent.socketAddress);

			socket = socketChannel.socket();

			httpClientSPIAgent.returnSocket(socket, false);

			Assert.assertEquals(1, socketBlockingQueue.size());
			Assert.assertSame(socket, socketBlockingQueue.poll());

			closePeers(socket, serverSocket);
		}
	}

	@Test
	public void testService() throws Exception {

		// Unable to borrow a socket

		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration,
			new MockRegistrationReference(new MockIntraband()));

		try {
			httpClientSPIAgent.service(null, null);

			Assert.fail();
		}
		catch (PortalResiliencyException pre) {
			Throwable throwable = pre.getCause();

			Assert.assertSame(ConnectException.class, throwable.getClass());
		}

		// Unable to send, successfully close

		MockIntraband mockIntraband = new MockIntraband();

		IOException ioException = new IOException();

		mockIntraband.setIOException(ioException);

		httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration, new MockRegistrationReference(mockIntraband));

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(),
				_spiConfiguration.getConnectorPort(), null);

		serverSocketChannel.configureBlocking(true);

		SocketChannel socketChannel = SocketChannel.open(
			httpClientSPIAgent.socketAddress);

		Socket socket = socketChannel.socket();

		Queue<Socket> socketBlockingQueue =
			httpClientSPIAgent.socketBlockingQueue;

		socketBlockingQueue.add(socket);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, _portlet);

		try {
			httpClientSPIAgent.service(mockHttpServletRequest, null);

			Assert.fail();
		}
		catch (PortalResiliencyException pre) {
			Throwable throwable = pre.getCause();

			Assert.assertSame(IOException.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertSame(MailboxException.class, throwable.getClass());
			Assert.assertSame(ioException, throwable.getCause());
		}

		ServerSocket serverSocket = serverSocketChannel.socket();

		closePeers(socket, serverSocket);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			HttpClientSPIAgent.class.getName(), Level.OFF);

		try {

			// Unable to send, unable to close, without log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			socket = new Socket(
				InetAddressUtil.getLoopbackInetAddress(),
				_spiConfiguration.getConnectorPort());

			SocketImpl socketImpl = swapSocketImpl(socket, null);

			httpClientSPIAgent = new HttpClientSPIAgent(
				_spiConfiguration,
				new MockRegistrationReference(mockIntraband));

			socketBlockingQueue = httpClientSPIAgent.socketBlockingQueue;

			socketBlockingQueue.add(socket);

			try {
				httpClientSPIAgent.service(mockHttpServletRequest, null);

				Assert.fail();
			}
			catch (PortalResiliencyException pre) {
				Throwable throwable = pre.getCause();

				Assert.assertSame(IOException.class, throwable.getClass());
			}

			Assert.assertTrue(logRecords.isEmpty());

			swapSocketImpl(socket, socketImpl);

			closePeers(socket, serverSocket);

			// Unable to send, unable to close, with log

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			socket = new Socket(
				InetAddressUtil.getLoopbackInetAddress(),
				_spiConfiguration.getConnectorPort());

			socketImpl = swapSocketImpl(socket, null);

			httpClientSPIAgent = new HttpClientSPIAgent(
				_spiConfiguration,
				new MockRegistrationReference(mockIntraband));

			socketBlockingQueue = httpClientSPIAgent.socketBlockingQueue;

			socketBlockingQueue.add(socket);

			try {
				httpClientSPIAgent.service(mockHttpServletRequest, null);

				Assert.fail();
			}
			catch (PortalResiliencyException pre) {
				Throwable throwable = pre.getCause();

				Assert.assertSame(IOException.class, throwable.getClass());
			}

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(IOException.class, throwable.getClass());

			swapSocketImpl(socket, socketImpl);
		}
		finally {
			captureHandler.close();
		}

		closePeers(socket, serverSocket);

		// Successfully send

		socketChannel = SocketChannel.open(httpClientSPIAgent.socketAddress);

		socketChannel.configureBlocking(true);

		mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				try {
					long receipt = ReflectionTestUtil.invoke(
						MailboxUtil.class, "depositMail",
						new Class<?>[] {ByteBuffer.class},
						datagram.getDataByteBuffer());

					byte[] receiptData = new byte[8];

					BigEndianCodec.putLong(receiptData, 0, receipt);

					return Datagram.createResponseDatagram(
						datagram, ByteBuffer.wrap(receiptData));
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		};

		httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration, new MockRegistrationReference(mockIntraband));

		socketBlockingQueue = httpClientSPIAgent.socketBlockingQueue;

		socket = socketChannel.socket();

		socketBlockingQueue.add(socket);

		Serializer serializer = new Serializer();

		serializer.writeString(_SERVLET_CONTEXT_NAME);
		serializer.writeObject(new SPIAgentResponse(_SERVLET_CONTEXT_NAME));

		long receipt = ReflectionTestUtil.invoke(
			MailboxUtil.class, "depositMail", new Class<?>[] {ByteBuffer.class},
			serializer.toByteBuffer());

		Socket remoteSocket = serverSocket.accept();

		OutputStream outputStream = remoteSocket.getOutputStream();

		outputStream.write("HTTP/1.1 200 OK\n\n".getBytes("US-ASCII"));

		byte[] receiptData = new byte[8];

		BigEndianCodec.putLong(receiptData, 0, receipt);

		outputStream.write(receiptData);

		outputStream.flush();

		httpClientSPIAgent.service(
			mockHttpServletRequest, new MockHttpServletResponse());

		socket.close();
		remoteSocket.close();
		serverSocket.close();
	}

	@Test
	public void testTransferResponse() throws Exception {

		// Exception

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(new MockIntraband());

		HttpClientSPIAgent httpClientSPIAgent = new HttpClientSPIAgent(
			_spiConfiguration, mockRegistrationReference);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST,
			new SPIAgentRequest(_mockHttpServletRequest));

		RecordSPIAgentResponse recordSPIAgentResponse =
			new RecordSPIAgentResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_RESPONSE, recordSPIAgentResponse);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_ORIGINAL_RESPONSE, mockHttpServletResponse);

		Exception exception = new Exception();

		httpClientSPIAgent.transferResponse(
			mockHttpServletRequest,
			new BufferCacheServletResponse(new MockHttpServletResponse()),
			exception);

		Assert.assertNull(
			mockHttpServletRequest.getAttribute(WebKeys.SPI_AGENT_REQUEST));
		Assert.assertNull(
			mockHttpServletRequest.getAttribute(WebKeys.SPI_AGENT_RESPONSE));
		Assert.assertSame(exception, recordSPIAgentResponse.exception);
		Assert.assertNull(
			mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_ORIGINAL_RESPONSE));
		Assert.assertEquals(8, mockHttpServletResponse.getContentLength());
		Assert.assertNull(recordSPIAgentResponse._request);
		Assert.assertNull(recordSPIAgentResponse._bufferCacheServletResponse);
		Assert.assertSame(
			mockRegistrationReference,
			recordSPIAgentResponse._registrationReference);
		Assert.assertSame(
			mockHttpServletResponse.getOutputStream(),
			recordSPIAgentResponse._outputStream);

		// Response

		SPIAgentRequest spiAgentRequest = new SPIAgentRequest(
			_mockHttpServletRequest);

		File tempFile = File.createTempFile(
			HttpClientSPIAgentTest.class.getName(), null);

		Assert.assertTrue(tempFile.exists());

		spiAgentRequest.requestBodyFile = tempFile;

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, spiAgentRequest);

		recordSPIAgentResponse = new RecordSPIAgentResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_RESPONSE, recordSPIAgentResponse);

		mockHttpServletResponse = new MockHttpServletResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_ORIGINAL_RESPONSE, mockHttpServletResponse);

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(new MockHttpServletResponse());

		httpClientSPIAgent.transferResponse(
			mockHttpServletRequest, bufferCacheServletResponse, null);

		Assert.assertFalse(tempFile.exists());
		Assert.assertNull(
			mockHttpServletRequest.getAttribute(WebKeys.SPI_AGENT_REQUEST));
		Assert.assertNull(
			mockHttpServletRequest.getAttribute(WebKeys.SPI_AGENT_RESPONSE));
		Assert.assertNull(recordSPIAgentResponse.exception);
		Assert.assertNull(
			mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_ORIGINAL_RESPONSE));
		Assert.assertEquals(8, mockHttpServletResponse.getContentLength());
		Assert.assertSame(
			mockHttpServletRequest, recordSPIAgentResponse._request);
		Assert.assertSame(
			bufferCacheServletResponse,
			recordSPIAgentResponse._bufferCacheServletResponse);
		Assert.assertSame(
			mockRegistrationReference,
			recordSPIAgentResponse._registrationReference);
		Assert.assertSame(
			mockHttpServletResponse.getOutputStream(),
			recordSPIAgentResponse._outputStream);

		// Undeletable request body file

		spiAgentRequest = new SPIAgentRequest(_mockHttpServletRequest);

		tempFile = File.createTempFile(
			HttpClientSPIAgentTest.class.getName(), null);

		Assert.assertTrue(tempFile.exists());

		spiAgentRequest.requestBodyFile = tempFile;

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, spiAgentRequest);

		recordSPIAgentResponse = new RecordSPIAgentResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_RESPONSE, recordSPIAgentResponse);

		mockHttpServletResponse = new MockHttpServletResponse();

		mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_ORIGINAL_RESPONSE, mockHttpServletResponse);

		bufferCacheServletResponse = new BufferCacheServletResponse(
			new MockHttpServletResponse());

		Assert.assertTrue(tempFile.delete());

		httpClientSPIAgent.transferResponse(
			mockHttpServletRequest, bufferCacheServletResponse, null);

		Set<String> files = ReflectionTestUtil.getFieldValue(
			Class.forName("java.io.DeleteOnExitHook"), "files");

		Assert.assertTrue(files.contains(tempFile.getPath()));
	}

	protected void closePeers(Socket socket, ServerSocket serverSocket)
		throws IOException {

		socket.close();

		socket = serverSocket.accept();

		socket.close();
	}

	protected void closeSocketChannel(
			SocketChannel socketChannel, FileDescriptor fileDescriptor)
		throws IOException {

		ReflectionTestUtil.setFieldValue(socketChannel, "fd", fileDescriptor);

		socketChannel.close();
	}

	protected SocketImpl swapSocketImpl(Socket socket, SocketImpl socketImpl) {
		SocketImpl oldSocketImpl = ReflectionTestUtil.getFieldValue(
			socket, "impl");

		if (socketImpl == null) {
			Socket unbindSocket = new Socket();

			socketImpl = ReflectionTestUtil.getFieldValue(unbindSocket, "impl");

			ReflectionTestUtil.setFieldValue(
				socketImpl, "cmdsock",
				new Socket() {

					@Override
					public synchronized void close() throws IOException {
						throw new IOException();
					}

				});
		}

		ReflectionTestUtil.setFieldValue(socket, "impl", socketImpl);

		return oldSocketImpl;
	}

	private static final String _SERVLET_CONTEXT_NAME = "SERVLET_CONTEXT_NAME";

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private Portlet _portlet;
	private final SPIConfiguration _spiConfiguration = new SPIConfiguration(
		null, null, 1234, "baseDir", null, null, null);

	private static class RecordSPIAgentResponse extends SPIAgentResponse {

		public RecordSPIAgentResponse() {
			super(_SERVLET_CONTEXT_NAME);
		}

		@Override
		public void captureResponse(
			HttpServletRequest request,
			BufferCacheServletResponse bufferCacheServletResponse) {

			_request = request;
			_bufferCacheServletResponse = bufferCacheServletResponse;
		}

		@Override
		public void writeTo(
			RegistrationReference registrationReference,
			OutputStream outputStream) {

			_registrationReference = registrationReference;
			_outputStream = outputStream;
		}

		private BufferCacheServletResponse _bufferCacheServletResponse;
		private OutputStream _outputStream;
		private RegistrationReference _registrationReference;
		private HttpServletRequest _request;

	}

}