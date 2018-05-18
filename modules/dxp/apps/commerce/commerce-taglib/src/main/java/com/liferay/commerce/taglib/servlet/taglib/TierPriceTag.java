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

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class TierPriceTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			CommerceContext commerceContext =
				(CommerceContext)request.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			Optional<CommercePriceList> commercePriceListOptional =
				commerceContext.getCommercePriceList();

			if (commercePriceListOptional.isPresent()) {
				CommercePriceList commercePriceList =
					commercePriceListOptional.get();

				CommercePriceEntry commercePriceEntry =
					CommercePriceEntryLocalServiceUtil.fetchCommercePriceEntry(
						_cpInstanceId,
						commercePriceList.getCommercePriceListId());

				if ((commercePriceEntry != null) &&
					commercePriceEntry.getHasTierPrice()) {

					_commerceTierPriceEntries =
						CommerceTierPriceEntryLocalServiceUtil.
							getCommerceTierPriceEntries(
								commercePriceEntry.getCommercePriceEntryId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS,
								new CommerceTierPriceEntryMinQuantityComparator(
									true));
				}
			}

			if (_commerceCurrencyId == 0) {
				CommerceCurrency commerceCurrency =
					commerceContext.getCommerceCurrency();

				_commerceCurrencyId = commerceCurrency.getCommerceCurrencyId();
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setCommerceCurrencyId(long commerceCurrencyId) {
		_commerceCurrencyId = commerceCurrencyId;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commercePriceFormatter = ServletContextUtil.getCommercePriceFormatter();
		servletContext = ServletContextUtil.getServletContext();
	}

	public void setTaglibQuantityInputId(String taglibQuantityInputId) {
		_taglibQuantityInputId = taglibQuantityInputId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceCurrencyId = 0;
		_commerceTierPriceEntries = null;
		_cpInstanceId = 0;
		_taglibQuantityInputId = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:tier-price:commerceCurrencyId",
			_commerceCurrencyId);
		request.setAttribute(
			"liferay-commerce:tier-price:commercePriceFormatter",
			commercePriceFormatter);
		request.setAttribute(
			"liferay-commerce:tier-price:commerceTierPriceEntries",
			_commerceTierPriceEntries);
		request.setAttribute(
			"liferay-commerce:tier-price:cpInstanceId", _cpInstanceId);
		request.setAttribute(
			"liferay-commerce:tier-price:taglibQuantityInputId",
			_taglibQuantityInputId);
	}

	protected CommercePriceFormatter commercePriceFormatter;

	private static final String _PAGE = "/tier_price/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(TierPriceTag.class);

	private long _commerceCurrencyId;
	private List<CommerceTierPriceEntry> _commerceTierPriceEntries;
	private long _cpInstanceId;
	private String _taglibQuantityInputId;

}