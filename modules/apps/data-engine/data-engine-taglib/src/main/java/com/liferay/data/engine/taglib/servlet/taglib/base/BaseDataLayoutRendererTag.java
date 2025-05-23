/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.taglib.servlet.taglib.base;

import com.liferay.data.engine.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

import java.util.Map;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public abstract class BaseDataLayoutRendererTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getContainerId() {
		return _containerId;
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

	public Long getDataRecordId() {
		return _dataRecordId;
	}

	public Map<String, Object> getDataRecordValues() {
		return _dataRecordValues;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public boolean getDisableFieldRepetition() {
		return _disableFieldRepetition;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public String getNamespace() {
		return _namespace;
	}

	public boolean getPersistDefaultValues() {
		return _persistDefaultValues;
	}

	public boolean getPersisted() {
		return _persisted;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean getSubmittable() {
		return _submittable;
	}

	public void setContainerId(String containerId) {
		_containerId = containerId;
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

	public void setDataRecordId(Long dataRecordId) {
		_dataRecordId = dataRecordId;
	}

	public void setDataRecordValues(Map<String, Object> dataRecordValues) {
		_dataRecordValues = dataRecordValues;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setDisableFieldRepetition(boolean disableFieldRepetition) {
		_disableFieldRepetition = disableFieldRepetition;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setPersistDefaultValues(boolean persistDefaultValues) {
		_persistDefaultValues = persistDefaultValues;
	}

	public void setPersisted(boolean persisted) {
		_persisted = persisted;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setSubmittable(boolean submittable) {
		_submittable = submittable;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_containerId = null;
		_contentType = null;
		_dataDefinitionId = null;
		_dataLayoutId = null;
		_dataRecordId = null;
		_dataRecordValues = null;
		_defaultLanguageId = null;
		_disableFieldRepetition = false;
		_displayType = null;
		_languageId = null;
		_namespace = null;
		_persistDefaultValues = false;
		_persisted = false;
		_readOnly = false;
		_submittable = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(httpServletRequest, "containerId", _containerId);
		setNamespacedAttribute(httpServletRequest, "contentType", _contentType);
		setNamespacedAttribute(
			httpServletRequest, "dataDefinitionId", _dataDefinitionId);
		setNamespacedAttribute(
			httpServletRequest, "dataLayoutId", _dataLayoutId);
		setNamespacedAttribute(
			httpServletRequest, "dataRecordId", _dataRecordId);
		setNamespacedAttribute(
			httpServletRequest, "dataRecordValues", _dataRecordValues);
		setNamespacedAttribute(
			httpServletRequest, "defaultLanguageId", _defaultLanguageId);
		setNamespacedAttribute(
			httpServletRequest, "disableFieldRepetition",
			_disableFieldRepetition);
		setNamespacedAttribute(httpServletRequest, "displayType", _displayType);
		setNamespacedAttribute(httpServletRequest, "languageId", _languageId);
		setNamespacedAttribute(httpServletRequest, "namespace", _namespace);
		setNamespacedAttribute(
			httpServletRequest, "persistDefaultValues", _persistDefaultValues);
		setNamespacedAttribute(httpServletRequest, "persisted", _persisted);
		setNamespacedAttribute(httpServletRequest, "readOnly", _readOnly);
		setNamespacedAttribute(httpServletRequest, "submittable", _submittable);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-data-engine:data-layout-renderer:";

	private static final String _PAGE = "/data_layout_renderer/page.jsp";

	private String _containerId;
	private String _contentType;
	private Long _dataDefinitionId;
	private Long _dataLayoutId;
	private Long _dataRecordId;
	private Map<String, Object> _dataRecordValues;
	private String _defaultLanguageId;
	private boolean _disableFieldRepetition;
	private String _displayType;
	private String _languageId;
	private String _namespace;
	private boolean _persistDefaultValues;
	private boolean _persisted;
	private boolean _readOnly;
	private boolean _submittable = true;

}