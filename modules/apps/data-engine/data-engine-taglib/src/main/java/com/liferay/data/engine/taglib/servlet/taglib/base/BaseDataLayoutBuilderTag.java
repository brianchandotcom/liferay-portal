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
 * @author Leonardo Barros
 * @generated
 */
public abstract class BaseDataLayoutBuilderTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getComponentId() {
		return _componentId;
	}

	public java.lang.String getContentType() {
		return _contentType;
	}

	public java.lang.Long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public java.lang.Long getDataLayoutId() {
		return _dataLayoutId;
	}

	public java.lang.Long getGroupId() {
		return _groupId;
	}

	public boolean getLocalizable() {
		return _localizable;
	}

	public java.lang.String getNamespace() {
		return _namespace;
	}

	public java.util.Set getScopes() {
		return _scopes;
	}

	public void setComponentId(java.lang.String componentId) {
		_componentId = componentId;
	}

	public void setContentType(java.lang.String contentType) {
		_contentType = contentType;
	}

	public void setDataDefinitionId(java.lang.Long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDataLayoutId(java.lang.Long dataLayoutId) {
		_dataLayoutId = dataLayoutId;
	}

	public void setGroupId(java.lang.Long groupId) {
		_groupId = groupId;
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	public void setNamespace(java.lang.String namespace) {
		_namespace = namespace;
	}

	public void setScopes(java.util.Set scopes) {
		_scopes = scopes;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_componentId = null;
		_contentType = null;
		_dataDefinitionId = null;
		_dataLayoutId = null;
		_groupId = null;
		_localizable = false;
		_namespace = null;
		_scopes = null;
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
		setNamespacedAttribute(request, "componentId", _componentId);
		setNamespacedAttribute(request, "contentType", _contentType);
		setNamespacedAttribute(request, "dataDefinitionId", _dataDefinitionId);
		setNamespacedAttribute(request, "dataLayoutId", _dataLayoutId);
		setNamespacedAttribute(request, "groupId", _groupId);
		setNamespacedAttribute(request, "localizable", _localizable);
		setNamespacedAttribute(request, "namespace", _namespace);
		setNamespacedAttribute(request, "scopes", _scopes);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "liferay-data-engine:data-layout-builder:";

	private static final String _END_PAGE =
		"/data_layout_builder/end.jsp";

	private static final String _START_PAGE =
		"/data_layout_builder/start.jsp";

	private java.lang.String _componentId = null;
	private java.lang.String _contentType = null;
	private java.lang.Long _dataDefinitionId = null;
	private java.lang.Long _dataLayoutId = null;
	private java.lang.Long _groupId = null;
	private boolean _localizable = false;
	private java.lang.String _namespace = null;
	private java.util.Set _scopes = null;

}