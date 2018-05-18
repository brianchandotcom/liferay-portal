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
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.service.CPRuleLocalService;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommerceContextFactoryImpl implements CommerceContextFactory {

	@Override
	public CommerceContext create(HttpServletRequest httpServletRequest) {
		return new CommerceContextImpl(
			httpServletRequest, _commerceCurrencyService,
			_commerceOrderHttpHelper, _commerceOrganizationHelper,
			_commercePriceListLocalService, _commerceUserSegmentHelper,
			_cpRuleLocalService, _portal);
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CommerceUserSegmentHelper _commerceUserSegmentHelper;

	@Reference
	private CPRuleLocalService _cpRuleLocalService;

	@Reference
	private Portal _portal;

}