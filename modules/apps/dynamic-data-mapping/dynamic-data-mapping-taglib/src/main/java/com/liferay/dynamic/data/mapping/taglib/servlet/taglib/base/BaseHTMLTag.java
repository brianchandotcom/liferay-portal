/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public abstract class BaseHTMLTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getCheckRequired() {
		return _checkRequired;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public DDMFormValues getDdmFormValues() {
		return _ddmFormValues;
	}

	public Locale getDefaultEditLocale() {
		return _defaultEditLocale;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public String getDocumentLibrarySelectorURL() {
		return _documentLibrarySelectorURL;
	}

	public String getFieldsNamespace() {
		return _fieldsNamespace;
	}

	public long getGroupId() {
		return _groupId;
	}

	public boolean getIgnoreRequestValue() {
		return _ignoreRequestValue;
	}

	public String getImageSelectorURL() {
		return _imageSelectorURL;
	}

	public String getLayoutSelectorURL() {
		return _layoutSelectorURL;
	}

	public boolean getLocalizable() {
		return _localizable;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean getRepeatable() {
		return _repeatable;
	}

	public Locale getRequestedLocale() {
		return _requestedLocale;
	}

	public boolean getShowEmptyFieldLabel() {
		return _showEmptyFieldLabel;
	}

	public boolean getShowLanguageSelector() {
		return _showLanguageSelector;
	}

	public boolean getSynchronousFormSubmission() {
		return _synchronousFormSubmission;
	}

	public void setCheckRequired(boolean checkRequired) {
		_checkRequired = checkRequired;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setDdmFormValues(DDMFormValues ddmFormValues) {
		_ddmFormValues = ddmFormValues;
	}

	public void setDefaultEditLocale(Locale defaultEditLocale) {
		_defaultEditLocale = defaultEditLocale;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	public void setDocumentLibrarySelectorURL(
		String documentLibrarySelectorURL) {

		_documentLibrarySelectorURL = documentLibrarySelectorURL;
	}

	public void setFieldsNamespace(String fieldsNamespace) {
		_fieldsNamespace = fieldsNamespace;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	public void setImageSelectorURL(String imageSelectorURL) {
		_imageSelectorURL = imageSelectorURL;
	}

	public void setLayoutSelectorURL(String layoutSelectorURL) {
		_layoutSelectorURL = layoutSelectorURL;
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setRepeatable(boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setRequestedLocale(Locale requestedLocale) {
		_requestedLocale = requestedLocale;
	}

	public void setShowEmptyFieldLabel(boolean showEmptyFieldLabel) {
		_showEmptyFieldLabel = showEmptyFieldLabel;
	}

	public void setShowLanguageSelector(boolean showLanguageSelector) {
		_showLanguageSelector = showLanguageSelector;
	}

	public void setSynchronousFormSubmission(
		boolean synchronousFormSubmission) {

		_synchronousFormSubmission = synchronousFormSubmission;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checkRequired = true;
		_classNameId = 0;
		_classPK = 0;
		_ddmFormValues = null;
		_defaultEditLocale = null;
		_defaultLocale = null;
		_documentLibrarySelectorURL = null;
		_fieldsNamespace = null;
		_groupId = 0;
		_ignoreRequestValue = false;
		_imageSelectorURL = null;
		_layoutSelectorURL = null;
		_localizable = true;
		_readOnly = false;
		_repeatable = true;
		_requestedLocale = null;
		_showEmptyFieldLabel = true;
		_showLanguageSelector = true;
		_synchronousFormSubmission = true;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(
			httpServletRequest, "checkRequired", _checkRequired);
		setNamespacedAttribute(httpServletRequest, "classNameId", _classNameId);
		setNamespacedAttribute(httpServletRequest, "classPK", _classPK);
		setNamespacedAttribute(
			httpServletRequest, "ddmFormValues", _ddmFormValues);
		setNamespacedAttribute(
			httpServletRequest, "defaultEditLocale", _defaultEditLocale);
		setNamespacedAttribute(
			httpServletRequest, "defaultLocale", _defaultLocale);
		setNamespacedAttribute(
			httpServletRequest, "documentLibrarySelectorURL",
			_documentLibrarySelectorURL);
		setNamespacedAttribute(
			httpServletRequest, "fieldsNamespace", _fieldsNamespace);
		setNamespacedAttribute(httpServletRequest, "groupId", _groupId);
		setNamespacedAttribute(
			httpServletRequest, "ignoreRequestValue", _ignoreRequestValue);
		setNamespacedAttribute(
			httpServletRequest, "imageSelectorURL", _imageSelectorURL);
		setNamespacedAttribute(
			httpServletRequest, "layoutSelectorURL", _layoutSelectorURL);
		setNamespacedAttribute(httpServletRequest, "localizable", _localizable);
		setNamespacedAttribute(httpServletRequest, "readOnly", _readOnly);
		setNamespacedAttribute(httpServletRequest, "repeatable", _repeatable);
		setNamespacedAttribute(
			httpServletRequest, "requestedLocale", _requestedLocale);
		setNamespacedAttribute(
			httpServletRequest, "showEmptyFieldLabel", _showEmptyFieldLabel);
		setNamespacedAttribute(
			httpServletRequest, "showLanguageSelector", _showLanguageSelector);
		setNamespacedAttribute(
			httpServletRequest, "synchronousFormSubmission",
			_synchronousFormSubmission);
	}

	protected static final String ATTRIBUTE_NAMESPACE = "liferay-ddm:html:";

	private static final String _START_PAGE = "/html/start.jsp";

	private boolean _checkRequired = true;
	private long _classNameId;
	private long _classPK;
	private DDMFormValues _ddmFormValues;
	private Locale _defaultEditLocale;
	private Locale _defaultLocale;
	private String _documentLibrarySelectorURL;
	private String _fieldsNamespace;
	private long _groupId;
	private boolean _ignoreRequestValue;
	private String _imageSelectorURL;
	private String _layoutSelectorURL;
	private boolean _localizable = true;
	private boolean _readOnly;
	private boolean _repeatable = true;
	private Locale _requestedLocale;
	private boolean _showEmptyFieldLabel = true;
	private boolean _showLanguageSelector = true;
	private boolean _synchronousFormSubmission = true;

}