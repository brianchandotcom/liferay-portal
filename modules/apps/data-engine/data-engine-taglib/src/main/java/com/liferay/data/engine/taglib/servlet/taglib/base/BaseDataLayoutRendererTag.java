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
public abstract class BaseDataLayoutRendererTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getContainerId() {
		return _containerId;
	}

	public java.lang.Long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public java.lang.Long getDataLayoutId() {
		return _dataLayoutId;
	}

	public java.lang.Long getDataRecordId() {
		return _dataRecordId;
	}

	public java.util.Map<java.lang.String, java.lang.Object> getDataRecordValues() {
		return _dataRecordValues;
	}

	public java.lang.String getNamespace() {
		return _namespace;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public void setContainerId(java.lang.String containerId) {
		_containerId = containerId;
	}

	public void setDataDefinitionId(java.lang.Long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDataLayoutId(java.lang.Long dataLayoutId) {
		_dataLayoutId = dataLayoutId;
	}

	public void setDataRecordId(java.lang.Long dataRecordId) {
		_dataRecordId = dataRecordId;
	}

	public void setDataRecordValues(java.util.Map<java.lang.String, java.lang.Object> dataRecordValues) {
		_dataRecordValues = dataRecordValues;
	}

	public void setNamespace(java.lang.String namespace) {
		_namespace = namespace;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_containerId = null;
		_dataDefinitionId = null;
		_dataLayoutId = null;
		_dataRecordId = null;
		_dataRecordValues = null;
		_namespace = null;
		_readOnly = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "containerId", _containerId);
		setNamespacedAttribute(request, "dataDefinitionId", _dataDefinitionId);
		setNamespacedAttribute(request, "dataLayoutId", _dataLayoutId);
		setNamespacedAttribute(request, "dataRecordId", _dataRecordId);
		setNamespacedAttribute(request, "dataRecordValues", _dataRecordValues);
		setNamespacedAttribute(request, "namespace", _namespace);
		setNamespacedAttribute(request, "readOnly", _readOnly);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "liferay-data-engine:data-layout-renderer:";

	private static final String _PAGE =
		"/data_layout_renderer/page.jsp";

	private java.lang.String _containerId = null;
	private java.lang.Long _dataDefinitionId = null;
	private java.lang.Long _dataLayoutId = null;
	private java.lang.Long _dataRecordId = null;
	private java.util.Map<java.lang.String, java.lang.Object> _dataRecordValues = null;
	private java.lang.String _namespace = null;
	private boolean _readOnly = false;

}