<#if dataFactory.maxCommerceGroupCount != 0>
	<#assign
		accountEntryModels = dataFactory.newAccountEntryModels()
		commerceCurrencyModel = dataFactory.newCommerceCurrencyModel()
		commerceGroupModels = dataFactory.newCommerceGroupModels()
		commerceInventoryWarehouseModels = dataFactory.newCommerceInventoryWarehouseModels()
		countryModel = dataFactory.newCountryModel()
		cpOptionCategoryModels = dataFactory.newCPOptionCategoryModels()
		cpOptionModel = dataFactory.newCPOptionModel("select", 1)
		cpTaxCategoryModel = dataFactory.newCPTaxCategoryModel()

		commerceChannelModels = dataFactory.newCommerceChannelModels(commerceGroupModels, commerceCurrencyModel)
		cpSpecificationOptionModels = dataFactory.newCPSpecificationOptionModels(cpOptionCategoryModels)

		commerceChannelGroupModels = dataFactory.newCommerceChannelGroupModels(commerceChannelModels)

		commerceShippingMethodModels = dataFactory.newCommerceShippingMethodModels(commerceChannelGroupModels)
	/>

	<#list accountEntryModels as accountEntryModel>
		${dataFactory.toInsertSQL(accountEntryModel)}

		${dataFactory.toInsertSQL(dataFactory.newAccountEntryUserRelModel(sampleUserModel, accountEntryModel.accountEntryId))}

		${dataFactory.toInsertSQL(dataFactory.newAccountEntryGroupModel(accountEntryModel))}
	</#list>

	${dataFactory.toInsertSQL(commerceCurrencyModel)}

	<#list commerceGroupModels as commerceGroupModel>
		${dataFactory.toInsertSQL(commerceGroupModel)}

		<#assign commerceSiteNavigationPortletPreferencesModels = dataFactory.newCommerceSiteNavigationPortletPreferencesModels(commerceGroupModel) />

		<#list commerceSiteNavigationPortletPreferencesModels as commerceSiteNavigationPortletPreferencesModel>
			${dataFactory.toInsertSQL(commerceSiteNavigationPortletPreferencesModel)}
		</#list>

		<#list dataFactory.newCommerceSiteNavigationPortletDDMTemplateModels(commerceGroupModel.groupId) as commerceSiteNavigationPortletDDMTemplateModel>
			${dataFactory.toInsertSQL(commerceSiteNavigationPortletDDMTemplateModel)}
		</#list>

		<#list dataFactory.newCommerceSiteNavigationPortletPreferenceValueModels(commerceSiteNavigationPortletPreferencesModels) as commerceSiteNavigationPortletPreferenceValueModel>
			${dataFactory.toInsertSQL(commerceSiteNavigationPortletPreferenceValueModel)}
		</#list>

		<#list dataFactory.newLayoutSetModels(commerceGroupModel.groupId, "minium_WAR_miniumtheme") as commerceLayoutSetModel>
			${dataFactory.toInsertSQL(commerceLayoutSetModel)}
		</#list>

		<#list dataFactory.newCommerceLayoutModels(commerceGroupModel.groupId) as commerceLayoutModel>
			<#assign portletPreferencesModels = dataFactory.newCommercePortletPreferencesModels(commerceLayoutModel) />

			<#list portletPreferencesModels as portletPreferencesModel>
				${dataFactory.toInsertSQL(portletPreferencesModel)}
			</#list>

			<#list dataFactory.newCommerceLayoutPortletPreferenceValueModels(portletPreferencesModels) as portletPreferenceValueModel>
				${dataFactory.toInsertSQL(portletPreferenceValueModel)}
			</#list>

			<@insertLayout _layoutModel=commerceLayoutModel />
		</#list>
	</#list>

	<#list commerceInventoryWarehouseModels as commerceInventoryWarehouseModel>
		${dataFactory.toInsertSQL(commerceInventoryWarehouseModel)}

		<#list commerceChannelModels as commerceChannelModel>
			${dataFactory.toInsertSQL(dataFactory.newCommerceChannelRelModel(dataFactory.commerceInventoryWarehouseClassNameId, commerceInventoryWarehouseModel.commerceInventoryWarehouseId, commerceChannelModel.commerceChannelId))}
		</#list>
	</#list>

	${dataFactory.toInsertSQL(countryModel)}

	<#list cpOptionCategoryModels as cpOptionCategoryModel>
		${dataFactory.toInsertSQL(cpOptionCategoryModel)}
	</#list>

	${dataFactory.toInsertSQL(cpOptionModel)}

	${dataFactory.toInsertSQL(dataFactory.newCPOptionValueModel(cpOptionModel.CPOptionId, 1))}

	${dataFactory.toInsertSQL(cpTaxCategoryModel)}

	<#list commerceChannelModels as commerceChannelModel>
		${dataFactory.toInsertSQL(commerceChannelModel)}
	</#list>

	<#list cpSpecificationOptionModels as cpSpecificationOptionModel>
		${dataFactory.toInsertSQL(cpSpecificationOptionModel)}
	</#list>

	<#list commerceChannelGroupModels as commerceChannelGroupModel>
		${dataFactory.toInsertSQL(commerceChannelGroupModel)}
	</#list>

	<#list dataFactory.newCommerceCatalogModels(commerceCurrencyModel) as commerceCatalogModel>
		${dataFactory.toInsertSQL(commerceCatalogModel)}

		${dataFactory.toInsertSQL(dataFactory.newCommerceCatalogResourcePermissionModel(commerceCatalogModel))}

		<#assign
			commerceCatalogGroupModel = dataFactory.newCommerceCatalogGroupModel(commerceCatalogModel)

			commercePriceListModels = dataFactory.newCommercePriceListModels(commerceCatalogGroupModel.groupId, commerceCurrencyModel.commerceCurrencyId, true, true, "price-list")

			promotionCommercePriceListModel = dataFactory.newCommercePriceListModel(commerceCatalogGroupModel.groupId, commerceCurrencyModel.commerceCurrencyId, true, true, "promotion")

			commerceProductDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, 0, "Commerce Product")

			cpDefinitionDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, commerceProductDLFolderModel.folderId, "Commerce Product Definition")

			cProductModels = dataFactory.newCProductModels(commerceCatalogGroupModel.groupId)
		/>

		${dataFactory.toInsertSQL(commerceCatalogGroupModel)}

		<#list commercePriceListModels as commercePriceListModel>
			${dataFactory.toInsertSQL(commercePriceListModel)}
		</#list>

		${dataFactory.toInsertSQL(promotionCommercePriceListModel)}

		${dataFactory.toInsertSQL(commerceProductDLFolderModel)}

		${dataFactory.toInsertSQL(cpDefinitionDLFolderModel)}

		<#list cProductModels as cProductModel>
			<#assign
				cpDefinitionModels = dataFactory.newCPDefinitionModels(cpTaxCategoryModel, cProductModel)

				friendlyURLEntryModel = dataFactory.newFriendlyURLEntryModel(globalGroupModel.groupId, dataFactory.CProductClassNameId, cProductModel.CProductId)

				friendlyURLEntryLocalizationModel = dataFactory.newFriendlyURLEntryLocalizationModel(friendlyURLEntryModel, "definition-" + cProductModel.publishedCPDefinitionId)
			/>

			${dataFactory.toInsertSQL(cProductModel)}

			<#list cpDefinitionModels as cpDefinitionModel>
				${dataFactory.toInsertSQL(cpDefinitionModel)}

				<#list commerceChannelModels as commerceChannelModel>
					${dataFactory.toInsertSQL(dataFactory.newCommerceChannelRelModel(dataFactory.CPDefinitionClassNameId, cpDefinitionModel.CPDefinitionId, commerceChannelModel.commerceChannelId))}
				</#list>

				${dataFactory.toInsertSQL(dataFactory.newCPDefinitionModelAssetEntryModel(cpDefinitionModel, cpDefinitionModel.groupId))}

				<#assign cpDefinitionLocalizationModel = dataFactory.newCPDefinitionLocalizationModel(cpDefinitionModel) />

				${dataFactory.toInsertSQL(cpDefinitionLocalizationModel)}

				<#list dataFactory.getSequence(dataFactory.maxCPDefinitionSpecificationOptionValueCount) as cpDefinitionSpecificationOptionValueCount>
					<#assign
						cpSpecificationOptionModel = cpSpecificationOptionModels[cpDefinitionSpecificationOptionValueCount - 1]

						cpDefinitionSpecificationOptionValueModel = dataFactory.newCPDefinitionSpecificationOptionValueModel(cpDefinitionModel.CPDefinitionId, cpSpecificationOptionModel.CPSpecificationOptionId, cpSpecificationOptionModel.CPOptionCategoryId, cpDefinitionSpecificationOptionValueCount)
					/>

					${dataFactory.toInsertSQL(cpDefinitionSpecificationOptionValueModel)}
				</#list>

				<#list dataFactory.newCPInstanceModels(cpDefinitionModel) as cpInstanceModel>
					${dataFactory.toInsertSQL(cpInstanceModel)}

					<#list commerceInventoryWarehouseModels as commerceInventoryWarehouseModel>
						<#assign commerceInventoryWarehouseItemModel = dataFactory.newCommerceInventoryWarehouseItemModel(commerceInventoryWarehouseModel, cpInstanceModel) />

						${dataFactory.toInsertSQL(commerceInventoryWarehouseItemModel)}

						${csvFileWriter.write("commerceInventoryWarehouseItem", virtualHostModel.hostname + "," + commerceInventoryWarehouseItemModel.commerceInventoryWarehouseItemId + ", " + commerceInventoryWarehouseItemModel.commerceInventoryWarehouseId + ", " + cpInstanceModel.CPInstanceId + "\n")}
					</#list>

					<#list commercePriceListModels as commercePriceListModel>
						${dataFactory.toInsertSQL(dataFactory.newCommercePriceEntryModel(commercePriceListModel.commercePriceListId, cpInstanceModel.CPInstanceUuid, cpDefinitionModel.CProductId))}
					</#list>

					${dataFactory.toInsertSQL(dataFactory.newCommercePriceEntryModel(promotionCommercePriceListModel.commercePriceListId, cpInstanceModel.CPInstanceUuid, cpDefinitionModel.CProductId))}

					${csvFileWriter.write("commerceProduct", virtualHostModel.hostname + "," + friendlyURLEntryLocalizationModel.urlTitle + ", " + cpInstanceModel.CPInstanceId + ", " + cpInstanceModel.gtin + ", " + cpInstanceModel.manufacturerPartNumber + ", " + cpInstanceModel.sku + ", " + cpDefinitionModel.CPDefinitionId + ", " + cpDefinitionLocalizationModel.name + ", " + cpDefinitionLocalizationModel.description + ", " + commerceChannelGroupModels[0].groupId + ", " + commerceCatalogModel.commerceCatalogId + ", " + commerceCatalogGroupModel.groupId + ", " + commerceCurrencyModel.commerceCurrencyId + "\n")}
				</#list>

				${csvFileWriter.write("cpDefinition", virtualHostModel.hostname + "," + cpDefinitionModel.CPDefinitionId + "\n")}

				<#if (dataFactory.maxCPDefinitionAttachmentTypeImageCount != 0) || (dataFactory.maxCPDefinitionAttachmentTypePDFCount != 0)>
					<#include "commerce_product_attachment_file_entries.ftl">
				</#if>
			</#list>

			${dataFactory.toInsertSQL(friendlyURLEntryModel)}

			${dataFactory.toInsertSQL(friendlyURLEntryLocalizationModel)}

			${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryMapping(friendlyURLEntryModel))}
		</#list>
	</#list>

	<#list accountEntryModels as accountEntryModel>
		<#assign
		addressModel = dataFactory.newAddressModel(accountEntryModel.accountEntryId, countryModel.countryId)

		accountEntryCommerceOrderModels = dataFactory.newAccountEntryCommerceOrderModels(commerceChannelGroupModels[0].groupId, accountEntryModel.accountEntryId, commerceCurrencyModel.commerceCurrencyId, addressModel.addressId, addressModel.addressId, commerceShippingMethodModels[0].commerceShippingMethodId, "Standard Delivery", 2)
		/>

		${dataFactory.toInsertSQL(addressModel)}

		<#list accountEntryCommerceOrderModels as accountEntryCommerceOrderModel>
			${dataFactory.toInsertSQL(accountEntryCommerceOrderModel)}

			${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(accountEntryCommerceOrderModel, commercePriceListModels[0].commercePriceListId, cProductModels[dataFactory.getRandomCProductModelIndex()]))}
		</#list>

		<#if dataFactory.maxAccountEntryCommerceOrderCount != 0>
			<#assign
			accountEntryCommerceOrderItemModel = dataFactory.newCommerceOrderItemModel(accountEntryCommerceOrderModels[0], commercePriceListModels[0].commercePriceListId, cProductModels[dataFactory.getRandomCProductModelIndex()])
			/>

			${csvFileWriter.write("commerceDeliveryAPI", virtualHostModel.hostname + "," + accountEntryModel.accountEntryId + "," + commerceChannelModels[0].commerceChannelId + "," + addressModel.addressId + "," + addressModel.countryId + "," + commerceCurrencyModel.code + "," + commerceShippingMethodModels[0].engineKey + "," + accountEntryCommerceOrderItemModel.CProductId + "," + accountEntryCommerceOrderItemModel.CPInstanceId + "," + accountEntryCommerceOrderModels[0].commerceOrderId + "\n")}
		</#if>
	</#list>

	<#list commerceChannelGroupModels as commerceChannelGroupModel>
		<#assign
		commerceB2BSiteTypePortletPreferencesModel = dataFactory.newCommerceB2BSiteTypePortletPreferencesModel(commerceChannelGroupModel.groupId)

		commerceShippingMethodModel = commerceShippingMethodModels[commerceChannelGroupModel?index]

		commerceShippingFixedOptionModel = dataFactory.newCommerceShippingFixedOptionModel(commerceChannelGroupModel.groupId, commerceShippingMethodModel.commerceShippingMethodId)
		/>

		${dataFactory.toInsertSQL(commerceB2BSiteTypePortletPreferencesModel)}

		${dataFactory.toInsertSQL(dataFactory.newCommerceB2BSiteTypePortletPreferenceValueModel(commerceB2BSiteTypePortletPreferencesModel))}

		${dataFactory.toInsertSQL(commerceShippingMethodModel)}

		${dataFactory.toInsertSQL(commerceShippingFixedOptionModel)}

		<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModel.groupId, accountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, commerceShippingMethodModel.commerceShippingMethodId, 8) as cancelledCommerceOrderModel>
			${dataFactory.toInsertSQL(cancelledCommerceOrderModel)}

			${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(cancelledCommerceOrderModel, commercePriceListModels[0].commercePriceListId, cProductModels[0]))}
		</#list>

		<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModel.groupId, accountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, commerceShippingMethodModel.commerceShippingMethodId, 1) as pendingCommerceOrderModel>
			${dataFactory.toInsertSQL(pendingCommerceOrderModel)}

			<#assign
			randomCProductModel = cProductModels[dataFactory.getRandomCProductModelIndex()]

			pendingCommerceOrderItemModel = dataFactory.newCommerceOrderItemModel(pendingCommerceOrderModel, commercePriceListModels[0].commercePriceListId, randomCProductModel)
			/>

			${dataFactory.toInsertSQL(pendingCommerceOrderItemModel)}

			${csvFileWriter.write("commerceOrder", virtualHostModel.hostname + "," + pendingCommerceOrderModel.commerceOrderId + ", " + pendingCommerceOrderItemModel.commerceOrderItemId + ", " + pendingCommerceOrderItemModel.quantity + ", " + dataFactory.getCPInstanceId(randomCProductModel.publishedCPDefinitionId) + ", " + countryModel.countryId + ", " + pendingCommerceOrderModel.uuid + ", " + commerceInventoryWarehouseModels[0].commerceInventoryWarehouseId + ", " + commerceGroupModels[0].groupId + "\n")}
		</#list>
	</#list>

	<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModels[0].groupId, accountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, 0, 0, 0, "", 2) as openCommerceOrderModel>
		${dataFactory.toInsertSQL(openCommerceOrderModel)}

		${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(openCommerceOrderModel, commercePriceListModels[0].commercePriceListId, cProductModels[dataFactory.getRandomCProductModelIndex()]))}
	</#list>
</#if>