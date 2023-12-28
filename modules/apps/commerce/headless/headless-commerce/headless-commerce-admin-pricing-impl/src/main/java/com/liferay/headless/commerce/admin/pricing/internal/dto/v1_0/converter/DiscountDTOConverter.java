/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.Discount;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceDiscount",
	service = DTOConverter.class
)
public class DiscountDTOConverter
	implements DTOConverter<CommerceDiscount, Discount> {

	@Override
	public String getContentType() {
		return Discount.class.getSimpleName();
	}

	@Override
	public Discount toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.getCommerceDiscount(
				(Long)dtoConverterContext.getId());

		return new Discount() {
			{
				setActive(commerceDiscount::isActive);
				setCouponCode(commerceDiscount::getCouponCode);
				setCustomFields(
					() -> {
						ExpandoBridge expandoBridge =
							commerceDiscount.getExpandoBridge();

						return expandoBridge.getAttributes();
					});
				setDisplayDate(commerceDiscount::getDisplayDate);
				setExpirationDate(commerceDiscount::getExpirationDate);
				setExternalReferenceCode(
					commerceDiscount::getExternalReferenceCode);
				setId(commerceDiscount::getCommerceDiscountId);
				setLimitationTimes(commerceDiscount::getLimitationTimes);
				setLimitationType(commerceDiscount::getLimitationType);
				setMaximumDiscountAmount(
					commerceDiscount::getMaximumDiscountAmount);
				setNumberOfUse(commerceDiscount::getNumberOfUse);
				setPercentageLevel1(commerceDiscount::getLevel1);
				setPercentageLevel2(commerceDiscount::getLevel2);
				setPercentageLevel3(commerceDiscount::getLevel3);
				setPercentageLevel4(commerceDiscount::getLevel4);
				setTarget(commerceDiscount::getTarget);
				setTitle(commerceDiscount::getTitle);
				setUseCouponCode(commerceDiscount::isUseCouponCode);
				setUsePercentage(commerceDiscount::isUsePercentage);
			}
		};
	}

	@Reference
	private CommerceDiscountService _commerceDiscountService;

}