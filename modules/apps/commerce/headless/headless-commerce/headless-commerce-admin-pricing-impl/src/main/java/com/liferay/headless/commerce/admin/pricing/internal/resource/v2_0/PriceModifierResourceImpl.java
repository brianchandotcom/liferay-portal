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

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.exception.NoSuchPriceModifierException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifier;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceModifierDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceModifierUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceModifierResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/price-modifier.properties",
	scope = ServiceScope.PROTOTYPE, service = PriceModifierResource.class
)
public class PriceModifierResourceImpl extends BasePriceModifierResourceImpl {

	@Override
	public void deletePriceModifier(Long id) throws Exception {
		_commercePriceModifierService.deleteCommercePriceModifier(id);
	}

	@Override
	public void deletePriceModifierByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceModifier == null) {
			throw new NoSuchPriceModifierException(
				"Unable to find Price Modifier with externalReferenceCode: " +
					externalReferenceCode);
		}

		_commercePriceModifierService.deleteCommercePriceModifier(
			commercePriceModifier.getCommercePriceModifierId());
	}

	@Override
	public Page<PriceModifier>
			getPriceListByExternalReferenceCodePriceModifiersPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierService.getCommercePriceModifiers(
				commercePriceList.getCommercePriceListId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commercePriceModifierService.getCommercePriceModifiersCount(
				commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceModifiers(commercePriceModifiers), pagination, totalItems);
	}

	@Override
	public Page<PriceModifier> getPriceListIdPriceModifiersPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierService.getCommercePriceModifiers(
				id, pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commercePriceModifierService.getCommercePriceModifiersCount(id);

		return Page.of(
			_toPriceModifiers(commercePriceModifiers), pagination, totalItems);
	}

	@Override
	public PriceModifier getPriceModifier(Long id) throws Exception {
		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.getCommercePriceModifier(id);

		return _toPriceModifier(
			commercePriceModifier.getCommercePriceModifierId());
	}

	@Override
	public PriceModifier getPriceModifierByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceModifier == null) {
			throw new NoSuchPriceModifierException(
				"Unable to find Price Modifier with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _toPriceModifier(
			commercePriceModifier.getCommercePriceModifierId());
	}

	@Override
	public Response patchPriceModifier(Long id, PriceModifier priceModifier)
		throws Exception {

		_updatePriceModifier(
			_commercePriceModifierService.getCommercePriceModifier(id),
			priceModifier);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchPriceModifierByExternalReferenceCode(
			String externalReferenceCode, PriceModifier priceModifier)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceModifier == null) {
			throw new NoSuchPriceModifierException(
				"Unable to find Price Modifier with externalReferenceCode: " +
					externalReferenceCode);
		}

		_updatePriceModifier(commercePriceModifier, priceModifier);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public PriceModifier postPriceListByExternalReferenceCodePriceModifier(
			String externalReferenceCode, PriceModifier priceModifier)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommercePriceModifier commercePriceModifier =
			_upsertCommercePriceModifier(commercePriceList, priceModifier);

		return _toPriceModifier(
			commercePriceModifier.getCommercePriceModifierId());
	}

	@Override
	public PriceModifier postPriceListIdPriceModifier(
			Long id, PriceModifier priceModifier)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_upsertCommercePriceModifier(
				_commercePriceListService.getCommercePriceList(id),
				priceModifier);

		return _toPriceModifier(
			commercePriceModifier.getCommercePriceModifierId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePriceModifier commercePriceModifier)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commercePriceModifier.getCommercePriceModifierId(),
				"deletePriceModifier",
				_commercePriceModifierModelResourcePermission)
		).put(
			"get",
			addAction(
				"VIEW", commercePriceModifier.getCommercePriceModifierId(),
				"getPriceModifier",
				_commercePriceModifierModelResourcePermission)
		).put(
			"update",
			addAction(
				"UPDATE", commercePriceModifier.getCommercePriceModifierId(),
				"patchPriceModifier",
				_commercePriceModifierModelResourcePermission)
		).build();
	}

	private DateConfig _getDisplayDateConfig(Date date, TimeZone timeZone) {
		if (date == null) {
			return new DateConfig(CalendarFactoryUtil.getCalendar(timeZone));
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		return new DateConfig(calendar);
	}

	private DateConfig _getExpirationDateConfig(Date date, TimeZone timeZone) {
		if (date == null) {
			Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
				timeZone);

			expirationCalendar.add(Calendar.MONTH, 1);

			return new DateConfig(expirationCalendar);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		return new DateConfig(calendar);
	}

	private PriceModifier _toPriceModifier(Long commercePriceModifierId)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.getCommercePriceModifier(
				commercePriceModifierId);

		return _priceModifierDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePriceModifier), _dtoConverterRegistry,
				commercePriceModifierId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<PriceModifier> _toPriceModifiers(
			List<CommercePriceModifier> commercePriceModifiers)
		throws Exception {

		List<PriceModifier> priceModifiers = new ArrayList<>();

		for (CommercePriceModifier commercePriceModifier :
				commercePriceModifiers) {

			priceModifiers.add(
				_toPriceModifier(
					commercePriceModifier.getCommercePriceModifierId()));
		}

		return priceModifiers;
	}

	private void _updateNestedResources(
			PriceModifier priceModifier,
			CommercePriceModifier commercePriceModifier)
		throws Exception {

		PriceModifierUtil.upsertCommercePriceModifierRels(
			_assetCategoryLocalService, _commercePricingClassService,
			_cProductLocalService, _commercePriceModifierRelService,
			priceModifier, commercePriceModifier, _serviceContextHelper);
	}

	private CommercePriceModifier _updatePriceModifier(
			CommercePriceModifier commercePriceModifier,
			PriceModifier priceModifier)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		DateConfig displayDateConfig = _getDisplayDateConfig(
			priceModifier.getDisplayDate(), serviceContext.getTimeZone());

		DateConfig expirationDateConfig = _getExpirationDateConfig(
			priceModifier.getExpirationDate(), serviceContext.getTimeZone());

		commercePriceModifier =
			_commercePriceModifierService.updateCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId(),
				commercePriceModifier.getGroupId(), priceModifier.getTitle(),
				priceModifier.getTarget(), priceModifier.getPriceListId(),
				priceModifier.getModifierType(),
				priceModifier.getModifierAmount(), priceModifier.getPriority(),
				GetterUtil.getBoolean(priceModifier.getActive(), true),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				GetterUtil.getBoolean(priceModifier, true), serviceContext);

		// Update nested resources

		_updateNestedResources(priceModifier, commercePriceModifier);

		return commercePriceModifier;
	}

	private CommercePriceModifier _upsertCommercePriceModifier(
			CommercePriceList commercePriceList, PriceModifier priceModifier)
		throws Exception {

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		DateConfig displayDateConfig = _getDisplayDateConfig(
			priceModifier.getDisplayDate(), serviceContext.getTimeZone());

		DateConfig expirationDateConfig = _getExpirationDateConfig(
			priceModifier.getExpirationDate(), serviceContext.getTimeZone());

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.upsertCommercePriceModifier(
				serviceContext.getUserId(),
				GetterUtil.getLong(priceModifier.getId()),
				commercePriceList.getGroupId(), priceModifier.getTitle(),
				priceModifier.getTarget(),
				commercePriceList.getCommercePriceListId(),
				priceModifier.getModifierType(),
				priceModifier.getModifierAmount(),
				GetterUtil.get(priceModifier.getPriority(), 0D),
				GetterUtil.getBoolean(priceModifier.getActive(), true),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				priceModifier.getExternalReferenceCode(),
				GetterUtil.getBoolean(priceModifier.getNeverExpire(), true),
				serviceContext);

		// Update nested resources

		_updateNestedResources(priceModifier, commercePriceModifier);

		return commercePriceModifier;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.pricing.model.CommercePriceModifier)"
	)
	private ModelResourcePermission<CommercePriceModifier>
		_commercePriceModifierModelResourcePermission;

	@Reference
	private CommercePriceModifierRelService _commercePriceModifierRelService;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PriceModifierDTOConverter _priceModifierDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}