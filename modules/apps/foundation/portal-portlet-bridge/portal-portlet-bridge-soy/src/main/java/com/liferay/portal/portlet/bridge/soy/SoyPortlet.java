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

package com.liferay.portal.portlet.bridge.soy;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.portlet.bridge.soy.internal.SoyPortletHelper;
import com.liferay.portal.template.soy.utils.SoyTemplateResourcesCollector;

import java.io.IOException;
import java.io.Writer;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Miroslav Ligas
 * @author Bruno Basto
 */
public class SoyPortlet extends MVCPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		propagateRequestParameters = GetterUtil.getBoolean(
			getInitParameter("propagate-request-parameters"), true);

		_bundle = FrameworkUtil.getBundle(getClass());

		try {
			_soyPortletHelper = new SoyPortletHelper(_bundle);

			template = _getTemplate();
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.TEMPLATE, template);

		super.render(renderRequest, renderResponse);
	}

	protected Set<String> getJavaScriptRequiredModules(String path) {
		return Collections.emptySet();
	}

	@Override
	protected void include(
			String namespace, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
		throws IOException, PortletException {

		try {
			String path = getPath(portletRequest, portletResponse);

			if (Validator.isNull(path)) {
				path = namespace;
			}

			template.put(
				TemplateConstants.NAMESPACE,
				_soyPortletHelper.getTemplateNamespace(path));

			if (propagateRequestParameters) {
				propagateRequestParameters(portletRequest);
			}

			Writer writer = null;

			if (portletResponse instanceof MimeResponse) {
				MimeResponse mimeResponse = (MimeResponse)portletResponse;

				writer = UnsyncPrintWriterPool.borrow(mimeResponse.getWriter());
			}
			else {
				writer = new UnsyncStringWriter();
			}

			populateJavaScriptTemplateContext(
				template, portletResponse.getNamespace());

			HttpServletRequest httpServletRequest =
				PortalUtil.getHttpServletRequest(portletRequest);

			template.prepare(httpServletRequest);

			template.processTemplate(writer);

			String portletJavaScript = _soyPortletHelper.getPortletJavaScript(
				template, path, portletResponse.getNamespace(),
				getJavaScriptRequiredModules(path));

			writer.write(portletJavaScript);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		if (clearRequestParameters) {
			if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponse.setProperty("clear-request-parameters", "true");
			}
		}
	}

	protected void populateJavaScriptTemplateContext(
		Template template, String portletNamespace) {

		String portletComponentId = portletNamespace.concat("PortletComponent");

		template.put("element", StringPool.POUND.concat(portletComponentId));
		template.put("id", portletComponentId);
	}

	protected void propagateRequestParameters(PortletRequest portletRequest) {
		Map<String, String[]> parametersMap = portletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
			String parameterName = entry.getKey();
			String[] parameterValues = entry.getValue();

			if (parameterValues.length == 1) {
				template.put(parameterName, parameterValues[0]);
			}
			else if (parameterValues.length > 1) {
				template.put(parameterName, parameterValues);
			}
		}
	}

	protected boolean propagateRequestParameters;
	protected Template template;

	private Template _getTemplate() throws TemplateException {
		SoyTemplateResourcesCollector soyTemplateResourcesCollector =
			new SoyTemplateResourcesCollector(_bundle, templatePath);

		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			soyTemplateResourcesCollector.getTemplateResources(), false);
	}

	private Bundle _bundle;
	private SoyPortletHelper _soyPortletHelper;

}