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

package com.liferay.portal.template.soy.renderer.internal;

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.template.soy.renderer.SoyRenderer;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = SoyRenderer.class)
public class SoyRendererImpl implements SoyRenderer {

	@Override
	public void renderSoy(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String templateNamespace,
			Map<String, ?> context)
		throws IOException, TemplateException {

		renderSoy(
			httpServletRequest, httpServletResponse.getWriter(),
			templateNamespace, context);
	}

	@Override
	public void renderSoy(
			HttpServletRequest httpServletRequest, Writer writer,
			String templateNamespace, Map<String, ?> context)
		throws TemplateException {

		_logger.warning(
			"Server-side rendering support for Soy templates is no longer " +
				"available");
	}

	private static final Logger _logger = Logger.getLogger(
		SoyRendererImpl.class.getName());

}