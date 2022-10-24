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

package com.liferay.site.initializer.extender.internal;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.initializer.util.CPDefinitionsImporter;
import com.liferay.commerce.initializer.util.CPOptionsImporter;
import com.liferay.commerce.initializer.util.CPSpecificationOptionsImporter;
import com.liferay.commerce.initializer.util.CommerceInventoryWarehousesImporter;
import com.liferay.commerce.initializer.util.PortletSettingsImporter;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.site.initializer.extender.internal.util.SiteInitializerUtil;

import java.math.BigDecimal;

import java.net.URL;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = CommerceSiteInitializer.class)
public class CommerceSiteInitializer {

	public void addCPDefinitions(
			Bundle bundle, Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception {

		Channel channel = _addOrUpdateCommerceChannel(
			serviceContext, servletContext);

		if (channel == null) {
			return;
		}

		_addOrUpdateCommerceCatalogs(
			bundle, channel,
			_addCommerceInventoryWarehouses(serviceContext, servletContext),
			serviceContext, servletContext);
		_addCommerceNotificationTemplates(
			bundle, channel.getId(), documentsStringUtilReplaceValues,
			objectDefinitionIdsStringUtilReplaceValues, serviceContext,
			servletContext);
	}

	public void addPortletSettings(
			ClassLoader classLoader, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		String resourcePath = "/site-initializer/portlet-settings.json";

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return;
		}

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		_portletSettingsImporter.importPortletSettings(
			JSONFactoryUtil.createJSONArray(json), classLoader,
			"/site-initializer/portlet-settings/",
			serviceContext.getScopeGroupId(), group.getGroupId(),
			serviceContext.getUserId());
	}

	public long getCommerceChannelGroupId(long siteGroupId) {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				siteGroupId);

		return commerceChannel.getGroupId();
	}

	public String getCommerceOrderClassName() {
		return CommerceOrder.class.getName();
	}

	private List<CommerceInventoryWarehouse> _addCommerceInventoryWarehouses(
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception {

		return _commerceInventoryWarehousesImporter.
			importCommerceInventoryWarehouses(
				JSONFactoryUtil.createJSONArray(
					SiteInitializerUtil.read(
						"/site-initializer/commerce-inventory-warehouses.json",
						servletContext)),
				serviceContext.getScopeGroupId(), serviceContext.getUserId());
	}

	private void _addCommerceNotificationTemplate(
			Bundle bundle, long commerceChannelId,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			String resourcePath, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			resourcePath + "commerce-notification-template.json",
			servletContext);

		if (Validator.isNull(json)) {
			return;
		}

		JSONObject commerceNotificationTemplateJSONObject =
			JSONFactoryUtil.createJSONObject(json);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(commerceChannelId);

		JSONObject bodyJSONObject = _jsonFactory.createJSONObject();

		Enumeration<URL> enumeration = bundle.findEntries(
			resourcePath, "*.html", false);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				bodyJSONObject.put(
					FileUtil.getShortFileName(
						FileUtil.stripExtension(url.getPath())),
					StringUtil.replace(
						StringUtil.read(url.openStream()), "[$", "$]",
						documentsStringUtilReplaceValues));
			}
		}

		_commerceNotificationTemplateLocalService.
			addCommerceNotificationTemplate(
				serviceContext.getUserId(), commerceChannel.getGroupId(),
				commerceNotificationTemplateJSONObject.getString("name"),
				commerceNotificationTemplateJSONObject.getString("description"),
				commerceNotificationTemplateJSONObject.getString("from"),
				SiteInitializerUtil.toMap(
					commerceNotificationTemplateJSONObject.getString(
						"fromName")),
				commerceNotificationTemplateJSONObject.getString("to"),
				commerceNotificationTemplateJSONObject.getString("cc"),
				commerceNotificationTemplateJSONObject.getString("bcc"),
				StringUtil.replace(
					commerceNotificationTemplateJSONObject.getString("type"),
					"[$", "$]", objectDefinitionIdsStringUtilReplaceValues),
				commerceNotificationTemplateJSONObject.getBoolean("enabled"),
				SiteInitializerUtil.toMap(
					commerceNotificationTemplateJSONObject.getString(
						"subject")),
				SiteInitializerUtil.toMap(bodyJSONObject.toString()),
				serviceContext);
	}

	private void _addCommerceNotificationTemplates(
			Bundle bundle, long commerceChannelId,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			"/site-initializer/commerce-notification-templates");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			_addCommerceNotificationTemplate(
				bundle, commerceChannelId, documentsStringUtilReplaceValues,
				objectDefinitionIdsStringUtilReplaceValues, resourcePath,
				serviceContext, servletContext);
		}
	}

	private void _addCommerceProductSpecifications(
			String resourcePath, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		ProductSpecificationResource.Builder
			productSpecificationResourceBuilder =
				_productSpecificationResourceFactory.create();

		ProductSpecificationResource productSpecificationResource =
			productSpecificationResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		_cpSpecificationOptionsImporter.importCPSpecificationOptions(
			jsonArray, serviceContext.getScopeGroupId(),
			serviceContext.getUserId());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						jsonObject.getString(
							"cpDefinitionExternalReferenceCode"),
						serviceContext.getCompanyId());

			if (cpDefinition == null) {
				continue;
			}

			ProductSpecification productSpecification =
				new ProductSpecification() {
					{
						productId = cpDefinition.getCPDefinitionId();
						specificationKey = jsonObject.getString("key");
						value = JSONUtil.toStringMap(
							jsonObject.getJSONObject(
								"productSpecificationValue"));
					}
				};

			productSpecificationResource.postProductIdProductSpecification(
				cpDefinition.getCPDefinitionId(), productSpecification);
		}
	}

	private void _addCPDefinitions(
			String assetVocabularyName, Bundle bundle, Catalog catalog,
			Channel channel,
			List<CommerceInventoryWarehouse> commerceInventoryWarehouses,
			String resourcePath, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return;
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		Group commerceCatalogGroup =
			_commerceCatalogLocalService.getCommerceCatalogGroup(
				catalog.getId());

		List<CPDefinition> cpDefinitions =
			_cpDefinitionsImporter.importCPDefinitions(
				JSONFactoryUtil.createJSONArray(json), assetVocabularyName,
				commerceCatalogGroup.getGroupId(), channel.getId(),
				ListUtil.toLongArray(
					commerceInventoryWarehouses,
					CommerceInventoryWarehouse.
						COMMERCE_INVENTORY_WAREHOUSE_ID_ACCESSOR),
				bundleWiring.getClassLoader(),
				StringUtil.replace(resourcePath, ".json", "/"),
				serviceContext.getScopeGroupId(), serviceContext.getUserId());

		if (ListUtil.isEmpty(cpDefinitions)) {
			return;
		}

		for (CPDefinition cpDefinition : cpDefinitions) {
			List<CPInstance> cpInstances = cpDefinition.getCPInstances();

			if (ListUtil.isEmpty(cpInstances)) {
				continue;
			}

			for (CPInstance cpInstance : cpInstances) {
				_addOrUpdateCommercePriceEntries(
					cpDefinition, cpInstance, serviceContext);
			}
		}
	}

	private void _addCPInstanceSubscriptions(
			String resourcePath, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return;
		}

		ProductOptionResource.Builder productOptionResourceBuilder =
			_productOptionResourceFactory.create();

		ProductOptionResource productOptionResource =
			productOptionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject subscriptionPropertiesJSONObject =
				jsonArray.getJSONObject(i);

			CPOption cpOption = _cpOptionLocalService.fetchCPOption(
				serviceContext.getCompanyId(),
				subscriptionPropertiesJSONObject.getString("optionKey"));

			if (cpOption == null) {
				continue;
			}

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						subscriptionPropertiesJSONObject.getString(
							"cpDefinitionExternalReferenceCode"),
						serviceContext.getCompanyId());

			if (cpDefinition == null) {
				continue;
			}

			productOptionResource.postProductIdProductOptionsPage(
				cpDefinition.getCProductId(),
				new ProductOption[] {
					new ProductOption() {
						{
							facetable = cpOption.isFacetable();
							fieldType = cpOption.getDDMFormFieldTypeName();
							key = cpOption.getKey();
							name = LocalizedMapUtil.getI18nMap(
								cpOption.getNameMap());
							optionId = cpOption.getCPOptionId();
							required = cpOption.isRequired();
							skuContributor = cpOption.isSkuContributor();
						}
					}
				});

			_cpInstanceLocalService.buildCPInstances(
				cpDefinition.getCPDefinitionId(), serviceContext);

			JSONArray cpInstancePropertiesJSONArray =
				subscriptionPropertiesJSONObject.getJSONArray(
					"cpInstanceProperties");

			if (cpInstancePropertiesJSONArray == null) {
				continue;
			}

			for (int j = 0; j < cpInstancePropertiesJSONArray.length(); j++) {
				JSONObject cpInstancePropertiesJSONObject =
					cpInstancePropertiesJSONArray.getJSONObject(j);

				_updateCPInstanceProperties(
					cpDefinition, cpInstancePropertiesJSONObject,
					serviceContext);
			}

			_addOrUpdateCommercePriceEntries(
				cpDefinition,
				_cpInstanceLocalService.fetchByExternalReferenceCode(
					subscriptionPropertiesJSONObject.getString(
						"cpDefinitionExternalReferenceCode"),
					serviceContext.getCompanyId()),
				serviceContext);
		}
	}

	private void _addCPOptions(
			Catalog catalog, String resourcePath, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return;
		}

		Group commerceCatalogGroup =
			_commerceCatalogLocalService.getCommerceCatalogGroup(
				catalog.getId());

		_cpOptionsImporter.importCPOptions(
			JSONFactoryUtil.createJSONArray(json),
			commerceCatalogGroup.getGroupId(), serviceContext.getUserId());
	}

	private void _addDefaultCPDisplayLayout(
			Channel channel, String resourcePath, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception {

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			serviceContext.getScopeGroupId(),
			jsonObject.getBoolean("privateLayout"),
			jsonObject.getString("friendlyURL"));

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to create a default product commerce product " +
						"display layout from JSON: " + json);
			}

			return;
		}

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(channel.getId());

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue("productLayoutUuid", layout.getUuid());

		modifiableSettings.store();
	}

	private void _addModelResourcePermissions(
			String className, String primKey, String resourcePath,
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception {

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			_resourcePermissionLocalService.addModelResourcePermissions(
				serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
				serviceContext.getUserId(), className, primKey,
				ModelPermissionsFactory.create(
					HashMapBuilder.put(
						jsonObject.getString("roleName"),
						ArrayUtil.toStringArray(
							jsonObject.getJSONArray("actionIds"))
					).build(),
					null));
		}
	}

	private void _addOrUpdateCommerceCatalogs(
			Bundle bundle, Channel channel,
			List<CommerceInventoryWarehouse> commerceInventoryWarehouses,
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			"/site-initializer/commerce-catalogs");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		CatalogResource.Builder builder = _catalogResourceFactory.create();

		CatalogResource catalogResource = builder.user(
			serviceContext.fetchUser()
		).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(".options.json") ||
				resourcePath.endsWith(".products.json") ||
				resourcePath.endsWith(".products.specifications.json") ||
				resourcePath.endsWith(
					".products.subscriptions.properties.json") ||
				!resourcePath.endsWith(".json")) {

				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, servletContext);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			String assetVocabularyName = jsonObject.getString(
				"assetVocabularyName");

			jsonObject.remove("assetVocabularyName");

			Catalog catalog = Catalog.toDTO(String.valueOf(jsonObject));

			if (catalog == null) {
				_log.error(
					"Unable to transform commerce catalog from JSON: " + json);

				continue;
			}

			catalog = catalogResource.postCatalog(catalog);

			_addCPOptions(
				catalog,
				StringUtil.replaceLast(resourcePath, ".json", ".options.json"),
				serviceContext, servletContext);
			_addCPDefinitions(
				assetVocabularyName, bundle, catalog, channel,
				commerceInventoryWarehouses,
				StringUtil.replaceLast(resourcePath, ".json", ".products.json"),
				serviceContext, servletContext);

			_addCommerceProductSpecifications(
				StringUtil.replaceLast(
					resourcePath, ".json", ".products.specifications.json"),
				serviceContext, servletContext);

			_addCPInstanceSubscriptions(
				StringUtil.replaceLast(
					resourcePath, ".json",
					".products.subscriptions.properties.json"),
				serviceContext, servletContext);
		}
	}

	private Channel _addOrUpdateCommerceChannel(
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception {

		String resourcePath = "/site-initializer/commerce-channel.json";

		String json = SiteInitializerUtil.read(resourcePath, servletContext);

		if (json == null) {
			return null;
		}

		ChannelResource.Builder channelResourceBuilder =
			_channelResourceFactory.create();

		ChannelResource channelResource = channelResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		jsonObject.put("siteGroupId", serviceContext.getScopeGroupId());

		Channel channel = Channel.toDTO(jsonObject.toString());

		if (channel == null) {
			_log.error(
				"Unable to transform commerce channel from JSON: " + json);

			return null;
		}

		Page<Channel> channelsPage = channelResource.getChannelsPage(
			null,
			channelResource.toFilter(
				StringBundler.concat(
					"siteGroupId eq '", serviceContext.getScopeGroupId(), "'")),
			null, null);

		Channel existingChannel = channelsPage.fetchFirstItem();

		if (existingChannel == null) {
			channel = channelResource.postChannel(channel);
		}
		else {
			channel = channelResource.putChannel(
				existingChannel.getId(), channel);
		}

		_addDefaultCPDisplayLayout(
			channel,
			StringUtil.replaceLast(
				resourcePath, ".json", ".default-cp-display-layout.json"),
			serviceContext, servletContext);
		_addModelResourcePermissions(
			CommerceChannel.class.getName(), String.valueOf(channel.getId()),
			StringUtil.replaceLast(
				resourcePath, ".json", ".model-resource-permissions.json"),
			serviceContext, servletContext);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				serviceContext.getScopeGroupId(),
				CommerceAccountConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"commerceSiteType",
			String.valueOf(CommerceAccountConstants.SITE_TYPE_B2C));

		modifiableSettings.store();

		_commerceAccountRoleHelper.checkCommerceAccountRoles(serviceContext);

		_commerceCurrencyLocalService.importDefaultValues(serviceContext);

		_cpMeasurementUnitLocalService.importDefaultValues(serviceContext);

		return channel;
	}

	private void _addOrUpdateCommercePriceEntries(
			CPDefinition cpDefinition, CPInstance cpInstance,
			ServiceContext serviceContext)
		throws Exception {

		_addOrUpdateCommercePriceEntry(
			cpDefinition, cpInstance, serviceContext,
			CommercePriceListConstants.TYPE_PRICE_LIST);
		_addOrUpdateCommercePriceEntry(
			cpDefinition, cpInstance, serviceContext,
			CommercePriceListConstants.TYPE_PROMOTION);
	}

	private void _addOrUpdateCommercePriceEntry(
			CPDefinition cpDefinition, CPInstance cpInstance,
			ServiceContext serviceContext, String type)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.
				getCatalogBaseCommercePriceListByType(
					cpInstance.getGroupId(), type);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		BigDecimal price = cpInstance.getPrice();

		if (CommercePriceListConstants.TYPE_PROMOTION.equals(
				commercePriceList.getType())) {

			price = cpInstance.getPromoPrice();
		}

		if (commercePriceEntry == null) {
			_commercePriceEntryLocalService.addCommercePriceEntry(
				cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), price,
				BigDecimal.ZERO, serviceContext);
		}
		else {
			_commercePriceEntryLocalService.updateCommercePriceEntry(
				commercePriceEntry.getCommercePriceEntryId(), price,
				BigDecimal.ZERO, serviceContext);
		}
	}

	private void _updateCPInstanceProperties(
			CPDefinition cpDefinition,
			JSONObject cpInstancePropertiesJSONObject,
			ServiceContext serviceContext)
		throws Exception {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpDefinition.getCPDefinitionId(),
			cpInstancePropertiesJSONObject.getString("cpInstanceSku"));

		if (cpInstance == null) {
			return;
		}

		String propertyType = cpInstancePropertiesJSONObject.getString(
			"propertyType");

		if (StringUtil.equals(propertyType, "CREATE_SUBSCRIPTION")) {
			JSONObject subscriptionTypeSettingsJSONObject =
				cpInstancePropertiesJSONObject.getJSONObject(
					"subscriptionTypeSettings");

			_cpInstanceLocalService.updateSubscriptionInfo(
				cpInstance.getCPInstanceId(),
				cpInstancePropertiesJSONObject.getBoolean(
					"overrideSubscriptionInfo"),
				cpInstancePropertiesJSONObject.getBoolean(
					"subscriptionEnabled"),
				cpInstancePropertiesJSONObject.getInt("subscriptionLength"),
				cpInstancePropertiesJSONObject.getString("subscriptionType"),
				UnicodePropertiesBuilder.create(
					JSONUtil.toStringMap(subscriptionTypeSettingsJSONObject),
					true
				).build(),
				cpInstancePropertiesJSONObject.getLong("maxSubscriptionCycles"),
				cpInstancePropertiesJSONObject.getBoolean(
					"deliverySubscriptionEnabled"),
				cpInstancePropertiesJSONObject.getInt(
					"deliverySubscriptionLength"),
				cpInstancePropertiesJSONObject.getString(
					"deliverySubscriptionType"),
				new UnicodeProperties(),
				cpInstancePropertiesJSONObject.getLong(
					"deliveryMaxSubscriptionCycles"));
		}
		else if (StringUtil.equals(propertyType, "UPDATE_PRICE")) {
			cpInstance.setPrice(
				BigDecimal.valueOf(
					cpInstancePropertiesJSONObject.getLong("skuPrice")));
			cpInstance.setPromoPrice(
				BigDecimal.valueOf(
					cpInstancePropertiesJSONObject.getLong("skuPromoPrice")));

			_cpInstanceLocalService.updateCPInstance(cpInstance);
		}

		_addOrUpdateCommercePriceEntries(
			cpDefinition, cpInstance, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSiteInitializer.class);

	@Reference
	private CatalogResource.Factory _catalogResourceFactory;

	@Reference
	private ChannelResource.Factory _channelResourceFactory;

	@Reference
	private CommerceAccountRoleHelper _commerceAccountRoleHelper;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceInventoryWarehousesImporter
		_commerceInventoryWarehousesImporter;

	@Reference
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionsImporter _cpDefinitionsImporter;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference
	private CPOptionsImporter _cpOptionsImporter;

	@Reference
	private CPSpecificationOptionsImporter _cpSpecificationOptionsImporter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletSettingsImporter _portletSettingsImporter;

	@Reference
	private ProductOptionResource.Factory _productOptionResourceFactory;

	@Reference
	private ProductSpecificationResource.Factory
		_productSpecificationResourceFactory;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private SettingsFactory _settingsFactory;

}