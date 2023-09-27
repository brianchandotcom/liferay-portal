/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Diego Mastrorilli
 */
public class InfoBoxTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		HttpServletRequest httpServletRequest = getRequest();

		setNamespacedAttribute(httpServletRequest, "actionLabel", _actionLabel);
		setNamespacedAttribute(
			httpServletRequest, "actionTargetId", _actionTargetId);
		setNamespacedAttribute(httpServletRequest, "actionUrl", _actionUrl);
		setNamespacedAttribute(
			httpServletRequest, "elementClasses", _elementClasses);
		setNamespacedAttribute(httpServletRequest, "title", _title);

		super.doStartTag();

		return EVAL_BODY_INCLUDE;
	}

	public String getActionLabel() {
		return _actionLabel;
	}

	public String getActionTargetId() {
		return _actionTargetId;
	}

	public String getActionUrl() {
		return _actionUrl;
	}

	public String getElementClasses() {
		return _elementClasses;
	}

	public String getTitle() {
		return _title;
	}

	public void setActionLabel(String actionLabel) {
		_actionLabel = actionLabel;
	}

	public void setActionTargetId(String actionTargetId) {
		_actionTargetId = actionTargetId;
	}

	public void setActionUrl(String actionUrl) {
		_actionUrl = actionUrl;
	}

	public void setElementClasses(String elementClasses) {
		_elementClasses = elementClasses;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionLabel = null;
		_actionTargetId = null;
		_actionUrl = null;
		_elementClasses = null;
		_title = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:info-box:";

	private static final String _END_PAGE = "/info_box/end.jsp";

	private static final String _START_PAGE = "/info_box/start.jsp";

	private String _actionLabel;
	private String _actionTargetId;
	private String _actionUrl;
	private String _elementClasses;
	private String _title;

}