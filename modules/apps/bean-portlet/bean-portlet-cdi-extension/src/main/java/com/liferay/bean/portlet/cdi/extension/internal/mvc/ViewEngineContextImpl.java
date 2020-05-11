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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.bean.portlet.extension.ViewRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Locale;

import javax.mvc.Models;
import javax.mvc.engine.ViewEngineContext;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author  Neil Griffin
 */
public class ViewEngineContextImpl implements ViewEngineContext {

	public ViewEngineContextImpl(
		Configuration configuration, Locale locale, MimeResponse mimeResponse,
		Models models, PortletRequest portletRequest) {

		_configuration = configuration;
		_locale = locale;
		_mimeResponse = mimeResponse;
		_models = models;
		_portletRequest = portletRequest;
	}

	@Override
	public Configuration getConfiguration() {
		return _configuration;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public MediaType getMediaType() {
		if (_mediaType == null) {
			String contentType = _mimeResponse.getContentType();

			if (contentType == null) {
				_mediaType = MediaType.TEXT_HTML_TYPE;
			}
			else {
				String type = contentType;
				String subtype = null;

				int pos = contentType.indexOf('/');

				if (pos > 0) {
					type = contentType.substring(0, pos);
					subtype = contentType.substring(pos + 1);
				}

				_mediaType = new MediaType(
					type, subtype, _mimeResponse.getCharacterEncoding());
			}
		}

		return _mediaType;
	}

	@Override
	public Models getModels() {
		return _models;
	}

	@Override
	public OutputStream getOutputStream() {
		try {
			return _mimeResponse.getPortletOutputStream();
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);

			return null;
		}
	}

	@Override
	public <T> T getRequest(Class<T> type) {
		return type.cast(_portletRequest);
	}

	@Override
	public ResourceInfo getResourceInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getResponse(Class<T> type) {
		return type.cast(_mimeResponse);
	}

	@Override
	public MultivaluedMap<String, Object> getResponseHeaders() {
		throw new UnsupportedOperationException();
	}

	@Override
	public UriInfo getUriInfo() {
		return new UriInfoImpl();
	}

	@Override
	public String getView() {
		return (String)_portletRequest.getAttribute(ViewRenderer.VIEW_NAME);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewEngineContextImpl.class);

	private final Configuration _configuration;
	private final Locale _locale;
	private MediaType _mediaType;
	private final MimeResponse _mimeResponse;
	private final Models _models;
	private final PortletRequest _portletRequest;

}