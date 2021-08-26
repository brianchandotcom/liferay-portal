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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0;

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.DiscountRuleDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountRuleUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountRuleResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/discount-rule.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountRuleResource.class
)
public class DiscountRuleResourceImpl extends BaseDiscountRuleResourceImpl {

	@Override
	public Response deleteDiscountRule(Long id) throws Exception {
		_commerceDiscountRuleService.deleteCommerceDiscountRule(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<DiscountRule>
			getDiscountByExternalReferenceCodeDiscountRulesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		List<CommerceDiscountRule> commerceDiscountRules =
			_commerceDiscountRuleService.getCommerceDiscountRules(
				commerceDiscount.getCommerceDiscountId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commerceDiscountRuleService.getCommerceDiscountRulesCount(
				commerceDiscount.getCommerceDiscountId());

		return Page.of(
			_toDiscountRules(commerceDiscountRules), pagination, totalItems);
	}

	@Override
	public Page<DiscountRule> getDiscountIdDiscountRulesPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceDiscountRule> commerceDiscountRules =
			_commerceDiscountRuleService.getCommerceDiscountRules(
				id, pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commerceDiscountRuleService.getCommerceDiscountRulesCount(id);

		return Page.of(
			_toDiscountRules(commerceDiscountRules), pagination, totalItems);
	}

	@Override
	public DiscountRule getDiscountRule(Long id) throws Exception {
		return _toDiscountRule(GetterUtil.getLong(id));
	}

	@Override
	public Response patchDiscountRule(Long id, DiscountRule discountRule)
		throws Exception {

		CommerceDiscountRule commerceDiscountRule =
			_commerceDiscountRuleService.getCommerceDiscountRule(id);

		_commerceDiscountRuleService.updateCommerceDiscountRule(
			commerceDiscountRule.getCommerceDiscountRuleId(),
			discountRule.getType(),
			GetterUtil.get(
				discountRule.getTypeSettings(),
				commerceDiscountRule.getTypeSettings()));

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public DiscountRule postDiscountByExternalReferenceCodeDiscountRule(
			String externalReferenceCode, DiscountRule discountRule)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		CommerceDiscountRule commerceDiscountRule =
			DiscountRuleUtil.addCommerceDiscountRule(
				_commerceDiscountRuleService, discountRule, commerceDiscount,
				_serviceContextHelper.getServiceContext());

		return _toDiscountRule(
			commerceDiscountRule.getCommerceDiscountRuleId());
	}

	@Override
	public DiscountRule postDiscountIdDiscountRule(
			Long id, DiscountRule discountRule)
		throws Exception {

		CommerceDiscountRule commerceDiscountRule =
			DiscountRuleUtil.addCommerceDiscountRule(
				_commerceDiscountRuleService, discountRule,
				_commerceDiscountService.getCommerceDiscount(id),
				_serviceContextHelper.getServiceContext());

		return _toDiscountRule(
			commerceDiscountRule.getCommerceDiscountRuleId());
	}

	private DiscountRule _toDiscountRule(Long discountId) throws Exception {
		return _discountRuleDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				discountId, contextAcceptLanguage.getPreferredLocale()));
	}

	private List<DiscountRule> _toDiscountRules(
			List<CommerceDiscountRule> commerceDiscountRules)
		throws Exception {

		List<DiscountRule> discountRules = new ArrayList<>();

		for (CommerceDiscountRule commerceDiscountRule :
				commerceDiscountRules) {

			discountRules.add(
				_toDiscountRule(
					commerceDiscountRule.getCommerceDiscountRuleId()));
		}

		return discountRules;
	}

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountRuleDTOConverter _discountRuleDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}