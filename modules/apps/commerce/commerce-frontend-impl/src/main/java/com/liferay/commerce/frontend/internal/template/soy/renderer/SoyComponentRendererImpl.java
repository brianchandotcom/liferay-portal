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

package com.liferay.commerce.frontend.internal.template.soy.renderer;

import com.liferay.commerce.frontend.template.soy.renderer.ComponentDescriptor;
import com.liferay.commerce.frontend.template.soy.renderer.SoyComponentRenderer;
import com.liferay.commerce.frontend.template.soy.renderer.SoyRenderer;
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		renderSoyComponent(
			httpServletRequest, httpServletResponse.getWriter(),
			componentDescriptor, context);
	}

	public void renderSoyComponent(
			HttpServletRequest httpServletRequest, Writer writer,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		SoyComponentRendererHelper soyComponentRendererHelper =
			new SoyComponentRendererHelper(
				httpServletRequest, componentDescriptor, context, _portal,
				_soyRenderer);

		soyComponentRendererHelper.renderSoyComponent(writer);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SoyRenderer _soyRenderer;

}