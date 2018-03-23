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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eudaldo Alonso
 */
public class FormTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getAction() {
		return _action;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public boolean getEscapeXml() {
		return _escapeXml;
	}

	public boolean getInlineLabels() {
		return _inlineLabels;
	}

	public String getMethod() {
		return _method;
	}

	public String getName() {
		return _name;
	}

	public String getOnSubmit() {
		return _onSubmit;
	}

	public String getPortletNamespace() {
		return _portletNamespace;
	}

	public boolean getUseNamespace() {
		return _useNamespace;
	}

	public boolean getValidateOnBlur() {
		return _validateOnBlur;
	}

	public void setAction(PortletURL portletURL) {
		if (portletURL != null) {
			setAction(portletURL.toString());
		}
	}

	public void setAction(String action) {
		_action = action;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = escapeXml;
	}

	public void setInlineLabels(boolean inlineLabels) {
		_inlineLabels = inlineLabels;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnSubmit(String onSubmit) {
		_onSubmit = onSubmit;
	}

	public void setPortletNamespace(String portletNamespace) {
		_portletNamespace = portletNamespace;
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;
	}

	public void setValidateOnBlur(boolean validateOnBlur) {
		_validateOnBlur = validateOnBlur;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_action = null;
		_checkboxNames.clear();
		_cssClass = null;
		_escapeXml = true;
		_inlineLabels = false;
		_method = "post";
		_name = "fm";
		_onSubmit = null;
		_portletNamespace = null;
		_useNamespace = true;
		_validateOnBlur = true;

		if (_validatorTagsMap != null) {
			for (List<ValidatorTag> validatorTags :
					_validatorTagsMap.values()) {

				for (ValidatorTag validatorTag : validatorTags) {
					validatorTag.cleanUp();
				}
			}

			_validatorTagsMap.clear();
		}
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
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-frontend:form:action", _action);
		request.setAttribute("liferay-frontend:form:cssClass", _cssClass);
		request.setAttribute(
			"liferay-frontend:form:escapeXml", String.valueOf(_escapeXml));
		request.setAttribute(
			"liferay-frontend:form:inlineLabels",
			String.valueOf(_inlineLabels));
		request.setAttribute("liferay-frontend:form:method", _method);
		request.setAttribute("liferay-frontend:form:name", _name);
		request.setAttribute("liferay-frontend:form:onSubmit", _onSubmit);
		request.setAttribute(
			"liferay-frontend:form:portletNamespace", _portletNamespace);
		request.setAttribute(
			"liferay-frontend:form:useNamespace",
			String.valueOf(_useNamespace));
		request.setAttribute(
			"liferay-frontend:form:validateOnBlur",
			String.valueOf(_validateOnBlur));
		request.setAttribute(
			"liferay-frontend:form:validatorTagsMap", _validatorTagsMap);
		request.setAttribute(
			"LIFERAY_SHARED_aui:form:checkboxNames", _checkboxNames);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "liferay-frontend:form:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/form/end.jsp";

	private static final String _START_PAGE = "/form/start.jsp";

	private String _action;
	private final List<String> _checkboxNames = new ArrayList<>();
	private String _cssClass;
	private boolean _escapeXml = true;
	private boolean _inlineLabels;
	private String _method = "post";
	private String _name = "fm";
	private String _onSubmit;
	private String _portletNamespace;
	private boolean _useNamespace = true;
	private boolean _validateOnBlur = true;
	private final Map<String, List<ValidatorTag>> _validatorTagsMap =
		new HashMap<>();

}