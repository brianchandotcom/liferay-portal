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

package com.liferay.frontend.taglib.soy.internal.util;

import com.liferay.frontend.taglib.soy.servlet.taglib.util.ComponentDescriptor;
import com.liferay.frontend.taglib.soy.servlet.taglib.util.SoyComponentRenderer;
import com.liferay.frontend.taglib.soy.servlet.taglib.util.SoyRenderer;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = SoyComponentRenderer.class)
public class SoyComponentRendererImpl implements SoyComponentRenderer {

	public void renderSoyComponent(
			HttpServletRequest request, HttpServletResponse response,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		renderSoyComponent(
			request, response.getWriter(), componentDescriptor, context);
	}

	public void renderSoyComponent(
			HttpServletRequest request, Writer writer,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		SoyComponentRendererHelper soyComponentRendererHelper =
			new SoyComponentRendererHelper(
				request, componentDescriptor, context, _portal, _soyRenderer);

		soyComponentRendererHelper.renderSoyComponent(writer);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SoyRenderer _soyRenderer;

}