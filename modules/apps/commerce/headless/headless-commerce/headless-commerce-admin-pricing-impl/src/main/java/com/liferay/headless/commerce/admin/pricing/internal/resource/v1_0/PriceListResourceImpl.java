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

import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.TierPrice;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.PriceListDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.odata.entity.v1_0.PriceListEntityModel;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.PriceListAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.TierPriceUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.PriceListResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
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
	properties = "OSGI-INF/liferay/rest/v1_0/price-list.properties",
	scope = ServiceScope.PROTOTYPE, service = PriceListResource.class
)
public class PriceListResourceImpl
	extends BasePriceListResourceImpl implements EntityModelResource {

	@Override
	public Response deletePriceList(Long id) throws Exception {
		_commercePriceListService.deleteCommercePriceList(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deletePriceListByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		_commercePriceListService.deleteCommercePriceList(
			commercePriceList.getCommercePriceListId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public PriceList getPriceList(Long id) throws Exception {
		return _toPriceList(GetterUtil.getLong(id));
	}

	@Override
	public PriceList getPriceListByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		return _toPriceList(commercePriceList.getCommercePriceListId());
	}

	@Override
	public Page<PriceList> getPriceListsPage(
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommercePriceList.class.getName(), StringPool.BLANK, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toPriceList(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Response patchPriceList(Long id, PriceList priceList)
		throws Exception {

		_updatePriceList(
			_commercePriceListService.getCommercePriceList(id), priceList);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchPriceListByExternalReferenceCode(
			String externalReferenceCode, PriceList priceList)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		_updatePriceList(commercePriceList, priceList);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public PriceList postPriceList(PriceList priceList) throws Exception {
		CommercePriceList commercePriceList = _addOrUpdatePriceList(priceList);

		return _toPriceList(commercePriceList.getCommercePriceListId());
	}

	private CommercePriceList _addOrUpdatePriceList(PriceList priceList)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.getCommerceCatalog(
				priceList.getCatalogId());

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(
				contextCompany.getCompanyId(), priceList.getCurrencyCode());

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		CommercePriceList commercePriceList =
			_commercePriceListService.addOrUpdateCommercePriceList(
				priceList.getExternalReferenceCode(),
				commerceCatalog.getGroupId(), 0L,
				commerceCurrency.getCommerceCurrencyId(), true,
				CommercePriceListConstants.TYPE_PRICE_LIST, 0, false,
				priceList.getName(),
				GetterUtil.get(priceList.getPriority(), 0D),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				GetterUtil.getBoolean(priceList.getNeverExpire(), true),
				serviceContext);

		// Expando

		Map<String, ?> customFields = priceList.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), CommercePriceList.class,
				commercePriceList.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(
			priceList, commercePriceList, serviceContext);
	}

	private PriceList _toPriceList(Long commercePriceListId) throws Exception {
		return _priceListDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private CommercePriceList _updateNestedResources(
			PriceList priceList, CommercePriceList commercePriceList,
			ServiceContext serviceContext)
		throws Exception {

		// Price list account groups

		PriceListAccountGroup[] priceListAccountGroups =
			priceList.getPriceListAccountGroups();

		if (priceListAccountGroups != null) {
			for (PriceListAccountGroup priceListAccountGroup :
					priceListAccountGroups) {

				CommercePriceListCommerceAccountGroupRel
					commercePriceListCommerceAccountGroupRel =
						_commercePriceListCommerceAccountGroupRelService.
							fetchCommercePriceListCommerceAccountGroupRel(
								commercePriceList.getCommercePriceListId(),
								priceListAccountGroup.getAccountGroupId());

				if (commercePriceListCommerceAccountGroupRel != null) {
					continue;
				}

				PriceListAccountGroupUtil.
					addCommercePriceListCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commercePriceListCommerceAccountGroupRelService,
						priceListAccountGroup, commercePriceList,
						serviceContext);
			}
		}

		// Price entries

		PriceEntry[] priceEntries = priceList.getPriceEntries();

		if (priceEntries != null) {
			for (PriceEntry priceEntry : priceEntries) {
				CommercePriceEntry commercePriceEntry =
					_commercePriceEntryService.addOrUpdateCommercePriceEntry(
						priceEntry.getExternalReferenceCode(),
						GetterUtil.getLong(priceEntry.getId()),
						GetterUtil.getLong(priceEntry.getSkuId()), null,
						commercePriceList.getCommercePriceListId(),
						priceEntry.getPrice(),
						(BigDecimal)GetterUtil.get(
							priceEntry.getPromoPrice(), BigDecimal.ZERO),
						priceEntry.getSkuExternalReferenceCode(),
						serviceContext);

				TierPrice[] tierPrices = priceEntry.getTierPrices();

				if (tierPrices != null) {
					for (TierPrice tierPrice : tierPrices) {
						TierPriceUtil.addOrUpdateCommerceTierPriceEntry(
							_commerceTierPriceEntryService, tierPrice,
							commercePriceEntry, serviceContext);
					}
				}
			}
		}

		return commercePriceList;
	}

	private CommercePriceList _updatePriceList(
			CommercePriceList commercePriceList, PriceList priceList)
		throws Exception {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(
				contextCompany.getCompanyId(), priceList.getCurrencyCode());

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		commercePriceList = _commercePriceListService.updateCommercePriceList(
			commercePriceList.getCommercePriceListId(),
			commerceCurrency.getCommerceCurrencyId(), true, 0,
			GetterUtil.get(priceList.getName(), commercePriceList.getName()),
			GetterUtil.get(
				priceList.getPriority(), commercePriceList.getPriority()),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(priceList.getNeverExpire(), true),
			serviceContext);

		// Expando

		Map<String, ?> customFields = priceList.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), CommercePriceList.class,
				commercePriceList.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(
			priceList, commercePriceList, serviceContext);
	}

	private static final EntityModel _entityModel = new PriceListEntityModel();

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private PriceListDTOConverter _priceListDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}