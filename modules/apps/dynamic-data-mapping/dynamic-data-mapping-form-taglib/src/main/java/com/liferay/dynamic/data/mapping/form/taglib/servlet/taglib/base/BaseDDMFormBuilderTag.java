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
public abstract class BaseDDMFormBuilderTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public Long getDdmStructureId() {
		return _ddmStructureId;
	}

	public Long getDdmStructureVersionId() {
		return _ddmStructureVersionId;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public String getEditingLanguageId() {
		return _editingLanguageId;
	}

	public long getFieldSetClassNameId() {
		return _fieldSetClassNameId;
	}

	public String getRefererPortletNamespace() {
		return _refererPortletNamespace;
	}

	public boolean getShowPagination() {
		return _showPagination;
	}

	public void setDdmStructureId(Long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	public void setDdmStructureVersionId(Long ddmStructureVersionId) {
		_ddmStructureVersionId = ddmStructureVersionId;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setEditingLanguageId(String editingLanguageId) {
		_editingLanguageId = editingLanguageId;
	}

	public void setFieldSetClassNameId(long fieldSetClassNameId) {
		_fieldSetClassNameId = fieldSetClassNameId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setRefererPortletNamespace(String refererPortletNamespace) {
		_refererPortletNamespace = refererPortletNamespace;
	}

	public void setShowPagination(boolean showPagination) {
		_showPagination = showPagination;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_ddmStructureId = null;
		_ddmStructureVersionId = null;
		_defaultLanguageId = null;
		_editingLanguageId = null;
		_fieldSetClassNameId = 0;
		_refererPortletNamespace = null;
		_showPagination = true;
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(
			httpServletRequest, "ddmStructureId", _ddmStructureId);
		setNamespacedAttribute(
			httpServletRequest, "ddmStructureVersionId",
			_ddmStructureVersionId);
		setNamespacedAttribute(
			httpServletRequest, "defaultLanguageId", _defaultLanguageId);
		setNamespacedAttribute(
			httpServletRequest, "editingLanguageId", _editingLanguageId);
		setNamespacedAttribute(
			httpServletRequest, "fieldSetClassNameId", _fieldSetClassNameId);
		setNamespacedAttribute(
			httpServletRequest, "refererPortletNamespace",
			_refererPortletNamespace);
		setNamespacedAttribute(
			httpServletRequest, "showPagination", _showPagination);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-form:ddm-form-builder:";

	private static final String _END_PAGE = "/ddm_form_builder/end.jsp";

	private static final String _START_PAGE = "/ddm_form_builder/start.jsp";

	private Long _ddmStructureId;
	private Long _ddmStructureVersionId;
	private String _defaultLanguageId;
	private String _editingLanguageId;
	private long _fieldSetClassNameId;
	private String _refererPortletNamespace;
	private boolean _showPagination = true;

}