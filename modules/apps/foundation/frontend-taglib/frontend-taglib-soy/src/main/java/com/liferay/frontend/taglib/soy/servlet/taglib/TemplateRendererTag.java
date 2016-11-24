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

package com.liferay.frontend.taglib.soy.servlet.taglib;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.soy.utils.SoyContext;
import com.liferay.portal.template.soy.utils.SoyJavaScriptRenderer;
import com.liferay.portal.template.soy.utils.SoyTemplateResourcesCollector;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.io.IOException;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Bruno Basto
 */
public class TemplateRendererTag extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doEndTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();

		Map<String, Object> context = getContext();

		try {
			prepareContext(context);

			if (isRenderTemplate()) {
				renderTemplate(jspWriter, context);
			}

			if (isRenderJavaScript()) {
				renderJavaScript(jspWriter, context);
			}
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			cleanUp();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() {
		_bundle = FrameworkUtil.getBundle(getClass());

		try {
			_template = _getTemplate();
		}
		catch (TemplateException te) {
			te.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getComponentId() {
		if (Validator.isNull(_componentId)) {
			_componentId = StringUtil.randomId();
		}

		return _componentId;
	}

	public String getModule() {
		return _module;
	}

	public String getTemplateNamespace() {
		return _templateNamespace;
	}

	public void putHTMLValue(String key, String value) {
		Map<String, Object> context = getContext();

		if (context instanceof SoyContext) {
			((SoyContext)context).putHTML(key, value);
		}
		else {
			putValue(key, value);
		}
	}

	public void putValue(String key, Object value) {
		Map<String, Object> context = getContext();

		context.put(key, value);
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setContext(Map<String, Object> context) {
		_context = context;
	}

	public void setModule(String module) {
		_module = module;
	}

	public void setTemplateNamespace(String namespace) {
		_templateNamespace = namespace;
	}

	protected void cleanUp() {
		if (!ServerDetector.isResin()) {
			_componentId = null;
			_context = null;
			_module = null;
			_templateNamespace = null;
		}
	}

	protected Map<String, Object> getContext() {
		if (_context == null) {
			_context = new SoyContext();
		}

		return _context;
	}

	protected String getElementSelector() {
		return StringPool.POUND.concat(getComponentId()).concat(" > div");
	}

	protected boolean isRenderJavaScript() {
		return Validator.isNotNull(getModule());
	}

	protected boolean isRenderTemplate() {
		return true;
	}

	protected void prepareContext(Map<String, Object> context) {
	}

	protected void renderJavaScript(
			JspWriter jspWriter, Map<String, Object> context)
		throws Exception, IOException {

		SoyJavaScriptRenderer javaScriptComponentRenderer =
			_getJavaScriptComponentRenderer();

		if (!context.containsKey("element")) {
			context.put("element", getElementSelector());
		}

		String componentJavaScript = javaScriptComponentRenderer.getJavaScript(
			context, getComponentId(), SetUtil.fromString(getModule()));

		jspWriter.write(componentJavaScript);
	}

	protected void renderTemplate(
			JspWriter jspWriter, Map<String, Object> context)
		throws IOException, TemplateException {

		_template.putAll(context);

		_template.put(TemplateConstants.NAMESPACE, getTemplateNamespace());

		_template.prepare(request);

		jspWriter.append("<div id=\"");
		jspWriter.append(HtmlUtil.escapeAttribute(getComponentId()));
		jspWriter.append("\">");

		_template.processTemplate(jspWriter);

		jspWriter.append("</div>");
	}

	private SoyJavaScriptRenderer _getJavaScriptComponentRenderer()
		throws Exception {

		if (_soyJavaScriptRenderer == null) {
			_soyJavaScriptRenderer = new SoyJavaScriptRenderer();
		}

		return _soyJavaScriptRenderer;
	}

	private Template _getTemplate() throws TemplateException {
		SoyTemplateResourcesCollector soyTemplateResourcesCollector =
			new SoyTemplateResourcesCollector(_bundle, StringPool.SLASH);

		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			soyTemplateResourcesCollector.getAllTemplateResources(), false);
	}

	private Bundle _bundle;
	private String _componentId;
	private Map<String, Object> _context;
	private String _module;
	private SoyJavaScriptRenderer _soyJavaScriptRenderer;
	private Template _template;
	private String _templateNamespace;

}