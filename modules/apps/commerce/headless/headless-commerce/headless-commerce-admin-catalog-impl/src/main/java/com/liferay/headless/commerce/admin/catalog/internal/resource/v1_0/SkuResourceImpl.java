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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeRegistry;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuSubscriptionConfiguration;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuVirtualSettings;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0.SkuHelper;
import com.liferay.headless.commerce.admin.catalog.internal.odata.entity.v1_0.SkuEntityModel;
import com.liferay.headless.commerce.admin.catalog.internal.util.DateConfigUtil;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.SkuUtil;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.SkuVirtualSettingsUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.Serializable;

import java.math.BigDecimal;

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
	properties = "OSGI-INF/liferay/rest/v1_0/sku.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, SkuResource.class}
)
@CTAware
public class SkuResourceImpl
	extends BaseSkuResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteSku(Long id) throws Exception {
		_cpInstanceService.deleteCPInstance(id);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deleteSkuByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find SKU with external reference code " +
					externalReferenceCode);
		}

		_cpInstanceService.deleteCPInstance(cpInstance.getCPInstanceId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<Sku> getProductByExternalReferenceCodeSkusPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _skuHelper.getSkusPage(
			cpDefinition.getCProductId(),
			contextAcceptLanguage.getPreferredLocale(), pagination);
	}

	@NestedField(parentClass = Product.class, value = "skus")
	@Override
	public Page<Sku> getProductIdSkusPage(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		return _skuHelper.getSkusPage(
			id, contextAcceptLanguage.getPreferredLocale(), pagination);
	}

	@Override
	public Sku getSku(Long id) throws Exception {
		return _toSku(GetterUtil.getLong(id));
	}

	@Override
	public Sku getSkuByExternalReferenceCode(String externalReferenceCode)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find SKU with external reference code " +
					externalReferenceCode);
		}

		return _toSku(cpInstance.getCPInstanceId());
	}

	@Override
	public Page<Sku> getSkusPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _skuHelper.getSkusPage(
			contextCompany.getCompanyId(), search, filter, pagination, sorts,
			document -> _toSku(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Sku patchSku(Long id, Sku sku) throws Exception {
		CPInstance cpInstance = _cpInstanceService.getCPInstance(id);

		String externalReferenceCode = sku.getExternalReferenceCode();

		if (Validator.isNotNull(externalReferenceCode)) {
			cpInstance = _cpInstanceService.updateExternalReferenceCode(
				cpInstance.getCPInstanceId(), externalReferenceCode);
		}

		_updateSKU(cpInstance, sku);

		return _toSku(id);
	}

	@Override
	public Sku patchSkuByExternalReferenceCode(
			String externalReferenceCode, Sku sku)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find SKU with external reference code " +
					externalReferenceCode);
		}

		return _updateSKU(cpInstance, sku);
	}

	@Override
	public Sku postProductByExternalReferenceCodeSku(
			String externalReferenceCode, Sku sku)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addOrUpdateSKU(cpDefinition, sku);
	}

	@Override
	public Sku postProductIdSku(Long id, Sku sku) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _addOrUpdateSKU(cpDefinition, sku);
	}

	private Sku _addOrUpdateSKU(CPDefinition cpDefinition, Sku sku)
		throws Exception {

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			cpDefinition.getGroupId());

		Map<String, Serializable> expandoBridgeAttributes =
			_getExpandoBridgeAttributes(sku);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		CPInstance cpInstance = SkuUtil.addOrUpdateCPInstance(
			_cpInstanceService, sku, cpDefinition,
			_cpDefinitionOptionRelService, _cpDefinitionOptionValueRelService,
			serviceContext);

		serviceContext.setExpandoBridgeAttributes(null);

		SkuUtil.updateCommercePriceEntries(
			_commercePriceEntryLocalService, _commercePriceListLocalService,
			_configurationProvider, cpInstance,
			(BigDecimal)GetterUtil.get(sku.getPrice(), cpInstance.getPrice()),
			(BigDecimal)GetterUtil.get(
				sku.getPromoPrice(), cpInstance.getPromoPrice()),
			serviceContext);

		_updateNestedResources(sku, cpInstance, serviceContext);

		return _toSku(cpInstance.getCPInstanceId());
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(Sku sku) {
		return CustomFieldsUtil.toMap(
			CPInstance.class.getName(), contextCompany.getCompanyId(),
			sku.getCustomFields(), contextAcceptLanguage.getPreferredLocale());
	}

	private Sku _toSku(Long cpInstanceId) throws Exception {
		return _skuDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, cpInstanceId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private CPInstance _updateNestedResources(
			Sku sku, CPInstance cpInstance, ServiceContext serviceContext)
		throws Exception {

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CPType cpType = _cpTypeRegistry.getCPType(
			cpDefinition.getProductTypeName());

		if (cpType == null) {
			return cpInstance;
		}

		// Virtual

		SkuVirtualSettings skuVirtualSettings = sku.getSkuVirtualSettings();

		if (skuVirtualSettings != null) {
			if (!VirtualCPTypeConstants.NAME.equals(cpType.getName())) {
				throw new CPDefinitionProductTypeNameException();
			}

			SkuVirtualSettingsUtil.addOrUpdateSkuVirtualSettings(
				cpInstance, skuVirtualSettings,
				_cpDefinitionVirtualSettingService, _uniqueFileNameProvider,
				serviceContext);
		}

		return cpInstance;
	}

	private Sku _updateSKU(CPInstance cpInstance, Sku sku) throws Exception {
		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			cpInstance.getGroupId());

		Map<String, Serializable> expandoBridgeAttributes =
			_getExpandoBridgeAttributes(sku);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		long replacementCProductId = cpInstance.getReplacementCProductId();
		String replacementCPInstanceUuid =
			cpInstance.getReplacementCPInstanceUuid();

		if (GetterUtil.getBoolean(sku.getDiscontinued())) {
			CPInstance discontinuedCPInstance = null;

			if (Validator.isNotNull(
					sku.getReplacementSkuExternalReferenceCode())) {

				discontinuedCPInstance =
					_cpInstanceService.fetchByExternalReferenceCode(
						sku.getReplacementSkuExternalReferenceCode(),
						contextCompany.getCompanyId());
			}

			if ((discontinuedCPInstance == null) &&
				(GetterUtil.getLong(sku.getReplacementSkuId()) > 0)) {

				discontinuedCPInstance = _cpInstanceService.fetchCPInstance(
					sku.getReplacementSkuId());
			}

			if (discontinuedCPInstance != null) {
				CPDefinition cpDefinition =
					discontinuedCPInstance.getCPDefinition();

				replacementCProductId = cpDefinition.getCProductId();

				replacementCPInstanceUuid =
					discontinuedCPInstance.getCPInstanceUuid();
			}
		}

		int discontinuedDateMonth = 0;
		int discontinuedDateDay = 0;
		int discontinuedDateYear = 0;

		if (cpInstance.getDiscontinuedDate() != null) {
			DateConfig discontinuedDateConfig = new DateConfig(
				DateConfigUtil.convertDateToCalendar(
					GetterUtil.getDate(
						sku.getDiscontinuedDate(),
						DateFormatFactoryUtil.getDate(
							contextAcceptLanguage.getPreferredLocale(),
							contextUser.getTimeZone()),
						cpInstance.getDiscontinuedDate())));

			discontinuedDateMonth = discontinuedDateConfig.getMonth();
			discontinuedDateDay = discontinuedDateConfig.getDay();
			discontinuedDateYear = discontinuedDateConfig.getYear();
		}

		DateConfig displayDateConfig = new DateConfig(
			DateConfigUtil.convertDateToCalendar(
				GetterUtil.getDate(
					sku.getDisplayDate(),
					DateFormatFactoryUtil.getDate(
						contextAcceptLanguage.getPreferredLocale(),
						contextUser.getTimeZone()),
					cpInstance.getDisplayDate())));

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;

		if (cpInstance.getExpirationDate() != null) {
			DateConfig expirationDateConfig = new DateConfig(
				DateConfigUtil.convertDateToCalendar(
					GetterUtil.getDate(
						sku.getExpirationDate(),
						DateFormatFactoryUtil.getDate(
							contextAcceptLanguage.getPreferredLocale(),
							contextUser.getTimeZone()),
						cpInstance.getExpirationDate())));

			expirationDateMonth = expirationDateConfig.getMonth();
			expirationDateDay = expirationDateConfig.getDay();
			expirationDateYear = expirationDateConfig.getYear();
			expirationDateHour = expirationDateConfig.getHour();
			expirationDateMinute = expirationDateConfig.getMinute();
		}

		SkuSubscriptionConfiguration skuSubscriptionConfiguration =
			sku.getSkuSubscriptionConfiguration();

		boolean deliverySubscriptionEnable =
			cpInstance.isDeliverySubscriptionEnabled();
		int deliverySubscriptionLength =
			cpInstance.getDeliverySubscriptionLength();
		long deliverySubscriptionMaxSubscriptionCycles =
			cpInstance.getDeliveryMaxSubscriptionCycles();
		UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties =
			cpInstance.getDeliverySubscriptionTypeSettingsUnicodeProperties();
		String deliverySubscriptionTypeValue =
			cpInstance.getDeliverySubscriptionType();
		boolean overrideSubscriptionInfo =
			cpInstance.isOverrideSubscriptionInfo();
		boolean subscriptionEnable = cpInstance.isSubscriptionEnabled();
		int subscriptionLength = cpInstance.getSubscriptionLength();
		long subscriptionMaxSubscriptionCycles =
			cpInstance.getMaxSubscriptionCycles();
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties =
			cpInstance.getSubscriptionTypeSettingsUnicodeProperties();
		String subscriptionTypeValue = cpInstance.getSubscriptionType();

		if (skuSubscriptionConfiguration != null) {
			deliverySubscriptionEnable = GetterUtil.getBoolean(
				skuSubscriptionConfiguration.getDeliverySubscriptionEnable(),
				deliverySubscriptionEnable);
			deliverySubscriptionLength = GetterUtil.getInteger(
				skuSubscriptionConfiguration.getDeliverySubscriptionLength(),
				deliverySubscriptionLength);

			if (Validator.isNotNull(
					skuSubscriptionConfiguration.
						getDeliverySubscriptionTypeSettings())) {

				deliverySubscriptionTypeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.create(
						skuSubscriptionConfiguration.
							getDeliverySubscriptionTypeSettings(),
						true
					).build();
			}

			SkuSubscriptionConfiguration.DeliverySubscriptionType
				deliverySubscriptionType =
					skuSubscriptionConfiguration.getDeliverySubscriptionType();

			if (deliverySubscriptionType != null) {
				deliverySubscriptionTypeValue =
					deliverySubscriptionType.getValue();
			}

			deliverySubscriptionMaxSubscriptionCycles = GetterUtil.getLong(
				skuSubscriptionConfiguration.
					getDeliverySubscriptionNumberOfLength(),
				deliverySubscriptionMaxSubscriptionCycles);
			overrideSubscriptionInfo = GetterUtil.getBoolean(
				skuSubscriptionConfiguration.getOverrideSubscriptionInfo(),
				overrideSubscriptionInfo);
			subscriptionEnable = GetterUtil.getBoolean(
				skuSubscriptionConfiguration.getEnable(), subscriptionEnable);
			subscriptionLength = GetterUtil.getInteger(
				skuSubscriptionConfiguration.getLength(), subscriptionLength);
			subscriptionMaxSubscriptionCycles = GetterUtil.getLong(
				skuSubscriptionConfiguration.getNumberOfLength(),
				subscriptionMaxSubscriptionCycles);

			if (Validator.isNotNull(
					skuSubscriptionConfiguration.
						getSubscriptionTypeSettings())) {

				subscriptionTypeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.create(
						skuSubscriptionConfiguration.
							getSubscriptionTypeSettings(),
						true
					).build();
			}

			SkuSubscriptionConfiguration.SubscriptionType subscriptionType =
				skuSubscriptionConfiguration.getSubscriptionType();

			if (subscriptionType != null) {
				subscriptionTypeValue = subscriptionType.getValue();
			}
		}

		cpInstance = _cpInstanceService.updateCPInstance(
			cpInstance.getExternalReferenceCode(), cpInstance.getCPInstanceId(),
			GetterUtil.get(sku.getSku(), cpInstance.getSku()),
			GetterUtil.get(sku.getGtin(), cpInstance.getGtin()),
			GetterUtil.get(
				sku.getManufacturerPartNumber(),
				cpInstance.getManufacturerPartNumber()),
			GetterUtil.get(sku.getPurchasable(), cpInstance.isPurchasable()),
			GetterUtil.get(sku.getWidth(), cpInstance.getWidth()),
			GetterUtil.get(sku.getHeight(), cpInstance.getHeight()),
			GetterUtil.get(sku.getDepth(), cpInstance.getDepth()),
			GetterUtil.get(sku.getWeight(), cpInstance.getWeight()),
			(BigDecimal)GetterUtil.get(sku.getPrice(), cpInstance.getPrice()),
			(BigDecimal)GetterUtil.get(
				sku.getPromoPrice(), cpInstance.getPromoPrice()),
			(BigDecimal)GetterUtil.get(sku.getCost(), cpInstance.getCost()),
			GetterUtil.get(sku.getPublished(), cpInstance.isPublished()),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute,
			GetterUtil.get(
				sku.getNeverExpire(),
				(cpInstance.getExpirationDate() == null) ? true : false),
			overrideSubscriptionInfo, subscriptionEnable, subscriptionLength,
			subscriptionTypeValue, subscriptionTypeSettingsUnicodeProperties,
			subscriptionMaxSubscriptionCycles, deliverySubscriptionEnable,
			deliverySubscriptionLength, deliverySubscriptionTypeValue,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliverySubscriptionMaxSubscriptionCycles,
			GetterUtil.getString(sku.getUnspsc(), cpInstance.getUnspsc()),
			GetterUtil.getBoolean(
				sku.getDiscontinued(), cpInstance.isDiscontinued()),
			replacementCPInstanceUuid, replacementCProductId,
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear,
			serviceContext);

		serviceContext.setExpandoBridgeAttributes(null);

		SkuUtil.updateCommercePriceEntries(
			_commercePriceEntryLocalService, _commercePriceListLocalService,
			_configurationProvider, cpInstance,
			(BigDecimal)GetterUtil.get(sku.getPrice(), cpInstance.getPrice()),
			(BigDecimal)GetterUtil.get(
				sku.getPromoPrice(), cpInstance.getPromoPrice()),
			serviceContext);

		_updateNestedResources(sku, cpInstance, serviceContext);

		return _toSku(cpInstance.getCPInstanceId());
	}

	private static final EntityModel _entityModel = new SkuEntityModel();

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CPTypeRegistry _cpTypeRegistry;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(target = DTOConverterConstants.SKU_DTO_CONVERTER)
	private DTOConverter<CPInstance, Sku> _skuDTOConverter;

	@Reference
	private SkuHelper _skuHelper;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}