/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.content.helper.CPContentHelper;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class AddToWishListTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest httpServletRequest = getRequest();

			_commerceAccountId = CommerceUtil.getCommerceAccountId(
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT));

			CPSku cpSku = null;

			if (!_cpContentHelper.hasMultipleCPSkus(_cpCatalogEntry)) {
				cpSku = _cpContentHelper.getDefaultCPSku(_cpCatalogEntry);
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_inWishList = _cpContentHelper.isInWishList(
				cpSku, _cpCatalogEntry, themeDisplay);

			if (cpSku != null) {
				_skuId = cpSku.getCPInstanceId();
			}
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

	public boolean isLarge() {
		return _large;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(
			httpServletRequest, "commerceAccountId", _commerceAccountId);
		setNamespacedAttribute(
			httpServletRequest, "cpCatalogEntry", _cpCatalogEntry);
		setNamespacedAttribute(httpServletRequest, "inWishList", _inWishList);
		setNamespacedAttribute(httpServletRequest, "large", _large);
		setNamespacedAttribute(httpServletRequest, "skuId", _skuId);
	}

	public void setCPCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setLarge(boolean large) {
		_large = large;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());

		_cpContentHelper = ServletContextUtil.getCPContentHelper();
	}

	public void setSkuId(long skuId) {
		_skuId = skuId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceAccountId = 0;
		_cpCatalogEntry = null;
		_cpContentHelper = null;
		_inWishList = false;
		_large = false;
		_skuId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:add-to-wish-list:";

	private static final String _PAGE = "/add_to_wish_list/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		AddToWishListTag.class);

	private long _commerceAccountId;
	private CPCatalogEntry _cpCatalogEntry;
	private CPContentHelper _cpContentHelper;
	private boolean _inWishList;
	private boolean _large;
	private long _skuId;

}