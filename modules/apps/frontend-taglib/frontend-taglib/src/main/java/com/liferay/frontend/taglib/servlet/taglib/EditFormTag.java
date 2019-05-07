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

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class EditFormTag extends IncludeTag {

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

	public boolean isEscapeXml() {
		return _escapeXml;
	}

	public boolean isFluid() {
		return _fluid;
	}

	public boolean isInlineLabels() {
		return _inlineLabels;
	}

	public boolean isUseNamespace() {
		return _useNamespace;
	}

	public boolean isValidateOnBlur() {
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

	public void setFluid(boolean fluid) {
		_fluid = fluid;
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

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
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
		_fluid = false;
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:action",
			_getAction(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:cssClass", _cssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:escapeXml", String.valueOf(_escapeXml));
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:fluid", _fluid);
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:inlineLabels",
			String.valueOf(_inlineLabels));
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:method", _method);
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:name", _name);
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:onSubmit", _onSubmit);
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:portletNamespace", _portletNamespace);
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:useNamespace",
			String.valueOf(_useNamespace));
		httpServletRequest.setAttribute(
			"liferay-frontend:edit-form:validateOnBlur",
			String.valueOf(_validateOnBlur));
		httpServletRequest.setAttribute(
			"LIFERAY_SHARED_aui:form:checkboxNames", _checkboxNames);
		httpServletRequest.setAttribute(
			"LIFERAY_SHARED_aui:form:validatorTagsMap", _validatorTagsMap);
	}

	private String _getAction(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isAddSessionIdToURL()) {
			return PortalUtil.getURLWithSessionId(
				_action, themeDisplay.getSessionId());
		}

		return _action;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:edit-form:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/edit_form/end.jsp";

	private static final String _START_PAGE = "/edit_form/start.jsp";

	private String _action;
	private final List<String> _checkboxNames = new ArrayList<>();
	private String _cssClass;
	private boolean _escapeXml = true;
	private boolean _fluid;
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