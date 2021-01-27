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

package com.liferay.frontend.taglib.react.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.frontend.taglib.react.internal.util.ServicesProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class ComponentTag extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doEndTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();

		Map<String, Object> props = getProps();

		try {
			prepareProps(props);

			ComponentDescriptor componentDescriptor = new ComponentDescriptor(
				getModule(), getComponentId(), null, isPositionInLine());

			ReactRenderer reactRenderer = ServicesProvider.getReactRenderer();

			reactRenderer.renderReact(
				componentDescriptor, props, request, jspWriter);
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
		finally {
			cleanUp();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getComponentId() {
		return _componentId;
	}

	public String getModule() {
		return StringBundler.concat(getNamespace(), "/", _module);
	}

	@Override
	public void release() {
		super.release();

		_setServletContext = false;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #setProps(Map)}
	 */
	@Deprecated
	public void setData(Map<String, Object> data) {
		setProps(data);
	}

	public void setModule(String module) {
		_module = module;
	}

	public void setProps(Map<String, Object> props) {
		_props = props;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);

		_setServletContext = true;
	}

	protected void cleanUp() {
		_componentId = null;
		_module = null;
		_props = Collections.emptyMap();
		_setServletContext = false;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getProps()}
	 */
	@Deprecated
	protected Map<String, Object> getData() {
		return getProps();
	}

	protected String getNamespace() {
		ServletContext servletContext = pageContext.getServletContext();

		if (_setServletContext) {
			servletContext = this.servletContext;
		}

		try {
			return NPMResolvedPackageNameUtil.get(servletContext);
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			JSModuleResolver jsModuleResolver =
				ServicesProvider.getJSModuleResolver();

			return jsModuleResolver.resolveModule(servletContext, null);
		}
	}

	protected Map<String, Object> getProps() {
		return _props;
	}

	protected boolean isPositionInLine() {
		String fragmentId = ParamUtil.getString(request, "p_f_id");

		if (Validator.isNotNull(fragmentId)) {
			return true;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isIsolated() || themeDisplay.isLifecycleResource() ||
			themeDisplay.isStateExclusive()) {

			return true;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		if (Validator.isNotNull(portletId) &&
			themeDisplay.isPortletEmbedded(
				themeDisplay.getScopeGroupId(), themeDisplay.getLayout(),
				portletId)) {

			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #prepareData(Map)}
	 */
	@Deprecated
	protected void prepareData(Map<String, Object> data) {
		prepareProps(data);
	}

	protected void prepareProps(Map<String, Object> props) {
	}

	private String _componentId;
	private String _module;
	private Map<String, Object> _props = Collections.emptyMap();
	private boolean _setServletContext;

}