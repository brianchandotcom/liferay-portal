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
import com.liferay.portal.util.PropsValues;

import java.io.DataInput;
import java.io.DataInputStream;
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

		socketPool = new ArrayBlockingQueue<Socket>(
			PropsValues.PORTAL_RESILIENCY_SPI_AGENT_HTTPCLIENTSPIAGENT_SOCKETPOOL_MAX_SIZE);

		String httpRequestContentString =
			"POST " + AGENT_CONTEXT_PATH + MAPPING_PATTERN +
				" HTTP/1.1\r\nHost: localhost:" +
					spiConfiguration.getConnectorPort() + "\r\n" +
						"Content-Length: 8\r\n\r\n";

		httpRequestContent = httpRequestContentString.getBytes(
			Charset.forName("US-ASCII"));
	}

	@Override
	public void destroy() {
		Iterator<Socket> iterator = socketPool.iterator();

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
				AGENT_CONTEXT_PATH, spiConfiguration.getBaseDir(),
				MAPPING_PATTERN, AcceptorServlet.class.getName());
		}
		catch (Exception e) {
			throw new PortalResiliencyException(e);
		}
	}

	@Override
	public HttpServletRequest prepareRequest(HttpServletRequest request)
		throws IOException {

		AgentRequest agentRequest = AgentRequest.readFrom(
			request.getInputStream());

		HttpServletRequest agentServletRequest = agentRequest.populateRequest(
			request);

		agentServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST, agentRequest);

		return agentServletRequest;
	}

	@Override
	public HttpServletResponse prepareResponse(
		HttpServletRequest request, HttpServletResponse response) {

		BufferCacheServletResponse agentServletResponse =
			new BufferCacheServletResponse(
				new ReadOnlyServletResponse(response));

		request.setAttribute(WebKeys.SPI_AGENT_ORIGINAL_RESPONSE, response);
		request.setAttribute(WebKeys.SPI_AGENT_RESPONSE, new AgentResponse());

		return agentServletResponse;
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException {

		Socket socket = null;

		try {
			socket = borrowSocket();

			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(httpRequestContent);

			AgentRequest agentRequest = new AgentRequest(request);

			agentRequest.writeTo(registrationReference, outputStream);

			InputStream inputStream = socket.getInputStream();

			DataInputStream dataInputStream = new DataInputStream(inputStream);

			boolean forceCloseSocket = consumeHttpResponseHead(dataInputStream);

			AgentResponse agentResponse = AgentResponse.readFrom(
				dataInputStream);

			agentResponse.populate(request, response);

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

		request.removeAttribute(WebKeys.SPI_AGENT_REQUEST);

		AgentResponse agentResponse = (AgentResponse)request.getAttribute(
			WebKeys.SPI_AGENT_RESPONSE);

		request.removeAttribute(WebKeys.SPI_AGENT_RESPONSE);

		if (exception != null) {
			agentResponse.setException(exception);
		}
		else {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			agentResponse.captureResponse(request, bufferCacheServletResponse);
		}

		HttpServletResponse originalResponse =
			(HttpServletResponse)request.getAttribute(
				WebKeys.SPI_AGENT_ORIGINAL_RESPONSE);

		request.removeAttribute(WebKeys.SPI_AGENT_ORIGINAL_RESPONSE);

		originalResponse.setContentLength(8);

		agentResponse.writeTo(
			registrationReference, originalResponse.getOutputStream());
	}

	protected Socket borrowSocket() throws IOException {
		Socket socket = socketPool.poll();

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
			throw new IOException("Error Status Line : " + statusLine);
		}

		boolean forceCloseSocket = false;

		String line = null;

		while (((line = dataInput.readLine()) != null) && (line.length() > 0)) {
			String[] headerKeyValuePair = StringUtil.split(
				line, CharPool.COLON);

			String headerName = headerKeyValuePair[0].trim();

			headerName = headerName.toLowerCase();

			if (headerName.equals("connection")) {
				String headerValue = headerKeyValuePair[1].trim();

				headerValue = headerValue.toLowerCase();

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

			pooled = socketPool.offer(socket);
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

	protected static final String AGENT_CONTEXT_PATH = "/spiagent";
	protected static final String MAPPING_PATTERN = "/acceptor";

	protected final byte[] httpRequestContent;
	protected final RegistrationReference registrationReference;
	protected final SocketAddress socketAddress;
	protected final BlockingQueue<Socket> socketPool;

	private static Log _log = LogFactoryUtil.getLog(HttpClientSPIAgent.class);

}