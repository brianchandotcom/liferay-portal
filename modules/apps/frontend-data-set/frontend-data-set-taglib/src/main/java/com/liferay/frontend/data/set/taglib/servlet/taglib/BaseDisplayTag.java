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

package com.liferay.frontend.data.set.taglib.servlet.taglib;

import com.liferay.frontend.data.set.taglib.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.data.set.taglib.internal.util.ServicesProvider;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.taglib.util.AttributesTagSupport;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Marko Cikos
 */
public class BaseDisplayTag extends AttributesTagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			return processEndTag();
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
		finally {
			doClearTag();
		}
	}

	public Map<String, Object> getAdditionalProps() {
		return _additionalProps;
	}

	public String getId() {
		return _id;
	}

	public String getPropsTransformer() {
		return _propsTransformer;
	}

	public String getRandomNamespace() {
		return _randomNamespace;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		_additionalProps = additionalProps;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setPropsTransformer(String propsTransformer) {
		_propsTransformer = propsTransformer;
	}

	public void setPropsTransformerServletContext(
		ServletContext propsTransformerServletContext) {

		_propsTransformerServletContext = propsTransformerServletContext;
	}

	public void setRandomNamespace(String randomNamespace) {
		_randomNamespace = randomNamespace;
	}

	protected void cleanUp() {
		_additionalProps = null;
		_id = null;
		_propsTransformer = null;
		_propsTransformerServletContext = null;
		_randomNamespace = null;
	}

	protected void doClearTag() {
		clearDynamicAttributes();
		clearParams();
		clearProperties();

		cleanUp();
	}

	protected ServletContext getPropsTransformerServletContext() {
		if (_propsTransformerServletContext != null) {
			return _propsTransformerServletContext;
		}

		return pageContext.getServletContext();
	}

	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		if (_additionalProps != null) {
			props.put("additionalProps", _additionalProps);
		}

		return props;
	}

	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"table-root\" id=\"");
		jspWriter.write(getRandomNamespace());
		jspWriter.write("table-id\"><span aria-hidden=\"true\" class=\"");
		jspWriter.write("loading-animation my-7\"></span>");

		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		String moduleName = npmResolver.resolveModuleName(
			"@liferay/frontend-data-set-web/FrontendDataSet");

		String propsTransformer = null;

		if (Validator.isNotNull(_propsTransformer)) {
			String resolvedPackageName;

			try {
				resolvedPackageName = NPMResolvedPackageNameUtil.get(
					getPropsTransformerServletContext());
			}
			catch (UnsupportedOperationException
						unsupportedOperationException) {

				JSModuleResolver jsModuleResolver =
					ServicesProvider.getJSModuleResolver();

				resolvedPackageName = jsModuleResolver.resolveModule(
					getPropsTransformerServletContext(), null);
			}

			propsTransformer = resolvedPackageName + "/" + _propsTransformer;
		}

		ComponentDescriptor componentDescriptor = new ComponentDescriptor(
			moduleName, getId(), new LinkedHashSet<>(), false,
			propsTransformer);

		ReactRenderer reactRenderer = ServicesProvider.getReactRenderer();

		reactRenderer.renderReact(
			componentDescriptor, prepareProps(new HashMap<>()), getRequest(),
			jspWriter);

		jspWriter.write("</div>");

		return EVAL_PAGE;
	}

	protected void setAttributes(HttpServletRequest httpServletRequest) {
	}

	private Map<String, Object> _additionalProps;
	private String _id;
	private String _propsTransformer;
	private ServletContext _propsTransformerServletContext;
	private String _randomNamespace;

}