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

package com.liferay.data.engine.taglib.servlet.taglib.base;

import com.liferay.data.engine.taglib.internal.servlet.ServletContextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
public abstract class BaseLayoutBuilderTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getDataDefinitionInputId() {
		return _dataDefinitionInputId;
	}

	public java.lang.Long getDataLayoutId() {
		return _dataLayoutId;
	}

	public java.lang.String getDataLayoutInputId() {
		return _dataLayoutInputId;
	}

	public java.lang.String getNamespace() {
		return _namespace;
	}

	public void setDataDefinitionInputId(java.lang.String dataDefinitionInputId) {
		_dataDefinitionInputId = dataDefinitionInputId;
	}

	public void setDataLayoutId(java.lang.Long dataLayoutId) {
		_dataLayoutId = dataLayoutId;
	}

	public void setDataLayoutInputId(java.lang.String dataLayoutInputId) {
		_dataLayoutInputId = dataLayoutInputId;
	}

	public void setNamespace(java.lang.String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_dataDefinitionInputId = null;
		_dataLayoutId = null;
		_dataLayoutInputId = null;
		_namespace = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "dataDefinitionInputId", _dataDefinitionInputId);
		setNamespacedAttribute(request, "dataLayoutId", _dataLayoutId);
		setNamespacedAttribute(request, "dataLayoutInputId", _dataLayoutInputId);
		setNamespacedAttribute(request, "namespace", _namespace);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "liferay-data-engine:layout-builder:";

	private static final String _END_PAGE =
		"/layout_builder/end.jsp";

	private static final String _START_PAGE =
		"/layout_builder/start.jsp";

	private java.lang.String _dataDefinitionInputId = null;
	private java.lang.Long _dataLayoutId = null;
	private java.lang.String _dataLayoutInputId = null;
	private java.lang.String _namespace = null;

}