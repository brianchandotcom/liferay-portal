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

package com.liferay.frontend.taglib.clay.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.clay.internal.util.ReactRendererProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.taglib.util.AttributesTagSupport;
import com.liferay.taglib.util.InlineUtil;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 */
public class BaseContainerTag extends AttributesTagSupport {

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

	@Override
	public int doStartTag() throws JspException {
		try {
			return processStartTag();
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getCssClass()}
	 */
	@Deprecated
	public String getClassName() {
		return getCssClass();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getComponentId() {
		return _componentId;
	}

	public String getContainerElement() {
		return _containerElement;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getContributorKey() {
		return _contributorKey;
	}

	public String getCssClass() {
		return _cssClass;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Map<String, String> getData() {
		return _data;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getDefaultEventHandler() {
		return _defaultEventHandler;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getCssClass()}
	 */
	@Deprecated
	public String getElementClasses() {
		return getCssClass();
	}

	public String getId() {
		return _id;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setCssClass(String)}
	 */
	@Deprecated
	public void setClassName(String className) {
		setCssClass(className);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setContainerElement(String containerElement) {
		_containerElement = containerElement;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setContributorKey(String contributorKey) {
		_contributorKey = contributorKey;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setData(Map<String, String> data) {
		_data = data;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setDefaultEventHandler(String defaultEventHandler) {
		_defaultEventHandler = defaultEventHandler;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setCssClass(String)}
	 */
	@Deprecated
	public void setElementClasses(String elementClasses) {
		setCssClass(elementClasses);
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	protected void cleanUp() {
		_componentId = null;
		_containerElement = null;
		_contributorKey = null;
		_cssClass = null;
		_data = null;
		_defaultEventHandler = null;
		_elementClasses = null;
		_id = null;
	}

	protected void doClearTag() {
		clearDynamicAttributes();
		clearParams();
		clearProperties();

		cleanUp();
	}

	protected String getHydratedModuleName() {
		return null;
	}

	protected String processCssClasses(Set<String> cssClasses) {
		if (Validator.isNotNull(_cssClass)) {
			cssClasses.addAll(StringUtil.split(_cssClass, CharPool.SPACE));
		}

		return StringUtil.merge(cssClasses, StringPool.SPACE);
	}

	protected Map<String, Object> processData(Map<String, Object> data) {
		data.put("cssClass", _cssClass);
		data.put("id", _id);

		return data;
	}

	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		String hydratedModuleName = getHydratedModuleName();

		if (Validator.isNotNull(hydratedModuleName)) {
			NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

			String moduleName = npmResolver.resolveModuleName(
				hydratedModuleName);

			ComponentDescriptor componentDescriptor = new ComponentDescriptor(
				moduleName, _id, new LinkedHashSet<>(), false);

			ReactRenderer reactRenderer =
				ReactRendererProvider.getReactRenderer();

			reactRenderer.renderReact(
				componentDescriptor, processData(new HashMap<>()), request,
				jspWriter);
		}

		jspWriter.write("</");
		jspWriter.write(_containerElement);
		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		if (_containerElement == null) {
			setContainerElement("div");
		}

		jspWriter.write("<");
		jspWriter.write(_containerElement);
		jspWriter.write(" class=\"");
		jspWriter.write(processCssClasses(new LinkedHashSet<>()));
		jspWriter.write("\"");

		if (Validator.isNotNull(_id)) {
			jspWriter.write(" id=\"");
			jspWriter.write(_id);
			jspWriter.write("\"");
		}

		_writeDynamicAttributes(jspWriter);

		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	private void _writeDynamicAttributes(JspWriter jspWriter) throws Exception {
		String dynamicAttributesString = InlineUtil.buildDynamicAttributes(
			getDynamicAttributes());

		if (!dynamicAttributesString.isEmpty()) {
			jspWriter.write(dynamicAttributesString);
		}
	}

	private String _componentId;
	private String _containerElement;
	private String _contributorKey;
	private String _cssClass;
	private Map<String, String> _data;
	private String _defaultEventHandler;
	private String _elementClasses;
	private String _id;

}