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

import com.liferay.frontend.taglib.soy.servlet.taglib.util.SoyRenderer;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.template.soy.utils.SoyTemplateResourcesProvider;

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
			HttpServletRequest request, HttpServletResponse response,
			String templateNamespace, Map<String, ?> context)
		throws IOException, TemplateException {

		renderSoy(request, response.getWriter(), templateNamespace, context);
	}

	public void renderSoy(
			HttpServletRequest request, Writer writer, String templateNamespace,
			Map<String, ?> context)
		throws TemplateException {

		Template template = _getTemplate();

		template.putAll(context);

		template.put(TemplateConstants.NAMESPACE, templateNamespace);

		template.prepare(request);

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
				"Unable to find the '" + TemplateConstants.LANG_TYPE_SOY +
					"' template manager");
		}

		return _templateManager.getTemplate(
			_soyTemplateResourcesProvider.getAllTemplateResources(), false);
	}

	@Reference
	private SoyTemplateResourcesProvider _soyTemplateResourcesProvider;

	private volatile TemplateManager _templateManager;

}