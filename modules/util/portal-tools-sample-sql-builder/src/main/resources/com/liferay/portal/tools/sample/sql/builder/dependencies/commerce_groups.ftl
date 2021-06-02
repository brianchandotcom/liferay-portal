<#if dataFactory.maxCommerceGroupCount != 0>
	<#assign
		commerceAccountEntryModels = dataFactory.newCommerceAccountEntryModels()

		commerceGroupModels = dataFactory.newCommerceGroupModels()

		commerceChannelModels = dataFactory.newCommerceChannelModels(commerceGroupModels, commerceCurrencyModel)

		commerceChannelGroupModels = dataFactory.newCommerceChannelGroupModels(commerceChannelModels)

		commerceInventoryWarehouseModels = dataFactory.newCommerceInventoryWarehouseModels()

		cpOptionModel = dataFactory.newCPOptionModel("select", 1)

		cpOptionCategoryModels = dataFactory.newCPOptionCategoryModels()

		cpSpecificationOptionModels = dataFactory.newCPSpecificationOptionModels(cpOptionCategoryModels)

		cpTaxCategoryModel = dataFactory.newCPTaxCategoryModel()

		addressModel = dataFactory.newAddressModel(commerceAccountEntryModels[0].accountEntryId, countryModel.countryId)
	/>

	<#list commerceAccountEntryModels as commerceAccountEntryModel>
		${dataFactory.toInsertSQL(commerceAccountEntryModel)}

		${dataFactory.toInsertSQL(dataFactory.newAccountEntryUserRelModel(sampleUserModel, commerceAccountEntryModel.accountEntryId))}

		${dataFactory.toInsertSQL(dataFactory.newCommerceAccountEntryGroupModel(commerceAccountEntryModel))}
	</#list>

	${dataFactory.toInsertSQL(addressModel)}

	<#list commerceInventoryWarehouseModels as commerceInventoryWarehouseModel>
		${dataFactory.toInsertSQL(commerceInventoryWarehouseModel)}

		<#list commerceChannelModels as commerceChannelModel>
			${dataFactory.toInsertSQL(dataFactory.newCommerceChannelRelModel(dataFactory.commerceInventoryWarehouseClassNameId, commerceInventoryWarehouseModel.commerceInventoryWarehouseId, commerceChannelModel.commerceChannelId))}
		</#list>
	</#list>

	${dataFactory.toInsertSQL(cpOptionModel)}

	${dataFactory.toInsertSQL(dataFactory.newCPOptionValueModel(cpOptionModel.CPOptionId, 1))}

	<#list cpOptionCategoryModels as cpOptionCategoryModel>
		${dataFactory.toInsertSQL(cpOptionCategoryModel)}
	</#list>

	<#list cpSpecificationOptionModels as cpSpecificationOptionModel>
		${dataFactory.toInsertSQL(cpSpecificationOptionModel)}
	</#list>

	${dataFactory.toInsertSQL(cpTaxCategoryModel)}

	<#list dataFactory.newCommerceCatalogModels(commerceCurrencyModel) as commerceCatalogModel>
		${dataFactory.toInsertSQL(commerceCatalogModel)}

		${dataFactory.toInsertSQL(dataFactory.newCommerceCatalogResourcePermissionModel(commerceCatalogModel))}

		<#assign
			commerceCatalogGroupModel = dataFactory.newCommerceCatalogGroupModel(commerceCatalogModel)

			commercePriceListModel = dataFactory.newCommercePriceListModel(commerceCatalogGroupModel.groupId, commerceCurrencyModel.commerceCurrencyId, true, true, "price-list")

			promotionCommercePriceListModel = dataFactory.newCommercePriceListModel(commerceCatalogGroupModel.groupId, commerceCurrencyModel.commerceCurrencyId, true, true, "promotion")

			commerceProductDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, 0, "Commerce Product")

			cpDefinitionDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, commerceProductDLFolderModel.folderId, "Commerce Product Definition")

			cProductModels = dataFactory.newCProductModels(commerceCatalogGroupModel.groupId)
		/>

		${dataFactory.toInsertSQL(commerceCatalogGroupModel)}

		${dataFactory.toInsertSQL(commercePriceListModel)}

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

						${csvFileWriter.write("commerceInventoryWarehouseItem", commerceInventoryWarehouseItemModel.commerceInventoryWarehouseItemId + ", " + commerceInventoryWarehouseItemModel.commerceInventoryWarehouseId + ", " + cpInstanceModel.CPInstanceId + "\n")}
					</#list>

					<#assign
						commercePriceEntryModel = dataFactory.newCommercePriceEntryModel(commercePriceListModel.commercePriceListId, cpInstanceModel.CPInstanceUuid, cpDefinitionModel.CProductId)

						promotionCommercePriceEntryModel = dataFactory.newCommercePriceEntryModel(promotionCommercePriceListModel.commercePriceListId, cpInstanceModel.CPInstanceUuid, cpDefinitionModel.CProductId)
					/>

					${dataFactory.toInsertSQL(commercePriceEntryModel)}

					${dataFactory.toInsertSQL(promotionCommercePriceEntryModel)}

					${csvFileWriter.write("commerceProduct", friendlyURLEntryLocalizationModel.urlTitle + ", " + cpInstanceModel.CPInstanceId + ", " + cpInstanceModel.gtin + ", " + cpInstanceModel.manufacturerPartNumber + ", " + cpInstanceModel.sku + ", " + cpDefinitionModel.CPDefinitionId + ", " + cpDefinitionLocalizationModel.name + ", " + cpDefinitionLocalizationModel.description + ", " + commerceChannelGroupModels[0].groupId + ", " + commerceCatalogModel.commerceCatalogId + ", " + commerceCatalogGroupModel.groupId + ", " + commerceCurrencyModel.commerceCurrencyId + "\n")}
				</#list>

				${csvFileWriter.write("cpDefinition", cpDefinitionModel.CPDefinitionId + "\n")}

				<#if dataFactory.maxCPDefinitionAttachmentTypePDFCount != 0>
					<#include "commerce_product_attachment_file_entries.ftl">
				</#if>
			</#list>

			${dataFactory.toInsertSQL(friendlyURLEntryModel)}

			${dataFactory.toInsertSQL(friendlyURLEntryLocalizationModel)}

			${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryMapping(friendlyURLEntryModel))}
		</#list>
	</#list>

	<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModels[0].groupId, commerceAccountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, 0, 0, 0, "", 2) as openCommerceOrderModel>
		${dataFactory.toInsertSQL(openCommerceOrderModel)}

		${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(openCommerceOrderModel, commercePriceListModel.commercePriceListId, cProductModels[dataFactory.getRandomCProductModelIndex()]))}
	</#list>

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

	<#list commerceChannelModels as commerceChannelModel>
		${dataFactory.toInsertSQL(commerceChannelModel)}
	</#list>

	<#list commerceChannelGroupModels as commerceChannelGroupModel>
		<#assign
			commerceB2BSiteTypePortletPreferencesModel = dataFactory.newCommerceB2BSiteTypePortletPreferencesModel(commerceChannelGroupModel.groupId)

			commerceShippingMethodModel = dataFactory.newCommerceShippingMethodModel(commerceChannelGroupModel.groupId)

			commerceShippingFixedOptionModel = dataFactory.newCommerceShippingFixedOptionModel(commerceChannelGroupModel.groupId, commerceShippingMethodModel.commerceShippingMethodId)
		/>

		${dataFactory.toInsertSQL(commerceB2BSiteTypePortletPreferencesModel)}

		${dataFactory.toInsertSQL(dataFactory.newCommerceB2BSiteTypePortletPreferenceValueModel(commerceB2BSiteTypePortletPreferencesModel))}

		${dataFactory.toInsertSQL(commerceShippingMethodModel)}

		${dataFactory.toInsertSQL(commerceShippingFixedOptionModel)}

		<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModel.groupId, commerceAccountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, addressModel.addressId, addressModel.addressId, commerceShippingMethodModel.commerceShippingMethodId, "Standard Delivery", 8) as cancelledCommerceOrderModel>
			${dataFactory.toInsertSQL(cancelledCommerceOrderModel)}

			${dataFactory.toInsertSQL(dataFactory.newCommerceOrderItemModel(cancelledCommerceOrderModel, commercePriceListModel.commercePriceListId, cProductModels[0]))}
		</#list>

		<#list dataFactory.newCommerceOrderModels(commerceChannelGroupModel.groupId, commerceAccountEntryModels[0].accountEntryId, commerceCurrencyModel.commerceCurrencyId, addressModel.addressId, addressModel.addressId, commerceShippingMethodModel.commerceShippingMethodId, "Standard Delivery", 1) as pendingCommerceOrderModel>
			${dataFactory.toInsertSQL(pendingCommerceOrderModel)}

			<#assign
				randomCProductModel = cProductModels[dataFactory.getRandomCProductModelIndex()]

				pendingCommerceOrderItemModel = dataFactory.newCommerceOrderItemModel(pendingCommerceOrderModel, commercePriceListModel.commercePriceListId, randomCProductModel)
			/>

			${dataFactory.toInsertSQL(pendingCommerceOrderItemModel)}

			${csvFileWriter.write("commerceOrder", pendingCommerceOrderModel.commerceOrderId + ", " + pendingCommerceOrderItemModel.commerceOrderItemId + ", " + pendingCommerceOrderItemModel.quantity + ", " + dataFactory.getCPInstanceId(randomCProductModel.publishedCPDefinitionId) + ", " + addressModel.countryId + ", " + pendingCommerceOrderModel.uuid + ", " + commerceInventoryWarehouseModels[0].commerceInventoryWarehouseId + ", " + commerceGroupModels[0].groupId + "\n")}
		</#list>

		${dataFactory.toInsertSQL(commerceChannelGroupModel)}
	</#list>
</#if>