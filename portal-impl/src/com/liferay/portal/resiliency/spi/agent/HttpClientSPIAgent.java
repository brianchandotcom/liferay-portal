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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.agent.AcceptorServlet;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ReadOnlyServletResponse;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import java.nio.charset.Charset;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class HttpClientSPIAgent implements SPIAgent {

	public HttpClientSPIAgent(
			SPIConfiguration spiConfiguration,
			RegistrationReference registrationReference)
		throws UnknownHostException {

		this.registrationReference = registrationReference;

		socketAddress = new InetSocketAddress(
			InetAddressUtil.getLoopbackInetAddress(),
			spiConfiguration.getConnectorPort());
		socketBlockingQueue = new ArrayBlockingQueue<Socket>(
			PropsValues.PORTAL_RESILIENCY_SPI_AGENT_CLIENT_POOL_MAX_SIZE);

		String httpServletRequestContentString =
			"POST " + SPI_AGENT_CONTEXT_PATH + MAPPING_PATTERN +
				" HTTP/1.1\r\nHost: localhost:" +
					spiConfiguration.getConnectorPort() + "\r\n" +
						"Content-Length: 8\r\n\r\n";

		httpServletRequestContent = httpServletRequestContentString.getBytes(
			Charset.forName("US-ASCII"));
	}

	@Override
	public void destroy() {
		Iterator<Socket> iterator = socketBlockingQueue.iterator();

		while (iterator.hasNext()) {
			Socket socket = iterator.next();

			iterator.remove();

			try {
				socket.close();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
		}
	}

	@Override
	public void init(SPI spi) throws PortalResiliencyException {
		try {
			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			spi.addServlet(
				SPI_AGENT_CONTEXT_PATH, spiConfiguration.getBaseDir(),
				MAPPING_PATTERN, AcceptorServlet.class.getName());
		}
		catch (Exception e) {
			throw new PortalResiliencyException(e);
		}
	}

	@Override
	public HttpServletRequest prepareRequest(HttpServletRequest request)
		throws IOException {

		SPIAgentRequest spiAgentRequest = SPIAgentRequest.readFrom(
			request.getInputStream());

		HttpServletRequest spiAgentHttpServletRequest =
			spiAgentRequest.populateRequest(request);

		spiAgentHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, spiAgentRequest);

		return spiAgentHttpServletRequest;
	}

	@Override
	public HttpServletResponse prepareResponse(
		HttpServletRequest request, HttpServletResponse response) {

		HttpServletResponse spiAgentHttpServletResponse =
			new BufferCacheServletResponse(
				new ReadOnlyServletResponse(response));

		request.setAttribute(WebKeys.SPI_AGENT_ORIGINAL_RESPONSE, response);

		Portlet portlet = (Portlet)request.getAttribute(
			WebKeys.SPI_AGENT_PORTLET);

		request.setAttribute(
			WebKeys.SPI_AGENT_RESPONSE,
			new SPIAgentResponse(portlet.getContextName()));

		return spiAgentHttpServletResponse;
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException {

		Socket socket = null;

		try {
			socket = borrowSocket();

			SPIAgentRequest spiAgentRequest = new SPIAgentRequest(request);

			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(httpServletRequestContent);

			spiAgentRequest.writeTo(registrationReference, outputStream);

			InputStream inputStream = socket.getInputStream();

			DataInputStream dataInputStream = new DataInputStream(inputStream);

			boolean forceCloseSocket = consumeHttpResponseHead(dataInputStream);

			SPIAgentResponse spiAgentResponse = SPIAgentResponse.readFrom(
				dataInputStream);

			spiAgentResponse.populate(request, response);

			returnSocket(socket, forceCloseSocket);

			socket = null;
		}
		catch (IOException ioe) {
			throw new PortalResiliencyException(ioe);
		}
		finally {
			if (socket != null) {
				try {
					socket.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe, ioe);
					}
				}
			}
		}
	}

	@Override
	public void transferResponse(
			HttpServletRequest request, HttpServletResponse response,
			Exception exception)
		throws IOException {

		SPIAgentRequest spiAgentRequest = (SPIAgentRequest)request.getAttribute(
			WebKeys.SPI_AGENT_REQUEST);

		request.removeAttribute(WebKeys.SPI_AGENT_REQUEST);

		File requestBodyFile = spiAgentRequest.requestBodyFile;

		if (requestBodyFile != null) {
			if (!requestBodyFile.delete()) {
				requestBodyFile.deleteOnExit();
			}
		}

		SPIAgentResponse spiAgentResponse =
			(SPIAgentResponse)request.getAttribute(WebKeys.SPI_AGENT_RESPONSE);

		request.removeAttribute(WebKeys.SPI_AGENT_RESPONSE);

		if (exception != null) {
			spiAgentResponse.setException(exception);
		}
		else {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			spiAgentResponse.captureResponse(
				request, bufferCacheServletResponse);
		}

		HttpServletResponse originalResponse =
			(HttpServletResponse)request.getAttribute(
				WebKeys.SPI_AGENT_ORIGINAL_RESPONSE);

		request.removeAttribute(WebKeys.SPI_AGENT_ORIGINAL_RESPONSE);

		originalResponse.setContentLength(8);

		spiAgentResponse.writeTo(
			registrationReference, originalResponse.getOutputStream());
	}

	protected Socket borrowSocket() throws IOException {
		Socket socket = socketBlockingQueue.poll();

		if (socket != null) {
			if (socket.isClosed() || !socket.isConnected() ||
				socket.isInputShutdown() || socket.isOutputShutdown()) {

				try {
					socket.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe, ioe);
					}
				}

				socket = null;
			}
		}

		if (socket == null) {
			socket = new Socket();

			socket.connect(socketAddress);
		}

		return socket;
	}

	protected boolean consumeHttpResponseHead(DataInput dataInput)
		throws IOException {

		String statusLine = dataInput.readLine();

		if (!statusLine.equals("HTTP/1.1 200 OK")) {
			throw new IOException("Error status line: " + statusLine);
		}

		boolean forceCloseSocket = false;

		String line = null;

		while (((line = dataInput.readLine()) != null) && (line.length() > 0)) {
			String[] headerKeyValuePair = StringUtil.split(
				line, CharPool.COLON);

			String headerName = headerKeyValuePair[0].trim();

			headerName = StringUtil.toLowerCase(headerName);

			if (headerName.equals("connection")) {
				String headerValue = headerKeyValuePair[1].trim();

				headerValue = StringUtil.toLowerCase(headerValue);

				if (headerValue.equals("close")) {
					forceCloseSocket = true;
				}
			}
		}

		return forceCloseSocket;
	}

	protected void returnSocket(Socket socket, boolean forceCloseSocket) {
		boolean pooled = false;

		if (!forceCloseSocket && socket.isConnected() &&
			!socket.isInputShutdown() && !socket.isOutputShutdown()) {

			pooled = socketBlockingQueue.offer(socket);
		}

		if (!pooled) {
			try {
				socket.close();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
		}
	}

	protected static final String MAPPING_PATTERN = "/acceptor";

	protected static final String SPI_AGENT_CONTEXT_PATH = "/spi_agent";

	protected final byte[] httpServletRequestContent;
	protected final RegistrationReference registrationReference;
	protected final SocketAddress socketAddress;
	protected final BlockingQueue<Socket> socketBlockingQueue;

	private static final Log _log = LogFactoryUtil.getLog(
		HttpClientSPIAgent.class);

}