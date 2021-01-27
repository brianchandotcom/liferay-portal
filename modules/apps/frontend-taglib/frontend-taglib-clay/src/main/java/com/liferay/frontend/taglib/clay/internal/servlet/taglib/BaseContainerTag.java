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

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.clay.internal.util.ServicesProvider;
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

import javax.servlet.ServletContext;
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

	public Map<String, Object> getAdditionalProps() {
		return _additionalProps;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getCssClass()}
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
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getCssClass()}
	 */
	@Deprecated
	public String getElementClasses() {
		return getCssClass();
	}

	public String getHydratedContainerElement() {
		return _hydratedContainerElement;
	}

	public String getId() {
		return _id;
	}

	public String getPropsTransformer() {
		return _propsTransformer;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		_additionalProps = additionalProps;
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

	public void setHydratedContainerElement(String hydratedContainerElement) {
		_hydratedContainerElement = hydratedContainerElement;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPropsTransformer(String propsTransformer) {
		_propsTransformer = propsTransformer;
	}

	public void setPropsTransformerServletContext(
		ServletContext propsTransformerServletContext) {

		_propsTransformerServletContext = propsTransformerServletContext;
	}

	protected void cleanUp() {
		_additionalProps = null;
		_componentId = null;
		_containerElement = null;
		_contributorKey = null;
		_cssClass = null;
		_data = null;
		_defaultEventHandler = null;
		_elementClasses = null;
		_hydratedContainerElement = "div";
		_id = null;
		_propsTransformer = null;
		_propsTransformerServletContext = null;
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

		props.put("cssClass", getCssClass());

		String defaultEventHandler = getDefaultEventHandler();

		if (Validator.isNotNull(defaultEventHandler)) {
			props.put("defaultEventHandler", defaultEventHandler);
		}

		props.put("id", getId());

		props.putAll(getDynamicAttributes());

		return props;
	}

	protected String processCssClasses(Set<String> cssClasses) {
		String cssClass = getCssClass();

		if (Validator.isNotNull(cssClass)) {
			cssClasses.addAll(StringUtil.split(cssClass, CharPool.SPACE));
		}

		return StringUtil.merge(cssClasses, StringPool.SPACE);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #prepareProps()}
	 */
	@Deprecated
	protected Map<String, Object> processData(Map<String, Object> data) {
		return data;
	}

	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</");
		jspWriter.write(_containerElement);
		jspWriter.write(">");

		String hydratedModuleName = getHydratedModuleName();

		if (hydratedModuleName != null) {
			NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

			String moduleName = npmResolver.resolveModuleName(
				hydratedModuleName);

			String propsTransformer = null;

			if (Validator.isNotNull(_propsTransformer)) {
				String resolvedPackageName;
				
				try {
					resolvedPackageName = NPMResolvedPackageNameUtil.get(
						getPropsTransformerServletContext());
				} 
				catch(UnsupportedOperationException unsupportedOperationException) {
					JSModuleResolver jsModuleResolver =
						ServicesProvider.getJSModuleResolver();

					resolvedPackageName = jsModuleResolver.resolveModule(
						getPropsTransformerServletContext(), null);
				}

				propsTransformer =
					resolvedPackageName + "/" + _propsTransformer;
			}
			else if (Validator.isNotNull(getDefaultEventHandler())) {
				propsTransformer = npmResolver.resolveModuleName(
					"frontend-taglib-clay" +
						"/DefaultEventHandlersPropsTransformer");
			}

			ComponentDescriptor componentDescriptor = new ComponentDescriptor(
				moduleName, getId(), new LinkedHashSet<>(), false,
				propsTransformer);

			ReactRenderer reactRenderer =
				ServicesProvider.getReactRenderer();

			reactRenderer.renderReact(
				componentDescriptor, prepareProps(new HashMap<>()), request,
				jspWriter);

			jspWriter.write("</");
			jspWriter.write(_hydratedContainerElement);
			jspWriter.write(">");
		}

		return EVAL_PAGE;
	}

	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		if (getHydratedModuleName() != null) {
			jspWriter.write("<");
			jspWriter.write(_hydratedContainerElement);
			jspWriter.write(">");
		}

		if (_containerElement == null) {
			setContainerElement("div");
		}

		jspWriter.write("<");
		jspWriter.write(_containerElement);

		writeCssClassAttribute();

		if (Validator.isNotNull(getId())) {
			writeIdAttribute();
		}

		writeDynamicAttributes();

		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	protected void writeCssClassAttribute() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write(" class=\"");
		jspWriter.write(processCssClasses(new LinkedHashSet<>()));
		jspWriter.write("\"");
	}

	protected void writeDynamicAttributes() throws Exception {
		String dynamicAttributesString = InlineUtil.buildDynamicAttributes(
			getDynamicAttributes());

		if (!dynamicAttributesString.isEmpty()) {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(dynamicAttributesString);
		}
	}

	protected void writeIdAttribute() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write(" id=\"");
		jspWriter.write(getId());
		jspWriter.write("\"");
	}

	private Map<String, Object> _additionalProps;
	private String _componentId;
	private String _containerElement;
	private String _contributorKey;
	private String _cssClass;
	private Map<String, String> _data;
	private String _defaultEventHandler;
	private String _elementClasses;
	private String _hydratedContainerElement = "div";
	private String _id;
	private String _propsTransformer;
	private ServletContext _propsTransformerServletContext;

}