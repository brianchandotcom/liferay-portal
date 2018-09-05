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

import com.liferay.frontend.taglib.soy.internal.util.SoyContextFactoryUtil;
import com.liferay.frontend.taglib.soy.internal.util.SoyJavaScriptRendererUtil;
import com.liferay.frontend.taglib.soy.internal.util.SoyTemplateResourcesProviderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.utils.SoyContext;
import com.liferay.taglib.aui.ScriptTag;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.io.IOException;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

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

			Locale locale = (Locale)context.get("locale");

			if (locale == null) {
				context.put("locale", LocaleUtil.getMostRelevantLocale());
			}

			String portletId = (String)context.get("portletId");

			if (Validator.isNull(portletId)) {
				context.put(
					"portletId", request.getAttribute(WebKeys.PORTLET_ID));
			}

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

	public boolean getHydrate() {
		if (_hydrate != null) {
			return _hydrate;
		}

		Map<String, Object> context = getContext();

		if (Validator.isNotNull(_componentId) || Validator.isNotNull(_module) ||
			Validator.isNotNull(context.get("data"))) {

			return true;
		}

		return false;
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
		if (context instanceof SoyContext) {
			_context = context;
		}
		else {
			_context = SoyContextFactoryUtil.createSoyContext(context);
		}
	}

	public void setDependencies(Set<String> dependencies) {
		_dependencies = dependencies;
	}

	public void setHydrate(boolean hydrate) {
		_hydrate = hydrate;
	}

	public void setModule(String module) {
		_module = module;
	}

	public void setTemplateNamespace(String namespace) {
		_templateNamespace = namespace;
	}

	public void setWrapper(boolean wrapper) {
		_wrapper = wrapper;
	}

	protected void cleanUp() {
		if (!ServerDetector.isResin()) {
			_componentId = null;
			_context = null;
			_dependencies = null;
			_hydrate = null;
			_module = null;
			_templateNamespace = null;
			_wrapper = null;
		}
	}

	protected Map<String, Object> getContext() {
		if (_context == null) {
			_context = SoyContextFactoryUtil.createSoyContext();
		}

		return _context;
	}

	protected String getElementSelector() {
		String selector = StringPool.POUND.concat(getComponentId());

		if (isWrapper()) {
			selector = selector.concat(" > *:first-child");
		}

		return selector;
	}

	protected boolean isRenderJavaScript() {
		if (getHydrate() && Validator.isNotNull(getModule())) {
			return true;
		}

		return false;
	}

	protected boolean isRenderTemplate() {
		return true;
	}

	protected boolean isWrapper() {
		if (_wrapper != null) {
			return _wrapper;
		}

		return isRenderJavaScript();
	}

	protected void prepareContext(Map<String, Object> context) {
	}

	protected void renderJavaScript(
			JspWriter jspWriter, Map<String, Object> context)
		throws Exception, IOException {

		if (!context.containsKey("element")) {
			context.put("element", getElementSelector());
		}

		Set<String> requiredModules = new LinkedHashSet<>();

		requiredModules.add(getModule());

		if (_dependencies != null) {
			requiredModules.addAll(_dependencies);
		}

		String componentJavaScript = SoyJavaScriptRendererUtil.getJavaScript(
			context, getComponentId(), requiredModules, isWrapper());

		ScriptTag.doTag(
			null, null, null, componentJavaScript, getBodyContent(),
			pageContext);
	}

	protected void renderTemplate(
			JspWriter jspWriter, Map<String, Object> context)
		throws IOException, TemplateException {

		boolean wrapper = isWrapper();

		if (!wrapper && !context.containsKey("id")) {
			context.put("id", getComponentId());
		}

		_template.putAll(context);

		_template.put(TemplateConstants.NAMESPACE, getTemplateNamespace());

		_template.prepare(request);

		if (wrapper) {
			jspWriter.append("<div id=\"");
			jspWriter.append(HtmlUtil.escapeAttribute(getComponentId()));
			jspWriter.append("\">");
		}

		_template.processTemplate(jspWriter);

		if (wrapper) {
			jspWriter.append("</div>");
		}
	}

	private Template _getTemplate() throws TemplateException {
		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			SoyTemplateResourcesProviderUtil.getAllTemplateResources(), false);
	}

	private String _componentId;
	private Map<String, Object> _context;
	private Set<String> _dependencies;
	private Boolean _hydrate;
	private String _module;
	private Template _template;
	private String _templateNamespace;
	private Boolean _wrapper;

}