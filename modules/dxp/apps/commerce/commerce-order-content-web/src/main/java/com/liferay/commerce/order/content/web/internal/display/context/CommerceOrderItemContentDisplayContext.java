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

package com.liferay.commerce.order.content.web.internal.display.context;

import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemContentDisplayContext
	extends BaseCommerceOrderContentDisplayContext<CommerceOrderItem> {

	public CommerceOrderItemContentDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceOrderLocalService commerceOrderLocalService,
			CommerceOrderItemLocalService commerceOrderItemLocalService,
			CommercePriceFormatter commercePriceFormatter,
			CPDefinitionHelper cpDefinitionHelper)
		throws ConfigurationException {

		super(httpServletRequest, commerceOrderLocalService);

		_commerceOrderItemLocalService = commerceOrderItemLocalService;
		_commercePriceFormatter = commercePriceFormatter;
		_cpDefinitionHelper = cpDefinitionHelper;
	}

	public String getCPDefinitionURL(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public String getFormattedPrice(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return _commercePriceFormatter.format(
			commerceOrder.getCommerceCurrency(), commerceOrderItem.getPrice());
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderItems");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(getCommerceOrderId()));

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceOrderItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");

		int total = _commerceOrderItemLocalService.getCommerceOrderItemsCount();

		_searchContainer.setTotal(total);

		List<CommerceOrderItem> results =
			_commerceOrderItemLocalService.getCommerceOrderItems(
				getCommerceOrderId(), _searchContainer.getStart(),
				_searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private final CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private SearchContainer<CommerceOrderItem> _searchContainer;

}