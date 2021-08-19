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
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountOrderType;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountOrderTypeDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountOrderTypeUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountOrderTypeResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/discount-order-type.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {DiscountOrderTypeResource.class, NestedFieldSupport.class}
)
public class DiscountOrderTypeResourceImpl
	extends BaseDiscountOrderTypeResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteDiscountOrderType(Long id) throws Exception {
		_commerceDiscountOrderTypeRelService.deleteCommerceDiscountOrderTypeRel(
			id);
	}

	@Override
	public Page<DiscountOrderType>
			getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
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

		return Page.of(
			transform(
				_commerceDiscountOrderTypeRelService.
					getCommerceDiscountOrderTypeRels(
						commerceDiscount.getCommerceDiscountId(),
						StringPool.BLANK, pagination.getStartPosition(),
						pagination.getEndPosition(), null),
				commerceDiscountOrderTypeRel -> _toDiscountOrderType(
					commerceDiscountOrderTypeRel.
						getCommerceDiscountOrderTypeRelId())),
			pagination,
			_commerceDiscountOrderTypeRelService.
				getCommerceDiscountOrderTypeRelsCount(
					commerceDiscount.getCommerceDiscountId(),
					StringPool.BLANK));
	}

	@NestedField(parentClass = Discount.class, value = "discountOrderTypes")
	@Override
	public Page<DiscountOrderType> getDiscountIdDiscountOrderTypesPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_commerceDiscountOrderTypeRelService.
					getCommerceDiscountOrderTypeRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition(), null),
				commerceDiscountOrderTypeRel -> _toDiscountOrderType(
					commerceDiscountOrderTypeRel.
						getCommerceDiscountOrderTypeRelId())),
			pagination,
			_commerceDiscountOrderTypeRelService.
				getCommerceDiscountOrderTypeRelsCount(id, search));
	}

	@Override
	public DiscountOrderType
			postDiscountByExternalReferenceCodeDiscountOrderType(
				String externalReferenceCode,
				DiscountOrderType discountOrderType)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			DiscountOrderTypeUtil.addCommerceDiscountOrderTypeRel(
				commerceDiscount, _commerceDiscountOrderTypeRelService,
				_commerceOrderTypeService, discountOrderType,
				_serviceContextHelper);

		return _toDiscountOrderType(
			commerceDiscountOrderTypeRel.getCommerceDiscountOrderTypeRelId());
	}

	@Override
	public DiscountOrderType postDiscountIdDiscountOrderType(
			Long id, DiscountOrderType discountOrderType)
		throws Exception {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			DiscountOrderTypeUtil.addCommerceDiscountOrderTypeRel(
				_commerceDiscountService.getCommerceDiscount(id),
				_commerceDiscountOrderTypeRelService, _commerceOrderTypeService,
				discountOrderType, _serviceContextHelper);

		return _toDiscountOrderType(
			commerceDiscountOrderTypeRel.getCommerceDiscountOrderTypeRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commerceDiscountOrderTypeRel.
					getCommerceDiscountOrderTypeRelId(),
				"deleteDiscountOrderType",
				_commerceDiscountOrderTypeRelModelResourcePermission)
		).build();
	}

	private DiscountOrderType _toDiscountOrderType(
			Long commerceDiscountOrderTypeRelId)
		throws Exception {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			_commerceDiscountOrderTypeRelService.
				getCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRelId);

		return _discountOrderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceDiscountOrderTypeRel),
				_dtoConverterRegistry, commerceDiscountOrderTypeRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel)"
	)
	private ModelResourcePermission<CommerceDiscountOrderTypeRel>
		_commerceDiscountOrderTypeRelModelResourcePermission;

	@Reference
	private CommerceDiscountOrderTypeRelService
		_commerceDiscountOrderTypeRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private DiscountOrderTypeDTOConverter _discountOrderTypeDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}