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

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class QuantityInputTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_cpDefinition = CPDefinitionServiceUtil.getCPDefinition(
				_cpDefinitionId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get cpDefinition", pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setUseSelect(boolean useSelect) {
		_useSelect = useSelect;
	}

	@Override
	protected void cleanUp() {
		_cpDefinition = null;
		_cpDefinitionId = 0;
		_useSelect = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-commerce:quantity-input:cpDefinition", _cpDefinition);
		request.setAttribute(
			"liferay-commerce:quantity-input:useSelect", _useSelect);
	}

	private static final String _PAGE = "/quantity_input/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		QuantityInputTag.class);

	private CPDefinition _cpDefinition;
	private long _cpDefinitionId;
	private boolean _useSelect = true;

}