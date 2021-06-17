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

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountSku;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountSkuDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountSkuUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountSkuResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/discount-sku.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountSkuResource.class
)
public class DiscountSkuResourceImpl extends BaseDiscountSkuResourceImpl {

	@Override
	public void deleteDiscountSku(Long id) throws Exception {
		_commerceDiscountRelService.deleteCommerceDiscountRel(id);
	}

	@NestedField(parentClass = Discount.class, value = "discountSkus")
	@Override
	public Page<DiscountSku> getDiscountIdDiscountSkusPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelService.getCPInstancesByCommerceDiscountId(
				id, search, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commerceDiscountRelService.getCPInstancesByCommerceDiscountIdCount(
				id, search);

		return Page.of(
			_toDiscountSkus(commerceDiscountRels), pagination, totalItems);
	}

	@Override
	public DiscountSku postDiscountIdDiscountSku(
			@NotNull Long id, DiscountSku discountSku)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			DiscountSkuUtil.addCommerceDiscountRel(
				_cpInstanceLocalService, _commerceDiscountRelService,
				discountSku, _commerceDiscountService.getCommerceDiscount(id),
				_serviceContextHelper);

		return _toDiscountSku(commerceDiscountRel.getCommerceDiscountRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceDiscountRel commerceDiscountRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceDiscountRel.getCommerceDiscountRelId(),
				"deleteDiscountSku",
				_commerceDiscountRelModelResourcePermission)
		).build();
	}

	private DiscountSku _toDiscountSku(Long commerceDiscountRelId)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			_commerceDiscountRelService.getCommerceDiscountRel(
				commerceDiscountRelId);

		return _discountSkuDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceDiscountRel), _dtoConverterRegistry,
				commerceDiscountRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<DiscountSku> _toDiscountSkus(
			List<CommerceDiscountRel> commerceDiscountRels)
		throws Exception {

		List<DiscountSku> discountSkus = new ArrayList<>();

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			discountSkus.add(
				_toDiscountSku(commerceDiscountRel.getCommerceDiscountRelId()));
		}

		return discountSkus;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscountRel)"
	)
	private ModelResourcePermission<CommerceDiscountRel>
		_commerceDiscountRelModelResourcePermission;

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private DiscountSkuDTOConverter _discountSkuDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}