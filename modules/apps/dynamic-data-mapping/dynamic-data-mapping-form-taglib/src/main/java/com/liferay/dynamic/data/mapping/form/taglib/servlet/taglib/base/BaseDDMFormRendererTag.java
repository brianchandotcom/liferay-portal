/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.base;

import com.liferay.dynamic.data.mapping.form.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

/**
 * @author Pedro Queiroz
 * @author Rafael Praxedes
 */
public abstract class BaseDDMFormRendererTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public Long getDdmFormInstanceId() {
		return _ddmFormInstanceId;
	}

	public Long getDdmFormInstanceRecordId() {
		return _ddmFormInstanceRecordId;
	}

	public Long getDdmFormInstanceRecordVersionId() {
		return _ddmFormInstanceRecordVersionId;
	}

	public Long getDdmFormInstanceVersionId() {
		return _ddmFormInstanceVersionId;
	}

	public String getNamespace() {
		return _namespace;
	}

	public boolean getShowFormBasicInfo() {
		return _showFormBasicInfo;
	}

	public boolean getShowSubmitButton() {
		return _showSubmitButton;
	}

	public void setDdmFormInstanceId(Long ddmFormInstanceId) {
		_ddmFormInstanceId = ddmFormInstanceId;
	}

	public void setDdmFormInstanceRecordId(Long ddmFormInstanceRecordId) {
		_ddmFormInstanceRecordId = ddmFormInstanceRecordId;
	}

	public void setDdmFormInstanceRecordVersionId(
		Long ddmFormInstanceRecordVersionId) {

		_ddmFormInstanceRecordVersionId = ddmFormInstanceRecordVersionId;
	}

	public void setDdmFormInstanceVersionId(Long ddmFormInstanceVersionId) {
		_ddmFormInstanceVersionId = ddmFormInstanceVersionId;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setShowFormBasicInfo(boolean showFormBasicInfo) {
		_showFormBasicInfo = showFormBasicInfo;
	}

	public void setShowSubmitButton(boolean showSubmitButton) {
		_showSubmitButton = showSubmitButton;
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(
			httpServletRequest, "ddmFormInstanceId", _ddmFormInstanceId);
		setNamespacedAttribute(
			httpServletRequest, "ddmFormInstanceRecordId",
			_ddmFormInstanceRecordId);
		setNamespacedAttribute(
			httpServletRequest, "ddmFormInstanceRecordVersionId",
			_ddmFormInstanceRecordVersionId);
		setNamespacedAttribute(
			httpServletRequest, "ddmFormInstanceVersionId",
			_ddmFormInstanceVersionId);
		setNamespacedAttribute(httpServletRequest, "namespace", _namespace);
		setNamespacedAttribute(
			httpServletRequest, "showFormBasicInfo", _showFormBasicInfo);
		setNamespacedAttribute(
			httpServletRequest, "showSubmitButton", _showSubmitButton);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-form:ddm-form-renderer:";

	private static final String _PAGE = "/ddm_form_renderer/page.jsp";

	private Long _ddmFormInstanceId;
	private Long _ddmFormInstanceRecordId;
	private Long _ddmFormInstanceRecordVersionId;
	private Long _ddmFormInstanceVersionId;
	private String _namespace;
	private boolean _showFormBasicInfo = true;
	private boolean _showSubmitButton = true;

}