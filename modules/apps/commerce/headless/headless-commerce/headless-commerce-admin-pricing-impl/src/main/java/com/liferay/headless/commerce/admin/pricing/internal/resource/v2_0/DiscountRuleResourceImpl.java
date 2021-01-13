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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountRuleDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountRuleUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountRuleResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/discount-rule.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountRuleResource.class
)
public class DiscountRuleResourceImpl extends BaseDiscountRuleResourceImpl {

	@Override
	public void deleteDiscountRule(Long id) throws Exception {
		_commerceDiscountRuleService.deleteCommerceDiscountRule(id);
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
				"Unable to find Discount with externalReferenceCode: " +
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
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommerceDiscountRule> commerceDiscountRules =
			_commerceDiscountRuleService.getCommerceDiscountRules(
				id, search, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commerceDiscountRuleService.getCommerceDiscountRulesCount(
				id, search);

		return Page.of(
			_toDiscountRules(commerceDiscountRules), pagination, totalItems);
	}

	@Override
	public DiscountRule getDiscountRule(Long id) throws Exception {
		return _toDiscountRule(GetterUtil.getLong(id));
	}

	@Override
	public DiscountRule patchDiscountRule(Long id, DiscountRule discountRule)
		throws Exception {

		CommerceDiscountRule commerceDiscountRule =
			_commerceDiscountRuleService.getCommerceDiscountRule(id);

		return _toDiscountRule(
			_commerceDiscountRuleService.updateCommerceDiscountRule(
				commerceDiscountRule.getCommerceDiscountRuleId(),
				GetterUtil.get(
					discountRule.getName(), commerceDiscountRule.getName()),
				discountRule.getType(),
				GetterUtil.get(
					discountRule.getTypeSettings(),
					commerceDiscountRule.getTypeSettings())));
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
				"Unable to find Discount with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommerceDiscountRule commerceDiscountRule =
			DiscountRuleUtil.addCommerceDiscountRule(
				_commerceDiscountRuleService, discountRule, commerceDiscount,
				_serviceContextHelper);

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
				_serviceContextHelper);

		return _toDiscountRule(
			commerceDiscountRule.getCommerceDiscountRuleId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceDiscountRule commerceDiscountRule)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceDiscountRule.getCommerceDiscountRuleId(),
				"deleteDiscountRule",
				_commerceDiscountRuleModelResourcePermission)
		).put(
			"get",
			addAction(
				"VIEW", commerceDiscountRule.getCommerceDiscountRuleId(),
				"getDiscountRule", _commerceDiscountRuleModelResourcePermission)
		).put(
			"update",
			addAction(
				"UPDATE", commerceDiscountRule.getCommerceDiscountRuleId(),
				"patchDiscountRule",
				_commerceDiscountRuleModelResourcePermission)
		).build();
	}

	private DiscountRule _toDiscountRule(
			CommerceDiscountRule commerceDiscountRule)
		throws Exception {

		return _toDiscountRule(
			commerceDiscountRule.getCommerceDiscountRuleId());
	}

	private DiscountRule _toDiscountRule(Long commerceDiscountRuleId)
		throws Exception {

		CommerceDiscountRule commerceDiscountRule =
			_commerceDiscountRuleService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		return _discountRuleDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceDiscountRule), _dtoConverterRegistry,
				commerceDiscountRuleId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
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

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscountRule)"
	)
	private ModelResourcePermission<CommerceDiscountRule>
		_commerceDiscountRuleModelResourcePermission;

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountRuleDTOConverter _discountRuleDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}