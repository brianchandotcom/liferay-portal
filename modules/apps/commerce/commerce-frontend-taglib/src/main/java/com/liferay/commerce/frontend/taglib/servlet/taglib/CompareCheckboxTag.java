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

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Diego Mastrorilli
 */
public class CompareCheckboxTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			CPDefinition cpDefinition =
				CPDefinitionLocalServiceUtil.getCPDefinition(
					_cpCatalogEntry.getCPDefinitionId());

			HttpServletRequest httpServletRequest = getRequest();

			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			long commerceAccountId = CommerceUtil.getCommerceAccountId(
				commerceContext);

			_pictureUrl = cpDefinition.getDefaultImageThumbnailSrc(
				commerceAccountId);

			List<Long> cpDefinitionIds = _getCPDefinitionIds(
				commerceContext.getCommerceChannelGroupId(), commerceAccountId,
				CookieKeys.getCookie(
					httpServletRequest,
					_getCPDefinitionIdsCookieKey(
						commerceContext.getCommerceChannelGroupId())));

			_inCompare = cpDefinitionIds.contains(
				_cpCatalogEntry.getCPDefinitionId());
		}
		catch (Exception exception) {
			_log.error(exception);

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public CPCatalogEntry getCPCatalogEntry() {
		return _cpCatalogEntry;
	}

	public String getLabel() {
		return _label;
	}

	public void setCPCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpCatalogEntry = null;
		_disabled = false;
		_inCompare = false;
		_label = StringPool.BLANK;
		_pictureUrl = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-commerce:compare-checkbox:cpCatalogEntry",
			_cpCatalogEntry);
		httpServletRequest.setAttribute(
			"liferay-commerce:compare-checkbox:disabled", _disabled);
		httpServletRequest.setAttribute(
			"liferay-commerce:compare-checkbox:inCompare", _inCompare);
		httpServletRequest.setAttribute(
			"liferay-commerce:compare-checkbox:label", _label);
		httpServletRequest.setAttribute(
			"liferay-commerce:compare-checkbox:pictureUrl", _pictureUrl);
	}

	private List<Long> _getCPDefinitionIds(
			long groupId, long commerceAccountId,
			String cpDefinitionIdsCookieValue)
		throws PortalException {

		CPCompareHelper cpCompareHelper =
			ServletContextUtil.getCPCompareHelper();

		return cpCompareHelper.getCPDefinitionIds(
			groupId, commerceAccountId, cpDefinitionIdsCookieValue);
	}

	private String _getCPDefinitionIdsCookieKey(long commerceChannelGroupId) {
		CPCompareHelper cpCompareHelper =
			ServletContextUtil.getCPCompareHelper();

		return cpCompareHelper.getCPDefinitionIdsCookieKey(
			commerceChannelGroupId);
	}

	private static final String _PAGE = "/compare_checkbox/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		CompareCheckboxTag.class);

	private CPCatalogEntry _cpCatalogEntry;
	private boolean _disabled;
	private boolean _inCompare;
	private String _label = StringPool.BLANK;
	private String _pictureUrl;

}