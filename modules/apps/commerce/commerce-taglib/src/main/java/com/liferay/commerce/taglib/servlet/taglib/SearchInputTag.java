/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class SearchInputTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		_data = _getData();

		return super.doStartTag();
	}

	public PortletURL getActionURL() {
		return _actionURL;
	}

	public String getFormName() {
		return _formName;
	}

	public void setActionURL(PortletURL actionURL) {
		_actionURL = actionURL;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionURL = null;
		_data = null;
		_formName = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest = getRequest();

		httpServletRequest.setAttribute(
			"liferay-commerce:search-input:actionURL", _actionURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:search-input:data", _data);
		httpServletRequest.setAttribute(
			"liferay-commerce:search-input:formName", _formName);
	}

	private Map<String, Object> _getData() {
		Map<String, Object> data = new HashMap<>();

		_actionURL.setParameter("keywords", StringPool.BLANK);

		String searchActionURL = String.valueOf(_actionURL);

		List<String> parameters = StringUtil.split(
			HttpComponentsUtil.getQueryString(searchActionURL),
			CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() == 0) {
				continue;
			}

			List<String> parameterParts = StringUtil.split(
				parameter, CharPool.EQUAL);

			if (parameterParts.isEmpty()) {
				continue;
			}

			String parameterValue = StringPool.BLANK;

			if (parameterParts.size() > 1) {
				parameterValue = parameterParts.get(1);
			}

			if (Validator.isNull(parameterValue)) {
				continue;
			}

			parameterValue = HttpComponentsUtil.decodeURL(parameterValue);

			data.put(parameterParts.get(0), parameterValue);
		}

		return data;
	}

	private static final String _PAGE = "/search_input/page.jsp";

	private PortletURL _actionURL;
	private Map<String, Object> _data;
	private String _formName;

}