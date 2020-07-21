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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.util.CPCompareHelperUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Diego Mastrorilli
 */
public class CompareCheckboxTag extends IncludeTag {

	@Override
	public int doStartTag()  throws JspException {
		try {
			CPDefinition cpDefinition =
				CPDefinitionLocalServiceUtil.getCPDefinition(_cpDefinitionId);

			_pictureUrl = cpDefinition.getDefaultImageThumbnailSrc();

			long commerceAccountId = 0;

			CommerceContext commerceContext =
				(CommerceContext)request.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			if (commerceAccount != null) {
				commerceAccountId = commerceAccount.getCommerceAccountId();
			}

			HttpServletRequest originalHttpServletRequest =
				PortalUtil.getOriginalServletRequest(request);

			List<Long> cpDefinitionIds = CPCompareHelperUtil.getCPDefinitionIds(
				commerceContext.getCommerceChannelGroupId(), commerceAccountId,
				originalHttpServletRequest.getSession());

			_inCompare = cpDefinitionIds.contains(_cpDefinitionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return super.doStartTag();
	}

	public void setLabel(String label) {
		_label = label;
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

		_disabled = false;
		_inCompare = false;
		_cpDefinitionId = 0;
		_label = "";
		_pictureUrl = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-commerce:compare-checkbox:disabled", _disabled);
		request.setAttribute("liferay-commerce:compare-checkbox:inCompare", _inCompare);
		request.setAttribute("liferay-commerce:compare-checkbox:itemId", _cpDefinitionId);
		request.setAttribute("liferay-commerce:compare-checkbox:label", _label);
		request.setAttribute("liferay-commerce:compare-checkbox:pictureUrl", _pictureUrl);
	}

	private static final String _PAGE = "/compare_checkbox/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		CompareCheckboxTag.class);

	private boolean _disabled = false;
	private boolean _inCompare = false;
	private long _cpDefinitionId = 0;
	private String _label = "";
	private String _pictureUrl;
}