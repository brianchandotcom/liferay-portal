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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import javax.mvc.Models;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.ws.rs.core.Configuration;

/**
 * @author  Neil Griffin
 */
public class ViewEngineContextImpl extends BaseViewEngineContext {

	public ViewEngineContextImpl(
		Configuration configuration, MimeResponse mimeResponse, Models models,
		PortletRequest portletRequest) {

		_configuration = configuration;
		_mimeResponse = mimeResponse;
		_models = models;
		_portletRequest = portletRequest;
	}

	@Override
	public Configuration getConfiguration() {
		return _configuration;
	}

	@Override
	public Models getModels() {
		return _models;
	}

	@Override
	protected MimeResponse getMimeResponse() {
		return _mimeResponse;
	}

	@Override
	protected PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	private final Configuration _configuration;
	private final MimeResponse _mimeResponse;
	private final Models _models;
	private final PortletRequest _portletRequest;

}