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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
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
public class SPIAgentResponse extends SPIAgentSerializable {

	public SPIAgentResponse(String servletContextName) {
		super(servletContextName);
	}

	public void captureRequestSessionAttributes(
		HttpServletRequest httpServletRequest) {

		distributedRequestAttributes = extractDistributedRequestAttributes(
			httpServletRequest, Direction.RESPONSE);

		SPIAgentRequest spiAgentRequest =
			(SPIAgentRequest)httpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_REQUEST);

		Map<String, Serializable> originalSessionAttributes =
			spiAgentRequest.getOriginalSessionAttributes();

		Map<String, Serializable> newSessionAttributes =
			extractSessionAttributes(httpServletRequest);

		Set<String> removedSessionAttributeNames =
			originalSessionAttributes.keySet();

		removedSessionAttributeNames.removeAll(newSessionAttributes.keySet());

		deltaSessionAttributes = new HashMap<>(newSessionAttributes);

		for (String removedSessionAttributeName :
				removedSessionAttributeNames) {

			deltaSessionAttributes.put(removedSessionAttributeName, null);
		}

		captureThreadLocals();
	}

	public void captureResponse(
			HttpServletRequest httpServletRequest,
			BufferCacheServletResponse bufferCacheServletResponse)
		throws IOException {

		Boolean portalResiliencyAction =
			(Boolean)httpServletRequest.getAttribute(
				WebKeys.PORTAL_RESILIENCY_ACTION);

		if (portalResiliencyAction != Boolean.TRUE) {
			portalResiliencyResponse = false;

			return;
		}

		portalResiliencyResponse = true;

		metaData = bufferCacheServletResponse.getMetaData();

		byteData = null;

		if (bufferCacheServletResponse.isByteMode()) {
			ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

			if (byteBuffer.remaining() > 0) {
				if (byteBuffer.hasArray()) {
					byte[] byteArray = byteBuffer.array();

					if (byteBuffer.remaining() == byteArray.length) {
						byteData = byteArray;
					}
				}

				if (byteData == null) {
					byteData = new byte[byteBuffer.remaining()];

					byteBuffer.get(byteData);
				}
			}
		}

		stringData = null;

		if (!bufferCacheServletResponse.isCharMode()) {
			return;
		}

		String content = bufferCacheServletResponse.getString();

		if (content.length() == 0) {
			return;
		}

		if (ParamUtil.get(
				httpServletRequest, "portalResiliencyPortletShowFooter",
				PropsValues.PORTAL_RESILIENCY_PORTLET_SHOW_FOOTER)) {

			int index = content.lastIndexOf("</div>");

			if (index > 0) {
				StringBundler sb = new StringBundler(6);

				sb.append(content.substring(0, index));
				sb.append("<div class=\"alert alert-info\"><strong>");
				sb.append("This portlet is from SPI ");
				sb.append(PortalUtil.getPortalLocalPort(false));
				sb.append("</strong></div>");
				sb.append(content.substring(index));

				content = sb.toString();
			}
		}

		stringData = content;
	}

	public void populate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalResiliencyException {

		if (exception != null) {
			throw new PortalResiliencyException("SPI exception", exception);
		}

		if (!portalResiliencyResponse) {
			return;
		}

		String typeSettings = (String)distributedRequestAttributes.remove(
			WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS);

		if (typeSettings != null) {
			Layout layout = (Layout)httpServletRequest.getAttribute(
				WebKeys.LAYOUT);

			layout.setTypeSettings(typeSettings);
		}

		for (Map.Entry<String, Serializable> entry :
				distributedRequestAttributes.entrySet()) {

			httpServletRequest.setAttribute(entry.getKey(), entry.getValue());
		}

		HttpSession session = httpServletRequest.getSession();

		for (Map.Entry<String, Serializable> entry :
				deltaSessionAttributes.entrySet()) {

			session.setAttribute(entry.getKey(), entry.getValue());
		}

		try {
			MetaInfoCacheServletResponse.finishResponse(
				metaData, httpServletResponse);

			if (byteData != null) {
				ServletResponseUtil.write(
					httpServletResponse, ByteBuffer.wrap(byteData));
			}

			if (stringData != null) {
				ServletResponseUtil.write(
					httpServletResponse, CharBuffer.wrap(stringData));
			}
		}
		catch (IOException ioe) {
			throw new PortalResiliencyException(ioe);
		}

		restoreThreadLocals();
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	protected byte[] byteData;
	protected Map<String, Serializable> deltaSessionAttributes;
	protected Map<String, Serializable> distributedRequestAttributes;
	protected Exception exception;
	protected MetaInfoCacheServletResponse.MetaData metaData;
	protected boolean portalResiliencyResponse;
	protected String stringData;

}