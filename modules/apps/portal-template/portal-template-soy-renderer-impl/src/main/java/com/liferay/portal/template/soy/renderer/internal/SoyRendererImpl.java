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

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.template.soy.renderer.SoyRenderer;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = SoyRenderer.class)
public class SoyRendererImpl implements SoyRenderer {

	public void renderSoy(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String templateNamespace,
			Map<String, ?> context)
		throws IOException, TemplateException {

		renderSoy(
			httpServletRequest, httpServletResponse.getWriter(),
			templateNamespace, context);
	}

	public void renderSoy(
			HttpServletRequest httpServletRequest, Writer writer,
			String templateNamespace, Map<String, ?> context)
		throws TemplateException {

		Template template = _getTemplate();

		template.putAll(context);

		template.put(TemplateConstants.NAMESPACE, templateNamespace);

		template.prepare(httpServletRequest);

		template.processTemplate(writer);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addTemplateManager(TemplateManager templateManager) {
		String templateManagerName = templateManager.getName();

		if (templateManagerName.equals(TemplateConstants.LANG_TYPE_SOY)) {
			_templateManager = templateManager;
		}
	}

	protected void removeTemplateManager(TemplateManager templateManager) {
		String templateManagerName = templateManager.getName();

		if (templateManagerName.equals(TemplateConstants.LANG_TYPE_SOY)) {
			_templateManager = null;
		}
	}

	private Template _getTemplate() throws TemplateException {
		if (_templateManager == null) {
			throw new TemplateException(
				"Unable to find the Soy template manager");
		}

		return _templateManager.getTemplate(null, false);
	}

	private volatile TemplateManager _templateManager;

}