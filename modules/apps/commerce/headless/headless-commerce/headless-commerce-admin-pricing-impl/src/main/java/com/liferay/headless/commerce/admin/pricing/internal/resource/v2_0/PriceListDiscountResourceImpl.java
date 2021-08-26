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

import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListDiscount;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListDiscountDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListDiscountUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListDiscountResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/price-list-discount.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PriceListDiscountResource.class}
)
public class PriceListDiscountResourceImpl
	extends BasePriceListDiscountResourceImpl implements NestedFieldSupport {

	@Override
	public void deletePriceListDiscount(Long id) throws Exception {
		_commercePriceListDiscountRelService.deleteCommercePriceListDiscountRel(
			id);
	}

	@Override
	public Page<PriceListDiscount>
			getPriceListByExternalReferenceCodePriceListDiscountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		List<CommercePriceListDiscountRel> commercePriceListDiscountRels =
			_commercePriceListDiscountRelService.
				getCommercePriceListDiscountRels(
					commercePriceList.getCommercePriceListId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		int totalItems =
			_commercePriceListDiscountRelService.
				getCommercePriceListDiscountRelsCount(
					commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceListDiscounts(commercePriceListDiscountRels), pagination,
			totalItems);
	}

	@NestedField(parentClass = PriceList.class, value = "priceListDiscounts")
	@Override
	public Page<PriceListDiscount> getPriceListIdPriceListDiscountsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommercePriceListDiscountRel> commercePriceListDiscountRels =
			_commercePriceListDiscountRelService.
				getCommercePriceListDiscountRels(
					id, pagination.getStartPosition(),
					pagination.getEndPosition(), null);

		int totalItems =
			_commercePriceListDiscountRelService.
				getCommercePriceListDiscountRelsCount(id);

		return Page.of(
			_toPriceListDiscounts(commercePriceListDiscountRels), pagination,
			totalItems);
	}

	@Override
	public PriceListDiscount
			postPriceListByExternalReferenceCodePriceListDiscount(
				String externalReferenceCode,
				PriceListDiscount priceListDiscount)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			PriceListDiscountUtil.addCommercePriceListDiscountRel(
				_commerceDiscountService, _commercePriceListDiscountRelService,
				priceListDiscount, commercePriceList, _serviceContextHelper);

		return _toPriceListDiscount(
			commercePriceListDiscountRel.getCommercePriceListDiscountRelId());
	}

	@Override
	public PriceListDiscount postPriceListIdPriceListDiscount(
			Long id, PriceListDiscount priceListDiscount)
		throws Exception {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			PriceListDiscountUtil.addCommercePriceListDiscountRel(
				_commerceDiscountService, _commercePriceListDiscountRelService,
				priceListDiscount,
				_commercePriceListService.getCommercePriceList(id),
				_serviceContextHelper);

		return _toPriceListDiscount(
			commercePriceListDiscountRel.getCommercePriceListDiscountRelId());
	}

	private PriceListDiscount _toPriceListDiscount(
			Long commercePriceListDiscountRelId)
		throws Exception {

		return _priceListDiscountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListDiscountRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<PriceListDiscount> _toPriceListDiscounts(
			List<CommercePriceListDiscountRel> commercePriceListDiscountRels)
		throws Exception {

		List<PriceListDiscount> priceListDiscounts = new ArrayList<>();

		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				commercePriceListDiscountRels) {

			priceListDiscounts.add(
				_toPriceListDiscount(
					commercePriceListDiscountRel.
						getCommercePriceListDiscountRelId()));
		}

		return priceListDiscounts;
	}

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommercePriceListDiscountRelService
		_commercePriceListDiscountRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private PriceListDiscountDTOConverter _priceListDiscountDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}