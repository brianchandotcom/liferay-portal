/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.taglib.servlet.taglib.base;

import com.liferay.data.engine.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public abstract class BaseDataLayoutBuilderTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public List<Map<String, Object>> getAdditionalPanels() {
		return _additionalPanels;
	}

	public String getComponentId() {
		return _componentId;
	}

	public String getContentType() {
		return _contentType;
	}

	public Long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public Long getDataLayoutId() {
		return _dataLayoutId;
	}

	public boolean getDisplayFieldName() {
		return _displayFieldName;
	}

	public String getFieldSetContentType() {
		return _fieldSetContentType;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public boolean getLocalizable() {
		return _localizable;
	}

	public ServletContext getModuleServletContext() {
		return _moduleServletContext;
	}

	public String getNamespace() {
		return _namespace;
	}

	public Set<String> getScopes() {
		return _scopes;
	}

	public boolean getSearchableFieldsDisabled() {
		return _searchableFieldsDisabled;
	}

	public String getSubmitButtonId() {
		return _submitButtonId;
	}

	public void setAdditionalPanels(
		List<Map<String, Object>> additionalPanels) {

		_additionalPanels = additionalPanels;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setContentType(String contentType) {
		_contentType = contentType;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDataLayoutId(Long dataLayoutId) {
		_dataLayoutId = dataLayoutId;
	}

	public void setDisplayFieldName(boolean displayFieldName) {
		_displayFieldName = displayFieldName;
	}

	public void setFieldSetContentType(String fieldSetContentType) {
		_fieldSetContentType = fieldSetContentType;
	}

	public void setGroupId(Long groupId) {
		_groupId = groupId;
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	public void setModuleServletContext(ServletContext moduleServletContext) {
		_moduleServletContext = moduleServletContext;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setScopes(Set<String> scopes) {
		_scopes = scopes;
	}

	public void setSearchableFieldsDisabled(boolean searchableFieldsDisabled) {
		_searchableFieldsDisabled = searchableFieldsDisabled;
	}

	public void setSubmitButtonId(String submitButtonId) {
		_submitButtonId = submitButtonId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_additionalPanels = null;
		_componentId = null;
		_contentType = null;
		_dataDefinitionId = null;
		_dataLayoutId = null;
		_displayFieldName = false;
		_fieldSetContentType = null;
		_groupId = null;
		_localizable = false;
		_moduleServletContext = null;
		_namespace = null;
		_scopes = null;
		_searchableFieldsDisabled = false;
		_submitButtonId = null;
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
			httpServletRequest, "additionalPanels", _additionalPanels);
		setNamespacedAttribute(httpServletRequest, "componentId", _componentId);
		setNamespacedAttribute(httpServletRequest, "contentType", _contentType);
		setNamespacedAttribute(
			httpServletRequest, "dataDefinitionId", _dataDefinitionId);
		setNamespacedAttribute(
			httpServletRequest, "dataLayoutId", _dataLayoutId);
		setNamespacedAttribute(
			httpServletRequest, "displayFieldName", _displayFieldName);
		setNamespacedAttribute(
			httpServletRequest, "fieldSetContentType", _fieldSetContentType);
		setNamespacedAttribute(httpServletRequest, "groupId", _groupId);
		setNamespacedAttribute(httpServletRequest, "localizable", _localizable);
		setNamespacedAttribute(
			httpServletRequest, "moduleServletContext", _moduleServletContext);
		setNamespacedAttribute(httpServletRequest, "namespace", _namespace);
		setNamespacedAttribute(httpServletRequest, "scopes", _scopes);
		setNamespacedAttribute(
			httpServletRequest, "searchableFieldsDisabled",
			_searchableFieldsDisabled);
		setNamespacedAttribute(
			httpServletRequest, "submitButtonId", _submitButtonId);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-data-engine:data-layout-builder:";

	private static final String _END_PAGE = "/data_layout_builder/end.jsp";

	private static final String _START_PAGE = "/data_layout_builder/start.jsp";

	private List<Map<String, Object>> _additionalPanels;
	private String _componentId;
	private String _contentType;
	private Long _dataDefinitionId;
	private Long _dataLayoutId;
	private boolean _displayFieldName;
	private String _fieldSetContentType;
	private Long _groupId;
	private boolean _localizable;
	private ServletContext _moduleServletContext;
	private String _namespace;
	private Set<String> _scopes;
	private boolean _searchableFieldsDisabled;
	private String _submitButtonId;

}