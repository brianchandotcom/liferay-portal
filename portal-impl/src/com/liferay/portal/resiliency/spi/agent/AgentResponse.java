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

import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.resiliency.spi.action.PortalResiliencyAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Serializable;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 */
public class AgentResponse extends AgentSerializable {

	public void captureRequestSessionAttributes(HttpServletRequest request) {

		// Request attributes

		_distributedRequestAttributes = extractDistributedRequestAttributes(
			request, Direction.Response);

		// Session attributes

		AgentRequest agentRequest = (AgentRequest)request.getAttribute(
			SPIAgent.AGENT_REQUEST);

		Map<String, Serializable> originalSessionAttributes =
			agentRequest.getOriginalSessionAttributes();

		HttpSession session = request.getSession();

		Map<String, Serializable> newSessionAttributes =
			extractSessionAttributes(session);

		Set<String> removedSessionAttributeNames =
			originalSessionAttributes.keySet();

		removedSessionAttributeNames.removeAll(newSessionAttributes.keySet());

		Map<String, Serializable> deltaSessionAttributes =
			new HashMap<String, Serializable>(newSessionAttributes);

		for (String removedSessionAttributeName :
				removedSessionAttributeNames) {

			deltaSessionAttributes.put(removedSessionAttributeName, null);
		}

		_deltaSessionAttributes = deltaSessionAttributes;

		captureThreadLocals();
	}

	public void captureResponse(
			HttpServletRequest request,
			BufferCacheServletResponse bufferCacheServletResponse)
		throws IOException {

		Boolean portalResiliencyActionTag = (Boolean)request.getAttribute(
			PortalResiliencyAction.PORTAL_RESILIENCY_ACTION_TAG);

		if (portalResiliencyActionTag == Boolean.TRUE) {
			_portalResiliencyResponse = true;
		}

		// Response metainfo

		_metaData = bufferCacheServletResponse.getMetaData();

		// Response content

		if (bufferCacheServletResponse.isByteMode()) {
			ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

			if (byteBuffer.remaining() > 0) {
				byte[] byteData = byteBuffer.array();

				if ((byteBuffer.arrayOffset() == 0) &&
					(byteBuffer.remaining() == byteData.length)) {

					_byteData = byteData;
				}
				else {
					_byteData = new byte[byteBuffer.remaining()];

					System.arraycopy(
						byteData, byteBuffer.arrayOffset(), _byteData, 0,
						byteBuffer.remaining());
				}
			}
		}

		if (bufferCacheServletResponse.isCharMode()) {
			String content = bufferCacheServletResponse.getString();

			if (content.length() > 0) {
				if (ParamUtil.get(
						request, "prpsf",
						PropsValues.PORTAL_RESILIENCY_PORTLET_SHOW_FOOTER)) {

					int index = content.lastIndexOf("</");

					if (index > 0) {
						StringBundler sb = new StringBundler(6);

						sb.append(content.substring(0, index));
						sb.append("<div class='portlet-msg-info'>");
						sb.append("<b>Above portlet is from SPI ");
						sb.append(PortalUtil.getPortalPort(false));
						sb.append("</b></div>");
						sb.append(content.substring(index));

						content = sb.toString();
					}
				}

				_stringData = content;
			}
		}
	}

	public void populate(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalResiliencyException {

		// Exception

		if (_exception != null) {
			throw new PortalResiliencyException("SPI Exception", _exception);
		}

		if (_portalResiliencyResponse) {

			// Layout type setting

			String typeSetting = (String)_distributedRequestAttributes.remove(
				SPIAgent.LAYOUT_TYPE_SETTINGS);

			if (typeSetting != null) {
				Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

				layout.setTypeSettings(typeSetting);
			}

			// Request Attributes

			for (Map.Entry<String, Serializable> entry :
					_distributedRequestAttributes.entrySet()) {

				String name = entry.getKey();
				Serializable value = entry.getValue();

				request.setAttribute(name, value);
			}

			// Session Attributes

			HttpSession session = request.getSession();

			for (Map.Entry<String, Serializable> entry :
					_deltaSessionAttributes.entrySet()) {

				String name = entry.getKey();
				Serializable value = entry.getValue();

				session.setAttribute(name, value);
			}

			try {

				// Response metainfo

				MetaInfoCacheServletResponse.finishResponse(
					_metaData, response);

				// Response content

				if (_byteData != null) {
					ServletResponseUtil.write(
						response, ByteBuffer.wrap(_byteData));
				}

				if (_stringData != null) {
					ServletResponseUtil.write(
						response, CharBuffer.wrap(_stringData));
				}
			}
			catch (IOException ioe) {
				throw new PortalResiliencyException(ioe);
			}

			restoreThreadLocals();
		}
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	private byte[] _byteData;
	private Map<String, Serializable> _deltaSessionAttributes;
	private Map<String, Serializable> _distributedRequestAttributes;
	private Exception _exception;
	private MetaInfoCacheServletResponse.MetaData _metaData;
	private boolean _portalResiliencyResponse;
	private String _stringData;

}