/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.commerce.product.util.CPCompareUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CompareProductTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_cpDefinitionIds = CPCompareUtil.getCPDefinitionIds(request);

			_cpDefinition = CPDefinitionServiceUtil.getCPDefinition(
				_cpDefinitionId);

			if (_cpDefinitionIds == null) {
				_cpDefinitionIds = new ArrayList<>();
			}

			_checked = _cpDefinitionIds.contains(_cpDefinitionId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
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

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checked = false;
		_cpDefinition = null;
		_cpDefinitionId = 0;
		_cpDefinitionIds = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:compare-product:checked", _checked);
		request.setAttribute(
			"liferay-commerce:compare-product:cpDefinition", _cpDefinition);
	}

	private static final String _PAGE = "/compare_product/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		CompareProductTag.class);

	private boolean _checked;
	private CPDefinition _cpDefinition;
	private long _cpDefinitionId;
	private List<Long> _cpDefinitionIds;

}