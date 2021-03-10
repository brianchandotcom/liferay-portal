<#assign
	commerceCurrencyModel = dataFactory.newCommerceCurrencyModel()

	commerceGroupModels = dataFactory.newCommerceGroupModels()

	commerceChannelModels = dataFactory.newCommerceChannelModels(commerceGroupModels, commerceCurrencyModel)

	commerceChannelGroupModels = dataFactory.newCommerceChannelGroupModels(commerceChannelModels)

	commerceInventoryWarehouseModels = dataFactory.newCommerceInventoryWarehouseModels()

	cpOptionModel = dataFactory.newCPOptionModel("select", 1)

	cpOptionCategoryModels = dataFactory.newCPOptionCategoryModels()

	cpSpecificationOptionModels = dataFactory.newCPSpecificationOptionModels(cpOptionCategoryModels)

	cpTaxCategoryModel = dataFactory.newCPTaxCategoryModel()

	defaultCPInstanceModel = dataFactory.newCPInstanceModel(dataFactory.newCPDefinitionModel(cpTaxCategoryModel, dataFactory.newCProductModel(0), 1), 1)

	defaultCommercePriceEntryModel = dataFactory.newCommercePriceEntryModel(0, "", 0)
/>

<#include "commerce_accounts.ftl">

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

		commerceProductDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, 0, "Commerce Product")

		cpDefinitionDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, commerceProductDLFolderModel.folderId, "Commerce Product Definition")
	/>

	<@insertGroup _groupModel=commerceCatalogGroupModel />

	${dataFactory.toInsertSQL(commercePriceListModel)}

	${dataFactory.toInsertSQL(commerceProductDLFolderModel)}

	${dataFactory.toInsertSQL(cpDefinitionDLFolderModel)}

	<#list dataFactory.newCProductModels(commerceCatalogGroupModel.groupId) as cProductModel>
		<#assign
			cpDefinitionModels = dataFactory.newCPDefinitionModels(cpTaxCategoryModel, cProductModel)

			cProductModel = dataFactory.setCProductModelPublishedCPDefinitionId(cProductModel, cpDefinitionModels)

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

			${csvFileWriter.write("cpDefinitionLocalization", cpDefinitionLocalizationModel.name + ", " + cpDefinitionLocalizationModel.description + "\n")}

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
					${dataFactory.toInsertSQL(dataFactory.newCommerceInventoryWarehouseItemModel(commerceInventoryWarehouseModel, cpInstanceModel))}
				</#list>

				<#assign
					defaultCPInstanceModel = cpInstanceModel

					commercePriceEntryModel = dataFactory.newCommercePriceEntryModel(commercePriceListModel.commercePriceListId, cpInstanceModel.CPInstanceUuid, cpDefinitionModel.CProductId)

					defaultCommercePriceEntryModel = commercePriceEntryModel
				/>

				${dataFactory.toInsertSQL(commercePriceEntryModel)}

				${csvFileWriter.write("cpInstance", cpInstanceModel.sku + "\n")}
			</#list>

			<#include "commerce_product_attachment_file_entries.ftl">
		</#list>

		${dataFactory.toInsertSQL(friendlyURLEntryModel)}

		${dataFactory.toInsertSQL(friendlyURLEntryLocalizationModel)}

		${dataFactory.toInsertSQL(dataFactory.newFriendlyURLEntryMapping(friendlyURLEntryModel))}

		${csvFileWriter.write("cpFriendlyURLEntry", friendlyURLEntryLocalizationModel.urlTitle + "\n")}
	</#list>
</#list>

<#include "commerce_orders.ftl">

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

	${csvFileWriter.write("commerceGroup", commerceGroupModel.groupId + ", " + commerceGroupModel.name + "\n")}
</#list>

<#list commerceChannelModels as commerceChannelModel>
	${dataFactory.toInsertSQL(commerceChannelModel)}
</#list>

<#list commerceChannelGroupModels as commerceChannelGroupModel>
	<#assign
		commerceB2BSiteTypePortletPreferencesModel = dataFactory.newCommerceB2BSiteTypePortletPreferencesModel(commerceChannelGroupModel.groupId)
	/>

	${dataFactory.toInsertSQL(commerceB2BSiteTypePortletPreferencesModel)}

	${dataFactory.toInsertSQL(dataFactory.newCommerceB2BSiteTypePortletPreferenceValueModel(commerceB2BSiteTypePortletPreferencesModel))}

	<@insertGroup _groupModel=commerceChannelGroupModel />
</#list>