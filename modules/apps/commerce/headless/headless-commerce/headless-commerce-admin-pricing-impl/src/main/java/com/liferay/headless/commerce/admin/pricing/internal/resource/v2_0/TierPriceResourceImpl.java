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

import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.TierPrice;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.TierPriceDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.TierPriceUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.TierPriceResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

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
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/tier-price.properties",
	scope = ServiceScope.PROTOTYPE, service = TierPriceResource.class
)
public class TierPriceResourceImpl extends BaseTierPriceResourceImpl {

	@Override
	public void deleteTierPrice(Long id) throws Exception {
		_commerceTierPriceEntryService.deleteCommerceTierPriceEntry(id);
	}

	@Override
	public void deleteTierPriceByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTierPriceEntry == null) {
			throw new NoSuchTierPriceEntryException(
				"Unable to find Tier Price with externalReferenceCode: " +
					externalReferenceCode);
		}

		_commerceTierPriceEntryService.deleteCommerceTierPriceEntry(
			commerceTierPriceEntry.getCommerceTierPriceEntryId());
	}

	@Override
	public Page<TierPrice> getPriceEntryByExternalReferenceCodeTierPricesPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceEntry == null) {
			throw new NoSuchPriceEntryException(
				"Unable to find Price Entry with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			_commerceTierPriceEntryService.getCommerceTierPriceEntries(
				commercePriceEntry.getCommercePriceEntryId(),
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(
				commercePriceEntry.getCommercePriceEntryId());

		return Page.of(
			_toTierPrices(commerceTierPriceEntries), pagination, totalItems);
	}

	@Override
	public Page<TierPrice> getPriceEntryIdTierPricesPage(
			Long id, Pagination pagination)
		throws Exception {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.fetchCommercePriceEntry(id);

		if (commercePriceEntry == null) {
			throw new NoSuchPriceEntryException(
				"Unable to find Price Entry with id: " + id);
		}

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			_commerceTierPriceEntryService.getCommerceTierPriceEntries(
				id, pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(id);

		return Page.of(
			_toTierPrices(commerceTierPriceEntries), pagination, totalItems);
	}

	@Override
	public TierPrice getTierPrice(Long id) throws Exception {
		return _toTierPrice(GetterUtil.getLong(id));
	}

	@Override
	public TierPrice getTierPriceByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTierPriceEntry == null) {
			throw new NoSuchTierPriceEntryException(
				"Unable to find Tier Price with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _toTierPrice(
			commerceTierPriceEntry.getCommerceTierPriceEntryId());
	}

	@Override
	public Response patchTierPrice(Long id, TierPrice tierPrice)
		throws Exception {

		_updateCommerceTierPriceEntry(
			_commerceTierPriceEntryService.getCommerceTierPriceEntry(id),
			tierPrice);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchTierPriceByExternalReferenceCode(
			String externalReferenceCode, TierPrice tierPrice)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTierPriceEntry == null) {
			throw new NoSuchTierPriceEntryException(
				"Unable to find Tier Price with externalReferenceCode: " +
					externalReferenceCode);
		}

		_updateCommerceTierPriceEntry(commerceTierPriceEntry, tierPrice);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public TierPrice postPriceEntryByExternalReferenceCodeTierPrice(
			String externalReferenceCode, TierPrice tierPrice)
		throws Exception {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceEntry == null) {
			throw new NoSuchPriceEntryException(
				"Unable to find Price Entry with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommerceTierPriceEntry commerceTierPriceEntry =
			TierPriceUtil.upsertCommerceTierPriceEntry(
				_commerceTierPriceEntryService, tierPrice, commercePriceEntry,
				_serviceContextHelper);

		return _toTierPrice(
			commerceTierPriceEntry.getCommerceTierPriceEntryId());
	}

	@Override
	public TierPrice postPriceEntryIdTierPrice(Long id, TierPrice tierPrice)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			TierPriceUtil.upsertCommerceTierPriceEntry(
				_commerceTierPriceEntryService, tierPrice,
				_commercePriceEntryService.getCommercePriceEntry(id),
				_serviceContextHelper);

		return _toTierPrice(
			commerceTierPriceEntry.getCommerceTierPriceEntryId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceTierPriceEntry.getCommerceTierPriceEntryId(),
				"deleteTierPrice",
				_commerceTierPriceEntryModelResourcePermission)
		).put(
			"get",
			addAction(
				"VIEW", commerceTierPriceEntry.getCommerceTierPriceEntryId(),
				"getTierPrice", _commerceTierPriceEntryModelResourcePermission)
		).put(
			"update",
			addAction(
				"UPDATE", commerceTierPriceEntry.getCommerceTierPriceEntryId(),
				"patchTierPrice",
				_commerceTierPriceEntryModelResourcePermission)
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

	private TierPrice _toTierPrice(Long commerceTierPriceEntryId)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		return _tierPriceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceTierPriceEntry), _dtoConverterRegistry,
				commerceTierPriceEntryId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<TierPrice> _toTierPrices(
			List<CommerceTierPriceEntry> commerceTierPriceEntries)
		throws Exception {

		List<TierPrice> tierPrices = new ArrayList<>();

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntries) {

			tierPrices.add(
				_toTierPrice(
					commerceTierPriceEntry.getCommerceTierPriceEntryId()));
		}

		return tierPrices;
	}

	private CommerceTierPriceEntry _updateCommerceTierPriceEntry(
			CommerceTierPriceEntry commerceTierPriceEntry, TierPrice tierPrice)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		DateConfig displayDateConfig = _getDisplayDateConfig(
			tierPrice.getDisplayDate(), serviceContext.getTimeZone());

		DateConfig expirationDateConfig = _getExpirationDateConfig(
			tierPrice.getExpirationDate(), serviceContext.getTimeZone());

		return _commerceTierPriceEntryService.updateCommerceTierPriceEntry(
			commerceTierPriceEntry.getCommerceTierPriceEntryId(),
			BigDecimal.valueOf(tierPrice.getPrice()),
			GetterUtil.get(
				tierPrice.getMinimumQuantity(),
				commerceTierPriceEntry.getMinQuantity()),
			commercePriceEntry.isBulkPricing(),
			GetterUtil.getBoolean(tierPrice.getDiscountDiscovery(), true),
			tierPrice.getDiscountLevel1(), tierPrice.getDiscountLevel2(),
			tierPrice.getDiscountLevel3(), tierPrice.getDiscountLevel4(),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(tierPrice.getNeverExpire(), true),
			serviceContext);
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommerceTierPriceEntry)"
	)
	private ModelResourcePermission<CommerceTierPriceEntry>
		_commerceTierPriceEntryModelResourcePermission;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private TierPriceDTOConverter _tierPriceDTOConverter;

}