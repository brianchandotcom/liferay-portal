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

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.base;

import com.liferay.dynamic.data.mapping.form.taglib.internal.servlet.ServletContextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Pedro Queiroz
 * @author Rafael Praxedes
 * @generated
 */
public abstract class BaseDDMFormRendererTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.Long getDdmFormInstanceId() {
		return _ddmFormInstanceId;
	}

	public java.lang.Long getDdmFormInstanceRecordId() {
		return _ddmFormInstanceRecordId;
	}

	public java.lang.Long getDdmFormInstanceRecordVersionId() {
		return _ddmFormInstanceRecordVersionId;
	}

	public java.lang.Long getDdmFormInstanceVersionId() {
		return _ddmFormInstanceVersionId;
	}

	public java.lang.String getNamespace() {
		return _namespace;
	}

	public boolean getShowFormBasicInfo() {
		return _showFormBasicInfo;
	}

	public boolean getShowSubmitButton() {
		return _showSubmitButton;
	}

	public void setDdmFormInstanceId(java.lang.Long ddmFormInstanceId) {
		_ddmFormInstanceId = ddmFormInstanceId;
	}

	public void setDdmFormInstanceRecordId(java.lang.Long ddmFormInstanceRecordId) {
		_ddmFormInstanceRecordId = ddmFormInstanceRecordId;
	}

	public void setDdmFormInstanceRecordVersionId(java.lang.Long ddmFormInstanceRecordVersionId) {
		_ddmFormInstanceRecordVersionId = ddmFormInstanceRecordVersionId;
	}

	public void setDdmFormInstanceVersionId(java.lang.Long ddmFormInstanceVersionId) {
		_ddmFormInstanceVersionId = ddmFormInstanceVersionId;
	}

	public void setNamespace(java.lang.String namespace) {
		_namespace = namespace;
	}

	public void setShowFormBasicInfo(boolean showFormBasicInfo) {
		_showFormBasicInfo = showFormBasicInfo;
	}

	public void setShowSubmitButton(boolean showSubmitButton) {
		_showSubmitButton = showSubmitButton;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_ddmFormInstanceId = null;
		_ddmFormInstanceRecordId = null;
		_ddmFormInstanceRecordVersionId = null;
		_ddmFormInstanceVersionId = null;
		_namespace = null;
		_showFormBasicInfo = true;
		_showSubmitButton = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "ddmFormInstanceId", _ddmFormInstanceId);
		setNamespacedAttribute(request, "ddmFormInstanceRecordId", _ddmFormInstanceRecordId);
		setNamespacedAttribute(request, "ddmFormInstanceRecordVersionId", _ddmFormInstanceRecordVersionId);
		setNamespacedAttribute(request, "ddmFormInstanceVersionId", _ddmFormInstanceVersionId);
		setNamespacedAttribute(request, "namespace", _namespace);
		setNamespacedAttribute(request, "showFormBasicInfo", _showFormBasicInfo);
		setNamespacedAttribute(request, "showSubmitButton", _showSubmitButton);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "liferay-form:ddm-form-renderer:";

	private static final String _PAGE =
		"/ddm_form_renderer/page.jsp";

	private java.lang.Long _ddmFormInstanceId = null;
	private java.lang.Long _ddmFormInstanceRecordId = null;
	private java.lang.Long _ddmFormInstanceRecordVersionId = null;
	private java.lang.Long _ddmFormInstanceVersionId = null;
	private java.lang.String _namespace = null;
	private boolean _showFormBasicInfo = true;
	private boolean _showSubmitButton = true;

}