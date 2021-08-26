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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountProduct;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.DiscountDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountCategoryUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountProductUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountRuleUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
	properties = "OSGI-INF/liferay/rest/v1_0/discount.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountResource.class
)
public class DiscountResourceImpl extends BaseDiscountResourceImpl {

	@Override
	public Response deleteDiscount(Long id) throws Exception {
		_commerceDiscountService.deleteCommerceDiscount(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteDiscountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		_commerceDiscountService.deleteCommerceDiscount(
			commerceDiscount.getCommerceDiscountId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Discount getDiscount(Long id) throws Exception {
		return _toDiscount(GetterUtil.getLong(id));
	}

	@Override
	public Discount getDiscountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		return _toDiscount(commerceDiscount.getCommerceDiscountId());
	}

	@Override
	public Page<Discount> getDiscountsPage(Pagination pagination)
		throws Exception {

		BaseModelSearchResult<CommerceDiscount>
			commerceDiscountBaseModelSearchResult =
				_commerceDiscountService.searchCommerceDiscounts(
					contextCompany.getCompanyId(), StringPool.BLANK,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		return Page.of(
			_toDiscounts(commerceDiscountBaseModelSearchResult.getBaseModels()),
			pagination, commerceDiscountBaseModelSearchResult.getLength());
	}

	@Override
	public Response patchDiscount(Long id, Discount discount) throws Exception {
		_updateDiscount(
			_commerceDiscountService.getCommerceDiscount(id), discount);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchDiscountByExternalReferenceCode(
			String externalReferenceCode, Discount discount)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		_updateDiscount(commerceDiscount, discount);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Discount postDiscount(Discount discount) throws Exception {
		CommerceDiscount commerceDiscount = _addOrUpdateCommerceDiscount(
			discount);

		return _toDiscount(commerceDiscount.getCommerceDiscountId());
	}

	private CommerceDiscount _addOrUpdateCommerceDiscount(Discount discount)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.addOrUpdateCommerceDiscount(
				discount.getExternalReferenceCode(),
				GetterUtil.getLong(discount.getId()), discount.getTitle(),
				discount.getTarget(),
				GetterUtil.getBoolean(discount.getUseCouponCode()),
				discount.getCouponCode(),
				GetterUtil.getBoolean(discount.getUsePercentage()),
				discount.getMaximumDiscountAmount(),
				discount.getPercentageLevel1(), discount.getPercentageLevel2(),
				discount.getPercentageLevel3(), discount.getPercentageLevel4(),
				discount.getLimitationType(),
				GetterUtil.getInteger(discount.getLimitationTimes()),
				GetterUtil.getBoolean(discount.getActive()),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				GetterUtil.getBoolean(discount.getNeverExpire(), true),
				serviceContext);

		// Expando

		Map<String, ?> customFields = discount.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), CommerceDiscount.class,
				commerceDiscount.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(
			discount, commerceDiscount, serviceContext);
	}

	private Discount _toDiscount(Long commerceDiscountId) throws Exception {
		return _discountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<Discount> _toDiscounts(
			List<CommerceDiscount> commerceDiscounts)
		throws Exception {

		List<Discount> discounts = new ArrayList<>();

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			discounts.add(
				_toDiscount(commerceDiscount.getCommerceDiscountId()));
		}

		return discounts;
	}

	private CommerceDiscount _updateDiscount(
			CommerceDiscount commerceDiscount, Discount discount)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		commerceDiscount = _commerceDiscountService.updateCommerceDiscount(
			commerceDiscount.getCommerceDiscountId(), discount.getTitle(),
			discount.getTarget(),
			GetterUtil.get(
				discount.getUseCouponCode(),
				commerceDiscount.isUseCouponCode()),
			GetterUtil.get(
				discount.getCouponCode(), commerceDiscount.getCouponCode()),
			GetterUtil.get(
				discount.getUsePercentage(),
				commerceDiscount.isUsePercentage()),
			(BigDecimal)GetterUtil.get(
				discount.getMaximumDiscountAmount(),
				commerceDiscount.getMaximumDiscountAmount()),
			(BigDecimal)GetterUtil.get(
				discount.getPercentageLevel1(), commerceDiscount.getLevel1()),
			(BigDecimal)GetterUtil.get(
				discount.getPercentageLevel2(), commerceDiscount.getLevel2()),
			(BigDecimal)GetterUtil.get(
				discount.getPercentageLevel3(), commerceDiscount.getLevel3()),
			(BigDecimal)GetterUtil.get(
				discount.getPercentageLevel4(), commerceDiscount.getLevel4()),
			discount.getLimitationType(),
			GetterUtil.get(
				discount.getLimitationTimes(),
				commerceDiscount.getLimitationTimes()),
			GetterUtil.get(discount.getActive(), commerceDiscount.isActive()),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(discount.getNeverExpire(), true),
			serviceContext);

		// Expando

		Map<String, ?> customFields = discount.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), CommerceDiscount.class,
				commerceDiscount.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(
			discount, commerceDiscount, serviceContext);
	}

	private CommerceDiscount _updateNestedResources(
			Discount discount, CommerceDiscount commerceDiscount,
			ServiceContext serviceContext)
		throws Exception {

		// Discount account groups

		DiscountAccountGroup[] discountAccountGroups =
			discount.getDiscountAccountGroups();

		if (discountAccountGroups != null) {
			for (DiscountAccountGroup discountAccountGroup :
					discountAccountGroups) {

				CommerceDiscountCommerceAccountGroupRel
					commerceDiscountCommerceAccountGroupRel =
						_commerceDiscountCommerceAccountGroupRelService.
							fetchCommerceDiscountCommerceAccountGroupRel(
								commerceDiscount.getCommerceDiscountId(),
								discountAccountGroup.getAccountGroupId());

				if (commerceDiscountCommerceAccountGroupRel != null) {
					continue;
				}

				DiscountAccountGroupUtil.
					addCommerceDiscountCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commerceDiscountCommerceAccountGroupRelService,
						discountAccountGroup, commerceDiscount, serviceContext);
			}
		}

		// Discount categories

		DiscountCategory[] discountCategories =
			discount.getDiscountCategories();

		if (discountCategories != null) {
			for (DiscountCategory discountCategory : discountCategories) {
				CommerceDiscountRel commerceDiscountRel =
					_commerceDiscountRelService.fetchCommerceDiscountRel(
						AssetCategory.class.getName(),
						discountCategory.getCategoryId());

				if (commerceDiscountRel != null) {
					continue;
				}

				DiscountCategoryUtil.addCommerceDiscountRel(
					_assetCategoryLocalService, _commerceDiscountRelService,
					discountCategory, commerceDiscount, serviceContext);
			}
		}

		// Discount products

		DiscountProduct[] discountProducts = discount.getDiscountProducts();

		if (discountProducts != null) {
			for (DiscountProduct discountProduct : discountProducts) {
				CProduct cProduct = _cProductLocalService.fetchCProduct(
					discountProduct.getProductId());

				if (cProduct == null) {
					cProduct =
						_cProductLocalService.
							fetchCProductByExternalReferenceCode(
								contextCompany.getCompanyId(),
								discountProduct.
									getProductExternalReferenceCode());
				}

				if (cProduct == null) {
					throw new NoSuchCProductException(
						"Unable to find product with external reference code " +
							discountProduct.getProductExternalReferenceCode());
				}

				CommerceDiscountRel commerceDiscountRel =
					_commerceDiscountRelService.fetchCommerceDiscountRel(
						CPDefinition.class.getName(),
						cProduct.getPublishedCPDefinitionId());

				if (commerceDiscountRel != null) {
					continue;
				}

				DiscountProductUtil.addCommerceDiscountRel(
					_cProductLocalService, _commerceDiscountRelService,
					discountProduct, commerceDiscount, serviceContext);
			}
		}

		// Discount rules

		DiscountRule[] discountRules = discount.getDiscountRules();

		if (discountRules != null) {
			for (DiscountRule discountRule : discountRules) {
				CommerceDiscountRule commerceDiscountRule =
					_commerceDiscountRuleService.fetchCommerceDiscountRule(
						discountRule.getId());

				if (commerceDiscountRule != null) {
					continue;
				}

				DiscountRuleUtil.addCommerceDiscountRule(
					_commerceDiscountRuleService, discountRule,
					commerceDiscount, serviceContext);
			}
		}

		return commerceDiscount;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private DiscountDTOConverter _discountDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}