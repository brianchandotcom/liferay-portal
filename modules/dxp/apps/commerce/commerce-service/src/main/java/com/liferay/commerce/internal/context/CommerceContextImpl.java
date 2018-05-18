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

package com.liferay.commerce.internal.context;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.CPRuleLocalService;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceContextImpl implements CommerceContext {

	public CommerceContextImpl(
		HttpServletRequest httpServletRequest,
		CommerceCurrencyService commerceCurrencyService,
		CommerceOrderHttpHelper commerceOrderHttpHelper,
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommercePriceListLocalService commercePriceListLocalService,
		CommerceUserSegmentHelper commerceUserSegmentHelper,
		CPRuleLocalService cpRuleLocalService, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_commerceCurrencyService = commerceCurrencyService;
		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_commerceOrganizationHelper = commerceOrganizationHelper;
		_commercePriceListLocalService = commercePriceListLocalService;
		_commerceUserSegmentHelper = commerceUserSegmentHelper;
		_cpRuleLocalService = cpRuleLocalService;
		_portal = portal;
	}

	@Override
	public CommerceCurrency getCommerceCurrency() throws PortalException {
		if (_commerceCurrency != null) {
			return _commerceCurrency;
		}

		long groupId = _portal.getScopeGroupId(_httpServletRequest);

		_commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		return _commerceCurrency;
	}

	@Override
	public CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		_commerceOrder = _commerceOrderHttpHelper.getCurrentCommerceOrder(
			_httpServletRequest);

		return _commerceOrder;
	}

	@Override
	public Optional<CommercePriceList> getCommercePriceList()
		throws PortalException {

		if (_commercePriceList != null) {
			return _commercePriceList;
		}

		long groupId = _portal.getScopeGroupId(_httpServletRequest);

		_commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				groupId, getCommerceUserSegmentEntryIds());

		return _commercePriceList;
	}

	@Override
	public long[] getCommerceUserSegmentEntryIds() throws PortalException {
		if (_commerceUserSegmentEntryIds != null) {
			return _commerceUserSegmentEntryIds;
		}

		_commerceUserSegmentEntryIds =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_httpServletRequest);

		return _commerceUserSegmentEntryIds;
	}

	@Override
	public List<CPRule> getCPRules() {
		if (_cpRules != null) {
			return _cpRules;
		}

		return null;
	}

	@Override
	public Organization getOrganization() throws PortalException {
		if (_organization != null) {
			return _organization;
		}

		_organization = _commerceOrganizationHelper.getCurrentOrganization(
			_httpServletRequest);

		return _organization;
	}

	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyService _commerceCurrencyService;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private final CommerceOrganizationHelper _commerceOrganizationHelper;
	private Optional<CommercePriceList> _commercePriceList;
	private final CommercePriceListLocalService _commercePriceListLocalService;
	private long[] _commerceUserSegmentEntryIds;
	private final CommerceUserSegmentHelper _commerceUserSegmentHelper;
	private final CPRuleLocalService _cpRuleLocalService;
	private List<CPRule> _cpRules;
	private final HttpServletRequest _httpServletRequest;
	private Organization _organization;
	private final Portal _portal;

}