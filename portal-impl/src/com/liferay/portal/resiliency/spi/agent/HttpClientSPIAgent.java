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
import com.liferay.portal.util.PropsValues;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

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

		_registrationReference = registrationReference;

		_socketAddress = new InetSocketAddress(
			InetAddressUtil.getLoopbackInetAddress(),
			spiConfiguration.getConnectorPort());

		_socketPool = new ArrayBlockingQueue<Socket>(
			PropsValues.PORTAL_RESILIENCY_SPI_AGENT_HTTPCLIENTSPIAGENT_SOCKETPOOL_MAX_SIZE);

		try {
			_httpRequestContent =
				("POST " + _AGENT_CONTEXT_PATH + _MAPPING_PATTERN +
					" HTTP/1.1\r\nHost: localhost:" +
						spiConfiguration.getConnectorPort() + "\r\n" +
							"Content-Length: 8\r\n\r\n").getBytes("US-ASCII");
		}
		catch (UnsupportedEncodingException uee) {
			throw new RuntimeException(uee);
		}
	}

	public void destroy() {
		Iterator<Socket> iterator = _socketPool.iterator();

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

	public void init(SPI spi) throws PortalResiliencyException {
		try {
			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			spi.addServlet(
				_AGENT_CONTEXT_PATH, spiConfiguration.getBaseDir(),
				_MAPPING_PATTERN, AcceptorServlet.class.getName());
		}
		catch (Exception e) {
			throw new PortalResiliencyException(e);
		}
	}

	public HttpServletRequest prepareRequest(HttpServletRequest request)
		throws IOException {

		AgentRequest agentRequest = AgentRequest.readFrom(
			request.getInputStream());

		HttpServletRequest agentServletRequest = agentRequest.populateRequest(
			request);

		agentServletRequest.setAttribute(AGENT_REQUEST, agentRequest);

		return agentServletRequest;
	}

	public HttpServletResponse prepareResponse(
		HttpServletRequest request, HttpServletResponse response) {

		BufferCacheServletResponse agentServletResponse =
			new BufferCacheServletResponse(
				new ReadOnlyServletResponse(response));

		request.setAttribute(_ORIGINAL_RESPONSE, response);

		AgentResponse agentResponse = new AgentResponse();

		request.setAttribute(AGENT_RESPONSE, agentResponse);

		return agentServletResponse;
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException {

		try {
			Socket socket = borrowSocket();

			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(_httpRequestContent);

			AgentRequest agentRequest = new AgentRequest(request);

			agentRequest.writeTo(_registrationReference, outputStream);

			InputStream inputStream = socket.getInputStream();

			DataInputStream dataInputStream = new DataInputStream(inputStream);

			boolean forceCloseSocket = consumeHttpResponseHead(dataInputStream);

			AgentResponse agentResponse = AgentResponse.readFrom(
				dataInputStream);

			agentResponse.populate(request, response);

			returnSocket(socket, forceCloseSocket);
		}
		catch (IOException ioe) {
			throw new PortalResiliencyException(ioe);
		}
	}

	public void transferResponse(
			HttpServletRequest request, HttpServletResponse response,
			Exception exception)
		throws IOException {

		request.removeAttribute(AGENT_REQUEST);

		AgentResponse agentResponse = (AgentResponse)request.getAttribute(
			AGENT_RESPONSE);

		request.removeAttribute(AGENT_RESPONSE);

		HttpServletResponse originalResponse =
			(HttpServletResponse)request.getAttribute(_ORIGINAL_RESPONSE);

		request.removeAttribute(_ORIGINAL_RESPONSE);

		if (exception != null) {
			agentResponse.setException(exception);
		}
		else {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			agentResponse.captureResponse(request, bufferCacheServletResponse);
		}

		originalResponse.setContentLength(8);

		agentResponse.writeTo(
			_registrationReference, originalResponse.getOutputStream());
	}

	protected Socket borrowSocket() throws IOException {
		Socket socket = _socketPool.poll();

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

			socket.connect(_socketAddress);
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

		while ((line = dataInput.readLine()) != null) {
			if (line.length() > 0) {
				String[] headerKeyValuePair = StringUtil.split(
					line, CharPool.COLON);

				String headerName = headerKeyValuePair[0];
				headerName = headerName.trim().toLowerCase();

				if (headerName.equals("connection")) {
					String headerValue = headerKeyValuePair[1];
					headerValue = headerValue.trim().toLowerCase();

					if (headerValue.equals("close")) {
						forceCloseSocket = true;
					}
				}

			}
			else {
				break;
			}
		}

		return forceCloseSocket;
	}

	protected void returnSocket(Socket socket, boolean forceCloseSocket) {
		boolean pooled = false;

		if (!forceCloseSocket && socket.isConnected() &&
			!socket.isInputShutdown() && !socket.isOutputShutdown()) {

			pooled = _socketPool.offer(socket);
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

	private static final Log _log = LogFactoryUtil.getLog(
		HttpClientSPIAgent.class);

	private static final String _AGENT_CONTEXT_PATH = "/spiagent";

	private static final String _MAPPING_PATTERN = "/acceptor";

	private static final String _ORIGINAL_RESPONSE = "ORIGINAL_RESPONSE";

	private final byte[] _httpRequestContent;
	private final RegistrationReference _registrationReference;
	private final SocketAddress _socketAddress;
	private final BlockingQueue<Socket> _socketPool;

}