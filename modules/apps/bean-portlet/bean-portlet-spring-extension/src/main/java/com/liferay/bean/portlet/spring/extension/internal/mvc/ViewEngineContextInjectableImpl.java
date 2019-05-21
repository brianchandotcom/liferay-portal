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

import javax.annotation.ManagedBean;

import javax.mvc.Models;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.ws.rs.core.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author  Neil Griffin
 */
@ManagedBean
@Scope(proxyMode = ScopedProxyMode.INTERFACES, value = "portletRedirect")
public class ViewEngineContextInjectableImpl extends BaseViewEngineContext {

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

	@Autowired
	private Configuration _configuration;

	@Autowired
	private MimeResponse _mimeResponse;

	@Autowired
	private Models _models;

	@Autowired
	private PortletRequest _portletRequest;

}