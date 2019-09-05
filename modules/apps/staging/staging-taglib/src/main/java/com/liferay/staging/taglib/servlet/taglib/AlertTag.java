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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Péter Alius
 */
public class AlertTag extends IncludeTag implements BodyTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public AlertType getType() {
		return _type;
	}

	public boolean isDismissible() {
		return _dismissible;
	}

	public boolean isFluid() {
		return _fluid;
	}

	public void setDismissible(boolean dismissible) {
		_dismissible = dismissible;
	}

	public void setFluid(boolean fluid) {
		_fluid = fluid;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setType(AlertType type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_dismissible = false;
		_fluid = false;
		_type = AlertType.INFO;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:alert:dismissible", String.valueOf(_dismissible));
		httpServletRequest.setAttribute(
			"liferay-staging:alert:fluid", String.valueOf(_fluid));
		httpServletRequest.setAttribute(
			"liferay-staging:alert:type", _type.getAlertCode());
	}

	private static final String _ATTRIBUTE_NAMESPACE = "liferay-staging:alert:";

	private static final String _PAGE = "/alert/page.jsp";

	private boolean _dismissible;
	private boolean _fluid;
	private AlertType _type = AlertType.INFO;

}