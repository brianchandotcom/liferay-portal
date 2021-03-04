<#assign
	cpOptionModel = dataFactory.newCPOptionModel("select", 1)

	cpOptionCategoryModels = dataFactory.newCPOptionCategoryModels()

	cpSpecificationOptionModels = dataFactory.newCPSpecificationOptionModels(cpOptionCategoryModels)

	cpTaxCategoryModel = dataFactory.newCPTaxCategoryModel()

	defaultCPInstanceModel = dataFactory.newCPInstanceModel(dataFactory.newCPDefinitionModel(cpTaxCategoryModel, dataFactory.newCProductModel(0), 1), 1)

	defaultCommercePriceEntryModel = dataFactory.newCommercePriceEntryModel(0, "", 0)
/>

${dataFactory.toInsertSQL(cpOptionModel)}

${dataFactory.toInsertSQL(dataFactory.newCPOptionValueModel(cpOptionModel.CPOptionId, 1))}

<#list cpOptionCategoryModels as cpOptionCategoryModel>
	${dataFactory.toInsertSQL(cpOptionCategoryModel)}
</#list>

<#list cpSpecificationOptionModels as cpSpecificationOptionModel>
	${dataFactory.toInsertSQL(cpSpecificationOptionModel)}
</#list>

${dataFactory.toInsertSQL(cpTaxCategoryModel)}

<#list commerceCatalogGroupModels as commerceCatalogGroupModel>
	<#assign
		commercePriceListModel = dataFactory.newCommercePriceListModel(commerceCatalogGroupModel.groupId, commerceCurrencyModel.commerceCurrencyId, true, true, "price-list")

		commerceProductDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, 0, "Commerce Product")

		cpDefinitionDLFolderModel = dataFactory.newDLFolderModel(commerceCatalogGroupModel.groupId, commerceProductDLFolderModel.folderId, "Commerce Product Definition")
	/>

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

			<#assign
				cpDefinitionLocalizationModel = dataFactory.newCPDefinitionLocalizationModel(cpDefinitionModel)
			/>

			${dataFactory.toInsertSQL(cpDefinitionLocalizationModel)}

			${csvFileWriter.write("cpDefinitionLocalization", cpDefinitionLocalizationModel.name + ", " + cpDefinitionLocalizationModel.description + "\n")}

			<#list dataFactory.getSequence(dataFactory.maxCPDefinitionSpecificationOptionValueCount) as cpDefinitionSpecificationOptionValueCount>
				<#assign
					cpSpecificationOptionModel = cpSpecificationOptionModels[cpDefinitionSpecificationOptionValueCount - 1]

					cpDefinitionSpecificationOptionValueModel = dataFactory.newCPDefinitionSpecificationOptionValueModel(cpDefinitionModel.CPDefinitionId, cpSpecificationOptionModel.CPSpecificationOptionId, cpSpecificationOptionModel.CPOptionCategoryId, cpDefinitionSpecificationOptionValueCount)
				/>

				${dataFactory.toInsertSQL(cpDefinitionSpecificationOptionValueModel)}
			</#list>

			<#assign
				cpInstanceModels = dataFactory.newCPInstanceModels(cpDefinitionModel)
			/>

			<#list cpInstanceModels as cpInstanceModel>
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
